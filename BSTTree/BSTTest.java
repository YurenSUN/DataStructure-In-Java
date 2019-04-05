////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p2
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: Feb 21
// Other source credits: N/A
/////////////////////////////////////////////////

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class for BST
 * 
 * @author Yuren Sun
 *
 */
public class BSTTest extends DataStructureADTTest {

  BST<String, String> bst; // represent the first bst use to be tested
  BST<Integer, String> bst2; // represent the second bst use to be tested

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    // The setup must initialize this class's instances
    // and the super class instances.
    // Because of the inheritance between the interfaces and classes,
    // we can do this by calling createInstance() and casting to the desired type
    // and assigning that same object reference to the super-class fields.
    dataStructureInstance = bst = createInstance();
    dataStructureInstance2 = bst2 = createInstance2();    
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = bst = null;
    dataStructureInstance2 = bst2 = null;
  }

  /**
   * create the instance of first bst tree
   */
  @Override
  protected BST<String, String> createInstance() {
    return new BST<String, String>();
  }

  /**
   * create the instance for second bst tree
   */
  @Override
  protected BST<Integer, String> createInstance2() {
    return new BST<Integer, String>();
  }

  /**
   * Test that empty trees still produce a valid but empty traversal list for each of the four
   * traversal orders.
   */
  @Test
  void testBST_001_empty_traversal_orders() {
    try {

      List<String> expectedOrder = new ArrayList<String>();

      // Get the actual traversal order lists for each type
      List<String> inOrder = bst.getInOrderTraversal();
      List<String> preOrder = bst.getPreOrderTraversal();
      List<String> postOrder = bst.getPostOrderTraversal();
      List<String> levelOrder = bst.getLevelOrderTraversal();

      // // UNCOMMENT IF DEBUGGING THIS TEST
      // System.out.println(" EXPECTED: "+expectedOrder);
      // System.out.println(" In Order: "+inOrder);
      // System.out.println(" Pre Order: "+preOrder);
      // System.out.println(" Post Order: "+postOrder);
      // System.out.println("Level Order: "+levelOrder);

      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 002: " + e.getMessage());
    }

  }

  /**
   * Test that trees with one key,value pair produce a valid traversal lists for each of the four
   * traversal orders.
   */
  @Test
  void testBST_002_check_traversals_after_insert_one() {

    try {

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      bst2.insert(10, "ten");
      if (bst2.numKeys() != 1)
        fail("added 10, size should be 1, but was " + bst2.numKeys());

      List<Integer> inOrder = bst2.getInOrderTraversal();
      List<Integer> preOrder = bst2.getPreOrderTraversal();
      List<Integer> postOrder = bst2.getPostOrderTraversal();
      List<Integer> levelOrder = bst2.getLevelOrderTraversal();

      // // UNCOMMENT IF DEBUGGING THIS TEST
      // System.out.println(" EXPECTED: "+expectedOrder);
      // System.out.println(" In Order: "+inOrder);
      // System.out.println(" Pre Order: "+preOrder);
      // System.out.println(" Post Order: "+postOrder);
      // System.out.println("Level Order: "+levelOrder);

      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 003: " + e.getMessage());
    }

  }

  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 In-Order traversal order: 10-20-30
   */
  @Test
  void testBST_003_check_inOrder_for_balanced_insert_order() {
    // insert 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(20); // V
      expectedOrder.add(30); // R

      // GET IN-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Pre-Order traversal order: 20-10-30
   */
  @Test
  void testBST_004_check_preOrder_for_balanced_insert_order() {
    // insert 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20); // V
      expectedOrder.add(10); // L
      expectedOrder.add(30); // R

      // GET pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Post-Order traversal order: 10-30-20
   */
  @Test
  void testBST_005_check_postOrder_for_balanced_insert_order() {
    // insert 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 10-30-20
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10); // L
      expectedOrder.add(30); // R
      expectedOrder.add(20); // V

      // GET post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Level-Order traversal order: 20-10-30
   */
  @Test
  void testBST_006_check_levelOrder_for_balanced_insert_order() {
    // insert 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 20-10-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET level-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 In-Order traversal order: 10-20-30
   */
  @Test
  void testBST_007_check_inOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 10-20-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET in-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Pre-Order traversal order: 10-20-30
   */
  @Test
  void testBST_008_check_preOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 10-20-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Post-Order traversal order: 30-20-10
   */
  @Test
  void testBST_009_check_postOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 30-20-10
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(30);
      expectedOrder.add(20);
      expectedOrder.add(10);

      // GET post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Level-Order traversal order: 10-20-30 (FIXED ON 2/14/18)
   */
  @Test
  void testBST_010_check_levelOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 10-20-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET level-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test insert null key, should throw IllegalNullKeyException
   */
  @Test
  void testBST_011_insert_null_key_IllegalNullKeyException() {
    try {
      bst2.insert(null, "1st key inserted");
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test insert duplicate key, should throw DuplicateKeyException
   */
  @Test
  void testBST_012_insert_duplicate_key_DuplicateKeyException() {
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(10, "2st key inserted"); // duplicated
      fail("does not catch DuplicateKeyException");
    } catch (DuplicateKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test remove null key, should throw IllegalNullKeyException
   */
  @Test
  void testBST_013_remove_null_key_IllegalNullKeyException() {
    try {
      bst2.remove(null);
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test remove non-exist key, should throw KeyNotFoundException
   */
  @Test
  void testBST_014_remove_nonexist_key_KeyNotFoundException() {
    try {
      bst2.remove(10); // nonexist
      fail("does not catch KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * insert 5 remove 3, test remove method, UnBalanced
   */
  @Test
  void testBST_015_remove_3_from_5_Unbalanced() {
    try {
      // insert 5 nodes
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(40, "4st key inserted");
      bst2.insert(50, "5nd key inserted");

      // check the numKeys first
      if (bst2.numKeys != 5)
        fail("fail to count numKeys correctly");

      // remove 3, root, middle, end, check numKeys and contains
      bst2.remove(10);
      if (bst2.numKeys != 4 || bst2.contains(10))
        fail("fail to remove 10");

      bst2.remove(30);
      if (bst2.numKeys != 3 || bst2.contains(30))
        fail("fail to remove 30");

      bst2.remove(50);
      if (bst2.numKeys != 2 || bst2.contains(50))
        fail("fail to remove 50");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * remove 4 from 6, balanced
   */
  @Test
  void testBST_016_remove_4_from_6_Balanced() {
    try {
      // insert 6 nodes
      bst2.insert(30, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(40, "3rd key inserted");
      bst2.insert(10, "4st key inserted");
      bst2.insert(50, "5nd key inserted");
      bst2.insert(15, "6nd key inserted");

      // check the numKeys first
      if (bst2.numKeys != 6)
        fail("fail to count numKeys correctly");

      // remove 4, root, leaf, with one/two child, check numKeys and contains
      // than check the levelOrderTraversal to make sure that place the predecessor correctly
      bst2.remove(20); // only has left child
      if (bst2.numKeys != 5 || bst2.contains(20))
        fail("fail to remove 10");
      if (!bst2.getLevelOrderTraversal().toString().equals("[30, 10, 40, 15, 50]"))
        fail("remove incorrectly");

      bst2.remove(30); // have both child, root
      System.out.println(bst2.numKeys);
      System.out.println(bst2.contains(30) );
      if (bst2.numKeys != 4 || bst2.contains(30))
        fail("fail to remove 30");
      if (!bst2.getLevelOrderTraversal().toString().equals("[15, 10, 40, 50]"))
        fail("remove incorrectly");

      bst2.remove(40); // have both child
      if (bst2.numKeys != 3 || bst2.contains(40))
        fail("fail to remove 30");
      if (!bst2.getLevelOrderTraversal().toString().equals("[15, 10, 50]"))
        fail("remove incorrectly");

      bst2.remove(50); // leaf
      if (bst2.numKeys != 2 || bst2.contains(50))
        fail("fail to remove 50");
      if (!bst2.getLevelOrderTraversal().toString().equals("[15, 10]"))
        fail("remove incorrectly");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get null to test whether IllegalNullKeyException is thrown
   */
  @Test
  void testBST_017_get_null_IllegalNullKeyException() {
    try {
      bst2.get(null);
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get non-exist key to check whether KeyNotFoundException is thrown
   */
  @Test
  void testBST_018_get_nonexist_key_KeyNotFoundException() {
    try {
      bst2.get(10); // nonexist
      fail("does not catch KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get 4 key after inserting 6 keys to test the get method and test whether remove when
   * getting
   */
  @Test
  void testBST_019_get_4_from_6() {
    try {
      // insert 6 nodes
      bst2.insert(30, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(40, "3rd key inserted");
      bst2.insert(10, "4nd key inserted");
      bst2.insert(50, "5nd key inserted");
      bst2.insert(25, "6nd key inserted");

      // try to get 4 key, root, middle, right end, left end
      if (!bst2.get(30).equals("1st key inserted"))
        fail("fail to get correct value from a specific key");
      if (!bst2.get(20).equals("2nd key inserted"))
        fail("fail to get correct value from a specific key");
      if (!bst2.get(10).equals("4nd key inserted"))
        fail("fail to get correct value from a specific key");
      if (!bst2.get(50).equals("5nd key inserted"))
        fail("fail to get correct value from a specific key");

      // check whether remove when getting, should have 6 nodes
      if (bst2.numKeys != 6)
        fail("remove when getting");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test contain null key to test whether IllegalNullKeyException is thrown
   * 
   */
  @Test
  void testBST_020_contains_nul_IllegalNulKeyException() {
    try {
      bst2.contains(null);
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get key at null when the root is null, should return null
   */
  @Test
  void testBST_021_getKeyAtRoot_null_root() {
    try {
      if (bst2.getKeyAtRoot() != null)
        fail("fail to return null when getting key at null root");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test getKeyAtRoot method
   */
  @Test
  void testBST_022_get_key_at_root() {
    try {
      bst2.insert(10, "1st key inserted");
      if (!bst2.getKeyAtRoot().equals(10))
        fail("fail to return null when getting key at null root");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * insert 6 keys and test getKeyOfLeftChildOf method
   */
  @Test
  void testBST_023_getKeyOfLeftChildOf() {
    try {
      // insert 6 nodes
      bst2.insert(30, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(40, "3rd key inserted");
      bst2.insert(10, "4nd key inserted");
      bst2.insert(50, "5nd key inserted");
      bst2.insert(25, "6nd key inserted");

      // try to get 4 key, root, middle, right end, left end
      if (!bst2.getKeyOfLeftChildOf(30).equals(20))
        fail("fail to get correct value from the left child of a specific key");
      if (!bst2.getKeyOfLeftChildOf(20).equals(10))
        fail("fail to get correct value from the left child of a specific key");
      if (bst2.getKeyOfLeftChildOf(40) != null)
        fail("fail to get correct value from the left child of a specific key");
      if (bst2.getKeyOfLeftChildOf(25) != null)
        fail("fail to get correct value from the left child of a specific key");

      // check whether remove when getting, should have 6 nodes
      if (bst2.numKeys != 6)
        fail("remove when getting");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * insert 6 keys and test getKeyOfRightChildOf method
   */
  @Test
  void testBST_024_getKeyOfRightChildOf() {
    try {
      // insert 6 nodes
      bst2.insert(30, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(40, "3rd key inserted");
      bst2.insert(10, "4nd key inserted");
      bst2.insert(50, "5nd key inserted");
      bst2.insert(25, "6nd key inserted");

      // try to get 4 key, root, middle, right end, left end
      if (!bst2.getKeyOfRightChildOf(30).equals(40))
        fail("fail to get correct value from the right child of a specific key");
      if (!bst2.getKeyOfRightChildOf(20).equals(25))
        fail("fail to get correct value from the right child of a specific key");
      if (!bst2.getKeyOfRightChildOf(40).equals(50))
        fail("fail to get correct value from the right child of a specific key");
      if (bst2.getKeyOfRightChildOf(25) != null)
        fail("fail to get correct value from the right child of a specific key");

      // check whether remove when getting, should have 6 nodes
      if (bst2.numKeys != 6)
        fail("remove when getting");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test getKeyOfLeftChildOf null key to test whether IllegalNullKeyException is thrown
   * 
   */
  @Test
  void testBST_025_getKeyOfLeftChildOf_nul_IllegalNulKeyException() {
    try {
      bst2.getKeyOfLeftChildOf(null);
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test getKeyOfLeftChildOf null key to test whether IllegalNullKeyException is thrown
   * 
   */
  @Test
  void testBST_026_getKeyOfRightChildOf_nul_IllegalNulKeyException() {
    try {
      bst2.getKeyOfRightChildOf(null);
      fail("does not catch IllegalNullKeyException");
    } catch (IllegalNullKeyException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get getKeyOfLeftChildOf key to check whether KeyNotFoundException is thrown
   */
  @Test
  void testBST_027_get_getKeyOfLeftChildOf_key_KeyNotFoundException() {
    try {
      bst2.getKeyOfLeftChildOf(10); // nonexist
      fail("does not catch KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get getKeyOfRightChildOf key to check whether KeyNotFoundException is thrown
   */
  @Test
  void testBST_028_get_getKeyOfRightChildOf_key_KeyNotFoundException() {
    try {
      bst2.getKeyOfRightChildOf(10); // nonexist
      fail("does not catch KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // pass
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test whether return the right height, insert 6 nodes, unbalanced
   */
  @Test
  void testBST_029_getHeight_unbalanced() {
    try {
      // insert 6 nodes, test getHeight after each insert
      // height add 1 when insert each
      bst2.insert(10, "1st key inserted");
      if (bst2.getHeight() != 1)
        fail("can not get the correct height");

      bst2.insert(20, "2nd key inserted");
      if (bst2.getHeight() != 2)
        fail("can not get the correct height");

      bst2.insert(30, "3rd key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

      bst2.insert(40, "4nd key inserted");
      if (bst2.getHeight() != 4)
        fail("can not get the correct height");

      bst2.insert(50, "5nd key inserted");
      if (bst2.getHeight() != 5)
        fail("can not get the correct height");

      bst2.insert(60, "6nd key inserted");
      if (bst2.getHeight() != 6)
        fail("can not get the correct height");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test whether return the right height, insert 6 nodes, balanced
   */
  @Test
  void testBST_030_getHeight_balanced() {
    try {
      // insert 6 nodes, test getHeight after each insert
      bst2.insert(30, "1st key inserted");
      if (bst2.getHeight() != 1)
        fail("can not get the correct height");

      bst2.insert(20, "2nd key inserted");
      if (bst2.getHeight() != 2)
        fail("can not get the correct height");

      bst2.insert(40, "3rd key inserted");
      if (bst2.getHeight() != 2)
        fail("can not get the correct height");

      bst2.insert(25, "4nd key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

      bst2.insert(50, "5nd key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

      bst2.insert(10, "6nd key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * test get height on bst with null root, should return 0
   */
  @Test
  void testBST_031_getHeight_null_root() {
    try {
      if (bst2.getHeight() != 0) // null root
        fail("can not get the correct height of bst with null root");
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

}
