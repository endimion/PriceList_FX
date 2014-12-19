package control;

import java.io.File;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import model.DbHelper;
import model.ProductsParsedFromFile;
import model.UnixForFileHelper;
import model.XlsDataTableBuilder;
import view.LoginView;
import view.ProgressBarPopWindow;
import view.TableViewer;


public class UnixForFilesController {

	File the_file;
	DbHelper dbh;
	Stage st;
	ProgressBarPopWindow pbm;
	LoginView lv;
	ChoiceBox<String> catChoice;
	
	public UnixForFilesController(File unixforFile, DbHelper dbh, Stage st, 
			LoginView lv,ChoiceBox<String> catChoice){
		the_file = unixforFile;
		this.dbh = dbh;
		this.st = st;
		this.lv = lv;
		this.catChoice = catChoice;
	}///UnixForNoCategoryController
	
	
	
	public void readAndDisplayUnixFile(){
		UnixForFileHelper ufh = new UnixForFileHelper(the_file, st);
		ObservableList<ProductsParsedFromFile> data = ufh.ParseFileWithNoCategories();
		
		XlsDataTableBuilder xdtb = new XlsDataTableBuilder() ;
		xdtb.buildProductsTable(data);
		
		final control.MenuBuilder mb = new control.MenuBuilder(st,dbh,lv,catChoice);	
		MenuBar mbar = mb.getMenu();
		
		Button back = new Button();
		back.setOnAction(event ->{
			ProductsTableController ptc = new ProductsTableController(st, dbh,lv);
			ptc.buildAndDisplayProductsTable(false);
		});
		
		Button save = new Button();
		save.setOnAction(event ->{
			
			
			
			Task<Void> showTask = new Task<Void>() {
			    @Override
			    protected Void call() throws Exception {
			        Platform.runLater(new Runnable() {
			            public void run() {
			            	ProgressBar bar = new ProgressBar(0);
			            	pbm = new ProgressBarPopWindow(bar, "Μεταφορά Εγγραφών στη Βάση Δεδομένων", new Stage());
							ufh.saveParsedFileWithNoCategoryToDb(data, dbh, bar,pbm);
							
			            	
			            	
			               }
			           });
			            return null;
			       }
			    };
			
			    new Thread(showTask).start();
			    showTask.setOnSucceeded(event2->{
					//if(pbm!=null){pbm.getStage().close();}
				});
			    	
			    
			
		});//end of setonaction
		
		
		TableViewer tv = new TableViewer(st, 
				xdtb.buildProductsTable(data), mbar, 
				save, "Αποθήκευση", back,"Επιστροφή", null, "",null,"", 
				null, null, null, null, null,"Τιμοκατάλογος XLS",
				null,null,null,null,null,null);
		tv.displayView();
		
	}//end readAndDisplayUnixFile
	
	
	
	
	public void readAndDisplayUnixCategoryFile(){
		UnixForFileHelper ufh = new UnixForFileHelper(the_file, st);
		ObservableList<ProductsParsedFromFile> data = ufh.ParseFileWithCategories();
		
		XlsDataTableBuilder xdtb = new XlsDataTableBuilder() ;
		xdtb.buildProductsTable(data);
		
		final control.MenuBuilder mb = new control.MenuBuilder(st,dbh,lv,catChoice);	
		MenuBar mbar = mb.getMenu();
		
		Button back = new Button();
		back.setOnAction(event ->{
			ProductsTableController ptc = new ProductsTableController(st, dbh,lv);
			ptc.buildAndDisplayProductsTable(false);
		});
		
		Button save = new Button();
		save.setOnAction(event ->{
			
			
			
			Task<Void> showTask = new Task<Void>() {
			    @Override
			    protected Void call() throws Exception {
			        Platform.runLater(new Runnable() {
			            public void run() {
			            	ProgressBar bar = new ProgressBar(0);
			            	pbm = new ProgressBarPopWindow(bar, "Μεταφορά Εγγραφών στη Βάση Δεδομένων", new Stage());
							
			            	
			            	
			            	ufh.saveParsedFileWithNoCategoryToDb(data, dbh, bar,pbm);
							
			               }
			           });
			            return null;
			       }
			    };
			
			    new Thread(showTask).start();
			    showTask.setOnSucceeded(event2->{
					//if(pbm!=null){pbm.getStage().close();}
				});
			    	
			    
			
		});//end of setonaction
		
		
		TableViewer tv = new TableViewer(st, 
				xdtb.buildProductsTable(data), mbar, 
				save, "Αποθήκευση", back,"Επιστροφή", null, "",null,"", 
				null, null, null, null, null,"Τιμοκατάλογος XLS",
				null,null,null,null,null,null);
		tv.displayView();
		
	}//end readAndDisplayUnixFile
	
}
