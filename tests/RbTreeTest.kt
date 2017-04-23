import InterfacesAndEnums.Color
import RbTree.RbNode
import jdk.nashorn.internal.ir.annotations.Immutable
import org.junit.jupiter.api.Test
import RbTree.RbTree
import org.junit.jupiter.api.Assertions.*
import java.util.*
import RbTree.TreeIterator

/**
 * Created by nikita on 13.03.17.
 */
internal class RbTreeTest {

    fun fillIdealTree(tree: RbTree<Int, Int>) {
        tree.root = RbNode(13, 13, color = Color.BLACK)
        tree.root!!.rightChild = RbNode(15, 15)
        tree.root!!.rightChild!!.parent = tree.root
        tree.root!!.leftChild = RbNode(9, 9, color = Color.RED)
        tree.root!!.leftChild!!.parent = tree.root
        tree.root!!.leftChild!!.rightChild = RbNode(11, 11, color = Color.BLACK)
        tree.root!!.leftChild!!.rightChild!!.parent = tree.root!!.leftChild
        tree.root!!.leftChild!!.leftChild = RbNode(5, 5, color = Color.BLACK)
        tree.root!!.leftChild!!.leftChild!!.parent = tree.root!!.leftChild
        tree.root!!.leftChild!!.leftChild!!.rightChild = RbNode(7, 7, color = Color.RED)
        tree.root!!.leftChild!!.leftChild!!.rightChild!!.parent = tree.root!!.leftChild!!.leftChild
        tree.root!!.leftChild!!.leftChild!!.leftChild = RbNode(3, 3, color = Color.RED)
        tree.root!!.leftChild!!.leftChild!!.leftChild!!.parent = tree.root!!.leftChild!!.leftChild
    }


    @Test
    fun searchInNotEmptyTreeExistedValue() {
        val notEmptyTree = RbTree<Int, Int>()
        for (i in 1..30 step 4) notEmptyTree.insert(i, i)
        assertEquals(5, notEmptyTree.search(5))
        assertEquals(13, notEmptyTree.search(13))

    }

    @Test
    fun searchInNotEmptyTreeUnExistedValue() {
        var emptyTree = RbTree<Int, Int>()
        val notEmptyTree = RbTree<Int, Int>()
        for (i in 1..30 step 7) notEmptyTree.insert(i, i)
        assertEquals(null, notEmptyTree.search(-5))
        assertEquals(null, notEmptyTree.search(-13))
        assertEquals(null, emptyTree.search(-6))

    }

    @Test
    fun searchInEmptyTree() {
        var emptyTree = RbTree<Int, Int>()
        assertEquals(null, emptyTree.search(3))
    }

    @Test
    operator fun iterator() {
        var Tree: RbTree<Int, String> = RbTree()
        var random = Random()
        for (i in 1..1000)
            Tree.insert(random.nextInt(), random.nextFloat().toString())
        var prev: RbTree<Int, String>
        val list = mutableListOf<RbNode<Int, String>>()
        for (i in Tree) list.add(i)
        for (i in 1..list.size - 2) assertEquals(true, list[i + 1].key > list[i].key)
        list.clear()
        for (i in 1..1000000) Tree.insert(random.nextInt(), random.nextFloat().toString())
        for (i in Tree) list.add(i)
        for (i in 1..list.size - 2) assertEquals(true, list[i + 1].key > list[i].key)
    }


    @Test
    fun isItRbTree() {
        val Tree = RbTree<Int, Int>()
        fillIdealTree(Tree)
        assertEquals(true, Tree.isItRbTree())
    }

    @Test
    fun deleteInEmptyTree() {
        val emptyTree = RbTree<Int, Int>()
        assertEquals(false, emptyTree.delete(3))
    }

    @Test
    fun deleteRedLeaf() {
        val notEmptyTree = RbTree<Int, Int>()
        for (i in 1..30 step 4) notEmptyTree.insert(i, i)
        assertEquals(true, notEmptyTree.delete(29))
        assertEquals(true, notEmptyTree.isItRbTree())
    }

    @Test
    fun insertNodeInEmptyTree() {
        val Tree: RbTree<Int, Int> = RbTree()
        Tree.insert(1, 1)
        assertEquals(1, Tree.search(1))
        Tree.insert(2, 2)
        assertEquals(2, Tree.search(2))
        Tree.insert(1, 1)
        assertEquals(null, Tree.search(4))
        Tree.insert(-5, -5)
        assertEquals(-5, Tree.search(-5))
    }

    @Test
    fun deleteNodeWithOneNode() {
        val notEmptyTree = RbTree<Int, Int>(RbNode(1, 1))
        assertEquals(true, notEmptyTree.delete(1))
        assertEquals(null, notEmptyTree.root)
    }

    @Test
    fun delete() {
        val Tree: RbTree<Int, Int> = RbTree()
        for (i in 1..10000) Tree.insert(i, i)
        for (j in 10000 downTo 1 step 3) {
            Tree.delete(j)
            assertEquals(true, Tree.isItRbTree())
        }
    }

    @Test
    fun insertExistedKey(){
        val Tree: RbTree<Int, Int> = RbTree()
        assertEquals(true,Tree.insert(1,1))
        assertEquals(false,Tree.insert(1,1))

    }

    @Test
    fun deleteAlreadyRemovedKey(){
        val Tree = RbTree<Int, Int>()
        fillIdealTree(Tree)
        assertEquals(true,Tree.delete(5))
        assertEquals(false,Tree.delete(5))
    }
    @Test
    fun deleteWhenBrotherOfRemovedChildIsBlackAndBothHisChildIsBlack() {
        val Tree = RbTree<Int, Int>()
        fillIdealTree(Tree)
        val expectedTree = RbTree<Int, Int>()
        fillIdealTree(expectedTree)
        expectedTree.root!!.leftChild!!.leftChild!!.key = 7
        expectedTree.root!!.leftChild!!.leftChild!!.value = 7
        expectedTree.root!!.leftChild!!.leftChild!!.leftChild!!.parent = expectedTree.root!!.leftChild!!.leftChild
        expectedTree.root!!.leftChild!!.leftChild!!.rightChild = null
        val printer = TreePrinter()
        Tree.delete(5)
        val iterator = TreeIterator<Int, Int>(Tree.root)
        for (i in expectedTree) if (iterator.hasNext()) assertTrue(i.equals(iterator.next()))
    }


    @Test
    fun insert() {
        val Tree: RbTree<Int, Int> = RbTree()
        for (i in 1..10000 step 12) {
            Tree.insert(i, i)
            assertEquals(true, Tree.isItRbTree())
        }
        for (i in 10000 downTo 5 step 7) {
            Tree.insert(i, i)
            assertEquals(true, Tree.isItRbTree())
        }
    }

    @Test
    fun stressTest() {
        val Tree = RbTree<Int, Int>()
        for (i in 1..14000000 step 2) Tree.insert(i, i)
        for (i in 14000000 downTo  1 step 3 ) Tree.delete(i)
        assertEquals(true,Tree.isItRbTree())
    }
}
