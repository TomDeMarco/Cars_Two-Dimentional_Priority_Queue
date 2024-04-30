
package cs1501_p3;

import java.io.Serializable;

public class DLBNode implements Serializable {

    //variables held by node
    private char let;
    private int carMileageIndex;
    private int carPriceIndex;


    /**
     * Lead to other alternatives for current letter in the path
     */
    private DLBNode right;

    /**
     * Leads to keys with prefixed by the current path
     */
    private DLBNode down;

    /**
     * Constructor that accepts the letter for the new node to represent
     */
    public DLBNode(char let){
        this.let = let;
    }

    //Alternative Constructor for end nodes
    public DLBNode(char let, int carMileageIndex, int carPriceIndex){
        this.let = let;
        this.carMileageIndex = carMileageIndex;
        this.carPriceIndex = carPriceIndex;
        this.right = null;
        this.down = null;
    }

    /**
     * Getter for the letter this DLBNode represents
     *
     * @return The letter
     */
    public char getLet() {
        return let;
    }

    //index of carMileage in PQ
    public int getcarMileageIndex(){
        return carMileageIndex;
    }

        //index of carPrice in PQ
    public int getcarPriceIndex(){
        return carPriceIndex;
    }

    /**
     * Getter for the next linked-list DLBNode
     *
     * @return Reference to the right DLBNode
     */
    public DLBNode getRight() {
        return right;
    }

    /**
     * Getter for the child DLBNode
     *
     * @return Reference to the down DLBNode
     */
    public DLBNode getDown() {
        return down;
    }

    /**
     * Setter for the next linked-list DLBNode
     *
     * @param r DLBNode to set as the right reference
     */
    public void setRight(DLBNode r) {
        right = r;
    }

    /**
     * Setter for the child DLBNode
     *
     * @param d DLBNode to set as the down reference
     */
    public void setDown(DLBNode d) {
        down = d;
    }
    
    //setter for mileage index
    public void setMileageIndex(int index){
        carMileageIndex=index;
    }
    //setter for price
    public void setPriceIndex(int index){
        carPriceIndex=index;
    }

}