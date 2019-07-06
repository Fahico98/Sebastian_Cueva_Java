
package SebastianCueva;

import java.io.*;
import java.util.regex.Matcher;

public class fileManager {
   
   public static final String MAIN_DIRECTORY = System.getProperty("user.dir").replaceAll(Matcher.quoteReplacement("\\"), "/"),
         SRC_DIRECTORY = MAIN_DIRECTORY + "/src",
         TEXTS_DIRECTORY = SRC_DIRECTORY + "/textFiles";
   
   public static BufferedWriter writer = null;
   public static BufferedReader reader = null;
   
   public static void openFileWriter(String fileName, boolean append){
      try{
         File file = new File(TEXTS_DIRECTORY + "/" + fileName + ".txt");
         writer = new BufferedWriter(new FileWriter(file, append));
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
   }
   
   public static void openFileReader(String fileName){
      try{
         File file = new File(TEXTS_DIRECTORY + "/" + fileName + ".txt");
         reader = new BufferedReader(new FileReader(file));
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
   }
   
   public static void closeFileWriter(){
      try{
         writer.close();
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
   }
   
   public static void closeFileReader(){
      try{
         reader.close();
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
   }
   
   public static boolean saveLine(String data){
      try{
         if(writer != null){
            writer.write(data);
            writer.newLine();
         }else{
            return(false);
         }
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
      return(true);
   }
   
   public static String loadLine(){
      try{
         if(reader != null){
            String line = reader.readLine();
            if(line == null || line.equals("")){
               return(null);
            }else{
               return(line);
            }
         }else{
            return(null);
         }
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
      return(null);
   }
   
    public static void cleanFile(String fileName){
      try{
         File file = new File(TEXTS_DIRECTORY + "/" + fileName + ".txt");
         writer = new BufferedWriter(new FileWriter(file, false));
         writer.write("");
         writer.close();
      }catch(IOException e){
         System.out.println(e.getMessage());
      }
   }
}
