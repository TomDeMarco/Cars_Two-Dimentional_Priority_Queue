/**
 * A driver for CS1501 Project 3
 * @author    Dr. Farnan
 */
package cs1501_p3;

public class App {
	public static void main(String[] args) {
		//Add Tests
		System.out.println("Testing add---------------------------------------------------------------------");
		CarsPQ cars = new CarsPQ();
		Car c = new Car("5", "Ford", "Fiesta", 20, 20, "White");
		cars.add(c);
		// System.out.println("First add: " +cars.carsMileage[0].getMileage());
		// System.out.println("First add: " + cars.carsPrice[0].getPrice());
		// System.out.println(cars.cars.root.getDown().getLet());
		// //System.out.println(cars.cars.root.getDown().getDown().getLet());
		// DLBNode curr = cars.cars.root.getDown().getDown();
		// System.out.println(curr.getLet());
		// System.out.println("First add: " + curr.getcarMileageIndex());
		// System.out.println("First add: " + curr.getcarPriceIndex());

		Car d = new Car("94509868", "Ford", "Fiesta", 25, 30, "White");
		Car e = new Car("55097609", "Ford", "Fiesta", 30, 40, "White");

		cars.add(d);
		// System.out.println("Second add: " + d.getcarMileageIndex());
		// System.out.println("Second add: " + d.getcarPriceIndex());
		cars.add(e);
		// System.out.println("Third add: " + e.getcarMileageIndex());
		// System.out.println("Third add: " + e.getcarPriceIndex());

		//special case with no comparison except one swap
		// Car firstSwapMileage = new Car("4950502", "Ford", "Fiesta", 40, 25, "White"); // should swap with parent
		// Car firstSwapPrice = new Car("4970357082", "Ford", "Fiesta", 15, 5, "White"); // should also swap with parent 
		// cars.add(firstSwapPrice);
		// cars.add(firstSwapMileage); 
		// cars.add(new Car("495050ce", "Ford", "Fiesta", 60, 50, "White"));
		// cars.add(new Car("4950502de", "Ford", "Fiesta", 90, 23, "White"));
		// cars.add(new Car("495050cdev", "Ford", "Fiesta", 10, 10, "White"));

		System.out.println("Testing PQ Add: --------------------------------------");
		// System.out.println("Adding a duplicate, should throw an exception: ");
		// cars.add(e);
		cars.printPQ();
		System.out.println("Testing car attributes within array, Should be {4970357082, Ford, Fiesta, 15, 5, White} at index 0: " + cars.carsMileage[0].getVIN() + " " + cars.carsMileage[0].getMake() + " " + cars.carsMileage[0].getModel()+ " " + cars.carsMileage[0].getPrice() + cars.carsMileage[0].getMileage());

		System.out.println("Testing get method: --------------------------------------");
		CarsPQ cars2 = new CarsPQ();
		cars2.add(new Car("495050ce", "Ford", "Fiesta", 10, 10, "White"));
		cars2.add(new Car("495050260", "Ford", "Fiesta", 40, 40, "White"));
		cars2.add(new Car("495050cdev", "Ford", "Fiesta", 60, 60, "White"));
		cars2.add(new Car("375892y587", "Ford", "Fiesta", 1, 1, "White"));


		System.out.println("Getting 495050ce : " +cars2.get("495050ce").getVIN());
		System.out.println("Getting 495050260 : " +cars2.get("495050260").getVIN());
		System.out.println("Getting 495050cdev : " +cars2.get("495050cdev").getVIN());
		System.out.println("Getting 375892y587 : " +cars2.get("375892y587").getVIN());

		System.out.println("Testing Update Mileage method: --------------------------------------");
		CarsPQ cars3 = new CarsPQ();
		cars3.add(new Car("495050ce", "Ford", "Fiesta", 10, 10, "White"));
		cars3.add(new Car("495050260", "Ford", "Fiesta", 40, 40, "White"));
		cars3.add(new Car("495050cdev", "Ford", "Fiesta", 60, 60, "White"));
		cars3.add(new Car("375892y587", "Ford", "Fiesta", 1, 1, "White"));

		cars3.printPQ();

		System.out.println("Updating the root from 1 to 30 it should end up at 1 and 10 at root: " );
		cars3.updateMileage("375892y587",30);

		cars3.printPQ();

		System.out.println("Testing Update Price method: --------------------------------------");
		
		cars3.printPQ();

		System.out.println("Updating the root from 1 to 30 it should end up at 1 and 10 at root: " );
		cars3.updatePrice("375892y587",30);

		cars3.printPQ();

		System.out.println("Testing Update Color------------------------------------------------------------------------------------------------------------");
		CarsPQ cars4 = new CarsPQ();
		cars4.add(new Car("375892y587", "Ford", "Fiesta", 1, 1, "White"));
		System.out.println("The color before update color is: " + cars4.carsMileage[0].getColor());
		cars4.updateColor("375892y587", "Blue");
		System.out.println("The color after update color is: "+ cars4.carsMileage[0].getColor());

		System.out.println("Testing remove method-----------------------------------------------------------------------------------------------");
		// CarsPQ cars5 = new CarsPQ();
		// cars5.add(new Car("VIN1", "Make1", "Model1", 10000, 20000, "Color1"));
    	// cars5.add(new Car("VIN2", "Make2", "Model2", 20000, 25000, "Color2"));
        // cars5.add(new Car("VIN3", "Make3", "Model3", 15000, 18000, "Color3"));
		// System.out.println("Before removing:");
        // cars5.printPQ();
		// System.out.println("Words in DLB before removal:");
        // System.out.println(cars5.cars.traverse());
		// cars5.remove("VIN2");
        // cars5.printPQ();
        // System.out.println("Words in DLB after removal:");
        // System.out.println(cars5.cars.traverse());
		CarsPQ removeTest = new CarsPQ("/workspaces/project3-Tomd445/app/src/test/resources/cars.txt");
		System.out.println("Before removing:");
        removeTest.printPQ();
		System.out.println("Cars in DLB before removal:");
        System.out.println(removeTest.mileageOfCars.traverse());
		System.out.println("The price of X1U2PEJSC361L10MZ is: "+removeTest.get("X1U2PEJSC361L10MZ").getPrice() +" and the mileage of X1U2PEJSC361L10MZ is: "+ removeTest.get("X1U2PEJSC361L10MZ").getMileage() );
		removeTest.remove("X1U2PEJSC361L10MZ");
        removeTest.printPQ();
        System.out.println("Cars in DLB after removal:");
        System.out.println(removeTest.mileageOfCars.traverse());
		System.out.println("Testing removal of entire array----------------------------------");
		System.out.println("Before Removal DLB: "+removeTest.mileageOfCars.traverse());
		for(int i=0;i<removeTest.mileageNumOfCars;i++){
			removeTest.remove(removeTest.carsMileage[i].getVIN());
		}
		System.out.println("After removal DLB: "+removeTest.mileageOfCars.traverse());
		System.out.println("Testing getLowMileage method: ------------------------------------------------------------------------------------------------------------");
		CarsPQ cars8 = new CarsPQ();
		cars8.add(new Car("495050ce", "Ford", "Fiesta", 10, 11, "White"));
		cars8.add(new Car("495050260", "Honda", "Civic", 1, 2, "White"));
		cars8.add(new Car("495050cdev", "Ford", "Fiesta", 60, 60, "White"));
		cars8.add(new Car("375892y587", "Ford", "Fiesta", 1, 10, "White"));
		System.out.println("Cars8 lowest mileage Ford Fiesta should be (375892y587): "+ cars8.getLowMileage("Ford","Fiesta").getVIN());

		System.out.println("Testing getLowPriceMethod------------------------------------------------------------");
		CarsPQ cars9 = new CarsPQ();
		cars9.add( new Car("4950502", "Honda", "Civic", 1, 1, "White"));
		cars9.add( new Car("4970357082", "Ford", "Fiesta", 15, 5, "White"));
		cars9.add(new Car("495050ce", "Ford", "Fiesta", 60, 50, "White"));
		cars9.add(new Car("4950502de", "Ford", "Fiesta", 10, 1, "White"));
		cars9.add(new Car("495050cdev", "Ford", "Fiesta", 15, 10, "White"));
		System.out.println("Cars8 lowest mileage Ford Fiesta should be (4950502de): "+ cars9.getLowPrice("Ford","Fiesta").getVIN());

		System.out.println("Testing Resize Method----------------------------------------");
		CarsPQ cars10 = new CarsPQ();
		cars10.add( new Car("4950502", "Honda", "Civic", 1, 1, "White"));
		cars10.add( new Car("4970357082", "Ford", "Fiesta", 15, 5, "White"));
		cars10.printPQ();
		cars10.add(new Car("495050ce", "Ford", "Fiesta", 60, 50, "White"));
		cars10.add(new Car("4950502de", "Ford", "Fiesta", 10, 1, "White"));
		cars10.add(new Car("495050cdev", "Ford", "Fiesta", 15, 10, "White"));
		cars10.printPQ();


		System.out.println("Testing input constructor-------------------------------------");
		CarsPQ cpq = new CarsPQ("/workspaces/project3-Tomd445/app/src/test/resources/cars.txt");
		cpq.printPQ();

		System.out.println("Testing getLowMileage method: ------------------------------------------------------------------------------------------------------------");
		System.out.println("Cars8 lowest mileage Ford Fiesta should be (1Y5NWYGLY5F4PX4HH): "+ cpq.getLowMileage("Ford","Escort").getVIN());

		System.out.println("Testing getLowPriceMethod------------------------------------------------------------");
		System.out.println("Cars8 lowest price Ford Fiesta should be (SY719WJ4MMYVN0XNG): "+ cpq.getLowPrice("Hyundai","Elantra").getVIN());
}
}

