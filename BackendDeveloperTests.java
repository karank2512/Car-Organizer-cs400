import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Scanner;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BackendDeveloperTests {

    /**
     * tests the backend class interface methods and the working of the constructor
     */
    @Test
    public void testCar() {
        Car car = new Car(6300, "Toyota", "Cruiser", 2008, 274117.0);

        assertEquals("Toyota", car.getBrand());
        assertEquals("Cruiser", car.getModel());
        assertEquals(2008, car.getYear());
        assertEquals(6300, car.getPrice());
        assertEquals(274117.0, car.getMileage());
    }

    /**
     * tests the readFileData method of the backend interface class and checks if the data is loaded
     * or not.
     */
    @Test
    public void testReadFileData() {
//        IterableMultiKeyRBT<Car> testTree = new IterableMultiKeyRBT<>();

        Backend testBackend = new Backend(new IterableMultiKeyRBT<Car>());

        //This is the first car element in testcars.csv.
//        Car firstCar = new Car(27700, "chevrolet", "1500", 2018, 6654.0F);
        assertEquals(true, testBackend.readFileData("src/cars.csv"));
    }

    @Test
    public void testBadFileData() {
        Backend testBackend = new Backend(new IterableMultiKeyRBT<Car>());
        assertFalse(testBackend.readFileData(("/Users/karankapur/Downloads/fakeFile123" + ".csv")));
    }


    /**
     * tests the getCarsWithMinimumMileage method of the backend interface class and checks if the
     * method is returning the cars with minimum mileage or not.
     */
    @Test
    public void testCarsWithMinimumMileage() {

        Backend testBackend = new Backend(new IterableMultiKeyRBT<Car>());
        testBackend.readFileData("src/cars.csv");

        Car minCar = new Car(12520, "gmc", "door", 2017, 30636.0);

        Assertions.assertTrue(testBackend.getCarsWithMinimumMileage().get(0).compareTo(minCar) == 0);
    }

    /**
     * tests the getCarsWithMileageAboveThreshold method of the backend interface class and checks if
     * the method is returning the list of cars with mileage above a specific threshold or not.
     */

    @Test
    public void testCarsWithMileageAboveThreshold() {
        Backend testBackend = new Backend(new IterableMultiKeyRBT<Car>());
        testBackend.readFileData("src/cars.csv");

        Car threshCar1 = new Car(6300, "toyota", "cruiser", 2008, 274117.0);
        Car threshCar2 = new Car(1000, "chevrolet", "door", 2005, 135675.0);

        Assertions.assertTrue(testBackend.getCarsWithMileageAboveThreshold(100000.0).get(0).compareTo(threshCar2) == 0);
        Assertions.assertTrue(testBackend.getCarsWithMileageAboveThreshold(100000.0).get(1).compareTo(threshCar1) == 0);
    }

    /**
     * tests the integration of frontend with backend by testing an invalid file through
     * readDataFile() method
     */

    @Test
    public void integrationTestLoadDataInvalidPath() {
        // Prepare simulated user input with an invalid path.
        String input = "invalid_path.csv\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Capture the console output to check if the output contains an error message.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the loadData() method.
        Backend backend = new Backend(new IterableMultiKeyRBT<Car>());
        frontendImplementation frontend = new frontendImplementation(backend, new Scanner(System.in));
        frontend.readDataFile();

        // Check if the output contains an error message.
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("Error: Invalid path"));
    }

    /**
     * tests the integration of frontend with backend by testing the mileageThreshold() method
     */
    @Test
    public void integrationTestFrontendMileageThreshold(){
        // Prepare simulated user input with a mileage threshold.
        String input = "100000";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Capture the console output to check if the output contains the list of cars.
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call the mileageThreshold() method.
        Backend backend = new Backend(new IterableMultiKeyRBT<Car>());
        frontendImplementation frontend = new frontendImplementation(backend, new Scanner(System.in));
        frontend.readDataFile();
        frontend.mileageThreshold();
        // Check if the output contains the list of songs.
        String output = outputStream.toString();
        Assertions.assertTrue(output.contains("Cars with mileage above the given mileage are:"));
        // we are considering that the threshold input by the user is 100000
        Car expectedCar1 = new Car(6300, "toyota", "cruiser", 2008, 274117.0);
        Car expectedCar2 = new Car(1000, "chevrolet", "door", 2005, 135675.0);

        Assertions.assertTrue(output.contains(expectedCar1.toString()));
        Assertions.assertTrue(output.contains(expectedCar2.toString()));
    }

}
