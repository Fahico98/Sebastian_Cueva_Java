package SebastianCueva;


import java.io.*;
import java.util.ArrayList;

public class Main {
   
   public static void main(String[] args) throws FileNotFoundException, InterruptedException {
      RandomTestDataGenerator dataGenerator = new RandomTestDataGenerator();
      saveData(dataGenerator.generateStringList(10000, 8));
      
      MyHashMap_LinearProbing<String, String> linearProbingMap = (MyHashMap_LinearProbing)loadHashMap("linearProbing");
      MyHashMap_QuadraticProbing<String, String> linearQuadraticMap = (MyHashMap_QuadraticProbing)loadHashMap("quadraticProbing");
      MyHashMap_DoubleHashing<String, String> doubleHashingMap = (MyHashMap_DoubleHashing)loadHashMap("doubleHashing");
      
      /*
      BST<String> bst = (BST<String>)loadTree("BST");
      AVLTree<String> avlt = (AVLTree<String>)loadTree("AVLT");
      System.out.println("bst comparisonsAverage: " + String.format("%.4f", bst.getComparisonsAverage()));
      System.out.println("bst height: " + bst.getHeight());
      System.out.println("");
      System.out.println("avlt comparisonsAverage: " + String.format("%.4f", avlt.getComparisonsAverage()));
      System.out.println("avlt height: " + avlt.getHeight());
      System.out.println("avlt spinningLL: " + avlt.getSpinningLL());
      System.out.println("avlt spinningLR: " + avlt.getSpinningLR());
      System.out.println("avlt spinningRR: " + avlt.getSpinningRR());
      System.out.println("avlt spinningRL: " + avlt.getSpinningRL());
      */
   }
   
   public static void saveData(ArrayList<String> list){
      fileManager.openFileWriter("data", false);
      for (int i = 0; i < list.size(); i++) {
         fileManager.saveLine(list.get(i));
      }
      fileManager.closeFileWriter();
   }
   
   public static Tree<String> loadTree(String treeClass){
      Tree<String> output = null;
      fileManager.openFileReader("data");
      if(treeClass.equals("AVLT")){
         output = new AVLTree();
         fileManager.openFileWriter("comparisonsAVLT", false);
      }else if(treeClass.equals("BST")){
         output = new BST();
         fileManager.openFileWriter("comparisonsBST", false);
      }
      while(true){
         String line = fileManager.loadLine();
         if(line != null){
            output.insert(line);
         }else{
            break;
         }
      }
      fileManager.closeFileReader();
      fileManager.closeFileWriter();
      output.computeComparisonsAverage();
      return output;
   }
   
   public static MyMap<String, String> loadHashMap(String hashMapType){
      MyMap<String, String> output = null;
      switch(hashMapType){
         case "linearProbing":
            output = new MyHashMap_LinearProbing();
            fileManager.cleanFile("linearProbing");
            break;
         case "quadraticProbing":
            output = new MyHashMap_QuadraticProbing();
            fileManager.cleanFile("quadraticProbing");
            break;
         case "doubleHashing":
            output = new MyHashMap_DoubleHashing();
            fileManager.cleanFile("doubleHashing");
            break;
      }
      fileManager.openFileReader("data");
      while(true){
         String line = fileManager.loadLine();
         if(line != null){
            output.put(line, line);
         }else{
            break;
         }
      }
      return(output);
   }
}
