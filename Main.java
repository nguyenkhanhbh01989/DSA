import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private String code;
    private float grade;
    private String rank;

    public Student(String name, String code, float grade) {
        this.name = name;
        this.code = code;
        this.grade = grade;
        this.rank = calculateRank(grade);
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public float getGrade() {
        return grade;
    }

    public String getRank() {
        return rank;
    }

    private String calculateRank(float grade) {
        if (grade >= 0 && grade < 6.5) {
            return "F";
        } else if (grade >= 6.5 && grade < 8) {
            return "M";
        } else if (grade >= 8 && grade <= 10) {
            return "D";
        } else {
            return "Unknown";
        }
    }

    public void display() {
        System.out.println("Name: " + name +
                ", Code: " + code +
                ", Grade: " + grade +
                ", Rank: " + rank);
    }
}

public class Main {
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Remove Student by Code");
            System.out.println("4. Sort Students by Grade Descending");
            System.out.println("5. Search Student by Code or Name");
            System.out.println("6. Search Students with Grade >= X");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> displayAllStudents();
                    case 3 -> removeStudentByCode();
                    case 4 -> sortStudentsByGradeDescending();
                    case 5 -> searchStudentByCodeOrName();
                    case 6 -> searchStudentsByGrade();
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again.");
                scanner.nextLine(); // Consume invalid input
                choice = -1; // reset choice to stay in loop
            }
        } while (choice != 0);
    }

    private static void addStudent() {
        String name = "";
        boolean validName = false;
        while (!validName) {
            try {
                System.out.print("Enter name: ");
                name = scanner.nextLine();
                if (!name.matches("[a-zA-Z\\s]+")) {
                    throw new IllegalArgumentException("Name must not contain numbers or special characters.");
                }
                validName = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.print("Enter code: ");
        String code = scanner.nextLine();

        float grade = 0;
        boolean validGrade = false;
        while (!validGrade) {
            try {
                System.out.print("Enter grade (0-10): ");
                grade = scanner.nextFloat();
                scanner.nextLine(); // Consume newline
                if (grade < 0 || grade > 10) {
                    throw new IllegalArgumentException("Grade must be between 0 and 10.");
                }
                validGrade = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        students.add(new Student(name, code, grade));
        System.out.println("Student added successfully.");
    }

    private static void displayAllStudents() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students to display.");
                return;
            }
            for (Student student : students) {
                student.display();
            }
        } catch (Exception e) {
            System.out.println("Error displaying students. Please try again.");
        }
    }

    private static void removeStudentByCode() {
        try {
            System.out.print("Enter student code to remove: ");
            String code = scanner.nextLine();
            students.removeIf(student -> student.getCode().equals(code));
            System.out.println("Student removed if it existed.");
        } catch (Exception e) {
            System.out.println("Error removing student. Please try again.");
        }
    }

    private static void sortStudentsByGradeDescending() {
        try {
            int n = students.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (students.get(j).getGrade() < students.get(j + 1).getGrade()) {
                        // Swap students[j] and students[j + 1]
                        Student temp = students.get(j);
                        students.set(j, students.get(j + 1));
                        students.set(j + 1, temp);
                    }
                }
            }
            System.out.println("Students sorted by grade in descending order.");
        } catch (Exception e) {
            System.out.println("Error sorting students. Please try again.");
        }
    }

    private static void searchStudentByCodeOrName() {
        try {
            System.out.print("Enter student code or name to search: ");
            String input = scanner.nextLine();
            boolean found = false;
            for (Student student : students) {
                if (student.getCode().equals(input) || student.getName().equalsIgnoreCase(input)) {
                    student.display();
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Student not found.");
            }
        } catch (Exception e) {
            System.out.println("Error searching for student. Please try again.");
        }
    }

    private static void searchStudentsByGrade() {
        try {
            System.out.print("Enter minimum grade to search: ");
            float x = scanner.nextFloat();
            scanner.nextLine(); // Consume newline
            boolean found = false;
            for (Student student : students) {
                if (student.getGrade() >= x) {
                    student.display();
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No students found with grade >= " + x);
            }
        } catch (Exception e) {
            System.out.println("Error searching for students. Please try again.");
        }
    }
}
