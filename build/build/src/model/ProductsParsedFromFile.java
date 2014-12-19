package model;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;




public class ProductsParsedFromFile {

	
	private final SimpleStringProperty category, subCategory, 
		description, supplier,invCode,ekemsCode;
	
	private final SimpleFloatProperty invPrice, gramsForInvPrice, pricePerKilo;;

	
	
	public ProductsParsedFromFile(String catName,  String supp, String subCatName,
			String descr, String invCode, String ekemsCode, 
			float price, float grams, float variation, float percent, float pricePerK){
		
		category =new SimpleStringProperty(catName);
		supplier =new SimpleStringProperty(supp);
		subCategory =new SimpleStringProperty(subCatName);
		description =new SimpleStringProperty( descr.replace("'", ""));
		this.invCode =new SimpleStringProperty(invCode);
		this.ekemsCode =new SimpleStringProperty(ekemsCode);
		invPrice =new SimpleFloatProperty(price);
		gramsForInvPrice =new SimpleFloatProperty(grams);
		pricePerKilo  =new SimpleFloatProperty(pricePerK);
			
	}//end of constructor



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



	public float getInvPrice() {
		return invPrice.get();
	}



	public float getGramsForInvPrice() {
		return gramsForInvPrice.get();
	}



	public float getPricePerKilo() {
		return pricePerKilo.get();
	}

	
	
	
	
	
	
	
	
}//end of class
