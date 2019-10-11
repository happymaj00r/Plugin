/*     */ package myvcrime;
/*     */ import java.util.ArrayList;
import java.util.HashMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;

import myvcrime.abilities.Effects;
/*     */ 
/*     */ public class Waffen
/*     */   implements Listener
/*     */ {
/*     */   public static main plugin;
/*  36 */   private HashMap<String, Long> cooldowns = new HashMap<String, Long>();
            public ArrayList<String> handCuffedPlayer = new ArrayList<String>();
/*     */  
            Effect effectsClass;
            
/*     */   public Waffen(main instance)
/*     */   {
/*  34 */     plugin = instance;
/*     */   }

			private HashMap<ItemStack,Integer> itemDuraMap = new HashMap<ItemStack,Integer>();
			public void addDurability(Player p, ItemStack item,short amount){
				
				if(item.getType() == Material.GOLD_AXE){
					if(itemDuraMap.containsKey(item)){
						int dura = itemDuraMap.get(item);
						dura++;
						if(dura >= 45){
							item.setDurability((short) (item.getDurability()+1));
							itemDuraMap.put(item, 0);
						} else {
							itemDuraMap.put(item, dura);
						}
						if(item.getDurability() >= item.getType().getMaxDurability()){
							Inventory inv = p.getInventory();
							inv.removeItem(item);
							itemDuraMap.remove(item);
						}
						
					} else {
						itemDuraMap.put(item, 1);
					}
				} else {
				
				item.setDurability((short) (item.getDurability() + amount));
				Material mat = item.getType();
				short maxDurability = mat.getMaxDurability();
				if(item.getDurability() >= maxDurability){	
					Inventory inv = p.getInventory();
					inv.removeItem(item);
				}
				}
				
			}
/*     */ 
/*     */   @EventHandler
/*     */   public void onPlayerInteractWaffen1(PlayerInteractEvent e)
/*     */   {
/*  41 */     Player p = e.getPlayer();
/*  43 */     if ((e.getAction() == Action.LEFT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_AIR))
/*     */     {   
				ItemStack itemHand = p.getItemInHand();
/*  71 */       if ((p.getItemInHand().getType() == Material.IRON_AXE) && (p.getInventory().contains(Material.ARROW)))
/*     */       {
/*  73 */         Long time = Long.valueOf(System.currentTimeMillis());
/*  74 */         if (this.cooldowns.containsKey(p.getName()))
/*     */         {
/*  76 */           Long lastUsage = (Long)this.cooldowns.get(p.getName());
/*     */ 
/*  78 */           if (lastUsage.longValue() + 400L > time.longValue())
/*     */           {
/*  80 */             return;
/*     */           }
/*     */         }
				  addDurability(p,itemHand,(short) 2);
					p.updateInventory();
/*  83 */         Arrow a2 = (Arrow)p.launchProjectile(Arrow.class);
/*  84 */         a2.setVelocity(p.getEyeLocation().getDirection().multiply(4));
/*  85 */         p.getWorld().playSound(p.getPlayer().getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 1.0F, 0.0F);
/*  86 */         this.cooldowns.put(p.getName(), time);
			      Effects effects = new Effects(plugin);
			      effects.shotgun(p);
/*  88 */         if (p.getInventory().contains(Material.ARROW))
/*     */         {
					Inventory inv = p.getInventory();
					ItemStack arrow = new ItemStack(Material.ARROW,1);
					ItemStack namedArrow = new ItemBuilder(Material.ARROW,"Ammo",new ArrayList<String>()).getItem();
					if(inv.containsAtLeast(arrow,1)){
						inv.removeItem(new ItemStack[]{arrow});
					} else {
						inv.removeItem(new ItemStack[]{namedArrow});
					}			
/*     */         }
/*     */       }
/*     */ 
/*  96 */       if ((p.getItemInHand().getType() == Material.GOLD_AXE) && (p.getInventory().contains(Material.ARROW)))
/*     */       {
/*  98 */         Long time = Long.valueOf(System.currentTimeMillis());
/*  99 */         if (this.cooldowns.containsKey(p.getName()))
/*     */         {
/* 101 */           Long lastUsage = (Long)this.cooldowns.get(p.getName());
/*     */ 
/* 103 */           if (lastUsage.longValue() + 100L > time.longValue())
/*     */           {
/* 105 */             return;
/*     */           }
/*     */         }
addDurability(p,itemHand,(short) 1);
/* 108 */         Arrow a3 = (Arrow)p.launchProjectile(Arrow.class);
/* 109 */         a3.setVelocity(p.getEyeLocation().getDirection().multiply(4));
/* 110 */         p.getWorld().playSound(p.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0F, 0.0F);
/* 111 */         this.cooldowns.put(p.getName(), time);
/*     */ 
/* 113 */         if (p.getInventory().contains(Material.ARROW))
/*     */         {
					Inventory inv = p.getInventory();
					ItemStack arrow = new ItemStack(Material.ARROW,1);
					ItemStack namedArrow = new ItemBuilder(Material.ARROW,"Ammo",new ArrayList<String>()).getItem();
					if(inv.containsAtLeast(arrow,1)){
						inv.removeItem(new ItemStack[]{arrow});
					} else {
						inv.removeItem(new ItemStack[]{namedArrow});
					}
/*     */         }
/*     */       }
/*     */ 
/* 121 */       if ((p.getItemInHand().getType() == Material.DIAMOND_AXE) && (p.getInventory().contains(Material.FIREBALL)))
/*     */       {
/* 123 */         Long time = Long.valueOf(System.currentTimeMillis());
/* 124 */         if (this.cooldowns.containsKey(p.getName()))
/*     */         {
/* 126 */           Long lastUsage = (Long)this.cooldowns.get(p.getName());
/* 127 */           if (lastUsage.longValue() + 5000L > time.longValue())
/*     */           {
/* 129 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 133 */        Fireball a3 = (Fireball)p.launchProjectile(Fireball.class);
addDurability(p,itemHand,(short) 39);
				Effects effects = new Effects(plugin);
				effects.rocketLauncher(p);
/* 134 */       a3.setVelocity(p.getEyeLocation().getDirection().multiply(4));
/* 135 */         p.getWorld().playSound(p.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0F, 0.0F);
/* 136 */         this.cooldowns.put(p.getName(), time);
/*     */ 
/* 138 */         if (p.getInventory().contains(Material.FIREBALL))
/*     */         {
					ItemStack namedBall = new ItemBuilder(Material.FIREBALL,"Rocket",new ArrayList<String>()).getItem();
					if(p.getInventory().containsAtLeast(namedBall,1)){
						p.getInventory().removeItem(new ItemStack[]{namedBall});
					} else {
						p.getInventory().removeItem(new ItemStack[] { 
						new ItemStack(Material.FIREBALL, 1) });	
					}
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 147 */       if ((p.getItemInHand().getType() == Material.STONE_AXE) && (p.getInventory().contains(Material.ARROW)))
/*     */       {
/* 149 */         Long time = Long.valueOf(System.currentTimeMillis());
/* 150 */         if (this.cooldowns.containsKey(p.getName()))
/*     */         {
/* 152 */           Long lastUsage = (Long)this.cooldowns.get(p.getName());
/*     */ 
/* 154 */           if (lastUsage.longValue() + 1500L > time.longValue())
/*     */           {
/* 156 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 160 */         Arrow a3 = (Arrow)p.launchProjectile(Arrow.class);
addDurability(p,itemHand,(short) 1);
p.updateInventory();
				 
/* 161 */         a3.setVelocity(p.getEyeLocation().getDirection().multiply(4));
/* 162 */         p.getWorld().playSound(p.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0F, 0.0F);
/* 163 */         this.cooldowns.put(p.getName(), time);
/*     */ 
/* 165 */         if (p.getInventory().contains(Material.ARROW))
/*     */         {
	Inventory inv = p.getInventory();
	ItemStack arrow = new ItemStack(Material.ARROW,1);
	ItemStack namedArrow = new ItemBuilder(Material.ARROW,"Ammo",new ArrayList<String>()).getItem();
	if(inv.containsAtLeast(arrow,1)){
		inv.removeItem(new ItemStack[]{arrow});
	} else {
		inv.removeItem(new ItemStack[]{namedArrow});
	}
/*     */         }
/*     */       }
/*     */ 
/* 173 */       if ((p.getItemInHand().getType() == Material.WOOD_AXE) && (p.getInventory().contains(Material.ARROW)))
/*     */       {
/* 175 */         Long time = Long.valueOf(System.currentTimeMillis());
/* 176 */         if (this.cooldowns.containsKey(p.getName()))
/*     */         {
/* 178 */           Long lastUsage = (Long)this.cooldowns.get(p.getName());
/*     */ 
/* 180 */           if (lastUsage.longValue() + 800.0D > time.longValue())
/*     */           {
/* 182 */             return;
/*     */           }
/*     */         }
/*     */ addDurability(p,itemHand,(short) 1);
/* 186 */         Arrow a = (Arrow)p.launchProjectile(Arrow.class);
/* 187 */         a.setVelocity(p.getEyeLocation().getDirection().multiply(4));
/* 188 */         p.getWorld().playSound(p.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0F, 0.0F);
/* 189 */         this.cooldowns.put(p.getName(), time);
/*     */ 
/* 191 */         if (p.getInventory().contains(Material.ARROW))
/*     */         {
	Inventory inv = p.getInventory();
	ItemStack arrow = new ItemStack(Material.ARROW,1);
	ItemStack namedArrow = new ItemBuilder(Material.ARROW,"Ammo",new ArrayList<String>()).getItem();
	if(inv.containsAtLeast(arrow,1)){
		inv.removeItem(new ItemStack[]{arrow});
	} else {
		inv.removeItem(new ItemStack[]{namedArrow});
	}
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   @SuppressWarnings({ "deprecation" })
            @EventHandler
/*     */   public void onEntityDamageByEntity(EntityDamageByEntityEvent event)
/*     */   {   
	         if(event.getCause() == event.getCause().PROJECTILE) 
	         {	        	 
/* 208 */       if ((event.getDamager() instanceof Arrow))
/*     */       {
/* 210 */         if (event.getEntityType() == EntityType.PLAYER)
/*     */         {
/* 212 */           Arrow shootedArrow = (Arrow)event.getDamager();
					if(shootedArrow.getShooter() == null){
						return;
					}
/* 214 */           if (((Entity) shootedArrow.getShooter()).getType() == EntityType.PLAYER)
/*     */           {
/* 216 */             Player enemy = (Player)event.getEntity();
/* 217 */             Player damager = (Player)shootedArrow.getShooter();
/* 218 */             System.out.println("Damager gefunden");
/* 219 */             Material handItem = damager.getItemInHand().getType();
/*     */ 
/* 221 */             if (handItem == Material.WOOD_AXE)
/*     */             {
/* 223 */               int schadenHoltAxt = plugin.getConfig().getInt("MyViCrime.Waffen.Holzaxt.Schaden");
/* 224 */               event.setDamage(schadenHoltAxt);
/*     */ 
/* 227 */               SpielerProfil.addCrime(Integer.valueOf(5), damager.getName(), "hurt_" + enemy.getName());
/*     */             }
/*     */ 
/* 230 */             if (handItem == Material.STONE_AXE)
/*     */             {
/* 232 */               int schadenSteinAxt = plugin.getConfig().getInt("MyViCrime.Waffen.Steinaxt.Schaden");
/* 233 */               event.setDamage(schadenSteinAxt);
/*     */ 
/* 236 */               SpielerProfil.addCrime(Integer.valueOf(5), damager.getName(), "hurt_" + enemy.getName());
/*     */             }
/*     */ 
/* 239 */             if (handItem == Material.GOLD_AXE)
/*     */             {
/* 241 */               int schadenGoldAxt = plugin.getConfig().getInt("MyViCrime.Waffen.Goldaxt.Schaden");
/* 242 */               event.setDamage(schadenGoldAxt);
/*     */ 
/* 245 */               SpielerProfil.addCrime(Integer.valueOf(5), damager.getName(), "hurt_" + enemy.getName());
/*     */             }
/*     */ 
/* 248 */             if (handItem == Material.IRON_AXE)
/*     */             {
/* 250 */               int schadenEisenAxt = plugin.getConfig().getInt("MyViCrime.Waffen.Eisenaxt.Schaden");
/* 251 */               event.setDamage(schadenEisenAxt);
/*     */ 
/* 254 */               SpielerProfil.addCrime(Integer.valueOf(5), damager.getName(), "hurt_" + enemy.getName());
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 259 */             System.out.println("Shooter ist kein Spieler!");
/*     */           }
/*     */         }
				 if(event.getEntityType() == EntityType.ZOMBIE){
					 Zombie zombie = (Zombie) event.getEntity();
					 Arrow arrow = (Arrow) event.getDamager();
					 if(arrow.getShooter() instanceof Player){
						 Player p = (Player) arrow.getShooter();
						 if(!zombie.getScoreboardTags().contains(p.getName())){
							 SpielerProfil.addCrime(5, p.getName(),"Cop hit");
							 zombie.addScoreboardTag(p.getName());
						 }
					 }
				 }
/*     */       }
	         }
	         if(event.getCause() == DamageCause.ENTITY_ATTACK){
	        	 if(event.getDamager() instanceof Player){
	        		 Player p = (Player) event.getDamager();
	        		 ItemStack item = p.getItemInHand();
	        		 if(item.getType() == Material.GOLD_SWORD){
	        		 }
	        	 }
	         }
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void onArrowHit(ProjectileHitEvent event)
/*     */   {
/* 273 */     if ((event.getEntity() instanceof Arrow))
/*     */     {
/* 275 */       Arrow arrow = (Arrow)event.getEntity();
/* 276 */       arrow.remove();
/*     */     }
/*     */ 
/* 280 */     if ((event.getEntity() instanceof Fireball))
/*     */     {
/* 282 */       Fireball fireball = (Fireball)event.getEntity();
/* 283 */       TNTPrimed tnt = (TNTPrimed)fireball.getLocation().getWorld().spawn(fireball.getLocation(), TNTPrimed.class);
/* 284 */       tnt.setFuseTicks(0);
/*     */     }
/*     */ 
/* 287 */     if ((event.getEntity() instanceof Snowball))
/*     */     {
/* 289 */       final Snowball granate = (Snowball)event.getEntity();
/*     */ 
/* 292 */       Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 297 */           Waffen.this.playCircularEffect(granate.getLocation(), Effect.MOBSPAWNER_FLAMES, Boolean.valueOf(false));
/* 298 */           TNTPrimed tnt2 = (TNTPrimed)granate.getLocation().getWorld().spawn(granate.getLocation(), TNTPrimed.class);
/* 299 */           tnt2.setFuseTicks(0);
/*     */         }
/*     */       }
/*     */       , 60L);
/*     */ 
/* 305 */       playCircularEffect(granate.getLocation(), Effect.SMOKE, Boolean.valueOf(true));
/*     */ 
/* 307 */       Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 312 */           Waffen.this.playCircularEffect(granate.getLocation().add(0.0D, 1.0D, 0.0D), Effect.SMOKE, Boolean.valueOf(true));
/*     */         }
/*     */       }
/*     */       , 20L);
/*     */ 
/* 318 */       Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 323 */           Waffen.this.playCircularEffect(granate.getLocation().add(0.0D, 1.0D, 0.0D), Effect.SMOKE, Boolean.valueOf(true));
/*     */         }
/*     */       }
/*     */       , 40L);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void playCircularEffect(Location location, Effect effect, Boolean v)
/*     */   {
/* 333 */     for (int i = 0; i <= 8; i += ((!v.booleanValue()) && (i == 3) ? 2 : 1))
/*     */     {
/* 335 */       location.getWorld().playEffect(location, effect, i);
/*     */     }
/*     */   }

            public void setEffectsClass(Effect effects)
            {
            	effectsClass = effects;
            }
/*     */ }

/* Location:           C:\Users\HAPPYMAJOR\Desktop\jd-gui-0.3.5.windows\MyViCrime.jar
 * Qualified Name:     main.Waffen
 * JD-Core Version:    0.6.2
 */