/**
 * Created by nikita on 06.03.17.
 */


class TreePrinter {
    public fun <C : Tree <T, P>, T : Comparable<T>, P>  printTreeInOrder(tree: C) {
        for (i in tree) {
            if (i.color == Color.BLACK) print(i.value.toString() + " ")
            else print(27.toChar() + "[31m" + i.value + 27.toChar() + "[0m" + ' ')
        }
    }
    public fun <C : Tree <T, P>, T : Comparable<T>, P>  printTreeAsTree(tree: C) = printTree(getRootForPrint(tree))
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
    private  fun <C : Tree <T, P>, T : Comparable<T>, P> getRootForPrint(tree:C):Node<T,P>?{
        var node :Node<T,P>? = null
        link@ for (i in tree)
        {
            if (i.parent == null)
            {
                node = i
                break@link
            }
        }
        return node
    }
    private  fun <C : Tree <T, P>, T : Comparable<T>, P> getNodeByKey(tree:C,key:T):Node<T,P>? {
        var tmp = getRootForPrint(tree)
        while(tmp!=null) {
            when{
                tmp.key == key -> return tmp
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null
    }

    fun <C : Tree <T, P>, T : Comparable<T>, P> printSubTreeByKeyASTree  (tree:C,key:T)
    {
        var tmp: Node<T, P>? = getNodeByKey(tree,key)
        this.printTree(tmp)

    }
}