
/**
 * Created by nikita on 27.02.17.
 */

/**
 *
 */
open class RbTree <T:Comparable<T>,P>(private  var root: Node<T,P>? = null) :Tree<T,P>, Iterable<  Node<T,P>>{

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

    override fun iterator(): Iterator<Node<T, P>> =TreeIterator(root)

    override fun search(key: T): P? {
        var tmp: Node<T, P>? = root
        while (tmp != null) {
            when {
                tmp.key == key -> return tmp.value
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null
    }

    override fun insert(key: T, value: P):Boolean {
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
                throw UnsupportedOperationException("Bad in rotate right")
            }
        }
        copyNode.rightChild = node
        node.parent = copyNode
    }

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


    override fun delete(key: T):Boolean {
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
            return false
        }
        if (removedNode==root&& removedNode.leftChild==null&& removedNode.rightChild==null) {
            root = null
            return true
        }
        removeNode(removedNode)
        return true
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
        }
        if (isBlack(remNode))
        {
            when
            {
                remNode.leftChild != null -> RbRemoveFixUp(remNode.leftChild)
                remNode.rightChild != null -> RbRemoveFixUp(remNode.rightChild)
                else->
                {

                    nil = Node(key = remNode.key, value = remNode.value,color = Color.BLACK)
                    nil.parent =remNode.parent
                    if (remNode.parent!!.leftChild ==null) remNode!!.parent!!.leftChild=nil
                    else remNode!!.parent!!.rightChild = nil

                    RbRemoveFixUp(nil)///her
                    if (nil.parent!!.leftChild== nil) nil.parent!!.leftChild =null
                    else nil.parent!!.rightChild = null
                }
            }

        }
        return

    }


    private fun RbRemoveFixUp(x: Node<T, P>?) {
        var w: Node<T, P>?
        var node = x
        while (node !== root && isBlack(node)) {
            if (node === node!!.parent!!.leftChild) {
                w = node!!.parent!!.rightChild
                if (isBlack(w)==false){
                    w!!.color = Color.BLACK
                    node.parent!!.color = Color.RED
                    leftRotate(node.parent!!)
                    w = node.parent!!.rightChild
                }
                if (isBlack(w!!.leftChild)&& isBlack(w.rightChild)){
                    w.color = Color.RED
                    node = node.parent
                }
                else if (isBlack(w.rightChild))
                {
                    w.leftChild!!.color = Color.BLACK
                    w.color = Color.RED
                    rightRotate(w)
                    w = node.parent!!.rightChild

                }//
                else{
                    w.color = node.parent!!.color
                    node.parent!!.color = Color.BLACK
                    w.rightChild!!.color = Color.BLACK
                    leftRotate(node.parent!!)
                    node = root
                }

            }



            else {
                w = node!!.parent!!.leftChild
                if (isBlack(w) == false){
                    w!!.color = Color.BLACK
                    node.parent!!.color = Color.RED
                    rightRotate(node.parent!!)
                    w = node.parent!!.leftChild
                }
                if (isBlack(w!!.rightChild)&& isBlack(w.leftChild)){
                    w.color = Color.RED///????cuurious
                    node = node.parent
                }
                else if (isBlack(w.leftChild))
                {
                    w.rightChild!!.color = Color.BLACK
                    w.color = Color.RED
                    leftRotate(w)
                    w = node.parent!!.leftChild

                }
                else{
                    w.color = node.parent!!.color
                    node.parent!!.color = Color.BLACK
                    w.leftChild!!.color = Color.BLACK
                    rightRotate(node.parent!!)
                    node = root
                }
            }
        }
        if (isBlack(node)==false) node!!.color =Color.BLACK
    }

    private fun isBlack(node:Node<T,P>?):Boolean
    {
        if (node == null) return true
        if (node.color ==Color.BLACK) return true
        return  false
    }


}