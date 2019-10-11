package myvcrime;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class Tools implements Listener{
	
	Plugin plugin;
	
	public Tools(Plugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void event(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Player){
			Player p = e.getPlayer();
			Material itemInHand = p.getItemInHand().getType();
			
			if(!(e.getHand() == EquipmentSlot.HAND))
				return;
			
			if(itemInHand == ToolsType.BOLTCUTTER.getMaterial()){
				Player clickedPlayer = (Player) e.getRightClicked();
				String name = clickedPlayer.getName();
				if(SpielerProfil.isParalyzed(name)){
					SpielerProfil.deParalyze(name);
					ItemStack item = p.getItemInHand();
					
					clickedPlayer.sendMessage(ChatColor.YELLOW + name + ChatColor.GREEN + " hat dir die Handschellen abgenommen!");
					CustomItems.decreaseDurabilityInHand(p, CustomItemType.BOLTCUTTER);
				}
			    if(clickedPlayer.getScoreboardTags().contains("handschellenEffect")){
			    	clickedPlayer.removeScoreboardTag("handschellenEffect");
			    	if(clickedPlayer.hasPotionEffect(PotionEffectType.SLOW))
			    		clickedPlayer.removePotionEffect(PotionEffectType.SLOW);
				}
			    
			    if(SpielerProfil.isFleeingPrisoner(clickedPlayer.getName())){
			    	SpielerProfil.setFleeingPrisoner(clickedPlayer.getName(), false);
			    }		    
			    
			    if(SpielerProfil.prisoner.contains(clickedPlayer.getName())){
			    	SpielerProfil.prisoner.remove(clickedPlayer.getName());
			    }
			    
			    p.getInventory().removeItem(p.getItemInHand());
			}
		}
	}
}
