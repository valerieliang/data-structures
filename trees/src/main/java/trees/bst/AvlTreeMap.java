package trees.bst;

import trees.OrderedMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * Map implemented as an AVL Tree.
 *
 * @param <K> Type for keys.
 * @param <V> Type for values.
 */
public class AvlTreeMap<K extends Comparable<K>, V> implements OrderedMap<K, V> {

  /*** Do not change variable name of 'root'. ***/
  private Node<K, V> root;
  private int size;

  // returns height of the (sub)tree from that node
  private int height(Node<K,V> x) {
    if (x == null) {
      return -1;
    }
    return x.height;
  }

  // Balances tree by calling respective rotation
  private Node<K,V> balance(Node<K,V> n) {
    // absolute value greater than 1 for imbalance
    if (balanceFactor(n) < -1) {
      if (balanceFactor(n.right) > 0) {
        n.right = rotateRight(n.right); // right left rotation
      }
      n = rotateLeft(n); // single left rotation
    } else if (balanceFactor(n) > 1) {
      if (balanceFactor(n.left) < 0) { // left right rotation
        n.left = rotateLeft(n.left);
      }
      n = rotateRight(n); // single right rotation
    }
    // no balancing needed
    return n;
  }

  // calculates the balance factor by comparing the heights of the subtrees
  private int balanceFactor(Node<K,V> node) {
    return height(node.left) - height(node.right);
  }

  // single left rotation
  private Node<K,V> rotateLeft(Node<K,V> node) {
    Node<K,V> child = node.right;
    node.right = child.left;
    child.left = node;
    // update heights
    node.height = 1 + Math.max(height(node.left), height(node.right));
    child.height = 1 + Math.max(height(child.left), height(child.right));
    return child;
  }

  // single right rotation
  private Node<K,V> rotateRight(Node<K,V> node) {
    Node<K,V> child = node.left;
    node.left = child.right;
    child.right = node;
    // update heights
    node.height = 1 + Math.max(height(node.left), height(node.right));
    child.height = 1 + Math.max(height(child.left), height(child.right));
    return child;
  }

  // Insert given key and value into subtree rooted at given node;
  // return changed subtree with a new node added.
  private Node<K, V> insert(Node<K, V> n, K k, V v) {
    if (n == null) {
      return new Node<>(k, v);
    }

    int cmp = k.compareTo(n.key);
    if (cmp < 0) {
      n.left = insert(n.left, k, v);
    } else if (cmp > 0) {
      n.right = insert(n.right, k, v);
    } else {
      throw new IllegalArgumentException("duplicate key " + k);
    }
    n.height = 1 + Math.max(height(n.left), height(n.right));

    return balance(n);
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
    // increases size if insertion doesn't throw exception
    size++;
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
    // decreases size if removal doesn't throw exception
    size--;
    return value;
  }

  // Remove node with given key from subtree rooted at given node;
  // Return changed subtree with given key missing.
  private Node<K, V> remove(Node<K, V> subtreeRoot, Node<K, V> toRemove) {
    int cmp = subtreeRoot.key.compareTo(toRemove.key);
    if (cmp == 0) {
      return remove(subtreeRoot);
    } else if (cmp > 0) {
      subtreeRoot.left = remove(subtreeRoot.left, toRemove);
    } else {
      subtreeRoot.right = remove(subtreeRoot.right, toRemove);
    }
    subtreeRoot.height = 1 + Math.max(height(subtreeRoot.left), height(subtreeRoot.right));
    return balance(subtreeRoot);
  }

  // Remove given node and return the remaining tree (structural change).
  private Node<K, V> remove(Node<K, V> node) {
    // Easy if the node has 0 or 1 child.
    if (node.right == null) {
      return node.left;
    } else if (node.left == null) {
      return node.right;
    }

    // If it has two children, find the predecessor (max in left subtree),
    Node<K, V> toReplaceWith = max(node);
    // then copy its data to the given node (value change),
    node.key = toReplaceWith.key;
    node.value = toReplaceWith.value;
    // then remove the predecessor node (structural change).
    node.left = remove(node.left, toReplaceWith);

    return balance(node);
  }

  // Return a node with maximum key in subtree rooted at given node.
  private Node<K, V> max(Node<K, V> node) {
    Node<K, V> curr = node.left;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
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

  // Return node for given key,
  // throw an exception if the key is not in the tree.
  private Node<K, V> findForSure(K k) {
    Node<K, V> n = find(k);
    if (n == null) {
      throw new IllegalArgumentException("cannot find key " + k);
    }
    return n;
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

  /**
   * Iterator for traversing over tree in order.
   *
   * @return In order iterator.
   */
  @Override
  public Iterator<K> iterator() {
    return new InorderIterator();
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
   *
   * <p>Inner node class, each holds a key (which is what we sort the
   * BST by) as well as a value. We don't need a parent pointer as
   * long as we use recursive insert/remove helpers.</p>
   **/
  private static class Node<K, V> implements BinaryTreeNode {
    Node<K, V> left;
    Node<K, V> right;
    K key;
    V value;
    int height;

    // Constructor to make node creation easier to read.
    Node(K k, V v) {
      // left and right default to null
      key = k;
      value = v;
      height = 0;
    }

    @Override
    public String toString() {
      return key + ":" + value;
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

  // Iterative in-order traversal over the keys
  private class InorderIterator implements Iterator<K> {
    private final Stack<Node<K, V>> stack;

    InorderIterator() {
      stack = new Stack<>();
      pushLeft(root);
    }

    private void pushLeft(Node<K, V> curr) {
      while (curr != null) {
        stack.push(curr);
        curr = curr.left;
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.isEmpty();
    }

    @Override
    public K next() {
      Node<K, V> top = stack.pop();
      pushLeft(top.right);
      return top.key;
    }
  }
}
