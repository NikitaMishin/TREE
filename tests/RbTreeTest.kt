
import RbTree.RbNode
import jdk.nashorn.internal.ir.annotations.Immutable
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

/**
 * Created by nikita on 13.03.17.
 */
internal class RbTreeTest {

    @Test
    fun searchInNotEmptyTreeExistedValue() {
        val notEmptyTree = RbTree.RbTree<Int, Int>()
        for (i in 1..30 step 4) notEmptyTree.insert(i, i)
        assertEquals(5, notEmptyTree.search(5))
        assertEquals(13, notEmptyTree.search(13))

    }//ok

    @Test
    fun searchInNotEmptyTreeUnExistedValue() {
        var emptyTree = RbTree.RbTree<Int, Int>()
        val notEmptyTree = RbTree.RbTree<Int, Int>()
        for (i in 1..30 step 7) notEmptyTree.insert(i, i)
        assertEquals(null, notEmptyTree.search(-5))
        assertEquals(null, notEmptyTree.search(-13))
        assertEquals(null, emptyTree.search(-6))

    }//ok

    @Test
    fun searchInEmptyTree() {
        var emptyTree = RbTree.RbTree<Int, Int>()
        assertEquals(null, emptyTree.search(3))
    }//ok

    @Test
    operator fun iterator() {
        var Tree: RbTree.RbTree<Int, String> = RbTree.RbTree()
        var random = Random()
        for (i in 1..1000)
            Tree.insert(random.nextInt(), random.nextFloat().toString())
        var prev: RbTree.RbTree<Int, String>
        val list = mutableListOf<RbNode<Int, String>>()
        for (i in Tree) list.add(i)
        for (i in 1..list.size - 2) assertEquals(true, list[i + 1].key > list[i].key)
        list.clear()
        for (i in 1..1000000) Tree.insert(random.nextInt(), random.nextFloat().toString())
        for (i in Tree) list.add(i)
        for (i in 1..list.size - 2) assertEquals(true, list[i + 1].key > list[i].key)
    }//ok

    @Test
    fun deleteInEmptyTree() {
        val emptyTree = RbTree.RbTree<Int, Int>()
        assertEquals(false, emptyTree.delete(3))
    }//ok

    @Test
    fun deleteRedLeaf() {
        val notEmptyTree = RbTree.RbTree<Int, Int>()
        for (i in 1..30 step 4) notEmptyTree.insert(i, i)
        assertEquals(true,notEmptyTree.delete(29))
        assertEquals(true,notEmptyTree.isItRbTree())
    }//ok

    @Test
    fun deleteNodeWithOneNode() {
        val notEmptyTree = RbTree.RbTree<Int, Int>(RbNode(1,1))
        assertEquals(true,notEmptyTree.delete(1))
        assertEquals(null,notEmptyTree.root)
    }//ok




    @Test
    fun insertNodeInEmptyTree() {
        val Tree: RbTree.RbTree<Int, Int> = RbTree.RbTree()
        Tree.insert(1, 1)
        assertEquals(1, Tree.search(1))
        Tree.insert(2, 2)
        assertEquals(2, Tree.search(2))
        Tree.insert(1, 1)
        assertEquals(null, Tree.search(4))
        Tree.insert(-5, -5)
        assertEquals(-5, Tree.search(-5))
    }//ok


    @Test
    fun insertNodeInTreeCase1() {//case1
       // assertEquals(false, IdealTree.insert(5, 5))
        var Tree = RbTree.RbTree<Int, Int>()
        for (i in 1..100) {
            Tree.insert(i, i)
            assertTrue(Tree.isItRbTree())
        }
    }

    @Test
    fun insertNodeInTreeCase2() {//case2

    }

    @Test
    fun insertNodeInTreeCase3() {//case3

    }

    @Test
    fun insertNodeInTreeCase4() {//case4
    }

    @Test
    fun insertNodeInTreeCase5() {//case5
    }

    @Test
    fun insertNodeInTreeCase6() {//case6

    }

}