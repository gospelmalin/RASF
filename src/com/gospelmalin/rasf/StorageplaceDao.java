package com.gospelmalin.rasf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StorageplaceDao handles the storageplace related queries for the database, isolating the database from the business layer.
 */
public class StorageplaceDao {
	
	/**
    * Show all storageplaces
    *
    */
	public List<Storageplace> getAll() {
		List<Storageplace> storageplacesList = null;   
		storageplacesList = new ArrayList<Storageplace>();
		Database db = new Database();
		try {
			String query = "SELECT * from storageplace;";
			ResultSet rs = db.executeQuery(query);
			while(rs.next()) {
			  int storageplaceKey = rs.getInt("storageplace_key");
			  String storageplaceName = rs.getString("storageplace_name");
			//  System.out.println(storageplaceKey + " " + storageplaceName); // For development only
			  storageplacesList.add(new Storageplace(storageplaceKey, storageplaceName));
			}
		} catch (SQLException e) {
			System.err.println("An SQL exception occured when trying to get all storageplaces " + e.getMessage());
		}
	      return storageplacesList; 
	   } 
	
	/**
    * Show information about selected storageplace
    *
    */
	public Storageplace getStorageplace(int storageplaceKey){
		  List<Storageplace> storageplaces = getAll();
	      for(Storageplace storageplace: storageplaces){
	         if(storageplace.getStorageplaceKey() == storageplaceKey){
	            return storageplace;
	         }
	      }
	      return null;
	   }

	/**
	 * Add a new storageplace
	 *
	 */
	public int addStorageplace (Storageplace pStorageplace){
	      List<Storageplace> storageplaceList = getAll();
	      boolean storageplaceExists = false;
	      for(Storageplace storageplace: storageplaceList){
	         if(storageplace.getStorageplaceName() == pStorageplace.getStorageplaceName()){
	        	 storageplaceExists = true;
	            break;
	         }
	      }		
	      if(!storageplaceExists){
	    	  storageplaceList.add(pStorageplace);
	         // Setup query
	         String query = "INSERT INTO storageplace(storageplace_name) VALUE(?);";
	         Connection conn = Database.connectMariaDb();
	         try {
				// Setup statement
				 PreparedStatement stmt = conn.prepareStatement(query);
				 // Set values
				stmt.setString(1, pStorageplace.getStorageplaceName());						
				// Execute statement
				stmt.executeUpdate();				
				// Closing statement and connection
				stmt.close();
				Database.mariaDbClose();
			} catch (SQLException e) {
				System.err.println("An SQL exception occured when trying to add a record to storageplace " + e.getMessage());
				return 0;
			}		      
	         return 1;
	      }
	      return 0;
	   }
	
	/**
	 * Update storageplace name
	 *
	 */
	public int updateStorageplace(Storageplace pStorageplace){
	      List<Storageplace> storageplaceList = getAll();
	      for(Storageplace storageplace: storageplaceList){
	         if(storageplace.getStorageplaceKey() == pStorageplace.getStorageplaceKey()){
	           int index = storageplaceList.indexOf(storageplace);			
	           storageplaceList.set(index, pStorageplace);
	           String query = "UPDATE storageplace SET storageplace_name = ? WHERE storageplace_key = ?;";
			       
	           Connection conn = Database.connectMariaDb();
		         try {
					// Setup statement
					 PreparedStatement stmt = conn.prepareStatement(query);	
					 // Set values
					 stmt.setString(1, pStorageplace.getStorageplaceName());
					 stmt.setInt(2, pStorageplace.getStorageplaceKey());										
					// Execute statement
					stmt.executeUpdate();					
					// Closing statement and connection
					stmt.close();
					Database.mariaDbClose();
				} catch (SQLException e) {
					System.err.println("An SQL exception occured when trying to update a record in storageplace " + e.getMessage());
					return 0;
				}	            
	            return 1;
	         }
	      }		
	      return 0;
	   }
	
		/**
	    * Delete a storageplace.
	    * Delete only allowed for storageplaces with no linked items,
	    * as deleting a storageplace otherwise would also delete those items.
	    *
	    */
		public int deleteStorageplace(int storageplaceKey){			
			 ItemDao itemDao = new ItemDao();
			  List<Item> itemList = itemDao.getAll();
			 //Check if storageplace is used by any item(s)
			  boolean storageplaceUsed = false;
		      for(Item item: itemList){
		        if(item.getStorageplaceKey() == storageplaceKey){
		        	storageplaceUsed = true;
			      	System.out.println("Storageplace may only be deleted while not assigned to item(s). Please reassign item(s) and retry.");
			          break;
		        }
		      }
		      if (storageplaceUsed == false) {       
	            String query = "DELETE FROM storageplace WHERE storageplace_key=?;";
		         Connection conn = Database.connectMariaDb();
		         try {
					// Setup statement
					 PreparedStatement stmt = conn.prepareStatement(query);     
					 // Set values				
					stmt.setInt(1, storageplaceKey);					
					// Execute statement
					stmt.executeUpdate();					
					// Closing statement and connection
					stmt.close();
					Database.mariaDbClose();
				} catch (SQLException e) {
					System.err.println("An SQL exception occured when trying to delete from storageplace " + e.getMessage());
					return 0;
				}
	            return 1;   
	         }		
		      return 0;
		   }

}
