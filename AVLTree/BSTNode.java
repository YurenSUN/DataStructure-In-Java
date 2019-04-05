////////////////// File Header////////////////////
// Course: CS400 Lecture 001
// Assignment Name: p2
// Author: Yuren Sun
// Email: ysun299@wisc.edu
// Due date: Feb 21
// Other source credits: N/A
/////////////////////////////////////////////////

/**
 * represent the BST nodes
 * 
 * @author Yuren Sun
 *
 * @param <K>
 * @param <V>
 */
class BSTNode<K, V> {

  K key; // represent the key of node
  V value; // represent the value of the node
  BSTNode<K, V> left; // represent the left child
  BSTNode<K, V> right; // represent the right child
  int balanceFactor; // represent the height of left subtree minu the right subtree
  int height; // represent the height of tree

  /**
   * constructor for BSTNode, set height to 1
   * 
   * @param key
   * @param value
   * @param leftChild
   * @param rightChild
   */
  BSTNode(K key, V value, BSTNode<K, V> leftChild, BSTNode<K, V> rightChild) {
    this.key = key;
    this.value = value;
    this.left = leftChild;
    this.right = rightChild;
    this.height = 1;
    this.balanceFactor = 0;
  }

  /**
   * constructor for BSTNode, set height to 1
   * 
   * @param key
   * @param value
   */
  BSTNode(K key, V value) {
    this(key, value, null, null);
  }


  /**
   * return true if has left child, false otherwise
   * 
   * @return
   */
  boolean hasLeft() {
    return this.left != null;
  }

  /**
   * return true if has right child, false otherwise
   */
  boolean hasRight() {
    return this.right != null;
  }
}
