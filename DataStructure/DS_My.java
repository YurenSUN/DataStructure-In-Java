/**
 * the DataStructure implemented by Yuren Sun
 * @author Yuren Sun
 *
 */
public class DS_My<K extends Comparable<K>, V> implements DataStructureADT<K, V> {
  private DS_My_Node<K, V> root; //the first node
  private int size; //the size of DS_My


  /**
   * constructor of DS_My, initialize root to null and size to 0
   */
  public DS_My() {
    root=null;
    size=0;
  }

  /**
   * Add the key,value pair to the data structure and increases size.
   * If key is null, throws IllegalArgumentException("null key");
   * If key is already in data structure, throws RuntimeException("duplicate key");
   * can accept and insert null values
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void insert(Comparable k, Object v) throws IllegalArgumentException, RuntimeException {
    if (k == null)
      throw new IllegalArgumentException("null key");
    
    if (this.contains(k)) 
      throw new RuntimeException("duplicate key");
    
    if (root == null)
      root = new DS_My_Node(k,v);
    else {
      //add to the end of the list
      DS_My_Node current = root;
      while (current.hasNext()) //to get to the end of the list
        current = current.getNext();
      current.setNext(new DS_My_Node(k,v));
    }
    size ++;
  }

  /**
   * If key is found, Removes the key from the data structure and decrease
   * If key is null, throws IllegalArgumentException("null key") without decreasing size
   * If key is not found, returns false.
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public boolean remove(Comparable k) throws IllegalArgumentException {
    if (k == null)
      throw new IllegalArgumentException("null key");
    if (root == null) 
      return false;
    if(root.getKey().equals(k)) {
      root = root.getNext();
      size--;
      return true;
    }    
    if(root.hasNext()) {
      DS_My_Node current = root.getNext(); //node after that being tested
      DS_My_Node before = root; //the node to be tested
      //go through the list and check
      while (current != null) {
        before = current;
        current = current.getNext();
        if (before.getKey().equals(k)) {
          before.setNext(before.getNext());
          size --;
          return true;
        }
      }
    }    
    return false;
  }

  /**
   * Returns true if the key is in the data structure
   * Returns false if key is null or not present
   */
  @SuppressWarnings({"rawtypes"})
  @Override
  public boolean contains(Comparable k) {
    if(k == null || root == null)
      return false;
    
    //go through the list to check equal
    if(root.getKey().equals(k))
      return true;
    
    DS_My_Node current = root; //the node to be check
    while (current.hasNext()) {
      current = current.getNext();
      if (current.getKey().equals(k))
        return true;
    }
    return false;
  }

  /**
   * Returns the value associated with the specified key
   * If key is null, throws IllegalArgumentException("null key") without decreasing size
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Object get(Comparable k) {
    if (k == null)
      throw new IllegalArgumentException("null key");
    
    if(root == null)
      return null;
    
    //go through the list and check equal
    if(root.getKey().equals(k))
      return root.getValue();
    
    DS_My_Node current = root; //the node to be check
    while (current.hasNext()) {
      current = current.getNext();
      if (current.getKey().equals(k))
        return current.getValue();
    }    
    return null;
  }

  /**
   * Getter for size
   * @return size of DS_My
   */
  @Override
  public int size() {
    return size;
  }
  
  /**
   * Inner class represents nodes for DS_My
   * @author sun
   *
   * @param <K>
   * @param <V>
   */
  @SuppressWarnings("hiding")
  private class DS_My_Node<K extends Comparable<K>, V> {
    private V value; //the value paired with key
    private K key; //the key used for searching and recognizing the node
    private DS_My_Node<K, V> next; //store the next node
    
    /**
     * constructor of DS_My_Node, K can not be null, value can be null 
     * @param key
     * @param value
     * @throws IllegalArgumentException if key is null
     */
    private DS_My_Node(K key, V value) throws IllegalArgumentException{
      if (key == null)
        throw new IllegalArgumentException("null key");
      this.key = key;
      this.value = value;
      this.next = null;
    }
    
    /**
     * Getter for key
     * @return Key
     */
    private K getKey() {
      return this.key;
    }
    
    /**
     * Getter for value
     * @return value
     */
    private V getValue() {
      return this.value;
    }
    
    /**
     * Getter for next node
     * return null if do not have next
     * @return next
     */
    private DS_My_Node<K, V> getNext() {
      return next;
    }
    
    /**
     * Setter for next node
     * @param next node to be set
     */
    private void setNext(DS_My_Node<K, V> next) {
      this.next = next;
    }
    
    /**
     * return true if has next, false otherwise
     */
    private boolean hasNext() {
      if(next == null)
        return false;
      return true;
    }
  }

}
