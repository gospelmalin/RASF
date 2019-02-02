package com.gospelmalin.rasf;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "storageplace")
public class Storageplace implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private int storageplaceKey;
	private String storageplaceName;

		// Constructors
		public Storageplace(){}
		
		public Storageplace(String storageplaceName) {
			super();
			this.storageplaceName = storageplaceName;
		}
		
		public Storageplace(int storageplaceKey, String storageplaceName) {
			super();
			this.storageplaceKey = storageplaceKey;
			this.storageplaceName = storageplaceName;
		}

		
		// Getters and setters
		public int getStorageplaceKey() {
			return storageplaceKey;
		}
		
		@XmlElement
		public void setStorageplaceKey(int storageplaceKey) {
			this.storageplaceKey = storageplaceKey;
		}

		public String getStorageplaceName() {
			return storageplaceName;
		}
		
		@XmlElement
		public void setStorageplaceName(String storageplaceName) {
			this.storageplaceName = storageplaceName;
		}
		
		@Override
		   public boolean equals(Object object){
		      
			   if(object == null){
		         return false;
			   	} 
			  else if(!(object instanceof Storageplace)){
		         return false;
		      	}
			  else {
				 Storageplace storageplace = (Storageplace)object;
		         if(storageplaceKey == storageplace.getStorageplaceKey()
		            && storageplaceName.equals(storageplace.getStorageplaceName())
		         ){
		            return true;
		         }			
		      }
		      return false;
		   }	

}
