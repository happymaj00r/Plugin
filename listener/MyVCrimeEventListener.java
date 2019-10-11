package myvcrime.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import myvcrime.CustomItemType;
import myvcrime.CustomItems;
import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.WeaponsType;
import myvcrime.main;
import myvcrime.customEvents.PlayerBoughtEvent;
import myvcrime.customEvents.PlayerCrimeDepletedEvent;
import myvcrime.customEvents.PlayerMinedEvent;
import myvcrime.customEvents.PlayerOpenedChestEvent;
import myvcrime.customEvents.PlayerPickupBottlesEvent;
import myvcrime.customEvents.PlayerRobEvent;
import myvcrime.customEvents.PlayerSoldEvent;
import myvcrime.quests.QuestManager;

public class MyVCrimeEventListener implements Listener {
	
	Plugin plugin;
	main Main;
	
	public MyVCrimeEventListener(Plugin plugin){
		this.plugin = plugin;
		this.Main = (main) plugin;
	}
	
	@EventHandler
	public void onChestOpening(PlayerOpenedChestEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
	}
	
	@EventHandler
	public void onCrimeReachedZero(PlayerCrimeDepletedEvent e){
		Player p = e.getPlayer();
		String name = p.getName();
		
		if(SpielerProfil.prisoner.contains(name)){
			ItemStack is = CustomItems.getCustomItem(CustomItemType.FREIBRIEF);
			if(!p.getInventory().contains(is)){
				p.getInventory().addItem(is);					
				p.sendMessage(ChatColor.GRAY + "Du hast deine Strafe abgesessen.");		
			}
		}
	}
	
	@EventHandler
	public void onPlayerRob(PlayerRobEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
	}
	
	@EventHandler
	public void onPlayerBought(PlayerBoughtEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
		
		ItemStack stack = e.getItem();
		if(stack.getType() == Material.DIAMOND_SWORD){
		}
		if(stack.getType() == Material.GOLD_SWORD){
			stack.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
			if(stack.hasItemMeta()){
				ItemMeta meta = stack.getItemMeta();
				if(meta.hasDisplayName()){
					if(meta.getDisplayName().equalsIgnoreCase(WeaponsType.CLEAVER.getName())){
						List<String> list = new ArrayList<String>();
						list.add("Rüstungsdurchdringung");
						meta.setLore(list);
						stack.setItemMeta(meta);
					}
				}
			}
		}
	}
	//EFFECTS
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player){
			Player p = (Player) e.getDamager();
			ItemStack hand = p.getItemInHand();
			if(hand == null)
				return;
			if(!hand.hasItemMeta())
				return;
			if(Utility.hasLoreAndContains(hand, "Rüstungsdurchdringung")){
				if(e.isApplicable(DamageModifier.ARMOR)){
					e.setDamage(DamageModifier.ARMOR, 0);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerMined(PlayerMinedEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
	}
	
	@EventHandler
	public void onPlayerPickupBottlesEvent(PlayerPickupBottlesEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
	}
	
	@EventHandler
	public void onPlayerSoldEvent(PlayerSoldEvent e){
		Player p = e.getPlayer();
		QuestManager manager = Main.questManager;
		manager.handleQuestEvent(p, e);
	}
}
