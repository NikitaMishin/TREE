package BST

import InterfacesAndEnums.Tree
import RbTree.TreeIterator
/**
 * Created by nikita on 27.02.17.
 */


/**
 * BST.BST for key Type T and value Type P
 *
 * fun "searchBykey" search node with input key and return value of this node else return null
 * fun "insert" insert input value by  input key. if key isn't unique then fun return message "Already have value by key = $key"
 * fun "delete" remove node by input key. fun print "Nothing to remove by key = $key!"   if nothing to remove by this key
 */

open class BST<T : Comparable<T>, P>(internal var root: BstNode<T, P>? = null) : Tree<T, P>, Iterable<BstNode<T, P>> {

    override fun iterator(): Iterator<BstNode<T, P>> = TreeIterator(root)

    override fun search(key: T): P? {
        var tmp: BstNode<T, P>? = root
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

    override fun insert(key: T, value: P): Boolean {
        var flag: Boolean = false
        var tmp: BstNode<T, P>? = root
        if (root == null) {
            root = BstNode(key = key, value = value)
            return true
        }
        link@ while (tmp != null) {
            when {
                tmp.key == key -> return false
                key > tmp.key -> if (tmp.rightChild == null) {
                    flag = true; break@link
                } else tmp = tmp.rightChild
                key < tmp.key -> if (tmp.leftChild == null) {
                    break@link
                } else tmp = tmp.leftChild
            }
        }
        var newNode: BstNode<T, P> = BstNode(key = key, value = value)
        newNode.parent = tmp
        if (!flag) {
            tmp!!.leftChild = newNode ///////
        } else tmp!!.rightChild = newNode
        return true
    }

    fun getValueByMaxKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: BstNode<T, P>? = root
        while (tmp?.rightChild != null) tmp = tmp.rightChild
        return tmp?.value
    }

    fun getValueByMinKey(): P? {
        if (root == null) {
            return null
        }
        var tmp: BstNode<T, P>? = root
        while (tmp?.leftChild != null) tmp = tmp.leftChild
        return tmp?.value
    }

    private fun getNodeByMinKey(root: BstNode<T, P>): BstNode<T, P> {//check on null??
//chekc
        var tmp = root
        while (tmp.leftChild != null) tmp = tmp.leftChild!!//
        return tmp
    }

    private fun getNodeBySearchByKey(key: T): BstNode<T, P>? {
        var tmp: BstNode<T, P>? = root
        while (tmp != null) {
            when {
                tmp.key == key -> return tmp
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null

    }

    override fun delete(key: T): Boolean {
        var removedNode: BstNode<T, P>? = getNodeBySearchByKey(key)

        if (removedNode == null) {
            println("Nothing to remove by key = $key!")
            return false
        }
        if (removedNode.leftChild != null && removedNode.rightChild != null) {
            var copyNode = getNodeByMinKey(removedNode.rightChild!!)

            if (copyNode.rightChild != null) copyNode.rightChild!!.parent = copyNode.parent
            when {
                copyNode.parent!!.rightChild == copyNode -> copyNode.parent!!.rightChild = copyNode.rightChild
                copyNode.parent!!.leftChild == copyNode -> copyNode.parent!!.leftChild = copyNode.rightChild
            }

            copyNode.parent = removedNode.parent
            copyNode.leftChild = removedNode.leftChild
            if (removedNode.leftChild != null) removedNode.leftChild!!.parent = copyNode
            copyNode.rightChild = removedNode.rightChild
            if (removedNode.rightChild != null) removedNode.rightChild!!.parent = copyNode //weak point

            when {
                removedNode.parent == null -> this.root = copyNode
                removedNode.parent!!.rightChild == removedNode -> removedNode.parent!!.rightChild = copyNode
                removedNode.parent!!.leftChild == removedNode -> removedNode.parent!!.leftChild = copyNode
            }

        } else {
            if (removedNode.leftChild != null) {
                removedNode.leftChild?.parent = removedNode.parent
                when {//how exactly work when
                    removedNode.parent == null -> {
                        root = removedNode.leftChild
                        return true
                    }

                    removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = removedNode.leftChild
                    removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = removedNode.leftChild
                }
            }
            if (removedNode.rightChild != null) {
                removedNode.rightChild?.parent = removedNode.parent
                when {
                    removedNode.parent == null -> {
                        root = removedNode.rightChild
                        return true
                    }
                    removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = removedNode.rightChild
                    removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = removedNode.rightChild
                }
            }
            if (removedNode.rightChild == null && removedNode.leftChild == null) {
                when {
                    removedNode.parent == null -> {
                        root = null
                        return true
                    }
                    removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = null
                    removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = null
                }
            }
        }
        return true
    }
}
