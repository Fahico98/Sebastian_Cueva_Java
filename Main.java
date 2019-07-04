
import java.io.*;
import java.util.ArrayList;

public class Main {
   
   public static void main(String[] args) throws FileNotFoundException {
      RandomTestDataGenerator dataGenerator = new RandomTestDataGenerator();
      saveData(dataGenerator.generateStringList(10000, 8));
      BST<String> bst = fillBST();
      System.out.println("bst comparisonsAverage: " + String.format("%.4f", bst.getComparisonsAverage()));
      System.out.println("bst height: " + bst.getHeight());
   }
   
   public static void saveData(ArrayList<String> list){
      fileManager.openFileWriter("data");
      for (int i = 0; i < list.size(); i++) {
         fileManager.saveLine(list.get(i));
      }
      fileManager.closeFileWriter();
   }
   
   public static BST<String> fillBST(){
      BST<String> output = new BST();
      fileManager.openFileReader("data");
      fileManager.openFileWriter("comparisonsBST");
      while(true){
         String line = fileManager.loadLine();
         if(line != null){
            output.insert(line);
         }else{
            fileManager.closeFileReader();
            fileManager.closeFileWriter();
            output.computeComparisonsAverageAndHeght();
            break;
         }
      }
      return output;
   }
}
