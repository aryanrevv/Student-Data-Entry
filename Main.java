/**
 * Name: Aryan Kiran Revankar
 * PRN: 24070126502
 * Batch: SY AIML A1
 */

 import java.util.*;
 import exceptions.*;
 
 public class Main {
     public static void main(String[] args) {
         Scanner sc = new Scanner(System.in);
         StudentManager manager = new StudentManager();
         boolean running = true;
 
         while (running) {
             System.out.println("\n--- Student Data Entry System ---");
             System.out.println("1. Add Student");
             System.out.println("2. Display All Students");
             System.out.println("3. Search Student");
             System.out.println("4. Update Student");
             System.out.println("5. Delete Student");
             System.out.println("6. Exit");
             System.out.print("Enter choice: ");
 
             int choice = sc.nextInt();
             sc.nextLine(); // consume newline
 
             try {
                 switch (choice) {
                     case 1 -> manager.addStudent(sc);
                     case 2 -> manager.displayStudents();
                     case 3 -> manager.searchStudent(sc);
                     case 4 -> manager.updateStudent(sc);
                     case 5 -> manager.deleteStudent(sc);
                     case 6 -> running = false;
                     default -> System.out.println("Invalid choice. Try again.");
                 }
             } catch (InvalidInputException | StudentNotFoundException | DuplicatePRNException | EmptyDatabaseException e) {
                 System.out.println("Error: " + e.getMessage());
             }
         }
         sc.close();
     }
 }
 
 class Student {
     String prn, name, dob;
     double marks;
 
     public Student(String prn, String name, String dob, double marks) {
         this.prn = prn;
         this.name = name;
         this.dob = dob;
         this.marks = marks;
     }
 
     public void display() {
         System.out.printf("\nPRN: %s\nName: %s\nDOB: %s\nMarks: %.2f\n", prn, name, dob, marks);
     }
 }
 
 class StudentManager {
     private final List<Student> students = new ArrayList<>();
 
     public void addStudent(Scanner sc) throws InvalidInputException, DuplicatePRNException {
         System.out.print("Enter PRN: ");
         String prn = sc.nextLine();
         if (prn.isEmpty()) throw new InvalidInputException("PRN cannot be empty");
 
         for (Student s : students) {
             if (s.prn.equals(prn)) throw new DuplicatePRNException("Student with PRN already exists");
         }
 
         System.out.print("Enter Name: ");
         String name = sc.nextLine();
         System.out.print("Enter DOB (dd/mm/yyyy): ");
         String dob = sc.nextLine();
         System.out.print("Enter Marks: ");
         double marks = sc.nextDouble();
         sc.nextLine();
 
         if (name.isEmpty() || dob.isEmpty() || marks < 0)
             throw new InvalidInputException("Invalid student data");
 
         students.add(new Student(prn, name, dob, marks));
         System.out.println("Student added successfully.");
     }
 
     public void displayStudents() throws EmptyDatabaseException {
         if (students.isEmpty()) throw new EmptyDatabaseException("No student records found.");
         for (Student s : students) s.display();
     }
 
     public void searchStudent(Scanner sc) throws StudentNotFoundException, EmptyDatabaseException {
         if (students.isEmpty()) throw new EmptyDatabaseException("No students to search.");
 
         System.out.println("1. By PRN\n2. By Name\n3. By Position");
         int opt = sc.nextInt();
         sc.nextLine();
         boolean found = false;
 
         switch (opt) {
             case 1 -> {
                 System.out.print("Enter PRN: ");
                 String prn = sc.nextLine();
                 for (Student s : students) {
                     if (s.prn.equals(prn)) {
                         s.display();
                         found = true;
                         break;
                     }
                 }
             }
             case 2 -> {
                 System.out.print("Enter Name: ");
                 String name = sc.nextLine();
                 for (Student s : students) {
                     if (s.name.equalsIgnoreCase(name)) {
                         s.display();
                         found = true;
                     }
                 }
             }
             case 3 -> {
                 System.out.print("Enter Position: ");
                 int pos = sc.nextInt();
                 if (pos >= 0 && pos < students.size()) {
                     students.get(pos).display();
                     found = true;
                 }
             }
         }
         if (!found) throw new StudentNotFoundException("Student not found.");
     }
 
     public void updateStudent(Scanner sc) throws StudentNotFoundException, InvalidInputException {
         System.out.print("Enter PRN of student to update: ");
         String prn = sc.nextLine();
         boolean found = false;
         for (Student s : students) {
             if (s.prn.equals(prn)) {
                 System.out.print("Enter New Name: ");
                 s.name = sc.nextLine();
                 System.out.print("Enter New DOB: ");
                 s.dob = sc.nextLine();
                 System.out.print("Enter New Marks: ");
                 s.marks = sc.nextDouble();
                 sc.nextLine();
                 found = true;
                 break;
             }
         }
         if (!found) throw new StudentNotFoundException("Student not found with PRN: " + prn);
         System.out.println("Student details updated successfully.");
     }
 
     public void deleteStudent(Scanner sc) throws StudentNotFoundException {
         System.out.print("Enter PRN of student to delete: ");
         String prn = sc.nextLine();
         Iterator<Student> iterator = students.iterator();
         while (iterator.hasNext()) {
             Student s = iterator.next();
             if (s.prn.equals(prn)) {
                 iterator.remove();
                 System.out.println("Student deleted successfully.");
                 return;
             }
         }
         throw new StudentNotFoundException("Student not found with PRN: " + prn);
     }
 }
 
 // Exception classes in exceptions/ folder
 package exceptions;
 
 public class InvalidInputException extends Exception {
     public InvalidInputException(String msg) {
         super(msg);
     }
 }
 
 package exceptions;
 
 public class DuplicatePRNException extends Exception {
     public DuplicatePRNException(String msg) {
         super(msg);
     }
 }
 
 package exceptions;
 
 public class EmptyDatabaseException extends Exception {
     public EmptyDatabaseException(String msg) {
         super(msg);
     }
 }
 
 package exceptions;
 
 public class StudentNotFoundException extends Exception {
     public StudentNotFoundException(String msg) {
         super(msg);
     }
 }
 