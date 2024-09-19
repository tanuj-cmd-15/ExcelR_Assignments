import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Employee {
    private String id;
    private String name;
    private String department;
    private double salary;

    public Employee(String id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Department: " + department + ", Salary: $" + salary;
    }
}

class EmployeeManager {
    private Map<String, Employee> employeeMap = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void addEmployee() {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine();
        if (employeeMap.containsKey(id)) {
            System.out.println("Employee ID already exists.");
            return;
        }
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); 

        Employee employee = new Employee(id, name, department, salary);
        employeeMap.put(id, employee);
        System.out.println("Employee added.");
    }

    public void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        String id = scanner.nextLine();
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            System.out.println("Employee not found.");
            return;
        }
        System.out.print("Enter new Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            employee.setName(name);
        }
        System.out.print("Enter new Department (leave blank to keep current): ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) {
            employee.setDepartment(department);
        }
        System.out.print("Enter new Salary (leave blank to keep current): ");
        String salaryInput = scanner.nextLine();
        if (!salaryInput.isEmpty()) {
            try {
                double salary = Double.parseDouble(salaryInput);
                employee.setSalary(salary);
            } catch (NumberFormatException e) {
                System.out.println("Invalid salary input.");
            }
        }
        System.out.println("Employee details updated.");
    }

    public void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        String id = scanner.nextLine();
        if (employeeMap.remove(id) != null) {
            System.out.println("Employee deleted.");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public void displayAllEmployees() {
        if (employeeMap.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            for (Employee employee : employeeMap.values()) {
                System.out.println(employee);
            }
        }
    }

    public void searchEmployee() {
        System.out.print("Enter Employee ID to search: ");
        String id = scanner.nextLine();
        Employee employee = employeeMap.get(id);
        if (employee != null) {
            System.out.println(employee);
        } else {
            System.out.println("Employee not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Management System:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Display All Employees");
            System.out.println("5. Search Employee");
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
                    manager.addEmployee();
                    break;
                case 2:
                    manager.updateEmployee();
                    break;
                case 3:
                    manager.deleteEmployee();
                    break;
                case 4:
                    manager.displayAllEmployees();
                    break;
                case 5:
                    manager.searchEmployee();
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
