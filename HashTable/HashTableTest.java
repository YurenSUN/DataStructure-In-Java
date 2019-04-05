////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p3b
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: March 28
// Other source credits: N/A
//  Bugs: none
/////////////////////////////////////////////////

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * the test class for HashTable
 * @author Yuren Sun
 *
 */
public class HashTableTest {
  HashTable<Integer, String> hashTable; //hash table used for tests

  /**
   * run before every test
   * initialize the hashTable with capacity of 10 and load factor threshold to 0.7
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    hashTable = new HashTable<Integer, String>(10, 0.7);
  }

  /**
   * run after every test
   * set hashTable to null
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
    hashTable = null;
  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    int scheme = hashTable.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * insert(null,null) should throws IllegalNullKeyException
   */
  @Test
  public void test001_insert_IllegalNullKey() {
    try {
      hashTable.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ 
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }

  /**
   * insert duplicated key, should throws DuplicateKeyException
   */
  @Test
  public void test002_insert_DuplicateKeyException() {
    try {
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(1, "3");
      fail("should not be able to insert duplicated key");
    } catch (DuplicateKeyException e) {
    /* expected */ 
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * insert different keys and check get each time
   */
  @Test
  public void test003_insert_check() {
    try {
      //insert 6 node and check get each time to see whether insert correctly
      hashTable.insert(1, "1");
      if (hashTable.get(1) != "1")
        fail("fail to insert");
      
      hashTable.insert(2, "2");
      if (hashTable.get(2) != "2")
        fail("fail to insert");
      
      hashTable.insert(3, "3");
      if (hashTable.get(3) != "3")
        fail("fail to insert");
      
      hashTable.insert(4, "4");
      if (hashTable.get(4) != "4")
        fail("fail to insert");
      
      hashTable.insert(5, "5");
      if (hashTable.get(5) != "5")
        fail("fail to insert");
      
      hashTable.insert(6, "6");
      if (hashTable.get(6) != "6")
        fail("fail to insert");
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * test whether the hash table rehash after the load factor threshold is reached
   */
  @Test
  public void test004_insert_rehash() {
    try {
      //insert 7 nodes
      hashTable.insert(1, "1");
      hashTable.insert(2, "2");
      hashTable.insert(3, "3");
      hashTable.insert(4, "4");
      hashTable.insert(5, "5");
      hashTable.insert(6, "6");
      hashTable.insert(7, "7"); //should rehash here
      if (hashTable.getCapacity() != 21)
        fail("did not rehash when load factor threshold is reached");

      hashTable.insert(8, "8");
      hashTable.insert(9, "9");
      hashTable.insert(10, "10");
      hashTable.insert(11, "11");
      if (hashTable.numKeys() != 11)
        fail("fail in insertion");
      
      for (int i = 1; i<12; i ++) { //check get for each node to see whether rehash correctly
        String correctGet = Integer.toString(i);
        if(!hashTable.get(i).equals(correctGet)) 
          fail("did not rehash correctly");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * insert keys that cause collision to check whether insert successfully
   */
  @Test
  public void test005_insert_collision() {
    try { //insert pairs with hashFunction return 0 and cause rehash
      hashTable.insert(10, "1");
      hashTable.insert(20, "2");
      hashTable.insert(30, "3");
      hashTable.insert(40, "4");     
      hashTable.insert(50, "5");     
      hashTable.insert(60, "6");     
      hashTable.insert(70, "7");
      
      for (int i = 1; i<8; i++) { //check whether every pair is in the table after rehash
        String value = Integer.toString(i);
        if (!hashTable.get(i*10).equals(value)) 
          fail("fail to insert at: i= " + i);  
      }
           
      if (hashTable.numKeys() != 7)
        fail("fail to insert with collision and rehash");
      
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * insert 500 items, check each time and check capacity
   */
  @Test
  public void test006_insert_500() {
    try {
      for (int i = 0; i < 500; i++) { //insert 500 items, capacity should be 1407 after rehash
        String value = Integer.toString(i);
        hashTable.insert(i, value);  
      }
      if (hashTable.getCapacity() != 1407)
        fail("fail to rehash");
      
      for (int i =0; i<500; i++) { //check whether every pair is in the table
        String value = Integer.toString(i);
        if (!hashTable.get(i).equals(value)) {
          System.out.println(i);
          fail("fail to insert at: i= " + i);  
        }
      }    
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }

  /**
   * remove null key, should throw IllegalNullKeyException
   */
  @Test
  public void test007_remove_null() {
    try {
      hashTable.insert(1, "1");
      hashTable.remove(null);
      fail("should not be able to remove null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ 
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * remove non-exist key, should return false
   */
  @Test
  public void test008_remove_nonexist_key() {
    try {
      hashTable.insert(1, "1");
      if (hashTable.remove(2)) //should return false
        fail("should return false when removing nonexist key");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * remove some after insert some, check the sizes 
   * and insert again, should not throw DuplicateKeyException
   */
  @Test
  public void test009_remove_some() {
    try {
      //add 6 pairs, not cause rehashing to make sure test whole remove method
      hashTable.insert(1, "1"); //2 normal nodes
      hashTable.insert(2, "2");
      hashTable.insert(10, "1");//4 node with collision
      hashTable.insert(20, "2");
      hashTable.insert(30, "3");
      hashTable.insert(40, "4");
      
      //remove and check size          
      hashTable.remove(1);
      if (hashTable.numKeys() != 5)
        fail("fail to remove");
        
      hashTable.remove(2);
      if (hashTable.numKeys() != 4)
        fail("fail to remove");
        
      hashTable.remove(40); //end of linked list
      if (hashTable.numKeys() != 3)
        fail("fail to remove");
        
      hashTable.remove(20); //middle of linked list
      if (hashTable.numKeys() != 2)
        fail("fail to remove");
        
      hashTable.remove(10); //start of linked list
      if (hashTable.numKeys() != 1)
        fail("fail to remove");
        
      hashTable.remove(30);
      if (hashTable.numKeys() != 0)
        fail("fail to remove");  

      //add again, should not cause DuplicatKeyException
      hashTable.insert(1, "1"); //2 normal nodes
      hashTable.insert(2, "2");
      hashTable.insert(10, "1"); //4 node with collision
      hashTable.insert(20, "2");
      hashTable.insert(30, "3");
      hashTable.insert(40, "4");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * test insert 500 pairs, then remove them and insert again, 
   * no DuplicateKeyException should be thrown
   */
  @Test
  public void test010_insert500_remove500_inser500() {
    try {
      for (int i = 0; i < 500; i++) { //insert 500 nodes
        String value = Integer.toString(i);
        hashTable.insert(i*10, value);  
      }
      
      for (int i = 499; i >= 0; i--) { //remove 500 nodes inserted
        hashTable.remove(i*10);  
      }
      
      for (int i = 0; i < 500; i++) { //insert again, should not cause DuplicateKeyException
        String value = Integer.toString(i);
        hashTable.insert(i*11, value);  
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }

  /**
   * get null key, should return IllegalNullKeyException
   */
  @Test
  public void test011_get_null() {
    try {
      hashTable.insert(1, "1");
      hashTable.get(null);
      fail("should not be able to get null key");
    } catch (IllegalNullKeyException e) {
      /* expected */ 
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * get non-exist key, should return KeyNotFoundException
   */
  @Test
  public void test012_get_nonexist_key() {
    try {
      hashTable.insert(1, "1");
      hashTable.get(2);
      fail("should not be able to get non-exist key");
    } catch (KeyNotFoundException e) {
      /* expected */ 
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  }
  
  /**
   * get some after insert some, check the sizes 
   * and insert again, should not throw DuplicateKeyException
   */
  @Test
  public void test013_remove_some() {
    try {
      //add 6 pairs, not cause rehashing to make sure test whole get method
      hashTable.insert(1, "1"); //2 normal nodes
      hashTable.insert(2, "2");
      hashTable.insert(10, "1");//4 node with collision
      hashTable.insert(20, "2");
      hashTable.insert(30, "3");
      hashTable.insert(40, "4");
      
      if (!hashTable.get(1).equals("1"))
        fail("do not get correctly");
      
      if (!hashTable.get(2).equals("2"))
        fail("do not get correctly");
      
      if (!hashTable.get(10).equals("1")) //start of linked list
        fail("do not get correctly");
      
      if (!hashTable.get(20).equals("2")) //middle of linked list
        fail("do not get correctly");
      
      if (!hashTable.get(30).equals("3")) //middle of linked list
        fail("do not get correctly");
      
      if (!hashTable.get(40).equals("4")) //end of linked list
        fail("do not get correctly");
      
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  } 
  
  /**
   * test insert 500 pairs, then get them, 
   */
  @Test
  public void test014_insert500_get500() {
    try {
      for (int i = 0; i < 500; i++) { //insert 500 nodes
        String value = Integer.toString(i);
        hashTable.insert(i*10, value);  
      }
      
      for (int i = 0; i < 500; i++) { //get 500 and test
        String value = Integer.toString(i);
        if (!hashTable.get(i*10).equals(value)) 
          fail("fail to get correctly");
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception occur");
    }
  } 
}
