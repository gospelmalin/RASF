package com.gospelmalin.rasf;

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
