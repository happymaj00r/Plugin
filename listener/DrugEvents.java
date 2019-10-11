package myvcrime.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class DrugEvents implements Listener{

	Plugin plugin;
	
	public DrugEvents(Plugin plugin){
		this.plugin = plugin;
	}
		
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getHand() == EquipmentSlot.OFF_HAND) {
                return; // off hand packet, ignore.
            }
            Block clickedBlock = e.getClickedBlock();
            if(clickedBlock.getType() == Material.BREWING_STAND){
            	Player p = e.getPlayer();
            	if(p.isSneaking()){
            		cookDrug(p);
            	}
            }
		}
	}
	
	private void cookDrug(Player p){
		if(p.getInventory().getItemInMainHand().getType() == Material.SUGAR_CANE){
			p.getInventory().removeItem(new ItemStack[]{new ItemStack(Material.SUGAR_CANE,1)});
			p.updateInventory();
			int rand = Jobs.zufall(0, 100);
			ItemStack is = new ItemStack(Material.SUGAR);
			ItemMeta im = is.getItemMeta();		
			
			
			// Probability rates for the drugs
			if(rand >= 0 && rand <= 40){ //40%
				im.setDisplayName("Speed II");
			}
			else if(rand >= 41 && rand <= 70){ //30%
				im.setDisplayName("Speed III");				
			}
			else if(rand >= 71 && rand <= 84){ //15%
				im.setDisplayName(ChatColor.LIGHT_PURPLE + "Speed IV");					
			}
			else if(rand >= 85 && rand <= 89){ // 5%
				is.setType(Material.INK_SACK);
				im.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Cocain");	
			}
			else if(rand >= 90 && rand <= 100){ // 10%
				im.setDisplayName("Speed I");
			}		
			
			is.setItemMeta(im);
			p.getInventory().addItem(is);
			p.updateInventory();
		}
	}
}
