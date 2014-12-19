package model;



import view.TableViewer;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class BestPriceService  extends Service<String> {

	ObservableList<Products> data;
	ObservableList<Products> sortedData;
	TableViewer tableViewer ;
	
	public BestPriceService(ObservableList<Products> inputData, TableViewer tv){
		data = inputData;
		tableViewer = tv;
	}
	
	
	
	
	@Override
	protected Task<String> createTask() {
		return new Task<String>() {
		     @Override
		     protected String call() throws Exception {
		    	
		    	TableDataSorter tds = new TableDataSorter();
		 		sortedData = tds.sortProductsDataBySubCategory(data);
		 		
		 		
		 		 return "success";
		      }
		   };
	}//end of createTask
	
	
	
	
	
	
	
	public ObservableList<Products> getSortedData(){
		return sortedData;
	}//end of getSortedData
	
	
}//end 
