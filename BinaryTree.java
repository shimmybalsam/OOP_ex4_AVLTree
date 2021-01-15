package oop.ex4.data_structures;

/**
 * an abstract class representing a binary tree.
 */
public abstract class BinaryTree extends Object {

    /**
     * an abstract add function to add a given value to tree.
     * @param newVal the given value to add.
     * @return true if added successfully, false otherwise.
     */
    public abstract boolean add(int newVal);

    /**
     * an abstract delete function to remove a given value from tree.
     * @param toDelete the value to be deleted.
     * @return true if deleted successfully, false otherwise.
     */
    public abstract boolean delete(int toDelete);

    /**
     * and abstract contains function checking if a value is in the tree.
     * @param checkVal the value to be searched for.
     * @return the depth of the value if found, -1 if not found.
     */
    public abstract int contains(int checkVal);

    /**
     * an abstract function
     * @return the amount of nodes in the tree.
     */
    public abstract int size();
}
