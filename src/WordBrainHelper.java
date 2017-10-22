import java.util.*;
import java.io.*;

public class WordBrainHelper {
   private Node root;
   
   public WordBrainHelper(String fileName, int minLength, int maxLength)
         throws FileNotFoundException {
      List<String> words = new ArrayList<String>();
      Scanner r = new Scanner(new File(fileName));
      while (r.hasNextLine()) {
         String word = r.next();
         if (word.length() > minLength && word.length() < maxLength) {
            words.add(word);
         }
      }
      
      this.addAll(words);
   }
   
   public void addAll(List<String> words) {
      for (String word : words) {
         this.add(word);
      }
   }
   
   public void printWords() {
      this.printWords(this.root, new Stack<Character>());
   }
   
   private void printWords(Node current, Stack<Character> acc) {
      if (current != null) {
         if (current.isWord) {
            System.out.println(acc);
         }
         
         for (Character c : current.children.keySet()) {
            acc.push(c);
            printWords(current.children.get(c), acc);
            acc.pop();
         }
      }
   }
   
   public Set<String> matrixSolver(String prefix, int length, char[][] matrix, 
         int startX, int startY) {
      Set<String> acc = new HashSet<String>();
      Node start = this.traverse(prefix, 0, this.root);
      boolean[][] visited = new boolean[matrix.length][matrix.length];
      visited[startY][startX] = true;
      this.matrixSolver(start, acc, "", prefix, length, matrix, startY, startX, visited);
      return acc;
   }
   
   private void matrixSolver(Node current, Set<String> acc, String word, 
         String prefix, int length, char[][] matrix, int row, int col, 
         boolean[][] visited) {
      //System.out.println(prefix + word);
      //System.out.println(length);
      if (current != null && (length >= word.length() + prefix.length() || length == -1)) {
         //System.out.println("entered here");   
         if (current.isWord && (length == word.length() + prefix.length() || length==-1)) {
            //System.out.println("adding " + prefix + word + " at length " + (word.length() + prefix.length()));
            acc.add(prefix + word);
         } else {
            //System.out.println(prefix+word);
         }
         
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               //System.out.println(col+j + ", " + (row + i));
               if (col + j < matrix.length && col + j >= 0 &&
                     row+i < matrix.length && row + i >= 0 && !visited[row+i][col+j]) {
                  char c =  matrix[row+i][col+j];
                  if (current.children.containsKey(c)) {
                     //System.out.println("exploring " + c);
                     visited[row+i][col+j] = true;
                     this.matrixSolver(current.children.get(c), acc, word + c, 
                           prefix, length, matrix, row+i, col+j, visited);
                     visited[row+i][col+j] = false;
                  }
               }
            }
         }
      }
   }
   
   
   // -1 for no restriction
   public Set<String> potentialWordsOfLength(String prefix, int length, List<Character> letters) {
      Node current = this.traverse(prefix, 0, this.root);
      if (current == null) {
         return new HashSet<String>();
      }
      
      Set<String> words = new TreeSet<String>();
      this.potentialWords(current, words, "", prefix, length - prefix.length(), letters);
      return words;
   }
   
   private void potentialWords(Node current, Set<String> acc, String word, 
         String prefix, int length, List<Character> letters) {
      if (current != null && (length >= word.length() || length == -1) && !letters.isEmpty()) {
         if (current.isWord && word.length() == length) {
            acc.add(prefix + word);
         }
         
         for (int i = 0; i < letters.size(); i++) {
            char c = letters.get(i);
            if (current.children.containsKey(c)) {
               letters.remove(i);
               this.potentialWords(current.children.get(c), acc, word + c, prefix, length, letters);
               letters.add(i, c);
            }
         }
      }
   }
         
   public boolean hasPrefix(String prefix) {
      return this.traverse(prefix, 0, this.root) != null;
   }
   
   private Node traverse(String prefix, int index, Node current) {
      if (index == prefix.length()) {
         return current;
      }
      
      if (current == null) {
         return null;
      }
      
      return traverse(prefix, index+1, current.children.get(prefix.charAt(index)));
   }
   
   public void add(String word) {
      this.root = this.add(word, 0, this.root);
   }
   
   private Node add(String word, int index, Node current) {
      if (index < word.length()) {
         if (current == null) {
            current = new Node(false);
         }
         Node child = null;
         if (current.children.containsKey(word.charAt(index))) {
            child = current.children.get(word.charAt(index));
         }
         child = this.add(word, index + 1, child);
         current.children.put(word.charAt(index), child); // redundant step to put back in
         // can use reference semantics magic instead
      } else if (index == word.length()) {
         if (current == null) {
            current = new Node(true);
         } else {
            current.isWord = true;
         }
      }
      return current;
   }
      
   private class Node {
      public boolean isWord;
      public HashMap<Character, Node> children;
      
      public Node(boolean isWord, HashMap<Character, Node> children) {
         this.isWord = isWord;
         this.children = children;
      }
      
      public Node(boolean isWord) {
         this(isWord, new HashMap<Character, Node>());
      }
   }
}