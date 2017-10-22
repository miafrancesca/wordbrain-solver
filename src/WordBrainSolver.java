import java.util.*;
import java.io.*;

public class WordBrainSolver {
   public static void main(String[] args) throws FileNotFoundException {
      WordBrainHelper helper = new WordBrainHelper("dictionary.txt", 2, 9);
      Scanner s = new Scanner(System.in);
      
      String input = "";
      
      do {
         System.out.println("build matrix");
         char[][] matrix = buildMatrix(s);
         solve(matrix, helper, s);
         System.out.print("enter anotherm matrix? (or QUIT): ");
         input = s.nextLine();
      } while (!input.equals("QUIT"));
   }
   
   public static void solve(char[][] matrix, WordBrainHelper helper, Scanner s) {
      String input = "";
      do {
         System.out.print("enter length: ");
         int length = s.nextInt(); 
         s.nextLine();

         //System.out.println("char to start at: " + matrix[y][x]);
         //Set<String> solutions = helper.matrixSolver("" + matrix[y][x], length, matrix, x, y);
         //System.out.println(solutions);
         extraSolve(matrix, helper, length);
         
         System.out.print("continue? (or QUIT to enter new matrix): ");
         input = s.nextLine();
      } while (!input.equals("QUIT"));
   }
   
   public static void extraSolve(char[][] matrix, WordBrainHelper helper, int length) {
      for (int i = 0; i < matrix.length; i++) {
         for (int j = 0; j < matrix.length; j++) {
            Set<String> solutions = helper.matrixSolver("" + matrix[i][j], length, matrix, j, i);
            if (!solutions.isEmpty()) {
               System.out.println("starts with " + matrix[i][j] + ": " + solutions);
            }
         }
      }
   }
   
   public static char[][] buildMatrix(Scanner s) {
      System.out.print("enter sidelength: ");
      int length = s.nextInt();
      s.nextLine();
      char[][] matrix = new char[length][length];
      
      for (int i = 0; i < length; i++) {
         System.out.print("row " + i + ": ");
         String row = s.nextLine(); // need checks here
         for (int j = 0; j < length; j++) {
            matrix[i][j] = row.charAt(j);
         }
         System.out.println("row " + i + ": " + Arrays.toString(matrix[i]));
      }      
      
      return matrix;
   }
   
   public static void anagrams(Scanner s, WordBrainHelper helper) {
      String input = "";
      while (!input.equals("QUIT")) {
         System.out.print("enter prefix: ");
         String prefix = s.nextLine();
         System.out.print("enter length: ");
         int length = s.nextInt();
         s.nextLine();
         System.out.print("enter letters: ");
         String letters = s.nextLine();
         List<Character> chars = new ArrayList<Character>();
         
         for(Character c : letters.toCharArray()) {
            chars.add(c);
         }
         
         Set<String> possibleWords = helper.potentialWordsOfLength(prefix, length, chars);
         System.out.println(possibleWords);
      }
   }
}