package org.backendapi.util;

public class BinarySearchTree {
  private TreeNode root;
  public void insert(int v){ root = insertRec(root, v); }
  public TreeNode getRoot(){ return root; }

  private TreeNode insertRec(TreeNode n, int v){
    if(n == null) return new TreeNode(v);
    if(v < n.value) n.left  = insertRec(n.left, v);
    else if(v > n.value) n.right = insertRec(n.right, v);
    return n;
  }
}
