/*     */ package myvcrime.listener;
/*     */ import java.util.ArrayList;
import java.util.Calendar;
/*     */ import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftWolf;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftZombie;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.Horse;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
/*     */ import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityWolf;
import net.minecraft.server.v1_12_R1.EntityZombie;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;

import myvcrime.Cop;
import myvcrime.CustomConsumableType;
import myvcrime.CustomEntityWolf;
import myvcrime.CustomEntityZombie;
import myvcrime.CustomItemType;
import myvcrime.CustomItems;
import myvcrime.Prison;
import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.chat.Msg;
import myvcrime.customGUI.InventoryMenu;
import myvcrime.items.ItemBuilder;
import myvcrime.raid.RaidableShop;
import myvcrime.raid.WaveRaid;
/*     */ 
/*     */ @SuppressWarnings("deprecation")
public class EventListener
/*     */   implements Listener
/*     */ {
/*     */   public static main plugin;
/*  28 */   public boolean schleifeAktiv = false;
/*     */ 
/*  30 */   HashMap<String, Integer> warnung = new HashMap<String, Integer>();
/*  31 */   HashMap<String, Integer> crime = new HashMap<String, Integer>();
            public static HashMap<String, Integer> jailedPlayerTime = new HashMap<String, Integer>();
            public static ArrayList<String> jailedPlayerList = new ArrayList<String>();
            
            public static ArrayList<Location> playerAttackedCane = new ArrayList<Location>();
            public static ArrayList<String> deadPlayerList = new ArrayList<String>();
            public static HashMap<String, Location> deadPlayer = new HashMap<String, Location>();
            public static HashMap<String, Hologram> holograms = new HashMap<String, Hologram>();
            public static HashMap<String, Location> deadPlayerBlock = new HashMap<String, Location>();
            public static HashMap<String, Integer> deadPlayerCooldown = new HashMap<String, Integer>();
            public static HashMap<Location, String> deadPlayerKiller = new HashMap<Location, String>();
            public HashMap<String, Long> copkillerCooldown = new HashMap<String, Long>();
            
            public static ArrayList<String> crimeTick = new ArrayList<String>();
            public ArrayList<String> reedemCode = new ArrayList<String>();
/*  32 */   public ArrayList<Location> 	MoneyprinterListe = new ArrayList<Location>();
/*  33 */   public ArrayList<Location> BlockListe = new ArrayList<Location>();

/*     */   public int taskID;
/*  37 */   public String[] strafregister = { "null" };
            public boolean crimeSchleife = false;
            public boolean adminBoolean;
    		public static int highestCrime = 0;
    		public static Inventory compassInv = Bukkit.createInventory(null, 9, "MyVCrime");
            public static Inventory cuffInv = Bukkit.createInventory(null, 9, "DU WURDEST VERHAFTET!");
            public Prison PlayerEffectEventsClass = new Prison(plugin);
            
            static{
            ItemStack brick = new ItemStack(Material.BRICK,1);
            ItemMeta im = brick.getItemMeta();
            im.setDisplayName("Rathaus");
            brick.setItemMeta(im);
            ItemStack weapon = new ItemStack(Material.STONE_SWORD);
            im = weapon.getItemMeta();
            im.setDisplayName("Weaponshop");
            weapon.setItemMeta(im);
            
            ItemStack prison = new ItemStack(Material.STONE);
            im = prison.getItemMeta();
            im.setDisplayName("Prison");
            prison.setItemMeta(im);
            
            ItemStack mine = new ItemStack(Material.COAL_ORE);
            im = mine.getItemMeta();
            im.setDisplayName("Mine");
            mine.setItemMeta(im);
            
            ItemStack casino = new ItemStack(Material.GLOWSTONE);
            im = casino.getItemMeta();
            im.setDisplayName("Casino");
            casino.setItemMeta(im);
            
            compassInv.setItem(4,casino);
            compassInv.setItem(3, mine);
            compassInv.setItem(2, prison);
            compassInv.setItem(0, brick);
            compassInv.setItem(1, weapon);
            
            ItemStack greenWool = new ItemStack(Material.WOOL,1,(short) 5);
            greenWool.setData(new MaterialData(5));
            im = greenWool.getItemMeta();
            im.setDisplayName("Bestechen");
            greenWool.setItemMeta(im);
            
            ItemStack redWool = new ItemStack(Material.WOOL,1,(short) 14);
            im = redWool.getItemMeta();
            im.setDisplayName("Jail");
            redWool.setItemMeta(im);
            
            ItemStack blueWool = new ItemStack(Material.WOOL,1,(short) 11);
            blueWool.setData(new MaterialData(11));
            im = blueWool.getItemMeta();
            im.setDisplayName("Info");
            blueWool.setItemMeta(im);
            
            cuffInv.setItem(3, greenWool);
            cuffInv.setItem(4, redWool);
            cuffInv.setItem(0, blueWool);
            }

/*     */ 
/*     */   public EventListener(main instance)
/*     */   {
/*  25 */     plugin = instance;
/*     */   }

			@EventHandler
			public void onInventoryOpen(InventoryOpenEvent e){
				if(e.getInventory().getName() == EventListener.cuffInv.getName()){
					Inventory inventory = e.getInventory();
					ItemStack is = inventory.getItem(3);
					ItemMeta im = is.getItemMeta();
					Player p = (Player) e.getPlayer();
					List<String> list = new ArrayList<String>();
					int payment = SpielerProfil.getCrime(p.getName())/2 + 1;
					list.add("Kosten der Bestechung: " + payment);
					im.setLore(list);
					is.setItemMeta(im);
				}
				if(e.getInventory().getName().equalsIgnoreCase("container.enderchest")){
					Player p = (Player) e.getPlayer();
					String name = p.getName();
					if(SpielerProfil.hasDailyReward(name)){
						Inventory inv = e.getInventory();
						inv.addItem(CustomItems.getCustomItem(CustomItemType.DAILYCHEST));
						SpielerProfil.setDailyReward(name, false);
					}
				}
			}
			
			@EventHandler
			public void onInventoryClose(InventoryCloseEvent e){
				if(e.getInventory().getName() == EventListener.cuffInv.getName()){
					if(SpielerProfil.isParalyzed(e.getPlayer().getName())){
						final HumanEntity h = e.getPlayer();
						 Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							 public void run() {
							 h.openInventory(cuffInv);
							 }
						},25L);
					}
				}
			}
			


			@EventHandler
			public void onInventoryClick(InventoryClickEvent event) {	
				Player player = (Player) event.getWhoClicked(); // The player that clicked the item
				ItemStack clicked = event.getCurrentItem(); // The item that was clicked
				Inventory inventory = event.getInventory(); // The inventory that was clicked in
				if(inventory.getName().equals(compassInv.getName())) {
					if(clicked.getType() == Material.STONE_SWORD){
						this.updateCompass(player.getName(), SpielerProfil.Locations.LOCATION_GUNSHOP.getLocation());
					    event.setCancelled(true);
					}
					
					if(clicked.getType() == Material.BRICK){
						this.updateCompass(player.getName(), SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
						event.setCancelled(true);
					}
					
					if(clicked.getType() == Material.GOLD_ORE){
						this.updateCompass(player.getName(), player.getLocation());
						event.setCancelled(true);
					}
					
					if(clicked.getType() == Material.COAL_ORE){
						this.updateCompass(player.getName(), SpielerProfil.Locations.LOCATION_MINE_SHOP.getLocation());
						event.setCancelled(true);
					}
					
					if(clicked.getType() == Material.STONE){
						this.updateCompass(player.getName(), SpielerProfil.Locations.LOCATION_PRISON_DOOR.getLocation());
						event.setCancelled(true);
					}
					
					if(clicked.getType() == Material.GLOWSTONE){
						this.updateCompass(player.getName(), SpielerProfil.Locations.LOCATION_CASINO.getLocation());
						event.setCancelled(true);
					}
				}
				
				
				
				if(inventory.getName().equalsIgnoreCase(EventListener.cuffInv.getName())){
					event.setCancelled(true);
					
					//Make sure player is not already in prison
					if(SpielerProfil.prisoner.contains(player.getName())){
						player.closeInventory();
						return; // Player is already in prison so do nothing
					}
					
					if(clicked.getType() == Material.WOOL){											    						
						MaterialData matData = clicked.getData();
						byte colorData = matData.getData();
						
						if(colorData == DyeColor.LIME.getWoolData()){
							if(SpielerProfil.isParalyzed(player.getName())){
								if(SpielerProfil.getCrime(player.getName()) >= 0){
									int payment = SpielerProfil.getCrime(player.getName())/2 + 1;
									if(main.economy.getBalance(player) >= payment){
										Msg.INFO.out(player, ChatColor.GREEN + "Cop erfolgreich bestochen!");
										SpielerProfil.decreaseMoney(player, payment);
										if(SpielerProfil.isAttemptingToEnterJail(player.getName())){
											SpielerProfil.attemptingJail.remove(player.getName());
										}
										SpielerProfil.setCrime(0, player.getName());
										SpielerProfil.deParalyze(player.getName());
										player.closeInventory();
									} else {
										Msg.WARN.out(player,ChatColor.RED + "Not enough money!");
										SpielerProfil.setLoreOfItem(inventory.getItem(3), "Nicht genügend Geld!");
										player.updateInventory();
									}
								}
							}
						}
						
						if(colorData == DyeColor.BLUE.getWoolData()){
						}
						
						if(colorData == DyeColor.RED.getWoolData()){
							if(SpielerProfil.prisoner.contains(player.getName())){
								player.teleport(SpielerProfil.Locations.LOCATION_PRISON.getLocation());
							} else {
								SpielerProfil.sentToJail(player);
							}
						}
					}
				}
			}
			
			@EventHandler
			public void onTargetEntity(EntityTargetEvent e){
				if(e.getTarget() instanceof Player){
					Player p = (Player) e.getTarget();
					if(SpielerProfil.protectedPlayer.contains(p.getName())){
						e.setCancelled(true);
					}
					if(plugin.raidClass.isPlayerRobbing(p)){
						Entity entity = e.getEntity();
						try{
							if(entity.getScoreboardTags().contains("raid") == false){
								e.setCancelled(true);
							}
						} catch(NullPointerException exception){
							e.setCancelled(true);
						}
					}
				}
			}
			
			@EventHandler
			public void onBlockPlace(BlockPlaceEvent e){
				Block block = e.getBlockPlaced();
				
				if(block.getType() == Material.BREWING_STAND){
					plugin.hologramList.put(Utility.serializeLocation(block.getLocation().add(0, 2, 0)),(com.gmail.filoghost.holographicdisplays.api.Hologram) HolographicDisplaysAPI.createHologram(plugin, block.getLocation().add(0, 2, 0),ChatColor.GOLD + "Cooker" , ChatColor.GRAY + "Sneak + Drug to cook!"));
					Utility.addHologramToStartList(block.getLocation().add(0, 2, 0), "stands");
				}
				
				Player p = e.getPlayer();
				Inventory inv = p.getInventory();
				ItemStack is = p.getItemInHand();
				ItemMeta im = is.getItemMeta();
				if(is.hasItemMeta()){
					if(im.getDisplayName().equalsIgnoreCase("Building Block")){
						plugin.customBlocks.createTempBlock(e.getBlock().getLocation(), is.getType());
					}
				}
			}
			
			
			@EventHandler
			public void onGrow(BlockGrowEvent e){
				if(e.getBlock().getType() == Material.SUGAR_CANE_BLOCK){
					Bukkit.broadcastMessage(e.getNewState().toString());
				}
			}
			
/*     */   @EventHandler
/*     */   public void onInteractMoneyprinter(PlayerInteractEvent e)
/*     */   { 
/*  42 */     if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
/*     */     {
				
	/*  48 */   Player p = e.getPlayer();
/*  44 */       Block printer = e.getClickedBlock();
				if(plugin.shopBuilder.isShop(e.getPlayer(), printer.getLocation())){
					if(e.getHand() == EquipmentSlot.HAND){
						if(e.getHand() != EquipmentSlot.OFF_HAND)
							plugin.shopBuilder.onRightClickShop(e.getPlayer(), printer.getLocation());
					}
				}
				

                if (e.getHand() == EquipmentSlot.OFF_HAND) {
                    return; // off hand packet, ignore.
                }
/*     */     }

              if((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK)) 
              {
            	  final Player p = e.getPlayer();
            	  if(p.getItemInHand() != null){
            		  if(e.getHand() == EquipmentSlot.OFF_HAND){
            			  
            		  } else {     
                		  plugin.lootChest.openChest(p, p.getInventory().getItemInMainHand());  
            		  }
            	  }
            	  
            	  if(e.getClickedBlock() != null){
            		  Block clickedBlock = e.getClickedBlock();
            		  if(clickedBlock.getType() == Material.TNT){
            			  clickedBlock.setType(Material.AIR);
            			  p.getWorld().spawnEntity(clickedBlock.getLocation(), EntityType.PRIMED_TNT);
            		  }
            	  }
            	  
            	  if(p.getInventory().getItemInHand().getType() == Material.PAPER){
            		  ItemStack is = p.getInventory().getItemInHand();
            		  ItemMeta im = is.getItemMeta();
            		  String name = ChatColor.stripColor(im.getDisplayName());
            		  if(name.equalsIgnoreCase(CustomConsumableType.FREIBRIEF.getName())){
            			  if(SpielerProfil.prisoner.contains(p.getName())){
            				  p.getInventory().removeItem(new ItemStack[]{is});
            				  Utility.sendTitle(p, ChatColor.GREEN + "You got freed!", "Don´t mess up again ;)");
            				  Msg.INFO.out(p, "You got freed from prison!");
            				  SpielerProfil.setSpawn(p, SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
            				  SpielerProfil.freeFromJail(p);
            				  plugin.gui.updateOf(p);
            			  }
            		  }
            	  }
            	  
            	  if(p.getInventory().getItemInHand().getType() == Material.COMPASS) 
            	  {
            		  if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Crime Watch"))
            		  
            		  if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("GPS"))
            		  p.openInventory(compassInv);
            		  
            		  if(p.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Navi")){
            			  InventoryMenu inv = SpielerProfil.getCompassInv(p.getName());
            			  if(inv != null)
            			  {
            				  inv.open(p);
            			  }
            		  }
            	  }         	  
            	  
            	  if(p.getInventory().getItemInHand().getType() == Material.SUGAR || p.getInventory().getItemInHand().getType() == Material.INK_SACK)
            	  {
            		  if(e.getHand() == EquipmentSlot.OFF_HAND)
            			  return;
            		  
            		  ItemStack is = e.getPlayer().getInventory().getItemInMainHand();
            		  
            		  if(is.hasItemMeta() == false)
            			  return;
            		  
            		  ItemMeta im = is.getItemMeta();
            		  String disName = im.getDisplayName();
            		  boolean isValid = false;    		  
            		  int lvl = 0;
            		  switch(disName){
            		  	case "Speed I":
            		  		lvl = 1;
            		  		isValid = true;
            		  		break;
            		  	case "Speed II":
            		  		lvl = 2;
            		  		isValid = true;
            		  		break;
            		  	case "Speed III":
            		  		lvl = 3;
            		  		isValid = true;
            		  		break;
            		  	case "Speed IV":
            		  		lvl = 4;
            		  		isValid = true;
            		  		break;
            		  	case "Speed V":
            		  		lvl = 5;
            		  		isValid = true;
            		  		break;
            		  }
            		  
            		  if(isValid){
            			  PotionEffect effect = new PotionEffect(PotionEffectType.SPEED,lvl,120);
            			  p.addPotionEffect(effect);
            			  ItemStack reIs = new ItemStack(is);
            			  reIs.setAmount(1);
            			  p.getInventory().removeItem(new ItemStack[]{reIs});
            		  }
            	  }
              }                       
              Player p = e.getPlayer();
              Action action = e.getAction();
              Block clicked = e.getClickedBlock();      
              
              if(!e.hasBlock())
              {
            	  return;
              }
            	             
              if (clicked.getType() == Material.IRON_DOOR_BLOCK || clicked.getRelative(BlockFace.DOWN).getType() == Material.IRON_DOOR_BLOCK) 
              {
                  if (action == Action.RIGHT_CLICK_BLOCK) 
                  {
                	  
                	  boolean isOwner = plugin.isPlayerOwnerOfRegion(clicked.getLocation(), p.getName());
                	  if(isOwner == true){
                    	  if(clicked.getRelative(BlockFace.DOWN).getType() == Material.IRON_DOOR_BLOCK)
                    	  {
                    		  clicked = clicked.getRelative(BlockFace.DOWN);
                    	  }
                          final BlockState state = clicked.getState();
                          final Door door = (Door) state.getData();
                          if (door.isOpen() == false) 
                          {
                              door.setOpen(true);
                              new BukkitRunnable()
                              
                              {
								@Override
								public void run() 
								{
									door.setOpen(false);
									state.update();  
								}                         	  
                              }.runTaskLater(plugin, 1200L);
                          }
                          else 
                          {                
                        	  clicked.getWorld().playSound(clicked.getLocation(), Sound.BLOCK_WOODEN_DOOR_OPEN, 1F, 1F);
                              door.setOpen(false);
                          }                        
                          state.update();    
                	  }
                  }
              }
          }

			@EventHandler
			public void onItemDrop(PlayerDropItemEvent e){
				ItemStack is = e.getItemDrop().getItemStack();
				if(is.getType() == Material.PAPER ){
					ItemMeta im = is.getItemMeta();
					String name = ChatColor.stripColor(im.getDisplayName());
					if(name.equalsIgnoreCase(CustomConsumableType.FREIBRIEF.getName())){
						e.setCancelled(true);
					}
				}
			}
			
            @EventHandler
            public void onEntityDeath(EntityDeathEvent e)
            {
            	if(e.getEntity() instanceof Zombie || e.getEntity() instanceof Wolf){
            		if(e.getEntity() instanceof Zombie)
            		{
            		    LivingEntity l = e.getEntity();
            		    CraftZombie z = (CraftZombie)l;
            		    EntityZombie zombie = z.getHandle();
            		    
            			if(zombie instanceof CustomEntityZombie){
            				if(e.getEntity().getKiller() instanceof Player) SpielerProfil.addCrime(1, e.getEntity().getKiller().getName(), "Cop kill");
            				
            				removeFromWaveList((Cop)zombie);
            			}
            		}
            		
            		int max = 7;
            		int min = 3;
            		String name = e.getEntity().getName();
            		if(name.equalsIgnoreCase(CustomEntityZombie.Type.MIDDLE_COP.getName())){
            			min = 5;
            			max = 10;
            		} else if (name.equalsIgnoreCase(CustomEntityZombie.Type.BIG_COP.getName())){
            			min = 10;
            			max = 20;
            		}
            		List<ItemStack> isList = e.getDrops();
            		if(isList.size() != 0){
        				isList.removeAll(isList);
        				ItemStack gold = new ItemStack(Material.GOLD_NUGGET,Jobs.zufall(min, max));
        				isList.add(gold);
            			if(Jobs.zufall(0, 20) == 5){
            				int rnd = Jobs.zufall(0, 6);
            				Material mat = Material.ARROW;
            				if(rnd == 1) mat = Material.CHAINMAIL_BOOTS;
            				if(rnd == 2) mat = Material.CHAINMAIL_CHESTPLATE;
            				if(rnd == 3) mat = Material.CHAINMAIL_HELMET;
            				if(rnd == 4) mat = Material.CHAINMAIL_LEGGINGS;
            				if(rnd == 5) mat = Material.STICK;
            				if(rnd == 6) mat = Material.ARROW;
            				if(rnd == 0) mat = Material.ARROW;
            				
            				int dura = Jobs.zufall(0, 10);
            				int amount = 1;
            				if(mat == Material.ARROW) amount = Jobs.zufall(1, 64);
            				
            				ItemStack is = new ItemStack(mat, amount);
            				is.setDurability((short) (is.getDurability()/dura));
            				isList.add(is);           				
            			}
            		}
            		if(e.getEntity() instanceof Wolf)
            		{
            		    LivingEntity l = e.getEntity();
            		    CraftWolf z = (CraftWolf)l;
            		    EntityWolf wolf = z.getHandle();
            			if(wolf instanceof CustomEntityWolf){
            				CustomEntityWolf w = (CustomEntityWolf) wolf;
            				if(w.isRaidSpawn())
            					removeFromWaveList((Cop)wolf);
            			}
            		}
            	}
            }
            @EventHandler
            public void onBrewFuel(BrewingStandFuelEvent e){
            		
            }
            
            @EventHandler
            public void onBrew(BrewEvent e){
            }
            
            @EventHandler
            public void onBlockBreak(BlockBreakEvent e){
            	if(e.getBlock().getType() == Material.BREWING_STAND){
            		Utility.removeHologramFromStartList(e.getBlock().getLocation().add(0, 2, 0), "stands");
            	}
            }      

            
            @EventHandler(priority = EventPriority.HIGHEST)
            public void onBlockPhysics(BlockPhysicsEvent e){
            	if(e.getBlock().getType() == Material.SUGAR_CANE_BLOCK){
            		e.setCancelled(true);          		
            	}
            }
            
            @EventHandler
            public void onBlockFace(BlockFadeEvent e){
 
            }
            
            @EventHandler
            public void onBlockGrow(BlockGrowEvent e){
            }
            
            @EventHandler
            public void onEntityHit(EntityDamageByEntityEvent e){
            	if(e.getDamager() instanceof Player){
            		Player p = (Player) e.getDamager();
            		String name = p.getName();
            		
            		Entity entity = e.getEntity();
            		if(entity instanceof LivingEntity){
            			LivingEntity l = (LivingEntity) entity;
            			if(l instanceof CraftZombie){        				           			
	            			CraftZombie cZ = (CraftZombie) l;
	            			EntityZombie eZ = cZ.getHandle();
	            			if(eZ instanceof Cop){
	            				if(eZ instanceof CustomEntityZombie){
	            					CustomEntityZombie z = (CustomEntityZombie) eZ;
	            					Set<String> tags = entity.getScoreboardTags();
	            					boolean isFirstHit = true;
	            					for(String tag : tags){
	            						if(tag.equalsIgnoreCase(name)){
	            							isFirstHit = false;
	            						}
	            					}
	            					if(isFirstHit){
	            						SpielerProfil.addCrime(1, name, "Cop hit");
	            						entity.addScoreboardTag(p.getName());
	            					}
	            				}
	            			}
            			}
            		}
            	}
            	
            	
            	if(e.getDamager() instanceof Zombie){
            		LivingEntity l = (LivingEntity) e.getDamager();
            		CraftZombie cZ = (CraftZombie) l;
            		EntityZombie eZ = cZ.getHandle();
                	if(eZ instanceof Cop){
                		if(eZ instanceof CustomEntityZombie){
                			CustomEntityZombie z = (CustomEntityZombie) eZ;
                			if(z.getCopType() == "Chief"){
                				double X = e.getDamager().getLocation().getX();
                				double Y = e.getDamager().getLocation().getY();
                				double Z = e.getDamager().getLocation().getZ();
                				X = -1 * (X - e.getEntity().getLocation().getX());
                				Y = -1 * (Y - e.getEntity().getLocation().getY());
                				Z = -1 * (Z - e.getEntity().getLocation().getZ());
                				Vector vec = new Vector(X,Y,Z);
                				vec.normalize();
                				e.getDamager().getWorld().spawnArrow(e.getDamager().getLocation().add(vec.getX(), 2, vec.getZ()), vec, 1F, 1F);
                				e.setCancelled(true);
                			}
                		}
                	}
            	}
            	if(e.getDamager() instanceof Zombie || e.getDamager() instanceof CraftZombie || e.getDamager() instanceof EntityZombie){
            		LivingEntity l = (LivingEntity) e.getDamager();
            		if(e.getEntity() instanceof Player){
            			if(SpielerProfil.protectedPlayer.contains(e.getEntity().getName())) e.setCancelled(true);
            			if(l.getName().equalsIgnoreCase("cop")){
            				Double damage = e.getDamage();
            				Player p = (Player) e.getEntity();
            				if(damage >= p.getHealth()){
                				CraftZombie cz = (CraftZombie) l;
                				EntityZombie ez = cz.getHandle();
                				if(ez instanceof CustomEntityZombie){
                					CustomEntityZombie custom = (CustomEntityZombie) ez;
                					if(custom.raid != true){
                						e.setCancelled(true);
                						if(!SpielerProfil.isAttemptingToEnterJail(p.getName())){
                							SpielerProfil.paralyze(p.getName());
                							SpielerProfil.attemptToSentToJailIn(p.getName(), 1000L);
                							SpielerProfil.stopZombieAggressionFor(p.getName(), 1000L);
                							p.openInventory(EventListener.cuffInv);
                						}
                					}
                				}
            				}
            			}
            		}
            	}
            	if(e.getDamager() instanceof Player){
            		Player p = (Player) e.getDamager();
            		if(SpielerProfil.protectedPlayer.contains(p.getName())){
            			if(SpielerProfil.isAttemptingToEnterJail(p.getName()) != true){
            				SpielerProfil.protectedPlayer.remove(p.getName());
            			}
            		}
            	}
            }
            public void removeFromWaveList(Cop entity)
            {
        		if(entity.isRaidSpawn()){
        			String shopName = entity.getAt();
        			EntityLiving living = (EntityLiving) entity;
        			int i = 0;
        			if(!plugin.waveEntities.containsKey(shopName))
        				return;
        			for(EntityLiving cop : plugin.waveEntities.get(shopName)){ 
        				if(cop == null) continue;
        				if(cop.equals(living)){
        					List<EntityLiving> list = plugin.waveEntities.get(shopName);
        					list.remove(i);
        					plugin.waveEntities.put(shopName, list);
        					if(list.isEmpty()) plugin.waveEntities.remove(shopName);
        					break;
        				}
    					i++;
        			}
        		}
            }
            	
            
/*     */  
/*     */ 
/*     */   @EventHandler
/*     */   public void onCrime(EntityDamageByEntityEvent event)
/*     */   {
/* 115 */     EntityType pOderNicht = event.getEntityType();
/* 116 */     if (pOderNicht == EntityType.PLAYER)
/*     */     {
/* 118 */       if (event.getDamager().getType() == EntityType.PLAYER)
/*     */       {
/* 120 */         Player p = (Player)event.getDamager();
/* 121 */         Player enemy = (Player)event.getEntity();

/*     */ 		  event.setCancelled(PlayerEffectEventsClass.onPlayerDamageByPlayer(p, enemy));

/* 123 */         if (plugin.getConfig().contains("MyViCrime.SpielerProfile." + p.getName()))
/*     */         {
/* 126 */         SpielerProfil.addCrime(Integer.valueOf(4), p.getName(), "Player hit " + enemy.getName());
/*     */         }
/*     */         else
/*     */         {
/* 130 */         plugin.getConfig().addDefault("MyViCrime.SpielerProfile." + p.getName(), Integer.valueOf(1));
/* 131 */           plugin.getConfig().options().copyDefaults(true);
/* 132 */           plugin.saveConfig();
/*     */         }
/*     */       }
/*     */     }
/*     */   }

            @EventHandler
            public void onPlayerDisconnect(PlayerQuitEvent e)
            {
                main.playerCount--;
                Player p = e.getPlayer();
              
                
                if(plugin.raidClass.isPlayerRobbing(p)){
                	String shopName = plugin.raidClass.getNameOfRobbedShop(p);
                	plugin.raidClass.onPlayerFleed(p, shopName);
                }
                
                plugin.crimeTick.removePlayer(p);
                
                for(int i = 0; i < this.crimeTick.size(); i++){
                	String key = crimeTick.get(i);
                	if(key == p.getName()){
                		crimeTick.remove(i);
                		break;
                	}
                }
            }
            
            @EventHandler
            public void worldLoadedEvent(WorldLoadEvent e){
				  Bukkit.broadcastMessage("Clearing cops");
				  for(Entity entity : e.getWorld().getEntities()){
					  if(!(entity instanceof Player)){
						  entity.remove();
					  }
				  }
				  System.out.println("clearing cops finished!");
            }
            
            
      
            
/*     */ 
/*     */   @EventHandler
/*     */   public void chakra(PlayerJoinEvent event)
/*     */   {
	          main.playerCount++;
	          Player p = event.getPlayer();
			  SpielerProfil.initCompass(p);
	          String name = p.getName();
	          int lastPlayedDay = SpielerProfil.getLastJoinDay(name);
	          Calendar calendar = Calendar.getInstance();
	          int day = calendar.get(Calendar.DAY_OF_MONTH);
	          if(lastPlayedDay != 0){
	        	  if(lastPlayedDay != day){
	        		  SpielerProfil.setDailyReward(name, true);
	        	  }
	          } else {
	        	  SpielerProfil.setDailyReward(name, true);
	          }
	          
	          ItemStack compass = new ItemBuilder(Material.COMPASS)
	        		  .type(Material.COMPASS)
	        		  .name("Navi")
	        		  .make();
	          
	          p.getInventory().addItem(compass);
	          
	          if(!SpielerProfil.isFleeingPrisoner(name))
	        	  plugin.crimeTick.addPlayer(p);
	        
	          
	          SpielerProfil.setLastJoinDay(name);	     
/*     */ 	  SpielerProfil.initializePlayerprofile(p);
	          SpielerProfil.setSpawnByRank(name);
	          plugin.gui.updateOf(p);
	          
	          if(SpielerProfil.getCurrentRank(p).equalsIgnoreCase("prisoner"))
	        	  SpielerProfil.setPrisoner(p);
	      
	          if(SpielerProfil.isFleeingPrisoner(p.getName()))
	        	  
              if(!(crimeTick.contains(p.getName()))) 
              {
            	  crimeTick.add(p.getName());
            	  Utility.sendTitleDelayed(p, "Herzlich Willkommen",  ChatColor.YELLOW + "" + ChatColor.BOLD + p.getName() , (long) 100);
              }
              if(!(crimeSchleife == true)) 
              {
            	  plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            		@SuppressWarnings("deprecation")
					@Override
					public void run() {
            			
            			for(int n = 0; n < deadPlayerList.size(); n++)
            			{
            				String name = deadPlayerList.get(n);
            				int cd = deadPlayerCooldown.get(name);
            				cd--;
            				
            				if(cd <= 0)
            				{
            					removePlayerHologram(name);
            				}
            				else
            				{
            					deadPlayerCooldown.put(name, cd);
            				}
            				
            			}
            			int counter = 0;
            			for (Iterator<String> iterator = plugin.deactivatedBlocks.iterator(); iterator.hasNext();) 
            			{
            			    String key = iterator.next();
            			    
            				Integer cooldown = plugin.StringIntegerMap.get(key);
            				cooldown--;
            				
            				int x = Integer.parseInt(key.split(":")[0]);
            				int y = Integer.parseInt(key.split(":")[1]);
            				int z = Integer.parseInt(key.split(":")[2]);
            				
            				Location loc = new Location(plugin.getServer().getWorld("Map"), x,y,z);
            		
            				if(cooldown == 0)
            				{
            					if(plugin.deactivatedMaterial.containsKey(key)){
            						Material mat = plugin.deactivatedMaterial.get(key);
            						plugin.deactivatedMaterial.remove(key);
            						plugin.activateActiveBlock(key, loc.getBlock(), mat , iterator,"Mining");  
            					}        				
            				}
            				else
            				{
            					plugin.StringIntegerMap.put(key, cooldown);
            				}            				
            			    counter++;
            			}
            			
					}
            		  
            	  }, 20L, 1200L);
            	  crimeSchleife = true;
              }
              // Gibt den Spieler beim joinen ihren angenommenen Job.
/*     */   }
			@EventHandler
			public void onEntityCombust(EntityCombustEvent event){
				if(event.getEntity() instanceof Zombie){

				}
			}
            public static void addCrimeTick(String name) 
            {
            	if(SpielerProfil.isFleeingPrisoner(name))
            		return;
            	plugin.crimeTick.addPlayer(Bukkit.getPlayer(name));
            }
            
            public static void removePlayerHologram(String name)
            {
				//Bukkit.getPlayer(name).sendMessage("hologramm entfernt");
				
			    Location loc = deadPlayerBlock.get(name);
				deadPlayerKiller.remove(loc);
				deadPlayerCooldown.remove(name);
				deadPlayer.remove(name);
				deadPlayerList.remove(name);
				deadPlayerBlock.remove(name);
				holograms.get(name).delete();
				holograms.remove(name);
            }
/*     */ 
/*     */   private void stopScheduler()
/*     */   {
/* 193 */     plugin.getServer().getScheduler().cancelTask(this.taskID);
/*     */   }
            @EventHandler
            public void onKill(PlayerDeathEvent e)
            {
            	if(e.getEntity() instanceof Player){
            		Player p = e.getEntity();
            		String name = p.getName();
            		if(SpielerProfil.isParalyzed(name)){
            			if(SpielerProfil.isAttemptingToEnterJail(name))
            				SpielerProfil.sentToJail(p);
            		}
            	}
            	
            	if(!(e.getEntity().getKiller() == null))
            	{           		       	
                	if(e.getEntity().getKiller().getType() == EntityType.PLAYER) 
                	{               
                    	String killer = e.getEntity().getKiller().getName();
                    	SpielerProfil.addCrime(100, killer, "Player kill " + e.getEntity().getName());                    	
                	}
            	}
            	
            	
            	if(!deadPlayerList.contains(e.getEntity().getName()))
            	{
                	Calendar c = Calendar.getInstance();
                	deadPlayer.put(e.getEntity().getName(), e.getEntity().getLocation());
                	
                	Location loc = e.getEntity().getLocation();
                	loc.setY(loc.getY()-1);
                	Block block = loc.getBlock();
                	loc = block.getLocation();
                	
                	deadPlayerBlock.put(e.getEntity().getName(), loc);
                	deadPlayerList.add(e.getEntity().getName());
                	deadPlayerCooldown.put(e.getEntity().getName(), 3);
                	holograms.put(e.getEntity().getName(), HolographicDisplaysAPI.createHologram(plugin, e.getEntity().getLocation().add(0, 1, 0),"R.I.P", ChatColor.YELLOW + e.getEntity().getName(), c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE)));   
                	
                	if(e.getEntity().getKiller() instanceof Player)
                	{
                		Player killer = e.getEntity().getKiller();
                		deadPlayerKiller.put(loc, killer.getName());
                	}
            	}
            }
            
            @EventHandler
            public void onPlayerOpenDoor(PlayerInteractEvent e){
            	if(Action.RIGHT_CLICK_BLOCK == e.getAction()){
            		if(e.getHand() == EquipmentSlot.HAND){
                		Material mat = e.getClickedBlock().getType(); 
            		}
            	}
            }
            
            @EventHandler
            public void onEntitySpawn(EntitySpawnEvent e){
            	Entity en = e.getEntity();
            	
            	if(en instanceof Squid){
            		e.setCancelled(true);
            	}
            	if(en instanceof Skeleton){
            		try{
                		if(en.getScoreboardTags().contains("pluginSpawn")){
                			
                		} else {
                			e.setCancelled(true);
                		}
            		} catch(NullPointerException ex){
                		e.setCancelled(true);	
            		}
            	}         	
            	if(en instanceof Creeper){
            		e.setCancelled(true);
            	}           	
            	if(en instanceof Spider){
            		e.setCancelled(true);
            	}
            	if(en instanceof Enderman){
            		e.setCancelled(true);
            	}
            	if(en instanceof Pig){
            		e.setCancelled(true);
            	}
            	if(en instanceof Cow){
            		e.setCancelled(true);
            	}           	
            	if(en instanceof Witch){
            		e.setCancelled(true);
            	}            	
            	if(en instanceof Chicken){
            		e.setCancelled(true);
            	}
            	if(en instanceof Sheep){
            		e.setCancelled(true);
            	}
            	if(en instanceof Horse){
            		e.setCancelled(true);
            	}
            	if(en instanceof Donkey){
            		e.setCancelled(true);
            	}
            	if(en instanceof Rabbit){
            		e.setCancelled(true);
            	}

            	if(en instanceof Zombie){
            		Location loc = en.getLocation();
                	String name = en.getName();
                	
            		try{
                		if(en.getScoreboardTags().contains("pluginSpawn")){
                			return;
                		}
            		} catch(NullPointerException ex){
            			
            		}
            		
            		if(name != "Cop" && name != "Chief" && name != "Officer" && name != "Swat"){
            			e.setCancelled(true);
            		} else {
            				List<String> shops = new ArrayList<String>();
            				for(int i = 0; i < plugin.shops.length; i++){
            					shops.add(plugin.shops[i]);
            				}
            				if(Utility.isInRegions(loc, shops) == false){
                        		List<String> list = new ArrayList<String>();
                        		list.add("myvcrimecity");
                        		list.add("overground");
                        		if(!Utility.isOnlyInRegions(loc, list)){
                        			e.setCancelled(true);
                        		}
            				}
            			}
            		}
            	}
            
            @EventHandler
            public void onItemHeld(PlayerItemHeldEvent e){
            	Player p = e.getPlayer();
            	int newSlot = e.getNewSlot();
            	ItemStack item = p.getInventory().getItem(newSlot);
            	
            	if(SpielerProfil.isWeapon(item)){
        			for(String s : main.shops){
        				if(Utility.isInRegion(p.getLocation(), s)){

	        					if(plugin.shopManager.getRaidableShop(s) != null){
	        						RaidableShop shop = plugin.shopManager.getRaidableShop(s);
	        						if(shop.isRaidable()){
	        							WaveRaid waveRaid = new WaveRaid(shop, p);
	        							waveRaid.start();
	        						} else {
	        							if(shop.hasRaid()){
	        								
	        							} else {
	        								Msg.INFO.out(p, shop.getName() + " got already robbed!");
	        							}
	        						}
	        					}
	        				
        				}
        			}
            	}
            }
            
            
            @EventHandler
            public void onSpawn(PlayerRespawnEvent e){
            	final Player p = e.getPlayer();
            	if(SpielerProfil.getSpawn(p) != null){
            		e.setRespawnLocation(SpielerProfil.getSpawn(p));
            		if(SpielerProfil.getCurrentRank(p) == "Prisoner"){
            			if(!SpielerProfil.prisoner.contains(p.getName())){
            				new BukkitRunnable(){
								@Override
								public void run() {
									SpielerProfil.sentToJail(p);
								}           					
            				}.runTaskLater(plugin, 1);
            			}
            		}
            	}
            	new BukkitRunnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						SpielerProfil.deParalyze(p.getName());
						if(SpielerProfil.prisoner.contains(p.getName()) && SpielerProfil.getCrime(p.getName()) <= 0){
							ItemStack is = CustomItems.getCustomItem(CustomItemType.FREIBRIEF);
							p.getInventory().addItem(new ItemStack[]{is});
						}
					}         		
            	}.runTaskLater(plugin, 50L);
            }
            
            public static void updateCompass(String name,Location loc) 
            {
            	Player p = Bukkit.getPlayer(name);
            	p.setCompassTarget(loc);
            }
            
            @EventHandler
            public void onResourcepackStatusEvent(PlayerResourcePackStatusEvent event){
            	if(event.getStatus() == PlayerResourcePackStatusEvent.Status.ACCEPTED){
            		SpielerProfil.acceptedRessourcePack.add(event.getPlayer().getName());
            	}
            }
            
            public static boolean rangeOf(int x2, int y2, int z2, int x, int y, int z, int range)
            {
            	for(int i = 0; i >= range; i++)
            	{
 
            	}
            	
				return false;
            	
            }
/*     */ 
           }

/* Location:           C:\Users\HAPPYMAJOR\Desktop\jd-gui-0.3.5.windows\MyViCrime.jar
 * Qualified Name:     main.EventListener
 * JD-Core Version:    0.6.2
 */