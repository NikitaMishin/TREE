import Nodes.Node
import java.util.*

/**
 * Created by nikita on 03.03.17.
 */



open class TreeIterator<T : Comparable<T>, P>(var node: Node<T, P>?) : Iterator<Node<T, P>> {
    private var next: Node<T, P>? = null
    private var v: Node<T, P>? = null
    private fun getNodeByMinKey(root: Node<T, P>): Node<T, P> {
        var tmp = root
        while (tmp.leftChild != null) tmp = tmp.leftChild!!//
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
        v = next
        if (v!!.rightChild != null) {
            next = getNodeByMinKey(v!!.rightChild!!)
            return true
        }
        if (v!!.parent != null && v!!.parent!!.rightChild == v) {
            var prev = v
            var x: Boolean = true
            if (v!!.parent != null) x = v!!.parent!!.rightChild == v
            else x = false
            while (v!!.leftChild != prev && (v!!.parent != null || x)) {
                prev = v
                v = v!!.parent
                if (v!!.parent != null) x = v!!.parent!!.rightChild == v
                else x = false
            }
            var checkout = (next!!.key < v!!.key)
            next = v
            return (checkout)
        } else {
            next = v!!.parent
            return (next != null)
        }

    }

    override fun next(): Node<T, P> = next!!
}
