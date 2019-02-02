package com.gospelmalin.rasf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoryDao {

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
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	      return categoriesList; 
	   } 

	public Category getCategory(int categoryKey){
		   List<Category> categories = getAll();

	      for(Category category: categories){
	         if(category.getCategoryKey() == categoryKey){
	            return category;
	         }
	      }
	      return null;
	   }
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
		      
	         return 1;
	      }
	      return 0;
	   }
	
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
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 0;
				}
	            
	            return 1;
	         }
	      }		
	      return 0;
	   }
	
}
