
// Course:                   CS400 Lecture 001
// Assignment Name           p1
// Author:                   Yuren Sun
// Email:                    ysun299@wisc.edu
// Due date:                 Feb 7 
// Other source credits:     N/A


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * the DataStructureADTTest to test all the methods in all the data structures
 * @author Yuren Sun
 *
 * @param <T>
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T dataStructureInstance; //the data dataStructure constructed for tests

  protected abstract T createInstance(); //create new data structure

  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @BeforeEach
  void setUp() throws Exception {
    dataStructureInstance = createInstance();
  }

  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = null;
  }

  /**
   * test the size for empty structure
   */
  @Test
  void test00_empty_ds_size() {
    if (dataStructureInstance.size() != 0)
      fail("data structure should be empty, with size=0, but size=" + dataStructureInstance.size());
  }

  /**
   * insert one key value pair into the data structure and then confirm that size() is 1.
   */
  @Test
  void test01_after_insert_one_size_is_one() {
    try{
      //insert and check size
      dataStructureInstance.insert(new String("p1Test01Key"), "p1Test01Value");
    if (dataStructureInstance.size() != 1)
      fail("data structure should have size with 1, but size =" + +dataStructureInstance.size());
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  } 

  /**
   * insert one key,value pair and remove it, then confirm size is 0.
   */
  @Test
  void test02_after_insert_one_remove_one_size_is_0() {
    try{
      //insert, check size (should be 1)
      dataStructureInstance.insert(new String("p1Test02Key"), "p1Test02Value");
    if (dataStructureInstance.size() != 1)
      fail("data structure should have size with 1, but size =" + +dataStructureInstance.size());

    //remove, check size(should be 0)
    dataStructureInstance.remove("p1Test02Key");
    if (dataStructureInstance.size() != 0)
      fail("data structure should be empty, with size=0, but size=" + dataStructureInstance.size());
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }

  /**
   * insert a few key,value pairs such that one of them has the same key as an earlier one. Confirm
   * that a RuntimeException is thrown.
   */
  @Test
  void test03_duplicate_exception_is_thrown() {
    try { 
      //insert 5 object and try duplicate
      dataStructureInstance.insert(new String("p1Test03Key1"), "p1Test03Value1");
      dataStructureInstance.insert(new String("p1Test03Key2"), "p1Test03Value2");
      dataStructureInstance.insert(new String("p1Test03Key3"), "p1Test03Value3");
      dataStructureInstance.insert(new String("p1Test03Key4"), "p1Test03Value4");
      dataStructureInstance.insert(new String("p1Test03Key5"), "p1Test03Value5");
   
      //try 3 duplicate insert independently   (root, middle, end)
      dataStructureInstance.insert(new String("p1Test03Key1"), "p1Test03Value1");
    } catch (RuntimeException e) {
      if (!e.getMessage().equals("duplicate key"))
        fail("Wrong message thrown with RuntimeException, should be: duplicate key, but shows: "
            + e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
    
    try {
      dataStructureInstance.insert(new String("p1Test03Key3"), "p1Test03Value3");
      fail("Did not catch RuntimeException");
    } catch (RuntimeException e) {
      if (!e.getMessage().equals("duplicate key"))
        fail("Wrong message thrown with RuntimeException, should be: duplicate key, but shows: "
            + e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
    
    try {
      dataStructureInstance.insert(new String("p1Test03Key5"), "p1Test03Value5");
      fail("Did not catch RuntimeException");
    } catch (RuntimeException e) {
      if (!e.getMessage().equals("duplicate key"))
        fail("Wrong message thrown with RuntimeException, should be: duplicate key, but shows: "
            + e.getMessage());
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

  /**
   * insert some key,value pairs, then try removing a key that was not inserted. 
   * Confirm that the return value is false.
   */
  @Test
  void test04_remove_returns_false_wrong_key() {
   try {
     //insert 2 objects and remove one not exist
     dataStructureInstance.insert(new String("p1Test04Key1"), "p1Test04Value1");
     dataStructureInstance.insert(new String("p1Test04Key2"), "p1Test04Value2");
    
     if(dataStructureInstance.remove("p1Test04Key3")) //key not exist
        fail("remove should return false, but return true when remove a key that was not inserted");
    }catch(Exception e) {
      fail("Unexcepted Exception occurs");
    }
  }
  
  /**
   * remove with key is null and check whether IllegalArgumentException("null key") is thrown
   */
  @Test
   void test05_remove_null_exception_thrown() {
     try {
       //should throw IllegalArgumentException
       dataStructureInstance.remove(null);
       fail("Did not catch IllegalArgumentException");
     }catch (IllegalArgumentException e){
       if(!e.getMessage().equals("null key"))
         fail("Wrong message thrown with IllegalArgumentException, "
             + "should be: null key, but shows: "
             + e.getMessage());
     }catch(Exception e) {
       fail("Unexpected Exception occurs");
     }
   }

  /**
   * insert and remove several times to test the methods
   */
  @Test
  void test06_insert_five_remove_four() {
    //insert four and check the size
    try{
      dataStructureInstance.insert(new String("p1Test06Key1"), "p1Test06Value1");
      dataStructureInstance.insert(new String("p1Test06Key2"), "p1Test06Value2");    
      dataStructureInstance.insert(new String("p1Test06Key3"), "p1Test06Value3");
      dataStructureInstance.insert(new String("p1Test06Key4"), "p1Test06Value4");
      if(dataStructureInstance.size()!=4)
        fail("data structure has size of 4, but size=" + dataStructureInstance.size());
      
      //remove three and check the size each time (root, middle, end)
      dataStructureInstance.remove("p1Test06Key1");
      if(dataStructureInstance.size()!=3)
        fail("data structure has size of 3, but size=" + dataStructureInstance.size());
      
      dataStructureInstance.remove("p1Test06Key3");
      if(dataStructureInstance.size()!=2)
        fail("data structure has size of 2, but size=" + dataStructureInstance.size());
      
      dataStructureInstance.remove("p1Test06Key4");
      if(dataStructureInstance.size()!=1)
        fail("data structure has size of 1, but size=" + dataStructureInstance.size());
     
      //insert one and remove it, check the size each time
      dataStructureInstance.insert(new String("p1Test06Key5"), "p1Test06Value5");
      if(dataStructureInstance.size()!=2)
        fail("data structure has size of 2, but size=" + dataStructureInstance.size());
      
      dataStructureInstance.remove("p1Test06Key5");
      if(dataStructureInstance.size()!=1)
        fail("data structure has size of 1, but size=" + dataStructureInstance.size());
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether IllegalArgumentException is thrown if insert a null key
   */
  @Test
  void test07_insert_null_exception_thrown(){
    try {
      dataStructureInstance.insert(null, "pqTest07Value1"); //null, throw IllegalArgumentException
      fail("Should throw IllegalArgumentException with null key ");
    }catch(IllegalArgumentException e){
      if(!e.getMessage().equals("null key"))
        fail("Wrong message thrown with IllegalArgumentException, "
            + "should be: null key, but shows: "
            + e.getMessage());
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether the remove from null structure will not cause exception and return false
   */
  @Test
  void test08_remove_from_null_structure() {
    try {
      if(dataStructureInstance.remove("p1Test08Key1")) //the structure is empty
        fail("remove from null structure but return true");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether the get method throws IllegalArgumentException("null key")
   */
  @Test
  void test09_get_null_exception_thrown() {
    try {
      dataStructureInstance.get(null); //null, throw IllegalArgumentException
      fail("Get null key does not return IllegalArgumentExceptio");
    }catch(IllegalArgumentException e){
      if(!e.getMessage().equals("null key"))
        fail("Wrong message thrown with IllegalArgumentException, "
            + "should be: null key, but shows: "
            + e.getMessage());
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether the get method return null and 
   * do not occur exception when get in a empty structure
   */
  @Test
  void test10_get_from_empty() {
    try {
      //the structure is empty, return null
      if(!(dataStructureInstance.get("p1test10Key1") == null))
        fail("did not return null when get object from empty structure");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }

  /**
   * insert keys and test whether get method works
   */
  @Test
  void test11_get_test_correct() {
    try {
      //insert 3 keys, get them and check
      dataStructureInstance.insert(new String("p1Test11Key1"), "p1Test11Value1");
      dataStructureInstance.insert(new String("p1Test11Key2"), "p1Test11Value2");    
      dataStructureInstance.insert(new String("p1Test11Key3"), "p1Test11Value3");
      if(!(dataStructureInstance.get("p1Test11Key1").equals("p1Test11Value1") && 
           dataStructureInstance.get("p1Test11Key2").equals("p1Test11Value2") &&
           dataStructureInstance.get("p1Test11Key3").equals("p1Test11Value3")))
          fail("fail to get correct values");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether get method return null when key not exists
   */
  @Test
  void test12_get_from_null_structure() {
    try {
      //empty structure, return null
      if(!(dataStructureInstance.get("p1Test13Key1") == null))
        fail("did not return null when key does not exist");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether contains method do not cause exception and return false when key is null
   */
  @Test
  void test13_get_wrong_key() {
    try {
      //insert 3 keys and get wrong key
      dataStructureInstance.insert(new String("p1Test13Key1"), "p1Test13Value1");
      dataStructureInstance.insert(new String("p1Test13Key2"), "p1Test13Value2");    
      dataStructureInstance.insert(new String("p1Test13Key3"), "p1Test13Value3");
      if(!(dataStructureInstance.get("p1test13Key4") == null)) //wrong key, return null
        fail("did not return null with wrong key");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether contains method return true when key is in the structure
   */
  @Test
  void test_14_contains_test_correct() {
    try {
      //insert three key and check whether contains each 
      dataStructureInstance.insert(new String("p1Test11Key1"), "p1Test11Value1");
      dataStructureInstance.insert(new String("p1Test11Key2"), "p1Test11Value2");    
      dataStructureInstance.insert(new String("p1Test11Key3"), "p1Test11Value3");
      
      if(!(dataStructureInstance.contains("p1Test11Key1") && //should all be true
           dataStructureInstance.contains("p1Test11Key2") &&
           dataStructureInstance.contains("p1Test11Key3")))
          fail("fail to get correct values");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether contains method return false when key is not found
   * contains from null structure, contains wrong key, and contains null key,
   * all be false, no exception
   */
  @Test
  void test_15_contains_wrong_key() {
    try {
      if(dataStructureInstance.contains("p1Test15Key1")) //null structure, no exception
        fail("return true when key is wrong when using contains method");
      
      dataStructureInstance.insert(new String("p1Test15Key1"), "p1Test15Value1");
      dataStructureInstance.insert(new String("p1Test15Key2"), "p1Test15Value2");    
      dataStructureInstance.insert(new String("p1Test15Key3"), "p1Test15Value3");
      
      if(dataStructureInstance.contains("p1Test15Key4")) //wrong key, no exception
        fail("return true when key is wrong when using contains method");
      
      if(dataStructureInstance.contains(null)) //wrong key, no exception
        fail("return true when key is wrong when using contains method");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether can insert,remove,and insert again(not duplicate)
   */
  @Test
  void test16_insert_remove_insert() {
    try {
      //insert and remove the same one, than insert again to check whether not duplicate
      dataStructureInstance.insert(new String("p1Test16Key1"), "p1Test16Value1");
      dataStructureInstance.remove("p1Test16Key1");    
      dataStructureInstance.insert(new String("p1Test16Key1"), "p1Test16Value1");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * test whether can store 500 objects and remove, check size
   */
  @Test
  void test17_500_object() {
    try {
      //insert 500 object and check size each time
      for(int i =0; i<500; i++) {
        //set key to i and value to i+1
        String k = Integer.toString(i);
        String v = Integer.toString(i+1);
        dataStructureInstance.insert(new String(k), v);
        if(dataStructureInstance.size()!= i+1)
          fail("Wrong size");       
      }
      
    //remove 500 object and check size each time
      for(int i =0; i<500; i++) {
        String k = Integer.toString(i);
        dataStructureInstance.remove(k);
        if(dataStructureInstance.size()!= (499-i))
          fail("Wrong size");       
      }            
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
  
  /**
   * try method contains for null key,should return false;
   */
  void test18_contains_null_key() {
    try {
      //null key, return false
      if(dataStructureInstance.contains(null))
        fail("did not return null when key does not exist");
    }catch(Exception e) {
      fail("Unexpected Exception occurs");
    }
  }
}
