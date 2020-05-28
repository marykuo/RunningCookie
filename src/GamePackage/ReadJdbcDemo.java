package GamePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.*;

public class ReadJdbcDemo {

	public static void main(String[] args) {

	      // obtain user input from JOptionPane input dialogs
	      String rank =JOptionPane.showInputDialog( "Enter rank" );
	      String name =JOptionPane.showInputDialog( "Enter name" );
	      String score =JOptionPane.showInputDialog( "Enter score" );

	      // convert String inputs to int values for use in a calculation
	       int number1 = Integer.parseInt( rank ); 
	      //int number2 = Integer.parseInt( secondNumber );
	      int number3 = Integer.parseInt( name );
	      insertTable(rank,name,score);
	      //int sum[] = {firstNumber,secondNumber,thirdNumber}; // add numbers
	      // int max=0;
	      //for (int i = 0; i < 3; i++) {
	      //  if(sum[i]>max)
	      //	  max=sum[i];
	      //    }
	      

	      // display result in a JOptionPane message dialog
	      /*JOptionPane.showMessageDialog( null, "The max number is " + thirdNumber, 
	         "MAX of Three Integers", JOptionPane.PLAIN_MESSAGE );*/
	}
	public static void insertTable( String rank,String name,String score) {	
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			} catch (Exception ex) {
				// handle the error
			}
	
			Connection conn = null;
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost/g13?"
						+ "user=root&password=0000&serverTimezone=UTC&useSSL=false");
	
				Statement stmt = conn.createStatement();
				
				//String sql1 = "UPDATE Score SET Name = 'and' WHERE Rank = 1";
				//stmt.executeUpdate(sql1);
				
				//String sql1 = "UPDATE Score SET Name = 'z8888' , Score = '99' WHERE Rank = 1";
				//stmt.executeUpdate(sql1); 
				
				PreparedStatement ps = conn.prepareStatement("INSERT INTO Score(Rank,Name,Score) VALUES(?,?,?);"); 
				//INSERT INTO [table] ([column], [column]) VALUES ('[value]', [value]');
				//UPDATE [table] SET [column] = '[updated-value]' WHERE [column] = [value];
				//ps = conn.prepareStatement("SELECT * FROM Score;");
				ps.setString(1, rank);
				ps.setString(2, name); 
				ps.setString(3, score);
				ps.executeUpdate(); 
				
				
				ResultSet rs = stmt.executeQuery("select *" + " from score"+ " where Rank");
				while (rs.next()) {
					System.out.println(
							rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) );
				}
	
			} catch (SQLException ex) {
				// handle any errors
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				System.out.println("InsertDB Exception :" + ex.toString()); 
			}
	
			
			
		}
}