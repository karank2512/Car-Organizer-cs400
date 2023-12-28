import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public interface FrontendInterface {
  /**
   * A constructor that accepts a reference to backend and a java.util.Scanner instance to read user
   * input.
   *
   * @param backendRef
   * @param scanner
   */
  // public void constructor(T backendRef, Scanner scanner); //


  /**
   * Initiates the main command loop for user. Asks for command from user.
   */
  public void start() throws FileNotFoundException;

  /**
   * Returns a list of the vehicles with the lowest mileage in the given data set.
   *
   * @return a list of vehicles with the lowest mileage
   */
  public void lowestMileage();

  /**
   * Returns a list of the vehicles at or above a threshold specified by the user after calling the command
   * set
   *
   * @return a list of vehicles at or above the threshold
   */
  public void mileageThreshold();
  /**
   * Terminates the main command loop for user.
   */
  public void exit();

  public void readDataFile();
}
