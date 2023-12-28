import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Backend implements BackendInterface {
  public IterableMultiKeySortedCollectionInterface<Car> rbt;

  public Backend(IterableMultiKeySortedCollectionInterface<Car> rbt) {
    this.rbt = rbt;
  }

  public static void main(String[] args) throws FileNotFoundException {
    Scanner scan = new Scanner(System.in);
    IterableMultiKeyRBT<Car> rbtClass = new IterableMultiKeyRBT<>();
    Backend backend = new Backend(rbtClass);
    frontendImplementation newM = new frontendImplementation(backend, scan);
    newM.start();
  }

  @Override
  public boolean readFileData(String filePath) {

    try {
      File carsList = new File(filePath);
      if (carsList.length() == 0) return false;
      Scanner fileScnr = new Scanner(carsList);
      fileScnr.nextLine();  //Skip first line, which just denotes values of each part.
      while (fileScnr.hasNextLine()) { //Some elements have spaces.
        String indivCarData = fileScnr.nextLine();
        String[] carData = indivCarData.split(",");
        //For now, we will not consider nested commas or quotes, as our data set testcars.csv has no nested commas.
        int price = Integer.parseInt(carData[0]);
        String brand = carData[1];
        String model = carData[2];
        int year = Integer.parseInt(carData[3]);
        double mileage = Double.parseDouble(carData[5]);

        Car newInsertCar = new Car(price, brand, model, year, mileage);

        rbt.insertSingleKey(newInsertCar);
      }
      fileScnr.close();
      return true;

    } catch (FileNotFoundException e) {
      return false;
    }
  }

  @Override
  public ArrayList<Car> getCarsWithMileageAboveThreshold(double mileageThreshold) {
    ArrayList<Car> returnList = new ArrayList<>();
    for (Car car : rbt ) {
      if (car.getMileage() >= mileageThreshold) {
        returnList.add(car);
      }
    }
    return returnList;
  }

  public ArrayList<Car> getCarsWithMinimumMileage() {
    ArrayList<Car> carsWithMinMileage = new ArrayList<>();
    double minMileage = Double.MAX_VALUE; // Initialize with a high value

    for (Car car : rbt) {
      double carMileage = car.getMileage();
      if (carMileage < minMileage) {
        carsWithMinMileage.clear(); // Clear the list for new minimum
        carsWithMinMileage.add(car);
        minMileage = carMileage;
      } else if (carMileage == minMileage) {
        carsWithMinMileage.add(car); // Add cars with the same minimum mileage
      }
    }

    return carsWithMinMileage;
  }
}
