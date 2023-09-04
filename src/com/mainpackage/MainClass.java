package com.mainpackage;

import java.sql.SQLException;
import java.util.Scanner;
import com.adminpackage.Admin;
import com.studentpackage.Student;

@SuppressWarnings("resource")
public class MainClass {

	
	public static void main(String[] args) throws SQLException,NullPointerException{
		System.out.println(" Welcome to Quiz based Application ");
		System.out.println("\n__________Student Operations__________");
		System.out.println("\n 1. Student Registration ");
		System.out.println(" 2. Student Login ");
		System.out.println("\n__________Admin Operations__________");
		System.out.println("\n 3. Display all students score as per ascending order ");
		System.out.println(" 4. Fetch student score by using student id ");
		System.out.println(" 5. Add questions with 4 options into database  ");
		System.out.print("\nEnter your choice >>> ");
		
		
		Scanner scanner=new Scanner(System.in);
		int choice=scanner.nextInt();
		
		Student student=new Student();
		Admin admin=new Admin();
		
		
		switch(choice) {
		case 1 : student.studentRegistration();
			break;
		case 2 : student.studentLogin();
			break;
		case 3:admin.allStudentScores();
			break;
		case 4:admin.studentScorebyID();
			break;
		case 5:admin.addQuestion();
			break;
			
		default :System.out.println("Invalid choice >>>>>"+choice);
		}

	}
	
	
}
