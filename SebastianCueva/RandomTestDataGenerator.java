package SebastianCueva;


import java.util.ArrayList;
import java.util.Random;

public class RandomTestDataGenerator {
   
   private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
   private static final int ALPHA_NUMERIC_STRING_LENGTH = ALPHA_NUMERIC_STRING.length();
   private Random rand = new Random();
   
   public ArrayList<String> generateStringList(int listLength, int stringLength){
      ArrayList<String> output = new ArrayList();
      for (int i = 0; i < listLength; i++) {
         String buffer = generateString(stringLength);
         if(!output.contains(buffer)){
            output.add(buffer);
         }else{
            i--;
         }
      }
      return output;
   }
   
   private String generateString(int stringLength){
      String str = "";
      int index = 0;
      for (int i = 0; i < stringLength; i++) {
         index = rand.nextInt(ALPHA_NUMERIC_STRING_LENGTH - 1);
         str += ALPHA_NUMERIC_STRING.substring(index, index + 1);
      }
      return str;
   }
}
