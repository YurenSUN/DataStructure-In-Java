////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p2
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: Feb 21
// Other source credits: N/A
/////////////////////////////////////////////////

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Assert;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * test class for AVL, extends BSTTest override some tests in BSTTest
 * 
 * @author Yuren Sun
 *
 */
public class AVLTest extends BSTTest {

  AVL<String, String> avl; // represent the first instance of avl
  AVL<Integer, String> avl2; // represent the second instance of avl

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    dataStructureInstance = bst = avl = createInstance();
    dataStructureInstance2 = bst2 = avl2 = createInstance2();
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    avl = null;
    avl2 = null;
  }


  /**
   * create the first instance of avl
   */
  @Override
  protected AVL<String, String> createInstance() {
    return new AVL<String, String>();
  }

  /**
   * create the second instance of avl
   */
  @Override
  protected AVL<Integer, String> createInstance2() {
    return new AVL<Integer, String>();
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Pre-Order traversal order: 20-10-30
   */
  @Override
  @Test
  void testBST_008_check_preOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      avl2.insert(10, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");

      // expected preOrder 20-10-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET pre-ORDER and check
      List<Integer> actualOrder = avl2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Post-Order traversal order: 10-30-20
   */
  @Override
  @Test
  void testBST_009_check_postOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      avl2.insert(10, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");

      // expected postOrder 10-30-20
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(30);
      expectedOrder.add(20);

      // GET post-ORDER and check
      List<Integer> actualOrder = avl2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Level-Order traversal order: 20-10-30 (FIXED ON 2/14/18)
   */
  @Override
  @Test
  void testBST_010_check_levelOrder_for_not_balanced_insert_order() {
    // insert 10-20-30 UNBALANCED
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      // expected levelOrder 20-10-30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET level-ORDER and check
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * remove 4 from 6, balanced
   */
  @Override
  @Test
  void testBST_016_remove_4_from_6_Balanced() {
    try {
      // insert 6 nodes
      avl2.insert(30, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(40, "3rd key inserted");
      avl2.insert(10, "4st key inserted");
      avl2.insert(50, "5nd key inserted");
      avl2.insert(15, "6nd key inserted");

      // check the insert first
      if (avl2.numKeys != 6
          || !avl2.getLevelOrderTraversal().toString().equals("[30, 15, 40, 10, 20, 50]"))
        fail("can not insert correctly");

      // remove 4, root, leaf, with one/two child, check numKeys and contains
      // than check the levelOrderTraversal to make sure that place the predecessor correctly
      avl2.remove(20); // only has left child
      if (avl2.numKeys != 5 || bst2.contains(20)
          || !avl2.getLevelOrderTraversal().toString().equals("[30, 15, 40, 10, 50]"))
        fail("can not remove correctly");

      avl2.remove(30); // have both child, root
      if (avl2.numKeys != 4 || bst2.contains(30)
          || !avl2.getLevelOrderTraversal().toString().equals("[15, 10, 40, 50]"))
        fail("can not remove correctly");

      avl2.remove(40); // have both child
      if (avl2.numKeys != 3 || bst2.contains(40)
          || !avl2.getLevelOrderTraversal().toString().equals("[15, 10, 50]"))
        fail("can not remove correctly");

      avl2.remove(50); // leaf
      if (avl2.numKeys != 2 || bst2.contains(50)
          || !avl2.getLevelOrderTraversal().toString().equals("[15, 10]"))
        fail("can not remove correctly");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * test whether return the right height, insert 6 nodes, unbalanced
   */
  @Override
  @Test
  void testBST_029_getHeight_unbalanced() {
    try {
      // insert 6 nodes, test getHeight after each insert
      avl2.insert(10, "1st key inserted");
      if (bst2.getHeight() != 1)
        fail("can not get the correct height");

      avl2.insert(20, "2nd key inserted");
      if (bst2.getHeight() != 2)
        fail("can not get the correct height");

      avl2.insert(30, "3rd key inserted");
      if (bst2.getHeight() != 2)
        fail("can not get the correct height");

      avl2.insert(40, "4th key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

      avl2.insert(50, "5th key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

      avl2.insert(60, "6th key inserted");
      if (bst2.getHeight() != 3)
        fail("can not get the correct height");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values in sorted order and then check the root, left, and right keys to see if
   * rebalancing occurred.
   */
  @Test
  void testAVL_001_insert_sorted_order_simple() {
    try {
      avl2.insert(10, "10");
      if (!avl2.getKeyAtRoot().equals(10))
        fail("avl insert at root does not work");

      avl2.insert(20, "20");
      if (!avl2.getKeyOfRightChildOf(10).equals(20))
        fail("avl insert to right child of root does not work");

      avl2.insert(30, "30");
      Integer k = avl2.getKeyAtRoot();

      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values in reverse sorted order and then check the root, left, and right keys to
   * see if rebalancing occurred in the other direction.
   */
  @Test
  void testAVL_002_insert_reversed_sorted_order_simple() {
    try {
      avl2.insert(30, "30");
      if (!avl2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");

      avl2.insert(20, "20");
      if (!avl2.getKeyOfLeftChildOf(30).equals(20))
        fail("avl insert to right child of root does not work");

      avl2.insert(10, "10");
      Integer k = avl2.getKeyAtRoot();

      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }


  /**
   * Insert three values so that a right-left rotation is needed to fix the balance.
   * 
   * Example: 10-30-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   */
  @Test
  void testAVL_003_insert_smallest_largest_middle_order_simple() {
    try {
      avl2.insert(10, "10");
      if (!avl2.getKeyAtRoot().equals(10))
        fail("avl insert at root does not work");

      avl2.insert(30, "30");
      if (!avl2.getKeyOfRightChildOf(10).equals(30))
        fail("avl insert to right child of root does not work");

      avl2.insert(20, "20");
      Integer k = avl2.getKeyAtRoot();

      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a left-right rotation is needed to fix the balance.
   * 
   * Example: 30-10-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   */
  @Test
  void testAVL_004_insert_largest_smallest_middle_order_simple() {
    try {
      avl2.insert(30, "30");
      if (!avl2.getKeyAtRoot().equals(30))
        fail("avl insert at root does not work");

      avl2.insert(10, "10");
      if (!avl2.getKeyOfLeftChildOf(30).equals(10))
        fail("avl insert to right child of root does not work");

      avl2.insert(20, "20");
      Integer k = avl2.getKeyAtRoot();

      if (!k.equals(20))
        fail("avl rotate does not work");

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * insert multiple number and check each time rebalanced
   */
  @Test
  void testAVL_005_insert_multiple() {
    try {
      // add three nodes as tested before, left-right rotate
      avl2.insert(30, "30");
      avl2.insert(10, "10");
      avl2.insert(20, "20");

      // new nodes, check insert with level order traversal each time rotation happens
      avl2.insert(60, "60");
      Assert.assertEquals(avl2.getLevelOrderTraversal().toString(), "[20, 10, 30, 60]");

      avl2.insert(1, "1");
      avl2.insert(25, "25");
      avl2.insert(0, "0");
      Assert.assertEquals(avl2.getLevelOrderTraversal().toString(), "[20, 1, 30, 0, 10, 25, 60]");

      avl2.insert(15, "15");
      avl2.insert(13, "13");
      if (!avl2.getLevelOrderTraversal().toString().equals("[20, 1, 30, 0, 13, 25, 60, 10, 15]"))
        fail("can not right left rotate correctly");

      avl2.insert(9, "9");
      String expected = "[20, 10, 30, 1, 13, 25, 60, 0, 9, 15]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(14, "14");
      expected = "[20, 10, 30, 1, 14, 25, 60, 0, 9, 13, 15]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(12, "12");
      expected = "[14, 10, 20, 1, 13, 15, 30, 0, 9, 12, 25, 60]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(24, "24");
      expected = "[14, 10, 25, 1, 13, 20, 30, 0, 9, 12, 15, 24, 60]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(90, "90");
      expected = "[14, 10, 25, 1, 13, 20, 60, 0, 9, 12, 15, 24, 30, 90]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(120, "120");
      avl2.insert(100, "100");
      expected = "[14, 10, 25, 1, 13, 20, 60, 0, 9, 12, 15, 24, 30, 100, 90, 120]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(130, "130");
      avl2.insert(140, "140");
      avl2.insert(150, "150");
      expected =
          "[14, 10, 100, 1, 13, 25, 130, 0, 9, 12, 20, 60, 120, 140, 15, 24, " + "30, 90, 150]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

      avl2.insert(160, "160");
      avl2.insert(170, "170");
      avl2.insert(180, "180");
      avl2.insert(190, "190");
      expected = "[100, 14, 150, 10, 25, 130, 170, 1, 13, 20, 60, 120, 140, 160, 180, "
          + "0, 9, 12, 15, 24, 30, 90, 190]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not right left rotate correctly");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * test to insert and remove multiple times
   */
  @Test
  void testAVL_006_insert_remove() {
    try {
      // add three nodes as tested before
      avl2.insert(30, "30");
      avl2.insert(10, "10");
      avl2.insert(20, "20");
      avl2.insert(60, "60");
      avl2.insert(1, "1");
      avl2.insert(25, "25");
      avl2.insert(0, "0");
      avl2.insert(15, "15");
      avl2.insert(13, "13");
      avl2.insert(9, "9");

      // remove and check each remove
      avl2.remove(20);
      String expected = "[15, 10, 30, 1, 13, 25, 60, 0, 9]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not remove correctly");


      avl2.remove(13);
      expected = "[15, 1, 30, 0, 10, 25, 60, 9]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not remove correctly");

      avl2.remove(1);
      expected = "[15, 9, 30, 0, 10, 25, 60]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not remove correctly");

      avl2.remove(15);
      expected = "[10, 9, 30, 0, 25, 60]";
      if (!avl2.getLevelOrderTraversal().toString().equals(expected))
        fail("can not remove correctly");

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }
}
