package myvcrime;

import org.bukkit.Material;

public enum ToolsType implements CustomToolsInterface {
	
	BOLTCUTTER(Material.GOLD_SPADE,(short) 1);
	
	Material mat;
	short durability;
	
	ToolsType(Material mat,short durability){
		this.mat = mat;
	}
	
	public Material getMaterial(){
		return mat;
	}

	@Override
	public String getName() {
		return this.toString();
	}

	@Override
	public String getDescription() {
		return "";
	}
	
	@Override
	public short getDeltaDurability(){
		return durability;
	}
}
