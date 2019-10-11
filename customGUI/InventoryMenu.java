package myvcrime.customGUI;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.IInventory;
import net.minecraft.server.v1_12_R1.PlayerInventory;

public class InventoryMenu extends PlayerInventory implements IGUI {

	private ArrayList<IButton> buttonList = new ArrayList<IButton>();
	private IGUI nextInventory = null;
	private IGUI backInventory = null;
	
	private Inventory inv;
	
	public InventoryMenu(EntityHuman p){
		super(p);
		init();
	}
	
	public void bindToItem(ItemStack is){
		
	}
	
	@Override
	public void onClick(Player p, ItemStack clickedItem){
		if(clickedItem.getType() == Material.ACACIA_DOOR_ITEM){
			ItemMeta im = clickedItem.getItemMeta();
			if(im.getDisplayName() == "Next Page"){
				if(nextInventory != null){
					nextInventory.open(p);
				}
			}
			if(im.getDisplayName() == "Back Page"){
				if(backInventory != null){
					backInventory.open(p);
				}
			}
			return;
		}
		
		for(IButton button : buttonList){
			ItemStack is = button.getItemStack();
			if(is.getType() == clickedItem.getType()){
				if(is.hasItemMeta() && clickedItem.hasItemMeta()){
					ItemMeta im = is.getItemMeta();
					ItemMeta im2 = clickedItem.getItemMeta();
					if(ChatColor.stripColor(im.getDisplayName()) == ChatColor.stripColor(im2.getDisplayName())){
						button.click(p);
					}
				} else if(!(is.hasItemMeta() && clickedItem.hasItemMeta())){
					button.click(p);	
				}
			}
		}
	}	
	
	@Override
	public Inventory getInventory(){
		return inv;
	}
	
	private void init(){
		IInventory iv = this;
		CraftInventory craftInventory = new CraftInventory(iv);
		inv = craftInventory;
		
		Inventory inventory = inv;
		ItemStack is = new ItemStack(Material.ACACIA_DOOR_ITEM);
		ItemStack is2 = new ItemStack(Material.ACACIA_DOOR_ITEM);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Next Page");
		is.setItemMeta(im);
		
		im = is2.getItemMeta();
		im.setDisplayName("Back Page");
		is2.setItemMeta(im);
		
		inventory.setItem(this.getSize() - 6, is);
		inventory.setItem(this.getSize() - 14, is2);
	}

	@Override
	public void addButton(IButton button) {
		if(buttonList.size() < this.getSize() - 1){
			buttonList.add(button);
			
			ItemStack is = new ItemStack(button.getMaterial());
			
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(button.getName());
			ArrayList<String> list = new ArrayList<String>();
			list.add(button.getDescription());
			im.setLore(list);
			is.setItemMeta(im);
			
			inv.addItem(is);
		}
	}	
	
	@Override
	public void addButton(IButton button, int index) {
		if(buttonList.size() < this.getSize() - 1){
			buttonList.add(button);
			
			ItemStack is = button.getItemStack();
			
			/*
			ItemMeta im = is.getItemMeta();
			im.setDisplayName(button.getName());
			ArrayList<String> list = new ArrayList<String>();
			list.add(button.getDescription());
			im.setLore(list);
			is.setItemMeta(im);
			*/
			
			inv.setItem(index, is);
		}
	}	

	@Override
	public void addPage(IGUI inv) {
		if(nextInventory == null){
			nextInventory = inv;
			inv.addBackPage(this);
		} else {
			while(nextInventory.getNextPage() != null){
				nextInventory = nextInventory.getNextPage(); //Get the last Inventory in the chain!
			}
			nextInventory.addPage(inv);
			nextInventory.getNextPage().addBackPage(nextInventory);
		}
	}
	
	@Override
	public IGUI getNextPage(){
		return nextInventory;
	}
	
	@Override
	public void open(Player p){
		if(p.getOpenInventory() != null){
			p.closeInventory();
			new BukkitRunnable(){
				@Override
				public void run(){
					p.openInventory(inv);
				}
			}.runTaskLater(main.getPlugin(), 3L);
		} else {
			p.openInventory(inv);
		}
	}

	@Override
	public void addBackPage(IGUI inv) {
		backInventory = inv;
	}
}
