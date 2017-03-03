import java.awt.Color

/**
 * Created by nikita on 27.02.17.
 */

/// true --- color red
class RbTree <T:Comparable<T>,P>(private  var root: Node<T,P>? = null) :Tree<T,P> {
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
    }

    override fun insertNode(key:T,value: P) {
        var flag: Boolean = false
        var tmp: Node<T, P>? = root
        if (root == null) {
            root = Node(key, value, color = false)//
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
                    break@link
                } else tmp = tmp.leftChild
            }
        }
        var newNode: Node<T, P> = Node(key = key, value = value,color = true)
        newNode.parent = tmp
        //newNode.setParent(tmp)
        if (flag == false) {
            tmp!!.leftChild = newNode ///////
        } else tmp!!.rightChild = newNode///////

        fixUpInsertNode(newNode)
        root!!.color = false
    }

    private  fun fixUpInsertNode(x:Node<T,P>){
        var node = x
        var tmp: Node<T,P>? = null
        while (node.parent != null && node.parent!!.color == true){
            if (node.parent?.parent?.leftChild == node.parent){//?
                tmp = node.parent?.parent?.rightChild//null?

                if (tmp != null && tmp.color == true){
                    //tmp== null ->black
                    node.parent?.color = false //black
                    tmp.color = false
                    node.parent?.parent?.color = true
                    node = node.parent!!.parent!!
                }
                else if(node.parent?.rightChild == node){
                    node = node.parent!!
                    leftRotate(node)
                }
                else {
                    node?.parent?.color = false
                    node?.parent?.parent?.color = true
                    //rightRotate((node!!.parent!!.parent!!))//??
                    rightRotate((node.parent!!.parent!!))//??
                  }
            }
            else{
                tmp = node?.parent?.parent?.leftChild//null?
                if (tmp!=null && tmp.color == true){
                    node.parent?.color=false
                    tmp.color = false
                    node.parent?.parent?.color = true
                    node = node.parent!!.parent!!
                }
                else if(node.parent?.leftChild == node){
                    node = node.parent!!
                    rightRotate(node)
                }
                else {
                    node.parent?.color = false
                    node.parent?.parent?.color = true
                    leftRotate(node.parent!!.parent!!)////??
                }
            }
        }
        root!!.color == false
    }


    private fun leftRotate(node: Node<T, P>){
        //assert
        if (node.rightChild==null) throw UnsupportedOperationException("Bad in fun left rotate right child ==null!")
        var copyNode = node.rightChild
        node.rightChild  = copyNode?.leftChild

        if (copyNode!!.leftChild!=null) copyNode.leftChild = node
        copyNode.parent = node.parent

        if (node.parent == null) this.root  = copyNode
        else when {
            node.parent?.leftChild == node -> node.parent?.leftChild = copyNode
            node.parent?.rightChild == node -> node.parent?.rightChild = copyNode
            else-> throw UnsupportedOperationException("Bad in rotate left")
        }
        ///about copyNode?.leftchild.parent??
        copyNode.leftChild = node
        node.parent  = copyNode
    }
    private fun rightRotate(node :Node<T,P>){
        //assert
        if (node.leftChild==null) throw UnsupportedOperationException("Bad in fun left rotate left child ==null!")
        var copyNode = node.leftChild
        node.leftChild  = copyNode?.rightChild

        if (copyNode!!.rightChild!=null) copyNode.rightChild = node
        copyNode.parent = node.parent//>>>??

        if (node.parent == null) this.root  = copyNode
        else when {
            node.parent?.leftChild == node -> node.parent?.leftChild = copyNode
            node.parent?.rightChild == node -> node.parent?.rightChild = copyNode
            else-> throw UnsupportedOperationException("Bad in rotate left")
        }
        copyNode.rightChild = node
        node.parent  = copyNode
    }


    fun printTree() = printTree(this.root, 0)
    public   fun printTree(node:Node <T,P>?, level:Int){//need to override in black tree
        if(node != null) {
            printTree(node.rightChild, level +1)
            for (i in 0..level+1 ) System.out.print("   ")
            if (node.color ==true)System.out.println(30.toChar() + node.value.toString())
            else {System.out.println(node.value)}
            printTree(node.leftChild,level + 1)
        }
    }




    override fun removeNodebyKey(key:T) {



    }

    override fun getValueByMinKey(key: T): P? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getValueByMaxKey(key: T): P? {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}