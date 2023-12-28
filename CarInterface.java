public interface CarInterface extends Comparable<Car> {

    // constructor

    /**
     * public BackendClassInterface(String brand, String model, int year, double price, double mileage){
     *  this.brand = brand;
     *  this.model = model;
     *  this.year = year;
     *  this.price = price;
     *  this.mileage = mileage;
     * }
     */

     /**
      * Used to get the brand of the car
      *
      * @return the brand of the car
      */
     public String getBrand();

     /**
      * Used to get the model of the car
      *
      * @return the model of the car
      */
     public String getModel();

     /**
      * Used to get the make year of the car
      *
      * @return the make year of the car
      */
     public int  getYear();

     /**
      * Used to get the price
      *
      * @return the price of the car
      */
     public double getPrice();

     /**
      * Used to get the mileage of the car
      *
      * @return the mileage of the car
      */
     public double getMileage();

     int compareTo(Car o);
}
