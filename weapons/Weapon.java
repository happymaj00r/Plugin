package myvcrime.weapons;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class Weapon {
	
	private ItemStack item;
	private ArrayList<Modifications> modificationsList = new ArrayList<Modifications>();
	
	public Weapon(ItemStack item){
		this.item = item;
	}

	public ItemStack getItem(){
		return item;
	}
	
	protected void shoot(){
		
	}
	
	public void addModification(Modifications modification){
		modificationsList.add(modification);
	}
	
	public ArrayList<Modifications> getModifications(){
		return modificationsList;
	}
	
}
