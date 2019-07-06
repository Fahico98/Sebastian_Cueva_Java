package SebastianCueva;

import java.util.*;

public interface Tree<E> extends Collection<E>
{
  static LinkedList<Object> l = new LinkedList<>();
  //public E get(int index);
  /** Return true if the element is in the tree */
  public boolean search(E e);

  /** Insert element e into the binary tree
   * Return true if the element is inserted successfully */
  public boolean insert(E e);
  
  public void computeComparisonsAverage();

  /** Delete the specified element from the tree
   * Return true if the element is deleted successfully */
  public boolean delete(E e);
  
  /** Get the number of elements in the tree */
  public int getSize();
  
  
  /** Inorder traversal from the root*/
  public default void inorder() {
  }

  /** Postorder traversal from the root */
  public default void postorder() {
  }

  /** Preorder traversal from the root */
  public default void preorder() {
  }
  
  @Override /** Return true if the tree is empty */
  public default boolean isEmpty() {
    return this.size() == 0;
  }

 

  @Override
  public default boolean contains(Object e) {
    return search((E)e);
  }
  
  @Override
  public default boolean add(E e) {
    return insert(e);
  }
  
  @Override
  public default boolean remove(Object e) {
    return delete((E)e);
  }
  
  @Override
  public default int size() {
    return getSize();
  }
  
   @Override
  public default boolean containsAll(Collection<?> c) 
  {
    boolean ver = false;

    for (Object ele : c) 
    {
      if(contains(ele))
      {
        ver = false; break;
      }    
      else
      {
        ver = true;
      }  
    }

    return ver;
  }

  @Override
  public default boolean addAll(Collection<? extends E> c)
  {
    
    boolean c1 = false;

   for (E ele : c ) 
   {
      if(!contains(ele))
      {
        add(ele); c1 = true;
      }     
   }

    return c1;
  }


  
  @Override
  public default boolean removeAll(Collection<?> c) 
  {
    // Left as an exercise
    
    boolean c2 = false;

    for (int e = 0; e < c.size(); e++) 
    {
        if(contains(e))
        {
            remove(e);
            c2 = true;
        }     
    }

    return c2;
  }


  @Override
  public default boolean retainAll(Collection <?> c)
  {
    
      boolean v = false;


      for (Object o : c )
      {
          if(contains((E)c))
          {
              v = true;
              this.delete((E)c);
          } 
      }

      return v;
  }


  @Override
  public default Object[] toArray()
  {
    Object [] arr = new Object[getSize()];
    int z = 0;

    return arr;
  }

  @Override
  public default <T> T[] toArray(T[] array) {
    Object [] arr = new Object[getSize()];
    int z = 0;

    return array;
  }

}