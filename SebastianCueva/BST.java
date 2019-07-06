package SebastianCueva;

import java.util.*;

public class BST<E extends Comparable<E>> implements Tree<E> {
   
   protected TreeNode<E> root;
   protected int size = 0, height = 0;
   private float comparisonsAverage = 0;

  /** Create a default binary tree */
  public BST() {
  }

  /** Create a binary tree from an array of objects */
  public BST(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      add(objects[i]);
  }

  @Override /** Returns true if the element is in the tree */
  public boolean search(E e) {
    TreeNode<E> current = root; // Start from the root

    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        current = current.right;
      }
      else // element matches current.element
        return true; // Element is found
    }

    return false;
  }

   @Override
   /**
    * Insert element e into the binary tree
    * Return true if the element is inserted successfully
    */
   public boolean insert(E e) {
      int comparisons = 0;
      if(root == null){
         root = createNewNode(e); // Create a new root
         root.level = 0;
      }else{
         // Locate the parent node
         TreeNode<E> parent = null;
         TreeNode<E> current = root;
         while(current != null){
            if(e.compareTo(current.element) < 0){
               comparisons++;
               parent = current;
               current = current.left;
            }else if(e.compareTo(current.element) > 0){
               comparisons++;
               parent = current;
               current = current.right;
            }else{
               return(false); // Duplicate node not inserted
            }
         }
         // Create the new node and attach it to the parent node
         if(e.compareTo(parent.element) < 0){
            parent.left = createNewNode(e);
            parent.left.level = parent.level + 1;
         }else{
            parent.right = createNewNode(e);
            parent.right.level = parent.level + 1;
         }
         int tempLevel = parent.level + 1;
         if(tempLevel > height){
            height = tempLevel;
         }
         comparisons++;
      }
      if(this.getClass().getName().equals("BST")){
         fileManager.saveLine(Integer.toString(comparisons));
      }else if(this.getClass().getName().equals("AVLTree")){
         ((AVLTree)this).tempComparisons = comparisons;
      }
      size++;
      return(true); // Element inserted successfully
   }
   
   /**
    * Save the comparisons amount into BSTComparisons.txt file.
    */
   public void computeComparisonsAverage(){
      if(this.getClass().getName().equals("BST")){
         fileManager.openFileReader("comparisonsBST");
      }else if(this.getClass().getName().equals("AVLTree")){
         fileManager.openFileReader("comparisonsAVLT");
      }
      int comparisons = 0;
      while(true){
         String line = fileManager.loadLine();
         if(line != null){
            comparisons += Integer.parseInt(line);
         }else{
            fileManager.closeFileReader();
            break;
         }
      }
      comparisonsAverage = (float) comparisons / size;
   }
   
   /**
    * @return the Search Binary Tree comparisonsAverage float attribute.
    */
   public float getComparisonsAverage(){
      return this.comparisonsAverage;
   }
   
   /**
    * @return the Search Binary Tree height attribute.
    */
   public int getHeight(){
      return this.height;
   }

   protected TreeNode<E> createNewNode(E e) {
      return new TreeNode<>(e);
   }

  @Override /** Inorder traversal from the root */
  public void inorder() {
    inorder(root);
  }

  /** Inorder traversal from a subtree */
  protected void inorder(TreeNode<E> root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  @Override /** Postorder traversal from the root */
  public void postorder() {
    postorder(root);
  }

  /** Postorder traversal from a subtree */
  protected void postorder(TreeNode<E> root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  @Override /** Preorder traversal from the root */
  public void preorder() {
    preorder(root);
  }

  /** Preorder traversal from a subtree */
  protected void preorder(TreeNode<E> root) {
    if (root == null) return;
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

   /**
    * This inner class is static, because it does not access 
    * any instance members defined in its outer class.
    */
   public static class TreeNode<E> {
      public E element;
      public TreeNode<E> left;
      public TreeNode<E> right;
      public int level;

      public TreeNode(E e) {
         element = e;
         level = 0;
      }
   }

  @Override /** Get the number of nodes in the tree */
  public int getSize() {
    return size;
  }

  /** Returns the root of the tree */
  public TreeNode<E> getRoot() {
    return root;
  }

   /** Returns a path from the root leading to the specified element
    * @param e
    * @return
    */
   public ArrayList<TreeNode<E>> path(E e) {
      ArrayList<TreeNode<E>> list = new ArrayList<>();
      TreeNode<E> current = root; // Start from the root
      while(current != null){
         list.add(current); // Add the node to the list
         if(this.getClass().getName().equals("AVLTree")){
            ((AVLTree)this).tempComparisons++;
         }
         if(e.compareTo(current.element) < 0){
            current = current.left;
         }else if(e.compareTo(current.element) > 0){
            current = current.right;
         }else{
            break;
         }
      }
      return(list); // Return an array list of nodes
   }

  @Override /** Delete an element from the binary tree.
   * Return true if the element is deleted successfully
   * Return false if the element is not in the tree */
  public boolean delete(E e) {
    // Locate the node to be deleted and also locate its parent node
    TreeNode<E> parent = null;
    TreeNode<E> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        parent = current;
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        parent = current;
        current = current.right;
      }
      else
        break; // Element is in the tree pointed at by current
    }

    if (current == null)
      return false; // Element is not in the tree

    // Case 1: current has no left child
    if (current.left == null) {
      // Connect the parent with the right child of the current node
      if (parent == null) {
        root = current.right;
      }
      else {
        if (e.compareTo(parent.element) < 0)
          parent.left = current.right;
        else
          parent.right = current.right;
      }
    }
    else {
      // Case 2: The current node has a left child
      // Locate the rightmost node in the left subtree of
      // the current node and also its parent
      TreeNode<E> parentOfRightMost = current;
      TreeNode<E> rightMost = current.left;

      while (rightMost.right != null) {
        parentOfRightMost = rightMost;
        rightMost = rightMost.right; // Keep going to the right
      }

      // Replace the element in current by the element in rightMost
      current.element = rightMost.element;

      // Eliminate rightmost node
      if (parentOfRightMost.right == rightMost)
        parentOfRightMost.right = rightMost.left;
      else
        // Special case: parentOfRightMost == current
        parentOfRightMost.left = rightMost.left;     
    }

    size--;
    return true; // Element deleted successfully
  }

  @Override /** Obtain an iterator. Use inorder. */
  public java.util.Iterator<E> iterator() {
    return new InorderIterator();
  }

  // Inner class InorderIterator
  private class InorderIterator implements java.util.Iterator<E> {
    // Store the elements in a list
    private java.util.ArrayList<E> list =
      new java.util.ArrayList<>();
    private int current = 0; // Point to the current element in list

    public InorderIterator() {
      inorder(); // Traverse binary tree and store elements in list
    }

    /** Inorder traversal from the root*/
    private void inorder() {
      inorder(root);
    }

    /** Inorder traversal from a subtree */
    private void inorder(TreeNode<E> root) {
      if (root == null)return;
      inorder(root.left);
      list.add(root.element);
      inorder(root.right);
    }

    @Override /** More elements for traversing? */
    public boolean hasNext() {
      if (current < list.size())
        return true;

      return false;
    }

    @Override /** Get the current element and move to the next */
    public E next() {
      return list.get(current++);
    }

    @Override // Remove the element returned by the last next()
    public void remove() {
    	if (current == 0) // next() has not been called yet
        throw new IllegalStateException(); 

    	delete(list.get(--current)); 
      list.clear(); // Clear the list
      inorder(); // Rebuild the list
    }
  }

  @Override /** Remove all elements from the tree */
  public void clear() {
    root = null;
    size = 0;
  }












  public int getNumberOfLeaves()
  {
    return getNumberOfLeaves(root);
    
  }

  private int getNumberOfLeaves(TreeNode<E> root)
  {
    if(root == null)
    {
      return 0;
    }

    else if(root.left == null && root.right == null)
    {
      return 1;
    }

    else
    {
      return getNumberOfLeaves(root.left) + getNumberOfLeaves(root.right);
    }

  }

  public int getNumberOfNonLeaves()
  {
      return size - getNumberOfLeaves(root);
  }



}