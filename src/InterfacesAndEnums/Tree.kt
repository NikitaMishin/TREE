package InterfacesAndEnums

/**
 * Created by nikita on 27.02.17.
 */


/**

 */

interface Tree<in T : Comparable<T>, P> {//In
    fun insert(key: T, value: P): Boolean
    fun search(key: T): P?//
    fun delete(key: T): Boolean
}



