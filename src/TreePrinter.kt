/**
 * Created by nikita on 06.03.17.
 */


class TreePrinter {
    public fun <C : Tree <T, P>, T : Comparable<T>, P >  printTreeInOrder(tree: C)
    {
        if (tree is RbTree<*,*>) {
            for (i in tree) {
                if (i.color == Color.BLACK) print(i.value.toString() + " ")
                else print(27.toChar() + "[31m" + i.value + 27.toChar() + "[0m" + ' ')
            }

        } else if (tree is BST<*, *>) {
            for (i in tree) {
                if (i.color == Color.BLACK) print(i.value.toString() + " ")
                else print(27.toChar() + "[31m" + i.value + 27.toChar() + "[0m" + ' ')
            }

        }
    }
    private fun <T : Comparable<T>, P>  printTree(node: Node <T, P>?, level: Int = 0) {
        if (node != null) {
            printTree(node.rightChild, level + 1)
            for (i in 1..level) print("  |")
            if (node.color == Color.RED) println(27.toChar() + "[31m" + node.value + 27.toChar() + "[0m")
            else {
                println(node.value)
            }
            printTree(node.leftChild, level + 1)
        }
    }
}