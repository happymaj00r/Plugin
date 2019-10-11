package myvcrime;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	
	private Material mat;
	private String displayName;
	List<String> lore;
	private ItemStack item;
	private ItemMeta meta;
	
	public ItemBuilder(Material mat){
		this.mat = mat;
		this.buildItem();
	}
	
	public ItemBuilder(Material mat, String displayName, List<String> lore){
		this.mat = mat;
		this.displayName = displayName;
		this.lore = lore;
		this.buildItemWithMeta();
	}
	
	public ItemBuilder(Material mat, int amount){
		this.mat = mat;
		this.buildItem(mat, amount);
	}
	
	private void createItemStack(){
		item = new ItemStack(mat);
	}
	
	private void createItemStack(Material mat,int amount){
		item = new ItemStack(mat, amount);
	}
	
	private void setItemMeta(){
		meta = item.getItemMeta();
	}
	
	private void setLore(){
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	private void buildItem(Material mat, int amount){
		createItemStack(mat, amount);
	}
	
	private void buildItem(){
		createItemStack();
	}
	
	private void setDisplayName(){
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
	}
	  
	private void buildItemWithMeta(){
		createItemStack();
		setItemMeta();
		setLore();
		setDisplayName();
	}	
		
	public ItemStack getItem(){
		return item;
	}
}
