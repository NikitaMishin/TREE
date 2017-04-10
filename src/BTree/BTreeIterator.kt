package BTree

/**
 * Created by nikita on 31.03.17.
 */
class BtreeIterator<T : Comparable<T>, P>(var node: BNode<T, P>?) : Iterator<BNode<T, P>> {
    private var queue: MutableList<BNode<T, P>> = mutableListOf()

    init {
        if (node != null) queue.add(node!!)
    }

    override fun hasNext(): Boolean = queue.isNotEmpty()

    override fun next(): BNode<T, P> {
        var tmp = queue.removeAt(0)
        if (tmp.Nodes.isNotEmpty()) queue.addAll(tmp.Nodes)
        return tmp
    }
}