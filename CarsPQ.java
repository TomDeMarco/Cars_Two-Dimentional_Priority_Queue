package cs1501_p3;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CarsPQ implements CarsPQ_Inter{

    //Instance Variables
    DLB cars;
    Car[] carsPrice;
    Car[] carsMileage;
    int priceNumOfCars;
    int mileageNumOfCars;
    int sizeOfArrays;

    //Constructor
    public CarsPQ(){
     priceNumOfCars=0;
     mileageNumOfCars=0;
     sizeOfArrays = 2;
     cars = new DLB();
     carsPrice = new Car[sizeOfArrays];
     carsMileage = new Car[sizeOfArrays];
    }

    //text file input constructor
    public CarsPQ(String filePath){
    mileageNumOfCars = 0;
    priceNumOfCars = 0;
    sizeOfArrays = 2;
    cars = new DLB();
    carsPrice = new Car[sizeOfArrays];
    carsMileage = new Car[sizeOfArrays];
    String line = "";
    try{
        BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
        fileReader.readLine(); // Assuming the first line is a header and should be skipped
        while((line = fileReader.readLine()) != null){
            String[] carInfo = line.split(":");
            if(carInfo.length == 6){
                String vin = carInfo[0];
                String make = carInfo[1];
                String model = carInfo[2];
                int mileage = Integer.parseInt(carInfo[3]);
                int price = Integer.parseInt(carInfo[4]);
                String color = carInfo[5];
                Car newCar = new Car(vin, make, model, mileage, price, color);
                add(newCar);
            }   
        }
        fileReader.close();
    } catch(IOException exception){
        // Debugging: Print exception message for troubleshooting
        System.out.println("Something bad happened: " + exception.getMessage());
        // Print stack trace for detailed error information
        exception.printStackTrace();
    }
    }

    /**
     * Add a new Car to the data structure
     * Should throw an `IllegalStateException` if there is already car with the
     * same VIN in the datastructure.
     *
     * @param c Car to be added to the data structure
     */
    public void add(Car car) throws IllegalStateException{

        if(car == null){//null entry
            throw new IllegalStateException();
        }else{ //non-null entry
        if(priceNumOfCars == sizeOfArrays || mileageNumOfCars == sizeOfArrays){
            this.resizePQ();
        }

        //add to mileage PQ
        carsMileage[mileageNumOfCars]=car;
        int currentMileageIndex=mileageNumOfCars; //set current index
        int parentIndex = (int) Math.floor(currentMileageIndex/2); //set parent index

        while(carsMileage[currentMileageIndex].getMileage() < carsMileage[parentIndex].getMileage() ){ //while there is a child less than a parent
                Car temp = carsMileage[currentMileageIndex]; // temp variable of child
                carsMileage[currentMileageIndex]= carsMileage[parentIndex]; //set child to parent
                carsMileage[parentIndex] = temp; //swap parent with child
                currentMileageIndex=parentIndex; //iterate up through heap
                parentIndex = (int) Math.floor(currentMileageIndex/2); //find next parent
        }
        mileageNumOfCars++;

        //add to cost PQ
        carsPrice[priceNumOfCars]=car; //add car
        int currentPriceIndex = priceNumOfCars; //set price index
        parentIndex = (int) Math.floor(currentPriceIndex/2); //parent index

        while(carsPrice[currentPriceIndex].getPrice() < carsPrice[parentIndex].getPrice() ){
                Car temp = carsPrice[currentPriceIndex]; // temp variable of child
                carsPrice[currentPriceIndex]= carsPrice[parentIndex]; //set child to parent
                carsPrice[parentIndex] = temp; //swap parent with child
                currentPriceIndex=parentIndex; //iterate up through heap
                parentIndex = (int) Math.floor(currentPriceIndex/2); // find new parent 
        }
                priceNumOfCars++;

        cars.add(car,currentMileageIndex,currentPriceIndex); // add car VIN to the cars DLB. also keep a reference to where that car is in the lists

        //must update the index of the cars that have been swapped
        updateIndirection(carsMileage,carsPrice);

        }

    }

    /**
     * Retrieve a new Car from the data structure
     * Should throw a `NoSuchElementException` if there is no car with the
     * specified VIN in the datastructure.
     *
     * @param vin VIN number of the car to be updated
     */
    public Car get(String vin) throws NoSuchElementException{
        if(cars.contains(vin) == false){
            throw new NoSuchElementException();
        }else{
            String key = vin +"^";
      DLBNode curr = cars.root.getDown();
      int i = 0;
      while(i<key.length()-1){ // loop through length of vin plus
        curr = cars.searchRight(key.charAt(i), curr);
        curr= curr.getDown();
        i++;
        }
      
        int indexOfCar = curr.getcarMileageIndex(); // get car index in mileage
        return carsMileage[indexOfCar]; //return the car
        }

    }

    /**
     * Update the price attribute of a given car
     * Should throw a `NoSuchElementException` if there is no car with the
     * specified VIN in the datastructure.
     *
     * @param vin      VIN number of the car to be updated
     * @param newPrice The updated price value
     */
    public void updatePrice(String vin, int newPrice) throws NoSuchElementException{
        if (!cars.contains(vin)) {
            throw new NoSuchElementException();
        }

        String key = vin + "^";
        DLBNode curr = cars.root.getDown();
        int i = 0;
        while (i < key.length() - 1) {
            curr = cars.searchRight(key.charAt(i), curr);
            curr = curr.getDown();
            i++;
        }
        int indexOfCar = curr.getcarPriceIndex();

        carsPrice[indexOfCar].setPrice(newPrice);

        heapifyDownPrice(indexOfCar);
        updateIndirectionPrice(carsPrice);
    }
    

    /**
     * Update the mileage attribute of a given car
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin        VIN number of the car to be updated
     * @param newMileage The updated mileage value
     */
    public void updateMileage(String vin, int newMileage) throws NoSuchElementException{
        if (!cars.contains(vin)) {
            throw new NoSuchElementException();
        }

        String key = vin + "^";
        DLBNode curr = cars.root.getDown();
        int i = 0;
        while (i < key.length() - 1) {
            curr = cars.searchRight(key.charAt(i), curr);
            curr = curr.getDown();
            i++;
        }
        int indexOfCar = curr.getcarMileageIndex();

        carsMileage[indexOfCar].setMileage(newMileage);

        heapifyDownMileage(indexOfCar);
        updateIndirectionMileage(carsMileage); 
        
    }
                   
    /**
     * Update the color attribute of a given car
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin      VIN number of the car to be updated
     * @param newColor The updated color value
     */
    public void updateColor(String vin, String newColor) throws NoSuchElementException{

        if(cars.contains(vin) == false){
            throw new NoSuchElementException();
        }else{
            String key = vin +"^";
      DLBNode curr = cars.root.getDown();
      int i = 0;
      while(i<key.length()-1){ // loop through length of vin plus
        curr = cars.searchRight(key.charAt(i), curr);
        curr= curr.getDown();
        i++;
        }
        int mileageIndex= curr.getcarMileageIndex();
        int priceIndex= curr.getcarPriceIndex();
 
        carsMileage[mileageIndex].setColor(newColor);
        carsPrice[priceIndex].setColor(newColor);
    }
    }

    /**
     * Remove a car from the data structure
     * Should throw a `NoSuchElementException` if there is not car with the
     * specified VIN in the datastructure.
     *
     * @param vin VIN number of the car to be removed
     */
    public void remove(String vin) throws NoSuchElementException{
        if(!cars.contains(vin)){
        throw new NoSuchElementException();
    }

    // Find the car in the DLB
    String key = vin + "^";
    DLBNode curr = cars.root.getDown();
    int i = 0;
    while(i < key.length() - 1){ 
        curr = cars.searchRight(key.charAt(i), curr);
        curr = curr.getDown();
        i++;
    }

    // Get the indices of the car in the arrays
    int priceIndex = curr.getcarPriceIndex();
    int mileageIndex = curr.getcarMileageIndex();

    // Swap the car with the last car in each array and set the last added car to null
    carsPrice[priceIndex] = carsPrice[priceNumOfCars - 1];
    carsPrice[priceNumOfCars-1] = null;
    carsMileage[mileageIndex] = carsMileage[mileageNumOfCars - 1];
    carsMileage[mileageNumOfCars-1] = null;
    priceNumOfCars--;
    mileageNumOfCars--;

    // Heapify down to maintain heap properties
    heapifyDownPrice(priceIndex);
    heapifyDownMileage(mileageIndex);

    //update DLB
    updateIndirection(carsMileage, carsPrice);
    cars.remove(vin);


}

    /**
     * Get the lowest priced car (across all makes and models)
     * Should return `null` if the data structure is empty
     *
     * @return Car object representing the lowest priced car
     */
    public Car getLowPrice(){
        Car lowestPriceCar = carsPrice[0];
        return lowestPriceCar;

    }

    /**
     * Get the lowest priced car of a given make and model
     * Should return `null` if the data structure is empty
     *
     * @param make  The specified make
     * @param model The specified model
     * 
     * @return Car object representing the lowest priced car
     */
    public Car getLowPrice(String make, String model){ 

        if(carsPrice[0]==null){
            return null;
        }
        Car lowestPriceCar = null;
        for(int i=0; i<priceNumOfCars;i++){
            Car nextCar = carsPrice[i];
            if(lowestPriceCar==null && carsPrice[i].getMake() == make && carsPrice[i].getModel()== model){
                    lowestPriceCar=nextCar;
            }else if((carsPrice[i].getMake() == make && carsPrice[i].getModel()== model) && nextCar.getPrice()<lowestPriceCar.getPrice()){
                    lowestPriceCar=nextCar;
            }
        }
        return lowestPriceCar;
    }

    /**
     * Get the car with the lowest mileage (across all makes and models)
     * Should return `null` if the data structure is empty
     *
     * @return Car object representing the lowest mileage car
     */
    public Car getLowMileage(){
        Car lowestMileageCar= carsMileage[0];
        return lowestMileageCar;
    }

    /**
     * Get the car with the lowest mileage of a given make and model
     * Should return `null` if the data structure is empty
     *
     * @param make  The specified make
     * @param model The specified model
     *
     * @return Car object representing the lowest mileage car
     */
    public Car getLowMileage(String make, String model){ //issue here is due to remove method? lowestMileageCar never set to a value
        if(carsMileage[0]==null){
            return null;
        }
        Car lowestMileageCar = null;

        //debug start
        System.out.println("The Desired Make is: "+make);
        System.out.println("The Desired Model is: "+model);
        //debug end


        for(int i=0; i<mileageNumOfCars;i++){
            Car nextCar = carsMileage[i];

            //debug start
            System.out.println("NextCar is: "+nextCar.getVIN());
            System.out.println("nextCar Make is: "+ nextCar.getMake());
            System.out.println("nextCar Model is: "+nextCar.getModel());
            if(lowestMileageCar != null){
            System.out.println("lowestCar is: "+lowestMileageCar.getVIN());
            }
            System.out.println("At index "+ i +" in array " + "and total number of cars is: "+mileageNumOfCars); 
            //debug end

            if((lowestMileageCar==null) && (carsMileage[i].getMake() == make && carsMileage[i].getModel()== model)){
                    lowestMileageCar=nextCar;
            }else if((carsMileage[i].getMake() == make && carsMileage[i].getModel()== model) && nextCar.getMileage()<lowestMileageCar.getMileage()){
                    lowestMileageCar=nextCar;
            }
        }
        return lowestMileageCar;
    }

    //Helper Methods---------------------------------------------------------

    // Helper method to heapify down the carsPrice array
    private void heapifyDownPrice(int index){
    int leftChildIndex = 2 * index + 1;
    int rightChildIndex = 2 * index + 2;
    int smallest = index;

    if(leftChildIndex < priceNumOfCars && carsPrice[leftChildIndex].getPrice() < carsPrice[smallest].getPrice()){
        smallest = leftChildIndex;
    }

    if(rightChildIndex < priceNumOfCars && carsPrice[rightChildIndex].getPrice() < carsPrice[smallest].getPrice()){
        smallest = rightChildIndex;
    }

    if(smallest != index){
        // Swap with the smallest child and recursively heapify down
        Car temp = carsPrice[index];
        carsPrice[index] = carsPrice[smallest];
        carsPrice[smallest] = temp;
        heapifyDownPrice(smallest);
    }
    }

    // Helper method to heapify down the carsMileage array
    private void heapifyDownMileage(int index){
    int leftChildIndex = 2 * index + 1;
    int rightChildIndex = 2 * index + 2;
    int smallest = index;

    if(leftChildIndex < mileageNumOfCars && carsMileage[leftChildIndex].getMileage() < carsMileage[smallest].getMileage()){
        smallest = leftChildIndex;
    }

    if(rightChildIndex < mileageNumOfCars && carsMileage[rightChildIndex].getMileage() < carsMileage[smallest].getMileage()){
        smallest = rightChildIndex;
    }

    if(smallest != index){
        // Swap with the smallest child and recursively heapify down
        Car temp = carsMileage[index];
        carsMileage[index] = carsMileage[smallest];
        carsMileage[smallest] = temp;
        heapifyDownMileage(smallest);
    }
    }

    //update cars DLB method will iterate through the array one at a time and make each item in the DLB reflect its accurate array index representation
    private void updateIndirection(Car[] mileage, Car[] price){
        updateIndirectionMileage(mileage);
        updateIndirectionPrice(price);
    }

    private void updateIndirectionMileage(Car[] mileage){
            for(int k=0;k<mileageNumOfCars;k++){
            String key = mileage[k].getVIN() +"^";
            DLBNode curr = cars.root.getDown();
            int i = 0;
            while(i<key.length()-1){ // loop through length of vin plus
            curr = cars.searchRight(key.charAt(i), curr);
            curr= curr.getDown();
            i++;
            }
            curr.setMileageIndex(k); //set car index in mileage into carot node
        }
    }

    private void updateIndirectionPrice(Car[] price){
            for(int j=0;j<priceNumOfCars;j++){
            String key = price[j].getVIN() +"^";
            DLBNode curr = cars.root.getDown();
            int i = 0;
            while(i<key.length()-1){ // loop through length of vin plus
                curr = cars.searchRight(key.charAt(i), curr);
                curr= curr.getDown();
                i++;
            }
            curr.setPriceIndex(j);
            }
    }

    //print each array debug method
    public void printPQ(){
        System.out.println("The Number of Cars is: " +priceNumOfCars);
        for(int i=0;i<priceNumOfCars;i++){
            System.out.println("Cars Price index " + i + " is: " +carsPrice[i].getPrice() + " and the VIN is : " +carsPrice[i].getVIN());
        }
        for(int j=0;j<mileageNumOfCars;j++){
            System.out.println("Cars Mileage index " + j + " is: " +carsMileage[j].getMileage() + " and the VIN is : " +carsMileage[j].getVIN());
        }
    }

    //resize each array method
    private void resizePQ(){
        int newSize = this.sizeOfArrays*2;
        Car[] newCarsPrice= new Car[newSize];
        Car[] newCarsMileage= new Car[newSize];

        for(int i=0;i<priceNumOfCars;i++){
            newCarsPrice[i]=carsPrice[i];
        }

        for(int j=0;j<mileageNumOfCars;j++){
            newCarsMileage[j]=carsMileage[j];
        }

        this.sizeOfArrays=newSize;
        this.carsPrice = newCarsPrice;
        this.carsMileage = newCarsMileage;
    }


}
