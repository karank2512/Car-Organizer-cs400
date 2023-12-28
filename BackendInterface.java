import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public interface BackendInterface{

    /**
     * Used to get the files in the dataset
     *
     * @param file
     * @return list of cars in the dataset
     */
    public boolean readFileData(String file) throws FileNotFoundException;

    /**
     * Used to get the list of cars with minimum mileage threshold
     *
     * @return the list of cars with mileage above minimum threshold
     */
    public ArrayList<Car> getCarsWithMinimumMileage();

    /**
     * Used to get the list of cars with mileage above a specific threshold
     *
     * @param mileageThreshold
     * @return
     */

    public ArrayList<Car> getCarsWithMileageAboveThreshold(double mileageThreshold);

}
