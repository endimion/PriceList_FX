package model;

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TableCellHelper {

	TableCell<Products,Float> the_cell;
	TextField textField;
	DbHelper dbh;
	
	
	
	public TableCellHelper(TableCell<Products,Float> cell, 
			TextField textField, DbHelper dbh){
		this.the_cell = cell;
		this.textField = textField;
		this.dbh = dbh;
		
		
	}
	
	
	
	/**
	 * Creates a textField with the required properties
	 */
	public  TextField createKilosTextField() {
        textField = new TextField(getString());
       
        Products pr;
    	float oldPricePerKilo;
    	
        pr = (Products) the_cell.getTableRow().getItem();
		oldPricePerKilo = pr.getPricePerKilo();
        
        textField.setMinWidth(the_cell.getWidth() - the_cell.getGraphicTextGap()*2);
        textField.setOnKeyPressed( (KeyEvent t) -> {
               
        	if (t.getCode() == KeyCode.ENTER) {
        		//the_cell.commitEdit(Float.parseFloat(textField.getText()));
				
        		  Float newValue = (Float) null ;
              	  try{
              		  newValue = Float.parseFloat(textField.getText());

	              	  if(newValue != null){
	              		the_cell.commitEdit(Float.parseFloat(textField.getText()));
	              	//	the_cell.
	              		int key = pr.getKey();
	              		
	              		float numOfKilos;
	              		
	              		if(pr.getCategory().equals("ΑΥΓΑ") ){
	              			 numOfKilos = newValue;
	              		}else{
	              			 numOfKilos = newValue/1000;
	              			 //System.out.println("number of kilos "+ numOfKilos);
	              		}
	              		
	              		pr.setPricePerKilo(pr.getInvPrice()/numOfKilos  );
	              		float newPricePerKilo = pr.getPricePerKilo();
	              		
	              		pr.setChecked(true);
	              		the_cell.setId("checked");
	              		pr.setGramsForInvPrice(newValue);
	              		
	              		//System.out.println("palila "+oldPricePerKilo+" nea   "+newPricePerKilo);
	              		if(oldPricePerKilo != 0 && oldPricePerKilo != newPricePerKilo){
	              			pr.setAbsVar(newPricePerKilo -oldPricePerKilo);
	              			pr.setPercentVar( ((newPricePerKilo -oldPricePerKilo)/oldPricePerKilo)* 100); 
	              			dbh.updateValue(key, "PERCENTAGE", "PRODUCTS", ((oldPricePerKilo - newPricePerKilo)/oldPricePerKilo)* 100);
		              		dbh.updateValue(key, "VARIATION", "PRODUCTS", oldPricePerKilo - newPricePerKilo);
	              		}
	              		
	              		dbh.updateValue(key, "GRAMS", "PRODUCTS", Float.parseFloat(textField.getText()));
	              		dbh.updateValue(key, "CHECKED", "PRODUCTS", 1);
	              		dbh.updateValue(key, "PRICE_PER_UNIT", "PRODUCTS",newPricePerKilo);
	              		
	              		
	              	  }//end if newValue not null
	              	
	              	  
              	  }catch(Exception e1){e1.printStackTrace();}

        	} else if (t.getCode() == KeyCode.ESCAPE) {
                	the_cell.cancelEdit();
                }//end else if
           
        });//ed setOnKeyPressed
        
        return textField;
    }//end of createTextField
	
	
	
	
	
	/**
	 * Creates a textField with the required properties for
	 * editing the invoicePrice field of a products object tableview
	 */
	public  TextField createInvPriceTextField() {
		textField = new TextField(getString());
	       
        Products pr;
    	float oldInvPrice;
    	
        pr = (Products) the_cell.getTableRow().getItem();
        oldInvPrice = pr.getInvPrice();
        
        textField.setMinWidth(the_cell.getWidth() - the_cell.getGraphicTextGap()*2);
        textField.setOnKeyPressed( (KeyEvent t) -> {
               
        	if (t.getCode() == KeyCode.ENTER) {
        		the_cell.commitEdit(Float.parseFloat(textField.getText()));
				Float newValue = (Float) null ;
              	try{
              		 newValue = Float.parseFloat(textField.getText());

	              	 if(newValue != null){
	              		the_cell.commitEdit(Float.parseFloat(textField.getText()));
	              		int key = pr.getKey();
	              		float numOfKilos = pr.getGramsForInvPrice()/1000;
	              		pr.setPricePerKilo(newValue/numOfKilos  );
	              		float newPricePerKilo = pr.getPricePerKilo();
	              		pr.setChecked(true);
	              		
	              		//System.out.println("palila "+oldPricePerKilo+" nea   "+newPricePerKilo);
	              		pr.setAbsVar(newValue -oldInvPrice);
	              		pr.setPercentVar( ((newValue -oldInvPrice)/oldInvPrice)* 100); 
	              		
	              		dbh.updateValue(key, "PRICE", "PRODUCTS", Float.parseFloat(textField.getText()));
	              		dbh.updateValue(key, "CHECKED", "PRODUCTS", 1);
	              		dbh.updateValue(key, "PRICE_PER_UNIT", "PRODUCTS",newPricePerKilo);
	              		dbh.updateValue(key, "PERCENTAGE", "PRODUCTS", ((newValue -oldInvPrice)/oldInvPrice* 100));
	              		dbh.updateValue(key, "VARIATION", "PRODUCTS", newValue -oldInvPrice);
	              	  }//end if newValue not null
              	  }catch(Exception e1){e1.printStackTrace();}
        	}else 
        	if (t.getCode() == KeyCode.ESCAPE) {
                		the_cell.cancelEdit();
            }//end else if
           
        });//ed setOnKeyPressed
        
        return textField;
	}//end of createInvPriceTextField
	
	
	
	
	
	
	
	
	private String getString() {
        return the_cell.getItem() == null ? "" : the_cell.getItem().toString();
    }
	
	
}
