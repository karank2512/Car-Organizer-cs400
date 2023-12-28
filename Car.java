public class Car implements CarInterface {

  private String brand;
  private String model;
  private int year;
  private int price;
  private double mileage;

  public Car(int price, String brand, String model, int year, double mileage) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.price = price;
    this.mileage = mileage;
  }

  @Override
  public String getBrand() {
    return this.brand;
  }

  @Override
  public String getModel() {
    return this.model;
  }

  @Override
  public int getYear() {
    return this.year;
  }

  @Override
  public double getPrice() {
    return this.price;
  }

  @Override
  public double getMileage() {
    return this.mileage;
  }

  @Override
  public String toString(){
    return (year + " " + brand + " " + model + " Mileage: " + mileage + " Price: " + price);
  }

  @Override
  public int compareTo(Car otherCar) {
    // Compare songs based on their danceability scores
    if (this.mileage < otherCar.mileage) {
      return -1;
    } else if (this.mileage > otherCar.mileage){
      return 1;
    } else {
      return 0;
    }

  }
}
