package RbTree

import InterfacesAndEnums.Color
import  InterfacesAndEnums.Tree

/**
 * Created by nikita on 27.02.17.
 */

open class RbTree<T : Comparable<T>, P>(internal var root: RbNode<T, P>? = null) : Tree<T, P>, Iterable<RbNode<T, P>> {

    internal fun isItRbTree(): Boolean {
        if (root == null) return true
        return blackHeight(root!!) >= 0
    }

    private fun blackHeight(node: RbNode<T, P>): Int//check
    {
        var leftHeight = 0
        var rightHeight = 0
        if (node.leftChild != null) leftHeight = blackHeight(node.leftChild!!)
        if (node.rightChild != null) rightHeight = blackHeight(node.rightChild!!)
        if (leftHeight != rightHeight) {
            return -100
        }
        if (isBlack(node)) leftHeight++
        return leftHeight
    }

    override fun iterator(): Iterator<RbNode<T, P>> = TreeIterator(root)

    override fun search(key: T): P? {
        var tmp: RbNode<T, P>? = root
        while (tmp != null) {
            when {
                tmp.key == key -> return tmp.value
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null
    }

    override fun insert(key: T, value: P): Boolean {
        var flag: Boolean = false
        var tmp: RbNode<T, P>? = root
        if (root == null) {
            root = RbNode(key, value, color = Color.BLACK)//
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
        var newNode: RbNode<T, P> = RbNode(key = key, value = value, color = Color.RED)
        newNode.parent = tmp
        if (flag == false) {
            tmp!!.leftChild = newNode ///////
        } else tmp!!.rightChild = newNode///////
        fixUpInsertNode(newNode)
        return true
    }//ok

    private fun getNodeByMinKey(node: RbNode<T, P>): RbNode<T, P> {
        var tmp = node
        while (tmp.leftChild != null) tmp = tmp.leftChild!!
        return tmp
    }//ok

    private fun fixUpInsertNode(x: RbNode<T, P>) {
        var node = x
        var tmp: RbNode<T, P>?
        while (node.parent != null && node.parent!!.color == Color.RED) {
            if (node.parent?.parent?.leftChild == node.parent) {//?
                tmp = node.parent?.parent?.rightChild//null?
                if (tmp != null && tmp.color == Color.RED) {
                    node.parent?.color = Color.BLACK //black
                    tmp.color = Color.BLACK
                    node.parent?.parent?.color = Color.RED
                    node = node.parent!!.parent!!
                } else {
                    if (node.parent?.rightChild == node) {
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
                    if (node.parent?.leftChild == node) {
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

    private fun leftRotate(node: RbNode<T, P>) {
        if (node.rightChild == null) throw UnsupportedOperationException("Bad in fun left rotate right child ==null!")
        var copyNode = node.rightChild
        node.rightChild = copyNode?.leftChild

        if (copyNode!!.leftChild != null) copyNode.leftChild!!.parent = node
        copyNode.parent = node.parent

        if (node.parent == null) {
            this.root = copyNode
        } else {
            when {
                node.parent?.leftChild == node -> node.parent?.leftChild = copyNode
                node.parent?.rightChild == node -> node.parent?.rightChild = copyNode
                else -> throw UnsupportedOperationException("Bad in rotate left")
            }
        }
        copyNode.leftChild = node
        node.parent = copyNode
    }

    private fun rightRotate(node: RbNode<T, P>) {
        if (node.leftChild == null) throw UnsupportedOperationException("Bad in fun left rotate left child ==null!")
        var copyNode = node.leftChild
        node.leftChild = copyNode?.rightChild

        if (copyNode!!.rightChild != null) copyNode.rightChild!!.parent = node
        copyNode.parent = node.parent

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

    fun getValueByMinKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: RbNode<T, P>? = root
        while (tmp?.leftChild != null) tmp = tmp.leftChild
        return tmp?.value
    }//ok

    fun getValueByMaxKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: RbNode<T, P>? = root
        while (tmp?.rightChild != null) tmp = tmp.rightChild
        return tmp?.value
    }


    override fun delete(key: T): Boolean {
        var tmp: RbNode<T, P>?
        var removedNode: RbNode<T, P>? = root
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
            return false
        }
        if (removedNode == root && removedNode.leftChild == null && removedNode.rightChild == null) {
            root = null
            return true
        }
        removeNode(removedNode)
        return true
    }

    private fun removeNode(x: RbNode<T, P>) {
        var remNode = x
        var right = true// position of remNode
        var nil: RbNode<T, P>
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
        if (remNode.parent == null) {
            if (right) root = remNode.rightChild
            else root = remNode.leftChild
        } else when {
            remNode.parent!!.leftChild == remNode -> if (right) remNode.parent!!.leftChild = remNode.rightChild else remNode.parent!!.leftChild = remNode.leftChild
            remNode.parent!!.rightChild == remNode -> if (right) remNode.parent!!.rightChild = remNode.rightChild else remNode.parent!!.rightChild = remNode.leftChild
        }
        if (isBlack(remNode)) {
            when {
                remNode.leftChild != null -> RbRemoveFixUp(remNode.leftChild)
                remNode.rightChild != null -> RbRemoveFixUp(remNode.rightChild)
                else -> {
                    nil = RbNode(key = remNode.key, value = remNode.value, color = Color.BLACK)
                    nil.parent = remNode.parent
                    if (remNode.parent!!.leftChild == null) remNode.parent!!.leftChild = nil
                    else remNode.parent!!.rightChild = nil
                    RbRemoveFixUp(nil)
                    if (nil.parent!!.leftChild == nil) nil.parent!!.leftChild = null
                    else nil.parent!!.rightChild = null
                }
            }

        }
        return
    }

    private fun RbRemoveFixUp(x: RbNode<T, P>?) {
        var tmp: RbNode<T, P>?
        var node = x
        while (node !== root && isBlack(node)) {
            if (node === node!!.parent!!.leftChild) {
                tmp = node!!.parent!!.rightChild
                if (!isBlack(tmp)) {
                    tmp!!.color = Color.BLACK
                    node.parent!!.color = Color.RED
                    leftRotate(node.parent!!)
                    tmp = node.parent!!.rightChild
                }
                if (isBlack(tmp!!.leftChild) && isBlack(tmp.rightChild)) {
                    tmp.color = Color.RED
                    node = node.parent
                } else if (isBlack(tmp.rightChild)) {
                    tmp.leftChild!!.color = Color.BLACK
                    tmp.color = Color.RED
                    rightRotate(tmp)
                    tmp = node.parent!!.rightChild
                } else {
                    tmp.color = node.parent!!.color
                    node.parent!!.color = Color.BLACK
                    tmp.rightChild!!.color = Color.BLACK
                    leftRotate(node.parent!!)
                    node = root
                }
            } else {
                tmp = node!!.parent!!.leftChild
                if (!isBlack(tmp)) {
                    tmp!!.color = Color.BLACK
                    node.parent!!.color = Color.RED
                    rightRotate(node.parent!!)
                    tmp = node.parent!!.leftChild
                }
                if (isBlack(tmp!!.rightChild) && isBlack(tmp.leftChild)) {
                    tmp.color = Color.RED
                    node = node.parent
                } else if (isBlack(tmp.leftChild)) {
                    tmp.rightChild!!.color = Color.BLACK
                    tmp.color = Color.RED
                    leftRotate(tmp)
                    tmp = node.parent!!.leftChild
                } else {
                    tmp.color = node.parent!!.color
                    node.parent!!.color = Color.BLACK
                    tmp.leftChild!!.color = Color.BLACK
                    rightRotate(node.parent!!)
                    node = root
                }
            }
        }
        if (!isBlack(node)) node!!.color = Color.BLACK
    }

    private fun isBlack(node: RbNode<T, P>?): Boolean {
        if (node == null) return true
        if (node.color == Color.BLACK) return true
        return false
    }

}