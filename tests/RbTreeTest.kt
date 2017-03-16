import jdk.nashorn.internal.ir.annotations.Immutable
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*

/**
 * Created by nikita on 13.03.17.
 */
internal class RbTreeTest {

    var IdealTree = RbTree<Int,Int>()// 3 5 8 1 2 4
    var EmptyTree = RbTree<Int,Int>()

    init {
        IdealTree.insertNode(3,3)//
        IdealTree.insertNode(5,5)//
        IdealTree.insertNode(8,8)///
        IdealTree.insertNode(1,1)//
        IdealTree.insertNode(2,2)//
        IdealTree.insertNode(4,4)//

        //var prinnt = TreePrinter()
        //prinnt.printTreeAsTree(IdealTree)
    }

    @Test
    fun isItRbTree() {
        assertEquals(true,IdealTree.isItRbTree())
        assertEquals(true,EmptyTree.isItRbTree())
    }

    @Test
    operator fun iterator() {
        var Tree  : RbTree<Int,String> = RbTree()
        var random = Random()
        for (i in 1..1000)
            Tree.insertNode (random.nextInt(),random.nextFloat().toString())
        var prev:RbTree<Int,String>
        val list = mutableListOf<Node<Int,String>>()
        for (i in Tree) list.add(i)
        for (i in 1..list.size-2) assertEquals (true,list[i+1].key > list[i].key)
        list.clear()
        for (i in 1..1000000) Tree.insertNode (random.nextInt(),random.nextFloat().toString())
        for (i in Tree) list.add(i)
        for (i in 1..list.size-2) assertEquals (true,list[i+1].key > list[i].key)
    }//ok

    @Test
    fun searchInNotEmptyTree() {
        assertEquals(5,IdealTree.searchByKey(5))
        assertEquals(3,IdealTree.searchByKey(3))
        assertEquals(null,IdealTree.searchByKey(6))
        assertEquals(2,IdealTree.searchByKey(2))

    }//ok
    @Test
    fun searchInEmptyTree() {
        var Tree = RbTree<Int,Int>()
        assertEquals(null,Tree.searchByKey(5))
        assertEquals(null,Tree.searchByKey(4))
        assertEquals(null,Tree.searchByKey(3))
        assertEquals(null,Tree.searchByKey(2))
        assertEquals(null,Tree.searchByKey(1))
    }//ok

    @Test
    fun insertNodeInEmptyTree() {
        val Tree  : RbTree<Int,Int> = RbTree()
        Tree.insertNode(1,1)
        assertEquals(1,Tree.searchByKey(1))
        Tree.insertNode(2,2)
        assertEquals(2,Tree.searchByKey(2))
        Tree.insertNode(1,1)
        assertEquals(null,Tree.searchByKey(4))
        Tree.insertNode(-5,-5)
        assertEquals(-5,Tree.searchByKey(-5))
    }//ok

    @Test
    fun insertNodeInTree() {
        assertEquals(false,IdealTree.insertNode(5,5))
        var Tree = RbTree<Int,Int>()
        for (i in 1..100) {
            Tree.insertNode(i, i)
            assertTrue(Tree.isItRbTree())
        }

    }//ok

    @Test
    fun removeNodeByKey() {
        assertEquals(1,1)
    }

    @Test
    fun getValueByMinKey() {
        assertEquals(1,IdealTree.getValueByMinKey())
    }

    @Test
    fun getValueByMaxKey() {
        assertEquals(8,IdealTree.getValueByMaxKey())
    }

}