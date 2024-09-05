package trees;

import trees.bst.TreapMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to Treap.
 */
public class TreapMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new TreapMap<>(333);
  }

  @Test
  @DisplayName("Test insert rotates left when needed")
  public void insertLeftRotation() {
    map.insert("1", "a");
    map.insert("2", "b");
    map.insert("3", "c");
    // assert statements
    // System.out.println(map.toString());
    /*
    String[] expected = new String[]{
            "2:b:-1397264289",
            "1:a:-1086614523 3:c:-463472126"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
     */
  }

  @Test
  @DisplayName("Test insert rotates right when needed")
  public void insertRightRotation() {
    map.insert("3","c");
    map.insert("2","b");
    map.insert("1","a");
    System.out.println(map.toString());
    // assert statements
    /*
    String[] expected = new String[]{
            "2:b:-1397264289",
            "1:a:-463472126 3:c:-1086614523"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
     */
  }

  @Test
  @DisplayName("Insert, right-left rotation")
  public void insertRightLeftRotation() {
    map.insert("1","a");
    map.insert("3","c");
    map.insert("2","b");
    System.out.println(map.toString());
    // assert
  }

  @Test
  @DisplayName("Insert, left-right rotation")
  public void insertLeftRightRotation() {
    map.insert("3","c");
    map.insert("1","a");
    map.insert("2","b");
    // assert
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Insert, no rotation")
  public void insertNoRotation() {
    map.insert("2","b");
    map.insert("3","c");
    map.insert("1","a");
    // assert
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove leaf, left rotation")
  public void removeLeafLeftRotation() {
    // setup
    map.insert("2","b");
    map.insert("3","c");
    map.insert("1","a");
    map.insert("4","d");

    map.remove("1");
    //
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove leaf, right left rotation")
  public void removeLeafRightLeftRotation() {
    // setup
    map.insert("2","b");
    map.insert("4","d");
    map.insert("1","a");
    map.insert("3","c");

    map.remove("1");
    //
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove leaf, right rotation")
  public void removeLeafRightRotation() {
    // setup
    map.insert("3","c");
    map.insert("2","b");
    map.insert("4","d");
    map.insert("1","a");

    map.remove("4");
    //
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove leaf, left right rotation")
  public void removeLeafLeftRightRotation() {
    // setup
    map.insert("3","c");
    map.insert("1","a");
    map.insert("4","d");
    map.insert("2","b");

    map.remove("4");
    //
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove leaf, no rotation")
  public void removeLeafNoRotation() {
    // setup
    map.insert("3","c");
    map.insert("2","b");
    map.insert("4","d");
    map.insert("1","a");

    map.remove("1");
    //
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove node with one child, double rotation")
  public void removeOneChildDoubleRotation() {
    //setup
    map.insert("e","e");
    map.insert("j","j");
    map.insert("c","c");
    map.insert("b","b");
    map.insert("d","d");
    map.insert("h","h");
    map.insert("l","l");
    map.insert("a","a");
    map.insert("g","g");
    map.insert("i","i");
    map.insert("k","k");
    map.insert("m","m");
    map.insert("f","f");

    map.remove("b");
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove node with one child, no rotation")
  public void removeOneChildNoRotation() {
    // setup
    map.insert("2", "b");
    map.insert("1", "a");
    map.insert("3", "c");
    map.insert("4", "d");

    map.remove("3");
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove node with two children, single rotation")
  public void removeTwoChildrenSingleRotation() {
    map.insert("f","f");
    map.insert("d","d");
    map.insert("i","i");
    map.insert("b","b");
    map.insert("e","e");
    map.insert("g","g");
    map.insert("j","j");
    map.insert("c","c");
    map.insert("a","a");
    map.insert("h","h");
    map.insert("k","k");

    map.remove("g");
    System.out.println(map.toString());
  }

  @Test
  @DisplayName("Remove node with two children, no rotation")
  public void removeTwoChildrenNoRotation() {
    // setup
    map.insert("3","c");
    map.insert("2","b");
    map.insert("4","d");
    map.insert("1","a");

    map.remove("3");
    System.out.println(map.toString());
  }

}