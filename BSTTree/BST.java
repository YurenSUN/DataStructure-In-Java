////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p2
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: Feb 21
// Other source credits: N/A
/////////////////////////////////////////////////

import java.util.ArrayList; // allowed for creating traversal lists
import java.util.LinkedList;
import java.util.List; // required for returning List<K>
import java.util.Queue;

/**
 * represent the binary search tree
 * 
 * @author Yuren Sun
 *
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<K>, V> implements BSTADT<K, V> {

  protected BSTNode<K, V> root; // represent the root of BST
  protected int numKeys; // number of keys in BST

  /**
   * Constructor, set root to null and numKeys to 0
   */
  public BST() {
    this.root = null;
    this.numKeys = 0;
  }

  /**
   * Returns the keys of the BST in pre-order traversal order. In the case of binary search trees,
   * the order is: V L R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in pre-order
   */
  @Override
  public List<K> getPreOrderTraversal() {
    List<K> returnList = new ArrayList<K>();
    if (this.root == null)
      return returnList;

    getPreOrderTraversal(returnList, root); // call the helper method
    return returnList;
  }

  /**
   * helper method for getPreOrderTraversal() the order is: V L R
   * 
   * @param list the list to put keys in
   * @param root the root to recursive from
   */
  private void getPreOrderTraversal(List<K> list, BSTNode<K, V> root) {
    list.add(root.key);
    if (root.hasLeft())
      getPreOrderTraversal(list, root.left); // recursive to left subtree
    if (root.hasRight())
      getPreOrderTraversal(list, root.right); // recursive to left subtree
  }

  /**
   * Returns the keys of the data structure in post-order traversal order. In the case of binary
   * search trees, the order is: L R V
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in post-order
   */
  @Override
  public List<K> getPostOrderTraversal() {
    List<K> returnList = new ArrayList<K>();
    if (this.root == null)
      return returnList;

    getPostOrderTraversal(returnList, root); // call the helper method
    return returnList;
  }

  /**
   * helper method of getPostOrderTraversal the order is: L R V
   * 
   * @param list
   * @param root
   */
  private void getPostOrderTraversal(List<K> list, BSTNode<K, V> root) {
    if (root.hasLeft())
      getPostOrderTraversal(list, root.left); // recursive to left subtree
    if (root.hasRight())
      getPostOrderTraversal(list, root.right); // recursive to left subtree
    list.add(root.key);
  }

  /**
   * Returns the keys of the data structure in level-order traversal order.
   * 
   * The root is first in the list, then the keys found in the next level down, and so on.
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in level-order
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    List<K> returnList = new ArrayList<K>();
    if (this.root == null)
      return returnList;

    getLevelOrderTraversal(returnList, root); // call the helper method
    return returnList;
  }

  /**
   * helper method of getLevelOrderTraversal The root is first in the list, then the keys found in
   * the next level down, and so on.
   * 
   * @param list
   * @param root
   */
  private void getLevelOrderTraversal(List<K> list, BSTNode<K, V> root) {
    Queue<BSTNode<K, V>> queue = new LinkedList<BSTNode<K, V>>(); // queue to store the nodes to be
                                                                  // use
    queue.add(root);
    while (!queue.isEmpty()) {
      BSTNode<K, V> current = queue.poll();
      list.add(current.key);
      if (current.hasLeft())
        queue.add(current.left);
      if (current.hasRight())
        queue.add(current.right);
    }
  }


  /**
   * Returns the keys of the data structure in sorted order. In the case of binary search trees, the
   * visit order is: L V R
   * 
   * If the SearchTree is empty, an empty list is returned.
   * 
   * @return List of Keys in-order
   */
  @Override
  public List<K> getInOrderTraversal() {
    List<K> returnList = new ArrayList<K>();
    if (this.root == null)
      return returnList;

    getInOrderTraversal(returnList, root); // call the helper method
    return returnList;
  }

  /**
   * helper method of getInOrderTraversal the visit order is: L V R
   * 
   * @param list
   * @param root
   */
  private void getInOrderTraversal(List<K> list, BSTNode<K, V> root) {
    if (root.hasLeft())
      getInOrderTraversal(list, root.left); // recursive to left subtree
    list.add(root.key);

    if (root.hasRight())
      getInOrderTraversal(list, root.right); // recursive to left subtree
  }

  /**
   * Add the key, value pair to the data structure and increase the number of keys.
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws DuplicateKeyException   if key is already in data structure
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (this.contains(key))
      throw new DuplicateKeyException();

    root = insert(root, key, value);
    this.numKeys++;
  }

  /**
   * helper method for insert method
   * 
   * @param key
   * @param value
   */
  private BSTNode<K, V> insert(BSTNode<K, V> root, K key, V value) {
    if (root == null) {
      return new BSTNode<K, V>(key, value);
    }

    if (root.key.compareTo(key) < 0) // key is larger than root, recurse to right subtree
      root.right = insert(root.right, key, value);


    if (root.key.compareTo(key) > 0) // key is smaller than root, recurse to left subtree
      root.left = insert(root.left, key, value);

    return root;
  }

  /**
   * If key is found, remove the key,value pair from the data structure and decrease num keys.
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException    if key is not found
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    root = remove(root, key);
    this.numKeys--;
    return true;
  }

  /**
   * helper method for remove method
   */
  private BSTNode<K, V> remove(BSTNode<K, V> root, K key) {
    if (root.key.equals(key)) {
      if (!(root.hasLeft() || root.hasRight())) // has no children, remove directly
        return null;
      else if (!root.hasLeft()) // only have right children
        return root.right;
      else if (!root.hasRight()) // only have left children
        return root.left;
      else { // have both children
        BSTNode<K, V> setNode = predecessor(root.left);
        root.key = setNode.key;
        root.value = setNode.value;
        root.left = remove(root.left, root.key); // Delete the in order predecessor
        return root;
      }
    }
    if (root.key.compareTo(key) > 0) // key smaller than key of root, recurse to left subtree
      root.left = remove(root.left, key);
    if (root.key.compareTo(key) < 0) // key larger than key of root, recurse to right subtree
      root.right = remove(root.right, key);

    return root;
  }

  /**
   * method to get the in order predecessor, right most of left subtree, keep recursing right
   */
  private BSTNode<K, V> predecessor(BSTNode<K, V> root) {
    BSTNode<K, V> current = root; // use to keep track of the node
    while (current.hasRight()) {
      current = current.right;
    }
    return current;
  }


  /**
   * Returns the value associated with the specified key Does not remove key or decrease number of
   * keys
   * 
   * @throws IllegalNullKeyException if key is null,
   * @throws KeyNotFoundException    if key is not found, throw.
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    return get(root, key);
  }

  /**
   * helper method for get method
   */
  private V get(BSTNode<K, V> root, K key) {
    if (root.key.equals(key))
      return root.value;
    if (root.key.compareTo(key) > 0) // key is smaller than key of root, recurse to left subtree
      return get(root.left, key);
    if (root.key.compareTo(key) < 0) // key is larger than key of root, recurse to right subtree
      return get(root.right, key);

    return null;
  }


  /**
   * Returns true if the key is in the data structure Returns false if key is not null and is not
   * present
   * 
   * @throws IllegalNullKeyException if key is null
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null)
      throw new IllegalNullKeyException();

    if (root == null)
      return false;

    return contains(root, key);
  }

  /**
   * helper method for contains method
   */
  private boolean contains(BSTNode<K, V> root, K key) {
    if (root.key != null && root.key.equals(key))
      return true;
    if (root.key.compareTo(key) > 0) {// key is smaller than the key of root, recurse to left
                                      // subtree
      if (root.hasLeft())
        return contains(root.left, key);
      return false;
    }
    if (root.key.compareTo(key) < 0) {// key is larger than the key of root, recurse to right
                                      // subtree
      if (root.hasRight())
        return contains(root.right, key);
      return false;
    }
    return false;
  }

  /**
   * Returns the number of key,value pairs in the data structure
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Returns the key that is in the root node of this BST. If root is null, returns null.
   * 
   * @return key found at root node, or null
   */
  @Override
  public K getKeyAtRoot() {
    if (this.root == null)
      return null;
    return root.key;
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the left child. If the left child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the left child of the found key
   * 
   * @throws IllegalNullKeyException if key argument is null
   * @throws KeyNotFoundException    if key is not found in this BST
   */
  @Override
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    return getKeyOfLeftChildOf(root, key);
  }

  /**
   * helper method forgetKeyOfLeftChildOf method
   */
  private K getKeyOfLeftChildOf(BSTNode<K, V> root, K key) {
    if (root.key.equals(key)) {
      if (root.hasLeft())
        return root.left.key;
      return null; // have no left child, return null
    }
    if (root.key.compareTo(key) > 0) // key is smaller than key of root, recurse to left subtree
      return getKeyOfLeftChildOf(root.left, key);
    if (root.key.compareTo(key) < 0) // key is larger than key of root, recurse to right subtree
      return getKeyOfLeftChildOf(root.right, key);

    return null;
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the right child. If the right child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the right child of the found key
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException    if key is not found in this BST
   */
  @Override
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null)
      throw new IllegalNullKeyException();
    if (!this.contains(key))
      throw new KeyNotFoundException();

    return getKeyOfRightChildOf(root, key);
  }

  /**
   * helper method forgetKeyOfRightChildOf method
   */
  private K getKeyOfRightChildOf(BSTNode<K, V> root, K key) {
    if (root.key.equals(key)) {
      if (root.hasRight())
        return root.right.key;
      return null; // have no left child, return null
    }
    if (root.key.compareTo(key) > 0) // key is smaller than key of root, recurse to left subtree
      return getKeyOfRightChildOf(root.left, key);
    if (root.key.compareTo(key) < 0) // key is larger than key of root, recurse to right subtree
      return getKeyOfRightChildOf(root.right, key);

    return null;
  }

  /**
   * Returns the height of this BST. H is defined as the number of levels in the tree.
   * 
   * If root is null, return 0 If root is a leaf, return 1 Else return 1 + max( height(root.left),
   * height(root.right) )
   * 
   * Examples: A BST with no keys, has a height of zero (0). A BST with one key, has a height of one
   * (1). A BST with two keys, has a height of two (2). A BST with three keys, can be balanced with
   * a height of two(2) or it may be linear with a height of three (3) ... and so on for tree with
   * other heights
   * 
   * @return the number of levels that contain keys in this BINARY SEARCH TREE
   */
  @Override
  public int getHeight() {
    if (root == null)
      return 0;
    if (!(root.hasLeft() || root.hasRight())) // root is a leaf
      return 1;

    return height(root);
  }

  /**
   * helper method of getHeight
   */
  private int height(BSTNode<K, V> root) {
    if (root == null)
      return 0;

    int leftHeight = height(root.left);
    int rightHeight = height(root.right);

    if (leftHeight >= rightHeight)
      return 1 + leftHeight;
    else
      return 1 + rightHeight;
  }
}
