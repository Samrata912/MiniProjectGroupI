package com.adminpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.Scanner;
import com.mainpackage.MainClass;

@SuppressWarnings("static-access")
public class Admin {
	
	Scanner scanner = new Scanner(System.in);
	MainClass mainClass=new MainClass();
	
	public void allStudentScores() throws SQLException {
		Connection connection = null ;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			
			preparedStatement = connection.prepareStatement("select id,userscore.UserName,userscore.Score from userscore inner join student on userscore.UserName=student.UserName  order by Score  ");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Formatter fmt=new Formatter();
			fmt.format("%15s %15s %15s\n","User Id","UserName","Score");
			
			while(resultSet.next()){
				
				fmt.format("%12s %16s %15s\n",resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
				
			}
			
			System.out.println(fmt);
			System.out.println("\n\nDo you want to Continue ? ('y' -> to display Main Menu / 'n' -> to Exit) >>");
			String op=scanner.next();
			if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
				mainClass.main(null);
			}else {
				System.exit(0);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();

		}
		
			
	}
	
	public void studentScorebyID() throws SQLException {
		Connection connection = null ;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			preparedStatement = connection.prepareStatement("select id,userscore.UserName,userscore.Score from userscore inner join student on userscore.UserName=student.UserName  order by Score  ");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			Formatter fmt=new Formatter();
			fmt.format("%15s %15s %15s\n","User Id","UserName","Score");
			
			System.out.print("Enter the User Id of Student >> ");
			String id=scanner.next();
			while(resultSet.next()){
				
				if(resultSet.getString(1).equals(id)) {
					fmt.format("%12s %16s %15s\n",resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3));
					break;
				}
				
			}
			
			System.out.println(fmt);
			System.out.println("\n\nDo you want to Continue ? ('y' -> to display Main Menu / 'n' -> to Exit) >>");
			String op=scanner.next();
			if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
				mainClass.main(null);
			}else {
				System.exit(0);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();

		}
	}

	public void addQuestion() throws SQLException {
		Connection connection = null ;
		PreparedStatement preparedStatement = null;
		
		System.out.print("Enter Question >> ");
		String Question_Descrption = scanner.nextLine();
		
		System.out.print("Enter option a >> ");
		String a = scanner.nextLine();
		
		System.out.print("Enter option b >> ");
		String b = scanner.nextLine();
		
		System.out.print("Enter option c >> ");
		String c = scanner.nextLine();
		
		System.out.print("Enter option d >> ");
		String d = scanner.nextLine();
		
		System.out.print("Enter answer key  >> ");
		String answer = scanner.next();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_quiz?autoReconnect=true&useSSL=false","root", "root");
			preparedStatement = connection.prepareStatement("insert into questions (Question_Descrption ,a,b,c,d, answer)values(?,?,?,?,?,?)");
			
			preparedStatement.setString(1,Question_Descrption);
			preparedStatement.setString(2,a);
			preparedStatement.setString(3,b);
			preparedStatement.setString(4,c);
			preparedStatement.setString(5,d);
			preparedStatement.setString(6,answer);
			
			preparedStatement.execute();
			
			System.out.println("___________Question added to question bank___________");
			System.out.println("\n\nDo you want to Continue ? ('y' -> to display Main Menu / 'n' -> to Exit) >>");
			String op=scanner.next();
			if(op.charAt(0)=='y' || op.charAt(0)=='Y') {
				mainClass.main(null);
			}else {
				System.exit(0);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.close();
			preparedStatement.close();

		}
	}
}
