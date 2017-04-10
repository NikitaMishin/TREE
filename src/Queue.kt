import java.util.*

/**
 * Created by nikita on 05.03.17.
 */

/**
 * standard MyQueue for type P
 * fun isEmpty return true if queue empty
 * fun add insert element in queue
 * fun "remove"   remove and return this element
 */

open class MyQueue<P>() {
    private var head: Item<P>? = null
    private var tail: Item<P>? = null

    class Item<P>(val value: P, var next: Item<P>? = null)

    fun add(element: P): Boolean {
        var item: Item<P> = Item(value = element)
        if (tail == null) {
            head = item
            tail = head
        } else {
            tail?.next = item
            tail = item
        }
        return true
    }

    fun remove(): P {
        if (head == null) throw UnsupportedOperationException("Queue is empty!!")
        val item: Item<P> = head!!
        head = head!!.next
        if (head == null) tail = null
        return item.value
    }

    fun isEmpty(): Boolean = head == null
    fun isNotEmpty(): Boolean = head != null
}