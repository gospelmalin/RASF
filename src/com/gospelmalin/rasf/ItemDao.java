package com.gospelmalin.rasf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ItemDao {
	
	public List<Item> getAll() {
		List<Item> itemsList = null; 
	      
		itemsList = new ArrayList<Item>();
		
		
		Database db = new Database();
		try {
			String query = "SELECT i.*, c.category_name, s.storageplace_key, s.storageplace_name from category c inner join item i on c.category_key = i.category_key left outer join storageplace s on i.storageplace_key = s.storageplace_key order by c.category_name, i.item_name;"; 
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
			  System.out.println(itemKey + " " + itemName);
			//  itemsList.add(new Item(itemKey, itemName)); //FUNKAR!
			  itemsList.add(new Item(itemKey, categoryKey, itemName, unitsAlways, available, numberOfUnits, categoryName, storageplaceKey, storageplaceName));
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		itemsList.add(new Item(1, "Item1"));
		itemsList.add(new Item(2, "Item2"));
		itemsList.add(new Item(3, "Item3"));
		 */
	      return itemsList; 
	   } 
	/*
	public List<Item> getAll2() {
		List<Item> itemsList = null; 
	      
		itemsList = new ArrayList<Item>();
		
		
		Database db = new Database();
		try {
			String query = "SELECT i.*, co.number_of_units, co.content_key, c.category_name from category c inner join item i on c.category_key = i.category_key left outer join content co on i.item_key = co.item_key order by c.category_name, i.item_name;"; //TODO uppdateraa query inkluidera content
			ResultSet rs = db.executeQuery(query);
	  
			
			while(rs.next()) {
			  int itemKey = rs.getInt("item_key");
			  int categoryKey = rs.getInt("category_key");
			  String itemName = rs.getString("item_name");
			  int unitsAlways = rs.getInt("units_always_at_home");
			  String available = rs.getString("available");
			  int numberOfUnits = rs.getInt("number_of_units");
			  int contentKey = rs.getInt("content_key");
			  String categoryName = rs.getString("category_name");
			  System.out.println(itemKey + " " + itemName);
			//  itemsList.add(new Item(itemKey, itemName)); //FUNKAR!
			  itemsList.add(new Item(itemKey, categoryKey, itemName, unitsAlways, available, numberOfUnits, contentKey, categoryName));
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	      return itemsList; 
	   } 
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
	

	public int updateItem(Item pItem){
	      List<Item> itemList = getAll();

	      for(Item item: itemList){
	         if(item.getItemKey() == pItem.getItemKey()){
	            int index = itemList.indexOf(item);			
	            itemList.set(index, pItem);

	           String query = "UPDATE item SET category_key = ?, item_name = ?, units_always_at_home = ?, number_of_units = ?,  available = ?, storageplace_key = ? WHERE item_key = ?;";
	           // String query = "UPDATE item SET item_name = ?, units_always_at_home = ?, number_of_units = ?,  available = ? WHERE item_key = ?;";
			       
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
					
					 
				/* borde gå
					 stmt.setString(1, pItem.getItemName());
					 stmt.setInt(2, pItem.getUnitsAlways());
					 stmt.setInt(3, pItem.getNumberOfUnits());
					 stmt.setString(4, pItem.getAvailable());
					 stmt.setInt(5, pItem.getItemKey());
					 
					 */
					 
					//test
					 /*
					 stmt.setString(1,"LAX");
					 stmt.setInt(2, 2);
					 stmt.setInt(3, 3);
					 stmt.setString(4, "YES");
					 stmt.setInt(5,2);
					*/
					// Execute statement
					stmt.executeUpdate();
					
					// Closing statement and connection
					stmt.close();
					Database.mariaDbClose();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            return 1;
	         }
	      }		
	      return 0;
	   }

	
	
	public List<Item> getAll1() {
		List<Item> itemsList = null; 
	      
		itemsList = new ArrayList<Item>();
		
		
		Database db = new Database();
		try {
			String query = "SELECT * from item;";
			ResultSet rs = db.executeQuery(query);
	  
			
			while(rs.next()) {
			  int itemKey = rs.getInt("item_key");
			  int categoryKey = rs.getInt("category_key");
			  String itemName = rs.getString("item_name");
			  int unitsAlways = rs.getInt("units_always_at_home");
			  String available = rs.getString("available");
			  System.out.println(itemKey + " " + itemName);
			//  itemsList.add(new Item(itemKey, itemName)); //FUNKAR!
			  itemsList.add(new Item(itemKey, categoryKey, itemName, unitsAlways, available));
			}
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		itemsList.add(new Item(1, "Item1"));
		itemsList.add(new Item(2, "Item2"));
		itemsList.add(new Item(3, "Item3"));
		 */
	      return itemsList; 
	   } 

}
