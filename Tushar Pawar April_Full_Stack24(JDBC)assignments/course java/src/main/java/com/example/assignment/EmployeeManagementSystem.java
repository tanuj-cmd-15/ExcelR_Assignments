package com.example.assignment;

import java.sql.*;
import java.util.Scanner;

public class EmployeeManagementSystem {

    // MySQL Database connection details
//    static final String JDBC_URL = "jdbc:mysql:
//    static final String JDBC_USER = "root";
//    static final String JDBC_PASSWORD = "root";

    static String jdbcURL = "jdbc:mysql://localhost:3306/employee_management?useSSL=false&serverTimezone=UTC";
    static String username = "root";
    static String password = "root";
    Connection connection = DriverManager.getConnection(jdbcURL, username, password);

    public EmployeeManagementSystem() throws SQLException {
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(jdbcURL, username, password)) {
            System.out.println("Connected to the database!");

            while (true) {
                System.out.println("\nEmployee Management System");
                System.out.println("1. Add Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Display All Employees");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addEmployee(conn, scanner);
                        break;
                    case 2:
                        updateEmployee(conn, scanner);
                        break;
                    case 3:
                        deleteEmployee(conn, scanner);
                        break;
                    case 4:
                        displayAllEmployees(conn);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    // Method to add a new employee
    private static void addEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();

            System.out.print("Enter department: ");
            String department = scanner.nextLine();

            System.out.print("Enter salary: ");
            double salary = scanner.nextDouble();

            String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                stmt.setString(2, name);
                stmt.setString(3, department);
                stmt.setDouble(4, salary);
                stmt.executeUpdate();
                System.out.println("Employee added successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding employee.");
            e.printStackTrace();
        }
    }

    // Method to update employee details
    private static void updateEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter employee ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            System.out.print("Enter new name (or leave blank to keep current): ");
            String name = scanner.nextLine();

            System.out.print("Enter new department (or leave blank to keep current): ");
            String department = scanner.nextLine();

            System.out.print("Enter new salary (or enter -1 to keep current): ");
            double salary = scanner.nextDouble();

            StringBuilder query = new StringBuilder("UPDATE employees SET ");
            boolean firstField = true;

            if (!name.isEmpty()) {
                query.append("name = ?");
                firstField = false;
            }
            if (!department.isEmpty()) {
                if (!firstField) query.append(", ");
                query.append("department = ?");
                firstField = false;
            }
            if (salary != -1) {
                if (!firstField) query.append(", ");
                query.append("salary = ?");
            }
            query.append(" WHERE id = ?");

            try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                int paramIndex = 1;
                if (!name.isEmpty()) stmt.setString(paramIndex++, name);
                if (!department.isEmpty()) stmt.setString(paramIndex++, department);
                if (salary != -1) stmt.setDouble(paramIndex++, salary);
                stmt.setInt(paramIndex, id);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Employee updated successfully.");
                } else {
                    System.out.println("Employee not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error updating employee.");
            e.printStackTrace();
        }
    }

    // Method to delete an employee
    private static void deleteEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.print("Enter employee ID to delete: ");
            int id = scanner.nextInt();

            String query = "DELETE FROM employees WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Employee deleted successfully.");
                } else {
                    System.out.println("Employee not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee.");
            e.printStackTrace();
        }
    }

    // Method to display all employees
    private static void displayAllEmployees(Connection conn) {
        String query = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nEmployee List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Department: " + rs.getString("department") +
                        ", Salary: " + rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving employees.");
            e.printStackTrace();
        }
    }
}
