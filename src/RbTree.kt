

/**
 * Created by nikita on 27.02.17.
 */

/**
 *
 */
open class RbTree <T:Comparable<T>,P>(private  var root: Node<T,P>? = null) :Tree<T,P> {

    public fun isItRbTree():Boolean {
        if (root==null) return true
        return blackHeight(root!!)>=0
    }
    private fun  blackHeight(node: Node<T, P>) : Int//check
    {
        var leftHeight =0
        var rightHeight = 0
        if (node.leftChild != null) leftHeight = blackHeight(node.leftChild!!)
        if (node.rightChild != null) rightHeight = blackHeight(node.rightChild!!)
        if (leftHeight!=rightHeight) {
            return -100
        }
        if (isBlack(node)) leftHeight++
        return leftHeight
    }
    override fun iterator(): Iterator<Node<T, P>> {
        if (root != null) {
            return TreeIterator(root!!)
        } else {
            throw UnsupportedOperationException("root == null")
        } //To change body of created functions use File | Settings | File Templates.
    }//ok

    override fun searchByKey(key: T): P? {
        var tmp: Node<T, P>? = root
        while (tmp != null) {
            when {
                tmp.key == key -> return tmp.value
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null
    }//ok

    override fun insertNode(key: T, value: P):Boolean {
        var flag: Boolean = false
        var tmp: Node<T, P>? = root
        if (root == null) {
            root = Node(key, value, color = Color.BLACK)//
            return true
        }
        link@ while (tmp != null) {
            when {
                tmp.key == key -> return false
                key > tmp.key -> if (tmp.rightChild == null) {
                    flag = true; break@link
                } else tmp = tmp.rightChild
                key < tmp.key -> if (tmp.leftChild == null) {
                    flag = false
                    break@link
                } else tmp = tmp.leftChild
            }
        }
        var newNode: Node<T, P> = Node(key = key, value = value, color = Color.RED)
        newNode.parent = tmp
        if (flag == false) {
            tmp!!.leftChild = newNode ///////
        } else tmp!!.rightChild = newNode///////
        fixUpInsertNode(newNode)
        return true
    }//ok

    private fun getNodeByMinKey(node: Node<T, P>): Node<T, P> {
        var tmp = node
        while (tmp.leftChild != null) tmp = tmp.leftChild!!
        return tmp
    }//ok

    private fun fixUpInsertNode(x: Node<T, P>) {
        var node = x
        var tmp: Node<T, P>? = null
        while (node.parent != null && node.parent!!.color == Color.RED) {
            if (node.parent?.parent?.leftChild == node.parent) {//?
                tmp = node.parent?.parent?.rightChild//null?
                if (tmp != null && tmp.color == Color.RED) {
                    node.parent?.color = Color.BLACK //black
                    tmp.color = Color.BLACK
                    node.parent?.parent?.color = Color.RED
                    node = node.parent!!.parent!!
                }
                else  {
                    if (node.parent?.rightChild == node)
                    {
                        node = node.parent!!
                        leftRotate(node)
                    }
                        node!!.parent!!.color = Color.BLACK
                        node.parent!!.parent?.color = Color.RED
                        rightRotate((node.parent!!.parent!!))//this shiy and 101

                }

            } else {
                tmp = node?.parent?.parent?.leftChild//null?
                if (tmp != null && tmp.color == Color.RED) {
                    node.parent?.color = Color.BLACK
                    tmp.color = Color.BLACK
                    node.parent?.parent?.color = Color.RED
                    node = node.parent!!.parent!!
                } else {
                    if (node.parent?.leftChild == node)
                    {
                        node = node.parent!!
                        rightRotate(node)
                    }
                        node.parent?.color = Color.BLACK
                        node.parent?.parent?.color = Color.RED
                        leftRotate(node.parent!!.parent!!)////??

                }
            }
        }
        root!!.color = Color.BLACK
    }

    private fun leftRotate(node: Node<T, P>) {
        if (node.rightChild == null) throw UnsupportedOperationException("Bad in fun left rotate right child ==null!")
        var copyNode = node.rightChild
        node.rightChild = copyNode?.leftChild

        if (copyNode!!.leftChild != null) copyNode.leftChild!!.parent = node
        copyNode.parent = node.parent

        if (node.parent == null)
        {
            this.root = copyNode
        }
        else {
            when {
                node.parent?.leftChild == node -> node.parent?.leftChild = copyNode
                node.parent?.rightChild == node -> node.parent?.rightChild = copyNode
                else -> throw UnsupportedOperationException("Bad in rotate left")
            }
        }
        copyNode.leftChild = node
        node.parent = copyNode
    }

    private fun rightRotate(node: Node<T, P>) {
        if (node.leftChild == null) throw UnsupportedOperationException("Bad in fun left rotate left child ==null!")
        var copyNode = node.leftChild
        node.leftChild = copyNode?.rightChild

        if (copyNode!!.rightChild != null) copyNode.rightChild!!.parent = node
        copyNode.parent = node.parent//>>>??

        if (node.parent == null) this.root = copyNode
        else when {
            node.parent?.leftChild == node -> node.parent?.leftChild = copyNode
            node.parent?.rightChild == node -> node.parent?.rightChild = copyNode
            else -> {
                println(copyNode.value.toString()+"its copynode")
                print (node.value)
                println(node.color)
                var printer= TreePrinter()
                printer.printTreeAsTree(this)
                throw UnsupportedOperationException("Bad in rotate right")
            }
        }
        copyNode.rightChild = node
        node.parent = copyNode
    }

   /* private fun transplant(v: Node<T, P>, u: Node<T, P>) {
        if (v.parent == null) root = u
        else if (v.parent!!.leftChild == v) v.parent!!.leftChild = u
        else v.parent!!.rightChild = u
        u.parent = v.parent
    }
    */
    override fun getValueByMinKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: Node<T, P>? = root
        while (tmp?.leftChild != null) tmp = tmp.leftChild
        return tmp?.value
    }//ok

    override fun getValueByMaxKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: Node<T, P>? = root
        while (tmp?.rightChild != null) tmp = tmp.rightChild
        return tmp?.value
    }

   // fun printTreeByBFS() = printTreeByBFS(root)
   /* open fun printTreeByBFS(node: Node<T, P>?) {
        if (node == null) throw UnsupportedOperationException("Node is null!!")
        var queue: MyQueue<Node<T, P>> = MyQueue()
        queue.add(node)
        var tmp: Node<T, P>
        while (queue.isEmpty() != true) {
            tmp = queue.remove()
            print(" " + tmp.value)
            if (tmp.leftChild != null) queue.add(tmp.leftChild!!)
            if (tmp.rightChild != null) queue.add(tmp.rightChild!!)
        }

    }
*/


    override fun removeNodeByKey(key: T) {
        var tmp: Node<T, P>? = null
        var removedNode: Node<T, P>? = root
        link@ while (removedNode != null) {
            when {
                removedNode.key == key -> {
                    break@link
                }
                key > removedNode.key -> removedNode = removedNode.rightChild
                key < removedNode.key -> removedNode = removedNode.leftChild
            }
        }
        if (removedNode == null) {
            println("Can't remove Node by key =  $key. Don't exist!!")
            return
        }
        removeNode(removedNode)
    }

    private fun removeNode(x: Node<T,P>) {
        var remNode = x
        var right = true
        var nil:Node<T,P>
        when {
            x.leftChild != null && x.rightChild != null -> {
                remNode = getNodeByMinKey(x.rightChild!!)
                x.key = remNode.key
                x.value = remNode.value
                if (remNode.rightChild != null) remNode.rightChild!!.parent = remNode.parent
            }
            x.leftChild != null -> {
                right = false
                remNode.leftChild!!.parent = x.parent
            }
            x.rightChild != null -> remNode.rightChild!!.parent = x.parent
        }
        if (remNode.parent == null)
        {
            if (right) root = remNode.rightChild
            else root = remNode.leftChild
        }
        else when {
            remNode.parent!!.leftChild == remNode -> if (right) remNode.parent!!.leftChild = remNode.rightChild else remNode.parent!!.leftChild = remNode.leftChild
            remNode.parent!!.rightChild == remNode -> if (right) remNode.parent!!.rightChild = remNode.rightChild else remNode.parent!!.rightChild = remNode.leftChild
            //else->
        }

        if (isBlack(remNode))
        {
            when
            {
                remNode.leftChild != null -> println(remNode.leftChild!!.value.toString()+remNode.leftChild!!.color)
                   // RbRemoveFixUp(remNode.leftChild)
                remNode.rightChild != null -> println(remNode.rightChild!!.value.toString()+"df")
                   //RbRemoveFixUp(remNode.rightChild)
                else->
                {
                    nil = Node(key = remNode.key, value = remNode.value,color = Color.BLACK)
                    nil.parent =remNode.parent
                    println(nil.value.toString()+"vot")
                    println(nil.parent!!.value.toString()+"svot")
                    //RbRemoveFixUp(nil)
                }
            }
        }
        return

    }

//
//    private fun RbRemoveFixUp(x: Node<T, P>?)
//    {
//        var node = x// think about if x is nil
//        if (x!!.value == x.parent!!.value) //node is nil need to clear him //he will destroy when w eleavw del fun???
//        while(x!=root && isBlack(x))
//        {
//            if (x==x!!.parent!!.leftChild)
//            {
//
//            }
//            else
//        }
//
//
//
//    }


    private fun isBlack(node:Node<T,P>?):Boolean
    {
        if (node == null) return true
        if (node.color ==Color.BLACK) return true
        return  false
    }
}