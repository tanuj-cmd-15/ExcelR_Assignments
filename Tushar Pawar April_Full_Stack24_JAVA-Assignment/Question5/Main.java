import java.util.*;
import java.util.Scanner;

abstract class CollectionManager {
    abstract void addElement();
    abstract void removeElement();
    abstract void displayElements();
}

class ListManager extends CollectionManager {
    private List<String> list = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    void addElement() {
        System.out.print("Enter element to add to the list: ");
        String element = scanner.nextLine();
        list.add(element);
        System.out.println("Element added.");
    }

    @Override
    void removeElement() {
        System.out.print("Enter element to remove from the list: ");
        String element = scanner.nextLine();
        if (list.remove(element)) {
            System.out.println("Element removed.");
        } else {
            System.out.println("Element not found.");
        }
    }

    @Override
    void displayElements() {
        System.out.println("List elements: " + list);
    }
}

class SetManager extends CollectionManager {
    private Set<String> set = new HashSet<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    void addElement() {
        System.out.print("Enter element to add to the set: ");
        String element = scanner.nextLine();
        if (set.add(element)) {
            System.out.println("Element added.");
        } else {
            System.out.println("Element already exists.");
        }
    }

    @Override
    void removeElement() {
        System.out.print("Enter element to remove from the set: ");
        String element = scanner.nextLine();
        if (set.remove(element)) {
            System.out.println("Element removed.");
        } else {
            System.out.println("Element not found.");
        }
    }

    @Override
    void displayElements() {
        System.out.println("Set elements: " + set);
    }
}

class MapManager extends CollectionManager {
    private Map<String, String> map = new TreeMap<>();
    private Scanner scanner = new Scanner(System.in);

    @Override
    void addElement() {
        System.out.print("Enter key: ");
        String key = scanner.nextLine();
        System.out.print("Enter value: ");
        String value = scanner.nextLine();
        if (map.containsKey(key)) {
            System.out.println("Key already exists. Updating value.");
        }
        map.put(key, value);
        System.out.println("Key-Value pair added.");
    }

    @Override
    void removeElement() {
        System.out.print("Enter key to remove from the map: ");
        String key = scanner.nextLine();
        if (map.remove(key) != null) {
            System.out.println("Key-Value pair removed.");
        } else {
            System.out.println("Key not found.");
        }
    }

    @Override
    void displayElements() {
        System.out.println("Map elements: " + map);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CollectionManager listManager = new ListManager();
        CollectionManager setManager = new SetManager();
        CollectionManager mapManager = new MapManager();

        int choice;
        do {
            System.out.println("Collection Management System:");
            System.out.println("1. Manage Lists");
            System.out.println("2. Manage Sets");
            System.out.println("3. Manage Maps");
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
                    manageCollection(listManager);
                    break;
                case 2:
                    manageCollection(setManager);
                    break;
                case 3:
                    manageCollection(mapManager);
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

    private static void manageCollection(CollectionManager manager) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Collection Management Menu:");
            System.out.println("1. Add element        ");
            System.out.println("2. Remove element      ");
            System.out.println("3. Display elements    ");
            System.out.println("0. Back to main menu   ");
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
                    manager.addElement();
                    break;
                case 2:
                    manager.removeElement();
                    break;
                case 3:
                    manager.displayElements();
                    break;
                case 0:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 0);
    }
}
