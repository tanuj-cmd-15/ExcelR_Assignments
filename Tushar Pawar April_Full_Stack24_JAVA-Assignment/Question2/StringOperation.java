import java.util.Scanner;

public class StringOperation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("String Operations Menu:");
            System.out.println("1. Concatenate Strings");
            System.out.println("2. Find Length of a String");
            System.out.println("3. Convert to Uppercase and Lowercase");
            System.out.println("4. Extract Substring");
            System.out.println("5. Split a Sentence");
            System.out.println("6. Reverse a String");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
                System.out.print("Enter your choice: ");
            }
            
            choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    System.out.print("Enter the first string: ");
                    String str1 = scanner.nextLine();
                    System.out.print("Enter the second string: ");
                    String str2 = scanner.nextLine();
                    System.out.println("Concatenated String: " + str1 + str2);
                    break;

                case 2:
                    System.out.print("Enter a string: ");
                    String strLength = scanner.nextLine();
                    System.out.println("Length of the string: " + strLength.length());
                    break;

                case 3:
                    System.out.print("Enter a string: ");
                    String strCase = scanner.nextLine();
                    System.out.println("Uppercase: " + strCase.toUpperCase());
                    System.out.println("Lowercase: " + strCase.toLowerCase());
                    break;

                case 4:
                    System.out.print("Enter a string: ");
                    String strSubstring = scanner.nextLine();
                    System.out.print("Enter starting index: ");
                    
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next(); 
                        System.out.print("Enter starting index: ");
                    }
                    int startIndex = scanner.nextInt();
                    
                    System.out.print("Enter ending index: ");
                    
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a number.");
                        scanner.next(); 
                        System.out.print("Enter ending index: ");
                    }
                    int endIndex = scanner.nextInt();
                    scanner.nextLine(); 
                    
                    if (startIndex >= 0 && endIndex <= strSubstring.length() && startIndex < endIndex) {
                        System.out.println("Substring: " + strSubstring.substring(startIndex, endIndex));
                    } else {
                        System.out.println("Invalid indices.");
                    }
                    break;

                case 5:
                    System.out.print("Enter a sentence: ");
                    String sentence = scanner.nextLine();
                    String[] words = sentence.split("\\s+");
                    System.out.println("Words in the sentence:");
                    for (String word : words) {
                        System.out.println(word);
                    }
                    break;

                case 6:
                    System.out.print("Enter a string: ");
                    String strReverse = scanner.nextLine();
                    String reversedString = new StringBuilder(strReverse).reverse().toString();
                    System.out.println("Reversed String: " + reversedString);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
        } while (choice != 0);
        
        scanner.close();
    }
}
