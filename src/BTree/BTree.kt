package BTree

import InterfacesAndEnums.Tree

/**
 * Created by nikita on 20.03.17.
 */


class BTree<K : Comparable<K>, V>(private var root: BNode<K, V>? = null, val t: Int = 5) : Iterable<BNode<K, V>>, Tree<K, V> {
    override fun iterator(): Iterator<BNode<K, V>> = BtreeIterator(root)

    public override fun search(key: K): V? {
        var pair = search(key = key, node = root)
        if (pair == null) return null
        return pair.second.keys[pair.first].second
    }
    fun search(key: K, node: BNode<K, V>? = root): Pair<Int, BNode<K, V>>?
    {
        if (node == null) return null
        var size = node.keys.size
        var i = 0
        while (i < size && key > node.keys[i].first) i++
        if (i < size && key == node.keys[i].first) return Pair(i, node)
        if (node.isLeaf) return null
        else return search(key, node.Nodes[i])
    }
    override public fun insert(key: K, value: V): Boolean {
        if (root == null) {
            this.root = BNode()
            root!!.keys.add(Pair(key, value))
            return true
        }
        var r = this.root
        if (search(key) != null) return false
        if (r!!.keys.size == 2 * t - 1) {
            var s: BNode<K, V> = BNode()
            this.root = s
            s.isLeaf = false
            s.Nodes.add(element = r)
            splitChild(s, 0)
            insertNonFull(s, key, value)
        } else insertNonFull(r, key, value)
        return true
    }

    private fun splitChild(node: BNode<K, V>, index: Int) {
        var parent = node
        var newChild = BNode<K, V>()
        var child = parent.Nodes[index]
        newChild.isLeaf = child.isLeaf
        for (j in 0..t - 2)
            newChild.keys.add(element = child.keys.removeAt(t))
        if (!child.isLeaf)
            for (j in 0..t - 1) newChild.Nodes.add(element = child.Nodes.removeAt(t))
        parent.Nodes.add(index + 1, newChild)
        parent.keys.add(index=index, element = child.keys.removeAt(t-1))
    }

    private fun insertNonFull(node: BNode<K, V>, key: K, value: V) {
        var i = 0
        var size = node.keys.size
        if (node.isLeaf) {
            while (i < size && key > node.keys[i].first) i++
            node.keys.add(i, Pair(key, value))
        } else {
            while (i < size && key > node.keys[i].first) i++
            if (node.Nodes[i].keys.size == 2 * t - 1) {
                splitChild(node, i)
                if (key > node.keys[i].first) i++
            }
            insertNonFull(node.Nodes[i], key, value)
        }
    }

    private fun PullKeyFromNeighbor(child: BNode<K, V>, parent: BNode<K, V>): Int // -1 -> from left, 0->no success 1-> from right //check
    {
        var indexOfChild = parent.Nodes.indexOf(child)
        var size = parent.Nodes.size
        if (indexOfChild > 0 && parent.Nodes[indexOfChild - 1].keys.size > t - 1) {
            var neighbour = parent.Nodes[indexOfChild - 1]
            child.keys.add(0, parent.keys[indexOfChild - 1])
            parent.keys[indexOfChild - 1] = neighbour.keys[neighbour.keys.lastIndex]
            neighbour.keys.removeAt(neighbour.keys.lastIndex)
            if (!child.isLeaf) {
                child.Nodes.add(0, neighbour.Nodes[neighbour.Nodes.lastIndex])
                neighbour.Nodes.removeAt(neighbour.Nodes.lastIndex)
            }
            return -1
        } else if (indexOfChild < size - 1 && parent.Nodes[indexOfChild + 1].keys.size > t - 1) {
            var neighbour = parent.Nodes[indexOfChild + 1]
            child.keys.add(parent.keys[indexOfChild])
            parent.keys[indexOfChild] = neighbour.keys[0]
            neighbour.keys.removeAt(0)
            if (!child.isLeaf) {
                child.Nodes.add(child.Nodes.lastIndex + 1, neighbour.Nodes[0])
                neighbour.Nodes.removeAt(0)
            }
            return 1
        }
        return 0
    }

    private fun mergeNodes(left: BNode<K, V>, right: BNode<K, V>, parent: BNode<K, V>): BNode<K, V> {// add case with root all added to the left
        left.keys.add(element = parent.keys[parent.Nodes.indexOf(right) - 1])//index of right
        parent.keys.removeAt(parent.Nodes.indexOf(right) - 1)//??
        left.keys.addAll(right.keys)
        left.Nodes.addAll(right.Nodes)
        parent.Nodes.remove(right)
        return left
    }

    override fun delete(key: K): Boolean {
        if (search(key) == null) return false
        var flag = delete(key = key, node = root)
        if (!root!!.isLeaf && root!!.keys.size == 0) root = root!!.Nodes[0]
        return flag
    }

    private fun delete(key: K, node: BNode<K, V>? = root): Boolean {
        if (node == null) return false
        var size = node.keys.size
        var i = 0//binSearch(key = key, array = node.keys)
        while (i < size && key > node.keys[i].first) i++

        when {
            i < node.keys.size && key == node.keys[i].first -> {
                if (node.isLeaf) {
                    node.keys.removeAt(i)
                    return true
                }
                var deletion = Pair(node.keys[i].first, node.keys[i].second)//node.keys[i]

                if (node.Nodes[i].keys.size > t - 1) {//t-1 or t
                    var tmp = node.Nodes[i]
                    while (!tmp.isLeaf) tmp = tmp.Nodes[tmp.Nodes.lastIndex]
                    node.keys[i] = tmp.keys[tmp.keys.lastIndex]//
                    tmp.keys[tmp.keys.lastIndex] = deletion//maybe create sth KEY AWRNING
                    return delete(key = key, node = node.Nodes[i])
                } else if (node.Nodes[i + 1].keys.size > t - 1) {
                    var tmp = node.Nodes[i + 1]
                    while (!tmp.isLeaf) tmp = tmp.Nodes[0]
                    node.keys[i] = tmp.keys[0]//??//////
                    tmp.keys[0] = deletion////??? WARNING
                    return delete(key = key, node = node.Nodes[i + 1])
                } else return delete(node = mergeNodes(left = node.Nodes[i], right = node.Nodes[i + 1], parent = node), key = key)
            }
            else -> {
                if (node.Nodes[i].keys.size == t - 1) {
                    var flag = PullKeyFromNeighbor(child = node.Nodes[i], parent = node)
                    if (flag == 0) when {
                        i == 0 -> return delete(key = key, node = mergeNodes(left = node.Nodes[i], right = node.Nodes[i + 1], parent = node))
                        else -> return delete(key = key, node = mergeNodes(left = node.Nodes[i - 1], right = node.Nodes[i], parent = node))
                    }
                    else delete(key = key, node = node.Nodes[i])
                } else return delete(node = node.Nodes[i], key = key)
            }
        }
        return true
    }

    internal fun binSearch(key: K, array: MutableList <Pair<K, V>>): Int {
        if (array.size == 0) throw UnsupportedOperationException("Empty MutableList")
        var middleIndex: Int
        var firstIndex = -1
        var lastIndex = array.size - 1
        while (firstIndex != lastIndex) {
            middleIndex = (firstIndex + lastIndex) / 2
            if (key > array[middleIndex].first) firstIndex = middleIndex
            else if (key < array[middleIndex].first) lastIndex = middleIndex
            else {
                firstIndex = middleIndex
                break
            }
        }
        when {
            firstIndex>0 ->return firstIndex-1
            else -> return firstIndex
        }

    }


}
