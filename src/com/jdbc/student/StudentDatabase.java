package com.jdbc.student;

import java.sql.*;
import java.util.Scanner;

public class StudentDatabase {
	
	private static Connection connection = null;
	private static Scanner sc=new Scanner(System.in);
	
	public static void main(String args[]) {
		StudentDatabase studentdatabase = new StudentDatabase();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			 String dbURL = "jdbc:mysql://localhost:3306/jdbc_demo";
			 String name = "root";
			 String password = "";
			 
			 connection = DriverManager.getConnection(dbURL, name, password);
			 
			 System.out.println("Enter your choice: ");
			 System.out.println("1.Insert Record");
			 System.out.println("2.select Record");
			 System.out.println("3.Update Record");
			 System.out.println("4.Delete Record");
			 
			 int choice=Integer.parseInt(sc.nextLine());
			
			 switch(choice)
			 {
			 	 case 1:
				 studentdatabase.insertRecord();
				 break;
				 
			 	 case 2:
			 	 studentdatabase.selectRecord();
			 	 break;
			 	 
			 	 case 3:
			     studentdatabase.UpdateRecord();
			     break;
			     
			 	 case 4:
			     studentdatabase.DeleteRecord();
			 	 break;
			     
			 }
	} catch (Exception e) {
		throw new RuntimeException("Something went wrong");
		}
	}
	
	private void insertRecord() {
	    String sql = "insert into student_info(rollno, name, class, division) values(?,?,?,?)";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        System.out.println("Enter your Roll Number = ");
	        preparedStatement.setLong(1, sc.nextLong());
	        sc.nextLine(); // Consume the newline character

	        System.out.println("Enter your Name = ");
	        preparedStatement.setString(2, sc.nextLine());

	        System.out.println("Enter your Class = ");
	        preparedStatement.setString(3, sc.nextLine());

	        System.out.println("Enter your Division = ");
	        preparedStatement.setString(4, sc.nextLine());

	        int row = preparedStatement.executeUpdate();
	        if (row > 0) {
	            System.out.println("Record Inserted Successfully");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Handle the exception appropriately
	    }
	}
	
	private void selectRecord() throws SQLException {
		
		System.out.print("Enter the Student roll number that you want to display : ");
		int number = Integer.parseInt(sc.nextLine());
		
		String sql="select * from student_info where rollno = "+number;
		
		Statement statement=connection.createStatement();
		ResultSet result= statement.executeQuery(sql);	
		
			if(result.next()) {
			int rollnumber = result.getInt("rollno");
			String Name = result.getString("name");
			String Class = result.getString("class");
			String Division = result.getString("division");
			System.out.println("Roll number = "+rollnumber);
			System.out.println("Name = "+Name);
			System.out.println("Class = "+Class);
			System.out.println("Division = "+Division);
		}
			else {
				System.out.println("Student not found");
			}
	}
	
	private void UpdateRecord() throws SQLException {
	    System.out.print("Enter the Student roll number that you want to update: ");
	    int number = Integer.parseInt(sc.nextLine());

	    String sql = "select * from student_info where rollno = " + number;

	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery(sql);

	    if (result.next()) {
	        int rollnumber = result.getInt("rollno");
	        String Name = result.getString("name");
	        String Class = result.getString("class");
	        String Division = result.getString("division");
	        System.out.println("Roll number = " + rollnumber);
	        System.out.println("Name = " + Name);
	        System.out.println("Class = " + Class);
	        System.out.println("Division = " + Division);
	    } else {
	        System.out.println("No student found");
	        return; // Exit the method if no student found
	    }

	    System.out.println("What do you want to update");
	    System.out.println("1.Name");
	    System.out.println("2.Class");
	    System.out.println("3.Division");

	    int ch = Integer.parseInt(sc.nextLine());

	    switch (ch) {
	        case 1:
	            System.out.println("Enter your New Name : ");
	            String NewName = sc.nextLine();
	            sql = "update student_info set name = ? where rollno = "+number;
	            PreparedStatement preparedstatement = connection.prepareStatement(sql);
	            preparedstatement.setString(1, NewName);
	            int row = preparedstatement.executeUpdate();
	            if (row > 0) {
	                System.out.println("Record updated successfully");
	            } else {
	                System.out.println("No data found...");
	            }
	            break;
	        case 2:
	            System.out.println("Enter your New Class : ");
	            String NewClass = sc.nextLine();
	            sql = "update student_info set class = ? where rollno = "+number;
	            PreparedStatement preparedstatement1 = connection.prepareStatement(sql);
	            preparedstatement1.setString(1, NewClass);
	            
	            int row1 = preparedstatement1.executeUpdate();
	            if (row1 > 0) {
	                System.out.println("Record updated successfully");
	            } else {
	                System.out.println("No data found...");
	            }
	            break;
	        case 3:
	            System.out.println("Enter your New Division : ");
	            String NewDivision = sc.nextLine();
	            sql = "update student_info set division = ? where rollno = "+number;
	            PreparedStatement preparedstatement2 = connection.prepareStatement(sql);
	            preparedstatement2.setString(1, NewDivision);
	            int row2 = preparedstatement2.executeUpdate();
	            if (row2 > 0) {
	                System.out.println("Record updated successfully");
	            } else {
	                System.out.println("No data found...");
	            }
	            break;
	    }
	}

	
		private void DeleteRecord() throws SQLException {
			System.out.println("Enter Roll Number do you Want to delete : ");
			int rollnumber = Integer.parseInt(sc.nextLine());
			String sql = "delete from student_info where rollno = "+rollnumber;
			Statement statement = connection.createStatement();
			int row = statement.executeUpdate(sql);
			if(row>0) {
				System.out.println("Record Deleted Successfully");
			}
		}
	
	}
