package myvcrime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.PotionType;

import com.gmail.filoghost.holograms.api.FloatingItem;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.sk89q.worldguard.internal.flywaydb.core.internal.util.StringUtils;

import myvcrime.chat.Msg;
import myvcrime.customEvents.PlayerBoughtEvent;
import myvcrime.customEvents.PlayerSoldEvent;
import myvcrime.quests.ConsumableQuestObjective;
import myvcrime.quests.QuestType;
import myvcrime.quests.SellQuestObjective;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class ShopBuilder {
	
	Plugin plugin;
	
	ShopBuilder(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void placeShop(Location loc, ItemStack is, Double price, String type){
		loc.add(0, 1, 0).getBlock().setTypeId(44);
		placeShopHologram(loc, is, price, type,"");
		saveShop(loc,is,price,type,"");
	}
	
	public void placeShop(Location loc, ItemStack is, Double price, String type, String customName){
		loc.add(0,1,0).getBlock().setTypeId(44);
		placeShopHologram(loc,is,price,type, customName);
		saveShop(loc,is,price,type,customName);
	}
		
	public void saveShop(Location loc, ItemStack is, Double price, String type, String customName){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		Bukkit.broadcastMessage(serializedLoc);
		String pathItem = "shops." +serializedLoc + ".Item";
		String pathAmount = "shops." +serializedLoc + ".Amount";
		String pathPrice = "shops." + serializedLoc + ".Price";
		String pathType = "shops." + serializedLoc + ".Type";
		String pathHologram = "shops." + serializedLoc + ".Holo";
		String pathFloat = "shops." + serializedLoc + ".FloatingItem";
		String pathName = "shops." + serializedLoc + ".customName";
		if(plugin.getConfig().contains("shops." + serializedLoc + ".Item")){
			Bukkit.broadcastMessage("Shop ist schon an dieser Stelle erstellt");
		} else {
			Hologram holo = main.hologramList.get(Utility.serializeLocation(loc));
			FloatingItem item = main.floatingItemList.get(Utility.serializeLocation(loc));
			String holoLoc = Utility.serializeLocation(holo.getLocation());
			String itemLoc = Utility.serializeLocation(item.getLocation());
			
			//Support for Potions and their special value
			if(is.getType() == Material.POTION){
				plugin.getConfig().set(pathItem, is.getType().toString() + "#" + is.getDurability());
			} else {
				plugin.getConfig().set(pathItem, is.getType().toString());
			}
			plugin.getConfig().set(pathPrice, price);
			plugin.getConfig().set(pathType, type);
			plugin.getConfig().set(pathAmount, is.getAmount());
			plugin.getConfig().set(pathHologram, holoLoc);
			plugin.getConfig().set(pathFloat, itemLoc);
			plugin.getConfig().set(pathName, customName);
			plugin.saveConfig();
			Bukkit.broadcastMessage("Shop erstellt");
		}
	}
	
	public void loadShops(){
		if(!plugin.getConfig().contains("shops")) return;
		for(String s : plugin.getConfig().getConfigurationSection("shops").getKeys(false)){
			String pathHologram = "shops." + s + ".Holo";
			String pathFloat = "shops." + s + ".FloatingItem";
			String pathItem = "shops." +s + ".Item";
			String pathAmount = "shops." +s + ".Amount";
			String pathPrice = "shops." + s + ".Price";
			String pathType = "shops." + s + ".Type";
			String pathName = "shops." + s + ".customName";
			String stringMaterial = (String) this.plugin.getConfig().get(pathItem);
			Material mat;
			short durability = 0; //important value for potions
			if(stringMaterial.contains("#")){
				String[] splittedString = stringMaterial.split("#");
				mat = Material.getMaterial(splittedString[0]);
				if(StringUtils.isNumeric(splittedString[1])){
					durability = Short.parseShort(splittedString[1]);
				}
			} else {
				mat = Material.getMaterial((String)this.plugin.getConfig().get(pathItem));
			}
			String itemLoc = plugin.getConfig().getString(pathFloat);
			String holoLoc = plugin.getConfig().getString(pathHologram);
			String customName = "";
			if(plugin.getConfig().contains(pathName)){
				customName = plugin.getConfig().getString(pathName);
			} else {
				plugin.getConfig().set(pathName, "");
			}
			Double price = plugin.getConfig().getDouble(pathPrice);
			int amount = plugin.getConfig().getInt(pathAmount);
			String type = plugin.getConfig().getString(pathType);
			ItemStack is;
			if(durability != 0){
				is = new ItemStack(mat,amount,durability);
			} else {
				is = new ItemStack(mat,amount);
			}
			Location locItem = Utility.deserializeLocation(itemLoc);
			Location locHolo = Utility.deserializeLocation(holoLoc);
			this.createShopItem(locItem, is);
			this.createShopHologram(locHolo, is, price, type,customName);
		}
	}
	
	public void placeShopHologram(Location loc, ItemStack is, Double price, String type, String customName){
		Location sLoc = loc;
		Location center = Utility.getCenter(sLoc);
		//old value 0,1.5,0
		Hologram holo = this.createShopHologram(center.add(0, 1.4, 0), is, price, type, customName);
		FloatingItem item = this.createShopItem(center.add(0, -0.8, 0),is);
		main.hologramList.put(Utility.serializeLocation(loc),holo);
		main.floatingItemList.put(Utility.serializeLocation(loc), item);
	}
	
	public void removeShopHologram(Location loc){
		if(main.floatingItemList.containsKey(loc)){
		main.floatingItemList.remove(Utility.serializeLocation(loc));
		main.floatingItemList.get(loc).hide();
		main.floatingItemList.get(loc).delete();
		main.hologramList.remove(Utility.serializeLocation(loc));
		main.hologramList.get(loc).clearLines();
		main.hologramList.get(loc).delete();
		} else {
			String serializedLoc = Utility.serializeLocationForPath(loc);
			if(plugin.getConfig().contains("shops." + serializedLoc)){
				String pathHologram = "shops." + serializedLoc + ".Holo";
				String pathFloat = "shops." + serializedLoc + ".FloatingItem";
				String stringLoc = (String) plugin.getConfig().get(pathHologram);
				String stringFloatLoc = plugin.getConfig().getString(pathFloat);
				Location holoLoc = Utility.deserializeLocation(stringLoc);
				Location floatLoc = Utility.deserializeLocation(stringFloatLoc);
				for(com.gmail.filoghost.holograms.api.Hologram h : main.api.getHolograms(plugin)){
					if(h.getLocation() == holoLoc)
						h.clearLines();
						h.delete();
						h.hide();
				}
				for(FloatingItem f : main.api.getFloatingItems(plugin)){
					if(f.getLocation() == floatLoc)
						f.delete();
						f.hide();
				}
			} else {
				
			}
			
		}
	}  
	
	public void removeShop(Location loc){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		String pathItem = "shops." +serializedLoc + ".Item";
		String pathAmount = "shops." +serializedLoc + ".Amount";
		String pathPrice = "shops." + serializedLoc + ".Price";
		String pathType = "shops." + serializedLoc + ".Type";
		String pathHologram = "shops." + serializedLoc + ".Holo";
		String pathFloat = "shops." + serializedLoc + ".FloatingItem";
		String pathName = "shops." + serializedLoc + ".customName";
		if(plugin.getConfig().contains(pathItem)){
			removeShopHologram(loc);
			plugin.getConfig().set(pathItem, null);
			plugin.getConfig().set(pathPrice, null);
			plugin.getConfig().set(pathType, null);
			plugin.getConfig().set(pathHologram, null);
			plugin.getConfig().set(pathFloat, null);
			plugin.getConfig().set(pathName, null);
			plugin.getConfig().set(pathAmount, null);
			plugin.getConfig().set("shops." + serializedLoc, null);
			plugin.saveConfig();
			plugin.reloadConfig();
			this.loadShops();

			Bukkit.broadcastMessage("Shop entfernst losl ");
		} else {
			Bukkit.broadcastMessage("Hier existisssssert kein shop");
		}
	}
	
	public FloatingItem createShopItem(Location loc, ItemStack is){
		FloatingItem item = main.api.createFloatingItem(plugin, loc, is);
		return item;
	}
	
	public Hologram createShopHologram(Location loc, ItemStack is, Double price, String type, String customName){
		ChatColor redOrGreen = ChatColor.RED;
		String name = is.getType().toString();
		if(type.equalsIgnoreCase("sell")) redOrGreen = ChatColor.GREEN;  
		if(customName != "") name = customName;
		Hologram holo;
		if(Utility.checkRemainder(price)){
			holo = (Hologram) main.api.createHologram(plugin, loc.add(0, 0, 0),redOrGreen + "" + ChatColor.BOLD +"[" + type + "]", ChatColor.GOLD + "" + ChatColor.BOLD  + name ,ChatColor.BOLD +""+ is.getAmount()+"",ChatColor.BOLD + "" + price * is.getAmount() + "$");
		} else {
			holo = (Hologram) main.api.createHologram(plugin, loc.add(0, 0, 0),redOrGreen + "" + ChatColor.BOLD +"[" + type + "]", ChatColor.GOLD + "" + ChatColor.BOLD  + name ,ChatColor.BOLD +""+ is.getAmount()+"",ChatColor.BOLD + "" + price.intValue() * is.getAmount() + "$");
		}
		return holo;
	}
	
	public void onRightClickShop(Player p, Location loc){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		String pathType = "shops." + serializedLoc + ".Type";
		
			String type = plugin.getConfig().getString(pathType);
			if(!type.equalsIgnoreCase("sell")){
				this.onRightClick(p, loc);
			} else {
				this.onLeftClick(p, loc);
			}
	}
	
	public Boolean isShop(Player p, Location loc){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		String pathItem = "shops." +  serializedLoc + ".Item";
		if(plugin.getConfig().contains(pathItem)){
			return true;
		} else {
			return false;
		}
	}
	
	public void onLeftClick(Player p, Location loc){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		String pathItem = "shops." +  serializedLoc + ".Item";
		String pathPrice = "shops." +  serializedLoc + ".Price";
		String pathType = "shops." + serializedLoc + ".Type";
		String pathAmount = "shops." + serializedLoc + ".Amount";
		String pathName = "shops." + serializedLoc + ".customName";
		
		if(plugin.getConfig().contains(pathItem)){
			String type = plugin.getConfig().getString(pathType);
			if(!type.equalsIgnoreCase("sell")){
				p.sendMessage(ChatColor.RED + "Dieser kauft nichts nicht!");
			} else {
				
				Inventory inv = p.getInventory();
				Double amount = new Double(plugin.getConfig().getInt(pathAmount));
				String matString = (String) plugin.getConfig().get(pathItem);
				String customName = (String) plugin.getConfig().get(pathName);
				short durability = 0;
				if(matString.contains("#")){
					String[] splitted = matString.split("#");
					matString = splitted[0];
					if(StringUtils.isNumeric(splitted[1])){
						durability = Short.parseShort(splitted[1]);
					}
				}
				Double price = plugin.getConfig().getDouble(pathPrice);
				Material mat = Material.matchMaterial(matString);
				ItemStack potion = null;
				if(mat == Material.POTION){
					potion = new ItemStack(Material.POTION,amount.intValue(),durability);
				}
				
				if(p.isSneaking()){
					int counter = 0;
					int countedItems = Utility.countItems(mat, inv);
					ItemStack[] items = inv.getContents();
					for(ItemStack item : items){
						if(item != null){
							if(item.getType() == mat){
								if(mat == Material.GOLD_NUGGET || mat == Material.DIAMOND || mat == Material.GLASS_BOTTLE){
									inv.removeItem(new ItemStack[]{item});
									counter+=item.getAmount();
									SpielerProfil.addMoney(p, item.getAmount() * price);
									plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
									continue;
								}
								if(item.getType() == Material.POTION){
									inv.removeItem(item);							
									SpielerProfil.addMoney(p, item.getAmount() * price);
									counter+=item.getAmount();
									plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
								} else {
									ItemStack removeItemkl = new ItemStack(item.getType(),item.getAmount());
									if(item.hasItemMeta()){
										if(item.getItemMeta().getDisplayName().equalsIgnoreCase(customName)){
											removeItemkl.setItemMeta(item.getItemMeta());
											inv.removeItem(new ItemStack[]{removeItemkl});
											SpielerProfil.addMoney(p, item.getAmount() * price);
											counter+=item.getAmount();
											plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
										}
									} else {
										if(item.hasItemMeta()){
											continue;
										}
										if(customName == ""){
											counter+=item.getAmount();
											inv.removeItem(new ItemStack[]{removeItemkl});
											SpielerProfil.addMoney(p, item.getAmount() * price);
											plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
										}
									}				
								}			
							}
						}
					}
					
					p.sendMessage(ChatColor.BLUE + "[!] " + ChatColor.DARK_GREEN + counter + " " + ChatColor.YELLOW + mat.toString() + ChatColor.DARK_GREEN + " for " + ChatColor.YELLOW + counter * price + "$"+ ChatColor.DARK_GREEN +  " sold!");
				} else {
					ItemStack removeItem = null;
					
					if(potion != null){
						Potion potionObject = Potion.fromItemStack(potion);
						removeItem = potionObject.toItemStack(amount.intValue());
					} else {
						ItemStack is = new ItemStack(mat,amount.intValue());
						
						if(customName != "" && mat != Material.GOLD_NUGGET && mat != Material.DIAMOND && mat != Material.GLASS_BOTTLE){
							ItemMeta meta = is.getItemMeta();
							meta.setDisplayName(customName);
							is.setItemMeta(meta);
						}						
						removeItem = is;
					}
					if(removeItem != null && inv.containsAtLeast(removeItem, amount.intValue())){
						
						
						
						//QUESTS
						main Main = (main) plugin;
						plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,mat,amount.intValue()));
						
						ItemStack[] isArry = new ItemStack[]{removeItem};
						inv.removeItem(isArry);
						
						SpielerProfil.addMoney(p, amount * price);
						
						p.sendMessage(ChatColor.BLUE + "[!] " + ChatColor.DARK_GREEN + amount.intValue() + " " + ChatColor.YELLOW + mat.toString() + ChatColor.DARK_GREEN + " for " + ChatColor.YELLOW + price * amount.intValue() +"$"+ ChatColor.DARK_GREEN +" sold");
					} else {
						if(removeItem != null && inv.containsAtLeast(removeItem, 1)){
							int counter = 0;
							int countedItems = Utility.countItems(mat, inv);
							ItemStack[] items = inv.getContents();
							for(ItemStack item : items){
								if(item != null){
									if(item.getType() == mat){
										if(item.getType() == Material.POTION){
											inv.removeItem(item);
											SpielerProfil.addMoney(p, item.getAmount() * price);
											counter+=item.getAmount();
											plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
										} else {
											ItemStack removeItemkl = new ItemStack(item.getType(),item.getAmount());
											if(item.hasItemMeta()){
												if(item.getItemMeta().getDisplayName().equalsIgnoreCase(customName)){
													removeItemkl.setItemMeta(item.getItemMeta());
													inv.removeItem(new ItemStack[]{removeItemkl});
													SpielerProfil.addMoney(p, item.getAmount() * price);
													counter+=item.getAmount();
													plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
												}
											} else {
												if(item.hasItemMeta()){
													continue;
												}
												if(customName == ""){
													counter+=item.getAmount();
													inv.removeItem(new ItemStack[]{removeItemkl});
													SpielerProfil.addMoney(p, item.getAmount() * price);
													plugin.getServer().getPluginManager().callEvent(new PlayerSoldEvent(p,item.getType(),item.getAmount()));
												}
											}				
										}			
									}
								}
							}
							
							p.sendMessage(ChatColor.BLUE + "[!] " + ChatColor.DARK_GREEN + counter + " " + ChatColor.YELLOW + mat.toString() + ChatColor.DARK_GREEN + " for " + ChatColor.YELLOW + counter * price + "$"+ ChatColor.DARK_GREEN +  " sold!");
						} else {
							p.sendMessage(ChatColor.RED + "Required items not found!");
						}
					}
				}
			}
		}
	}
	
	public void onRightClick(Player p, Location loc){
		String serializedLoc = Utility.serializeLocationForPath(loc);
		String pathItem = "shops." +  serializedLoc + ".Item";
		String pathPrice = "shops." +  serializedLoc + ".Price";
		String pathType = "shops." + serializedLoc + ".Type";
		String pathAmount = "shops." + serializedLoc + ".Amount";
		String pathName = "shops." + serializedLoc + ".customName";
		if(plugin.getConfig().contains(pathItem)){
			String type = plugin.getConfig().getString(pathType);
			if(!type.equalsIgnoreCase("buy")){
				p.sendMessage(ChatColor.RED + "Dieser verkauft nichts!");
			} else {
				ItemStack isArry = plugin.getConfig().getItemStack(pathItem);
				Inventory inv = p.getInventory();
				Double amount = (double) plugin.getConfig().getInt(pathAmount);
				Double price = plugin.getConfig().getDouble(pathPrice) * amount;
				if(main.economy.getBalance(p.getName()) < price){
					Msg.ALERT.out(p, "Not enough money!");
				} else {
				String matString = (String) plugin.getConfig().get(pathItem);
				Material mat;
				short durability = 0; // important for potions!
				if(matString.contains("#")){
					String[] splitted = matString.split("#");
					mat = Material.matchMaterial(splitted[0]);
					if(StringUtils.isNumeric(splitted[1])){
						durability = Short.parseShort(splitted[1]);
					}
				} else {
					mat = Material.matchMaterial(matString);
				}

				int freeSlots = Utility.getFreeSlotsOfInventory(p);
				String customName = plugin.getConfig().getString(pathName);
				if(freeSlots < 1){
					Msg.ALERT.out(p, "Not enough space!");
				} else {
					String name = customName == "" ? mat.toString() : customName;
					p.sendMessage(ChatColor.BLUE + "[!] " + ChatColor.DARK_GREEN + amount.intValue() + "X " + ChatColor.YELLOW + name + ChatColor.DARK_GREEN + " fuer " + ChatColor.YELLOW + "$" + price + ChatColor.DARK_GREEN +" gekauft");
					SpielerProfil.decreaseMoney(p, price);
					if(customName != ""){
					List<String> list = new ArrayList<String>();
					ItemStack item;
					if(mat == Material.POTION){
						Bukkit.broadcastMessage(mat.toString() + durability);
						item = new ItemStack(mat,amount.intValue(),durability);
					} else {
						item = new ItemBuilder(mat,customName,list).getItem();
					}
					item.setAmount(amount.intValue());
					enchantCustomArmor(item);
					plugin.getServer().getPluginManager().callEvent(new PlayerBoughtEvent(p,item,price));
					inv.addItem(item);
					} else {
						if(mat == Material.POTION){
							plugin.getServer().getPluginManager().callEvent(new PlayerBoughtEvent(p,new ItemStack(mat,amount.intValue()),price));
							inv.addItem(new ItemStack[]{new ItemStack(mat,amount.intValue(),durability)});
						} else {
							plugin.getServer().getPluginManager().callEvent(new PlayerBoughtEvent(p,new ItemStack(mat,amount.intValue()),price));
							inv.addItem(new ItemStack[]{new ItemStack(mat,amount.intValue())});
						}
					}
				}
				}
			}
		}
	}
	
	public void enchantCustomArmor(ItemStack item){
		if(item.getType() == Material.GOLD_HELMET){
			item.addEnchantment(Enchantment.OXYGEN, 1);
		}
		if(item.getType() == Material.BOW){
			item.addEnchantment(Enchantment.DAMAGE_UNDEAD, 7);
		}
		if(item.getType() == Material.GOLD_LEGGINGS){
		}
		if(item.getType() == Material.GOLD_BOOTS){
			item.addEnchantment(Enchantment.PROTECTION_FALL, 1);
		}
		if(item.getType() == Material.DIAMOND_HELMET){
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		}
		if(item.getType() == Material.DIAMOND_CHESTPLATE){
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		}
		if(item.getType() == Material.DIAMOND_BOOTS){
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		}
		if(item.getType() == Material.DIAMOND_LEGGINGS){
			item.addEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
		}
	}
}
