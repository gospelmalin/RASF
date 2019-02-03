package com.gospelmalin.rasf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class CategoryDao handles the category related queries for the database, isolating the database from the business layer.
 */
public class CategoryDao {

	/**
    * Show all categories
    *
    */
	public List<Category> getAll() {
		List<Category> categoriesList = null; 	      
		categoriesList = new ArrayList<Category>();		
		Database db = new Database();
		try {
			String query = "SELECT * from category;";
			ResultSet rs = db.executeQuery(query);	  
			while(rs.next()) {
			  int categoryKey = rs.getInt("category_key");
			  String categoryName = rs.getString("category_name");
			  System.out.println(categoryKey + " " + categoryName);
			  categoriesList.add(new Category(categoryKey, categoryName));
			}
		} catch (SQLException e) {
			System.err.println("An SQL exception occured when trying to get all categories " + e.getMessage());
			e.printStackTrace();
		}		
	      return categoriesList; 
	   } 
	
	/**
    * Show information about selected category
    *
    */
	public Category getCategory(int categoryKey){
		  List<Category> categories = getAll();
	      for(Category category: categories){
	         if(category.getCategoryKey() == categoryKey){
	            return category;
	         }
	      }
	      return null;
	   }


	/**
	 * Add a new category
	 *
	 */
	public int addCategory(Category pCategory){
	      List<Category> categoryList = getAll();
	      boolean categoryExists = false;
	      for(Category category: categoryList){
	         if(category.getCategoryName() == pCategory.getCategoryName()){
	        	 categoryExists = true;
	            break;
	         }
	      }		
	      if(!categoryExists){
	    	  categoryList.add(pCategory);
	         // Setup query
	         String query = "INSERT INTO category(category_name) VALUE(?);";
	         Connection conn = Database.connectMariaDb();
	         try {
				// Setup statement
				 PreparedStatement stmt = conn.prepareStatement(query);
				 // Set values
				stmt.setString(1, pCategory.getCategoryName());								
				// Execute statement
				stmt.executeUpdate();				
				// Closing statement and connection
				stmt.close();
				Database.mariaDbClose();
			} catch (SQLException e) {
				System.err.println("An SQL exception occured when trying to add a new category " + e.getMessage());
				return 0;
			}      
	         return 1;
	      }
	      return 0;
	   }
	
	/**
	 * Update category name
	 *
	 */
	public int updateCategory(Category pCategory){
	      List<Category> categoryList = getAll();
	      for(Category category: categoryList){
	         if(category.getCategoryKey() == pCategory.getCategoryKey()){
	           int index = categoryList.indexOf(category);			
	           categoryList.set(index, pCategory);
	           String query = "UPDATE category SET category_name = ? WHERE category_key = ?;";			     
	           Connection conn = Database.connectMariaDb();
		         try {
					// Setup statement
					 PreparedStatement stmt = conn.prepareStatement(query);	     
					 // Set values
					 stmt.setString(1, pCategory.getCategoryName());
					 stmt.setInt(2, pCategory.getCategoryKey());										
					// Execute statement
					stmt.executeUpdate();					
					// Closing statement and connection
					stmt.close();
					Database.mariaDbClose();
				} catch (SQLException e) {
					System.err.println("An SQL exception occured when trying to update a category record " + e.getMessage());
					e.printStackTrace();
					return 0;
				}	            
	            return 1;
	         }
	      }		
	      return 0;
	   }
	
	
	/**
    * Delete a category.
    * Delete only allowed for categories with no linked items,
    * as deleting a category otherwise would also delete those items.
    *
    */
	public int deleteCategory(int categoryKey){
	  ItemDao itemDao = new ItemDao();
	  List<Item> itemList = itemDao.getAll();
	  //Check if category is used by any item(s)
	  boolean categoryUsed = false;
      for(Item item: itemList){
        if(item.getCategoryKey() == categoryKey){
	      	 categoryUsed = true;
	      	 System.out.println("Category may only be deleted while not assigned to item(s). Please reassign item(s) and retry.");
	          break;
        }
      }
      if (categoryUsed == false) {   
       String query = "DELETE FROM category WHERE category_key=?;";
       Connection conn = Database.connectMariaDb();
       try {
		// Setup statement
    	   PreparedStatement stmt = conn.prepareStatement(query);    	   
		// Set values			
		   stmt.setInt(1, categoryKey);			
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
