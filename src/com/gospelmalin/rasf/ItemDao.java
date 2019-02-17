package com.gospelmalin.rasf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ItemDao handles the item related queries for the database, isolating the database from the business layer.
 */
public class ItemDao {
	
	/**
    * Show all items
    *
    */
	public List<Item> getAll() {
		List<Item> itemsList = null;   
		itemsList = new ArrayList<Item>();
		Database db = new Database();
		try {
			String query = "SELECT i.*, c.category_name, s.storageplace_key, s.storageplace_name from category c inner join item i on c.category_key = i.category_key left outer join storageplace s on i.storageplace_key = s.storageplace_key order by i.item_name;"; 
			ResultSet rs = db.executeQuery(query);	  			
			while(rs.next()) {
			  int itemKey = rs.getInt("item_key");
			  int categoryKey = rs.getInt("category_key");
			  String itemName = rs.getString("item_name");
			  int unitsAlways = rs.getInt("units_always_at_home");
			  String available = rs.getString("available");
			  int numberOfUnits = rs.getInt("number_of_units");
			  String categoryName = rs.getString("category_name");
			  int storageplaceKey = rs.getInt("storageplace_key");
			  String storageplaceName = rs.getString("storageplace_name");
			//  System.out.println(itemKey + " " + itemName); // Used for development only
			  itemsList.add(new Item(itemKey, categoryKey, itemName, unitsAlways, available, numberOfUnits, categoryName, storageplaceKey, storageplaceName));
			}
		} catch (SQLException e) {
			System.err.println("An SQL exception occured when trying to get all items " + e.getMessage());
		}
	      return itemsList; 
	   } 
	
	/**
    * Show information about selected item
    *
    */
	public Item getItem(int itemKey){
		  List<Item> items = getAll();
	      for(Item item: items){
	         if(item.getItemKey() == itemKey){
	            return item;
	         }
	      }
	      return null;
	   }
	/**
    * Show all items for selected category
    *
    */
	public List<Item> getAllForCategory(int categoryKey) {
		 List<Item> items = getAll();
		 List<Item> itemsForCategory = null;
		 itemsForCategory = new ArrayList<Item>();		 
		 for(Item item: items){
	         if(item.getCategoryKey() == categoryKey){
	           itemsForCategory.add(item);
	        //   System.out.println("Found this item in selected category: " + item.getItemName()); // For development only
	         }     
		 }
		 return itemsForCategory;
	   } 
	
	/**
    * Show all items for selected storageplace
    *
    */
	public List<Item> getAllForStorageplace(int storageplaceKey) {
		 List<Item> items = getAll();
		 List<Item> itemsForStorageplace = null;
		 itemsForStorageplace= new ArrayList<Item>();		 
		 for(Item item: items){
	         if(item.getStorageplaceKey() == storageplaceKey){
	        	 itemsForStorageplace.add(item);
	         //  System.out.println("Found this item for selected storageplace: " + item.getItemName()); // For development only
	         }     
		 }
		 return itemsForStorageplace;
	   } 
	
	/**
	 * Add a new item
	 *
	 */
	public int addItem(Item pItem){
	      List<Item> itemList = getAll();
	      boolean itemExists = false;
	      for(Item item: itemList){
	         if(item.getItemName() == pItem.getItemName()){
	            itemExists = true;
	            break;
	         }
	      }		
	      if(!itemExists){
	         itemList.add(pItem);
	         // Setup query
	         String query = "INSERT INTO item(category_key, item_name, units_always_at_home, number_of_units, available, storageplace_key) VALUES(?,?,?,?,?,?);";
	         Connection conn = Database.connectMariaDb();
	         try {
				// Setup statement
				 PreparedStatement stmt = conn.prepareStatement(query); 
				 // Set values
				stmt.setInt(1, pItem.getCategoryKey());
				stmt.setString(2, pItem.getItemName());
				stmt.setInt(3, pItem.getUnitsAlways());
				stmt.setInt(4, pItem.getNumberOfUnits());
				stmt.setString(5, pItem.getAvailable()); 
				stmt.setInt(6, pItem.getStorageplaceKey());				
				// Execute statement
				stmt.executeUpdate();				
				// Closing statement and connection
				stmt.close();
				Database.mariaDbClose();
			} catch (SQLException e) {
				System.err.println("An SQL exception occured when trying to add an item record " + e.getMessage());
				return 0;
			}	      
	         return 1;
	      }
	      return 0;
	   }
	
	/**
	 * Update an item - update units always at home,  update number of units when adding or removing items 
	 * from a storageplace, change storageplace or category for an item
	 *
	 */
	public int updateItem(Item pItem){
	      List<Item> itemList = getAll();
	      for(Item item: itemList){
	         if(item.getItemKey() == pItem.getItemKey()){
	           int index = itemList.indexOf(item);			
	           itemList.set(index, pItem);
	           String query = "UPDATE item SET category_key = ?, item_name = ?, units_always_at_home = ?, number_of_units = ?,  available = ?, storageplace_key = ? WHERE item_key = ?;";     
	           Connection conn = Database.connectMariaDb();
		         try {
					// Setup statement
					 PreparedStatement stmt = conn.prepareStatement(query);	     
					 // Set values
					 stmt.setInt(1, pItem.getCategoryKey());
					 stmt.setString(2, pItem.getItemName());
					 stmt.setInt(3, pItem.getUnitsAlways());
					 stmt.setInt(4, pItem.getNumberOfUnits());
					 stmt.setString(5, pItem.getAvailable());
					 stmt.setInt(6, pItem.getStorageplaceKey());
					 stmt.setInt(7, pItem.getItemKey());					
					// Execute statement
					stmt.executeUpdate();					
					// Closing statement and connection
					stmt.close();
					Database.mariaDbClose();
				} catch (SQLException e) {
					System.err.println("An SQL exception occured when trying to update an item record " + e.getMessage());
					return 0;
				}	            
	            return 1;
	         }
	      }		
	      return 0;
	   }


}
