package myvcrime.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.abilities.AbilityExecutor;
import myvcrime.abilities.AbilityItems;
import myvcrime.abilities.Superhuman;
import myvcrime.abilities.SuperhumanManager;
import myvcrime.abilities.UpgradeManager;
import myvcrime.quests.KillQuestObjective;
import myvcrime.quests.QuestObjective;
import myvcrime.quests.QuestType;
import net.md_5.bungee.api.ChatColor;

public class PlayerEvents implements Listener{
	
	
	
	main plugin;
	CustomEventInstances CEI;
	
	public PlayerEvents(Plugin plugin,CustomEventInstances CEI){
		this.plugin = (main) plugin;
		this.CEI = CEI;
	}
	
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent e){
		ItemStack stack = e.getItemDrop().getItemStack();
		if(stack.hasItemMeta()){
			ItemMeta meta = stack.getItemMeta();
			if(meta.hasLore()){
				List<String> list = meta.getLore();
				if(list.contains("Soulbound")){
					e.setCancelled(true);
				}
			}
		}
		if(plugin.questManager.isQuestItem(stack))
			e.setCancelled(true);
	}
	
	private HashMap<String,ItemStack[]> playerContentMap = new HashMap<String,ItemStack[]>();
	
	@EventHandler
	public void onPlayerRespawnEvent(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		String name = p.getName();
		if(playerContentMap.containsKey(name)){
			ItemStack[] content = playerContentMap.get(name);
			for(ItemStack item : content){
				if(item != null){
					if(plugin.questManager.isQuestItem(item))
						p.getInventory().addItem(item);
				}
			}
		}
	}
	
	private HashMap<ItemStack,Integer> itemDurabilityMap = new HashMap<ItemStack,Integer>();
	
	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent e){
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if(item.getType() == Material.GOLD_SWORD){
			e.setCancelled(true);
			if(!itemDurabilityMap.containsKey(item)){
				itemDurabilityMap.put(item, 1);
			} else {
				int dura = itemDurabilityMap.get(item);
				if(dura >= 17){
					item.setDurability((short) (item.getDurability()+1));
					itemDurabilityMap.put(item, 0);
					if(item.getDurability() == 33){
						itemDurabilityMap.remove(item);
						p.getInventory().removeItem(item);
					}
				} else {
					dura++;
					itemDurabilityMap.put(item, dura);
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
       	 	if(p.hasMetadata("NPC")){
       	 	 e.setCancelled(true);
    		 return;
       	 	}
		}
	}
	
	
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e){
		
		Iterator<ItemStack> iterator = e.getDrops().iterator();
		Player p = e.getEntity();
		
		while(iterator.hasNext()){
			ItemStack stack = iterator.next();
			if(!stack.hasItemMeta())
				continue;
			ItemMeta meta = stack.getItemMeta();
			if(!meta.hasLore())
				continue;
			if(meta.getLore().contains("Soulbound"))
				iterator.remove();		
			if(plugin.questManager.isQuestItem(stack))
				iterator.remove();
		}
		playerContentMap.put(p.getName(), p.getInventory().getContents());
	}
	
	@EventHandler
	public void onPlayerDamageByPlayer(EntityDamageByEntityEvent e){
		Entity entity = e.getEntity();
		
		if(entity instanceof Player){
			if(e.getDamager() instanceof Player){				
				Player attacker = (Player) e.getDamager();
				Player damaged = (Player) e.getEntity();				
				
				e.setCancelled(CEI.PrisonClass.onPlayerDamageByPlayer(damaged, attacker));
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		if(SpielerProfil.playerCuffed.contains(e.getPlayer().getName())){
			if(e.getTo().getY() > e.getFrom().getY()){
				Player p = e.getPlayer();
				Vector vec = new Vector(p.getVelocity().getX(),e.getFrom().getY() - e.getTo().getY(),p.getVelocity().getZ());
				e.getPlayer().setVelocity(vec);
			}	
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e){
  	  final Player p = e.getPlayer();
  	  if(p.getItemInHand() != null){
  		  if(e.getHand() == EquipmentSlot.OFF_HAND){
  			  
  		  } else {     
  			  if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
  				  ItemStack item = p.getItemInHand();
  				  if(plugin.questManager.isQuestItem(item)){
  					  QuestType quest = plugin.questManager.getQuestTypeOfItem(item);
  					  if(plugin.questManager.isQuestItemCompleted(quest, p)){
  						  plugin.questManager.finishQuest(quest, p);
  					  }
  				  }
  			  }
  		  }
  	  }
	}
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e){
		Inventory inv = e.getClickedInventory();
		if(inv.getTitle().equalsIgnoreCase("QuestMaster")){
			ItemStack item = e.getCurrentItem();
			if(item == null)
				return;
			if(item.getType() == Material.WRITTEN_BOOK){
				Player p = (Player) e.getWhoClicked();
				if(Utility.getFreeSlotsOfInventory(p) == 0){
					p.sendMessage(ChatColor.RED + "Nicht genügend Platz im Inventar!");
					return;
				}
				p.getInventory().addItem(item);
				inv.remove(item);
				ItemMeta meta = item.getItemMeta();
				String name = meta.getDisplayName();
				for(QuestType quest : QuestType.values()){
					if(quest.getName() != name)
						continue;
					plugin.questManager.addQuest(quest, p);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerDisconnectEvent(PlayerQuitEvent e){
		Player p = e.getPlayer();
		String name = p.getName();
		boolean doJail = false;
		if(SpielerProfil.isParalyzed(name)){
			doJail = true;
		}
		if(SpielerProfil.isAttemptingToEnterJail(name)){
			doJail = true;
		}
		if(doJail)
			SpielerProfil.sentToJail(p);
				
		if(plugin.raidClass.isPlayerRobbing(p)){
			String shop = plugin.raidClass.getNameOfRobbedShop(p);
			plugin.raidClass.onPlayerFleed(p, shop);
		}		
	}	
	
	@EventHandler
	public void onAttack(PlayerInteractEvent e){
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
			Player p = e.getPlayer();
			ItemStack item = p.getItemInHand();
			if(SpielerProfil.isWeapon(item)){
				Superhuman superhuman = SuperhumanManager.getSuperhuman(p);
				if(superhuman != null){
					AbilityExecutor abilityExecutor = new AbilityExecutor();
					ArrayList<AbilityItems> abilityItemsList = UpgradeManager.getActiveUpgrades(superhuman,UpgradeManager.getUpgradeTypeByItem(item));
					if(abilityItemsList.isEmpty()) return;
					for(AbilityItems abilityItem : abilityItemsList){
						abilityExecutor.executeAbility(p, abilityItem.getAbility());
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e){
		Player p = e.getEntity().getKiller();
		
		if(!plugin.questManager.hasQuest(p))
			return;
		
		QuestType[] quests = plugin.questManager.getCurrentQuests(p);
		for(QuestType quest : quests){
			QuestObjective objective = quest.getObjective();
			if(objective instanceof KillQuestObjective){
				KillQuestObjective killObjective = (KillQuestObjective) objective;
				if(killObjective.isMet(e)){
					plugin.questManager.addQuestPoints(quest, p, 1);
				}
			}
		}
	}
	
	
}
