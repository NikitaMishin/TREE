/**
 * Created by nikita on 27.02.17.
 */


/**
 *
 * interface Tree with type T of key and type P of value
 *
 */

interface  Tree<T:Comparable<T>,P>{
    fun insert(key:T, value:P):Boolean
    fun search(key:T):P?//
    fun delete(key:T):Boolean
    fun getValueByMinKey():P?
    fun getValueByMaxKey():P?
}


//// each node except root must have   t-1<keys< 2t-1
//class BTree <K:Comparable<K>,V> ( private var root:BNode<K,V>? = null,val t:Int=5):Iterable<BNode<K,V>>,Tree<K,V>{
//    override fun iterator(): Iterator<BNode<K, V>> {
//
//    }
//}// {//val t = 50?
