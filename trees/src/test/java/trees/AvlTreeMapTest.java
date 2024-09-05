package trees;

import trees.bst.AvlTreeMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * In addition to the tests in BinarySearchTreeMapTest (and in OrderedMapTest & MapTest),
 * we add tests specific to AVL Tree.
 */
@SuppressWarnings("All")
public class AvlTreeMapTest extends BinarySearchTreeMapTest {

  @Override
  protected Map<String, String> createMap() {
    return new AvlTreeMap<>();
  }

  @Test
  @DisplayName("Test that map automatically rotates left when needed")
  public void insertLeftRotation() {
    map.insert("1", "a");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a
     */

    map.insert("2", "b");
    // System.out.println(avl.toString());
    // must print
    /*
        1:a,
        null 2:b
     */

    map.insert("3", "c"); // it must do a left rotation here!
    // System.out.println(avl.toString());
    // must print
    /*
        2:b,
        1:a 3:c
     */

    String[] expected = new String[]{
        "2:b",
        "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Test that map automatically rotates right when needed")
  public void insertRightRotation() {
    map.insert("3","c");
    map.insert("2","b"); // current 2 <-3 ,  balanced
    map.insert("1","a"); // current 1 <- 2 <- 3 , need to rotate right
    // should be 1 <- 2 -> 3
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Insert, right-left rotation")
  public void insertRightLeftRotation() {
    map.insert("1","a");
    map.insert("3","c");
    map.insert("2","b");
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Insert, left-right rotation")
  public void insertLeftRightRotation() {
    map.insert("3","c");
    map.insert("1","a");
    map.insert("2","b");
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Insert, no rotation")
  public void insertNoRotation() {
    map.insert("2","b");
    map.insert("3","c");
    map.insert("1","a");
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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
    String[] expected = new String[]{
            "3:c",
            "2:b 4:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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
    String[] expected = new String[]{
            "3:c",
            "2:b 4:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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
    String[] expected = new String[]{
            "2:b",
            "1:a 3:c"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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
    String[] expected = new String[]{
            "3:c",
            "2:b 4:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
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

    String[] expected = new String[]{
            "h:h",
            "e:e j:j",
            "c:c g:g i:i l:l",
            "a:a d:d f:f null null null k:k m:m"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Remove node with one child, no rotation")
  public void removeOneChildNoRotation() {
    // setup
    map.insert("2","b");
    map.insert("1","a");
    map.insert("3","c");
    map.insert("4","d");

    map.remove("3");
    String[] expected = new String[]{
            "2:b",
            "1:a 4:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }

  @Test
  @DisplayName("Remove node with two children, single rotation")
  public void removeTwoChildrenSingleRotation() {
    //setup
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

    map.remove("g"); // has two children
    //replaced with 5, without rotation, there is an imbalance in the left subtree
    String[] expected = new String[]{
            "f:f",
            "d:d i:i",
            "b:b e:e h:h j:j",
            "a:a c:c null null null null null k:k"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());

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
    String[] expected = new String[]{
            "2:b",
            "1:a 4:d"
    };
    assertEquals((String.join("\n", expected) + "\n"), map.toString());
  }
}
