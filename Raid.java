package myvcrime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.World;


public class Raid {
	
	public HashMap<String,String> zoneOfPlayer = new HashMap<String,String>();
	
	public main plugin;
	
	public Raid(main instance){
		plugin = instance;
	}
	
	public enum Spawns {
		
		S_SPAWN("small_spawn") {
			@Override
			public List<EntityLiving> spawnMobs(Raid raid,Location loc, Player raider) {
				return raid.spawnRaidChief(loc, raider,CustomEntityZombie.Type.SMALL_COP, false);
			}
		},
		M_SPAWN("middle_spawn"){
			@Override
			public List<EntityLiving> spawnMobs(Raid raid,Location loc, Player raider) {
				return raid.spawnRaidChief(loc, raider,CustomEntityZombie.Type.MIDDLE_COP, true);		
			}
		},
		B_SPAWN("big_spawn"){
			@Override
			public List<EntityLiving> spawnMobs(Raid raid,Location loc, Player raider) {
				return raid.spawnRaidChief(loc, raider,CustomEntityZombie.Type.CHIEF, false);
			}
		};
				
		public static Spawns[] getTypes(){
			Spawns[] s = {S_SPAWN,M_SPAWN,B_SPAWN};
			return s;
		}
		
		String name;
		
		Spawns(String name){
			this.name = name;
		}
		
		public String getName(){
			return name;
		}
		
		public abstract List<EntityLiving> spawnMobs(Raid raid,Location loc, Player raider);
	}
	
    public List<EntityLiving> spawnRaidChief(Location loc, Player raider,CustomEntityZombie.Type type,boolean dog){
      	List<EntityLiving> list = new ArrayList<EntityLiving>();
    	World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        CustomEntityZombie customZombie = new CustomEntityZombie(nmsWorld, true,type);
        customZombie.setPosition(loc.getX(), loc.getY(), loc.getZ());            	           
        nmsWorld.addEntity(customZombie);
        list.add(customZombie);
        EntityPlayer entityPlayer = ((CraftPlayer)raider).getHandle();
        EntityLiving livingPlayer = (EntityLiving) entityPlayer; 
        customZombie.setRaidTarget(livingPlayer);
        if(plugin.spielerRaub.containsKey(raider.getName())) customZombie.setAt(plugin.spielerRaub.get(raider.getName()));
        if(dog){
        for(int i = 0; i < 1; i++)
        {
            CustomEntityWolf wolf = new CustomEntityWolf(nmsWorld, true);
        	wolf.setPosition(loc.getX(), loc.getY(), loc.getZ());
        	nmsWorld.addEntity(wolf);
        	wolf.setOwner(customZombie);
        	list.add(wolf);
        	if(plugin.spielerRaub.containsKey(raider.getName())) wolf.setAt(plugin.spielerRaub.get(raider.getName()));
        }
        }
        return list;
    }
    
    
    public void onPlayerFleed(Player p, String shopName){
 	   p.getPlayer().sendMessage(ChatColor.GRAY + "Du bist geflohen!");
 	   String name = p.getName();
 	   if(plugin.playerOutOfZone.contains(name))
 		   plugin.playerOutOfZone.remove(p.getName());
 	   if(plugin.timeLeft.containsKey(name))
 		   plugin.timeLeft.remove(p.getPlayer().getName());
 	   if(plugin.spielerRaub.containsKey(name))
 		   plugin.spielerRaub.remove(p.getPlayer().getName());
       if(plugin.delayedShopSpawn.contains(shopName)){
    	   plugin.delayedShopSpawn.remove(shopName);
       }
       if(plugin.waveEntities.containsKey(shopName)){
    	   List<EntityLiving> list = plugin.waveEntities.get(shopName);
    	   plugin.shopWave.remove(shopName);
    	   plugin.raubListe.remove(p.getPlayer().getName());
    	   for(Iterator<EntityLiving> i = list.iterator(); i.hasNext();){
    		   EntityLiving entity = i.next();
    		   entity.setHealth(0F);
    		   i.remove();
    	   }
       }      
	   Iterator<String> iterator = plugin.raubListe.iterator();
       while(iterator.hasNext()) 
       {
      	String key = iterator.next();
      	if(p.getPlayer().getName().equalsIgnoreCase(key)) 
      	{
      		iterator.remove();                     		                 		                    		
      	}
       }
    }
    
    public Boolean isPlayerRobbing(Player p){
    	if(getNameOfRobbedShop(p) == ""){
    		return false;
    	} else {
    		return true;
    	}
    }   
    
    public void onPlayerOutOfZone(final Player p, String shopName){
    	if(!plugin.playerOutOfZone.contains(p.getName())){
        	p.sendMessage(ChatColor.RED + "Du bist dabei zu fliehen, kehre sofort zurück!");
        	plugin.playerOutOfZone.add(p.getName());
    	new BukkitRunnable(){
    		int counter = 6;
    		public void run(){
    			String shopName = getNameOfRobbedShop(p);
    			if(shopName == "")
    				this.cancel();
    				
    			if(isPlayerInZone(p)){
    				onPlayerCameBack(p);
    				this.cancel();
    			} else {
        			if(counter <= 0){
        				onPlayerFleed(p,shopName);
        				this.cancel();
        			} else {
        				counter--;
        				p.sendMessage(ChatColor.RED + "[" + ChatColor.YELLOW + counter + ChatColor.RED + "]");
        			}
    			}
    		}
    	}.runTaskTimer(plugin, 20L, 20L);
    	}
    }
    
    public String getNameOfRobbedShop(Player p){
    	if(plugin.spielerRaub.containsKey(p.getName())){
    		String shopName = plugin.spielerRaub.get(p.getName());
    		return shopName;
    	} else {
    		return "";
    	}
    }
    
    public Boolean isPlayerInZone(Player p){
    	if(plugin.spielerRaub.containsKey(p.getName())){
    		String shopName = plugin.spielerRaub.get(p.getName());
    		if(this.zoneOfPlayer.containsKey(p.getName())){
    			String currentZone = zoneOfPlayer.get(p.getName());
    			if(shopName.equalsIgnoreCase(currentZone)){
    				return true;
    			} else {
    				return false;
    			}
    		} else {
    			return false;
    		}
    	} else {
    		return false;
    	}
    }
    
    public void onPlayerCameBack(Player p){
    	if(plugin.playerOutOfZone.contains(p.getName())){
    		plugin.playerOutOfZone.remove(p.getName());
    	}
    	p.sendMessage(ChatColor.GREEN + "Du bist wieder in der Zone");
    }
	
	public void ActivateRaidSpawns(String shopName,Player raider,Spawns s){	
			if(plugin.deactivatedRaidBlocks.containsKey(shopName))
			{
				List<String> blockList = plugin.deactivatedRaidBlocks.get(shopName);
			    for(Iterator i = blockList.iterator(); i.hasNext();){
			    	
			    	String stringLoc = (String) i.next();

					int x = Integer.parseInt(stringLoc.split(":")[0]);
					int y = Integer.parseInt(stringLoc.split(":")[1]);
					int z = Integer.parseInt(stringLoc.split(":")[2]);
					
					Location loc = new Location(plugin.getServer().getWorld("Map"), x,y,z);
					
					Block block = loc.getBlock();
					List<EntityLiving> list;
					
					if(plugin.waveEntities.containsKey(shopName)){
						list = plugin.waveEntities.get(shopName);
					} else {
						list = new ArrayList<EntityLiving>();
					}		
					list.addAll(s.spawnMobs(this, loc.add(0, +1, 0),raider));
					for(EntityLiving cop : list){
						if(cop == null) continue;
						Cop bulle = (Cop) cop;
						bulle.setAt(shopName);
					}
					plugin.waveEntities.put(shopName, list);
			    }    
			}
	}
	
	public void deactivateRaidSpawns(String shopName,Player raider, Spawns s){
		List<Location> list = plugin.getActiveRaidBlockLocations(Material.BEDROCK,raider.getWorld() ,shopName,s.getName());
		Bukkit.broadcastMessage("dea");
		for(int i = 0; i < list.size(); i++){
			Location loc = list.get(i);
			loc.getChunk().load();
			Block block = loc.getBlock();
			loc.getChunk().unload();
			plugin.decreaseDurability(block, shopName, 4, s.getName());
		}
	}

	public void spawnMobs(String currentLaden, Player player, Spawns s) {
		ActivateRaidSpawns(currentLaden, player,s);
		deactivateRaidSpawns(currentLaden, player,s);
	}	
	
	public void addSpawn(String shop,Location loc, Spawns s, int wave){
		String path = "MyVCrime." + shop + ".wave" + wave;
		String locSerialized = loc.getX() + "," + loc.getY() + "," + loc.getZ();
		String sL = locSerialized + ":::" + s.getName();

			List<String> list = plugin.getConfig().getStringList(path);
			if(list.contains(sL)){
				Bukkit.broadcastMessage("Dieser Ort ist schon markiert");
			} else {
				
				Bukkit.broadcastMessage("Ort markiert");
				list.add(sL);
				plugin.getConfig().set(path, list);
				plugin.saveConfig();
			}
	}
	
	public boolean spawnWave(String shop,int wave,Player p){
		String path = "MyVCrime." + shop + ".wave" + wave;
		if(plugin.getConfig().contains(path)){
			List<String> list = plugin.getConfig().getStringList(path);
			List<Location> locList = new ArrayList<Location>();
			for(String s : list){
				String[] splittedString = s.split(":::");
				String sSpawn = splittedString[1];
				String sLocation = splittedString[0];
				
				String[] deserializedLoc = sLocation.split(",");
				Location loc = new Location(Bukkit.getWorld("Map"),Double.parseDouble(deserializedLoc[0]),Double.parseDouble(deserializedLoc[1]) + 1.0,Double.parseDouble(deserializedLoc[2]));
				for(Spawns spawn : Spawns.getTypes()){
					p.sendMessage(spawn.getName() + ":::" + sSpawn);
					if(spawn.getName().equals(sSpawn)){
						List<EntityLiving> eList;
						if(plugin.waveEntities.containsKey(shop)){
							eList = plugin.waveEntities.get(shop);
						} else {
							eList = new ArrayList<EntityLiving>();
						}
						
						eList.addAll(spawn.spawnMobs(this, loc, p));
						plugin.waveEntities.put(shop, eList);
					}
				}
				return true;
			}
		} else {
			Bukkit.broadcastMessage("Dieser Shop besitzt keine Wave" + wave);
			return false;
		}
		Bukkit.broadcastMessage("Irgendwas lief schief..." + wave);
		return false;
	}
	
	public int randInt(int min, int max) {

	    // NOTE: This will (intentionally) not run as written so that folks
	    // copy-pasting have to think about how to initialize their
	    // Random instance.  Initialization of the Random instance is outside
	    // the main scope of the question, but some decent options are to have
	    // a field that is initialized once and then re-used as needed or to
	    // use ThreadLocalRandom (if using at least Java 1.7).
	    // 
	    // In particular, do NOT do 'Random rand = new Random()' here or you
	    // will get not very good / not very random results.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	public boolean spawnWaveDelayed(final Raid raid,final String shop,int wave,final Player p,int minDelay, int maxDelay){
		String path = "MyVCrime." + shop + ".wave" + wave;
		if(plugin.getConfig().contains(path)){
			List<String> list = plugin.getConfig().getStringList(path);
			List<Location> locList = new ArrayList<Location>();
			List<EntityLiving> emptyList = new ArrayList<EntityLiving>();
			plugin.waveEntities.put(shop, emptyList);
			plugin.delayedShopSpawn.add(shop);
			for(final String s : list){
				Long initDelay = 120L;
				if(!plugin.delayedShopSpawn.contains(shop)) initDelay = 0L;
				Long random = (long) this.randInt(minDelay, maxDelay);
				new BukkitRunnable() {
					public void run(){
						String[] splittedString = s.split(":::");
						String sSpawn = splittedString[1];
						String sLocation = splittedString[0];
						
						String[] deserializedLoc = sLocation.split(",");
						Location loc = new Location(Bukkit.getWorld("Map"),Double.parseDouble(deserializedLoc[0]),Double.parseDouble(deserializedLoc[1]) + 1.0,Double.parseDouble(deserializedLoc[2]));
						if(plugin.raubListe.contains(p.getName())){
						for(Spawns spawn : Spawns.getTypes()){
							if(spawn.getName().equals(sSpawn)){
								List<EntityLiving> eList;
								if(plugin.waveEntities.containsKey(shop)){
									eList = plugin.waveEntities.get(shop);
								} else {
									eList = new ArrayList<EntityLiving>();
								}
								for(int i = 0; i < 32; i++){
									for(int z = 0; z < 32; z++){
										loc.add(Math.sin(z) * i, Math.cos(z) * i, i);
										loc.getWorld().playEffect(loc, Effect.WITCH_MAGIC, 1);
										loc.subtract(Math.sin(z) * i, Math.cos(z)* i, i);
									}
								}
								plugin.delayedShopSpawn.remove(shop);
								eList.addAll(spawn.spawnMobs(raid, loc, p));
								plugin.waveEntities.put(shop, eList);
							}
						}
						}
					}
				}.runTaskLater(plugin, random + initDelay);
			}
			return true;
		} else {
			Bukkit.broadcastMessage("Dieser Shop besitzt keine Wave" + wave);
			return false;
		}
	}
	
	public void startRaid(String currentLaden, Player player){
		for(String bar : plugin.bars){
			if(bar.equalsIgnoreCase(currentLaden)){
				
			}
		}
		for(String shop : plugin.shops){
			if(shop.equalsIgnoreCase(currentLaden)){
				plugin.shopWave.put(player.getName(), 1);
				plugin.setRobCooldown(currentLaden, player.getName(), System.currentTimeMillis());
				SpielerProfil.addCrime(60, player.getName(), "Raubueberfall:" + currentLaden);
				this.startNextWave(currentLaden, player);
			}
		}
	}
	
	public boolean ShopisInRaid(String shopName){
		for(String name : plugin.spielerRaub.values()){
			if(name == shopName){
				return true;
			}
		}
		return false;
	}
	
	
		
	public Boolean startNextWave(String currentLaden, Player player){
		int c = plugin.shopWave.get(player.getName());
		Boolean bool = false;
		switch(c){
			case 1:
				bool = this.spawnWaveDelayed(this,currentLaden, 1, player,20,60);
				if(bool) Utility.sendTitleDelayed(player, ChatColor.GREEN + "Der Überfall beginnt!","",20L);
				break;
			case 2:
				bool = this.spawnWaveDelayed(this,currentLaden, 2, player,20,100);
				if(bool) Utility.sendTitleDelayed(player, ChatColor.GREEN + "Wave 2","",100L);
				break;
			case 3:
				bool = this.spawnWaveDelayed(this,currentLaden, 3, player,20,100);
				if(bool) Utility.sendTitleDelayed(player, ChatColor.GREEN + "Wave 3","",100L);
				break;
		}
		return bool;
	}
}
