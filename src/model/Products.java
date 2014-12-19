package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Products {

	
	
	
	
	private final SimpleStringProperty category, subCategory, description, supplier,
										invCode, ekemsCode;
	private final SimpleIntegerProperty key;
	private final SimpleFloatProperty invPrice, gramsForInvPrice, pricePerKilo;
	private final SimpleFloatProperty absVar, percentVar;
	
	private SimpleBooleanProperty checked;
	
	
	
	
	
	/**
	 * 
	 * @param catName, the name of the product Category
	 * @param keyVal, the primary key of the product
	 * @param supp, the name of the supplier of the product
	 * @param subCatName, the name of the products subCategory
	 * @param descr, a description for the product
	 * @param invCode, the invoice code of the product
	 * @param ekemsCode, the ekems code of the product
	 * @param price, the invoice price of the product
	 * @param grams, the number of grams the price of the invoice refers to 
	 * @param variation, the absolute number of the variation of the product
	 * @param percent, the percentage of the variation
	 * @param pricePerK, the price per kilo of the product
	 */
	public Products(String catName, int keyVal, String supp, String subCatName,
			String descr, String invCode, String ekemsCode, 
			float price, float grams, float variation, float percent, float pricePerK,
			boolean checked){
		
		category = new SimpleStringProperty(catName);
		supplier =  new SimpleStringProperty(supp);
		subCategory = new SimpleStringProperty(subCatName);
		description = new SimpleStringProperty(descr);
		
		key = new SimpleIntegerProperty(keyVal);
		this.invCode = new SimpleStringProperty(invCode);
		this.ekemsCode = new SimpleStringProperty(ekemsCode);
		
		invPrice = new SimpleFloatProperty(price);
		gramsForInvPrice = new SimpleFloatProperty(grams);
		pricePerKilo  = new SimpleFloatProperty(pricePerK);
		
		
		absVar = new SimpleFloatProperty(variation);
		percentVar = new SimpleFloatProperty(percent);
		
		this.checked = new SimpleBooleanProperty(checked);
		
	}//end of constructor


	
	//Get methods
	
	
	public String getCategory() {
		return category.get();
	}

	public String getSubCategory() {
		return subCategory.get();
	}

	public String getDescription() {
		return description.get();
	}

	public String getSupplier() {
		return supplier.get();
	}

	public String getInvCode() {
		return invCode.get();
	}

	public String getEkemsCode() {
		return ekemsCode.get();
	}

	public int getKey() {
		return key.get();
	}

	public float getInvPrice() {
		return invPrice.get();
	}

	public float getGramsForInvPrice() {
		return gramsForInvPrice.get();
	}

	public float getPricePerKilo() {
		return pricePerKilo.get();
	}

	public float getAbsVar() {
		return absVar.get();
	}

	public float getPercentVar() {
		return percentVar.get();
	}

	public boolean getChecked(){
		return checked.get();
	}

	
	
	//Setter methods
	
	public void setPricePerKilo(float newValue) {
		 pricePerKilo.set(newValue);
	}
	
	public void setCategory(String newValue){
		category.set(newValue);
	}
	
	public void setChecked(boolean newValue){
		checked.set(newValue);
	}
	
	
	public void setGramsForInvPrice(float newValue) {
		gramsForInvPrice.set(newValue);
	}
	
	public void setAbsVar(float newValue) {
		absVar.set(newValue);
	}
	
	public void setPercentVar(float newValue){
		percentVar.set(newValue);
	}
	
	
	
	
	// methods which expose private properties
	//
	
	public SimpleFloatProperty gramsForInvPriceProperty(){
		return gramsForInvPrice;
	}
	
	public SimpleBooleanProperty checkedProperty(){
		return checked;
	}
	
	public SimpleFloatProperty pricePerKiloProperty() {
		 return pricePerKilo;
	}
	
	public SimpleFloatProperty absVarProperty() {
		 return absVar;
	}
	
	public SimpleFloatProperty percentVarProperty() {
		 return percentVar;
	}
	
	
}//end of class
