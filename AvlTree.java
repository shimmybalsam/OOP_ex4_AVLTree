package oop.ex4.data_structures;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class represents the implementation of an AVL tree data structure.
 */
public class AvlTree extends BinaryTree implements Iterable<Integer> {
    int size = 0;
    Node cur, temp_cur, imbalanced, temp;

    /** represents that the AvlTree does not contain a Node with a given data. */
    private static final int NON_EXISTENT = -1;

    /**
     * Default constructor, creates a new, empty AvlTree.
     */
    public AvlTree(){

        this.cur = null;
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree. The new tree contains all the values
     * of the given tree, but not necessarily in the same structure.
     * @param tree The AVL tree to be copied
     */
    public AvlTree(AvlTree tree) throws NoDataException {
        if (tree!= null){
            Iterator<Integer> my_iter = tree.iterator();
            while(my_iter.hasNext()) {
                this.add(my_iter.next());
            }
        }else{
            throw new NoDataException();
        }
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     * @param data an int array containing the values to be added to the tree.
     */
    public AvlTree(int[]data) throws NoDataException {
        this();
        if (data!= null){
            for (int i = 0; i < data.length; i++) {
                this.add(data[i]);
            }
        }else{
            throw new NoDataException();
        }
    }

    @Override
    /**
     * iterator in interface Iterable<Integer>
     * returns an iterator for the Avl Tree. The returned iterator iterates over the tree nodes in an ascending
     * order, and does NOT implement the remove() method.
     */
    public Iterator<Integer> iterator() {
        LinkedList<Integer> l = new LinkedList<Integer>();
        addPerNode(cur, l);
        return l.iterator();
    }

    /**
     * a recursive function which runs down a binary AvlTree and adds each node to a new ascending linked list
     * @param cur the root of the tree, or subtree per round.
     * @param linkedList the list each node will be added to.
     */
    private void addPerNode(Node cur, LinkedList<Integer> linkedList){
        if (cur != null) {
            addPerNode(cur.get_leftSon(), linkedList);
            linkedList.add(cur.get_data());
            addPerNode(cur.get_rightSon(), linkedList);
        }
    }

    /**
     * Add a new node with the given data to the tree.
     * @param newValue the given data to be added.
     * @return true if successfully added, false otherwise (already exists a node with given data).
     */
    public boolean add(int newValue){
        if (this.contains(newValue) != -1) {
            return false;
        }
        if (cur == null){
            cur = new Node(newValue);
        }else{
            checkAddPerSubTree(cur, newValue);
        }
        size++;
        return true;
    }

    /**
     * a helpful recursive function finding where in the tree the new node must be added to. After adding,
     * updates heights, checks balance and rebalances if needed.
     * @param cur the node to be compared at each round if is larger or smaller than the data to be added.
     * @param newValue the data to be added.
     */
    private void checkAddPerSubTree(Node cur, int newValue) {
        if (cur.get_data() > newValue) {
            if (cur.get_leftSon() == null) {
                cur.set_leftSon(new Node(newValue));
                cur.get_leftSon().set_father(cur);
                this.height_update(cur);
                temp_cur = cur.get_father();
                imbalanced = this.balance(temp_cur);
                if (imbalanced != null) {
                    this.rebalance(imbalanced);
                }
            } else {
                this.checkAddPerSubTree(cur.get_leftSon(), newValue);
            }
        } else {
            if (cur.get_rightSon() == null) {
                cur.set_rightSon(new Node(newValue));
                cur.get_rightSon().set_father(cur);
                this.height_update(cur);
                temp_cur = cur.get_father();
                imbalanced = this.balance(temp_cur);
                if (imbalanced != null) {
                    this.rebalance(imbalanced);
                }
            }else {
                this.checkAddPerSubTree(cur.get_rightSon(), newValue);
            }
        }
    }

    /**
     * updates the height of a node and it's ancestors.
     * @param father the node, per round which from there up gets it's height updated (if needed).
     */
    private void height_update(Node father){
        while (father != null) {
            if (father.get_leftSon() != null && father.get_rightSon() != null) {
                father.set_height(Math.max(father.get_leftSon().get_height(),
                        father.get_rightSon().get_height()) + 1);
            } else if (father.get_leftSon() == null) {
                father.set_height(checkHeight(father.get_rightSon()));
            } else {
                father.set_height(checkHeight(father.get_leftSon()));
            }
            father = father.get_father();
        }

    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete){
        if (this.contains(toDelete) == -1){
            return false;
        }
        this.checkDeleteAllSubTrees(cur, toDelete);
        size--;
        return true;
    }

    /**
     * A
     * a helpful recursive function finding where in the tree the new node that must be deleted is. After deletion,
     * updates heights, checks balance and rebalances if needed.
     * @param cur the node to be compared at each round if is larger or smaller than the data to be deleted.
     * @param toDelete the of the data of the node which must be deleted.
     */
    private void checkDeleteAllSubTrees(Node cur, int toDelete){
        if (cur.get_data() == toDelete){
            if (cur.get_leftSon() == null){
                if (cur.get_rightSon() == null){
                    if (cur.get_father() == null) {
                        this.cur = null;
                    }else if (cur.get_father().get_data() > toDelete){
                        cur.get_father().set_leftSon(null);
                        this.height_update(cur);
                        imbalanced = this.balance(cur);
                            if (imbalanced != null){
                                rebalance(imbalanced);
                        }
                    }else {
                        cur.get_father().set_rightSon(null);
                        this.height_update(cur);
                        imbalanced = this.balance(cur);
                        if (imbalanced != null) {
                            rebalance(imbalanced);
                        }
                    }
                }else {
                    temp_cur = cur.get_rightSon();
                    while (temp_cur.get_leftSon() != null) {
                        temp_cur = temp_cur.get_leftSon();
                    }
                    cur.set_data(temp_cur.get_data());
                    if (temp_cur.get_father() != null) {
                        if (temp_cur.get_data() < temp_cur.get_father().get_data()) {
                            temp_cur.get_father().set_leftSon(null);
                            this.height_update(temp_cur.get_father());
                            temp_cur.set_father(null);
                            temp_cur = null;
                        } else {
                            temp_cur.get_father().set_rightSon(null);
                            this.height_update(temp_cur.get_father());
                            temp_cur.set_father(null);
                            temp_cur = null;
                        }
                        imbalanced = this.balance(cur);
                        if (imbalanced != null) {
                            rebalance(imbalanced);
                        }
                    }else{
                        temp_cur = null;
                    }
                }
            }else{
                temp_cur = cur.get_leftSon();
                while (temp_cur.get_rightSon() != null){
                    temp_cur = temp_cur.get_rightSon();
                }
                cur.set_data(temp_cur.get_data());
                if (temp_cur.get_data() < temp_cur.get_father().get_data()){
                    temp_cur.get_father().set_leftSon(null);
                    this.height_update(temp_cur.get_father());
                    temp_cur.set_father(null);
                    temp_cur = null;
                }else{
                    temp_cur.get_father().set_rightSon(null);
                    this.height_update(temp_cur.get_father());
                    temp_cur.set_father(null);
                    temp_cur = null;
                }
                imbalanced = this.balance(cur);
                if (imbalanced != null) {
                    rebalance(imbalanced);
                }
            }
        }else if(cur.get_data() > toDelete){
            this.checkDeleteAllSubTrees(cur.get_leftSon(), toDelete);
        }else{
            this.checkDeleteAllSubTrees(cur.get_rightSon(), toDelete);
        }
    }

    /**
     * checks if an AvlTree contains a node with a given data.
     * @param searchVal the given data to search for.
     * @return -1 if the node with said data does not exist, the depth of the node with the given data otherwise.
     */
    public int contains(int searchVal){

        return help_find(cur, searchVal, 0);
    }

    /**
     * a helpful recursive function which goes down the tree (adding depth each time) in order to find the node we
     * are looking for.
     * @param cur the current node of comparison.
     * @param searchVal the value we are searching for.
     * @param depth the given depth of cur at each round.
     * @return -1 if the node with said data does not exist, the depth of the node with the given data otherwise.
     */
    private int help_find(Node cur, int searchVal, int depth){
        if (cur == null){
            return NON_EXISTENT;
        }
        if (cur.get_data() == searchVal){
            return depth;
        }
        if (cur.get_data() > searchVal){
            return help_find(cur.get_leftSon(), searchVal, depth + 1);
        }else{
            return help_find(cur.get_rightSon(), searchVal, depth + 1);
        }
    }

    /**
     * @return the number of nodes in the tree.
     */
    public int size(){
        return this.size;
    }

    /**
     * Based on a the general member equation equivelant to the recursive call/ Recurrence relation of
     * T(h) = T(h-1) + T(h-2) + 1.
     * @param h a given height of an AvlTree.
     * @return the minimal number of nodes possible per AvlTree of given height h.
     */
    public static int findMinNodes(int h){
        return (int)(((Math.sqrt(5) + 2) / Math.sqrt((5)) * Math.pow((1 + Math.sqrt(5)) / 2, h) +
                (((Math.sqrt(5) - 2) / Math.sqrt(5)) * Math.pow((1 - Math.sqrt(5)) / 2, h) - 1)));
    }

    /**
     * @param h a given height of an AvlTree.
     * @return the maximum number of nodes possible per AvlTree of given height h.
     */
    public static int findMaxNodes(int h){

        return (int)(Math.pow(2,(h+1))) - 1;
    }

    /**
     * initiates a right rotation.
     * @param grandfather the node from which we start the rotation.
     */
    private void rotateRight(Node grandfather){
        temp = grandfather.get_leftSon();
        grandfather.set_leftSon(temp.get_rightSon());
        if (temp.get_rightSon() != null) {
            temp.get_rightSon().set_father(grandfather);
        }
        temp.set_rightSon(grandfather);
        temp.set_father(grandfather.get_father());
        grandfather.set_father(temp);
        if (temp.get_father() != null){
            if (temp.get_data() > temp.get_father().get_data()) {
                temp.get_father().set_rightSon(temp);
            }else{
                temp.get_father().set_leftSon(temp);
            }
        }
        if (cur == grandfather){
            cur = temp;
        }
        this.height_update(grandfather);
        this.height_update(temp);
    }

    /**
     * initiates a left rotation
     * @param grandfather the node from which we start the rotation.
     */
    private void rotateLeft(Node grandfather){
        temp = grandfather.get_rightSon();
        grandfather.set_rightSon(temp.get_leftSon());
        if (temp.get_leftSon() != null) {
            temp.get_leftSon().set_father(grandfather);
        }
        temp.set_leftSon(grandfather);
        temp.set_father(grandfather.get_father());
        grandfather.set_father(temp);
        if (temp.get_father() != null) {
            if (temp.get_data() > temp.get_father().get_data()) {
                temp.get_father().set_rightSon(temp);
            }else{
                temp.get_father().set_leftSon(temp);
            }
        }
        if (cur == grandfather){
            cur = temp;
        }
        this.height_update(grandfather);
        this.height_update(temp);
    }

    /**
     * initiates a right-left rotation.
     * @param grandfather the node from which we start the rotation.
     */
    private void rotateRightLeft(Node grandfather){
        rotateRight(grandfather.get_rightSon());
        rotateLeft(grandfather);

    }

    /**
     * initiates a left-right rotation.
     * @param grandfather the node from which we start the rotation.
     */
    private void rotateLeftRight(Node grandfather){
        rotateLeft(grandfather.get_leftSon());
        rotateRight(grandfather);
    }

    /**
     * checks if the a node, or it's anscestors or imbalanced.
     * @param grandfather the node to be checked.
     * @return the node causing the imbalance.
     */
    private Node balance(Node grandfather){
        if (grandfather != null) {
            if (grandfather.get_leftSon() != null && grandfather.get_rightSon() != null) {
                if (Math.abs(checkHeight(grandfather.get_leftSon()) - checkHeight(grandfather.get_rightSon())) > 1) {
                    return grandfather;
                } else if (grandfather.get_father() == null) {
                    return null;
                } else {
                    return this.balance(grandfather.get_father());
                }
            } else if (grandfather.get_leftSon() != null && checkHeight(grandfather.get_leftSon()) > 1) {
                return grandfather;
            } else if (grandfather.get_rightSon() != null && checkHeight(grandfather.get_rightSon()) > 1) {
                return grandfather;
            } else {
                return this.balance(grandfather.get_father());
            }
        }else{
            return null;
        }
    }

    /**
     * rebalances an imbalanced node.
     * @param grandfather the imbalanced node.
     */
    private void rebalance(Node grandfather){
        if (checkHeight(grandfather.get_leftSon()) - checkHeight(grandfather.get_rightSon()) > 1){
            if(checkHeight(grandfather.get_leftSon().get_leftSon()) >
                    checkHeight(grandfather.get_leftSon().get_rightSon())){
                rotateRight(grandfather);
            }else{
                rotateLeftRight(grandfather);
            }
        }else{
            if (checkHeight(grandfather.get_rightSon().get_rightSon()) >
                    checkHeight(grandfather.get_rightSon().get_leftSon())){
                rotateLeft(grandfather);
            }else{
                rotateRightLeft(grandfather);
            }
        }
    }

    /**
     * A helpful function which checks if a Node is null, in order to maintain a height check per node, without
     * colliding with a null exception when checking height of each son.
     * @param node a single node in an AvlTree.
     * @return 0 if node is null, the height of the node + 1 otherwise.
     */
    private int checkHeight(Node node){
        if (node == null){
            return 0;
        }else{
         return node.get_height()+1;
        }
    }
}

