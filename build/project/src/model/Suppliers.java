package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Suppliers {
	
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty key;
	
	public Suppliers(String supName, int keyVal){
		name = new SimpleStringProperty(supName);
		key = new SimpleIntegerProperty(keyVal);
	}//end of constructor

	
	public String getName() {
		return name.get();
	}

	public Integer getKey() {
		return key.get();
	}
	
	
	
	
	
}//end of Suppliers
