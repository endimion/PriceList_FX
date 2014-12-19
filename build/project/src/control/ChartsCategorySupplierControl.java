package control;

import java.util.Vector;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import model.DbHelper;
import model.Products;
import model.SubCatPair;
import model.VectorPairs;
import view.ChartsViewer;

public class ChartsCategorySupplierControl {

	BarChart<String,Number> bc;
	int categoryID= 383;
	@SuppressWarnings("rawtypes")
	Vector<XYChart.Series> seriesVect = new Vector<XYChart.Series>();
	Vector<String> subcategories = new Vector<String>();
	

	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChartsCategorySupplierControl(Vector<VectorPairs> subcatpairs, DbHelper dbh, 
			ChoiceBox<String> catChoice){
		
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        String catName = catChoice.getSelectionModel().getSelectedItem();
        Vector<VectorPairs> catPairs = dbh.getCategoriesIntoVector();
        
        for(VectorPairs vp : catPairs){
        	if((vp.getName()).equals(catName) ){
        		categoryID = vp.getKey();
        	}
        }//end of catPairs loop
        
        
        /**
         * we add the subCategories which have products matching the category 
         * in a vector 
         */
        for(int i =0; i< subcatpairs.size();i++){
        	if(((SubCatPair)subcatpairs.get(i)).getCatID() == categoryID){
        		subcategories.add(((SubCatPair)subcatpairs.get(i)).getName());
        		System.out.println(((SubCatPair)subcatpairs.get(i)).getName());
        	}
        }//end of for loop
        
        //now each such subcategory should be a new group like the
        // countries in the example
        
        Vector<String> suppliers = new  Vector<String>();
        //the series are the suppliers, i.e. like the years in our example
        
        /**
         * for each subcategory we build a vector of supplier 
         * which have products in that sub-category
         */
        
        Vector<Vector<String>> suppliersInCategory = new Vector<Vector<String>>();
        
        for(String subCategory:subcategories){
	        suppliers = dbh.findSupplierWithSubCat(subCategory);
	        suppliersInCategory.add(suppliers);
	        for(String sup:suppliers){System.out.println(sup + "--->"+subCategory);}
        }
        
        ObservableList<Products> products; //= FXCollections.observableArrayList();
        
        for(int j=0; j < suppliersInCategory.size(); j++){
        	for(int i =0; i< suppliersInCategory.get(j).size();i++){
	        	//for(String subCategory:subcategories){
		        	XYChart.Series series = new XYChart.Series();
		        	series.setName(suppliersInCategory.get(j).get(i) + "-->"+subcategories.get(j));
		        	seriesVect.add(series);
		        	
		        	String q =" NAME ='" +suppliersInCategory.get(j).get(i).trim()+"' AND SUB_CAT_NAME ='" 
		        						+subcategories.get(j).trim()+"'";
		        	products = dbh.getProductsToObservList(q);
		        	
		        	for(Products pr: products){
		        	
		        		series.getData().add(new XYChart.Data(
		        				subcategories.get(j), pr.getPricePerKilo()));
		        	
		        	}//end of products loop
	
		        //}//end for subcat loop
	        }//end of suppliers loop
        
        }//end 
        
        bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Τιμές Προιόντων ανα Προμηθευτών");
        xAxis.setLabel("Προμηθευτής");       
        yAxis.setLabel("Τιμή");
		
        // bc.getData().addAll(series1, series2, series3);
        for(XYChart.Series series :seriesVect){
        	bc.getData().add(series);
        }
        
	}//end of constructor
	
	public void displayChart(){
		new ChartsViewer(bc);
	}
	
	
	
}
