package view;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;




public class TableViewer {


	
		Stage primaryStage;
		Button bk, addProducts;
		BorderPane border ;
		HBox topBox;
		Scene scene;
		
		
	/**
	 * 	Displays a table in the scene with the given buttons
	 * @param primaryStage, the stage in which the table will be displayed at
	 * @param table, the table we want to display
	 * @param mbar, the menu bar for the scene, i.e. the application menu
	 * @param rb1, a button which if not null will be displayed at the right of the scene
	 * @param rb1Text, the text for the first button
	 * @param rb2, a button which if not null will be displayed at the left of the scene
	 * @param rb2Text, the text for rb2 button
	 * @param rb3, a button which if not null will be displayed at the left of the scene
	 * @param rb3Text, text for rb3
	 * @param rb4, a button which if not null will be displayed at the left of the scene
	 * @param rb4Text, text for rb4
	 * @param supliers, a String Choice box which should contain the suppliers of the database
	 * @param categories, a String Choice box which should contain the categories of the database
	 * @param subCategories, a String Choice box which should contain the subCategories of the database
	 */
	public TableViewer(Stage primaryStage, TableView<?> table, MenuBar mbar, 
			Button rb1, String rb1Text, Button rb2, String rb2Text, 
			Button rb3, String rb3Text, Button rb4, String rb4Text,
			ChoiceBox<String> supliers, 
			ChoiceBox<String> categories, ChoiceBox<String> subCategories, 
			Button search, TextField txt, String tableTitle, Button delete, 
			Button addCat,Button addSubCategory, 
			Button sort, Button newProd,Button export){
			
			this.primaryStage = primaryStage;
			double width = primaryStage.getWidth();
			double height = primaryStage.getHeight();
			double centerWidth = width/2;
			
			
			
			border = new BorderPane();
			border.setPadding(new Insets(0, 10, 0, 10));
			
			table.setPrefWidth(centerWidth);
			table.setPrefHeight( (4*height)/5);
			table.setMaxHeight( (4*height)/5);
			
			border.setCenter(table);
			
			
			topBox = new HBox(10);
			
			Text scenetitle = new Text(tableTitle);
			scenetitle.setId("welcome-text");
			
			topBox.getChildren().addAll(scenetitle);
			
			border.setTop(scenetitle);
			
			//the right pane
			VBox rBox = new VBox();
			rBox.setPadding(new Insets(0, 10, 0, 10));
			
			//the left pane
			VBox lBox = new VBox(10);
			lBox.setPadding(new Insets(0, 10, 0, 10));
			
			//The bottom box wrapper
			HBox bWrapper = new HBox(10);
			bWrapper.setPadding(new Insets(0, 0, 40, width/8));
			
			if( supliers != null && categories!=null && subCategories != null){
				VBox filtersWrapper = new VBox(8);
				
				Text filterTiltle = new Text("Φίλτρα");
				filterTiltle.setId("FilterTitle");
				
				
				
				
				Text categText = new Text("Κατηγορία: ");
				categText.setWrappingWidth(100);
				categText.setId("FilterSmallTitle");
				
				Text subCatText = new Text("Υποκ/ία: ");
				subCatText.setWrappingWidth(100);
				subCatText.setId("FilterSmallTitle");
				
				
				Text supText = new Text("Προμ/τές: ");
				supText.setWrappingWidth(100);
				supText.setId("FilterSmallTitle");
				
				HBox categoriesBox = new HBox();
				categoriesBox.getChildren().addAll(categText ,categories);
				HBox subCategoriesBox = new HBox();
				subCategoriesBox.getChildren().addAll(subCatText, subCategories);
				
				HBox suplBox = new HBox();
				suplBox.getChildren().addAll(supText, supliers);
				
				filtersWrapper.getChildren().addAll(filterTiltle,categoriesBox,
						subCategoriesBox, suplBox );
				
				HBox supFilters = new HBox(10);
				search.setText("");
				search.setId("searchID");
				//image = new Image(getClass().getResourceAsStream("/resources/search.png"));
				//search.setGraphic(new ImageView(image));
				
				
				//TextField txt TextField txt = new TextField();
				Text searchText = new Text("Αναζήτηση ");
				searchText.setWrappingWidth(100);
				searchText.setId("FilterSmallTitle");
				supFilters.getChildren().addAll(searchText,txt,search);
				
				VBox searchWrapper = new VBox(5);
				searchWrapper.setPadding(new Insets(33, 0, 0, 0));
				searchWrapper.getChildren().add(supFilters);
				
				HBox addingButtonsBox = new HBox(5);
				addingButtonsBox.setPadding(new Insets(33, 0, 0, 0));
				addingButtonsBox.getChildren().addAll(delete,addCat, 
						addSubCategory);
				addCat.setText("Νέα Κατηγορία");
				addCat.setId("addCat");
				addSubCategory.setText("Νέα Υποκατηγορία");
				addSubCategory.setId("addCat");
				
				
				
				HBox secondRow = new HBox(10);
				secondRow.getChildren().addAll(newProd,export);
				
				VBox bottomButtonsWrapper  = new VBox(5);
				bottomButtonsWrapper.setPadding(new Insets(0, 0, 0, 0));
				bottomButtonsWrapper.getChildren().addAll(addingButtonsBox,secondRow);
				
				
				
				delete.setId("delButton");
				delete.setText("Διαγραφή");
				newProd.setId("addProd");
				newProd.setText("Νέο Προιόν");
				export.setId("export");
				export.setText("Εξαγωγή σε XLS");
				
				
				
				bWrapper.getChildren().addAll(filtersWrapper, searchWrapper,
						bottomButtonsWrapper);
			}
			
			if(rb1 != null){
				rBox.getChildren().addAll(rb1);
				
				switch(rb1Text){
				
					case "Αποθήκευση": 	rb1.setId("save");
										break;
					
					case "Κατηγορίες":	rb1.setId("category");
										break;									
					
					default: 			break;					
				}//end of switch
				
				if(rb1Text.equals("Αποθήκευση")){
					
				}
				rb1.setText(rb1Text);
			}//end if rb1 is not null
			
			if(rb2!=null){
				lBox.getChildren().addAll(rb2);
				
				
				switch(rb2Text){
				
					case "Επιστροφή": 	rb2.setId("back");
										//image = new Image(getClass().getResourceAsStream("/resources/back_small.png"));
										//rb2.setGraphic(new ImageView(image));
										break;
					
					case "Κατηγορίες":	rb2.setId("category");
										//image = new Image(getClass().getResourceAsStream("/resources/categories.png"));
										//rb2.setGraphic(new ImageView(image));
										break;
					
					default: 			break;					
				}//end of switch
				
				rb2.setText(rb2Text);
			}//end if rb2 is not null
			
			
			if(rb3!=null){
				lBox.getChildren().addAll(rb3);
				switch(rb3Text){
				
					case "Υποκατ/ίες":	rb3.setId("subCat");
											//image = new Image(getClass().getResourceAsStream("/resources/sub.png"));
											//rb3.setGraphic(new ImageView(image));
											break;
					
					default: 				break;					
				}//end of switch
				rb3.setText(rb3Text);
			}//end if rb2 is not null
			
			
			if(rb4!=null){
				lBox.getChildren().addAll(rb4);
				switch(rb4Text.trim()){
				
					case "Προιόντα":		rb4.setId("products");
											//image = new Image(getClass().getResourceAsStream("/resources/suppliers_med.png"));
											//rb4.setGraphic(new ImageView(image));
											break;
					
					default: 				break;					
				}//end of switch
				rb4.setText(rb4Text);
			}//end if rb2 is not null
			
			
			if(sort!= null){
				lBox.getChildren().add(sort);
				sort.setId("sort");
				sort.setText("Φθηνότερα");
			}
			
			
			border.setRight(rBox);
			border.setLeft(lBox);
			border.setBottom(bWrapper);
			
			VBox sceneWrapper = new VBox();
			sceneWrapper.getChildren().addAll(mbar, border);
			
			scene = new Scene(sceneWrapper, primaryStage.getWidth(),primaryStage.getHeight());
			scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
			
		    this.primaryStage.setScene(scene);
			
	}//end SuppliersTableView
		
		
		

	
	
	
	
	
	
	public void displayView(){
			this.primaryStage.show();
	}
		
		
		
		
		
		
		
}//end of class
