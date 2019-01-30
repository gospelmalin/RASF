package com.gospelmalin.rasf;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "item")
public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int itemKey;
	private int categoryKey;
	private String itemName;
	private int unitsAlways;
	private String available;

	// Constructors
	public Item(){}
	
	public Item(String itemName) {
		super();
		this.itemName = itemName;
	}
	
	public Item(int itemKey, String itemName) {
		super();
		this.itemKey = itemKey;
		this.itemName = itemName;
	}
	
	public Item(int itemKey, int categoryKey, String itemName, int unitsAlways, String available) {
		super();
		this.itemKey = itemKey;
		this.categoryKey = categoryKey;
		this.itemName = itemName;
		this.unitsAlways = unitsAlways;
		this.available = available;
	}
	
	// Getters and setters
		public int getItemKey() {
			return itemKey;
		}
		
		@XmlElement
		public void setItemKey(int itemKey) {
			this.itemKey = itemKey;
		}
		
		public int getCategoryKey() {
			return categoryKey;
		}
		
		@XmlElement
		public void setCategoryKey(int categoryKey) {
			this.categoryKey = categoryKey;
		}

		public String getItemName() {
			return itemName;
		}
		
		@XmlElement
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		
		
		public int getUnitsAlways() {
			return unitsAlways;
		}
		
		@XmlElement
		public void setUnitsAlways(int unitsAlways) {
			this.unitsAlways = unitsAlways;
		}
		
		public String getAvailable() {
			return available;
		}
		
		@XmlElement
		public void setAvailable(String available) {
			this.available = available;
		}
		
		@Override
		   public boolean equals(Object object){
		      
			   if(object == null){
		         return false;
			   	} 
			  else if(!(object instanceof Item)){
		         return false;
		      	}
			  else {
		         Item item = (Item)object;
		         if(itemKey ==item.getItemKey()
		            && itemName.equals(item.getItemName())
		         ){
		            return true;
		         }			
		      }
		      return false;
		   }	
}
