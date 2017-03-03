/**
 * Created by nikita on 27.02.17.
 */

open class  BST <T:Comparable<T>,P>( private var root: Node<T,P>?) :Tree<T,P> { //need to test //tomorrow
    override fun searchByKey(key: T): P? {
        var tmp :Node<T,P>?   = root
        while(tmp!=null) {
            when{
                tmp.key == key -> return tmp.value
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        System.out.print("Can't find value by key =  $key.")
        return null
    }// override in fture vlass
    override fun insertNode(key:T,value: P) {
        var flag: Boolean = false
        var tmp: Node<T, P>? = root
        if (root == null) {
            root = Node(key = key, value = value)//
            return
        }
        link@ while (tmp != null) {
            when {
                tmp.key == key -> {
                    System.out.print("Already have value by key = $key");return
                }
                key > tmp.key -> if (tmp.rightChild == null) {
                    flag = true; break@link
                } else tmp = tmp.rightChild
                key < tmp.key -> if (tmp.leftChild == null) {
                    break@link
                } else tmp = tmp.leftChild
            }
        }
        var newNode: Node<T, P> = Node(key = key, value = value)
        newNode.parent = tmp
        //newNode.setParent(tmp)
        if (flag == false) {
            tmp!!.leftChild = newNode ///////
        } else tmp!!.rightChild = newNode
    }
    override fun getValueByMaxKey(key: T): P? {
        if (key == null) throw  UnsupportedOperationException("Wrong input")
        if (root == null){
            System.out.print("Tree is empty!")
            return null
        }
        var tmp: Node<T,P>? = root
        while(tmp?.rightChild != null) tmp = tmp?.rightChild
        return  tmp?.value
    }
    override fun getValueByMinKey(key: T): P? {
        if (key == null) throw  UnsupportedOperationException("Wrong input")
        if (root == null){
            System.out.print("Tree is empty!")
            return null
        }
        var tmp: Node<T,P>? = root
        while(tmp?.leftChild != null) tmp = tmp?.leftChild
        return  tmp?.value
    }///
    private  fun getNodeByMinKey(root:Node<T,P>):Node<T,P>{//check on null??
//chekc
        var tmp = root
        while(tmp.leftChild!=null) tmp = tmp.leftChild!!//
        return tmp
    }
    private  fun getNodeBySearchByKey (key: T):Node<T,P>?{
        var tmp :Node<T,P>?   = root
        while(tmp!=null) {
            when{
                tmp.key == key -> return tmp
                key > tmp.key -> tmp = tmp.rightChild
                key < tmp.key -> tmp = tmp.leftChild
            }
        }
        return null

    }
    override fun removeNodebyKey(key: T) {
        var removedNode:Node<T,P>? = getNodeBySearchByKey (key)

        if (removedNode == null) {
            System.out.print("Nothing to remove by key = $key!")
            return
        }
        if (removedNode.leftChild != null && removedNode.rightChild != null){
            var copyNode = getNodeByMinKey(removedNode!!.rightChild!!)

            if (copyNode.rightChild!=null) copyNode!!.rightChild!!.parent =  copyNode.parent
            when{
                copyNode.parent!!.rightChild == copyNode-> copyNode.parent!!.rightChild =copyNode.rightChild
                copyNode.parent!!.leftChild == copyNode->copyNode.parent!!.leftChild = copyNode.rightChild
                //else->  this.root = copyNode  //parent -null removed is root
            }

            copyNode.parent = removedNode.parent
            copyNode.leftChild = removedNode.leftChild
            removedNode.leftChild!!.parent = copyNode
            copyNode.rightChild = removedNode.rightChild
            removedNode.rightChild!!.parent = copyNode

            when{
                removedNode.parent == null-> this.root = copyNode
                removedNode.parent!!.rightChild == removedNode -> removedNode.parent!!.rightChild = copyNode
                removedNode.parent!!.leftChild == removedNode -> removedNode.parent!!.leftChild = copyNode
            }
            //removeNode.rightChild == null// and other
        }
        else {
            if (removedNode.leftChild != null) {
                removedNode?.leftChild?.parent = removedNode.parent
                when {//how exactly work when
                    removedNode.parent == null -> {
                        root = removedNode.leftChild
                        return
                    }

                    removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = removedNode.leftChild
                    removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = removedNode.leftChild
                }
            }
            if (removedNode.rightChild!= null) {
                removedNode?.rightChild?.parent = removedNode.parent
                when{
                    removedNode.parent == null -> {
                        root = removedNode.rightChild
                        return
                    }
                    removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = removedNode.rightChild
                    removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = removedNode.rightChild
                }
            }
            if (removedNode.rightChild == null&&removedNode.leftChild == null) {
                when{
                        removedNode.parent == null -> {
                            root = null
                            return
                        }
                        removedNode.parent?.leftChild == removedNode -> removedNode.parent?.leftChild = null
                        removedNode.parent?.rightChild == removedNode -> removedNode.parent?.rightChild = null
                }
            }
        }
    }
}
