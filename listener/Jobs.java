/*     */ package myvcrime.listener;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.List;

/*     */ import org.bukkit.ChatColor;
import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.material.Cauldron;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.Utility;
import myvcrime.main;
import myvcrime.customEvents.PlayerMinedEvent;
import myvcrime.customEvents.PlayerPickupBottlesEvent;
import myvcrime.quests.PickupQuestObjective;
import myvcrime.quests.QuestActionType;
import myvcrime.quests.QuestType;
/*     */ 
/*     */ public class Jobs
/*     */   implements Listener
/*     */ {
/*     */   public static main plugin;
/*  39 */   private HashMap<Location, Long> mülleimerCooldown = new HashMap<Location, Long>();
/*  40 */   private HashMap<String, Long> jobsCooldown = new HashMap<String, Long>();
            public static HashMap<String, String> killMission = new HashMap<String, String>();
            public HashMap<String, Long> lastClick = new HashMap<String, Long>();
            public ArrayList<String> schedulingNextClick = new ArrayList<String>();
            public ArrayList<String> playerScheduler = new ArrayList<String>();
            
            public static ArrayList<String> cuffedPlayer = new ArrayList<String>();
/*  41 */   public ArrayList<Location> mülleimer = new ArrayList<Location>();
/*     */   public static int i;
/*     */   public Long time;
/*     */ 
/*     */   public Jobs(main instance)
/*     */   {
/*  35 */     plugin = instance;
/*     */   }

public void addDurability(Player p, ItemStack item,short amount){
	item.setDurability((short) (item.getDurability() + amount));
	Material mat = item.getType();
	short maxDurability = mat.getMaxDurability();
	if(item.getDurability() >= maxDurability){	
		Inventory inv = p.getInventory();
		inv.removeItem(item);
	}
	
}
/*     */ 
/*     */   @SuppressWarnings("deprecation")
@EventHandler
/*     */   public void onJobs(PlayerInteractEvent event)
/*     */   {
/*  49 */     final Player p = event.getPlayer();

            	  Block clickedBlock = event.getClickedBlock();          	                
              
/*     */       if(event.getAction() == Action.LEFT_CLICK_BLOCK)
                {
	            if(p.getItemInHand().getType() == Material.WOOD_PICKAXE || p.getItemInHand().getType() == Material.IRON_PICKAXE || p.getItemInHand().getType() == Material.STONE_PICKAXE || p.getItemInHand().getType() == Material.GOLD_PICKAXE || p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)
	            {
	            					Short dura = 0;
	            	                Material mat = p.getItemInHand().getType();
	            	/*  75 */       this.time = Long.valueOf(System.currentTimeMillis());
	            	/*     */ Location loc = clickedBlock.getLocation();
	            	/*  77 */       if (this.jobsCooldown.containsKey(p.getName()))
	            	/*     */       {
	            	/*  79 */         Long lastUsage = (Long)this.jobsCooldown.get(p.getName());
	            	/*     */         int cooldown = 1000 * 4;
	            	                  switch(mat)
	            	                  {
	            	                     case WOOD_PICKAXE:
	            	                	     cooldown = 1000 * 23;
	            	                	     dura = 26;
	            	                	     break;	
	            	                     case STONE_PICKAXE:
	            	                    	 cooldown = 1000 * 18;
	            	                    	 dura = 50;
	            	                    	 break;
	            	                     case IRON_PICKAXE:
	            	                    	 cooldown = 1000 * 15;
	            	                    	 dura = 120;
	            	                    	 break;
	            	                     case GOLD_PICKAXE:
	            	                    	 cooldown = 1000 * 11;
	            	                    	 dura = 52;
	            	                    	 break;
	            	                     case DIAMOND_PICKAXE:	            	                   
	            	                    	 cooldown = 1000 * 13;
	            	                    	 dura = 280;
	            	                    	 break;	            	                    	 	            	                    	 
	            	                  }
	            	
	            	/*  81 */         if (lastUsage.longValue() + cooldown > this.time.longValue())
	            	/*     */         {
	    
	            		
	                    List<Location> locations = plugin.getActiveBlockLocations(clickedBlock,"Mining");
	                    for(Location l : locations)
	                    {
	                    	if(loc.equals(l))
	                    	{
	                			p.playSound(p.getLocation(), Sound.BLOCK_METAL_BREAK, 1.0F, 1.0F);
	    	            		Utility.playCircularEffect(p, clickedBlock, Effect.SMOKE, 8);
	    	            		if(this.schedulingNextClick.contains(p.getName())){
	    	            			this.lastClick.put(p.getName(), System.currentTimeMillis());
	    	            		} else {
	    	            			schedulingNextClick.add(p.getName());
	                				final Long time = System.currentTimeMillis();
	    	            			this.lastClick.put(p.getName(), time);
	    	            			new BukkitRunnable(){
	    	            				int seconds = 0;
	    	            				Long tim = time;
	    	            				String name = p.getName();
	    								@Override
	    								public void run() {
	    									if(schedulingNextClick.contains(name) == false){
	    										lastClick.remove(name);
	    										this.cancel();
	    									} else {
	    									if(tim != lastClick.get(name)){
	    										tim = lastClick.get(name);
	    										seconds = 0;
	    									} else {
	    										seconds++;
	    										if(seconds >= 4){
	    											schedulingNextClick.remove(name);
	    											jobsCooldown.remove(name);
	    											lastClick.remove(name);
	    											this.cancel();
	    											p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Abbau abgebrochen!");
	    										} else {
	    											p.sendMessage(ChatColor.RED +""+ seconds  + "/3");
	    										}
	    									}
	    									}
	    								}
	    	            			}.runTaskTimer(plugin, 20, 20);
	    	            		}
	                    	}
	                    }
	            	/*  83 */           return;
	            	/*     */         }
	            	/*     */       } else{  
	            		jobsCooldown.put(p.getName(), time);
	                    List<Location> locations = plugin.getActiveBlockLocations(clickedBlock,"Mining");
	                    for(Location l : locations)
	                    {
	                    	if(loc.equals(l))
	                    	{
	    	            		if(this.schedulingNextClick.contains(p.getName())){
	    	            			this.lastClick.put(p.getName(), System.currentTimeMillis());
	    	            		} else {
	    	            			schedulingNextClick.add(p.getName());
	                				final Long time = System.currentTimeMillis();
	    	            			this.lastClick.put(p.getName(), time);
	    	            			new BukkitRunnable(){
	    	            				int seconds = 0;
	    	            				Long tim = time;
	    	            				String name = p.getName();
	    								@Override
	    								public void run() {
	    									if(schedulingNextClick.contains(name) == false){
	    										lastClick.remove(name);
	    										this.cancel();
	    									} else {
	    									if(tim != lastClick.get(name)){
	    										seconds = 0;
	    										tim = lastClick.get(name);
	    									} else {
	    										seconds++;
	    										if(seconds >= 4){
	    											schedulingNextClick.remove(name);
	    											jobsCooldown.remove(name);
	    											lastClick.remove(name);
	    											this.cancel();
	    											p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Abbau abgebrochen!");
	    										} else {
	    											p.sendMessage(ChatColor.RED +""+ seconds  + "/3");
	    										}
	    									}
	    									}
	    								}
	    	            			}.runTaskTimer(plugin, 20, 20);
	    	            		}
	                    	}
	                    }
	            		return;
	            	}
                String name = clickedBlock.getType().toString();
            
                
                List<Location> locations = plugin.getActiveBlockLocations(clickedBlock,"Mining");
                for(Location l : locations)
                {
                	if(loc.equals(l))
                	{
                		ItemStack stack = p.getItemInHand();
                		
                		short[] lowestDura = new short[]{60,132,251,33,1562};
                		
                		this.addDurability(p, stack, (short) 1);               		
                		if(stack.getDurability() == dura){
                			if(stack.getType() == Material.WOOD_AXE)
                				stack.setDurability(lowestDura[0]);
                			if(stack.getType() == Material.GOLD_AXE)
                				stack.setDurability(lowestDura[3]);
                			if(stack.getType() == Material.STONE_AXE)
                				stack.setDurability(lowestDura[1]);
                			if(stack.getType() == Material.IRON_AXE)
                				stack.setDurability(lowestDura[2]);
                			if(stack.getType() == Material.DIAMOND_AXE)
                				stack.setDurability(lowestDura[4]);
                		}                		              		
                		Boolean doubleOre = false;
                		if(p.getItemInHand().getType() == Material.GOLD_PICKAXE) doubleOre = true;  
                		int randDec = this.zufall(0, 1);
                		
                		if(randDec == 1){
                			if(plugin.getDurability(clickedBlock, "Mining") - 2 < 0){
                				plugin.decreaseDurability(clickedBlock, "Mining", 1);
                			} else {
                    			plugin.decreaseDurability(clickedBlock,"Mining",2);
                			}
                		} 
                		
                		plugin.getServer().getPluginManager().callEvent(new PlayerMinedEvent(p,clickedBlock.getType(),1));
                		
                		jobsCooldown.put(p.getName(), time);
                		p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
                		Utility.playCircularEffect(p, clickedBlock, Effect.LAVA_POP,8);	
                		if(name.equalsIgnoreCase(Material.COAL_ORE.toString()))
                		{
                			p.getInventory().addItem(new ItemStack(Material.COAL));
                			p.updateInventory();
                			if(doubleOre){
                    			p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                    			p.updateInventory();
                			}
                		}
                		if(name.equalsIgnoreCase(Material.IRON_ORE.toString()))
                		{
             
                			for(int i = 0; i < 1; i++){
                			p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                			p.updateInventory();
                			if(doubleOre){
                    			p.getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                    			p.updateInventory();
                			}
                			}
                		}
                		if(name.equalsIgnoreCase(Material.GOLD_ORE.toString()))
                		{
                			for(int i = 0; i < 1; i++)
                			{
                				p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));
                				p.updateInventory();
                			}
                		}
                		if(name.equalsIgnoreCase(Material.DIAMOND_ORE.toString()))
                		{
                			for(int i = 0; i < 1; i++)
                			{
                				p.getInventory().addItem(new ItemStack(Material.DIAMOND));
                				p.updateInventory();
                    			if(doubleOre){
                        			p.getInventory().addItem(new ItemStack(Material.DIAMOND));
                        			p.updateInventory();
                    			}
                			}
                		}
                	}
                }
	            }
                }

                if(main.ab.containsKey(p.getName()))
                {
                	Block clicked = event.getClickedBlock();
                	for(Material m : main.blocksMaterial)
                	{
                		if(m.toString().equalsIgnoreCase(main.ab.get(p.getName())))
                		{
                			if(plugin.abReason.containsKey(p.getName()))
                			{
                				if(plugin.abDifficulty.containsKey(p.getName())){
                    				clicked.setType(m);
                    				boolean t = plugin.addActiveRaidBlock(clicked,plugin.abReason.get(p.getName()),plugin.abDifficulty.get(p.getName()));
                    				if(t == true)
                    					p.sendMessage(ChatColor.GREEN + "ActiveRaidBlock erfolgreich gesetzt.");
                				} else {
                    				clicked.setType(m);
                    				boolean t = plugin.addActiveBlock(clicked,plugin.abReason.get(p.getName()));
                    				if(t == true)
                    					p.sendMessage(ChatColor.GREEN + "ActiveBlock erfolgreich gesetzt.");
                				}
                			}
                		}
                	}
                }
                if(plugin.raidMarker.contains(p.getName())){
                	Block clicked = event.getClickedBlock();
                	Location loc = clicked.getLocation();
                	plugin.addSpawn(plugin.raidMarkerShop.get(p.getName()), loc, plugin.raidMarkerSpawn.get(p.getName()), plugin.raidMarkerWave.get(p.getName()));
                	plugin.raidMarker.remove(p.getName());
                	plugin.raidMarkerShop.remove(p.getName());
                	plugin.raidMarkerSpawn.remove(p.getName());
                	plugin.raidMarkerWave.remove(p.getName());
                }

/*  99 */       if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
/*     */       {
/* 101 */         if (event.getClickedBlock().getType() == Material.CAULDRON)
/*     */         {
/* 103 */           Cauldron leererEimer = (Cauldron)event.getClickedBlock().getState().getData();
/*     */ 
/* 105 */           if (leererEimer.isEmpty())
/*     */           {
/* 107 */             Block eimer = event.getClickedBlock();
/* 108 */             Long time = Long.valueOf(System.currentTimeMillis());
/*     */ 
/* 110 */             if (this.mülleimerCooldown.containsKey(eimer.getLocation()))
/*     */             {
/* 112 */               Long lastUsage = (Long)this.mülleimerCooldown.get(eimer.getLocation());
/*     */ 
/* 114 */               if (lastUsage.longValue() + 300000L > time.longValue())
/*     */               {
/* 116 */                 p.sendMessage(ChatColor.GRAY + "Der Mülleimer ist leer!");
/* 117 */                 return;
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 122 */             this.mülleimerCooldown.put(eimer.getLocation(), time);
/*     */ 
/* 124 */             int flaschen = zufall(1, 3);
/* 125 */             int rareDrop = zufall(0, 200);
                      int extraRareDrop = zufall(0,3000);
/* 126 */             int superRareDrop = zufall(0, 10000);
/*     */ 			  
					  ItemStack item = new ItemStack(Material.GLASS_BOTTLE);

					  plugin.getServer().getPluginManager().callEvent(new PlayerPickupBottlesEvent(p,flaschen,item));

//
/* 128 */             for (int i = 0; i < flaschen; i++)
/*     */             {
/* 130 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GLASS_BOTTLE) });
/* 131 */               p.updateInventory();
/*     */             }
/*     */ 
/* 135 */             if (extraRareDrop == 1)
/*     */             {
/* 137 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.STONE_SWORD) });
						p.sendMessage("Herzlichen Glückwunsch, du hast ein Knife aus dem Mülleimer gefischt!");
/*     */             }

/* 135 */             if (extraRareDrop == 2)
/*     */             {
/* 137 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD_SWORD) });
						p.sendMessage("Herzlichen Glückwunsch, du hast ein Basy aus dem Mülleimer gefischt!");
/*     */             }

/* 135 */             if (extraRareDrop == 3)
/*     */             {
/* 137 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GOLD_SWORD) });
						p.sendMessage("Herzlichen Glückwunsch, du hast ein Cleaver aus dem Mülleimer gefischt!");
/*     */             }

/* 135 */             if (rareDrop > 50 && rareDrop < 60)
/*     */             {
/* 137 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.COOKED_CHICKEN) });
/*     */             }   

					  if(rareDrop == 11){
						  p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.BREAD) });
					  }
						
					  if( rareDrop == 10){
						  p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.GRILLED_PORK) });
					  }
						
					  if(extraRareDrop == 201){
						  p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.WOOD_AXE) });
						  p.sendMessage("Herzlichen Glückwunsch, du hast eine Machete aus dem Mülleimer gefischt!");
					  }
/*     */ 
/* 141 */             if (extraRareDrop == 200)
/*     */             {
/* 143 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.IRON_SWORD) });
						p.sendMessage("Herzlichen Glückwunsch, du hast eine Machete aus dem Mülleimer gefischt!");
/*     */             }
/*     */ 
/* 153 */             if (superRareDrop == 0)
/*     */             {
/* 155 */               p.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.AQUA + "! Der heilige Muelleimergott hat deine Bemuehungen belohnt und dir ein Diamantschwert geschenkt! Kaempfe nun im Namen des Muelleimergottes!");
/* 156 */               p.getInventory().addItem(new ItemStack[] { new ItemStack(Material.DIAMOND_SWORD) });
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 163 */             p.sendMessage(ChatColor.GRAY + "Der Eimer ist mit Wasser voll gelaufen.");
/*     */           }
/*     */         }
/*     */       }
/*     */   }

/*     */   public static int zufall(int low, int high)
/*     */   {
/* 191 */     return (int)(Math.random() * (high - low) + low);
/*     */   }
}


/* Location:           C:\Users\HAPPYMAJOR\Desktop\jd-gui-0.3.5.windows\MyViCrime.jar
 * Qualified Name:     main.Jobs
 * JD-Core Version:    0.6.2
 */