package model;

/**
 * 
 * @author Nikos Triantafyllou
 * This method provides with data which cane be used to generate a JComboBox for editing
 * the JTable displaying the subcategories which is used as an editor in 
 * the products table view
 */

public class SubCatPair implements VectorPairs{

	final private int  cat_id;
	final private Integer id;
	final private String sub_cat_name;


	public SubCatPair(String sub_cat_name, int cat_id, Integer the_id)	{
		this.sub_cat_name = sub_cat_name;
		this.id = the_id;
		this.cat_id = cat_id;
	}

		
	public String getSubCatName(){
		return this.sub_cat_name;
	}

		
	public Integer getID(){
		return id;
	}

	
	public int getCatID(){
		return cat_id;
	}


	
	@Override
	public String getName() {
		return this.sub_cat_name;
	}
	
	@Override
	public Integer getKey() {
		return id;
	}

	
	
}
