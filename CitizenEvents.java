package myvcrime;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class CitizenEvents implements Listener{
	
	main plugin;
	
	public CitizenEvents(main plugin){
		this.plugin = plugin;
	}
	
    @EventHandler
    public void onClickNPC(NPCRightClickEvent e){
    	if(e.getClicker() instanceof Player){
    		Player p = e.getClicker();
    		NPC npc = e.getNPC();
    		Location loc = npc.getStoredLocation();
    		String bar = plugin.barsInstance.getBarOfNPC(loc);
 
    		if(bar != ""){
    			if(plugin.barsInstance.getOwner(bar) != ""){
    				Inventory inv = plugin.barsInstance.getBarInventory(bar);
    				p.openInventory(inv);
    			} else {
    				p.sendMessage(ChatColor.RED + "Du musst die Bar erst einnehmen!");
    			}
    		}
    		
    		if(npc.getName().equalsIgnoreCase("QuestMaster")){
    			plugin.questManager.showQuests(p);
    		}
    	}
    }
}
