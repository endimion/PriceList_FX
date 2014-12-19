package view;

import java.util.Vector;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ChartsViewer {

	Stage stage;
	
	
	
	BarChart<String,Number> bc;
	
	
	
	int categoryID= 383;
	
	@SuppressWarnings("rawtypes")
	Vector<XYChart.Series> seriesVect = new Vector<XYChart.Series>();
	
	
	Vector<String> subcategories = new Vector<String>();
	
	
	public ChartsViewer(BarChart<String,Number> bc){

		stage = new Stage();
		Scene scene  = new Scene(bc);
	    stage.setScene(scene);
	    stage.show();
        
   }//end of constructor
	
	
	
}//end of class




















/*series1 = new XYChart.Series();
series1.setName("2003");       
series1.getData().add(new XYChart.Data(austria, 25601.34));
series1.getData().add(new XYChart.Data(brazil, 20148.82));
series1.getData().add(new XYChart.Data(france, 10000));
series1.getData().add(new XYChart.Data(italy, 35407.15));
series1.getData().add(new XYChart.Data(usa, 12000));      

series2 = new XYChart.Series();
series2.setName("2004");
series2.getData().add(new XYChart.Data(austria, 57401.85));
series2.getData().add(new XYChart.Data(brazil, 41941.19));
series2.getData().add(new XYChart.Data(france, 45263.37));
series2.getData().add(new XYChart.Data(italy, 117320.16));
series2.getData().add(new XYChart.Data(usa, 14845.27));  

series3 = new XYChart.Series();
series3.setName("2005");
series3.getData().add(new XYChart.Data(austria, 45000.65));
series3.getData().add(new XYChart.Data(brazil, 44835.76));
series3.getData().add(new XYChart.Data(france, 18722.18));
series3.getData().add(new XYChart.Data(italy, 17557.31));
series3.getData().add(new XYChart.Data(usa, 92633.68));  */
