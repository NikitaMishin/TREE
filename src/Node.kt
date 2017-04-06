/**
 * Created by nikita on 27.02.17.
 */




interface  Nod<P>//would be Node


//class of bred black tree
open class RbNode<T :Comparable<T>, P>(internal var  key: T, var value:P,var color:Color = Color.BLACK ):Nod<P> {
    var leftChild:RbNode<T,P>? = null
    var rightChild:RbNode<T,P>? = null
    var parent:RbNode<T,P>? = null
}
//class of binary tree
open class  BinNode<T :Comparable<T>, P>(internal var  key: T,  var value: P ):Nod<P>{
    var leftChild:BinNode<T,P>? = null
    var rightChild:BinNode<T,P>? = null
    var parent:BinNode<T,P>? = null
}

//class Bi tree Node
open class BNode<T : Comparable<T>, P>:Nod<P>{
    var leaf : Boolean = true
    var keys: MutableList<T> = mutableListOf<T>()
    var Nodes: MutableList<BNode<T,P>> = mutableListOf<BNode<T,P>>()
}

//
open class Node<T :Comparable<T>, P>(internal var  key: T, var value:P,var color:Color = Color.BLACK) {//value
    var leftChild:Node<T,P>? = null
    var rightChild:Node<T,P>? = null
    var parent:Node<T,P>? = null
    override fun equals(other: Any?): Boolean {
        if (other is Node<*, *>) return  this.key==other.key && this.value==other.value  && this.color ==other.color
        return false
    }

}
