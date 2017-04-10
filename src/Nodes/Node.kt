package Nodes

/**
 * Created by nikita on 27.02.17.
 */


interface Nod<P,V>


open class BNode<K:Comparable<K>,V> : Nod<K, V> {
    var isLeaf: Boolean = true
    var keys: MutableList <Pair<K,V>> = mutableListOf<Pair<K,V>>()
    var Nodes: MutableList<BNode<K, V>> = mutableListOf<BNode<K, V>>()
}

open class Node<T : Comparable<T>, P>(internal var key: T, var value: P, var color: Color = Color.BLACK): Nod<T, P> {
    var leftChild: Node<T, P>? = null
    var rightChild: Node<T, P>? = null
    var parent: Node<T, P>? = null
    override fun equals(other: Any?): Boolean {
        if (other is Node<*, *>) return this.key == other.key && this.value == other.value && this.color == other.color
        return false
    }

}
