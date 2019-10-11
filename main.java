/*     */ package myvcrime;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holograms.api.FloatingItem;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.CustomEntityType.Type;
import myvcrime.abilities.AbilityExecutor;
import myvcrime.abilities.AbilityItems;
import myvcrime.abilities.PrimaryAbilities;
import myvcrime.abilities.Superhuman;
import myvcrime.abilities.SuperhumanManager;
import myvcrime.abilities.UpgradeManager;
import myvcrime.chat.Msg;
import myvcrime.crime.CrimeTick;
import myvcrime.customGUI.IButton;
import myvcrime.customGUI.IGUI;
import myvcrime.customGUI.InventoryButton;
import myvcrime.customGUI.InventoryMenu;
import myvcrime.customGUI.LocatorButton;
import myvcrime.customGUI.WarpButton;
import myvcrime.customGUI.WarpSetterButton;
import myvcrime.listener.CustomEventInstances;
import myvcrime.listener.DrugEvents;
import myvcrime.listener.EventListener;
import myvcrime.listener.InventoryEvents;
import myvcrime.listener.Jobs;
import myvcrime.listener.MyVCrimeEventListener;
import myvcrime.listener.PlayerEvents;
import myvcrime.listener.RegionEvents;
import myvcrime.mobs.Chief;
import myvcrime.mobs.Security;
import myvcrime.pathfinding.Path;
import myvcrime.pathfinding.Pathfinder;
import myvcrime.quests.QuestManager;
import myvcrime.quests.QuestType;
import myvcrime.raid.RaidableShop;
import myvcrime.raid.ShopManager;
import myvcrime.raid.WaveRaid;
import myvcrime.world.RandomSpawnNMS;
import myvcrime.world.Spawner;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntitySkeletonWither;
import net.minecraft.server.v1_12_R1.World;

/*     */ 
/*     */ @SuppressWarnings("deprecation")
public class main extends JavaPlugin
/*     */   implements Listener
/*     */ {
			
/*  63 */  public ArrayList<String> voteListe = new ArrayList<String>();
		   public ArrayList<String> delayedShopSpawn = new ArrayList<String>();
           public ArrayList<String> alarmListe = new ArrayList<String>();
           public ArrayList<String> raubListe = new ArrayList<String>();
           public ArrayList<String> reedemCode = new ArrayList<String>();
           public ArrayList<String> playerOutOfZone = new ArrayList<String>();
           public HashMap<String,List<String>> deactivatedRaidBlocks = new HashMap<String,List<String>>();
           
           public static HashMap<String,Hologram> hologramList = new HashMap<String,Hologram>();
           public static HashMap<String, String> ab = new HashMap<String, String>();
           public static ArrayList<String> copList = new ArrayList<String>();
            
           public HashMap<String, Integer> demote = new HashMap<String, Integer>();
           public HashMap<String, Integer> timeLeft = new HashMap<String, Integer>();
/*  64 */  public HashMap<String, Integer> vote = new HashMap<String, Integer>();
           public HashMap<String, Integer> alarm = new HashMap<String, Integer>(); 
           
           public HashMap<String, Long> mcdonalds = new HashMap<String, Long>();
           public HashMap<String, Raid.Spawns> abDifficulty = new HashMap<String, Raid.Spawns>();
           public HashMap<String, Long> bank = new HashMap<String, Long>();
           public HashMap<String, Long> gasstation = new HashMap<String, Long>();
           public HashMap<String, Long> supermarket = new HashMap<String, Long>();
           public HashMap<String, Long> supermarkt1 = new HashMap<String, Long>();
           public HashMap<String, Long> supermarkt2 = new HashMap<String, Long>();
           public HashMap<String, Long> shopCooldownsMap = new HashMap<String, Long>();
           public HashMap<String, Long> tankstelle = new HashMap<String, Long>();
           public HashMap<String, Long> waffenshop = new HashMap<String, Long>();
           public HashMap<String, Long> schmiede = new HashMap<String, Long>();
           public HashMap<String, Long> mcLarens = new HashMap<String, Long>();
           public HashMap<String, String> abReason = new HashMap<String, String>();
           public HashMap<String, Integer> shopWave = new HashMap<String, Integer>();
           public HashMap<String, Integer> playerCrimeCooldown = new HashMap<String, Integer>();
           public HashMap<String, List<EntityLiving>> waveEntities = new HashMap<String,List<EntityLiving>>();
           
           
           public ArrayList<String> raidMarker = new ArrayList<String>();
           public HashMap<String, Raid.Spawns> raidMarkerSpawn = new HashMap<String, Raid.Spawns>(); 
           public HashMap<String, String> raidMarkerShop = new HashMap<String, String>();
           public HashMap<String, Integer> raidMarkerWave = new HashMap<String, Integer>();
           public HashMap<String, String> currentRegion = new HashMap<String, String>();
           public HashMap<String, String> spielerRaub = new HashMap<String, String>();
           public HashMap<String, Integer> StringIntegerMap = new HashMap<String, Integer>();
           public ArrayList<String> deactivatedBlocks = new ArrayList<String>();
           public HashMap<String,Material> deactivatedMaterial = new HashMap<String,Material>();
           public static HashMap<String,FloatingItem> floatingItemList = new HashMap<String,FloatingItem>();
           
           public static String[] blocks = {Material.BEDROCK.toString(),Material.COAL_ORE.toString(), Material.GOLD_ORE.toString(), Material.DIAMOND_ORE.toString(), Material.IRON_ORE.toString()};
           public static Material[] blocksMaterial = {Material.BEDROCK,Material.COAL_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE, Material.IRON_ORE};
           List<Location> defaultList = new ArrayList<Location>();
/*     */ 
			public ShopManager shopManager = new ShopManager(this);
           	public CrimeTick crimeTick;
/*  66 */   public String[] waffenName = new String[4];
/*  67 */   public String[] gesetze = new String[3];
            public static String[] shops = {"minebugs","tanke","bank","bank2","kiosk","kiosk1","kiosk2","kiosk2","kiosk3","kiosk4","kiosk5","kiosk6","kiosk7","kiosk8","kiosk9","kiosk10","gunshop","gunshop2","gunshop3","steakhouse","blockshop2","blockshop","blockshop3","coffeeshop","arenashop","arenashop1","arenashop2","eshop","superfries","truck","swordshop","swordshop1","swordshop2","mineshop","thiefshop","thiefshop1","thiefshop2","armorshop","baecker","diashop","xplosive","sanjisshop"};
            public static String[] waveShops = {};
            public String[] bars = {"mclarens","bar","bar1","bar2",};

            Waffen waffenClass = new Waffen(this);
/*     */ 
            public int taskID;
/*  69 */   int i = 1;
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            
            public static Plugin plugin;
            public QuestManager questManager = new QuestManager(this);
            public SuperhumanManager superhumanManager = new SuperhumanManager();
            public GUI gui = new GUI(this);
            public CustomItems customItems = new CustomItems(this);
            public Raid raidClass = new Raid(this);
            public Groupmanager groupManager = new Groupmanager(this);
            public Utility utility = new Utility(this);
            public CustomBlocks customBlocks = new CustomBlocks(this);
            public LootChest lootChest = new LootChest(this);
            public ShopBuilder shopBuilder = new ShopBuilder(this);
            public SkinForcer skinForcer = new SkinForcer(this);
            private CustomEventInstances customEventInstances = new CustomEventInstances(this);
            public Bars barsInstance;
            public static int playerCount;
            
            boolean schleifeAktiv = false;
/*  70 */   boolean voteIsOn = false;
/*  71 */   boolean mayor = false;
/*     */ 	public static HolographicDisplaysAPI api;
/*     */   public void onEnable()
/*     */   {
			  main.plugin = this;
/*  26 */     PluginManager pm = getServer().getPluginManager();
			  pm.registerEvents(superhumanManager, this);
/*  27 */     pm.registerEvents(new EventListener(this), this);
/*  28 */     pm.registerEvents(waffenClass, this);
/*  29 */     pm.registerEvents(new Jobs(this), this);
/*  30 */     pm.registerEvents(new SpielerProfil(this), this);
/*  32 */     pm.registerEvents(this, this);
			  pm.registerEvents(new CitizenEvents(this), this);
			  pm.registerEvents(new Tools(this), this);
			  pm.registerEvents(new PlayerEvents(this,customEventInstances), this);
			  pm.registerEvents(new RegionEvents(this,customEventInstances,shopManager), this);
			  pm.registerEvents(new MyVCrimeEventListener(this), this);
			  pm.registerEvents(new DrugEvents(this), this);
			  pm.registerEvents(new InventoryEvents(this), this);	  
			  
			  if(this.setupEconomy()){
				  System.out.println("ECONOMY SETUP COMPLETE!");
			  } else {
				  System.out.println("ECONOMY SETUP FAILED!");
			  }			  			  
	
		       getLogger().info("Holographic Displays enabled");
		         if(getServer().getPluginManager().getPlugin("HolographicDisplays") == null || getServer().getPluginManager().getPlugin("HolographicDisplays").isEnabled() == false) {
		            getLogger().log(java.util.logging.Level.SEVERE, "ERROR: HolographicDisplays NOT LOADED!");
		            getServer().getPluginManager().disablePlugin(this);
		            return;
		        }
		        	        
		      crimeTick = new CrimeTick(this);
	          barsInstance = new Bars(this);
	          CustomEntityType.registerEntity(Type.ZOMBIE, Chief.class, false);
	          CustomEntityType.registerEntity(Type.ZOMBIE, Security.class, false);
	      
			  CustomEntityType.registerEntity(Type.ZOMBIE, CustomEntityZombie.class, false);
/*     */ 	  CustomEntityType.registerEntity(Type.WOLF, CustomEntityWolf.class, false);
			  CustomEntityType.addRandomSpawn(new CustomEntityType.SpawnData(CustomEntityZombie.class, 1000, 2, 5),CustomEntityType.MobMeta.MONSTER, CustomEntityType.Biome.COLLECTION_ALL);
/*  34 */     reloadConfig();
/*  35 */     getConfig().options().header("SpielerProfile");
			  getConfig().addDefault("Holograms.stands", new ArrayList<String>());
/*  36 */     getConfig().addDefault("MyViCrime.Waffen.Holzaxt.Schaden", Double.valueOf(5.0D));
/*  37 */     getConfig().addDefault("MyViCrime.Waffen.Steinaxt.Schaden", Double.valueOf(8.0D));
/*  38 */     getConfig().addDefault("MyViCrime.Waffen.Eisenaxt.Schaden", Double.valueOf(4.0D));
/*  39 */     getConfig().addDefault("MyViCrime.Waffen.Goldaxt.Schaden", Double.valueOf(12.0D));
              getConfig().addDefault("MyViCrime.AktiveBloecke.Mining." + Material.GOLD_ORE.toString(), defaultList);
              getConfig().addDefault("MyViCrime.AktiveBloecke.Mining." + Material.IRON_ORE.toString(), defaultList);
              getConfig().addDefault("MyViCrime.AktiveBloecke.Mining." + Material.DIAMOND_ORE.toString(), defaultList);
              getConfig().addDefault("MyViCrime.AktiveBloecke.Mining." + Material.COAL_ORE.toString(), defaultList);
              getConfig().addDefault("MyViCrime.AktiveBloecke.raid." + Material.BEDROCK.toString(), defaultList);
              getConfig().addDefault("MyViCrime.AktiveBloecke.juwelier." + Material.BEDROCK.toString(), defaultList);
/*     */ 
/*  44 */     getConfig().options().copyDefaults(true);
/*  45 */     saveConfig();
/*  46 */     System.out.println("[MyViCrime] Config geladen!");
/*  48 */     this.waffenName[0] = "lmg";
/*  49 */     this.waffenName[1] = "p99";
/*  50 */     this.waffenName[2] = "minigun";
/*  51 */     this.waffenName[3] = "magnum";
/*     */ 
/*  53 */     this.gesetze[0] = "BetÃ¤ubungsmittelgesetz";
/*  54 */     this.gesetze[1] = "Waffengesetz";
/*  55 */     this.gesetze[2] = "Steuern";
			  RandomSpawnNMS spawner = RandomSpawnNMS.getInstance(plugin, Bukkit.getWorld("Map"), ((CraftWorld)Bukkit.getWorld("Map")).getHandle());
			  spawner.createRandomSpawnInRegion(EntitySkeletonWither.class, "mountain", 50);
			  
			  this.shopManager.loadRaidableShops();
			  this.shopManager.loadShops();
	          this.loadDeactivatedActiveBlocks(shops);
	          this.setupHologramAPI();
	          Utility.loadHolograms();	      
			  shopBuilder.loadShops();	
			  UpgradeManager.loadFromConfig();
/*     */   }
/*     */ 
/*     */   public void onDisable()
/*     */   {
				plugin.saveConfig();
				UpgradeManager.saveToConfig();
/*     */   }
			public void setupHologramAPI(){
				api = new HolographicDisplaysAPI();
				System.out.println("[MYVCRIME] HolographicDisplaysAPI geladen!");
			}
			
			public static Plugin getPlugin(){
				return plugin;
			}

/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
			  if(cmd.getName().equalsIgnoreCase("kit")){
				  Player p = (Player) sender;
				  if(args.length == 1){
					  if(args[0].equalsIgnoreCase("map")){
						  if(SpielerProfil.canKit(p.getName(), KitType.MAP)){
							  SpielerProfil.giveKit(p.getName(), KitType.MAP);
						  }
					  }
					  if(args[0].equalsIgnoreCase("compass")){
						  if(SpielerProfil.canKit(p.getName(), KitType.COMPASS))
							  SpielerProfil.giveKit(p.getName(), KitType.COMPASS);
					  }
					  if(args[0].equalsIgnoreCase("lock")){
						  if(SpielerProfil.canKit(p.getName(), KitType.LOCK))
							  SpielerProfil.giveKit(p.getName(), KitType.LOCK);
					  }
					  if(args[0].equalsIgnoreCase("chest")){
						  if(SpielerProfil.canKit(p.getName(), KitType.CHEST))
							  SpielerProfil.giveKit(p.getName(), KitType.CHEST);
					  }
				  }
			  }
	
			  
			  
              if(cmd.getName().equalsIgnoreCase("ab"))
              {
            	  Player p = (Player)sender;
            	  if(p.isOp() == true)
            	  {
            		  if(ab.containsKey(p.getName()))
            		  {
            			  p.sendMessage(ChatColor.RED + "Markierungsmodus ausgeschaltet.");
            			  ab.remove(p.getName());
            			  return true;
            		  }
            		  if(args.length == 2)
            		  {
            			  p.sendMessage(args[1]);
            			  for(String s : blocks)
            			  {
            				  if(s.equalsIgnoreCase(args[0]))
            				  {
            					  if(!ab.containsKey(p.getName()))
            					  {
            						  p.sendMessage(ChatColor.GREEN + "Markierungsmodus eingeschaltet.");
            						  ab.put(p.getName(), s);
            						  abReason.put(p.getName(),args[1]);
            						  if(args.length == 4){
            							  for(Raid.Spawns spawns : Raid.Spawns.getTypes()){
            								  p.sendMessage(spawns.getName() + ":::" + args[2]);
            								  if(spawns.getName().equalsIgnoreCase(args[2])){
                    							  abDifficulty.put(p.getName(),spawns);
                    							  p.sendMessage("YO!");
            								  }
            							  }
            						  }
            						  return true;
            					  } else {
            						  return false;
            					  }
            				  }
            			  }          			  
            		  }
            		  if(args.length == 3){
            			  for(String s : this.shops){
            				  p.sendMessage(s + ":::" + args[0]);
            				  if(args[0].equalsIgnoreCase(s)){
            					  Character wave = args[1].charAt(0);
            					  int w = Character.getNumericValue(wave);
            					  p.sendMessage("VALUE" + w);
            					  this.raidMarker.add(p.getName());
            					  this.raidMarkerShop.put(p.getName(), args[0]);
            					  this.raidMarkerWave.put(p.getName(), w);
            					  for(Raid.Spawns spawn : Raid.Spawns.getTypes()){
            						  if(spawn.getName().equalsIgnoreCase(args[2])){
                    					  this.raidMarkerSpawn.put(p.getName(), spawn);
            						  }
            					  }
            					  
            				  } else {
            					  p.sendMessage("Dieser shop existiert nicht!");
            				  }
            			  }
            		  }
            	  }
              }

              if(cmd.getName().equalsIgnoreCase("Hilfe"))
              {
            	  Player p = (Player) sender;
            	  p.sendMessage(ChatColor.GRAY + "Du bist ein neuer Spieler und weißt nicht was zu tun ist? Dann gebe /Guide ein.");
            	  p.sendMessage(ChatColor.GRAY + "Du willst wissen, auf welche Arten sich gutes " + ChatColor.YELLOW + "Geld" +ChatColor.YELLOW+" verdienen lässt? /Moneyboy");
              }
              
              if(cmd.getName().equalsIgnoreCase("raid")){
              }
              
              if(cmd.getName().equalsIgnoreCase("Guide"))
              {
            	  Player p = (Player) sender;
            	  p.sendMessage(ChatColor.YELLOW + "[Erste Schritte]");
            	  p.sendMessage(ChatColor.GRAY + "1. Verkaufe deine Startnuggets bei der Bank gegenüber des Rathauses.");
            	  p.sendMessage(ChatColor.GRAY + "2. Suche dir ein Beruf mit /Jobs aus.");
            	  p.sendMessage(ChatColor.GRAY + "3. Kaufe dir eine Waffe beim Waffenshop.");
            	  p.sendMessage(ChatColor.GRAY + "4. Verdiene dir auf deiner Weise Geld :)");
            	  p.sendMessage(ChatColor.GRAY + "5. Miete dir eine Wohnung, sobald du genug Geld dafür hast.");
            	  p.sendMessage(ChatColor.GRAY + "6. Gründe oder trete einer Gruppe bei.");
            	  p.sendMessage(ChatColor.GRAY + "7. Zeige den anderen Gangstern wer der Boss ist!");
            	  
              }
              
              if(cmd.getName().equalsIgnoreCase("Moneyboy"))
              {
            	  Player p = (Player) sender;
            	  p.sendMessage(ChatColor.YELLOW + "[Möglichkeiten um Geld zu machen]");
            	  p.sendMessage(ChatColor.GRAY + "1. Sammel Pfandflaschen aus Muelleimern und gebe sie anschließend in einem Laden ab.");
            	  p.sendMessage(ChatColor.GRAY + "2. Raube ein paar Geschäfte aus, doch Vorsicht, du erhältst Crime und benötigst eine Waffe.");
            	  p.sendMessage(ChatColor.GRAY + "3. Gehe ins Bergwerk und ackere dich für eine geringe Bezahlung ab.");
            	  p.sendMessage(ChatColor.GRAY + "4. Schaue dir die Jobs und die damit verbundenen Möglichkeiten, an Geld zu kommen, an.");
            	  p.sendMessage(ChatColor.GRAY + "5. Exekutiere Banditen, die ein hohes Kopfgeld besitzen. Nutze einen Kompass, welcher dir die Position eines Spielers mit hohem Kopfgeld verrät.");
            	  p.sendMessage(ChatColor.GRAY + "6. Hol dir ein paar Dudes mit denen du die Bank ausraubst.");
            	  p.sendMessage(ChatColor.GRAY + "7. Schleime dich bei den Admins ein und erzaehl allen davon, wie cool der Server ist :). (Natürlich gibt es hierfuer KEIN Geld, tut uns leid :)");
            	  p.sendMessage(ChatColor.GRAY + "8. Suche Leute, die Geld haben und frage sie höflich, ob sie es dir aushändigen könnten :).");
              }
              

              if (cmd.getName().equalsIgnoreCase("Level"))
              {
            	  Player p = (Player) sender;
            	  
            	  int crimeLevel = SpielerProfil.getCrimeLevel(p.getName());
            	  
            	  int crimeExp = SpielerProfil.getCrimeExp(p.getName());
            	  
            	  p.sendMessage(ChatColor.BLACK + "Crime Level: " + ChatColor.GREEN + crimeLevel + ChatColor.BLACK + "  Exp: " + ChatColor.GREEN + crimeExp);
            	  
              }
              
              if(cmd.getName().equalsIgnoreCase("target"))
              {
            	  Player p = (Player) sender;
              }
/*     */ 
/* 359 */     if (cmd.getName().equalsIgnoreCase("Verbrechen"))
/*     */     {
/* 361 */       Player p = (Player)sender;
/* 363 */       for (String s : getConfig().getStringList("MyViCrime.Strafregister." + p.getName()))
/*     */       {
/* 365 */         if (!(s.equalsIgnoreCase("default")))
/*     */         {
/* 367 */           p.sendMessage(ChatColor.RED + s);
/*     */         }
/*     */       }
/*     */     }

			  if(cmd.getName().equalsIgnoreCase("shop")){
				  
				  Player p = (Player) sender;
				  
				  if(p.isOp() == false){
					  return true;
				  }
				  
        		  if(args.length == 4){
        			  Location loc = p.getTargetBlock(null, 10).getLocation();
        			  String ids = args[0];
        			  int id = 1;
        			  int state = 0;
        			  if(ids.contains(":")){
        				 String[] arry = ids.split(":");
        				 if(StringUtils.isNumeric(arry[0])){
            				 id = Integer.parseInt(arry[0]);
            				 state = Integer.parseInt(arry[1]);
        				 }
        			  } else {
        				  if(StringUtils.isNumeric(ids)) id = Integer.parseInt(ids); 
        			  }
        			  
          			  String type = args[1];
        			  String amounts = args[2];
        			  int amount = Integer.parseInt(amounts);
        			  String prices = args[3];
        			  Double price = Double.parseDouble(prices);
        			  
        			  if(StringUtils.isNumeric(ids) || state != 0){
            			  ItemStack is = new ItemStack(Material.getMaterial(id),amount);
            			  if(state != 0 && is.getType() != Material.POTION){
            				  is.setData(new MaterialData(state));
            			  } else {
            				  if(state != 0){
            					  p.sendMessage("potion");
            					  is = new ItemStack(Material.POTION,amount,(short)state);
            				  }
            			  }
            			  shopBuilder.placeShop(loc, is, price, type);
        			  } else {   			  
        				  if(Material.matchMaterial(ids) != null){
        					  ItemStack is = new ItemStack(Material.matchMaterial(ids),amount);
        					  shopBuilder.placeShop(loc, is, price, type);
        				  } else {
        					  Msg.ALERT.out(p, ids + " exisitert nicht!");
        				  }
        			  }
        		  }
        		  if(args.length == 5){
        			  
        			  Location loc = p.getTargetBlock(null, 10).getLocation();
        			  String ids = args[0];        			  
        			  int id;
        			  int state = 0;
        			  if(ids.contains(":")){
        				 String[] arry = ids.split(":");
        				 id = Integer.parseInt(arry[0]);
        				 state = Integer.parseInt(arry[1]);
        			  } else {
            			 id = Integer.parseInt(ids);  
        			  }
        			  String type = args[1];
        			  String amounts = args[2];
        			  int amount = Integer.parseInt(amounts);
        			  String prices = args[3];
        			  Double price = Double.parseDouble(prices);
        			  String customName = args[4];
        			  if(customName.contains("_")){
        				  customName = customName.replaceAll("_", " ");
        			  }
        			  ItemStack is = new ItemStack(Material.getMaterial(id),amount);
        			  if(state != 0){
        				  is.setData(new MaterialData(state));
        			  }
        			  shopBuilder.placeShop(loc, is, price, type,customName);
        		  }
        		  if(args.length == 1){
        			  if(args[0].equalsIgnoreCase("remove")){
        				  Location loc = p.getTargetBlock(null, 10).getLocation();
        				  shopBuilder.removeShop(loc);
        				  return true;
        			  }
        		  }
        		  
        		  if(args.length != 5 && args.length != 4){
        			  p.sendMessage(ChatColor.RED + "Falscher Befehl: /shop ID BUY/SELL ANZAHL PREIS (Optional:) NAME");
        		  }
			  }
			  if(cmd.getName().equalsIgnoreCase("spawn")){
				  Player p = (Player) sender;
				  if(args.length == 1){
					  if(args[0].equalsIgnoreCase("Rathaus"))
					  p.teleport(SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
					  
					  if(args[0].equalsIgnoreCase("Prison_indoor"))
						  p.teleport(SpielerProfil.Locations.LOCATION_PRISON.getLocation());
					  if(args[0].equalsIgnoreCase("Prison"))
						  p.teleport(SpielerProfil.Locations.LOCATION_PRISON_DOOR.getLocation());
					  if(args[0].equalsIgnoreCase("mineshop"))
						  p.teleport(SpielerProfil.Locations.LOCATION_MINE_SHOP.getLocation());
					  if(args[0].equalsIgnoreCase("list"))
						  p.sendMessage("Prison,Prison_indoor,Rathaus,mineshop");
				  } else {
					  p.teleport(SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
				  }
			  }
              if(cmd.getName().equalsIgnoreCase("test")){
            	  Player p = null;
            	  if(sender instanceof Player){
            		  p = (Player) sender;           
            	  }
            	  
            	  
            	  if(args.length == 1){
            		  final Player pp = (Player) sender;
            		  if(args[0].equalsIgnoreCase("wither")){
            			  ItemStack potion = new ItemStack(Material.POTION,1,(short) 8194);
            			  p.getInventory().addItem(potion);
            			  p.sendMessage("here a potion");
            		  }
            		  if(args[0].equalsIgnoreCase("prison")){
            			  Bukkit.broadcastMessage("PRSIONER:" + SpielerProfil.prisoner.contains(p.getName()));
            			  Bukkit.broadcastMessage("FLEEING PRISONER:" + SpielerProfil.isFleeingPrisoner(p.getName()));
            		  }
            		  if(args[0].equalsIgnoreCase("tagClear")){
            			  Set<String> tags = p.getScoreboardTags();
            			  List<String> list = new ArrayList<String>();
            			  list.addAll(tags);
            			  for(String tag : list){
            				  p.removeScoreboardTag(tag);
            			  }
            			  
            		  }
            		  if(args[0].equalsIgnoreCase("shop")){
            			  RaidableShop shop = new RaidableShop("bank","bank",Bukkit.getWorld("Map"));
            			  
            			  shop.setName("bank");
            			  World nmsWorld = ((CraftWorld)Bukkit.getWorld("Map")).getHandle();
            			  shop.addEntityToWave(0,Chief.class, 3);
            			  shop.addEntityToWave(1,Security.class, 1);
            			  shop.addEntityToWave(2,Security.class, 2);
            			  ArrayList<Location> list = new ArrayList<Location>();
            			  list.add(p.getLocation());
            			  shop.addWaveSpawnLocations(0, 0, list);
            			  
            			  shopManager.saveToConfig(shop);
            			  shopManager.loadRaidableShops();
            		  }
            		  if(args[0].equalsIgnoreCase("rob")){
            			  RaidableShop shop = shopManager.getRaidableShop("bank");
            			  Bukkit.broadcastMessage("----ROB COMMAND ----");
            			  if(shop.getEntityList(0) == null) Bukkit.broadcastMessage("LIST IS NULL");
            			  if(shop.getEntityList(0).isEmpty()) Bukkit.broadcastMessage("LIST IS EMPTY");
            			  WaveRaid raid = new WaveRaid(shop,p);
            			  raid.start();
            		  }
            		  if(args[0].equalsIgnoreCase("spawn")){
            			  Spawner spawner = new Spawner(Bukkit.getWorld("Map"));
            			  try {
							spawner.spawn(Chief.class, p.getLocation());
						} catch (NoSuchMethodException | SecurityException | InstantiationException
								| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		  }
            		  if(args[0].equalsIgnoreCase("t")){
            			  Bukkit.broadcastMessage("test");
            			  questManager.addQuest(QuestType.TUT1, pp);
            		  }
            		  
            		  
            		  
            		  if(args[0].equalsIgnoreCase("cast")){
            			  AbilityExecutor abilityExecutor = new AbilityExecutor();
            			  abilityExecutor.executeAbility(pp, PrimaryAbilities.FIRE_KEGEL);
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("up")){
            			  Superhuman superhuman = SuperhumanManager.getSuperhuman(pp);
            			  superhuman.setLevel(1);
            			  InventoryMenu menu = UpgradeManager.getUpgradeInventory(superhuman);
            			  menu.open(pp);
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("upgrade")){
            			  Superhuman superhuman = SuperhumanManager.getSuperhuman(pp);
            			  superhuman.setLevel(1);
            			  UpgradeManager.setUpgrade(superhuman, AbilityItems.BAISY_UPGRADE_LV1);
            			  Bukkit.broadcastMessage("sHAHA");
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("lold")){
            		
            			  SpielerProfil.loadWarpsFromConfig(pp);
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("tt")){
            			  questManager.addQuest(QuestType.TUT2, pp);
            		  }
            		  if(args[0].equalsIgnoreCase("ttt")){
            			  questManager.addQuest(QuestType.TUT3, pp);
            		  }
            		  if(args[0].equalsIgnoreCase("tttt")){
            			  questManager.addQuest(QuestType.TUT4, pp);
            		  }
            		  if(args[0].equalsIgnoreCase("ttttt")){
            		  }
            		  if(args[0].equalsIgnoreCase("lo")){
            			  UpgradeManager.loadFromConfig();
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("sa")){
            			  UpgradeManager.saveToConfig();
            		  }
            		  
            		  if(args[0].equalsIgnoreCase("menu")){
            			  EntityPlayer ep = ((CraftPlayer )pp).getHandle();
            			  EntityHuman human = (EntityHuman) ep;
            			  IGUI igui = new InventoryMenu(human);
            			  IGUI igui2 = new InventoryMenu(human);
            			  IGUI igui3 = new InventoryMenu(human);
            			  
            			  ItemStack is = new ItemStack(Material.ARROW);
            			  IButton button = new InventoryButton(is);
            			  
            			  ItemStack is2 = new ItemStack(Material.ANVIL);
            			  IButton button2 = new InventoryButton(is2);
            			  
            			  ItemStack is3 = new ItemStack(Material.APPLE);
            			  IButton button3 = new InventoryButton(is3);
            			  
            			  igui.addButton(button);
            			  igui2.addButton(button2);
            			  igui3.addButton(button3);
            			  igui.addPage(igui2);
            			  igui.addPage(igui3);
            			  pp.openInventory(igui.getInventory());
            		  }
            		  if(args[0].equalsIgnoreCase("hasQuest")){
            			  QuestManager manager = new QuestManager(this);
            			  for(QuestType type : manager.getCurrentQuests(pp)){
            				  Bukkit.broadcastMessage("quests:" + type.toString());
            			  }
            		  }
            		  if(args[0].equalsIgnoreCase("path")){
            				
            			  Location start = pp.getLocation().add(0, -1, 0);
            			  Location finish = SpielerProfil.Locations.LOCATION_PRISON_DOOR.getLocation();
            			  
            				Path path = new Pathfinder(start, finish).calculate();
            				//Where start and finish are locations
            				new BukkitRunnable(){
            					int counter = 0;
								@Override
								public void run() {
									if(counter >= path.getNodes().size()){
										this.cancel();
										return;
									}
									
									Location loc = path.getNodes().get(counter).toLocation(pp.getWorld());
									pp.getWorld().playEffect(loc, Effect.WITCH_MAGIC, 10);
									pp.getWorld().playEffect(loc, Effect.LAVA_POP, 10);
									pp.getWorld().playEffect(loc, Effect.HEART, 10);
									
									counter++;
								}
            					
            				}.runTaskTimer(this, 20L, 10L);
            		  }
            	  }
            	  if(args.length == 2){
            		  if(args[1].equalsIgnoreCase("normal")){
            			  String shopName = args[0];
            			  RaidableShop newShop = new RaidableShop(shopName,shopName, p.getWorld());
            			  newShop.setName(shopName);
            			  newShop.addEntityToWave(0, Security.class, 3);
            			  newShop.addEntityToWave(1, Chief.class, 1);
            			  newShop.addEntityToWave(1, Security.class, 3);
            			  newShop.addEntityToWave(2, Chief.class, 2);
            			  newShop.addEntityToWave(2, Security.class, 5);
            			  this.shopManager.saveToConfig(newShop);
            			  this.shopManager.loadRaidableShops();
            			  p.sendMessage("shop erstellt");
            		  }
            		  if(args[1].equalsIgnoreCase("hard")){
            			  String shopName = args[0];
            			  RaidableShop newShop = new RaidableShop(shopName,shopName, p.getWorld());
            			  newShop.addEntityToWave(0, Security.class, 6);
            			  newShop.addEntityToWave(0, Chief.class, 3);
            			  newShop.addEntityToWave(1, Security.class, 12);
            			  newShop.addEntityToWave(1, Chief.class, 6);
            			  newShop.addEntityToWave(2, Chief.class, 15);
            			  newShop.addEntityToWave(2, Security.class, 3);
            			  this.shopManager.saveToConfig(newShop);
            		  }
            	  }
              }
			return true;
              }

              public WorldEditPlugin getWorldEdit() 
              {
            	  Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
            	  if(p instanceof WorldEditPlugin) 
            	  {
        	    	  return (WorldEditPlugin) p;
        	      } else {
            	      return null;
        	      }       	        	          	   
              }
                 @EventHandler
                 public void onRegionEnter(RegionEnterEvent e)
                 { 
                	/*
                	 * DEPRECATED!!!!
                	 */
                	 
                	 Player p = e.getPlayer();               	 
                	 
                	 if(p instanceof Player)
                		 return;
                	 
                	 if(p.hasMetadata("NPC"))
                		 return;
                	 raidClass.zoneOfPlayer.put(p.getName(), e.getRegion().getId());
                	 if(raidClass.isPlayerRobbing(p)){
                		 return;
                	 }
                	 boolean waffeInDerHand = SpielerProfil.weaponInHand(p.getName());
                	 
            		 if(!(waffeInDerHand == true)) 
            		 {
            			 return;
            		 }
            		 if(barsInstance.isBar(e.getRegion().getId())){
            			 barsInstance.attemptToRaid(p, e.getRegion().getId());
            		 }
            		 
                	 boolean stop = true;
                	 for(String name : shops)
                		 if(name.equalsIgnoreCase(e.getRegion().getId())) stop = false;

                	 if(stop) return;
                	 	
                	 		
                		 if(getRobCooldown(e.getRegion().getId(), e.getPlayer().getName()) + 30000 > System.currentTimeMillis()) 
                		 {
                			 e.getPlayer().sendMessage(ChatColor.GRAY + "Der Laden wurde erst vor kurzem ausgeraubt!");           
                			 return;
                		 }
                		 if(!(waffeInDerHand == true)) 
                		 {
                			 return;
                		 }
                	
                		 
                		 if(!(alarm.containsKey(p.getName()))) 
                		 {
                			 
                			 /** 
                			  *  Check if this shop is already being attempted to rob
                			  *  START
                			  */
                			 
                			 int counter = 0;
                			 String shopName = e.getRegion().getId();
                			 boolean alreadyBeingRobbed = false;
                			 for(int i = 0; i < alarmListe.size(); i++){
                				 String key = alarmListe.get(i);
                				 if(spielerRaub.containsKey(key)){
                					 if(spielerRaub.get(key).equalsIgnoreCase(shopName)) counter++;
                				 }
                				 if(counter >= 1){
                					 alreadyBeingRobbed = true;
                					 break;
                				 }
                				 alreadyBeingRobbed = false;
                			 }
                			 if(alreadyBeingRobbed){
                				 p.sendMessage(ChatColor.RED + "Jemand versucht schon diesen Shop auszurauben!");
                				 return;
                			 }
                			 
                			 /**
                			  * END
                			  */
                			 
                			 /*
                			  * Check if this shop is already in raid
                			  */
                			 
                			 if(raidClass.ShopisInRaid(shopName)){
                				 p.sendMessage(ChatColor.RED + "Dieser Shop wird schon ausgeraubt!");
                				 return;
                			 }
                			 
                			 /*
                			  * End
                			  */
                			 
                			 spielerRaub.put(e.getPlayer().getName(), e.getRegion().getId());
                			 alarmListe.add(p.getName());
                    		 alarm.put(p.getName(), 13);
                    		 Utility.sendTitle(p, ChatColor.RED + "Warnung", "Du bist dabei einen Überfall zu begehen!");
                		 } 
                		 else 
                		 {
                			 p.sendMessage("Return");
                			 return;
                		 }
                		 
                		 if(!(schleifeAktiv == true)) 
                		 {
                			 this.taskID = this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() 
                    		 {
                    			@Override
    							public void run() 
    							{
                    				if((alarmListe.isEmpty() == true) && (raubListe.isEmpty() == true)) 
                    		    {
                    					main.this.stopScheduler();
                    					schleifeAktiv = false;
                    			}                    		    
                    				       
    							for(Iterator<String> i2 = alarmListe.iterator(); i2.hasNext();) 
    							{
    								String key = i2.next();
    								
    								Player p2 = Bukkit.getPlayer(key);																
    								
    								if(alarm.containsKey(key))
    								{
    									int counter = alarm.get(key);
    									counter--; 
    									if(alarm.containsKey(key))
    									{
    										alarm.put(key, counter);
    									}
    									if(counter<= 10){
    										Utility.sendTitle(p2, ChatColor.RED + "" + counter, "");
    									}
    									if(counter == 0) 
    									{
    										i2.remove();
    										alarm.remove(key); 
    									
    										raubListe.add(key);
    										timeLeft.put(key, 0);
    									
    										String currentLaden = spielerRaub.get(key);
    									
    										raidClass.startRaid(currentLaden, Bukkit.getPlayer(key));
    									
    				   									
    									
    										SpielerProfil.addCrimeExp(p2.getName(), 1);
    									}
    								}else{
    									i2.remove();
    									alarm.remove(key);
    									spielerRaub.remove(key);
    								}
       								if(SpielerProfil.weaponInHand(key) == false && alarm.containsKey(key)) 
    								{
    									Bukkit.broadcastMessage("NO WEAPON END RAID");
    									i2.remove();
    									alarm.remove(key);
    									spielerRaub.remove(key);
    								} 
    							}
                    				for(Iterator<String> i3 = raubListe.iterator(); i3.hasNext();){ 
                    					String key2 = i3.next();
                    					Player p3 = Bukkit.getPlayer(key2);
                    					
                    					if(spielerRaub.containsKey(key2)){
                    						String shop = spielerRaub.get(key2);
                    						if(waveEntities.containsKey(shop)){
                    							
                    						} else {
                    							if(delayedShopSpawn.contains(shop)){
                    							} else {
                    							if(shop.equalsIgnoreCase("bank")){
                    								if(shopWave.containsKey(key2))
                    								{
                    									if(shopWave.get(key2) == 3){
                    										p3.sendMessage("ALLE WAVES ERLEDIGT!");
                    										for(int i = 0; i < 15; i++){
                    											p3.updateInventory();
                        										p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
                    										}
                    										i3.remove();
                    										spielerRaub.remove(key2);
                    										timeLeft.remove(key2);
                    										shopWave.remove(key2);
                    									}
                    									if(shopWave.containsKey(key2)){
                    										if(shopWave.get(key2) == 2){
                    											for(int i = 0; i < 10; i++){
                    												p3.updateInventory();
                    												p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
                    											}
                    											shopWave.put(key2, 3);
                    											raidClass.startNextWave(shop, p3);
                    											p3.sendMessage("Wave 2 erledigt");
                    										}
                    										if(shopWave.get(key2) == 1){
                    											for(int i = 0; i < 5; i++){
                    												p3.updateInventory();
                    												p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
                    											}
                    											p3.sendMessage("Wave 1 erledigt");
                    											shopWave.put(key2, 2);
                    											raidClass.startNextWave(shop, p3);
                    										}
                    									}
                    									}
                    							   }
                    						   }                    						
                    							  for(String s : shops){
                    								  if(shop.equalsIgnoreCase(s)){
                    									  handleWave(key2,i3,p3,shop);
                    								  }
                    							  }
                    						}
                    					}
                    
                    				}
    							}
                    			 
                    		 }, 20L, 20L);
                			 schleifeAktiv = true;
                		 }
                 }
                 
                 public void handleWave(String key2, Iterator<String> i3, Player p3, String shop){
						if(shopWave.containsKey(key2))
						{
							if(shopWave.get(key2) == 3){
								Utility.sendTitle(p3, ChatColor.GREEN + "Alle Waves erledigt!", "well done :)");
								for(int i = 0; i < 15; i++){
									p3.updateInventory();
									p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
								}
								i3.remove();
								spielerRaub.remove(key2);
								timeLeft.remove(key2);
								shopWave.remove(key2);
								raubListe.remove(key2);
							}
							if(shopWave.containsKey(key2)){
								if(shopWave.get(key2) == 2){
									for(int i = 0; i < 10; i++){
										p3.updateInventory();
										p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
									}
									shopWave.put(key2, 3);
									if(raidClass.startNextWave(shop, p3) == false){
										i3.remove();
										spielerRaub.remove(key2);
										timeLeft.remove(key2);
										shopWave.remove(key2);
										raubListe.remove(key2);
										Utility.sendTitle(p3, ChatColor.GREEN + "Alle waves erledigt", "");
									}
									Utility.sendTitle(p3, ChatColor.GREEN + "Wave 2 erledigt!", "");
								}
								if(shopWave.get(key2) == 1){
									for(int i = 0; i < 5; i++){
										p3.updateInventory();
										p3.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));	
									}
									Utility.sendTitle(p3, ChatColor.GREEN + "Wave 1 erledigt!", "");
									shopWave.put(key2, 2);
									if(raidClass.startNextWave(shop, p3) == false){
										i3.remove();
										spielerRaub.remove(key2);
										timeLeft.remove(key2);
										shopWave.remove(key2);
										raubListe.remove(key2);
										Utility.sendTitle(p3, ChatColor.GREEN + "Alle waves erledigt", "");
									}
								}
							}
							}
                 }
                               
                 @EventHandler
                 public void onRegionLeave(RegionLeaveEvent e)
                 {
                	 if(raidClass.zoneOfPlayer.containsKey(e.getPlayer().getName()))
                		 raidClass.zoneOfPlayer.remove(e.getPlayer().getName());
                	 
                	 if(alarm.containsKey(e.getPlayer().getName())) 
                	 {
                	   boolean validShop = false;
                	   for(String s : shops){
                		   if(s.equalsIgnoreCase(e.getRegion().getId()))
                			   validShop = true;
                	   }
                       if((e.getRegion().getId().equalsIgnoreCase("bank")) || validShop || (e.getRegion().getId().equalsIgnoreCase("supermarket")) || (e.getRegion().getId().equalsIgnoreCase("ruestungsshop")) || (e.getRegion().getId().equalsIgnoreCase("tankstelle")) || (e.getRegion().getId().equalsIgnoreCase("supermarkt1")) || (e.getRegion().getId().equalsIgnoreCase("supermarkt2")) || (e.getRegion().getId().equalsIgnoreCase("waffenshop")) || (e.getRegion().getId().equalsIgnoreCase("schmiede")) || (e.getRegion().getId().equalsIgnoreCase("steakhouse"))) 
                       {
       	               if(!(alarm.containsKey(e.getPlayer().getName()))) 
                  	   {
                  		   return;
                  	   }
       	              Player p = e.getPlayer();
                     e.getPlayer().sendMessage(ChatColor.RED + "Du bist geflohen");

                     alarm.remove(e.getPlayer().getName());
                     spielerRaub.remove(e.getPlayer().getName());
                     if(this.delayedShopSpawn.contains(e.getRegion().getId())){
                    	 this.delayedShopSpawn.remove(e.getRegion().getId());
                     }
                     for(i = 0; i < alarmListe.size(); i++) 
                     {
                    	String name = alarmListe.get(i);
                    	if(e.getPlayer().getName().equalsIgnoreCase(name)) 
                    	{
                    		alarmListe.remove(i);
                    	}
                     }
                     
                     }
                   }
                	 
                 if(timeLeft.containsKey(e.getPlayer().getName())) 
                 {
                 	   boolean validShop = false;
                	   for(String s : shops){
                		   if(s.equalsIgnoreCase(e.getRegion().getId()))
                			   validShop = true;
                	   }
                   if((e.getRegion().getId().equalsIgnoreCase("bank")) || validShop || (e.getRegion().getId().equalsIgnoreCase("supermarket")) || (e.getRegion().getId().equalsIgnoreCase("ruestungsshop")) || (e.getRegion().getId().equalsIgnoreCase("tankstelle")) || (e.getRegion().getId().equalsIgnoreCase("supermarkt1")) || (e.getRegion().getId().equalsIgnoreCase("supermarkt2")) || (e.getRegion().getId().equalsIgnoreCase("waffenshop")) || (e.getRegion().getId().equalsIgnoreCase("schmiede")) || (e.getRegion().getId().equalsIgnoreCase("steakhouse")))
                   {
                	   if(!(timeLeft.containsKey(e.getPlayer().getName()))) 
                	   {
                		   return;
                	   }
                	   raidClass.onPlayerOutOfZone(e.getPlayer(),e.getRegion().getId());
                   }
                 }
                 
                 }
                 public void setRobCooldown(String laden, String name, Long cd) 
                 {
                	 for(String otherShop : this.shops){
                		 if(otherShop.equalsIgnoreCase(laden)){
                			 this.shopCooldownsMap.put(otherShop, cd);
                		 }
                	 }
                	 
                	 if(laden.equalsIgnoreCase("steakhouse")) 
                	 {
                		 mcdonalds.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("supermarket")) 
                	 {
                		 supermarket.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("ruestungsshop")) 
                	 {
                		 gasstation.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("supermarkt1")) 
                	 {
                		 supermarkt1.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("supermarkt2")) 
                	 {
                		 supermarkt2.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("tankstelle")) 
                	 {
                		 tankstelle.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("waffenshop")) 
                	 {
                		 waffenshop.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("bank")) 
                	 {
                		 bank.put(name, cd);
                	 }
                	 if(laden.equalsIgnoreCase("schmiede")) 
                	 {
                		 schmiede.put(name, cd);
                	 }
                 }
                 
                 public Long getRobCooldown(String laden, String name) 
                 {
                	 for(String otherShop : this.shops){
                		 if(!otherShop.equalsIgnoreCase(laden))
                			 continue;
                		 
                		 if(this.shopCooldownsMap.containsKey(otherShop)){
                			 Long cd = shopCooldownsMap.get(otherShop);
                			 return cd;
                		 } else {
                			 return 0L;
                			 //CHECK
                		 }
                	 }
       
                	 if(laden.equalsIgnoreCase("steakhouse")) 
                	 {
                		 if(!(mcdonalds.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return mcdonalds.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("supermarket")) 
                	 {
                		 if(!(supermarket.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return supermarket.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("ruestungsshop")) 
                	 {
                		 if(!(gasstation.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return gasstation.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("schmiede")) 
                	 {
                		 if(!(schmiede.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return schmiede.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("supermarkt1")) 
                	 {
                		 if(!(supermarkt1.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return supermarkt1.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("supermarkt2")) 
                	 {
                		 if(!(supermarkt2.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return schmiede.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("tankstelle")) 
                	 {
                		 if(!(tankstelle.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return tankstelle.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("waffenshop")) 
                	 {
                		 if(!(waffenshop.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return waffenshop.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("bank")) 
                	 {
                		 if(!(bank.containsKey(name))) 
                		 {
                			 return 0L;
                		 }
                		 return bank.get(name);
                	 }
                	 if(laden.equalsIgnoreCase("mcLarens"))
                	 {
                		 if(!(mcLarens.containsKey(name)))
                		 {
                			 return 0L;
                		 }
                		 return mcLarens.get(name);
                	 }
                	 
					return 0L;
                	 
                 }
                 
                 public static Permission permission = null;
                 public static Economy economy = null;
                 public static Chat chat = null;

                 private boolean setupPermissions()
                 {
                     RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
                     if (permissionProvider != null) {
                         permission = permissionProvider.getProvider();
                     }
                     return (permission != null);
                 }

                 private boolean setupChat()
                 {
                     RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
                     if (chatProvider != null) {
                         chat = chatProvider.getProvider();
                     }

                     return (chat != null);
                 }
                 
                 public boolean addActiveBlock(Block block, String reason)
                 {
                	 Material m = block.getType();
                	 String blockName = m.toString();
            		 String loc = block.getX() + ":" + block.getY() + ":" + block.getZ() + ":" + 4;
                	               	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                	 if(!list.contains(loc))
                	 {
                		 list.add(loc);
                		 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName, list);
                		 this.saveConfig();
                		 return true;
                	 }
                	 else
                	 {
                		 Bukkit.broadcastMessage(ChatColor.RED + "Dieser Block ist schon markiert!");
                		 return false;
                	 }
                 }
                 
                 public boolean addActiveRaidBlock(Block block, String reason, Raid.Spawns spawn){
                	 Material m = block.getType();
                	 String blockName = m.toString();
            		 String loc = block.getX() + ":" + block.getY() + ":" + block.getZ() + ":" + 4;
                	               	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + spawn.getName());
                	 if(!list.contains(loc))
                	 {
                		 list.add(loc);
                		 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + spawn.getName(), list);
                		 this.saveConfig();
                		 return true;
                	 }
                	 else
                	 {
                		 Bukkit.broadcastMessage(ChatColor.RED + "Dieser Block ist schon markiert!");
                		 return false;
                	 }
                 }
                 
                 public List<Location> getActiveBlockLocations(Block block, String reason)
                 {
                	 Material m = block.getType();
                	 String blockName = m.toString();
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                	 List<Location> locations = new ArrayList<Location>();
                	 
                	 for(String s : list)
                	 {
                		 Location l = new Location(block.getWorld(), Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2]));
                		 locations.add(l);
                	 }
                	 return locations;
                 }
                 
                 public List<Location> getActiveRaidBlockLocations(Block block, String reason, String diff)
                 {
                	 Material m = block.getType();
                	 String blockName = m.toString();
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff);
                	 List<Location> locations = new ArrayList<Location>();
                	 
                	 for(String s : list)
                	 {
                		 Location l = new Location(block.getWorld(), Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2]));
                		 locations.add(l);
                	 }
                	 return locations;
                 }
                 
                 public List<Location> getActiveBlockLocations(Material m,org.bukkit.World world, String reason)
                 {
                	 String blockName = m.toString();
                	               	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                	 List<Location> locations = new ArrayList<Location>();
                	 
                	 for(String s : list)
                	 {
                		 Location l = new Location((org.bukkit.World) world, Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2]));
                		 locations.add(l);
                	 }
                	 return locations;
                 }
                 
                 public List<Location> getActiveRaidBlockLocations(Material m,org.bukkit.World world, String reason, String diff)
                 {
                	 String blockName = m.toString();
                	               	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff);
                	 List<Location> locations = new ArrayList<Location>();
                	 
                	 for(String s : list)
                	 {
                		 Location l = new Location((org.bukkit.World) world, Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]), Integer.parseInt(s.split(":")[2]));
                		 locations.add(l);
                	 }
                	 return locations;
                 }
                 
                 public Integer getDurability(Block block, String reason)
                 {
                	 Material m = block.getType();
                	 String blockName = m.toString();
                	 int dura = 4;
                	              	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                	 
                	 for(String s : list)
                	 {
                		 String locationString = s.split(":")[0] + ":" + s.split(":")[1] + ":" + s.split(":")[2];
                		 String blockLocationString = block.getX() + ":" + block.getY() + ":" + block.getZ();
                		 
                		 if(locationString.equalsIgnoreCase(blockLocationString))
                		 {
                			 dura = Integer.parseInt(s.split(":")[3]);
                			 return dura;
                		 }
                		 
                	 }
                	 return dura;
                 }
                 
                 public Integer getRaidDurability(Block block, String reason, String diff)
                 {
                	 Material m = block.getType();
                	 String blockName = m.toString();
                	 int dura = 4;
                	              	 
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff);
                	 
                	 for(String s : list)
                	 {
                		 String locationString = s.split(":")[0] + ":" + s.split(":")[1] + ":" + s.split(":")[2];
                		 String blockLocationString = block.getX() + ":" + block.getY() + ":" + block.getZ();
                		 
                		 if(locationString.equalsIgnoreCase(blockLocationString))
                		 {
                			 dura = Integer.parseInt(s.split(":")[3]);
                			 return dura;
                		 }
                		 
                	 }
                	 return dura;
                 }
                 
              
                 public void decreaseDurability(Block block, String reason,int amount)
                 {
                	 String blockName = block.getType().toString();
                     List<Location> locations = getActiveBlockLocations(block, reason);
                     for(Location l : locations)
                     {
                    	 if(l.equals(block.getLocation()))
                    	 {
                    		 int dura = this.getDurability(block, reason);
                    		 int x = (int) l.getX();
                    		 int y = (int) l.getY();
                    		 int z = (int) l.getZ();
                    		 String loc = null;
                    		 
                    		 String locationString = x + ":" + y + ":" + z + ":" + dura;
                    		 dura-=amount;
                    		 String newLocationString = x + ":" + y + ":" + z + ":" + dura;
                    		 if(dura <= 0)
                    		 {
                    			 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                    			 for(String s : list)
                    			 {
                    				 if(s.equalsIgnoreCase(locationString))
                    				 {
                    					 loc = s;
                    				 }
                    			 }
            					 list.remove(loc);
            					 list.add(newLocationString);
            					 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName, list);
            					 this.saveConfig();
            					 this.deactivateActiveBlock(newLocationString, block,reason);
                    		 }
                    		 else
                    		 {
                    			 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                    			 for(String s : list)
                    			 {
                    				 if(s.equalsIgnoreCase(locationString))
                    				 {
                    					 loc = s;
                    				 }
                    			 }
            					 if(list.contains(locationString)){
            						 list.remove(locationString);
            					 }
            
            					 list.add(newLocationString);
            					 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName, list);
            					 this.saveConfig();
                    		 }
                    	 }
                     }
                 }
                 
                 public void decreaseDurability(Block block, String reason,int amount, String diff)
                 {
                	 String blockName = block.getType().toString();
                     List<Location> locations = getActiveRaidBlockLocations(block, reason,diff);
                     for(Location l : locations)
                     {
                    	 if(l.equals(block.getLocation()))
                    	 {
                    		 int dura = this.getDurability(block, reason);
                    		 int x = (int) l.getX();
                    		 int y = (int) l.getY();
                    		 int z = (int) l.getZ();
                    		 String loc = null;
                    		 
                    		 String locationString = x + ":" + y + ":" + z + ":" + dura;
                    		 dura-=amount;
                    		 String newLocationString = x + ":" + y + ":" + z + ":" + dura;
                    		 if(dura <= 0)
                    		 {
                    			 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff);
                    			 for(String s : list)
                    			 {
                    				 if(s.equalsIgnoreCase(locationString))
                    				 {
                    					 loc = s;
                    				 }
                    			 }
            					 list.remove(loc);
            					 list.add(newLocationString);
            					 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff, list);
            					 this.saveConfig();
            					 this.deactivateActiveBlock(newLocationString, block,reason);
                    		 }
                    		 else
                    		 {
                    			 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff);
                    			 for(String s : list)
                    			 {
                    				 if(s.equalsIgnoreCase(locationString))
                    				 {
                    					 loc = s;
                    				 }
                    			 }
            					 list.remove(loc);
            					 list.add(newLocationString);
            					 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + diff, list);
            					 this.saveConfig();
                    		 }
                    	 }
                     }
                 }
                 
                 public void deactivateActiveBlock(String loc, Block block, String reason)
                 {        
                	 Material m = block.getType();
                	 
                	 Location l = new Location(block.getWorld(), Integer.parseInt(loc.split(":")[0]), Integer.parseInt(loc.split(":")[1]), Integer.parseInt(loc.split(":")[2]));
                	 deactivatedMaterial.put(loc, block.getType());
                	 String stringLoc = Integer.parseInt(loc.split(":")[0]) + ":" + Integer.parseInt(loc.split(":")[1]) + ":" + Integer.parseInt(loc.split(":")[2]) + ":" + 0;

                     deactivatedBlocks.add(stringLoc); 
                     StringIntegerMap.put(stringLoc, 4);
                	 l.getBlock().setType(Material.STONE);
                 }
                 
                 public void addSpawn(String shop, Location loc, Raid.Spawns s, int wave){
                	 raidClass.addSpawn(shop, loc, s, wave);
                 }
                 
                 public void activateActiveBlock(String loc, Block block, Material mat,Iterator<String> iterator, String reason)
                 {
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + mat.toString());
                	 list.remove(loc);
                	 String newStringLocation = Integer.parseInt(loc.split(":")[0]) + ":" + Integer.parseInt(loc.split(":")[1]) + ":" + Integer.parseInt(loc.split(":")[2]) + ":" + 4;
                	 list.add(newStringLocation);
                	 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + mat.toString(), list);
                	 this.saveConfig();
                	 block.setType(mat);
                	 
                	 StringIntegerMap.remove(loc);
                	 iterator.remove();
                 }
                 
                 public void activateActiveRaidBlock(String loc, Block block, Material mat,Iterator<Material> materialIterator ,Iterator<String> iterator, String reason, String diff)
                 {
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + mat.toString() + "." + diff);
                	 list.remove(loc);
                	 String newStringLocation = Integer.parseInt(loc.split(":")[0]) + ":" + Integer.parseInt(loc.split(":")[1]) + ":" + Integer.parseInt(loc.split(":")[2]) + ":" + 4;
                	 list.add(newStringLocation);
                	 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + mat.toString() + "." + diff, list);
                	 this.saveConfig();
                	 block.setType(mat);
                	 
                	 StringIntegerMap.remove(loc);
                	 iterator.remove();
                	 materialIterator.remove();
                 }
                 
                 public void activateActiveBlock(String loc,Block block,Material mat, String reason, Iterator i)
                 {
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + mat.toString());
                	 list.remove(loc);
                	 String newStringLocation = Integer.parseInt(loc.split(":")[0]) + ":" + Integer.parseInt(loc.split(":")[1]) + ":" + Integer.parseInt(loc.split(":")[2]) + ":" + 4;
                	 list.add(newStringLocation);
                	 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + mat.toString(), list);
                	 this.saveConfig();
                	 block.setType(mat);
                	 
                	 i.remove();              	 
                 }
                 
                 public void activateActiveRaidBlock(String loc,Block block,Material mat, String reason, Iterator i, String diff)
                 {
                	 List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + mat.toString() + "." + diff);
                	 list.remove(loc);
                	 String newStringLocation = Integer.parseInt(loc.split(":")[0]) + ":" + Integer.parseInt(loc.split(":")[1]) + ":" + Integer.parseInt(loc.split(":")[2]) + ":" + 4;
                	 list.add(newStringLocation);
                	 this.getConfig().set("MyViCrime.AktiveBloecke." + reason + "." + mat.toString() + "." + diff, list);
                	 this.saveConfig();
                	 block.setType(mat);
                	 
                	 i.remove();              	 
                 }
                 
                 public void loadDeactivatedActiveBlocks(String[] shops2){
                	 for(String reason : shops2)
                	 {
                		 for(String blockName : blocks)
                		 {
                			boolean raid = false;
                			if(this.getConfig().contains("MyViCrime.AktiveBloecke." + reason + "." + blockName + ".")){
                				raid = true;
                			}
                			if(raid == true){
                				for(Raid.Spawns spawn: Raid.Spawns.getTypes()){
                					List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName + "." + spawn.getName());
                        		 	for(String s : list)
                        		 	{
                        		 		String stringLoc = s.split(":")[0] + ":" + s.split(":")[1] + ":" + s.split(":")[2] + ":" + 0;
                        		 		int dura = Integer.parseInt(s.split(":")[3]);
                        		 		if(dura == 0)
                        		 		{
                    		 				System.out.println(reason + ":::" + blockName+ ":::" + stringLoc);
                        		 			if(reason.equalsIgnoreCase("Mining")){
                        		 				this.deactivatedBlocks.add(stringLoc); 
                        		 				this.deactivatedMaterial.put(stringLoc, this.getMaterialByItsName(blockName));
                        		 				this.StringIntegerMap.put(stringLoc, 4);
                        		 			} else {
                        		 				List<String> stringList;
                        		 				if(this.deactivatedRaidBlocks.containsKey(reason)){
                        		 					stringList = this.deactivatedRaidBlocks.get(reason);  
                        		 				} else {
                        		 					stringList = new ArrayList<String>();
                        		 				}
                        		 				stringList.add(stringLoc);
                        		 				this.deactivatedRaidBlocks.put(reason, stringList);
                        		 			}               		 
                        		 			}
                        		 		}
                				}
                			} else {
                			List<String> list = this.getConfig().getStringList("MyViCrime.AktiveBloecke." + reason + "." + blockName);
                	 
                		 	for(String s : list)
                		 	{
                		 		String stringLoc = s.split(":")[0] + ":" + s.split(":")[1] + ":" + s.split(":")[2] + ":" + 0;
                		 		int dura = Integer.parseInt(s.split(":")[3]);
                		 		if(dura == 0)
                		 		{
            		 				System.out.println(reason + ":::" + blockName+ ":::" + stringLoc);
                		 			if(reason.equalsIgnoreCase("Mining")){
                		 				this.deactivatedMaterial.put(stringLoc, this.getMaterialByItsName(blockName));
                		 				this.StringIntegerMap.put(stringLoc, 4);
                		 				this.deactivatedBlocks.add(stringLoc);  
                		 			} else {
                		 				List<String> stringList;
                		 				if(this.deactivatedRaidBlocks.containsKey(reason)){
                		 					stringList = this.deactivatedRaidBlocks.get(reason);  
                		 				} else {
                		 					stringList = new ArrayList<String>();
                		 				}
                		 				stringList.add(stringLoc);
                		 				this.deactivatedRaidBlocks.put(reason, stringList);
                		 			}               		 
                		 			}
                		 		}
                			}
                		 }
                	 }
                 }
                 
                 private Material getMaterialByItsName(String blockName){
                	 List<Material> list = new ArrayList<Material>();
                	 list.add(Material.GOLD_ORE);
                	 list.add(Material.DIAMOND_ORE);
                	 list.add(Material.COAL_ORE);
                	 for(Material mat : list){
                		 if(mat.toString().equalsIgnoreCase(blockName)){
                			 return mat;
                		 }
                	 }
                	 return null;
                 }
                 
                 private boolean setupEconomy()
                 {
                     RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                     if (economyProvider != null) {
                         economy = economyProvider.getProvider();
                     }
                     return (economy != null);
                 }
                 
                 private void stopScheduler()
                 /*     */   {
                 /* 193 */     this.getServer().getScheduler().cancelTask(this.taskID);
                 /*     */   }
                 
                 public static void setGroup(Player player, String group) 
                 {
                     String[] groups = permission.getPlayerGroups(player);
                     for(int i = 0;i < groups.length;i++)
                     {
                         if(groups[i].equalsIgnoreCase("cop")) 
                         {                     	
                        	 if(player.getInventory().contains(Material.CHAINMAIL_BOOTS)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.CHAINMAIL_CHESTPLATE)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.CHAINMAIL_HELMET)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.CHAINMAIL_LEGGINGS)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.STICK)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.STRING)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.CHAINMAIL_BOOTS, 1) });
                        	 }
                        	 if(player.getInventory().contains(Material.WOOD_AXE)) 
                        	 {
                        		player.getInventory().removeItem(new ItemStack[] { 
                        		new ItemStack(Material.WOOD_AXE, 1) });
                        	 }
                        	 
                        	 player.getInventory().setBoots(null);
                        	 player.getInventory().setChestplate(null);
                        	 player.getInventory().setHelmet(null);
                        	 player.getInventory().setLeggings(null);
                             player.getInventory().clear();
                        	 copList.remove(player.getName());
                        	 Bukkit.broadcastMessage(ChatColor.GRAY + "Cop " + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " hat gekündigt!");
                         }
                         permission.playerRemoveGroup(player,groups[i]);
                     }  
                     permission.playerAddGroup(player,group);
                 }
                 
                 public static int randInt(int min, int max) {

                	    // Usually this can be a field rather than a method variable
                	    Random rand = new Random();

                	    // nextInt is normally exclusive of the top value,
                	    // so add 1 to make it inclusive
                	    int randomNum = rand.nextInt((max - min) + 1) + min;

                	    return randomNum;
                }
                 
                 public WorldGuardPlugin getWorldGuard() {
                	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
                	 
                	    if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
                	    return null;
                	    }
                	 
                	    return (WorldGuardPlugin)plugin;
                	    }
                	            public boolean isPlayerOwnerOfRegion(Location location, String name)
                	            {
                	               WorldGuardPlugin worldGuard = getWorldGuard();
                	               RegionManager regionManager = worldGuard.getRegionManager(location.getWorld());
                	               ApplicableRegionSet regions = regionManager.getApplicableRegions(location);
                	            if (regions.size() == 0){
                	        return false;
                	    }else{
                	        for (ProtectedRegion region : regions)
                	        {
                	        	if(region.getMembers().contains(Bukkit.getPlayer(name).getUniqueId()))
                	        	{
                	        		return true;
                	        	}
                	        }
                	        return false;
                	    }
                	}
                	            public void spawnChief(Location loc)
                	            {                	                     	         
                	            	World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
                	                CustomEntityZombie customZombie = new CustomEntityZombie(nmsWorld, false, CustomEntityZombie.Type.CHIEF);
                	                customZombie.setPosition(loc.getX(), loc.getY(), loc.getZ());            	           
                	                nmsWorld.addEntity(customZombie);               	                
                	                for(int i = 0; i < 3; i++)
                	                {
                	                    CustomEntityWolf wolf = new CustomEntityWolf(nmsWorld,false);
                	                	wolf.setPosition(loc.getX(), loc.getY(), loc.getZ());
                	                	nmsWorld.addEntity(wolf);
                	                	wolf.setOwner(customZombie);
                	                }
                	            }
          
}


/* Location:           C:\Users\HAPPYMAJOR\Desktop\jd-gui-0.3.5.windows\MyViCrime.jar
 * Qualified Name:     main.main
 * JD-Core Version:    0.6.2
 */