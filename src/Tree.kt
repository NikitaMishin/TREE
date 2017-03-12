/**
 * Created by nikita on 27.02.17.
 */


/**
 *
 * interface Tree with type T of key and type P of value
 *
 */

interface  Tree<T:Comparable<T>,P>: Iterable<Node<T,P>>{
    fun insertNode(key:T,value:P)
    fun searchByKey(key:T):P?//
    fun removeNodeByKey(key:T)
    fun getValueByMinKey(key: T):P?
    fun getValueByMaxKey(key: T):P?
}