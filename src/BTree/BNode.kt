package BTree

import InterfacesAndEnums.Nod

/**
 * Created by nikita on 10.04.17.
 */
open class BNode<K:Comparable<K>,V> : Nod<K, V> {
    var isLeaf: Boolean = true
    var keys: MutableList <Pair<K,V>> = mutableListOf<Pair<K,V>>()
    var Nodes: MutableList<BNode<K, V>> = mutableListOf<BNode<K, V>>()
}