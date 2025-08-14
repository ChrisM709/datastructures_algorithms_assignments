package org.backendapi.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {
  @Test
  void insertsMakeExpectedShape() {
    BinarySearchTree bst = new BinarySearchTree();
    for (int n : new int[]{5,1,7}) bst.insert(n);
    TreeNode r = bst.getRoot();
    assertNotNull(r);
    assertEquals(5, r.value);
    assertEquals(1, r.left.value);
    assertEquals(7, r.right.value);
  }
}
