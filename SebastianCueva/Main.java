
package SebastianCueva;

import java.io.*;
import java.util.ArrayList;

public class Main {
   
   public static void main(String[] args) throws FileNotFoundException, InterruptedException {
      RandomTestDataGenerator dataGenerator = new RandomTestDataGenerator();
      
      saveData(dataGenerator.generateStringList(10000, 8));
      
      BST<String> bst = (BST<String>)loadTree("BST");
      AVLTree<String> avlt = (AVLTree<String>)loadTree("AVLT");
      MyHashMap_LinearProbing<String, String> linearProbingMap = (MyHashMap_LinearProbing)loadHashMap("linearProbing");
      MyHashMap_QuadraticProbing<String, String> quadraticProbingMap = (MyHashMap_QuadraticProbing)loadHashMap("quadraticProbing");
      MyHashMap_DoubleHashing<String, String> doubleHashingMap = (MyHashMap_DoubleHashing)loadHashMap("doubleHashing");
      
      System.out.println("A continuación se muestran los datos obtenidos al incertar 10000 cadenas\n"
            + "de texto de 8 caracteres cada una dentro de diferentes estructuras de datos...\n\n");
      
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
      
      System.out.println(bst.getComparisonsData());
      System.out.println(avlt.getComparisonsData() + "\n");
      
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
      
      System.out.println(linearProbingMap.getCollisionsData());
      
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
      
      System.out.println(quadraticProbingMap.getCollisionsData());
      
      System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n");
      
      System.out.println(doubleHashingMap.getCollisionsData());
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
      fileManager.closeFileReader();
      return(output);
   }
}
