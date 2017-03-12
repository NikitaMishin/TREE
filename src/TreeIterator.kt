import java.util.*

/**
 * Created by nikita on 03.03.17.
 */
open class TreeIterator <T:Comparable<T>,P> (var node: Node<T,P>):Iterator<Node<T,P>> {

    //var queue:MyQueue<Node<T,P>>? = MyQueue()
        var stack: Stack<Node<T,P>>? = Stack()
        var queue:MyQueue<Node<T,P>> = MyQueue()
    init {
        var tmp:Node<T,P>? = node
        while (tmp != null) {
            stack!!.push(tmp)
            tmp = tmp.leftChild
        }
        while( stack!!.size > 0 ) {
            tmp = stack!!.pop()
            queue.add(tmp)
            tmp = tmp.rightChild
            while (tmp != null) {
                stack!!.push( tmp)
                tmp = tmp.leftChild
            }
        }
    }
  //  //override fun hasNext(): Boolean = queue!!.isNotEmpty()
    override fun hasNext(): Boolean = queue.isNotEmpty()
    override fun next(): Node<T,P> = queue.remove()
}