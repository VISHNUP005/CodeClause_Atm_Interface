
package atm_Interface;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class Atm {

	public static void main(String[] args) throws SQLException {
		
		
		
		MySqlConnection sqlc=new MySqlConnection();
		Services sv=new Services();
		Scanner sc=new Scanner(System.in);		
		
		while(true) {
			
		
		
		System.out.println("----State Bank of India----");
		String card_number;
		System.out.println("Please enter your 16 Digit card no");
		card_number=sc.nextLine();
		String cardno=card_number.toString();
		if (cardno.length()!=16) {
			System.out.println("------Enter the valid 16 digit Card number-------");
			System.out.println();
			continue;
			
		}
		else if(cardno.length()==16)
		{
			Connection con=sqlc.getConnection();
			Statement stmt=con.createStatement();
			PreparedStatement st = con.prepareStatement("select card_no from Atm1");
			ResultSet r1 = st.executeQuery();
			while(true) {
			if(r1.next()) {
	
		           

				String accno=r1.getString("card_no");
				if(card_number.equals(accno)) {
					PreparedStatement st1 = con.prepareStatement("select username,pin from Atm1 where card_no='"+accno+"'");
					ResultSet r11 = st1.executeQuery();
					if(r11.next()) {

				           String name=r11.getString("username");
				           int pin=r11.getInt("pin");
				           
				           System.out.println("------Welcome"+" "+name+"------");
				           
				           
				           
				        System.out.println("Please select your choice");			           
				   		System.out.println("1.Deposit ");
				   		System.out.println("2.Withdraw ");
				   		System.out.println("3.Balance Enquiry ");
				   		System.out.println("4.Transaction History ");
				   		System.out.println("5.Change PIN ");
				   		System.out.println("6.Exit ");
				   		
				   		int choice=sc.nextInt();
				   		switch (choice) {
				   		 
				           
				           case 1:
				           	sv.Deposit(accno,pin);
				               break;
				    
				           
				           case 2:
				           	sv.Withdraw(accno,pin);
				           	
				               break;
				    
				               
				           case 3:
				           	sv.balance_enquiry(accno,pin);
				               break;
				    
				               
				           case 4:
				        	   sv.Transaction(accno,pin);
				           	
				               break;
				           case 5:
				        	   sv.pin_change(accno, pin);
				        	   break;
				    
				           
				           case 6:
				           	System.out.println("Thank you..");
				           	System.exit(0);
				    
				               
				          
				           default:
				           	System.out.println("Invalid Operation");
				           	System.exit(0);
				           }	
				      }
					break;
				}
				
				

		}
			else{
				System.out.println("User not Found");
				System.exit(0);
			}}
			}
		
		
		
					
	}
}
}



 class Services extends Atm{
	Scanner sc=new Scanner(System.in);
	MySqlConnection sqlc=new MySqlConnection();
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");  
	ZoneId z = ZoneId.of( "America/Montreal" );
	LocalDate today = LocalDate.now( z );
	Date date = new Date(0);
	
	
	
	public void Deposit(String accno,int pin)throws SQLException {
		System.out.println("Enter the Amount to be Deposited..");
		int amt=sc.nextInt();
		int amount=0;
		int pin1=pin;
		String accno1=accno;

		
		
		

		
		Connection con=sqlc.getConnection();
		Statement stmt=con.createStatement();
		PreparedStatement st = con.prepareStatement("select current_balance from Atm1 where card_no='"+accno1+"'");
		ResultSet r1 = st.executeQuery();
		if(r1.next()) {

	            amount=r1.getInt("current_balance");
	        	
	    		System.out.println("Please enter your pin");
	    		int epin=sc.nextInt();
	    		if (epin==pin1) {
	    			int finalamt=amount+amt;
		    		
		    		String sql="update Atm1 set current_balance=('"+finalamt+"') where card_no='"+accno1+"'";
		    		stmt.executeUpdate(sql);
		    		

		    		
		    		String today1=today.toString();
		    		String sql1="insert into transactions values('"+accno1+"','Deposit','"+today1+"','"+amt +"')";
		    		stmt.executeUpdate(sql1);
		    		System.out.println("Your Account is been credited Rs"+amt+"Available Balance Rs"+finalamt);
		    		System.out.println("----Thank you----");
		    		System.exit(0);
	    			
	    		}
	    		else {
	    			System.exit(0);
	    		}
	    		
	    		
	    		
	    		System.exit(0);
	           

	      }
	
		
	}
	

	public void Withdraw(String accno,int pin)throws SQLException {
		System.out.println("Enter the Amount to Withdraw..");
		int amt=sc.nextInt();
		int amount=0;
		int pin1=pin;
		String accno1=accno;
		Connection con=sqlc.getConnection();
		Statement stmt=con.createStatement();
		PreparedStatement st = con.prepareStatement("select current_balance from Atm1 where card_no='"+accno1+"'");
		ResultSet r1 = st.executeQuery();
		if(r1.next()) {

	            amount=r1.getInt("current_balance");
	        	
	    		System.out.println("Please enter your pin");
	    		
	    		int epin=sc.nextInt();
	    		if (epin==pin1) {
	    			if(amount>amt) {
	    				int finalamt=amount-amt;
			    		
			    		String sql="update Atm1 set current_balance=('"+finalamt+"') where card_no='"+accno1+"'";
			    		stmt.executeUpdate(sql);
			    		String today1=today.toString();
			    		
			    		String sql1="insert into transactions values('"+accno1+"','Withdraw','"+today1+"','"+amt +"')";
			    		stmt.executeUpdate(sql1);
			    		System.out.println("Your Account is been Debited Rs"+amt+"Available Balance Rs"+finalamt);
			    		System.out.println("----Thank you----");
			    		System.exit(0);
		    			
		    		}
	    			else {
	    				System.out.println("------Insufficient Balance-----");
	    				System.out.println();
	    				System.out.println("---Transaction Failed---");
	    				System.exit(0);
	    			}
	    			
	    			
	    		}
	    		else {
	    			System.exit(0);
	    		}
	    		
	    		
	    		
	    		System.exit(0);
	           

	      }
	
		
	}
	
	
	
	public void balance_enquiry(String accno,int pin)throws SQLException {
		System.out.println("Enter your pin");
		int pin12=sc.nextInt();
		int pin1=pin;
		String accno1=accno;
		if(pin12==pin1) {
			Connection con=sqlc.getConnection();
			Statement stmt=con.createStatement();
			PreparedStatement st = con.prepareStatement("select current_balance from Atm1 where card_no='"+accno1+"'");
			ResultSet r1 = st.executeQuery();
			if(r1.next()) {
				int balance=r1.getInt("current_balance");
				System.out.println("Your Remaining Balance is"+balance);
				System.out.println();
				System.out.println("----Thank you----");
				System.exit(0);
			}
			
		}
		else {
			System.out.println("Incorrect pin");
			System.out.println();
			System.out.println("Try again");
			System.exit(0);
		}

		
		
	
	
		
	}
	//Transaction History
	
	public void Transaction(String accno,int pin)throws SQLException {
		System.out.println("Enter your pin");
		int pin12=sc.nextInt();
		int pin1=pin;
		String accno1=accno;
		if(pin12==pin1) {
			Connection con=sqlc.getConnection();
			Statement stmt=con.createStatement();
			PreparedStatement st = con.prepareStatement("select * from transactions where card_no='"+accno1+"'");
			ResultSet r1 = st.executeQuery();
			
			while(r1.next()) {
				String transaction=r1.getString("type_trans");
				String date=r1.getString("date_of_t");
				String amount=r1.getString("amount");
				
				System.out.println(transaction+" "+"of rs"+" "+amount+" "+"on"+" "+date);
				
			}
			System.out.println("----Thank You----");
			System.exit(0);
			
		}
		else {
			System.out.println("Incorrect pin");
			System.out.println();
			System.out.println("Try again");
			System.exit(0);
		}
		
		
			
	}
	//pin change
	public void pin_change(String accno,int pin)throws SQLException {
		System.out.println("Enter your pin");
		int pin12=sc.nextInt();
		int pin1=pin;
		String accno1=accno;
		if(pin12==pin1) {
			Connection con=sqlc.getConnection();
			Statement stmt=con.createStatement();
			PreparedStatement st = con.prepareStatement("select pin from Atm1 where card_no='"+accno1+"'");
			ResultSet r1 = st.executeQuery();
			if(r1.next()) {
				int oldpin=r1.getInt("pin");
				System.out.println("==Enter New Pin==");
				int newpin=sc.nextInt();
				System.out.println("==Confirm New Pin==");
				int cpin=sc.nextInt();
				if(newpin==oldpin) {
					System.out.println("New pin cannot be same as Old pin");
					System.exit(0);
				}
				
				if(newpin==cpin) {
//					PreparedStatement st123 = con.prepareStatement("update Atm1 set pin='"+newpin+"' where card_no='"+accno1+"'");
//					ResultSet rs = st123.executeQuery();
					String sql="update Atm1 set pin='"+newpin+"' where card_no='"+accno1+"'";
		    		stmt.executeUpdate(sql);
					System.out.println("Pin change successfull!!");
					System.out.println("--Thank You--");
					System.exit(0);
					
				}
				else {
					System.out.println("Pin does not match");
					System.exit(0);
				}
				
				System.out.println("----Thank you----");
				System.exit(0);
			}
			
		}
		else {
			System.out.println("Incorrect pin");
			System.out.println();
			System.out.println("Try again");
			System.exit(0);
		}

		
		
	
	
		
	}
	

	
	
}
 
 class MySqlConnection
 {
     private static String dbUrl = "jdbc:mysql:// localhost:3306/";
     private static String dbUsername = "root";
     private static String dbPassword = "SYSTEM";
     private static String dbName = "Atm";

     public  Connection getConnection()
     {
         Connection connection = null;
         try {
             
             connection = DriverManager.getConnection(dbUrl+ dbName, dbUsername, dbPassword);
         } catch (SQLException e) {
             System.out.println("Could not connect to DB: " + e.getMessage());
         }
         return connection;
     }
}
