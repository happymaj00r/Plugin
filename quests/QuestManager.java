package myvcrime.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import myvcrime.ItemBuilder;
import myvcrime.Utility;
import myvcrime.main;
import net.md_5.bungee.api.ChatColor;

public class QuestManager {
	
	Plugin plugin;
	
	private enum Path{		
		PATH_FINISHED_QUESTS() {
			@Override
			public String getPath(Player p) {
				return "MyViCrime.SpielerProfile." + p.getName() + ".FinishedQuests";
			}
		},		
		PATH_QUEST_LEVEL() {
			@Override
			public String getPath(Player p) {		
				return "MyViCrime.SpielerProfile." + p.getName() + ".QuestLevel";
			}
		};		
		abstract public String getPath(Player p);
	}
	
	HashMap<String,Inventory> inventoryNameMap = new HashMap<String,Inventory>();
	
	public QuestManager(Plugin plugin){
		this.plugin = plugin;
	}
	
	public int getQuestPoints(QuestType type, Player p){
		int points = (int) plugin.getConfig().get("MyViCrime.SpielerProfile." + p.getName() + ".Quest" + type.toString());
		return points;
	}
	
	public void setQuestPointsOfQuestItem(QuestType type, Player p, int val){
		ItemStack[] content = p.getInventory().getContents();
		for(ItemStack item : content){
			
			if(item == null)
				continue;
			if(!item.hasItemMeta())
				continue;
			
			ItemMeta meta = item.getItemMeta();
			String itemName = meta.getDisplayName();
			if(itemName.equalsIgnoreCase(type.getName())){
				List<String> list = new ArrayList<String>();
				if(val >= type.getNeededPoints()){
					list.add("COMPLETED");
					list.add("RIGHT CLICK ME!");
				} else {
					list.add(type.getDescription());
					list.add(val + "/" + type.getNeededPoints());
				}
				meta.setLore(list);
				item.setItemMeta(meta);							
			}
		}
	}
	
	public QuestType getQuestTypeOfItem(ItemStack item){
		if(item.hasItemMeta()){
			ItemMeta meta = item.getItemMeta();
			String name = meta.getDisplayName();
			for(QuestType quest : QuestType.values()){
				if(quest.getName().equalsIgnoreCase(name)){
					return quest;
				}
			}
		}
		return null;
	}
	
	public void handleQuestEvent(Player p, Event e){	
		//QUESTS
		if(hasQuest(p)){
			QuestType[] quests = getCurrentQuests(p);
			for(QuestType quest : quests){
				if(hasQuest(p, quest)){
					QuestObjective objective = quest.getObjective();
					if(objective.isMet(e)){
						int points = objective.getPoints(e);
						addQuestPoints(quest, p, points);
					}
				}
			}
		}
	}
	
	public boolean isQuestItem(ItemStack item){
		if(getQuestTypeOfItem(item) != null)
			return true;
		return false;
	}
	
	public boolean isQuestItemCompleted(QuestType type, Player p){
		ItemStack item = getQuestItem(type,p);
		if(item != null){
			if(item.hasItemMeta()){
				ItemMeta meta = item.getItemMeta();
				String name = meta.getDisplayName();
				if(type.getName().equalsIgnoreCase(name)){
					List<String> lore = meta.getLore();
					for(int i = 0; i < lore.size(); i++){
						if(lore.get(i).equalsIgnoreCase("COMPLETED")){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
		
	public void addQuestPoints(QuestType type, Player p, int amount){
		plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Quest" + type.toString(), getQuestPoints(type,p) + amount);
		plugin.saveConfig();
		
		p.sendMessage("[" + ChatColor.YELLOW+ type.getName() + ChatColor.WHITE +"] " + getQuestPoints(type,p) + "/" + type.getNeededPoints());
						
		setQuestPointsOfQuestItem(type, p, getQuestPoints(type,p));
	}
	
	public void removeQuestPoints(QuestType type, Player p, int amount){
		plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Quest" + type.toString(), getQuestPoints(type,p) - amount);
		plugin.saveConfig();
	}
	
	public boolean isQuestFinished(QuestType type, Player p){
		if(getQuestPoints(type, p) >= type.getNeededPoints())
			return true;
		return false;
	}
	
	public void addFinishedQuestToConfig(QuestType type, Player p){
		String path = Path.PATH_FINISHED_QUESTS.getPath(p);
		if(!plugin.getConfig().contains(path))
			initializeConfig(p);

		List<String> list = plugin.getConfig().getStringList(path);
		if(!list.contains(type.toString()))
			list.add(type.toString());
		
		plugin.getConfig().set(path, list);
		plugin.saveConfig();
	}
	
	public boolean isQuestCompleted(QuestType type, Player p){
		String path = Path.PATH_FINISHED_QUESTS.getPath(p);
		if(!plugin.getConfig().contains(path))
			return false;
		
		List<String> list = plugin.getConfig().getStringList(path);
		if(list.contains(type.toString()))
			return true;
		
		return false;
	}
	
	public void initializeConfig(Player p){
		String path = Path.PATH_FINISHED_QUESTS.getPath(p);
		String pathLevel = Path.PATH_QUEST_LEVEL.getPath(p);
		List<String> list = new ArrayList<String>();
		if(!plugin.getConfig().contains(path)){
			plugin.getConfig().set(path, list);
		}
		if(!plugin.getConfig().contains(pathLevel)){
			plugin.getConfig().set(pathLevel, 1);
		}
		plugin.saveConfig();
	}
	
	public void finishQuest(QuestType type, Player p){
		if(hasQuest(p,type)){
			if(isQuestFinished(type,p)){
				ItemStack[] loot = type.getLoot();
				if(Utility.getFreeSlotsOfInventory(p) >= loot.length){
					Utility.sendTitle(p, type.getName(), ChatColor.GREEN + "Erfolgreich abgeschlossen!");					
					for(int i = 0; i < loot.length; i++)
						p.getInventory().addItem(loot[i]);					
					main Main = (main) plugin;
					Main.economy.depositPlayer(p, type.getPrice());
					
					if(p.getScoreboardTags().contains(type.toString()))
						p.removeScoreboardTag(type.toString());
					
					addFinishedQuestToConfig(type,p);
					removeQuestItem(type,p);
				} else {
					p.sendMessage(ChatColor.RED + "Nicht genügend Platz im Inventar!");
				}
			}
		}
	}
	
	public ItemStack getQuestItem(QuestType type, Player p){
		ItemStack[] content = p.getInventory().getContents();
		for(ItemStack item : content){
			if(item == null)
				continue;
			if(!item.hasItemMeta())
				continue;
			
			ItemMeta meta = item.getItemMeta();
			String name = meta.getDisplayName();
			if(name.equalsIgnoreCase(type.getName()))
				return item;
		}
		return null;
	}
	
	public void removeQuestItem(QuestType type, Player p){
		ItemStack item = getQuestItem(type,p);
		if(item == null)
			return;

		p.getInventory().remove(item);
	}
	
	public void addQuest(QuestType type, Player p){
		Set<String> tags = p.getScoreboardTags();
		if(tags.contains(type.toString()))
			return;
		
		p.addScoreboardTag(type.toString());		
		Utility.sendTitle(p, type.getName(), ChatColor.GREEN + type.getDescription());
		plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Quest" + type.toString(), 0);
		plugin.saveConfig();
	}
	
	public void removeQuest(QuestType type, Player p){
		Set<String> tags = p.getScoreboardTags();
		if(tags.contains(type.toString()))
			p.removeScoreboardTag(type.toString());
	}
	
	public boolean hasQuest(Player p){
		try{
		if(p.getScoreboardTags() == null)
			return false;
		Set<String> tags = p.getScoreboardTags();
		if(tags.size() == 0)
			return false;
		for(QuestType type : QuestType.getQuests()){
			String questName = type.toString();
			if(tags.contains(questName))
				return true;
		}
		return false;
		} catch(NullPointerException e){
			return false;
		}
	}
	
	public boolean hasQuest(Player p, QuestType type){
		QuestType[] quests = getCurrentQuests(p);
		for(QuestType q : quests){
			if(q.getName().equalsIgnoreCase(type.getName()))
				return true;
		}
		return false;
	}
	
	public void showQuests(Player p){
		String name = p.getName();
		if(!inventoryNameMap.containsKey(name))
			initializeQuestInventory(p);
		Inventory inv = inventoryNameMap.get(name);
		p.openInventory(inv);
	}
	
	public void initializeQuestInventory(Player p){
		Inventory inv = Bukkit.createInventory(p, 18, "QuestMaster");
		QuestType[] quests = QuestType.values();
		for(QuestType quest : quests){
			//CHECK FOR ACCEPTED, FOR COMPLETED AND FOR LEVEL
			
			if(hasQuest(p, quest))
				continue;
			if(quest.getLevel() > getQuestLevel(p))
				continue;
			if(isQuestCompleted(quest, p))
				continue;
			
			List<String> lore = new ArrayList<String>();
			lore.add(quest.getDescription());
			lore.add(0 + "/" + quest.getNeededPoints());
			ItemStack item = new ItemBuilder(Material.WRITTEN_BOOK,quest.getName(),lore).getItem();
			inv.addItem(item);
		}
		inventoryNameMap.put(p.getName(), inv);
	}
	
	public double getQuestLevel(Player p){
		String path = Path.PATH_QUEST_LEVEL.getPath(p);
		if(!isConfigInitialized(p))
			initializeConfig(p);
		double lvl = plugin.getConfig().getDouble(path);
		return lvl;
	}
	
	public void addQuestLevel(Player p, double amount){
		if(!isConfigInitialized(p))
			initializeConfig(p);
		double currentLevel = plugin.getConfig().getDouble(Path.PATH_QUEST_LEVEL.getPath(p));
		double newLevel = currentLevel + amount;
		plugin.getConfig().set(Path.PATH_QUEST_LEVEL.getPath(p), newLevel);
		plugin.saveConfig();
	}
	
	public void setQuestLevel(Player p, double val){
		if(!isConfigInitialized(p))
			initializeConfig(p);
		plugin.getConfig().set(Path.PATH_QUEST_LEVEL.getPath(p), val);
		plugin.saveConfig();
	}
	
	public void clearCompletedQuestList(Player p){
		List<String> list = new ArrayList<String>();
		plugin.getConfig().set(Path.PATH_FINISHED_QUESTS.getPath(p), list);
		plugin.saveConfig();
	}
	
	public boolean isConfigInitialized(Player p){
		String path = Path.PATH_FINISHED_QUESTS.getPath(p);
		String pathLevel = Path.PATH_QUEST_LEVEL.getPath(p);
		if(!plugin.getConfig().contains(path) || !plugin.getConfig().contains(pathLevel))
			return false;
		return true;
	}
	
	public QuestType[] getCurrentQuests(Player p){
		List<QuestType> quests = new ArrayList<QuestType>();		
		Set<String> tags = p.getScoreboardTags();
		for(QuestType type : QuestType.getQuests()){
			String questname = type.toString();
			if(tags.contains(questname))
				quests.add(type);
		}
		QuestType[] arry = (QuestType[]) quests.toArray(new QuestType[quests.size()]);
		return arry;
	}
}
