package trees.bst;

import trees.OrderedMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Map implemented as a Treap.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class TreapMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'rand'. ***/
  private static Random rand;
  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  /**
   * Make a TreapMap.
   */
  public TreapMap() {
    rand = new Random();
  }

  /**
   * Make a TreapMap with set seed.
   * @param seed the seed for the Random object rand.
   */
  public TreapMap(int seed) {
    rand = new Random();
    rand.setSeed(seed);
  }

  // single left rotation
  private Node<K,V> rotateLeft(Node<K,V> node) {
    Node<K,V> child = node.right;
    node.right = child.left;
    child.left = node;
    return child;
  }

  // single right rotation
  private Node<K,V> rotateRight(Node<K,V> node) {
    Node<K,V> child = node.left;
    node.left = child.right;
    child.right = node;
    return child;
  }

  // balancing when node has 0 or 1 children
  private Node<K,V> balanceOne(Node<K,V> node) {
    if (node.left == null && node.right == null) {
      return node;
    } else if (node.left == null) {
      if (node.priority <= node.right.priority) {
        return node;
      } else {
        node = rotateLeft(node);
        node.left = balance(node.left);
      }
    } else if (node.right == null) {
      if (node.priority < node.left.priority) {
        return node;
      } else {
        node = rotateRight(node);
        node.right = balance(node.right);
      }
    }
    return node;
  }

  // balances treap by performing necessary rotations
  private Node<K,V> balance(Node<K,V> node) {
    if (node.left == null || node.right == null) {
      return balanceOne(node);
    }  else {
      if (node.left.priority < node.right.priority) {
        node = rotateRight(node);
        node.right = balance(node.right);
      } else {
        node = rotateLeft(node);
        node.left = balance(node.left);
      }
    }
    return node;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private Node<K, V> insert(Node<K, V> node, K k, V v) {
    if (node == null) {
      return new Node<>(k, v);
    }
    if (k.compareTo(node.key) < 0) {
      node.left = insert(node.left, k, v);
      if (node.left.priority < node.priority) {
        node = rotateRight(node);
      }
    } else if (k.compareTo(node.key) > 0) {
      node.right = insert(node.right, k, v);
      if (node.right.priority < node.priority) {
        node = rotateLeft(node);
      }
    } else {
      throw new IllegalArgumentException("duplicate key" + k);
    }
    return node;
  }

  /**
   * Insert a new key/value pair.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If k is null or already mapped.
   */
  @Override
  public void insert(K k, V v) throws IllegalArgumentException {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    root = insert(root, k, v);
    size++;
  }

  // Return node for given key.
  private Node<K, V> find(K k) {
    if (k == null) {
      throw new IllegalArgumentException("cannot handle null key");
    }
    Node<K, V> n = root;
    while (n != null) {
      int cmp = k.compareTo(n.key);
      if (cmp < 0) {
        n = n.left;
      } else if (cmp > 0) {
        n = n.right;
      } else {
        return n;
      }
    }
    return null;
  }

  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
  }

  // Return a node with maximum key in subtree rooted at given node.
  private Node<K, V> max(Node<K, V> node) {
    if (node == null) {
      return null;
    }
    Node<K, V> curr = node;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  // remove the node with the maximum key from the subtree rooted at given node
  private Node<K, V> removeMax(Node<K, V> node) {
    if (node.right == null) {
      return node.left;
    }
    node.right = removeMax(node.right);
    return balance(node);
  }

  /**
   * Remove an existing key/value pair.
   *
   * @param k The key.
   * @return The value that was associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  @Override
  public V remove(K k) throws IllegalArgumentException {
    Node<K, V> node = findForSure(k);
    V value = node.value;
    root = remove(root, node);
    size--;
    return value;
  }

  // removes node and updates balance
  private Node<K, V> remove(Node<K, V> node) {
    if (node.left == null) { // one or no child(ren)
      return node.right;
    } else if (node.right == null) {
      return node.left;
    }
    // two children
    Node<K, V> toReplaceWith = max(node.left);
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    node.priority = toReplaceWith.priority;
    node.left = removeMax(node.left);
    return balance(node);
  }

  // finds node to remove
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }

    return subtreeRoot;
  }

  /**
   * Update the value associated with a key.
   *
   * @param k The key.
   * @param v The value to be associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  @Override
  public void put(K k, V v) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    n.value = v;
  }

  /**
   * Get the value associated with a key.
   *
   * @param k The key.
   * @return The value associated with k.
   * @throws IllegalArgumentException If k is null or not mapped.
   */
  @Override
  public V get(K k) throws IllegalArgumentException {
    Node<K, V> n = findForSure(k);
    return n.value;
  }

  /**
   * Check existence of a key.
   *
   * @param k The key.
   * @return True if k is mapped, false otherwise (even for null!).
   */
  @Override
  public boolean has(K k) {
    if (k == null) {
      return false;
    }
    return find(k) != null;
  }

  /**
   * Number of mappings.
   *
   * @return Number of key/value pairs in the map.
   */
  @Override
  public int size() {
    return size;
  }

  @Override
  public Iterator<K> iterator() {
    return new TreapMapIterator<>(root);
  }

  /*** Do not change this function's name or modify its code. ***/
  @Override
  public String toString() {
    return BinaryTreePrinter.printBinaryTree(root);
  }


  /**
   * Feel free to add whatever you want to the Node class (e.g. new fields).
   * Just avoid changing any existing names, deleting any existing variables,
   * or modifying the overriding methods.
   * Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers. Since this is
   * a node class for a Treap we also include a priority field.
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int priority;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      priority = generateRandomInteger();
    }

    // Use this function to generate random values
    // to use as node priorities as you insert new
    // nodes into your TreapMap.
    private int generateRandomInteger() {
      // Note: do not change this function!
      return rand.nextInt();
    }

    @Override
    public String toString() {
      return key + ":" + value + ":" + priority;
    }

    @Override
    public BinaryTreeNode getLeftChild() {
      return left;
    }

    @Override
    public BinaryTreeNode getRightChild() {
      return right;
    }
  }

  private class TreapMapIterator<K extends Comparable<K>, V> implements Iterator<K> {

    private Stack<Node<K, V>> stack;

    TreapMapIterator(Node<K, V> root) {
      stack = new Stack<>();
      pushAll(root);
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> node = stack.pop();
      pushAll(node.right);
      return node.key;
    }

    private void pushAll(Node<K, V> node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }
  }
}
