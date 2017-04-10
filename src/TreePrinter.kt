import Nodes.Color
import Nodes.Node


/**
 * Created by nikita on 06.03.17.
 */


class TreePrinter {
    public fun <C : Tree <T, P>, T : Comparable<T>, P> printTreeInOrder(tree: C) {
        if (tree is RbTree.RbTree<*, *>) {
            for (i in tree) {
                if (i.color == Color.BLACK) print(i.value.toString() + " ")
                else print(27.toChar() + "[31m" + i.value + 27.toChar() + "[0m" + ' ')
            }

        }
        else if (tree is BST.BST<*, *>) for (i in tree) print(i.value.toString() + " ")
    }
    public fun <C : Tree <T, P>, T : Comparable<T>, P> printTree(tree: C) {
        if (tree is RbTree.RbTree<*, *>) {
             return printTreeAsTree(node = tree.root, level = 0)
        }
        else if (tree is BST.BST<*, *>)   return printTreeAsTree(node = tree.root, level = 0)

    }
    private fun printTreeAsTree(node: Node<*, *>?, level: Int = 0) {
        if (node != null) {
            printTreeAsTree(node.rightChild, level + 1)
            for (i in 1..level) print("  |")
            if (node.color == Color.RED) println(27.toChar() + "[31m" + node.value + 27.toChar() + "[0m")
            else {
                println(node.value)
            }
            printTreeAsTree(node.leftChild, level + 1)
        }
    }
}