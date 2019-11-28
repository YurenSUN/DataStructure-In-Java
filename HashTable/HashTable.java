/*
 * I used the "Chained" buckets, which are linked nodes (5 CHAINED BUCKET: array of linked nodes)
 * I created LinkedNode inner class for my HashTable class. 
 * It stores the key, value and the next node
 * In the array of hashTable, each element is an instance of LinkedNode
 * For insert, when collision happens, the new node will be linked to the end of the current 
 * nodes with the same index
 * For get and remove, use key and hashFunction to find the index and go through the 
 * linked nodes with the same index to get or remove the node
 */

/**
 * the class represents the hash table
 * @author Yuren Sun
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
  public int capacity; //the capacity of the hash table
  private double loadFactorThreshold; //the load factor that causes a resize and rehash
  private LinkedNode<K, V>[] hashTable; //the hashTable to store values
  private int size; //represent the number of value stored

  /**
   * a default no-arg constructor
   * set initial capacity to 11 and loadFactorTreshold to 0.75
   */
  @SuppressWarnings("unchecked")
  public HashTable() {
    this.capacity = 11;
    this.loadFactorThreshold = 0.75;
    hashTable = new LinkedNode[capacity];
  }

  /**
   * constructor set the initial capacity and load factor threshold as to the param
   * and create a new hashTable instance
   * @param initialCapacity represents the initial capacity of the hashTable
   * @param loadFactorThreshold represents the loadFactorThreshold of hashTable
   */
  @SuppressWarnings("unchecked")
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    this.capacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    hashTable = new LinkedNode[capacity];
  }

  /**
   * Add the key,value pair to the data structure and increase the number of size
   * @param key, the key used to decide the index to store
   * @param value, the value to be stored
   * @throws DuplicateKeyException if key is already in data structure
   * @throws IllegalNullKeyException if key is null
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    
    int index = this.hashFunction(key);
    if (hashTable[index] == null) //add in the hashTable array
      hashTable[index] = new LinkedNode<K,V>(key,value);
    
    else { // add to the end of the linked list if collision
      LinkedNode<K,V> trace = hashTable[index]; //variable used to find the end of the linked list
      if (trace.key.equals(key)) //key exists
        throw new DuplicateKeyException();
      
      while (trace.next != null) { // go to the end to the linked list
        if (trace.key.equals(key)) //key exists
          throw new DuplicateKeyException();
        trace = trace.next;
      }
      trace.next = new LinkedNode<K,V>(key,value);
    }
    
    //increase size and check whether need to rehash
    this.size ++;
    this.checkAndRehash();
  }
    
  
  /**
   * Check whether the load factor threshold is reached.
   * When the load factor threshold is reached, 
   * the capacity must increase to: 2 * capacity + 1
   * Then, the table is resized and elements are rehashed.
   * @throws DuplicateKeyException 
   * @throws IllegalNullKeyException 
   */
  private void checkAndRehash() throws IllegalNullKeyException, DuplicateKeyException {
    if (this.getLoadFactor() >= this.getLoadFactorThreshold()) { 
      //reach, need to rehash, create a new hash table and insert nodes to it
      HashTable<K,V> newTable = new HashTable<K,V>(this.capacity*2 +1, this.loadFactorThreshold);
      for (LinkedNode<K,V> node: hashTable) { //go through the hashTable array
        if (node!= null) {
          newTable.insert(node.key, node.value);
          
          LinkedNode<K,V> current = node; //go through the linked list within same index
          while(current.next != null) {
            current = current.next;
            newTable.insert(current.key, current.value);
          }
        }
      }
      this.hashTable = newTable.hashTable;
      this.capacity = this.capacity*2 +1;
    }
  }
  
  /**
   * If key is found, 
   * remove the key,value pair from the data structure
   * decrease number of keys.
   * return true
   * @param key is the key used to find the node to be remove
   * @return true if key is found and removed, false if key is not found
   * @throws IllegalNullKeyException if key is null
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
   
    int index = hashFunction(key);
    LinkedNode<K,V> node = hashTable[index]; //get the node in the array at the index of key
    if(node != null) {
      if (node.key.equals(key)) { //the node at index match
        if (node.next == null)
          hashTable[index] = null;
        else
          hashTable[index] = node.next;
        this.size --;  //reduce size and return
        return true;
      }else {
        //go through the linked list
        LinkedNode<K,V> current = node;
        LinkedNode<K,V> before;
        while (current.next != null) {
          before = current;
          current = current.next;
          if (current.key.equals(key)) {
            before.next = current.next;             
            this.size --; //reduce size and return
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Returns the value associated with the specified key
   * Does not remove key or decrease number of keys
   * @param the key used to find the value
   * @return the value found
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException if key is not found
   * @return the value associated with the specified key
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    
    LinkedNode<K,V> search = hashTable[hashFunction(key)]; 
    //represents the node in array at index of key
    if(search == null)
      throw new KeyNotFoundException();
    else if(search.key.equals(key)) 
      return search.value;
    else { //go through the whole list
      LinkedNode<K,V> current = search;
      while(current.next != null) {
        current = current.next;
        if (current.key.equals(key))
          return current.value;
      }
      throw new KeyNotFoundException(); //go through the list and not found
    }
  }

  /**
   * Returns the number of key,value pairs in the data structure
   * @return the number of key,value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return this.size;
  }

  /**
   * Returns the load factor threshold that was passed into the constructor when creating 
   * the instance of the HashTable.
   * @return the load factor threshold that was passed into the constructor when creating 
   */
  public double getLoadFactorThreshold() {
    return this.loadFactorThreshold;
  }

  /**
   * Returns the current load factor for this hash table
   * load factor = number of items / current table size 
   * @return the current load factor for this hash table
   */
  @Override
  public double getLoadFactor() {
    return (double)this.size/this.capacity;
  }

  /**
   *  Return the current Capacity (table size) of the hash table array.
   *  The initial capacity must be a positive integer, 1 or greater
   *  and is specified in the constructor.
   *  @return the current Capacity (table size) of the hash table array.
   */
  @Override
  public int getCapacity() {
    return this.capacity;
  }

  /**
   * Returns the collision resolution scheme used for this hash table.
   * Use 5 CHAINED BUCKET: array of linked nodes
   * @return the collision resolution scheme used for this hash table.
   */
  @Override
  public int getCollisionResolution() {
    return 5;
  }
  
  /**
   * the hashFunction for the hashTable
   * @param the key used to find index in the hashTable
   * @return the index in the hashTable
   */
  private int hashFunction(K key) {
    return Math.abs(key.hashCode()) % capacity;
  }
 
  /**
   * inner to for hashTable, represent nodes at each index and linked list
   * @author Yuren Sun
   *
   * @param <K>
   * @param <V>
   */
  @SuppressWarnings("hiding")
  private class LinkedNode<K extends Comparable<K>, V>{
    private K key; //represents the key of the node
    private V value; //represents the value of the node
    private LinkedNode<K,V> next; //represent the next node
    
    /**
     * constructor of linkedNode, set key and value as to the param and next to null
     * @param key
     * @param value
     */
    private LinkedNode(K key, V value) {
      this.key = key;
      this.value = value;
      this.next = null;
    }
  }
  
}
