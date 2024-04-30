
package cs1501_p3;
import java.util.ArrayList;
public class DLB{

  DLBNode root;
  int count;
  String word = "";

  // constructors
  public DLB(){
    root = new DLBNode('?');
    count = 0;
  }
  public DLB(DLBNode rootNode){
    this.root = rootNode;
    count = 0;
  }

   /**
     * Add a new word to the dictionary
     *
     * @param key New word to be added to the dictionary
     */
    public void add(Car car, int mileageIndex, int priceIndex){
        if(car == null){ // case of a null entry 
        return;
        }
        String key = car.getVIN() +"^";
        DLBNode curr = root.getDown(); // current node
        int i = 0;
        if(curr == null){ // root is null
          root.setDown( new DLBNode(key.charAt(i)));
          addDown( root.getDown(),key.substring(i+1), mileageIndex, priceIndex);
          count++;
        }else{ // root is not null
          while(i<key.length()){
              curr = searchRight(key.charAt(i), curr);
              if(curr.getLet()== key.charAt(i)){ // There is a char there, move down list
                curr = curr.getDown();
                i++;
              }else{ // add a new word
                curr.setRight( new DLBNode(key.charAt(i)));
                curr = curr.getRight();
                addDown( curr,key.substring(i+1), mileageIndex, priceIndex);
                count++;
                break;
              }
              }

          }
    }
        

    public DLBNode searchRight(char c, DLBNode input){ //1D loop through linkedList in the right direction
      DLBNode curr = input;
      while(curr.getRight() != null && curr.getLet() != c ){ // loop until null or finds the right char
        curr = curr.getRight();
      }
      return curr; // return the node before where the new char is placed or before the existing char

    }

    public void addDown(DLBNode curr, String key , int mileageIndex, int priceIndex){
        for(int i =0; i<key.length(); i++){
          curr.setDown( new DLBNode(key.charAt(i)));
          curr = curr.getDown();
        } 
        //add indexes in last node
        curr.setMileageIndex(mileageIndex);
        curr.setPriceIndex(priceIndex);
    }



    /**
     * Check if the dictionary contains a word
     *
     * @param key Word to search the dictionary for
     *
     * @return true if key is in the dictionary, false otherwise
     */
    public boolean contains(String key){
      if(key == null){//null keys
        return false;
      }
      DLBNode curr = root.getDown();
      int i = 0;
      while(i<key.length()){ // loop through length of word
      curr = searchRight(key.charAt(i), curr);
      if(curr.getLet() != key.charAt(i)){ // key is not in trie
        return false;
      }else if(curr.getLet() == key.charAt(i)){
        curr= curr.getDown();
        i++;
      }
      }
      if(curr.getLet() == '^'){
        return true;
      }else{
        return false;
      }
    
    }

    /**
     * Check if a String is a valid prefix to a word in the dictionary
     *
     * @param pre Prefix to search the dictionary for
     *
     * @return true if prefix is valid, false otherwise
     */
    public boolean containsPrefix(String pre){
      int i =0;
      DLBNode curr = root.getDown();
      while(i<pre.length()){ // go to the last char in word, return false if there is a mismatch along the way
        curr = searchRight(pre.charAt(i), curr);
      if(curr.getLet() == pre.charAt(i)){ 
        curr = curr.getDown();
        i++;
      }else if(curr.getLet() != pre.charAt(i)){
        return false;
      }
      }
        return true;
    }

    /**
     * Search for a word one character at a time
     *
     * @param next Next character to search for
     *
     * @return int value indicating result for current by-character search:
     *         -1: not a valid word or prefix
     *         0: valid prefix, but not a valid word
     *         1: valid word, but not a valid prefix to any other words
     *         2: both valid word and a valid prefix to other words
     */
    public int searchByChar(char next){
      word = word + next;

      if( contains(word) == false && containsPrefix(word)== false ){ 
          return -1;
      }else if(containsPrefix(word) == true  && contains(word) == false ){
          return 0;
      }else if(contains(word) == true  && containsPrefix(word) == false){
          return 1; 
      }else if( contains(word) == true && containsPrefix(word) == true ){
          return 2;
      }

      return 3;
      
    }

    /**
     * Reset the state of the current by-character search
     */
    public void resetByChar(){
      word = "";
    }

    /**
     * Suggest up to 5 words from the dictionary based on the current
     * by-character search. Ordering should depend on the implementation.
     * 
     * @return ArrayList<String> List of up to 5 words that are prefixed by
     *         the current by-character search
     */
    public ArrayList<String> suggest(){
      ArrayList<String> suggestions = new ArrayList<>();
      DLBNode curr = root.getDown();
      if(this.word == ""){ // case of no entries into searchByChar
        return suggestions;
      }
      suggestHelper(curr, word, suggestions);
      return suggestions;
    }

    private void suggestHelper(DLBNode curr, String currWord, ArrayList<String> suggestions){
      if(curr == null || suggestions.size() >=5 ){
        return;
      }

      if(curr.getLet() == '^' && currWord.length() > word.length()){ // if its the end of the word and it is bigger than this prefix
        suggestions.add(currWord.substring(word.length(),currWord.length()));
      }

      // add to word untill complete
      suggestHelper(curr.getDown(), currWord + curr.getLet(), suggestions); // go down
      suggestHelper(curr.getRight(), currWord, suggestions); // go right

    }

    /**
     * List all of the words currently stored in the dictionary
     * 
     * @return ArrayList<String> List of all valid words in the dictionary
     */
    public ArrayList<String> traverse(){
      ArrayList<String> words = new ArrayList<>();
      DLBNode curr = root.getDown();
      if(curr == null){
        return words;
      }
      traverseHelper(curr, "",words);
      return words;
    }

    //Traverse helper method: recursively goes through every word in the DLB from root.getDown()
     private void traverseHelper(DLBNode curr, String currWord, ArrayList<String> words){
      if(curr == null){
        return;
      }
       if(curr.getLet() == '^'){ // check whether prefix or word
         words.add(currWord); //if word add to words
        }
        
        traverseHelper(curr.getDown(), currWord + curr.getLet(), words); // go down
        traverseHelper(curr.getRight(), currWord, words); // go right
     }


    /**
     * Count the number of words in the dictionary
     *
     * @return int, the number of (distinct) words in the dictionary
     */
    public int count(){
      return count;
    }

    public boolean remove(String vin) {
    if (vin == null || vin.isEmpty()) {
        return false; // Cannot remove null or empty VIN
    }
    boolean removed = removeHelper(root.getDown(), vin.toCharArray(), 0);
    if (removed) {
        System.out.println("Removed VIN: " + vin); // Print the VIN number being removed
    }
    return removed;
    
}

  private boolean removeHelper(DLBNode node, char[] vinChars, int index) {
    if (node == null) {
        return false; // VIN not found
    }

    if (index == vinChars.length) {
        if (node.getLet() == '^') {
            // If the end of VIN is reached, remove '^' node
            return true;
        }
        return false; // VIN not found
    }

    DLBNode nextNode = node;
    while (nextNode != null && nextNode.getLet() != vinChars[index]) {
        nextNode = nextNode.getRight();
    }

    if (nextNode == null) {
        return false; // VIN not found
    }

    // Recursively remove next character in VIN
    boolean removed = removeHelper(nextNode.getDown(), vinChars, index + 1);

    // If '^' node is found and removed, remove '^' node
    if (removed && nextNode.getDown() == null) {
        if (nextNode.getRight() == null) {
            // If there are no nodes to the right, remove the current node
            node.setRight(null);
        }
    }

    return removed;
}






}