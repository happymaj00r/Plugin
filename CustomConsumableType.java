package myvcrime;

import org.bukkit.Material;

public enum CustomConsumableType implements CustomItemsInterface {

	FREIBRIEF("Freiticket",Material.PAPER,"Right click to be free!");
	
	String name;
	Material mat;
	String description;
	
	CustomConsumableType(String name, Material mat, String description){
		this.name = name;
		this.mat = mat;
		this.description = description;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return mat;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}
	
}
