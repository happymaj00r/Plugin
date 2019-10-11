/*     */ package myvcrime;
/*     */ 
/*     */ import java.util.ArrayList;
import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
import java.util.Set;

/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.chat.Msg;
import myvcrime.customGUI.InventoryMenu;
import myvcrime.customGUI.LocatorButton;
import myvcrime.customGUI.WarpButton;
import myvcrime.customGUI.WarpSetterButton;
import myvcrime.listener.EventListener;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityZombie;


/*     */ 
/*     */ public class SpielerProfil
/*     */   implements Listener
/*     */ {
	
			private static final int maxWarpAmount = 6;
/*     */   public static main plugin;
/*  22 */   public static ArrayList<String> configList = new ArrayList<String>();
/*  23 */   public static HashMap<String, Integer> copWarnung = new HashMap<String, Integer>();
			public static ArrayList<String> playerCuffed = new ArrayList<String>();
			public static ArrayList<String> prisoner = new ArrayList<String>();
			public static HashMap<String,Location> playerSpawn = new HashMap<String,Location>();
			public static ArrayList<String> attemptingJail = new ArrayList<String>();
			public static ArrayList<String> protectedPlayer = new ArrayList<String>();
			public static ArrayList<String> acceptedRessourcePack = new ArrayList<String>();
			private static ArrayList<String> dailyReward = new ArrayList<String>();
			private static HashMap<String,HashMap<String,Location>> playerWarpsMapMap = new HashMap<String,HashMap<String,Location>>();
			private static ArrayList<String> playerHandschellen = new ArrayList<String>();
/*     */ 	private static HashMap<String,InventoryMenu> inventoryMenuMap = new HashMap<String,InventoryMenu>();
			
/*     */   public SpielerProfil(main instance)
/*     */   {
/*  18 */     plugin = instance;
/*     */   }

			public enum Locations {
				
				LOCATION_PRISON(new Location(Bukkit.getWorld("Map"),-12,49,-156)),
				LOCATION_RATHAUS(new Location(Bukkit.getWorld("Map"), -22.5,69,-240.5)),
				LOCATION_PRISON_DOOR(new Location(Bukkit.getWorld("Map"),-38,64,-200)),
				LOCATION_DAILY_CHEST(new Location(Bukkit.getWorld("Map"),-22,70,-255)),
				LOCATION_TEST_CHEST(new Location(Bukkit.getWorld("Map"),17,78,-116)),
				LOCATION_GUNSHOP(new Location(Bukkit.getWorld("Map"),21,65,-198)),
				LOCATION_CASINO(new Location(Bukkit.getWorld("Map"),278,64,-105)),
				LOCATION_BANK(new Location(Bukkit.getWorld("Map"),-43,55,-291)),
				LOCATION_MINE_SHOP(new Location(Bukkit.getWorld("Map"),-281.5,63,81.5));
				
				Location loc;
				
				Locations(Location loc){
					this.loc = loc;
				}
				public Location getLocation(){
					return this.loc;
				}
			}
			public static void initializePlayerprofile(Player p){
				/* 157 */     if (!plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName() + ".Level"))
				/*     */     {
				/* 161 */       plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Level", Integer.valueOf(1));
				/* 163 */       plugin.saveConfig();
				/*     */     }

				/* 157 */     if (!plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName() + ".Crime"))
				/*     */     {
				/* 161 */       plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Crime", 0);
				/* 163 */       plugin.saveConfig();
				/*     */     }
				/*     */ 
				/* 157 */     if (!plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName() + ".Exp"))
				/*     */     {
				/* 161 */       plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Exp", Integer.valueOf(1));
				/* 163 */       plugin.saveConfig();
				/*     */     }

				/* 157 */     if (!plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName() + ".Rang"))
				/*     */     {
				/* 161 */       plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Rang", "Citizen");
				/* 163 */       plugin.saveConfig();
				/*     */     }
				/*     */ 
				/* 177 */     if (!plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName() + ".Strafregister"))
				/*     */     {
								List<String> list = new ArrayList<String>();
								list.add("default");
				/* 183 */       plugin.getConfig().set("MyViCrime.SpielerProfile." + p.getName() + ".Strafregister", list);
				/* 185 */       plugin.saveConfig();
				/*     */     }
			}
			
			public static InventoryMenu getCompassInv(String name){
				if(inventoryMenuMap.containsKey(name)){
					return inventoryMenuMap.get(name);
				}
				return null;
			}
			
			
			public static void initCompass(Player p){
  			  EntityHuman entityHuman = (EntityHuman) ((CraftPlayer) p).getHandle();
  			  InventoryMenu inventoryMenu = new InventoryMenu(entityHuman);
  			  ItemStack casinoIs = new myvcrime.items.ItemBuilder(Material.GLOWSTONE)
  					  					.type(Material.GLOWSTONE)
  					  					.amount(1)
  					  					.lore("Aligns compass to casino")
  					  					.name("Casino")
  					  					.make();
  			  ItemStack warpButton1 = new myvcrime.items.ItemBuilder(Material.ENDER_PORTAL_FRAME)
		  									.type(Material.ENDER_PORTAL_FRAME)
		  									.amount(1)
		  									.lore("Defines a warp to current Location.")
		  									.name("Warp1")
		  									.make();
  			  ItemStack warpButton2 = new myvcrime.items.ItemBuilder(Material.ENDER_PORTAL_FRAME)
		  									.type(Material.ENDER_PORTAL_FRAME)
		  									.amount(1)
		  									.lore("Defines a warp to current Location.")
		  									.name("Warp2")
		  									.make();
  			  ItemStack warpButton3 = new myvcrime.items.ItemBuilder(Material.ENDER_PORTAL_FRAME)
		  									.type(Material.ENDER_PORTAL_FRAME)
		  									.amount(1)
		  									.lore("Defines a warp to current Location.")
		  									.name("Warp3")
		  									.make();
  			  ItemStack warpToLocationButton1 = new myvcrime.items.ItemBuilder(Material.ENDER_PEARL)
		  									.type(Material.ENDER_PEARL)
		  									.amount(1)
		  									.lore("Warps to defined Location")
		  									.name("Warp1")
		  									.make();
  			  ItemStack warpToLocationButton2 = new myvcrime.items.ItemBuilder(Material.ENDER_PEARL)
		  									.type(Material.ENDER_PEARL)
		  									.amount(1)
		  									.lore("Warps to defined Location")
		  									.name("Warp2")
		  									.make();
  			  ItemStack warpToLocationButton3 = new myvcrime.items.ItemBuilder(Material.ENDER_PEARL)
		  									.type(Material.ENDER_PEARL)
		  									.amount(1)
		  									.lore("Warps to defined Location")
		  									.name("Warp3")
		  									.make();
  			  
  			  LocatorButton casinoButton = new LocatorButton(casinoIs,SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
  			  WarpSetterButton warpSetterButton1 = new WarpSetterButton(warpButton1,"Warp1");
  			  WarpSetterButton warpSetterButton2 = new WarpSetterButton(warpButton2,"Warp2");
  			  WarpSetterButton warpSetterButton3 = new WarpSetterButton(warpButton3,"Warp3");
  			  WarpButton warpButton11 = new WarpButton(warpToLocationButton1,warpSetterButton1);
  			  WarpButton warpButton22 = new WarpButton(warpToLocationButton2,warpSetterButton2);
  			  WarpButton warpButton33 = new WarpButton(warpToLocationButton3,warpSetterButton3);
  			  inventoryMenu.addButton(casinoButton);
  			  inventoryMenu.addButton(warpSetterButton1);
  			  inventoryMenu.addButton(warpSetterButton2);
  			  inventoryMenu.addButton(warpSetterButton3);
  			  inventoryMenu.addButton(warpButton11);
  			  inventoryMenu.addButton(warpButton22);
  			  inventoryMenu.addButton(warpButton33);
  			  inventoryMenuMap.put(p.getName(), inventoryMenu);
			}
			
			public static void setSpawn(Player p,Location loc){
				playerSpawn.put(p.getName(), loc);
				p.setBedSpawnLocation(loc, true);
			}
			
			public static Location getSpawn(Player p){
				if(playerSpawn.containsKey(p.getName())){
					return playerSpawn.get(p.getName());
				}
				return null;
			}
			
            public static int getCrimeLevel(String name)
            {
            	String path = "MyViCrime.SpielerProfile."+ name+".Level";
            	int crimeLevel = plugin.getConfig().getInt(path);
            	
            	return crimeLevel;
            }
            
            public static boolean hasRessourcePack(String name){
            	if(acceptedRessourcePack.contains(name)){
            		return true;
            	} else {
            		return false;
            	}
            }
            
            public static String getCurrentRank(Player p){
            	String name = p.getName();
            	String path = "MyViCrime.SpielerProfile." + name + ".Rang";
            	if(plugin.getConfig().contains(path)){
            		String rang = plugin.getConfig().getString(path);
            		return rang;
            	} else {
            		return "";
            	}
            }
            
            public static void updateName(Player p){
            	String name = p.getName();
            	String rank = getCurrentRank(p);
            	int lvl = getCrimeLevel(name);
            	p.setDisplayName(ChatColor.DARK_RED +"[" + ChatColor.RED +rank + ChatColor.DARK_RED + "]"+ ChatColor.WHITE+ " : " + ChatColor.WHITE + name);
            	
            }
            
            public static void setRank(String name,String rank){
            	String path =  "MyViCrime.SpielerProfile."+ name+".Rang";
            	plugin.getConfig().set(path, rank);
            	Player p = Bukkit.getPlayer(name);
            	updatePermissions(p);
            	plugin.saveConfig();
            }
            
            public static void updatePermissions(Player p){
            	String rank = getCurrentRank(p);
            	switch(rank){
            	case "Admin":
            		p.setOp(true);
            		break;
            	case "Citizen":
            		break;
            	}
            }
            
            public static void setCrimeLevel(String name, int zahl)
            {
            	String path = "MyViCrime.SpielerProfile."+ name+".Level";
            	Utility.sendTitle(Bukkit.getPlayer(name), ChatColor.GREEN + "LEVEL UP!", "Du bist nun Level" + ChatColor.YELLOW + " " + zahl);
            	plugin.getConfig().set(path, zahl);
            	plugin.saveConfig();
            }
            
    
            
            public static int getCrimeExp(String name)
            {
            	String path = "MyViCrime.SpielerProfile."+ name+".Exp"; 
            	int crimeExp = plugin.getConfig().getInt(path);
            	
            	return crimeExp;
            }
            
            public static void onWanted(Player p){
            	Utility.sendTitle(p, ChatColor.RED + "Du wirst gesucht!", "");
            }
            
            
            public static void setCrimeExp(String name, int zahl)
            {
            	String path = "MyViCrime.SpielerProfile."+ name+".Exp"; 
            	plugin.getConfig().set(path, zahl);
            	plugin.saveConfig();
            }
                    
            public static void addCrimeExp(String name, int zahl)
            {
            	String path = "MyViCrime.SpielerProfile."+ name+".Exp";
            	int honorExp = plugin.getConfig().getInt(path);
            	int newHonorExp = honorExp + zahl;
            	
            	int honorLevel = getCrimeLevel(name);
            	
            	if(newHonorExp >= 3 * honorLevel + 5)
            	{         
            		int restExp = newHonorExp - 3 * honorLevel + 5;
            		setCrimeLevel(name, honorLevel + 1);
            		int messageHonorLevel = honorLevel + 1;
            		Bukkit.getPlayer(name).sendMessage(ChatColor.GREEN + "Dein Crime Level ist auf Stufe " + ChatColor.YELLOW + messageHonorLevel + ChatColor.GREEN + " gestiegen!");
            	    
            		int newHonorLevel = getCrimeLevel(name);
            		
            		for(int i = newHonorLevel; i < 99; i++)
            		{           			
                		int currentHonorLevel = getCrimeLevel(name);
                		
            			if(restExp >= 3 * currentHonorLevel + 5)
            			{
            				restExp = restExp - 3 * currentHonorLevel +5;
            				
                    		setCrimeLevel(name, currentHonorLevel + 1);
                    		int messageHonorLevel2 = currentHonorLevel + 1;
                    		Bukkit.getPlayer(name).sendMessage(ChatColor.GREEN + "Dein Crime Level ist auf Stufe " + ChatColor.YELLOW + messageHonorLevel2 + ChatColor.GREEN + " gestiegen!");       				
            			}
            			else
            			{
            				setCrimeExp(name, restExp);
            			}
            		}      		           		
            	}
            	else
            	{
            		setCrimeExp(name, newHonorExp);
            	}           	           	            	
            }
            
            public static boolean isFleeingPrisoner(String name){
            	Player p = Bukkit.getPlayer(name);
            	try{
            		Set<String> set = p.getScoreboardTags();
            		if(set.contains("fleeingPrisoner"))
            			return true;
            		return false;
            	} catch(NullPointerException e){
            		//no tags so return false
            		return false;
            	}
            }
            
            public static void setFleeingPrisoner(String name, boolean bool){
            	Player p = Bukkit.getPlayer(name);
            	if(bool){
            		try{
            			Set<String> set = p.getScoreboardTags();
            			if(set.contains("fleeingPrisoner"))
            				return;
            			p.addScoreboardTag("fleeingPrisoner");
            			plugin.crimeTick.removePlayer(p);
            		} catch (NullPointerException e){
            			//no tags so just add
            			p.addScoreboardTag("fleeingPrisoner");
            			plugin.crimeTick.removePlayer(p);
            		}
            		
            	} else {
            		try{
            			Set<String> set = p.getScoreboardTags();
            			if(set.contains("fleeingPrisoner")){
            				p.removeScoreboardTag("fleeingPrisoner");
            				plugin.crimeTick.addPlayer(p);
            			}
            		} catch(NullPointerException e){
            			//No tags so do nothing
            		}
            	}
            }
        
/*     */ 
/*     */   public static int getCrime(String name)
/*     */   {
			  String pathCrime = "MyViCrime.SpielerProfile."+name+".Crime";
			  if(plugin.getConfig().contains(pathCrime)){
/*  27 */     int returnCrime = (int) plugin.getConfig().get(pathCrime);
/*  28 */     return returnCrime;
			  } else {
				  return 0;
			  }
/*     */   }
/*     */ 
/*     */   public static void addCrime(Integer zahl, String name, String verbrechen)
/*     */   {
			  String pathCrime = "MyViCrime.SpielerProfile."+ name+".Crime";
/*     */ 	  String pathRegister = "MyViCrime.SpielerProfile." + name + ".Strafregister";
/*  39 */     List<String> currentList = plugin.getConfig().getStringList(pathRegister);
/*     */ 
/*  41 */     if ((!currentList.contains(verbrechen)) || (verbrechen.equalsIgnoreCase("raub")) || (verbrechen.equalsIgnoreCase("Cop kill") || (verbrechen.equalsIgnoreCase("Cop hit") ||(verbrechen.equalsIgnoreCase("PRISON BREAK")))))
/*     */     {
/*  43 */       currentList.add(verbrechen);
/*     */ 
/*  45 */       Player p = Bukkit.getPlayer(name);
/*     */ 
/*  47 */       p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[!] " + ChatColor.YELLOW + verbrechen + ChatColor.RED + " + " + zahl + " Crime!");
/*     */       EventListener.addCrimeTick(name);
				int currentCriem = getCrime(name);
				if(currentCriem < 48 && currentCriem + zahl >= 48){
					onWanted(p);
				}
/*  49 */       plugin.getConfig().set(pathCrime, Integer.valueOf(plugin.getConfig().getInt(pathCrime) + zahl.intValue()));
/*  50 */       plugin.getConfig().set(pathRegister, currentList);
/*  51 */       plugin.saveConfig();
/*     */     }
/*     */ 
/*     */ 
/*  76 */     plugin.saveConfig();
			  plugin.crimeTick.stopForPlayer(Bukkit.getPlayer(name), 60);
			  SpielerProfil.onCrime(Bukkit.getPlayer(name));
/*     */   }
/*     */ 
/*     */   public static void addCrime(Integer zahl, String name)
/*     */   {
			String pathCrime = "MyViCrime.SpielerProfile."+ name+".Crime";	
			  if(plugin.playerCrimeCooldown.containsKey(name)){
				plugin.playerCrimeCooldown.put(name, 60);  
			  } else {
				  plugin.playerCrimeCooldown.put(name, 60);
			  }
			  
/*     */     EventListener.addCrimeTick(name);
/*  87 */     plugin.getConfig().set(pathCrime, Integer.valueOf(plugin.getConfig().getInt(	pathCrime) + zahl.intValue()));
/*  88 */     plugin.saveConfig();
/* 111 */     plugin.saveConfig();
			  SpielerProfil.onCrime(Bukkit.getPlayer(name));
/*     */   }

			public static void giveKit(String name,KitType kit){
				Player p = Bukkit.getPlayer(name);
				ItemStack[] loot = kit.getLoot();
				if(Utility.getFreeSlotsOfInventory(p) >= loot.length){
					for(int i = 0; i < loot.length; i++){
						p.getInventory().addItem(loot[i]);
					}
					String serializedDate = Utility.serializeCalendarTime();
					plugin.getConfig().set("MyViCrime.SpielerProfile." + name + ".Kit" + kit.toString(), serializedDate);
					plugin.saveConfig();
					p.sendMessage(ChatColor.GREEN + "KIT " + kit.toString() + " erfolgreich benutzt.");
				} else {
					p.sendMessage(ChatColor.RED + "Nicht genügend Platz im Inventar!");
				}
			}

			public static boolean canKit(String name,KitType kit){
				String path = "MyViCrime.SpielerProfile." + name + ".Kit";
				boolean can = false;
				
				if(plugin.getConfig().contains(path + kit.toString())){
					String serializedDate = plugin.getConfig().getString(path + kit.toString());
					Calendar cal = Utility.deserializeCalendarTime(serializedDate);
					Calendar calendar = Calendar.getInstance();
					
					// HIER KITS DIE STÜNDLICH VERWENDET WERDEN KÖNNEN
					if(calendar.get(Calendar.DAY_OF_MONTH) != cal.get(Calendar.DAY_OF_MONTH) || calendar.get(Calendar.HOUR_OF_DAY) != cal.get(Calendar.HOUR_OF_DAY)){
						switch(kit.toString()){
						case "MAP":							
							return true;
						case "COMPASS":
							return true;
						}
					}
					
				} else {
					System.out.println("Kit " + kit.toString() + " nicht in der Konfig vorhanden");
					return true;
				}
				
				return false;
			}

			public static void setSpawnByRank(String name){
				Player p = Bukkit.getPlayer(name);
				String rank = getCurrentRank(p);
				switch(rank){
				case "Citizen":
					setSpawn(p,Locations.LOCATION_RATHAUS.getLocation());
					break;
				case "Prisoner":
					setSpawn(p,Locations.LOCATION_PRISON.getLocation());
					break;
				case "Admin":
					setSpawn(p,Locations.LOCATION_PRISON.getLocation());
					break;
				}			
			}
			
			public static boolean isVolunteerPrisoner(){
				
				return true;
			}
			

			public static void sentToJail(Player p)
			{				
				if(p.getInventory().getName() == EventListener.cuffInv.getName()) p.closeInventory();
				deParalyze(p.getName());
				if(prisoner.contains(p.getName()))
				{
				} else {
					
					if(!hasHandschellenEffect(p))
						addHandschellenEffect(p);
					
					if(isFleeingPrisoner(p.getName()))
						setFleeingPrisoner(p.getName(),false);
					
					if(p.hasPotionEffect(PotionEffectType.SLOW))
						p.removePotionEffect(PotionEffectType.SLOW);
			
					setRank(p.getName(),"Prisoner");
					setSpawnByRank(p.getName());
					Utility.clearWeapons(p);
					p.setExhaustion(0F);
					p.setHealth(p.getMaxHealth());
					prisoner.add(p.getName());
					Location loc = new Location(p.getWorld(),-31,38,-156);
					p.teleport(Locations.LOCATION_PRISON.getLocation()); // Location vom Jail einfügen
					
					int crime = SpielerProfil.getCrime(p.getName());
					if(crime > 100)
						SpielerProfil.setCrime(20, p.getName());
					
					if(crime <= 10){
						SpielerProfil.setCrime(10, p.getName());
					}
					
					Utility.sendTitle(p, ChatColor.RED + "Eingebuchtet!", "Deine Zeit beträgt " + ChatColor.YELLOW + SpielerProfil.getCrime(p.getName())/10 + " Minuten");
					SpielerProfil.setSpawn(p, Locations.LOCATION_PRISON.getLocation());
					p.sendMessage(ChatColor.RED + "Du wurdest ins Gefängnis befördert!");
					plugin.gui.updateOf(p);
				}
			}
			
			public static void setPrisoner(Player p){
				if(!prisoner.contains(p.getName())){
					prisoner.add(p.getName());
					SpielerProfil.setRank(p.getName(), "Prisoner");
					if(SpielerProfil.getCrime(p.getName()) >= 100)
						SpielerProfil.setCrime(100, p.getName());
					SpielerProfil.setSpawn(p, Locations.LOCATION_PRISON.getLocation());
					plugin.gui.updateOf(p);
					if(!p.getScoreboardTags().isEmpty()){
						if(!p.getScoreboardTags().contains("handschellenEffect")){
							p.addScoreboardTag("handschellenEffect");
						}
					}
					if(isFleeingPrisoner(p.getName()))
						setFleeingPrisoner(p.getName(),false);
					
					if(p.hasPotionEffect(PotionEffectType.SLOW))
						p.removePotionEffect(PotionEffectType.SLOW);
					
					if(SpielerProfil.getCrime(p.getName()) == 0){
						if(SpielerProfil.prisoner.contains(p.getName())){
							ItemStack is = CustomItems.getCustomItem(CustomItemType.FREIBRIEF);
							if(!p.getInventory().contains(is)){
								p.getInventory().addItem(is);					
								p.sendMessage(ChatColor.GRAY + "Du hast deine Strafe abgesessen.");		
							}
						}
					}
				}
			}
			
			public static void setHandschellen(String name,boolean bool){
				if(bool){
					if(!playerHandschellen.contains(name))
						playerHandschellen.add(name);
				} else {
					if(playerHandschellen.contains(name))
						playerHandschellen.remove(name);
				}
			}
			
			public static boolean hasHandschellenEffect(Player p){
				try{
				if(p.getScoreboardTags().isEmpty())
					return false;
				if(p.getScoreboardTags().contains("handschellenEffect")){
					return true;
				} else {
					return false;
				}
				} catch(NullPointerException e){
					return false;
				}
			}
			
			public static void addHandschellenEffect(Player p){
				Set<String> tags = p.getScoreboardTags();
				if(!tags.contains("handschellenEffect")){
					p.addScoreboardTag("handschellenEffect");
				}
			}
			
			public static void removeHandschellenEffect(Player p){
				Set<String> tags = p.getScoreboardTags();
				if(tags.contains("handschellenEffect")){
					p.removeScoreboardTag("handschellenEffect");
				}
			}
			
			public static void addMoney(Player p, double amount){
				plugin.economy.depositPlayer(p, amount);
				plugin.gui.updateOf(p);
			}
			
			public static void decreaseMoney(Player p, double amount){
				plugin.economy.withdrawPlayer(p, amount);
				plugin.gui.updateOf(p);
			}
			
			public static double getMoney(Player p){
				return plugin.economy.getBalance(p);
			}
			
			public static void freeFromJail(Player p){
				if(prisoner.contains(p.getName())){
					prisoner.remove(p.getName());
					if(hasHandschellenEffect(p))
						p.removeScoreboardTag("handschellenEffect");
					SpielerProfil.setRank(p.getName(), "Citizen");
					p.teleport(Locations.LOCATION_RATHAUS.getLocation()); // Location vom Rathaus einfügen
					SpielerProfil.setSpawn(p, Locations.LOCATION_PRISON_DOOR.getLocation());
				} else {
					p.sendMessage(ChatColor.RED + "Spieler ist nicht im Jail!");
				}
			}
			
			
			private static void onCrime(Player p){
				if(SpielerProfil.getCrime(p.getName()) <= 0){
					clearRegister(p.getName());
				}
				if(SpielerProfil.getCrime(p.getName()) > 0){
					EventListener.addCrimeTick(p.getName());
				}
				plugin.gui.updateOf(p);
			}
/*     */ 
			public static void clearRegister(String name){
				String pathRegister = "MyViCrime.SpielerProfile." + name + ".Strafregister";
				plugin.getConfig().set(pathRegister, new ArrayList<String>());
			}
			
/*     */   public static void setCrime(Integer zahl, String name)
/*     */   {
	String pathCrime = "MyViCrime.SpielerProfile."+ name+".Crime";	
/* 117 */     plugin.getConfig().set(pathCrime, zahl);
/* 118 */     plugin.saveConfig();
			SpielerProfil.onCrime(Bukkit.getPlayer(name));
/*     */   }
/*     */ 
/*     */   public static void addLevel(String name, int zahl)
/*     */   {
	
/* 145 */     int currentLevel = getLevel(name);
/* 146 */     int newLevel = currentLevel + zahl;
/*     */ 
/* 148 */     plugin.getConfig().set("MyViCrime.SpielerProfile." + name + ".Level", Integer.valueOf(newLevel));
/*     */   }

            public static void paralyze(String name){
            	if(!SpielerProfil.isParalyzed(name)){
            		Player p = Bukkit.getPlayer(name);
            		p.setSprinting(false);
            		p.setWalkSpeed(0F);
            		p.setSneaking(true);
            		p.setCustomName(ChatColor.RED + "Cuffed " + ChatColor.WHITE + p.getName());
            		p.sendMessage(ChatColor.RED + "Dir wurden Handschellen angelegt!"); 
            		p.setCustomNameVisible(true);
            		playerCuffed.add(name);       
            	}
            }
            
            public static void attemptToSentToJailIn(final String name,Long time){
            	attemptingJail.add(name);
            	new BukkitRunnable(){
            		@Override
            		public void run(){
            			if(SpielerProfil.isParalyzed(name)){
            				SpielerProfil.sentToJail(Bukkit.getPlayer(name));
            			}           			
            			attemptingJail.remove(name);  			
            		}
            	}.runTaskLater(plugin, time);
            }          
            
            public static boolean isAttemptingToEnterJail(String name){
            	if(attemptingJail.contains(name)){
            		return true;
            	} else {
            		return false;
            	}
            }
            
            public static void deParalyze(String name){
            	Player p = Bukkit.getPlayer(name);
            	if(SpielerProfil.isParalyzed(name)){
            	if(p.getInventory().getName() == EventListener.cuffInv.getName()) p.closeInventory();
            	p.setWalkSpeed(0.2F);
            	p.setSneaking(false);
            	p.setCustomNameVisible(false);
            	p.sendMessage(ChatColor.GREEN + "Dir wurden die Handschellen abgenommen!");
            	playerCuffed.remove(name);
            	}
            }
            
            public static void setLoreOfItem(ItemStack is, String text){
            	ItemMeta im = is.getItemMeta();
            	List<String> list = new ArrayList<String>();
            	list.add(text);
            	im.setLore(list);
            	is.setItemMeta(im);
            }
            
            public static void loadWarpsFromConfig(Player p){
            	String name = p.getName();
            	final String PATH = "Warps." + name;
            	
            	FileConfiguration config = main.plugin.getConfig();
            	if(config.contains(PATH + ".Locations") && config.contains(PATH + ".Names")){
            		List<String> locationStringList = config.getStringList(PATH + ".Locations");
            		List<String> nameStringList = config.getStringList(PATH + ".Names");
            		if(!locationStringList.isEmpty()){
            			for(int i = 0; i < locationStringList.size(); i++){
            				setWarpWithoutMessages(p,nameStringList.get(i),Utility.deserializeLocation(locationStringList.get(i)));
            			}
            		}
            	}
            }
            
            public static void saveWarpToConfig(Player p, String warpName, Location loc){
            	final String pathLocation = "Warps." + p.getName() + ".Locations";
            	final String pathName = "Warps." + p.getName() + ".Names";
            	FileConfiguration config = main.plugin.getConfig();
            	if(config.contains(pathLocation) && config.contains(pathName)){
            		ArrayList<String> warpNameList = (ArrayList<String>) config.getStringList(pathName);
            		ArrayList<String> locationStringList = (ArrayList<String>) config.getStringList(pathLocation);
            		
            		if(warpNameList.contains(warpName)){
            			int index = warpNameList.indexOf(warpName);
            			if(locationStringList.size() > index){
            				locationStringList.remove(index);
            				warpNameList.remove(index);
            			}
            		}
            		
            		warpNameList.add(warpName);
            		locationStringList.add(Utility.serializeLocation(loc));
            		config.set(pathName, warpNameList);
            		config.set(pathLocation, locationStringList);
            	} else {
            		//initialize config section
            		config.createSection(pathLocation);
            		config.createSection(pathName);
            		List<String> emptyList = new ArrayList<String>();
            		config.set(pathLocation, emptyList);
            		config.set(pathName, emptyList);
            	}            	
            }
            
            public static void removeWarpFromConfig(Player p, String warpName, Location loc){
            	final String pathLocation = "Warps." + p.getName() + ".Locations";
            	final String pathName = "Warps." + p.getName() + ".Names";
            	FileConfiguration config = main.plugin.getConfig();
            	if(config.contains(pathLocation) && config.contains(pathName)){
            		ArrayList<String> warpNameList = (ArrayList<String>) config.getStringList(pathName);
            		ArrayList<String> locationStringList = (ArrayList<String>) config.getStringList(pathLocation);
            		if(warpNameList.contains(warpName)) warpNameList.remove(warpName);
            		if(locationStringList.contains(Utility.serializeLocation(loc))) locationStringList.remove(Utility.serializeLocation(loc));
            		config.set(pathName, warpNameList);
            		config.set(pathLocation, locationStringList);
            	} else {
            		//initialize config section
            		config.createSection(pathLocation);
            		config.createSection(pathName);
            		List<String> emptyList = new ArrayList<String>();
            		config.set(pathLocation, emptyList);
            		config.set(pathName, emptyList);
            	}  
            }
            
            private static void setWarpWithoutMessages(Player p,String warpName,Location loc){
            	String name = p.getName();
            	if(playerWarpsMapMap.containsKey(name)){
            		HashMap<String, Location> warpNameLocationMap = playerWarpsMapMap.get(name);
            		if(warpNameLocationMap.size() < maxWarpAmount){
            			if(warpNameLocationMap.containsKey(warpName)){
            				warpNameLocationMap.put(warpName, loc); // There is already a warp with this name
            														// So just re set it!
            				playerWarpsMapMap.put(name, warpNameLocationMap);
            				saveWarpToConfig(p,warpName,loc);
            			} else {
            				warpNameLocationMap.put(warpName, loc);
            				saveWarpToConfig(p,warpName,loc);
            				playerWarpsMapMap.put(name, warpNameLocationMap);
            			}
            		} else {
            		}
            	} else {
            		HashMap<String, Location> warpNameLocationMap = new HashMap<String, Location>();
            		warpNameLocationMap.put(warpName,loc);
            		playerWarpsMapMap.put(name, warpNameLocationMap);
            		saveWarpToConfig(p,warpName,loc);
            	}
            }
            
            public static void setWarp(Player p,String warpName,Location loc){
            	String name = p.getName();
            	if(playerWarpsMapMap.containsKey(name)){
            		HashMap<String, Location> warpNameLocationMap = playerWarpsMapMap.get(name);
            		if(warpNameLocationMap.size() < maxWarpAmount){
            			if(warpNameLocationMap.containsKey(warpName)){
            				warpNameLocationMap.put(warpName, loc); // There is already a warp with this name
            														// So just re set it!
            				playerWarpsMapMap.put(name, warpNameLocationMap);
            				saveWarpToConfig(p,warpName,loc);
            				Msg.INFO.out(p, warpName + " re set!");
            			} else {
            				Msg.INFO.out(p, warpName + " set!");
            				warpNameLocationMap.put(warpName, loc);
            				saveWarpToConfig(p,warpName,loc);
            				playerWarpsMapMap.put(name, warpNameLocationMap);
            			}
            		} else {
            			Msg.ALERT.out(p, "All warps already set, remove one!");
            		}
            	} else {
            		HashMap<String, Location> warpNameLocationMap = new HashMap<String, Location>();
            		warpNameLocationMap.put(warpName,loc);
            		playerWarpsMapMap.put(name, warpNameLocationMap);
            		Msg.INFO.out(p, warpName + " set!");
            		saveWarpToConfig(p,warpName,loc);
            	}
            }
            
            public static Location getWarp(Player p, String warpName){
            	String name = p.getName();
            	if(playerWarpsMapMap.containsKey(name)){
            		HashMap<String,Location> warpNameLocationMap = playerWarpsMapMap.get(name);
            		if(warpNameLocationMap.containsKey(warpName)){
            			return warpNameLocationMap.get(warpName);
            		} else {
            			Msg.ALERT.out(p, "No warp [" + warpName + "] found!");
            		}
            	} else {
            		Msg.ALERT.out(p, "No warp [" + warpName + "] found!");
            	}
            	return null;
            }      
            
            public static void removeWarp(Player p, String warpName, Location loc){
            	String name = p.getName();
            	if(playerWarpsMapMap.containsKey(name)){
            		HashMap<String,Location> warpNameLocationMap = new HashMap<String,Location>();
            		if(warpNameLocationMap.containsKey(warpName)){
            			warpNameLocationMap.remove(warpName);
            			playerWarpsMapMap.put(name, warpNameLocationMap);
            			Msg.INFO.out(p, "Warp removed!");
            			removeWarpFromConfig(p,warpName,loc);
            		} else {
            			Msg.ALERT.out(p, "No warp with this name found!");
            		}
            	} else {
            		Msg.ALERT.out(p, "No warp with this name found!");
            	}
            }
            
            public static void stopZombieAggressionFor(final String name, long time){
            	SpielerProfil.protectedPlayer.add(name);
            	
            	new BukkitRunnable(){
            		@Override
            		public void run(){
            			if(SpielerProfil.protectedPlayer.contains(name))
            				SpielerProfil.protectedPlayer.remove(name);
            		}
            	}.runTaskLater(plugin, time);
            	for(Entity l : Bukkit.getPlayer(name).getNearbyEntities(10.0, 10.0, 10.0)){
            		if(l.getName().equalsIgnoreCase("Cop")){
            			LivingEntity living = (LivingEntity) l;
            			if(living instanceof Zombie){
                			Zombie eZ = (Zombie) living;
                			eZ.setTarget(null);
                			if(eZ.getLastDamage() != 0 || eZ.getLastDamage() != 0.0){
                				if(eZ.getLastDamageCause() != null){
                					eZ.setLastDamageCause(new EntityDamageEvent(null, null, 0.0));
                				}
                			}
            			}
            			if(living instanceof EntityZombie){
            				EntityZombie z = (EntityZombie) living;
            				z.setGoalTarget(null);
            				z.setGoalTarget(null, null, false);
            			}
            		}
            	}
            }
            
            public static boolean isParalyzed(String name){
            	if(playerCuffed.contains(name)){
            		return true;
            	} else {
            		return false;
            	}
            }
/*     */ 
/*     */   public static int getLevel(String name)
/*     */   {
/* 154 */     int currentLevel = ((Integer)plugin.getConfig().get("MyViCrime.SpielerProfil." + name + ".Level")).intValue();
/*     */ 
/* 156 */     return currentLevel;
/*     */   }

			public static void setLastJoinDay(String name){
				String path = "MyViCrime.SpielerProfile." + name + ".LastJoin";
				Calendar calendar = Calendar.getInstance();
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				plugin.getConfig().set(path, day);
			}
			
			public static int getLastJoinDay(String name){
				String path = "MyViCrime.SpielerProfile." + name + ".LastJoin";
				if(plugin.getConfig().contains(path)){
				int day = plugin.getConfig().getInt(path);
					return day;
				} else {
					return 0;
				}
			}
			
			public static Inventory getDailyRewardInventory(String name){
				Player p = Bukkit.getPlayer(name);
				InventoryHolder owner = p;
				Inventory inv = Bukkit.createInventory(owner, 10, "DAILY REWARD");
				inv.addItem(CustomItems.getCustomItem(CustomItemType.DAILYCHEST));
				return inv;
			}
			
			public static void setDailyReward(String name ,boolean bool){
				if(bool){
					dailyReward.add(name);
				} else {
					if(dailyReward.contains(name))
						dailyReward.remove(name);
				}
			}
			
			public static boolean hasDailyReward(String name){
				return dailyReward.contains(name);
			}
			
			public static boolean hasClickedDailyChest(String name, Block block){
				if(block.getType() == Material.ENDER_CHEST){
					if(block.getLocation() == Locations.LOCATION_TEST_CHEST.getLocation()){
						return true;
					}
				}
				return false;
			}
			
			public static void giveRewardItem(Player p, ItemStack item, int amount){
				if(Utility.getFreeSlotsOfInventory(p) < 1){
					p.sendMessage(ChatColor.GRAY + "Nicht genügend Platz im Inventar!");
					return;
				}
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "[+]" + ChatColor.WHITE + " " + item.getType().toString() + " x " + ChatColor.YELLOW + amount);
				Inventory inv = p.getInventory();
				for(int i = 0; i < amount; i++){
					inv.addItem(item);
				}
			}
			
			
			  public static boolean isWeapon(ItemStack item) 
	          {            	
	            	if(item == null)
	            		return false;	            	
	            	
	            	Material hand = item.getType();
	            	
	            	if((hand == Material.WOOD_AXE) || (hand == Material.IRON_AXE) || (hand == Material.GOLD_AXE) || (hand == Material.DIAMOND_AXE) || (hand == Material.STONE_AXE) || (hand == Material.WOOD_SWORD) || (hand == Material.STONE_SWORD) || (hand == Material.IRON_SWORD) || (hand == Material.GOLD_SWORD) || (hand == Material.DIAMOND_SWORD)) 
	            	{
	            		return true;
	            	} else {
	                	return false;
	            	}           
	         }
			
            public static boolean weaponInHand(String name) 
            {
            	
            	Player p = Bukkit.getPlayer(name);
            	if(p == null)
            		return false;
            	
            	if(p.hasMetadata("NPC"))
            		return false;
            	
            	if(p.getItemInHand() == null)
            		return false;
            	
            	Material hand = p.getInventory().getItemInHand().getType();
            	
            	if((hand == Material.WOOD_AXE) || (hand == Material.IRON_AXE) || (hand == Material.GOLD_AXE) || (hand == Material.DIAMOND_AXE) || (hand == Material.STONE_AXE) || (hand == Material.WOOD_SWORD) || (hand == Material.STONE_SWORD) || (hand == Material.IRON_SWORD) || (hand == Material.GOLD_SWORD) || (hand == Material.DIAMOND_SWORD)) 
            	{
            		return true;
            	} else {
                	return false;
            	}            	
            }
/*     */ }

/* Location:           C:\Users\HAPPYMAJOR\Desktop\jd-gui-0.3.5.windows\MyViCrime.jar
 * Qualified Name:     main.SpielerProfil
 * JD-Core Version:    0.6.2
 */