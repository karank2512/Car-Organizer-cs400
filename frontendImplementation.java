import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class frontendImplementation implements FrontendInterface {

  Scanner scnr;
  BackendInterface backendRef;

  public frontendImplementation(BackendInterface backendRef, Scanner scanner) {
    scnr = scanner;
    this.backendRef = backendRef;
  }

  @Override
  public void start() throws FileNotFoundException, InputMismatchException {
    System.out.println("Welcome to the program!\nChoose one of the options to begin with: ");
    String options = "";
    options += "1) Display Cars with lowest mileage\n";
    options += "2) Display Cars with a mileage above a custom threshold\n";
    options += "3) Exit Program\n";
    options += "4) Read data from a file path";
    System.out.println(options);
    int exit = 0;
    while (exit == 0) {
      int optionSelected = 0;
      while (!scnr.hasNextInt()) {
        scnr.nextLine();
        System.out.println("Select a correct option from 1 to 4");
      }
      optionSelected = scnr.nextInt();
      while (optionSelected > 4 || optionSelected < 1) {
        System.out.println("Select a correct option from 1 to 4");
        optionSelected = scnr.nextInt();
      }
      switch (optionSelected) {
        case 1:
          lowestMileage();
          break;
        case 2:
          mileageThreshold();
          break;
        case 3:
          exit();
          exit++;
          break;
        case 4:
          readDataFile();
          break;
      }

      if (exit == 0) {
        System.out.println("\nWhich option would you like to select now?");
        System.out.println(options);
      }

    }
  }

  @Override
  public void readDataFile() {
    boolean success = false;
    Scanner scnr = new Scanner(System.in);
    try {
      System.out.println("Enter file path: ");
      success = backendRef.readFileData(scnr.next());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    if (success) {
      System.out.println("Data loaded successfully.");
    } else {
      System.out.println("Error: Invalid path");
    }
  }

  @Override
  public void lowestMileage() {
    List<Car> lowestMileageCars = backendRef.getCarsWithMinimumMileage();
    System.out.println("Cars with lowest mileage are:");
    for (int i = 0; i < lowestMileageCars.size(); i++) {
      System.out.println(lowestMileageCars.get(i));
    }
  }

  @Override
  public void mileageThreshold() {
    System.out.println("What is the minimum mileage?");
    double mileage = 0.0;
    if (scnr.hasNextDouble()) {
      mileage = scnr.nextDouble();
    }
    while (mileage < 0) {
      System.out.println("Please enter a positive number: ");
      mileage = scnr.nextDouble();
    }
    List<Car> mileageThresholdCars = backendRef.getCarsWithMileageAboveThreshold(mileage);
    System.out.println("Cars with mileage above the given mileage are:");
    for (int i = 0; i < mileageThresholdCars.size(); i++) {
      System.out.println(mileageThresholdCars.get(i));
    }
  }

  @Override
  public void exit() {
    System.out.print("Exit successfully!\n");
  }
}
