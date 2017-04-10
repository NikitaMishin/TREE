package BST


/**
 * Created by nikita on 03.03.17.
 */



open class TreeIterator<T : Comparable<T>, P>(var node: BstNode<T, P>?) : Iterator<BstNode<T, P>> {
    private var next: BstNode<T, P>? = null
    private var vertex: BstNode<T, P>? = null
    private fun getNodeByMinKey(root: BstNode<T, P>): BstNode<T, P> {
        var tmp = root
        while (tmp.leftChild != null) tmp = tmp.leftChild!!
        return tmp
    }

    override fun hasNext(): Boolean {
        if (node == null) {
            return false
        }
        if (next == null) {
            next = getNodeByMinKey(node!!)
            return true
        }
        vertex = next
        if (vertex!!.rightChild != null) {
            next = getNodeByMinKey(vertex!!.rightChild!!)
            return true
        }
        if (vertex!!.parent != null && vertex!!.parent!!.rightChild == vertex) {
            var prev = vertex
            var isRightChild: Boolean = true
            if (vertex!!.parent != null) isRightChild = vertex!!.parent!!.rightChild == vertex
            else isRightChild = false
            while (vertex!!.leftChild != prev && (vertex!!.parent != null || isRightChild)) {
                prev = vertex
                vertex = vertex!!.parent
                if (vertex!!.parent != null) isRightChild = vertex!!.parent!!.rightChild == vertex
                else isRightChild = false
            }
            var checkout = (next!!.key < vertex!!.key)
            next = vertex
            return (checkout)
        } else {
            next = vertex!!.parent
            return (next != null)
        }

    }

    override fun next(): BstNode<T, P> = next!!
}
