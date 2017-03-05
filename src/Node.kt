/**
 * Created by nikita on 27.02.17.
 */


/**
 *class Node is for comparable type T  of key and type P of value
 *
 * null in property of height equivalent to absence this property in Node
 * null in property color is equivalent to black child
 * by default color of node is True - Red
 * height is undefined
*/

open class  Node<T :Comparable<T>, P>(var key: T, var value:P,var color:Boolean = true,var height:Int? = null )  {//value
    var leftChild:Node<T,P>? = null
    var rightChild:Node<T,P>? = null
    var parent:Node<T,P>? = null
}
