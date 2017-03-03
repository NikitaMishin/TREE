/**
 * Created by nikita on 27.02.17.
 */




interface  Tree<T:Comparable<T>,P>{
    fun insertNode(key:T,value:P){}
    fun searchByKey(key:T):P?//
    fun removeNodebyKey(key:T)
    fun getValueByMinKey(key: T):P?
    fun getValueByMaxKey(key: T):P?
}