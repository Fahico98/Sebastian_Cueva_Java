
package SebastianCueva;

import java.util.*;

public class MyHashMap_LinearProbing<K, V> implements MyMap<K, V>{
   
   private final static int DEFAULT_INITIAL_CAPACITY = 5000, MAXIMUM_CAPACITY = 1 << 30, B = 3;
   private final static float DEFAULT_MAX_LOAD_FACTOR = 0.7f, INCREMENT = 0.5f;
   private int capacity, size, collisions, linearProbingCollisions, noCollision, increaseCases;
   private float loadFactorThreshold;
   private Entry<K, V>[] table;
   
   public MyHashMap_LinearProbing(){
      this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
   }
   
   private MyHashMap_LinearProbing(int initialCapacity, float loadFactorThreshold){
      if(initialCapacity > MAXIMUM_CAPACITY){
         this.capacity = MAXIMUM_CAPACITY;
      }else{
         this.capacity = initialCapacity;
      }
      this.loadFactorThreshold = loadFactorThreshold;  
      table = new Entry[capacity];
      size = 0;
   }
   
   @Override
   public boolean containsValue(V value) {
      for(int i = 0; i < capacity; i++){
         if(table[i] != null){
            Entry<K, V> entry = table[i];
            if(entry.getValue().equals(value)){
               return(true);
            }
         }
      }
      return(false);
   }
   
   @Override
   public Set<Entry<K,V>> entrySet() {
      Set<Entry<K, V>> set = new HashSet<>();
      for(int i = 0; i < capacity; i++){
         if(table[i] != null){
            Entry<K, V> entry = table[i];
            set.add(entry);
         }
      }
      return((set.isEmpty()) ? null : set);
   }
   
   @Override
   public V get(K key){
      int hashIndex = hash(key);
      while(true){
         if(table[hashIndex] != null){
            Entry<K, V> entry = table[hashIndex];
            if(entry.getKey().equals(key)){
               return(entry.getValue());
            }else{
               hashIndex = (hashIndex == (capacity - 1)) ? 0 : hashIndex + 1;
            }
         }else{
            return(null);
         }
      }
   }
   
   @Override
   public Set<K> keySet() {
      Set<K> set = new HashSet();
      for(int i = 0; i < capacity; i++){
         if (table[i] != null) {
            Entry<K, V> entry = table[i];
            set.add(entry.getKey()); 
         }
      }
      return((set.isEmpty()) ? null : set);
   }
   
   @Override
   public void put(K key, V value){
      boolean collision = false;
      if(get(key) == null){
         int hashIndex = hash(key);
         while(true){
            if(table[hashIndex] != null){
               collision = true;
               linearProbingCollisions++;
               hashIndex = (hashIndex == (capacity - 1)) ? 0 : hashIndex + 1;
            }else{
               table[hashIndex] = new Entry(key, value);
               if(collision){
                  collisions++;
               }else{
                  noCollision++;
               }
               size++;
               break;
            }
         }
         if(size >= capacity * loadFactorThreshold){
            if(capacity == MAXIMUM_CAPACITY){
               throw new RuntimeException("Exceeding maximum capacity");
            }
            rehash();
         }
      }
   }
   
   @Override
   public void remove(K key) {
      if(get(key) != null){
         int hashIndex = hash(key);
         Entry<K, V> entry = table[hashIndex];
         table[hashIndex] = null;
         size--;
      }
   }
   
   @Override
   public int size() {
      return size;
   }
   
   @Override
   public java.util.Set<V> values() {
      Set<V> set = new HashSet();
      for(int i = 0; i < capacity; i++){
         if(table[i] != null){
            Entry<K, V> entry = table[i];
            set.add(entry.getValue()); 
         }
      }
      return(set);
   }
   
   private int hash(K key){
      int k = 0;
      String strValue = (String)key;
      int n = strValue.length();
      char[] asciiArray = strValue.toCharArray();
      for(int i = 0; i < n; i++){
         k += ((int)asciiArray[i]) * (int)Math.pow(B, (n - (i + 1)));
      }
      return(k % capacity);
   }
   
   private void rehash() {
      increaseCases++;
      saveCollisionsData();
      Set<Entry<K, V>> set = entrySet();
      capacity += (capacity * INCREMENT);
      table = new Entry[capacity];
      size = 0;
      for(Entry<K, V> entry : set){
         put(entry.getKey(), entry.getValue());
      }
   }
   
   private void saveCollisionsData(){
      fileManager.openFileWriter("linearProbing", true);
      fileManager.saveLine(Integer.toString(increaseCases));
      fileManager.saveLine(Integer.toString(noCollision));
      fileManager.saveLine(Integer.toString(collisions));
      fileManager.saveLine(Integer.toString(linearProbingCollisions));
      noCollision = 0;
      collisions = 0;
      linearProbingCollisions = 0;
      fileManager.closeFileWriter();
   }

   @Override
   public void clear(){
      size = 0;
      removeEntries();
   }
   
   private void removeEntries(){
      for(int i = 0; i < capacity; i++) {
         if (table[i] != null){
            table[i] = null;
         }
      }
   }

   @Override
   public boolean containsKey(K key){
      return((get(key) != null) ? true : false);
   }
   
   @Override
   public boolean isEmpty() {
      return(size == 0);
   }
   
   public String getCollisionsData(){
      fileManager.openFileReader("linearProbing");
      String line, output = "";
      while(true){
         line = fileManager.loadLine();
         if(line != null){
            output += "Datos antes de la ampliación número " + line + " de la clase MyHashMap_LinearProbing.java.\n";
            output += "- entradas sin colición: " + fileManager.loadLine() + "\n";
            output += "- entradas que colicionaron: " + fileManager.loadLine() + "\n";
            output += "- coliciones totales debido al método Lineal Probing: " + fileManager.loadLine() + "\n\n";
         }else{
            fileManager.closeFileReader();
            break;
         }
      }
      return(output);
   }
}
