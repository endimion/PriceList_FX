package model;

public class SuppliersPair implements VectorPairs{

	final private String cat_name;
	final private Integer id;


	public SuppliersPair(String name, Integer the_id)	{
		cat_name = name;
		id = the_id;
	}

	@Override
	public String getName(){
		return this.cat_name;
	}

	
	public Integer getID(){
		return id;
	}
	
	
	@Override
	public Integer getKey(){
		return id;
	}
	
	
	
	
	
	
}
