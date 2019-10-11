package myvcrime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class CustomItems {
	
	Plugin plugin;

	
	public CustomItems(Plugin plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("static-access")
	public static boolean isCustomItem(ItemStack is){
		if(is.hasItemMeta()){
			ItemMeta im = is.getItemMeta();
			
			String displayName = is.getItemMeta().getDisplayName();
			for(CustomItemType c : CustomItemType.DAILYCHEST.getCustomTypes()){		
				if(ChatColor.stripColor(c.getType().getName()).equalsIgnoreCase(ChatColor.stripColor(displayName))){
					return true;
				}
			}			
			for(CustomItemType customItemType : CustomItemType.getCustomTypes()){
				Material customItemMaterial = customItemType.getType().getMaterial();
				String customItemName = ChatColor.stripColor(customItemType.getType().getName());
				String itemName = ChatColor.stripColor(im.getDisplayName());
				if(customItemMaterial == is.getType()){
					if(customItemName == itemName){
						//Material and name is the same = is custom item = return true;
						return true;
					}
				}
			}						
			return false;
		} else {
			return false;
		}
	}
	
	public static CustomItemType getCustomItemType(ItemStack is){
		ItemMeta im = is.getItemMeta();
		String itemName = ChatColor.stripColor(im.getDisplayName());
		for(CustomItemType customItemType : CustomItemType.getCustomTypes()){			
		}
		return null;
	}
	
	
	public static void decreaseDurabilityInHand(Player p,CustomItemType type){
		ItemStack is = p.getItemInHand();
		if(isCustomItem(is)){
			if(type.getType() instanceof CustomToolsInterface){
				CustomToolsInterface customTool = (CustomToolsInterface) type.getType();
				short deltaDurability = customTool.getDeltaDurability();
				is.setDurability((short) (is.getDurability() + deltaDurability));
			}
		}
	}
	
	public static ItemStack getCustomItem(CustomItemType type){
		CustomItemsInterface customType = type.getType();
		List<String> list = new ArrayList<String>();
		list.add(customType.getDescription());
		ItemStack is = new ItemBuilder(customType.getMaterial(),customType.getName(),list).getItem();
		return is;
	}
}
