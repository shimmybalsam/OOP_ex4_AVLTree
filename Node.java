package oop.ex4.data_structures;

/**
 * This class represents a single Node within an AVL tree.
 */
public class Node {
    private Node rightSon, leftSon, father;
    private int height = 0, data;

    /**
     * cunstructs a new node with given values.
     * @param father the node's father.
     * @param rightSon the node's right son.
     * @param leftSon the node's left son
     * @param height the node's height
     * @param data the node's data
     */
    public Node(Node father, Node rightSon, Node leftSon, int height, int data){
        this.father = father;
        this.rightSon = rightSon;
        this.leftSon = leftSon;
        this.height = height;
        this.data = data;
    }

    /**
     * creates a new node with only a given data.
     * @param data the data for the new node.
     */
    public Node(int data){

        this.data = data;
    }

    /**
     * @return the father of the node.
     */
    public Node get_father(){

        return this.father;
    }

    /**
     * sets a new father to the node.
     * @param new_father the new father.
     */
    public void set_father(Node new_father){

        this.father = new_father;
    }

    /**
     * @return the node's left son.
     */
    public Node get_leftSon(){
        return this.leftSon;
    }

    /**
     * sets a new left son for the node
     * @param new_leftSon the new left son.
     */
    public void set_leftSon(Node new_leftSon){
        this.leftSon = new_leftSon;
    }

    /**
     * @return node;s right son.
     */
    public Node get_rightSon(){
        return this.rightSon;
    }

    /**
     * sets a new right son to node.
     * @param new_rightSon the new right son.
     */
    public void set_rightSon(Node new_rightSon){
        this.rightSon = new_rightSon;
    }

    /**
     * @return node's height.
     */
    public int get_height(){

        return this.height;
    }

    /**
     * sets a new height to node.
     * @param new_height the new height.
     */
    public void set_height(int new_height){

        this.height = new_height;
    }

    /**
     * @return node's data
     */
    public int get_data(){
        return this.data;
    }

    /**
     * sets a new data to the node
     * @param new_data the new data.
     */
    public void set_data(int new_data){
        this.data = new_data;
    }
}
