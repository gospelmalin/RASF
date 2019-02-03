package com.gospelmalin.rasf;

	
	import java.sql.Connection;
	import java.sql.Driver;
	import java.sql.DriverManager;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;

/**
 * The Class Database handles the database connection.
 */
public class Database {
	
	public Database() {}

	// Database name - database should exist
   private static final String DATABASE = "storingfoods";
   // Connection string
   private static final String CONN_STR = "jdbc:mariadb://localhost:3306/" + DATABASE;
   //  Database credentials
   private static final String USER = "lab2";
   private static final String PWD = "lab2";
	
	//Connection
    private static Connection conn = null;
    
    //Connect to DB
    protected static Connection connectMariaDb() {
    	try {
    		// Register driver
    		Driver driver = (Driver) Class.forName("org.mariadb.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			 //Open a connection
	        System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(CONN_STR,USER,PWD);
			return conn;
		} catch (SQLException e) {
			System.err.println("An SQL exception occured when opening connection to MariaBD" + e.getMessage());
			} catch (InstantiationException e1) {
				System.err.println("An Instantiation exception occured when opening connection to MariaBD" + e1.getMessage());
		} catch (IllegalAccessException e2) {
			System.err.println("An Illegal Access exception occured when opening connection to MariaBD" + e2.getMessage());
		} catch (ClassNotFoundException e3) {
			System.err.println("Oops! Class not found when opening connection to MariaBD" + e3.getMessage());
		}
    	System.out.println("storingfoods (RAFS) Connected!");
    	return conn;
    }
    
    
    
  /**
   * Maria db close.
   */
  //Close Connection
   protected static void mariaDbClose() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e){
        	System.err.println("An SQL exception occured when closing connection " + e.getMessage());
        }
        
        System.out.println("Connection to storingfoods (RASF) is closed!");
    }
   
	/**
	 * Execute query.
	 *
	 * @param query the query
	 * @return the result set
	 */
	//DB Execute Select Query Operation
    public ResultSet executeQuery(String query) {
    	
    	// connect to db
    	connectMariaDb();

    	// statement for query
    	Statement stmt;
    	
    	// resultset for the result
    	ResultSet rs = null;
    	
    	// do querying
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			stmt.close();
			
		} catch (SQLException e) {
			System.err.println("An SQL exception occured when while executing query " + e.getMessage());
		}
		
		// close db
		mariaDbClose();
		
    	return rs;
    	
    } 

}
