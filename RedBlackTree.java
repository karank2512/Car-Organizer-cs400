// --== CS400 Fall 2023 File Header Information ==--
// Name: Karan Kapur
// Email: kkapur5@wisc.edu
// Group: B32
// TA: Casey Ford
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>{	

    protected static class RBTNode<T> extends Node<T> {
	public int blackHeight = 0;
	public RBTNode(T data) { super(data); }
	public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
	public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
	public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
    }

    /**
     * This method enforces the properties of a red black tree such as recoloring and
     * rotating as needed when a new node is inserted in the tree.
     *
     * @param newNode - the new node inserted to the tree
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
	RBTNode<T> parent, grandparent, aunt;
	//Case 1: The new node is the root which means that it's parent node is null.
	//In this case, we color the new node black and we're done.
	if (newNode.getUp() == null) {
	    newNode.blackHeight = 1;
	    return;
	}

	//Case 2: The new node has a black parent, there is no violation in this case.
	if (newNode.getUp().blackHeight == 1) {
	    return;
	}

	//If execution reaches here, the new node has a red parent
	while (newNode.getUp()!= null && newNode.getUp().blackHeight == 0) {

	    //defining parent, grandparent, and aunt nodes
	    parent = newNode.getUp();
	    grandparent = parent.getUp();
	    aunt = (parent == grandparent.getDownLeft()) ? grandparent.getDownRight() : grandparent.getDownLeft();

	    //Case 3: The aunt is red, so we recolor
	    if (aunt != null && aunt.blackHeight == 0) {
		parent.blackHeight = 1;
		aunt.blackHeight = 1;
		grandparent.blackHeight = 0;
		enforceRBTreePropertiesAfterInsert(grandparent);
	    }

	    //Case 4: The aunt is black (or null), so we rotate and recolor
	    else {
		if (parent.isRightChild()) {
		    if (parent.getDownLeft() == newNode) {
			// Right rotation at parent, followed by left rotation at grandparent.
			rotate(newNode, parent);
			newNode = parent;
			parent = newNode.getUp();
		    }
		    // Left rotation at grandparent.
		    rotate(parent, grandparent);
		    // Swap colors of parent and grandparent.
		    int tempColor = parent.blackHeight;
		    parent.blackHeight = grandparent.blackHeight;
		    grandparent.blackHeight = tempColor;
                }
		else {
		    if (newNode == parent.getDownRight()) {
			// Left rotation at parent, followed by right rotation at grandparent.
			rotate(newNode, parent);
			newNode = parent;
			parent = newNode.getUp();
		    }
		    // Right rotation at grandparent.
		    rotate(parent, grandparent);
		    // Swap colors of parent and grandparent.
		    int tempColor = parent.blackHeight;
		    parent.blackHeight = grandparent.blackHeight;
		    grandparent.blackHeight = tempColor;
		}
		// Continue checking recursively at the grandparent.
		enforceRBTreePropertiesAfterInsert(grandparent);
            }
        }
    }


    /**
     * This method is used to insert a new node  in the RBT and makes sure the tree does not hold any null references
     * @param data the data value to be inserted into the Red-Black Tree.
     * @return true if the value was successfully inserted.
     * @throws NullPointerException if the provided data argument is null.	   
     */
    
    @Override
    public boolean insert(T data) throws NullPointerException{
	if (data == null){
	    throw new NullPointerException("Cannot insert data value null into the tree.");
	}
	RBTNode<T> newNode = new RBTNode<>(data);
	//newNode.blackHeight = 0;
	//if(insertHelper(newNode)){
	boolean bool = insertHelper(newNode);
	enforceRBTreePropertiesAfterInsert(newNode);
	if(newNode == root){
	    newNode.blackHeight = 1;
	    //return true;
	}
	return bool;
    }
    
    /**
     * This method tests when a new node is added to a Red Black Tree as the root and if it is black in color. 
     * This test verifies that the RBT structure and properties are maintained after the insertion.
     * Test passes after all RBT conditions are met
     */
    
    @Test
    public void testCase1() {
	RedBlackTree<Integer> tree = new RedBlackTree<>();
	tree.insert(70);
        
        // Assertions to check the structure and properties of the resulting tree
	// Assertions.assertEquals(tree.root, 70);
	Assertions.assertEquals(1, ((RBTNode<Integer>)tree.root).blackHeight);
	Assertions.assertEquals("[ 70 ]", tree.toLevelOrderString());
    }

    /**
     * This method tests when a new node is added to a Red Black Tree when the parent node is black. 
     * This test verifies that the RBT structure and properties are maintained after the insertion.
     * Test passes after all RBT conditions are met
     */
    
    @Test
    public void testCase2() {
    	RedBlackTree<Integer> tree = new RedBlackTree<>();
	tree.insert(10);
	tree.insert(5);
	tree.insert(15); // new node with Black Parent
	
	// Assertions to check the structure and properties of the resulting tree
       	// Assertions.assertEquals(tree.root, 10);
	Assertions.assertEquals(1, ((RBTNode<Integer>)tree.root).blackHeight);
       	Assertions.assertEquals(0, ((RBTNode<Integer>)tree.root).getDownLeft().blackHeight);
	Assertions.assertEquals(0, ((RBTNode<Integer>)tree.root).getDownRight().blackHeight);
	Assertions.assertEquals("[ 10, 5, 15 ]", tree.toLevelOrderString());
    }

    /**
     * This method tests the behaviour of a Red Black Tree when a new node added has parent and uncle nodes that are red.
     * The test aims to verify that the RBT properties are maintained correctly after each insertion.
     * Test passes after all RBT conditions are met
     */
    @Test
    public void testCase3() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.insert(100);
	tree.insert(50);
	tree.insert(150);
	tree.insert(30); // new node with red parent and red uncle

	// Assertions to check the structure and properties of the resulting tree
	// Assertions.assertEquals(tree.root, 100);
	Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
	Assertions.assertEquals(1,(((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
	Assertions.assertEquals(1,(((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);
	Assertions.assertEquals(0,(((RBTNode<Integer>) tree.root).getDownLeft().getDownLeft()).blackHeight);
        Assertions.assertEquals("[ 100, 50, 150, 30 ]", tree.toLevelOrderString());
    }

    /**
     * This method tests the behavior of a Red-Black Tree (RBT) when a new node added has a red parent and a black (or null) uncle.
     * The test aims to verify that the RBT properties are maintained correctly after each insertion.
     * Test passes after all RBT conditions are met
     */
    @Test
    public void testCase4(){
	RedBlackTree<Integer> tree = new RedBlackTree<>();
	tree.insert(400);
	tree.insert(200);
	tree.insert(300); // new node with red parent and null uncle
	// Assertions to check the structure and properties of the resulting tree
	// Assertions.assertEquals(tree.root, 400);
	Assertions.assertEquals(1, ((RBTNode<Integer>)tree.root).blackHeight);
	Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
	Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);
	Assertions.assertEquals("[ 300, 200, 400 ]", tree.toLevelOrderString());
    }
    
}
