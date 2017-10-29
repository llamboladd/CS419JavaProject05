package ch34;

import java.sql.*;
import com.sun.xml.internal.ws.util.StringUtils;


public class FindUserTables {
  public static String[] main(String[] args)
      throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
	String returnString[] = new String[4];
	for(int i = 0; i < 4; i++){
		returnString[i] = "";
	}
    // Load the JDBC driver
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Driver loaded");

    // Establish a connection
    TestDatabaseStuff connectionStuff = new TestDatabaseStuff();
    Connection connection = DriverManager.getConnection
    		(connectionStuff.connectionString, connectionStuff.connectionUser, connectionStuff.connectionPass);
	
    System.out.println("Database connected");

    DatabaseMetaData dbMetaData = connection.getMetaData();
    
    int outItr = 0;
    ResultSet rsTables = dbMetaData.getTables(null, null, null,
      new String[] {"TABLE"});
	returnString[0] += "User tables\n";
    //System.out.println("User tables:");
    while (rsTables.next()){
    	returnString[1] += ++outItr + ": " + StringUtils.capitalize(rsTables.getString("TABLE_NAME")).replace('_', ' ') + "\n";
    	//System.out.println("\t\t" + rsTables.getString("TABLE_NAME") + " ");
    }
    returnString[0] += returnString[1];
    outItr = 0;
    
    returnString[0] += "\n";
    //System.out.println();
    
    ResultSet rsFunctions = dbMetaData.getFunctions(null, null, null);
	returnString[0] += "User Functions\n";
    //System.out.println("User Functions:");
    while (rsFunctions.next()){
    	returnString[2] += ++outItr + ": " + StringUtils.capitalize(rsFunctions.getString("FUNCTION_NAME")).replace('_', ' ') + "\n";
    	//System.out.println("\t\t" + rsFunctions.getString("FUNCTION_NAME") + " ");
    }
    returnString[0] += returnString[2];
    outItr = 0;

    returnString[0] += "\n";
    //System.out.println();
    
    ResultSet rsProcedures = dbMetaData.getProcedures(null, null, null);
	returnString[0] += "User Procedures\n";
    //System.out.println("User Procedures:");
    while (rsProcedures.next()){
    	returnString[3] += ++outItr + ": " + StringUtils.capitalize(rsProcedures.getString("PROCEDURE_NAME")).replace('_', ' ') + "\n";
    	//System.out.println("\t\t" + rsProcedures.getString("PROCEDURE_NAME") + " ");
    }
    returnString[0] += returnString[3];
    
    // Close the connection
    connection.close();
    System.out.println("Database Closed");
    return returnString;
  }
}
