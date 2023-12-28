import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
public class RedBlackTreePlaceholder<T extends Comparable<T>> implements IterableMultiKeySortedCollectionInterface<T> {
  ArrayList<Car> thisTree = new ArrayList<>(); // Hard coded array.
  protected int size = 5;

  //For our current testing purposes a small portion of the csv list is hard coded, and is
  // in-order organized.
  public RedBlackTreePlaceholder() {
    thisTree.add( new Car(2018, "chevrolet",
        "1500", 27700, 6654.0F));

    thisTree.add(new Car(5350, "dodge","mpv",2018,39590.0F));

    thisTree.add(new Car(25000, "ford","door",2014,64146.0F));

    thisTree.add(new Car(2899, "ford", "se", 2011, 190552.0F));

    thisTree.add(new Car(6300, "toyota", "cruiser", 2008, 274117F));
  }

  @Override
  public boolean insertSingleKey(Comparable key) {
    return true;
  }

  @Override
  public int numKeys() {
    return 0;
  }

  @Override
  public Iterator iterator() {
    return thisTree.iterator();
  }

  @Override
  public void setIterationStartPoint(Comparable startPoint) {
    //Hardcoded to what the test wants.
    thisTree = new ArrayList<>();
    thisTree.add(new Car(25000, "ford","door",2014,64146.0F));
    thisTree.add(new Car(2899, "ford", "se", 2011, 190552.0F));
    thisTree.add(new Car(6300, "toyota", "cruiser", 2008, 274117F));

  }


  @Override
  public boolean contains(Comparable data) {
    return true;
  }



  @Override
  public boolean insert(KeyListInterface<T> data) throws NullPointerException, IllegalArgumentException {
    return true;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public void clear() {

  }


}
