/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.fit.mp2;
import java.util.Scanner;
/**
 *
 * @author dastarosa
 */

class Student {
    private String id;
    private String fullName;

    public Student(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
}

class Course {
    private String code;
    private String title;
    private int capacity;
    private Student[] enrolledStudents;
    private int enrolledCount;

    public Course(String code, String title, int capacity) {
        this.code = code;
        this.title = title;
        this.capacity = capacity;
        this.enrolledStudents = new Student[capacity];
        this.enrolledCount = 0;
    }

    public boolean enroll(Student student) {
        if (student == null) {
            System.out.println("Enrollment failed: Student cannot be null.");
            return false;
        }
        if (enrolledCount >= capacity) {
            System.out.println("Enrollment failed: Course is full (Capacity: " + capacity + ").");
            return false;
        }
        // Check for duplicates
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledStudents[i].getId().equalsIgnoreCase(student.getId())) {
                System.out.println("Enrollment failed: Student " + student.getFullName() + " is already enrolled.");
                return false;
            }
        }
        
        enrolledStudents[enrolledCount++] = student;
        System.out.println("Success: " + student.getFullName() + " has been enrolled.");
        return true;
    }

    public Student findStudent(String id) {
        for (int i = 0; i < enrolledCount; i++) {
            if (enrolledStudents[i].getId().equalsIgnoreCase(id)) {
                return enrolledStudents[i];
            }
        }
        return null;
    }

    public void displayRoster() {
        System.out.println("\n========================================");
        System.out.println("           OFFICIAL COURSE ROSTER       ");
        System.out.println("========================================");
        System.out.println("Course Code  : " + code);
        System.out.println("Course Title : " + title);
        System.out.println("Capacity     : " + capacity);
        System.out.println("Enrolled     : " + enrolledCount);
        System.out.println("----------------------------------------");
        
        if (enrolledCount == 0) {
            System.out.println("No students currently enrolled.");
        } else {
            for (int i = 0; i < enrolledCount; i++) {
                System.out.println((i + 1) + ". ID: " + enrolledStudents[i].getId() + 
                                   " | Name: " + enrolledStudents[i].getFullName());
            }
        }
        System.out.println("========================================");
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }
}
public class Exer6CourseEnrollment {
    // Helper method to search the master array
    public static Student searchMasterArray(Student[] masterArray, String id) {
        for (int i = 0; i < masterArray.length; i++) {
            if (masterArray[i] != null && masterArray[i].getId().equalsIgnoreCase(id)) {
                return masterArray[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Course Setup ===");
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter Course Title: ");
        String courseTitle = scanner.nextLine();
        System.out.print("Enter Course Capacity: ");
        int courseCapacity = scanner.nextInt();
        scanner.nextLine();

        Course course = new Course(courseCode, courseTitle, courseCapacity);

        System.out.println("\n=== Master Student Registry Setup ===");
        System.out.print("Enter total number of students in the master registry: ");
        int masterSize = scanner.nextInt();
        scanner.nextLine();

        Student[] masterStudents = new Student[masterSize];
        for (int i = 0; i < masterSize; i++) {
            System.out.println("\nEnter details for Master Student " + (i + 1) + ":");
            System.out.print("Student ID: ");
            String id = scanner.nextLine();
            System.out.print("Full Name: ");
            String name = scanner.nextLine();
            masterStudents[i] = new Student(id, name);
        }

        System.out.print("\nEnter the number of enrollment attempts: ");
        int attempts = scanner.nextInt();
        scanner.nextLine(); 

        for (int a = 1; a <= attempts; a++) {
            System.out.println("\n--- Attempt " + a + " of " + attempts + " ---");
            System.out.print("Enter Student ID to enroll: ");
            String targetId = scanner.nextLine();

            Student foundStudent = searchMasterArray(masterStudents, targetId);

            if (foundStudent == null) {
                System.out.println("Attempt failed: Student ID '" + targetId + "' not found in the master registry.");
            } else {
                System.out.println("Found in registry: " + foundStudent.getFullName() + ". Proceeding to enroll...");
                course.enroll(foundStudent);
            }
        }

        course.displayRoster();
        System.out.println("\n--- Slot Summary ---");
        System.out.println("Occupied Slots : " + course.getEnrolledCount());
        System.out.println("Remaining Slots: " + (course.getCapacity() - course.getEnrolledCount()));

        scanner.close();
    }
}
