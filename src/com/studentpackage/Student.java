package com.studentpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.mainpackage.MainClass;

@SuppressWarnings("static-access")
public class Student {
	Scanner scanner = new Scanner(System.in);
	private boolean correctUserName=false;
	private boolean correctPassword=false;
	private String userName=null;
	
	MainClass mainClass=new MainClass();
	
	
	public void studentRegistration() throws SQLException,NullPointerException {
		
		
		System.out.print("Enter first name >> ");
		String firstName = scanner.next();
		
		System.out.print("Enter last name >> ");
		String lastName = scanner.next();
		
		System.out.print("Enter user name >> ");
		String userName = scanner.next();
		
		System.out.print("Enter password >> ");
		String password = scanner.next();
		
		System.out.print("Enter city >> ");
		String city = scanner.next();
		
		System.out.print("Enter mail id >> ");
		String mail = scanner.next();
		
		System.out.print("Enter mobile number >> ");
		String mobileNumber = scanner.next();
		Connection connection = null ;
		PreparedStatement preparedStatement = null;

		
 		try {
 			
 			
 			Class.forName("com.mysql.cj.jdbc.Driver");
			
 			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			
			preparedStatement = connection.prepareStatement("insert into student(FirstName, LastName, UserName, Password, city, mail_id, mobile_Number)values(?,?,?,?,?,?,?)");
			
			preparedStatement.setString(1,firstName);
			preparedStatement.setString(2,lastName);
			preparedStatement.setString(3,userName);
			preparedStatement.setString(4,password);
			preparedStatement.setString(5,city);
			preparedStatement.setString(6,mail);
			preparedStatement.setString(7,mobileNumber);
			
		preparedStatement.execute();
		System.out.println("_______________Registration is done successfully_______________\n\n ");
		
		
		
		
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Do you want to Login ('y' -> to go to login page / 'n' -> to go back to Main menu) >> ");
			String op=scanner.next();
			if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
				this.studentLogin();
			}else {
				mainClass.main(null);
			}
		}
	
		
	}
	
	
	
	public void studentLogin() throws SQLException,NullPointerException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null ;
		PreparedStatement examStatement = null ;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
 			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			
			
			
			if(correctUserName && correctPassword) {
				examStatement=connection.prepareStatement(" select UserName from userscore");
				ResultSet status = examStatement.executeQuery();
				int flag=0;
				while(status.next()) {
					if(status.getString("UserName").equals(userName)) {
						flag=1;
						break;
					}
				}
				
				if(flag==0) {
					System.out.println("\n\nDo you want to start the Exam  ('y' -> to start the exam / 'n' -> to logout) >> ");
					String op=scanner.next();
					if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
						this.studentExam();
					}else {
						mainClass.main(null);
					}
				}else {
					System.out.println("\n\nYou have already given the Exam ('y' -> to display your Scorecard / 'n' -> to logout) >>");
					String op=scanner.next();
					if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
						this.studentResult();
					}else {
						mainClass.main(null);
					}
				}
				
			}else {
				
				System.out.print("Enter user name >> ");
				userName= scanner.next();
				
				System.out.print("Enter password >>");
				String password = scanner.next();
				
				preparedStatement = connection.prepareStatement(" select UserName, Password from Student ");
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				int flag=0;
				while(resultSet.next()) {
					 if(resultSet.getString("UserName").equals(userName)) {
						 flag=1;
						if(resultSet.getString("Password").equals(password)) {
							System.out.println("Logged In successfully ");
							correctUserName=true;
							correctPassword=true;
							this.studentLogin();
						}else {
							System.out.println("User name or password is incorrect Relogin\n");
							correctUserName=false;
							correctPassword=false;
							this.studentLogin();
						}
					}
				}
				
				if(flag==0) {
					System.out.println(" Account not registred \n");
					System.out.print("Do you want to Register (y/n) >> ");
					String op=scanner.next();
					if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
						this.studentRegistration();
					}else {
						mainClass.main(null);
					}
				}
			}
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
 		
 	}
	
	
	
	@SuppressWarnings("rawtypes")
	public void studentExam() throws SQLException,NullPointerException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int count = 0;

		if (correctUserName && correctPassword) {

			System.out.println(" Your test has been started ");

			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				
	 			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");

				preparedStatement = connection.prepareStatement(" select * from questions ");

				ResultSet resultSet = preparedStatement.executeQuery();

				HashMap<String, String> hm=new HashMap<>();

				while (resultSet.next()) {
					
					String quer=resultSet.getString(2)+"\na "+resultSet.getString(3)+"\nb "+resultSet.getString(4)+"\nc "+resultSet.getString(5)+"\nd "+resultSet.getString(6);
					hm.put(quer, resultSet.getString(7));


				}
				
				int i=1;
				for(Map.Entry m:hm.entrySet()) {
					if(i<=10) {
						System.out.println(i+" "+m.getKey());
						System.out.print(" Enter your choice >>");
						String answer = scanner.next();
						if (answer.equals(m.getValue())) {
							count++;
						}
						i++;
					}else {
						break;
					}
				}
				PreparedStatement preparedStatement1  = connection.prepareStatement("insert into userscore (UserName,Score)values(?,?)");
				preparedStatement1.setString(1,userName);
				preparedStatement1.setInt(2, count);
				
				preparedStatement1.executeUpdate();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				connection.close();
				preparedStatement.close();
				System.out.println(" Your test has been finished \n");
				
				System.out.println("\n\nDo you want to see your result ('y' -> to display your Scorecard / 'n' -> to logout) >>");
				String op=scanner.next();
				if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
					this.studentResult();
				}else {
					mainClass.main(null);
				}
				
			}
				

		}



	}
	
	
	
	public void studentResult() throws SQLException,NullPointerException {
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			PreparedStatement examStatement=connection.prepareStatement(" select * from userscore");
			ResultSet status = examStatement.executeQuery();
			while(status.next()) {
				if(status.getString("UserName").equals(userName)) {
					System.out.println("Hi You have Scored "+status.getInt(2)+" marks");
				}
			}
	
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
					
 		
	}

	
	

}
