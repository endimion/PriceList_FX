package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Categories {

	private final SimpleStringProperty name;
	private final SimpleIntegerProperty key;
	private final BooleanProperty active;
	
	public Categories(String catName, int keyVal, boolean act){
		name = new SimpleStringProperty(catName);
		key = new SimpleIntegerProperty(keyVal);
		active = new SimpleBooleanProperty(act);
	}//end of constructor

	public String getName() {
		return name.get();
	}

	public Integer getKey() {
		return key.get();
	}
	
	
	public Boolean getActive(){
		return active.get(); 
	}
	
	
	public BooleanProperty activeProperty(){
		return active;
	}
	
	
	
	
}
