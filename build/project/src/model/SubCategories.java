package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SubCategories implements VectorPairs{

	
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty key;
	private final SimpleStringProperty cat;
	

	
	public SubCategories(String catName, int keyVal, String cat){
		name = new SimpleStringProperty(catName);
		key = new SimpleIntegerProperty(keyVal);
		this.cat = new SimpleStringProperty(cat);
	}//end of constructor

	
	@Override
	public String getName() {
		return name.get();
	}
	
	@Override
	public Integer getKey() {
		return key.get();
	}


	public String getCat_key() {
		return cat.get();
	}
	
	
	
	
	
	
	
	
	
	
	
}
