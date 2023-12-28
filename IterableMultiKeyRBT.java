// --== CS400 Fall 2023 File Header Information ==--
// Name: Karan Kapur
// Email: kkapur5@wisc.edu
// Group: B32
// TA: Casey Ford
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>>
    implements IterableMultiKeySortedCollectionInterface<T>{

  Comparable<T> iterationStartPoint;
  int numKeys = 0;

  /**
   * Inserts value into tree that can store multiple objects per key by keeping
   * lists of objects in each node of the tree.
   * @param key object to insert
   * @return true if a new node was inserted, false if the key was added into an existing node
   */
  @Override
  public boolean insertSingleKey(T key) {
    KeyList<T> newKeyList = new KeyList<>(key);

    numKeys++;

    //check if this key already exists in the tree
    if (this.findNode(newKeyList) == null)
    {
      //insert the key
      this.insert(newKeyList);
      return true;
    }
    else {
      //if the key already exists, find that node and add another item to that key
      Node<KeyListInterface<T>> node = this.findNode(newKeyList);
      node.data.addKey(key);
    }
    return false;
  }

  /**
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Returns an iterator that does an in-order iteration over the tree.
   */
  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      Stack<T> items = new Stack<>();
      Stack<Node<KeyListInterface<T>>> stack = getStartStack();

      @Override
      public boolean hasNext() {
        return !(stack.isEmpty());
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        if(items.isEmpty()) {
          Node<KeyListInterface<T>> currNode = stack.pop();
          for(T t : currNode.data){
            items.push(t);
          }

          if (currNode.down[1] != null) {
            stack.push(currNode.down[1]);
            currNode = currNode.down[1];
            while (currNode.down[0] != null) {
              stack.push(currNode.down[0]);
              currNode = currNode.down[0];
            }

          }
          return items.pop();
        }
        else return items.pop();
      }
    };
  }

  /**
   * Sets the starting point for iterations. Future iterations will start at the
   * starting point or the key closest to it in the tree. This setting is remembered
   * until it is reset. Passing in null disables the starting point.
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<T> startPoint) {
    this.iterationStartPoint = startPoint;
  }

  @Override
  public void clear(){
    super.clear();
    this.numKeys = 0;
  }
  protected Stack<Node<KeyListInterface<T>>> getStartStack() {

    Stack<Node<KeyListInterface<T>>> stack = new Stack<>();

    //checking if a starting point is defined
    if(iterationStartPoint != null){

      Node<KeyListInterface<T>> currNode = root;
      while(currNode != null){
        int compare = iterationStartPoint.compareTo(currNode.data.iterator().next());
        if(compare == 0) {
          //add the node if the starting point is same as the target
          stack.push(currNode);
          break;
        }
        else if(compare < 0) {
          //if the starting point < current node then add the node and move it to the right place
          stack.push(currNode); // add the node
          currNode = currNode.down[0];
        }
        else {
          currNode = currNode.down[1];
        }
      }
    }
    else{
      Node<KeyListInterface<T>> currNode = root;
      while(currNode != null){
        stack.push(currNode);
        currNode = currNode.down[0];
      }
    }
    return stack;
  }

  @Test
  public void testInsertSingleKey(){

    //creating an iterable Red Black Tree to add values to it
    IterableMultiKeyRBT<String> iterableRBT = new IterableMultiKeyRBT<>();

    //using the insertSingleKey() method to add single keys in the tree
    iterableRBT.insertSingleKey("Fruits");
    iterableRBT.insertSingleKey("Apples");
    iterableRBT.insertSingleKey("Grapefruit");

    if (!(iterableRBT.root.data.iterator().next().equals("Fruits")) ||
        !(iterableRBT.root.down[0].data.iterator().next().equals("Apples")) ||
        !(iterableRBT.root.down[1].data.iterator().next().equals("Grapefruit"))){
      Assertions.fail("Inserting new key is failed");
    }
  }

  @Test
  public void testEmptyTree() {

    //creating an iterable Red Black Tree to add values to it
    IterableMultiKeyRBT<Character> iterableRBT = new IterableMultiKeyRBT<>();

    //checks if the tree is empty
    Assertions.assertTrue(iterableRBT.iterator().hasNext() == false);
  }

  @Test
  public void testNumKeys(){

    //creating an iterable Red Black Tree to add values to it
    IterableMultiKeyRBT<String> iterableRBT = new IterableMultiKeyRBT<>();

    //using the insertSingleKey() method to add single keys in the tree
    iterableRBT.insertSingleKey("Fruits");
    iterableRBT.insertSingleKey("Apples");
    iterableRBT.insertSingleKey("Bananas");
    //adding a duplicate to see if it gets ignored. Ideally it should be ignored
    iterableRBT.insertSingleKey("Bananas");
    iterableRBT.insertSingleKey("Mango");

    Assertions.assertEquals(4, iterableRBT.size()); //size should ignore duplicates
    Assertions.assertEquals(5, iterableRBT.numKeys()); //confirms that 5 keys were added
  }
}
