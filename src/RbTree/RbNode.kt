package RbTree

import InterfacesAndEnums.Color
import InterfacesAndEnums.Nod

/**
 * Created by nikita on 10.04.17.
 */

open class RbNode<T : Comparable<T>, P>(internal var key: T, var value: P, var color: Color = Color.BLACK) : Nod<T, P> {
    var leftChild: RbNode<T, P>? = null
    var rightChild: RbNode<T, P>? = null
    var parent: RbNode<T, P>? = null
    override fun equals(other: Any?): Boolean {
        if (other is RbNode<*, *>) return this.key == other.key && this.value == other.value && this.color == other.color
        return false
    }

}