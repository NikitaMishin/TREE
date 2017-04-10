package BST

import InterfacesAndEnums.Color
import InterfacesAndEnums.Nod

/**
 * Created by nikita on 10.04.17.
 */


open class BstNode<T : Comparable<T>, P>(internal var key: T, var value: P) : Nod<T, P> {
    var leftChild: BstNode<T, P>? = null
    var rightChild: BstNode<T, P>? = null
    var parent: BstNode<T, P>? = null
    override fun equals(other: Any?): Boolean {
        if (other is BstNode<*, *>) return this.key == other.key && this.value == other.value
        return false
    }

}