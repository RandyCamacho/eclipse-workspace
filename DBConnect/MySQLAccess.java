import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MySQLAccess {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
			// Choose either the  the MySQL (or MariaDB)option
			// Remember to change "uid" and "pw" to your userid and password!
			
			/* 
			// These lines support the MySQL option (local and remote)
			// 
			 * 
			 */
        	System.out.println("Starting..."); //
        	
   			// To use MySQL locally, uncomment the next 3 lines
 			// and replace <YOURPASSWORD> with your own database password
     /*  	connect = DriverManager
                    .getConnection("jdbc:mysql://onyx.boisestate.edu:22/empdept?"                 		
                            + " user=msandbox&password=shabba3"); 
      */ 	
        	// TO use MySQL remotely (e.g., your laptop to onyx) uncomment the next four lines
        	// and replace <YOURPORTNO> and <YOURPASSWORD> with your own data
		connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3308/B?"
                    	+ " verifyServerCertificate=false&useSSL=true&"
                        + "user=msandbox&password=shabba3&"
                    	+ "serverTimezone=UTC");
		
		// Statement methods allow you to issue SQL queries to the database
		// Statements contrast with preparedStatements which you'll see below
		            statement = connect.createStatement();
		
		Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Team Name: ");
        String teamName = scanner.nextLine();
       
        preparedStatement = connect
                .prepareStatement("insert into Teams (TeamID, TeamName) values (?, ?)");
       
        int teamID = 11;
        
        preparedStatement.setInt(1, teamID);
        preparedStatement.setString(2, teamName);
        
        preparedStatement.executeUpdate();
        
        resultSet = statement
                .executeQuery("select TeamName from Teams where TeamName= 'blaze';");
        System.out.println("Reached after SELECT");
        writeResultSet(resultSet);
        
        for(int i = 1, bowlerID = 33; i <= 4; i++, bowlerID++) {
        
        System.out.println("Enter Team Member " + i + " FirstName and LastName:");            
        String memberF = scanner.next();
        String memberL = scanner.next();
        scanner.nextLine();
        
        System.out.println("Enter Team Member " + i + " Street Address");            
        String memberS = scanner.nextLine();
        
        System.out.println("Enter Team Member " + i + " City");
        String mCity = scanner.next();
        
        System.out.println("Enter Team Member " + i + " State");
        String mState = scanner.next();
        
        System.out.println("Enter Team Member " + i + " Zip");
        String mZip = scanner.next();
        scanner.nextLine();
        
        System.out.println("Enter Team Member " + i + " Phone Number");
        String memberP = scanner.nextLine();
        
        preparedStatement = connect
                .prepareStatement("insert into  Bowlers (BowlerID, BowlerLastName, BowlerFirstName, "
                		+ "BowlerAddress, BowlerCity, BowlerState, BowlerZip, BowlerPhoneNumber, TeamID) values (?,?,?, ?, ?, ?, ?, ?, ?)");

        preparedStatement.setInt(1, bowlerID);
        preparedStatement.setString(2, memberL);
        preparedStatement.setString(3, memberF);
        preparedStatement.setString(4, memberS);
        preparedStatement.setString(5, mCity);
        preparedStatement.setString(6, mState);
        preparedStatement.setString(7, mZip);
        preparedStatement.setString(8, memberP);
        preparedStatement.setString(9, "11");

        preparedStatement.executeUpdate();
        
        }     
        
        resultSet = statement
                .executeQuery("select * from Teams where TeamName=" + "'"+ teamName+ "'");
        writeResultSet(resultSet);
        
        resultSet = statement
                .executeQuery("select * from Bowlers where TeamID=" + "'"+ teamID+ "'");
        writeResultSet2(resultSet);
        
        } catch (Exception e) {
            throw e;
        } finally {
            close();
            System.out.println("Reached close");
        }
    }         
            private void writeResultSet(ResultSet resultSet) throws SQLException {
                // ResultSet is initialized before the first data set
                while (resultSet.next()) {
                    // It is possible to get the columns via name
                    // also possible to get the columns via the column number
                    // which starts at 1
                	int teamID = resultSet.getInt("TeamID");
                	String name = resultSet.getString("TeamName");
                	System.out.println("Retrieved Team Name: "+name);
                	System.out.println("Retrieved Team ID: "+teamID);
                }                
            }
            
            private void writeResultSet2(ResultSet resultSet) throws SQLException {
                // ResultSet is initialized before the first data set
                while (resultSet.next()) {
                    // It is possible to get the columns via name
                    // also possible to get the columns via the column number
                    // which starts at 1
               	
                	String fname = resultSet.getString("BowlerFirstName");
                	System.out.println("Retrieved Bowler First Name: "+fname);
                	String lname = resultSet.getString("BowlerLastName");
                	System.out.println("Retrieved Bowler Last Name: "+lname);
                	int teamID = resultSet.getInt("TeamID");
                	System.out.println("Retrieved Team ID: "+teamID); 
                	int BowlerID = resultSet.getInt("BowlerID");
                	System.out.println("Retrieved Bowler ID: "+BowlerID);
                	String bSA = resultSet.getString("BowlerAddress");
                	System.out.println("Retrieved Bowler Address: "+bSA);
                	String bCity = resultSet.getString("BowlerCity");
                	System.out.println("Retrieved Bowler City: "+bCity);
                	String bState = resultSet.getString("BowlerState");
                	System.out.println("Retrieved Bowler State: "+bState);
                	String bZip = resultSet.getString("BowlerZip");
                	System.out.println("Retrieved Bowler Zip: "+bZip);
                	String bPhone = resultSet.getString("BowlerPhoneNumber");
                	System.out.println("Retrieved Bowler Phone Number: "+bPhone);
                	
                	
                }
            }
            // You need to close the resultSet
            private void close() {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }

                    if (statement != null) {
                        statement.close();
                    }

                    if (connect != null) {
                        connect.close();
                    }
                } catch (Exception e) {

                }
            }       
}
