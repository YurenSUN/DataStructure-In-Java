////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p2
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: Feb 21
// Other source credits: N/A
/////////////////////////////////////////////////

/**
 * represent the average balanced tree
 * 
 * @author Yuren Sun
 *
 * @param <K>
 * @param <V>
 */
public class AVL<K extends Comparable<K>, V> extends BST<K, V> {
  /**
   * constructor, set root to null and numKeys to 0
   */
  public AVL() {
    this.root = null;
    this.numKeys = 0;
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
  private BSTNode<K, V> insert(BSTNode<K, V> node, K key, V value) {
    if (node == null) {
      return new BSTNode<K, V>(key, value);
    }

    if (node.key.compareTo(key) < 0) // key is larger than root, recurse to right subtree
      node.right = insert(node.right, key, value);


    if (node.key.compareTo(key) > 0) // key is smaller than root, recurse to left subtree
      node.left = insert(node.left, key, value);

    node.height = Math.max(height(node.left), height(node.right)) + 1;
    return balance(node);
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
    if (!super.contains(key))
      throw new KeyNotFoundException();

    root = remove(root, key);
    this.numKeys--;
    return true;
  }

  /**
   * helper method for remove method
   */
  private BSTNode<K, V> remove(BSTNode<K, V> node, K key) {
    if (node == null)
      return null;

    if (node.key.equals(key)) {
      if (!(node.hasLeft() || node.hasRight())) // has no children, remove directly
        return null;
      else if (!node.hasLeft()) // only have right children
        node = node.right;
      else if (!node.hasRight()) // only have left children
        node = node.left;
      else { // have both children
        BSTNode<K, V> setNode = predecessor(node.left);
        node.key = setNode.key;
        node.value = setNode.value;
        node.left = remove(node.left, node.key); // Delete the in order predecessor

      }
    } else if (node.key.compareTo(key) > 0) // key smaller than key of root, recurse to left subtree
      node.left = remove(node.left, key);

    else if (node.key.compareTo(key) < 0) // key larger than key of root, recurse to right subtree
      node.right = remove(node.right, key);

    // refresh and return
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    return balance(node);
  }

  /**
   * method to balance after insert or remove
   */
  private BSTNode<K, V> balance(BSTNode<K, V> node) {
    // find whether there are node with BF >1 or <-1
    if (balanceFactor(node) > 1) {
      // right rotate if the BF of left kid of NeedFix is 1, left-right if -1
      if (balanceFactor(node.left) >= 0) {
        node = rightRotate(node);
      } else
        node = leftRightRotate(node);

    } else if (balanceFactor(node) < -1) {
      // left rotate if the BF of right kid of NeedFix is -1, right-left if 1
      if (balanceFactor(node.right) <= 0)
        node = leftRotate(node);
      else
        node = rightLeftRotate(node);
    }
    return node;
  }

  /**
   * get the balance factor of the node, use number of (height of left subtree minus height of right
   * subtree)
   */
  private int balanceFactor(BSTNode<K, V> node) {
    int BF = 0;
    // factor is height of left subtree minus that of right
    if (node.left != null) {
      BF += node.left.height;
    }
    if (node.right != null) {
      BF -= node.right.height;
    }

    return BF;
  }

  /**
   * get height of a node
   * 
   * @param node
   * @return
   */
  private int height(BSTNode<K, V> node) {
    if (node == null) {
      return 0;
    }

    return node.height;
  }

  /**
   * right rotate if BF of grandparent is greater than 1, BF of grandparent.left is 1
   * 
   * @param node
   * @return
   */
  private BSTNode<K, V> rightRotate(BSTNode<K, V> node) {
    BSTNode<K, V> parent = node.left; // represent the node's left child
    node.left = parent.right;
    parent.right = node;

    // refresh both nodes and return
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
    return parent;
  }

  /**
   * left rotate if BF of grandparent is smaller than -1, BF of grandparent.right is -1
   * 
   * @param node
   * @return
   */
  private BSTNode<K, V> leftRotate(BSTNode<K, V> node) {
    BSTNode<K, V> parent = node.right; // represent the node's right child
    node.right = parent.left;
    parent.left = node;

    // refresh both nodes and return
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
    return parent;
  }

  /**
   * right-left rotate if BF of grandparent is smaller than -1, BF of grandparent.right is 1
   * 
   * @param root represents the grandparent node
   * @throws KeyNotFoundException
   * @throws IllegalNullKeyException
   */
  private BSTNode<K, V> rightLeftRotate(BSTNode<K, V> node) {
    BSTNode<K, V> parent = node.right; // represent the node's right child
    BSTNode<K, V> kid = parent.left; // reprensent the left child of the node's right child
    node.right = kid.left;
    parent.left = kid.right;
    kid.left = node;
    kid.right = parent;

    // refresh both nodes and return
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
    kid.height = Math.max(height(kid.left), height(kid.right)) + 1;
    return kid;
  }

  /**
   * left-right rotate if BF of grandparent is greater than 1, BF of grandparent.left is -1
   * 
   * @param root represents the grandparent node
   * @throws KeyNotFoundException
   * @throws IllegalNullKeyException
   */
  private BSTNode<K, V> leftRightRotate(BSTNode<K, V> node) {
    BSTNode<K, V> parent = node.left; // represent the node's left child
    BSTNode<K, V> kid = parent.right; // represent the right child of node's left child
    node.left = kid.right;
    parent.right = kid.left;
    kid.right = node;
    kid.left = parent;

    // refresh both nodes and return
    node.height = Math.max(height(node.left), height(node.right)) + 1;
    parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
    kid.height = Math.max(height(kid.left), height(kid.right)) + 1;
    return kid;
  }

  /**
   * method to get the in order predecessor, right most of left subtree, keep recursing right
   */
  private BSTNode<K, V> predecessor(BSTNode<K, V> root) {
    // root is the left child of the node to find predecessor
    BSTNode<K, V> current = root; // use to keep track of the node
    // go to the right most of the node
    while (current.hasRight()) {
      current = current.right;
    }
    return current;
  }
}
