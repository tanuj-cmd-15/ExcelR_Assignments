import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class FileOperations {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("File Operations Menu:");
            System.out.println("1. Create a new directory");
            System.out.println("2. Create a new text file and write content to it");
            System.out.println("3. Read content from an existing text file");
            System.out.println("4. Append new content to an existing text file");
            System.out.println("5. Copy content from one text file to another");
            System.out.println("6. Delete a text file");
            System.out.println("7. List all files and directories in a given directory");
            System.out.println("8. Search for a specific file in a directory and its subdirectories");
            System.out.println("9. Rename a file");
            System.out.println("10. Get information about a file");
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
                    System.out.print("Enter the directory path: ");
                    String dirPath = scanner.nextLine();
                    File dir = new File(dirPath);
                    if (dir.mkdirs()) {
                        System.out.println("Directory created successfully.");
                    } else {
                        System.out.println("Failed to create directory or directory already exists.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the file path: ");
                    String filePath = scanner.nextLine();
                    System.out.print("Enter content to write to the file: ");
                    String content = scanner.nextLine();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                        writer.write(content);
                        System.out.println("Content written to file successfully.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while writing to the file: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter the file path: ");
                    String readFilePath = scanner.nextLine();
                    try (BufferedReader reader = new BufferedReader(new FileReader(readFilePath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter the file path: ");
                    String appendFilePath = scanner.nextLine();
                    System.out.print("Enter content to append: ");
                    String appendContent = scanner.nextLine();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(appendFilePath, true))) {
                        writer.write(appendContent);
                        System.out.println("Content appended to file successfully.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while appending to the file: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Enter the source file path: ");
                    String sourceFilePath = scanner.nextLine();
                    System.out.print("Enter the destination file path: ");
                    String destFilePath = scanner.nextLine();
                    try {
                        Files.copy(Paths.get(sourceFilePath), Paths.get(destFilePath), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File copied successfully.");
                    } catch (IOException e) {
                        System.out.println("An error occurred while copying the file: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.print("Enter the file path: ");
                    String deleteFilePath = scanner.nextLine();
                    File fileToDelete = new File(deleteFilePath);
                    if (fileToDelete.delete()) {
                        System.out.println("File deleted successfully.");
                    } else {
                        System.out.println("Failed to delete the file or file does not exist.");
                    }
                    break;

                case 7:
                    System.out.print("Enter the directory path: ");
                    String listDirPath = scanner.nextLine();
                    File listDir = new File(listDirPath);
                    if (listDir.isDirectory()) {
                        File[] files = listDir.listFiles();
                        if (files != null) {
                            for (File file : files) {
                                System.out.println((file.isDirectory() ? "Directory: " : "File: ") + file.getName());
                            }
                        } else {
                            System.out.println("No files found in the directory.");
                        }
                    } else {
                        System.out.println("The specified path is not a directory.");
                    }
                    break;

                case 8:
                    System.out.print("Enter the directory path: ");
                    String searchDirPath = scanner.nextLine();
                    System.out.print("Enter the file name to search for: ");
                    String fileName = scanner.nextLine();
                    searchFile(new File(searchDirPath), fileName);
                    break;

                case 9:
                    System.out.print("Enter the current file path: ");
                    String oldFilePath = scanner.nextLine();
                    System.out.print("Enter the new file path: ");
                    String newFilePath = scanner.nextLine();
                    File oldFile = new File(oldFilePath);
                    File newFile = new File(newFilePath);
                    if (oldFile.renameTo(newFile)) {
                        System.out.println("File renamed successfully.");
                    } else {
                        System.out.println("Failed to rename the file or file does not exist.");
                    }
                    break;

                case 10:
                    System.out.print("Enter the file path: ");
                    String infoFilePath = scanner.nextLine();
                    File infoFile = new File(infoFilePath);
                    if (infoFile.exists()) {
                        System.out.println("File Size: " + infoFile.length() + " bytes");
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        System.out.println("Last Modified: " + sdf.format(new Date(infoFile.lastModified())));
                    } else {
                        System.out.println("File does not exist.");
                    }
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


    private static void searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFile(file, fileName); 
                } else if (file.getName().equalsIgnoreCase(fileName)) {
                    System.out.println("File found: " + file.getAbsolutePath());
                }
            }
        }
    }
}
