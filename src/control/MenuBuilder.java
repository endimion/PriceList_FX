package control;

import java.io.File;
import java.util.Vector;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DbHelper;
import model.ProductsParsedFromFile;
import model.VectorPairs;
import model.XlsDataTableBuilder;
import model.XlsHelper;
import view.LoginView;
import view.TableViewer;





public class MenuBuilder {

	Stage st;
	DbHelper dbh;
	LoginView lv;
	ChoiceBox<String> catChoice;
	
	public MenuBuilder(Stage the_stage, DbHelper dbh, LoginView lv, 
									ChoiceBox<String> catChoice){
		st = the_stage;
		this.dbh = dbh;
		this.lv = lv;
		
	}
	
	
	
	
	public void updateCatChoice(ChoiceBox<String> newCatChoice){
		this.catChoice = newCatChoice;
	}
	
	
	public MenuBar getMenu(){
		
		final MenuBar menuBar = new MenuBar();
		menuBar.setId("menu");
		 
        // --- Menu File
        Menu menuFile = new Menu("Αρχείο");
        MenuItem addΧLS = new MenuItem("Εισαγωγή Αρχείου XLS");
        
        
        
        final Vector<VectorPairs> catVect = dbh.getCategoriesIntoVector();
		final Vector<VectorPairs> supVect = dbh.getSuppliersIntoVector();
		final Vector<VectorPairs> subVect = dbh.getSubCategoriesIntoVector(); 

		final ObservableList<String> subCatList = FXCollections.observableArrayList();;
		final ObservableList<String> suplList = FXCollections.observableArrayList();;
		final ObservableList<String> catList = FXCollections.observableArrayList();;
		
			
		final TextField txt = new TextField();
		
		
		for(VectorPairs spl:supVect){
			suplList.add(spl.getName());
		}//end of loop
			
		for(VectorPairs sbp:subVect){
			subCatList.add(sbp.getName());
		}//end of loop
		
		for(VectorPairs cat:catVect){
			catList.add(cat.getName());
		}//end of loop
        
        
        final Button search = new Button();
        
        
        
        
        final Button back = new Button();
        back.setOnAction(event->{
        	ProductsTableController ptc = new ProductsTableController(st, dbh,lv);
    		ptc.buildAndDisplayProductsTable(false);
        });
        
        
        
        addΧLS.setOnAction(event ->{
        	FileChooser fc = new FileChooser();
        	fc.setTitle("Εισαγωγή αρχείου XLS προμηθευτών");
        	File selected = fc.showOpenDialog(st); 

        	if(selected != null ){
        		XlsHelper xlsh = new XlsHelper(dbh);
        	
        		ObservableList<ProductsParsedFromFile> xlsProds = 
        				xlsh.readXlsFile(selected);
        		
        		XlsDataTableBuilder xdtb = new XlsDataTableBuilder() ;
        		
        		Button saveB = new Button();
        		saveB.setOnAction(event2->{
  
        			Task<Void> showTask = new Task<Void>() {
        			    @Override
        			    protected Void call() throws Exception {
        			        Platform.runLater(new Runnable() {
        			            public void run() {
        			            	ProgressBar bar = new ProgressBar(0);
        			            	if(xlsProds != null){
        		        				xlsh.addXlsDataToDB(xlsProds, bar,st,lv);
        		        			}else{
        		        				System.out.println("no data!!!!");
        		        			}
        			               }//end of run
        			           });//end of runLater
        			            return null;
        			       }//end of call
        			    };//end of task
        			    new Thread(showTask).start();
        		});//end of saveB set on action
        		
        		
        		
        		
        		
        		TableViewer tv = new TableViewer(st, 
        				xdtb.buildProductsTable(xlsProds), menuBar, 
        				saveB, "Αποθήκευση",back,"Επιστροφή", null, "",null,"", 
        				null, null, null,search,txt,"Τιμοκατάλογος XLS",null,
        				null,null,null,null,null);
        		tv.displayView();
        	}//end of if selected is null
        });//end of addXLS setonaction
        
        MenuItem exit = new MenuItem("Έξοδος");
        exit.setOnAction(event ->{
        	st.close();
        });
        
       
        MenuItem logout = new MenuItem("Αποσύνδεση");
        logout.setOnAction(event->{
        	LoginController login = new LoginController(st, dbh);
			login.displayView();
        });
        
        
        
        MenuItem parseUnixNoCategory = new MenuItem("Άνοιγμα αρχείου Unixfor χωρίς κατηγορίες");
        parseUnixNoCategory.setOnAction(event ->{
        	FileChooser fc = new FileChooser();
        	fc.setTitle("Εισαγωγή αρχείου XLS προμηθευτών");
       	  	File selected = fc.showOpenDialog(st); 
      	  	UnixForFilesController ufncc = 
      	  			new UnixForFilesController(selected, dbh, st,lv,catChoice);
       	  	ufncc.readAndDisplayUnixFile();
        
        });
        
        
        MenuItem parseUnixWithCategory = new MenuItem("Άνοιγμα αρχείου Unixfor ME κατηγορίες");
        parseUnixWithCategory.setOnAction(event ->{
        	FileChooser fc = new FileChooser();
        	fc.setTitle("Εισαγωγή αρχείου XLS προμηθευτών");
       	  	File selected = fc.showOpenDialog(st); 
      	  	UnixForFilesController ufncc = 
      	  			new UnixForFilesController(selected, dbh, st,lv,catChoice);
       	  	ufncc.readAndDisplayUnixCategoryFile();
        
        });
        
        
        
        menuFile.getItems().addAll(addΧLS,parseUnixNoCategory,
        		parseUnixWithCategory,new SeparatorMenuItem(), logout,exit);
        // --- Menu View
        Menu menuAbout = new Menu("Σχετικά");
        
        
        Menu diagrams = new Menu("Διαγράμματα");
        MenuItem diag1 = new MenuItem("Διάγραμμα Κατηγοριών-Υποκατηγοριών");
        
        diag1.setOnAction(event->{
        	//ChartsViewer d1 = new ChartsViewer(dbh.getSubCategoriesIntoVector(),dbh, 
        	//								catChoice);
        	//d1.showChart();
        	ChartsCategorySupplierControl ccsc = 
        			new ChartsCategorySupplierControl(dbh.getSubCategoriesIntoVector(),dbh, 
        									catChoice);
        					
        	ccsc.displayChart();				
        });
        
        diagrams.getItems().add(diag1);
        
        menuBar.getMenus().addAll(menuFile,diagrams, menuAbout);
        
        return  menuBar;
	}//end getMenu
	

	
	
	
	
	
}
