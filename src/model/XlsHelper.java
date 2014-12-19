package model;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import view.LoginView;


public class XlsHelper {
	
	final int catCol = 1;
	final int subcatCol = 2;
	final int invCodeCol = 3;
	final int ekemsCodeCol = 4;
	final int descrCol = 5;
	final int priceOfInvCol = 6;
	final int gramsCol = 7;
	final int pricePerKilCol = 8;
	final int suplCol = 9;
	
	DbHelper dbh;
	
	
	public XlsHelper(DbHelper dbh){
		this.dbh = dbh;
	}
	
	
	/**
	 * 
	 * @param xls, an XLS file containing an EKEMS price list data 
	 * @return an Observable list of productsFromXls objects 
	 * containing the parsed xls data
	 */
	public ObservableList<ProductsParsedFromFile> readXlsFile(File xls){
		
		ObservableList<ProductsParsedFromFile> data = FXCollections.observableArrayList();
		Workbook workbook;
			
		String category, subCategory, description, supplier, invCode,ekemsCode ="";
		float invPrice, gramsForInvPrice, pricePerKilo =-1;
				
			try {
				workbook = Workbook.getWorkbook(xls);
				Sheet sheet = workbook.getSheet(0);
				int numOfRows = sheet.getRows();
					
				for(int i=1; i < numOfRows; i++){
					String cell = sheet.getCell(0,i).getContents();
						
						if(!cell.isEmpty())
							
							if(cell.toCharArray().length>0 && Character.isDigit(cell.toCharArray()[0]))
							{
								category = sheet.getCell(catCol,i).getContents();
								subCategory = sheet.getCell(subcatCol,i).getContents();
								description = sheet.getCell(descrCol,i).getContents();
								supplier = sheet.getCell(suplCol,i).getContents();
								invCode =  sheet.getCell(invCodeCol,i).getContents();
								ekemsCode =  sheet.getCell(ekemsCodeCol,i).getContents();
								
								invPrice =  Float.parseFloat(sheet.getCell(priceOfInvCol,i).getContents().replace(",","."));
								gramsForInvPrice =  Float.parseFloat(sheet.getCell(gramsCol,i).getContents().replace(",","."));
								pricePerKilo = Float.parseFloat(sheet.getCell(pricePerKilCol,i).getContents().replace(",","."));
								
								data.add(new ProductsParsedFromFile(category, supplier, 
										subCategory, description, invCode, ekemsCode, invPrice,
										gramsForInvPrice, 0, 0, pricePerKilo));
							}//end if its a digit
					 }//end of for loop
				} catch (Exception e) {e.printStackTrace(); } 
				
				return data;
	}//end readXlsFile
	
	
	
	
	
	/**
	 * 
	*/
	public void addXlsDataToDB(ObservableList<ProductsParsedFromFile> data, 
			final ProgressBar bar, Stage st, LoginView lv){
		dbh.insertProductsFromXlsToDB(data, bar, st,lv);
	}//end of addXlsDataToDB
	
	
	
	/**
	 * writes the given observable list to an xls file
	 */
	public void writeObsListToXLS(ObservableList<Products> data, String filePath){
		
		
		
		File file = new File("aaa.xls");
		 WorkbookSettings wbSettings = new WorkbookSettings();

		 //wbSettings.setLocale(new Locale("en", "EN"));

		 WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		    // Define the cell format
		 WritableCellFormat times = new WritableCellFormat(times10pt);
		 
		 
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			 workbook.createSheet("ΤΙΜΟΚΑΤΑΛΟΓΟΣ", 0);
			 WritableSheet excelSheet = workbook.getSheet(0);
			 //   createLabel(excelSheet);
			 //   createContent(excelSheet);

			 int no = 1;
			 
			 addLabel(excelSheet, 0, 0, "A/A", times);
			 addLabel(excelSheet, 1, 0, "ΚΑΤΗΓΟΡΙΑ", times);
			 addLabel(excelSheet, 2, 0, "ΥΠΟΚΑΤΗΓΟΡΙΑ", times);
			 addLabel(excelSheet, 3, 0, "ΚΩΔΙΚΟΣ ΤΙΜΟΛΟΓΙΟΥ", times);
			 addLabel(excelSheet, 4, 0, "ΚΩΔΙΚΟΣ ΕΚΕΜΣ", times);
			 addLabel(excelSheet, 5, 0, "ΠΕΡΙΓΡΑΦΗ", times);
			 addLabel(excelSheet, 6, 0, "ΤΙΜΗ ΤΙΜΟΛΟΓΙΟΥ", times);
			 addLabel(excelSheet, 7, 0, "ΓΡΑΜΜΑΡΙΑ ΠΟΥ ΑΝΑΦΕΡΕΤΑΙ Η ΤΙΜΗ", times);
			 addLabel(excelSheet, 8, 0, "ΤΙΜΗ ΑΝΑ ΚΙΛΟ", times);
			 addLabel(excelSheet, 9, 0, "ΠΡΟΜΗΘΕΥΤΗΣ", times);
			 
			 
			 for(Products pr: data){
				 
				 String ekemsCode = pr.getEkemsCode();
				 String invCode = pr.getEkemsCode();
				 String category = pr.getCategory();
				 String subCategory = pr.getSubCategory();
				 String desc = pr.getDescription();
				 float invprice = pr.getInvPrice();
				 float grams = pr.getGramsForInvPrice();
				 float ppk = pr.getPricePerKilo();
				 String supl = pr.getSupplier();
				 
				 addNumber(excelSheet, 0, no, no, times);
				 addLabel(excelSheet, 1, no, category, times);
				 addLabel(excelSheet, 2, no, subCategory, times);
				 addLabel(excelSheet, 3, no, invCode, times);
				 addLabel(excelSheet, 4, no, ekemsCode, times);
				 addLabel(excelSheet, 5, no, desc, times);
				 addFloat(excelSheet, 6, no, invprice, times);
				 addFloat(excelSheet, 7, no, grams, times);
				 addFloat(excelSheet, 8, no, ppk, times);
				 addLabel(excelSheet, 9, no, supl, times);
				 no++;
			 }
			 
			 
			    workbook.write();
			    workbook.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}//end of writeObsListToXLS
	
	
	
	private void addNumber(WritableSheet sheet, int column, int row,
		Integer integer,  WritableCellFormat times) 
				throws WriteException, RowsExceededException {
		jxl.write.Number number;
		number = new jxl.write.Number(column, row, integer, times);
		sheet.addCell(number);
	}//end of addNumber

	private void addFloat(WritableSheet sheet, int column, int row,
			Float fl,  WritableCellFormat times) 
					throws WriteException, RowsExceededException {
			jxl.write.Number number;
			number = new jxl.write.Number(column, row, fl, times);
			sheet.addCell(number);
	}//end of addNumber
	
	
	
	
	private void addLabel(WritableSheet sheet, int column, int row, String s, 
			WritableCellFormat times)
		      throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}
	
	
	
}
