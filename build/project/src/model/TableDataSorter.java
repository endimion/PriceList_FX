package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableDataSorter {

	
	
	Products secondMatch;
	
	public TableDataSorter(){
		
	}
	
	
	
	
	public ObservableList<Products>  sortProductsDataBySubCategory(
			ObservableList<Products> data){
		
		ObservableList<Products> sortedData = FXCollections.observableArrayList();
		
		//boolean newSubCatFound = false;
		//int sortedSize = 0;
		
		int start =0 ;
		int end = 0;

		if(data.size() ==1){
			sortedData.add(data.get(0));
		}else{
		
			for(int j= 0; j < data.size()-1; j++){
					
				Products selected = data.get(j);
				Products next = data.get(j+1);
				Products min =null;
				
				System.out.println(j);
				
				if(!selected.getSubCategory().equals(next.getSubCategory())){
					if(j+1 == data.size() -1){
						min = sortAndExtract(data,start,j);
						sortedData.add(min);
						
						sortedData.add(sortAndExtract(data,j+1,j+1));
						
					}else{
						end = j;
						min = sortAndExtract(data,start,end);
						sortedData.add(min);
					}//end if j+1 != data.size() -1
					
					start = j+1;

				}//end if !selected.getSubCategory().equals(next.getSubCategory())
				else{
					if(j+1 == data.size() -1 ){
						min = sortAndExtract(data,j,j+1);
						sortedData.add(min);
					}
					
				}//end of else selected.getSubCategory().equals(next.getSubCategory()
				
				
				if(secondMatch!=null && min!=null &&
						secondMatch.getPricePerKilo() == min.getPricePerKilo()
						&& secondMatch.getSubCategory().equals(min.getSubCategory())){
					sortedData.add(secondMatch);
					secondMatch = null;
				}
				
			}//end of innerloop
		}//end of if data.size() !=1
		
		return sortedData;
	}
	
	
	
	
	
	private Products sortAndExtract(ObservableList<Products> data, int start, int end){
		
		System.out.println(+start + " "+end);
		
		if(start == end){
			return data.get(start);
		}else{
			Products current = data.get(start);
			
			for(int j= start; j< end;j++){
				Products next = data.get(j+1);
				if(current.getPricePerKilo() > next.getPricePerKilo()){
					Products temp = current;
					current = next ;
					next = temp;
					
				}else{
					if(current.getPricePerKilo() == next.getPricePerKilo()){
						secondMatch = current;
						System.out.println("second "+secondMatch.getCategory());
					}
				}
			}//end of for loop
			
			
			return current;
		}//end of else
		
		
		
		
	}//end of method
	
	
	
	
	
	
	
	
	
	
	
	
}
