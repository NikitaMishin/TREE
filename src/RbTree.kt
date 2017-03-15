

/**
 * Created by nikita on 27.02.17.
 */

/**
 *
 */
open class RbTree <T:Comparable<T>,P>(private  var root: Node<T,P>? = null) :Tree<T,P> {

    override fun iterator(): Iterator<Node<T, P>> {
        if (root != null) {
            return TreeIterator(root!!)
        } else {
            throw UnsupportedOperationException("root ==null")
        } //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchByKey(key: T): P? {
        var tmp: Node<T, P>? = root
        while (tmp != null) {
            when {
                tmp.key == key -> return tmp.value
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        print("Can't find value by key =  $key.")
        return null
    }

    override fun insertNode(key: T, value: P) {
        var flag: Boolean = false
        var tmp: Node<T, P>? = root
        if (root == null) {
            root = Node(key, value, color = Color.BLACK)//
            return
        }
        link@ while (tmp != null) {
            when {
                tmp.key == key -> {
                    println("Already have value by key = $key");return
                }
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
    }

    private fun getNodeByMinKey(node: Node<T, P>): Node<T, P> {
        var tmp = node
        while (tmp.leftChild != null) tmp = tmp.leftChild!!
        return tmp
    }

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

    private fun transplant(v: Node<T, P>, u: Node<T, P>) {
        if (v.parent == null) root = u
        else if (v.parent!!.leftChild == v) v.parent!!.leftChild = u
        else v.parent!!.rightChild = u
        u.parent = v.parent
    }

    fun printTree() = printTree(this.root, 0)
    public fun printTree(node: Node <T, P>?, level: Int = 0) {
        if (node != null) {
            printTree(node.rightChild, level + 1)
            for (i in 1..level) print("  |")
            if (node.color == Color.RED) println(27.toChar() + "[31m" + node.value + 27.toChar() + "[0m")
            else {
                println(node.value)
            }
            printTree(node.leftChild, level + 1)
        }
    }

    fun printTreeByBFS() = printTreeByBFS(root)
    open fun printTreeByBFS(node: Node<T, P>?) {
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
        //
        tmp = removedNode
        var colorOftmp = removedNode.color

    }

    override fun getValueByMinKey(key: T): P? {
        if (root == null) {
            println("Tree is empty!")
            return null
        }
        var tmp: Node<T, P>? = root
        while (tmp?.leftChild != null) tmp = tmp.leftChild
        return tmp?.value
    }

    override fun getValueByMaxKey(key: T): P? {
        if (root == null) {
            println("Tree is empty!")
            return null
        }
        var tmp: Node<T, P>? = root
        while (tmp?.rightChild != null) tmp = tmp.rightChild
        return tmp?.value
    }

    private fun RbRemoveFixUp(x: Node<T, P>?) {
        while (x != root && (x == null || x.color == Color.BLACK)) {
            if (x == x!!.parent!!.leftChild) {

            } else {


            }


        }
    }
    /*
    public fun removeNode(key: T): Boolean {
        var removedNode: Node<T, P>? = root
        link@ while (removedNode != null) {
            when {
                removedNode.key == key -> break@link
                key > removedNode.key -> removedNode = removedNode.rightChild
                key < removedNode.key -> removedNode = removedNode.leftChild
            }
        }
        if (tmp == null) return false

        var tmp = tmp
        var yColor = removedNode.color
        var y = removedNode


        if (removedNode.leftChild == null) {
            tmp = removedNode.rightChild
            transplant(removedNode, removedNode.rightChild!!)

        } else if (removedNode.rightChild == null) {
            tmp = removedNode.leftChild
            transplant(removedNode, removedNode.leftChild!!)
        } else {
            y = getNodeByMinKey(removedNode)
            yColor = y.color
            if (y.parent == removedNode) {


            }
        }
        if (yColor == false) RbRemoveFixUp(tmp)

    }*/
}