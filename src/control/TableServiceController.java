package control;

import java.util.Vector;

import model.Products;
import model.TableBuilder;
import model.VectorPairs;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
//import view.TableViewer;


public class TableServiceController  extends Service<String> {
		
	TableBuilder tb;
	TableView<Products> ctv;
	Vector<VectorPairs> catVect; 
	Vector<VectorPairs> supVect;
	Vector<VectorPairs> subVect;
	String where;
	
	public TableServiceController(TableBuilder tb, 
			ChoiceBox<String> catChoice, ChoiceBox<String> supChoice, 
			ChoiceBox<String> subCatChoice, Button subCatButton, Button productsButton,
			Button categoriesButton, Stage primaryStage, MenuBar mbar, 
			String whereClause, Button search, TextField txt, Button delete,
			Vector<VectorPairs> catVect,Vector<VectorPairs> supVect,
			Vector<VectorPairs> subVect, Button addCat, Button addSubCategory, Button sort){
		
		this.tb = tb;
		this.catVect = catVect;
		this.supVect = supVect;
		this.subVect = subVect;
		this.where = whereClause;
		
		ctv = null;
		
		/*this.setOnSucceeded(event ->{
			view.TableViewer tv = new TableViewer(primaryStage, getTableView(), mbar,
					null,"",categoriesButton,"Κατηγορίες",
					subCatButton,"Υποκατ/ίες",
					productsButton,"Προιόντα", 
					supChoice, catChoice, subCatChoice,search,txt,
					"Τιμοκατάλογος Μονάδων Ε.Κ.Ε.Μ.Σ.",delete, addCat,addSubCategory,sort);
					
				tv.displayView();
		});*/
		
	}//end of constructor
	
	
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
		     @Override
		     protected String call() throws Exception {
		    	 //DO YOU HARD STUFF HERE
		    	 ctv = tb.buildProductsTable(catVect, supVect, subVect, where);
		    	
		    	 
		    	 
		    	 
		    	 return "success";
		      }
		   };
	}//end of createTask

	
	
	/**
	 * 
	 * @return, if the task has finished executing it will return the generated tablevie
	 * else it will return null
	 */
	public TableView<Products> getTableView(){
		return ctv;
	}
	
	
}//end of DbService