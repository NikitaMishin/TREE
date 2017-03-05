/**
 * Created by nikita on 03.03.17.
 */
open class RbIterator <T:Comparable<T>,P> (var node: Node<T,P>):Iterator<Node<T,P>> {
    var queue:MyQueue<Node<T,P>>? = MyQueue()
    init {
        queue!!.add(node)
    }
    override fun hasNext(): Boolean = queue!!.isNotEmpty()

    override fun next(): Node<T,P> {
        var tmp:Node<T,P> = queue!!.remove()
        if(tmp.leftChild != null) queue!!.add(tmp.leftChild!!)
        if(tmp.rightChild != null) queue!!.add(tmp.rightChild!!)
        return tmp
    }
}