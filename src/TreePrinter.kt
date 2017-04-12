import BTree.BNode
import InterfacesAndEnums.Tree
import InterfacesAndEnums.Color

/**
 * Created by nikita on 06.03.17.
 */

import  InterfacesAndEnums.Nod


import BST.BstNode
import RbTree.RbNode

class TreePrinter {
    public fun <C : Tree<T, P>, T : Comparable<T>, P> printTreeInOrder(tree: C) {
        when (tree) {
            is RbTree.RbTree<*, *> -> {
                for (i in tree) {
                    if (i.color == Color.BLACK) print(i.value.toString() + " ")
                    else print(27.toChar() + "[31m" + i.value + 27.toChar() + "[0m" + ' ')
                }
            }
            is BST.BST<*, *> -> {
                for (i in tree) print(i.value.toString() + " ")
            }
            is BTree.BTree<*, *> -> println("Not implemented")
        }
    }

    public fun <C : Tree<T, P>, T : Comparable<T>, P> printTree(tree: C) {
        when (tree) {
            is RbTree.RbTree<*, *> -> printTreeAsRbTree(node = tree.root, level = 0)
            is BST.BST<*, *> -> printTreeAsBstTree(node = tree.root, level = 0)
            is BTree.BTree<*, *> -> printBTree(tree)
        }
    }


    private fun printTreeAsBstTree(node: BstNode<out Comparable<*>, *>?, level: Int = 0) {
        if (node != null) {
            printTreeAsBstTree(node.rightChild, level + 1)
            for (i in 1..level) print("  |")
            println(node.value)
            printTreeAsBstTree(node.leftChild, level + 1)
        }
    }

    private fun printTreeAsRbTree(node: RbNode<out Comparable<*>, *>?, level: Int = 0) {
        if (node != null) {
            printTreeAsRbTree(node.rightChild, level + 1)
            for (i in 1..level) print("  |")
            if (node.color == Color.RED) println(27.toChar() + "[31m" + node.value + 27.toChar() + "[0m")
            else {
                println(node.value)
            }
            printTreeAsRbTree(node.leftChild, level + 1)
        }
    }
    private fun <C : BTree.BTree <*, *>> printBTree(tree: C) {
        var size: Int = 0
        var levelEnd: Int = 1
        for (i in tree) {
            print(" " + i.keys + " ")
            size += i.keys.size + 1
            levelEnd--
            if (levelEnd == 0) {
                println()
                levelEnd = size
                size = 0
            }
        }
    }
}
