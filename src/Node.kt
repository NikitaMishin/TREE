/**
 * Created by nikita on 27.02.17.
 */


/**
 *
 * null is equivalent to black child
 * by default color of node is Black
 * height is undefained
*/
open class  Node<T :Comparable<T>, P>(var key: T, var value:P,var color:Boolean = true,var height:Int? = null )  {//value
    var leftChild:Node<T,P>? = null
    var rightChild:Node<T,P>? = null
    var parent:Node<T,P>? = null
}
