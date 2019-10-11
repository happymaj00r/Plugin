package myvcrime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftZombie;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.listener.Jobs;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayInChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

@SuppressWarnings("deprecation")
public class Utility {
	
	static main plugin;
	
	public Utility(main plugin){
		this.plugin = plugin;
	}
	
	public static String getNameOfItem(ItemStack item){
		if(item.hasItemMeta()){
			ItemMeta meta = item.getItemMeta();
			if(meta.hasDisplayName()){
				return meta.getDisplayName();
			}
		}
		return "";
	}
	
	public static boolean hasLoreAndContains(ItemStack item,String s){
		if(item.hasItemMeta()){
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()){
				if(meta.getLore().contains(s)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static void clearWeapons(Player p){
		Inventory inv = p.getInventory();
		ItemStack[] content = inv.getContents();
		List<ItemStack> removalList = new ArrayList<ItemStack>();
		for(ItemStack item : content){
			if(item != null){
				Material mat = item.getType();
				if(mat == Material.IRON_AXE)
					removalList.add(item);
				if(mat == Material.GOLD_AXE)
					removalList.add(item);
				if(mat == Material.WOOD_AXE)
					removalList.add(item);
				if(mat == Material.DIAMOND_AXE)
					removalList.add(item);
				if(mat == Material.STONE_AXE)
					removalList.add(item);
				if(mat == Material.IRON_SWORD)
					removalList.add(item);
				if(mat == Material.GOLD_SWORD)
					removalList.add(item);
				if(mat == Material.WOOD_SWORD)
					removalList.add(item);
				if(mat == Material.DIAMOND_SWORD)
					removalList.add(item);
				if(mat == Material.STONE_SWORD)
					removalList.add(item);
				if(mat == Material.EGG)
					removalList.add(item);
				if(mat == Material.SNOW_BALL)
					removalList.add(item);
				if(mat == Material.TNT)
					removalList.add(item);
			}
		}
		
		if(removalList.size() > 0){
			for(ItemStack item : removalList){
				inv.remove(item);
			}
		}
	}
	
	public static Location getRandomLocationOutOfRegion(org.bukkit.World world, ProtectedRegion protectedRegion){
		BlockVector max = protectedRegion.getMaximumPoint();
		BlockVector min = protectedRegion.getMinimumPoint();
		double x = Jobs.zufall(min.getBlockX(), max.getBlockX());
		double y = Jobs.zufall(min.getBlockY(), max.getBlockY());
		double z = Jobs.zufall(min.getBlockZ(), max.getBlockZ());
		return new Location(world, x,y,z);
	}
	
	public static Location getRandomFreeLocationOutOfRegion(org.bukkit.World world, ProtectedRegion protectedRegion){
		int counter = 0;
		while(true){
			counter++;
			if(counter >= 100){
				Bukkit.broadcastMessage("[ERROR] Region has no free place");
				break;
			}
			BlockVector max = protectedRegion.getMaximumPoint();
			BlockVector min = protectedRegion.getMinimumPoint();
			double x = Jobs.zufall(min.getBlockX(), max.getBlockX());
			double y = Jobs.zufall(min.getBlockY(), max.getBlockY());
			double z = Jobs.zufall(min.getBlockZ(), max.getBlockZ());
			Location loc = new Location(world, x,y,z);
			if(loc.getBlock() == null || loc.getBlock().getType() == Material.AIR){
				return loc;
			}
		}
		return null;
	}
	
	public static String serializeLocation(Location loc){
		String serializedString = loc.getX() + ":" + loc.getY() + ":" + loc.getZ();
		return serializedString;
	}
	
	public static String serializeLocationForPath(Location loc){
		String serializedString = loc.getX() + "#" + loc.getY() + "#" + loc.getZ();
		serializedString = serializedString.replace(".", "+");
		return serializedString;
	}
	
	public static Location deserializeLocation(String stringLoc){
		Location loc;		
		String[] arry;
		String x = "";
		String y = "";
		String z = "";
		arry = stringLoc.split(":");
		for(int i = 0; i < 3; i++){
			x = i == 0 ? arry[i] : x;
			y = i == 1 ? arry[i] : y;
			z = i == 2 ? arry[i] : z;
		}	
		Double xx = Double.parseDouble(x);
		Double yy = Double.parseDouble(y);
		Double zz = Double.parseDouble(z);
		
		loc = new Location(Bukkit.getWorld("Map"),xx,yy,zz);
		return loc;
	}
	
	  public static String serializeCalendarTime(){
		  Calendar cal = Calendar.getInstance();
		  int hour = cal.get(Calendar.HOUR_OF_DAY);
		  int day = cal.get(Calendar.DAY_OF_MONTH);
		  int minute = cal.get(Calendar.MINUTE);
		  
		  String serializedTime = day + ":" + hour + ":" + minute;
		  
		  return serializedTime;
	  }
	  
	  public static Calendar deserializeCalendarTime(String serializedTime){
		  String[] arry = serializedTime.split(":");
		  Calendar cal = Calendar.getInstance();
		  Date date = new Date();
		  date.setMinutes(Integer.parseInt(arry[2]));
		  date.setHours(Integer.parseInt(arry[1]));
		  date.setDate(Integer.parseInt(arry[0]));
		  cal.setTime(date);
		  return cal;
	  }
	
	  public static Boolean checkRemainder(Double number){
		    Double d = number;
		    int teiler = number.intValue();
		    Double teilerDouble = new Double(teiler);
		    if(d > teilerDouble){
		     return true; 
		    } else {
		     return false; 
		    }
		  }
	
	public static void playTotemEffect(Player p){
		EntityPlayer ep = ((CraftPlayer) p).getHandle();
		PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 35);
		ep.playerConnection.sendPacket(status);
	}
	
	public static void sendTitle(Player p, String titleMSG, String subtitleMSG){
		// Title or subtitle, text, fade in (ticks), display time (ticks), fade out (ticks).
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titleMSG + "\"}"), 20, 80, 50);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\""+ subtitleMSG +"\"}"), 20, 80, 50);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	public static void sendTitle(Player p, String titleMSG, String subtitleMSG, long fadeIn,long displayTime, long fadeOut){
		// Title or subtitle, text, fade in (ticks), display time (ticks), fade out (ticks).
		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + titleMSG + "\"}"), (int) fadeIn, (int)displayTime, (int)fadeOut);
        PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\""+ subtitleMSG +"\"}"), (int)fadeIn, (int)displayTime, (int)fadeOut);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(subtitle);
	}
	
	
	public static ArrayList<Object> fillArrayInList(Object[] arry){
		ArrayList<Object> list = new ArrayList<Object>();
		for(int i = 0; i < arry.length; i++){
			Object obj = arry[i];
			list.add(obj);
		}
		return list;
	}
	
    public static int countItems(Material material, Inventory inv)
    {
        ItemStack[] items = inv.getContents();
        int amount = 0;
        for (ItemStack item : items) 
        {
        	if(item != null){
        		if (item.getType() == material) {
        			amount += item.getAmount();
        		}
        	}
        }
        return amount;
    }
	
	public static void sendTitleDelayed(final Player p, final String titleMSG, final String subtittleMSG, Long delay){
		new BukkitRunnable(){
			public void run(){
				sendTitle(p, titleMSG, subtittleMSG);
			}
		}.runTaskLater(plugin, delay);
	}
	
	public static int getFreeSlotsOfInventory(Player p){
		Inventory inv = p.getInventory();
		int counter = 0;
		int freeSlots = 0;
		ItemStack[] items = inv.getStorageContents();
		for(ItemStack is : items){
			if(is != null){
			if(is.hasItemMeta()){
				counter++;
			} else {
				counter++;
			}
			}
		}
		freeSlots = items.length - counter;
		return freeSlots;
	}
	
	
	public static void addHologramToStartList(Location loc, String key){
		String stringLoc = serializeLocation(loc);
		List<String> list;
		if(!plugin.getConfig().contains("Holograms")){
			if(!plugin.getConfig().contains("Holograms." + key))
				plugin.getConfig().set("Holograms." + key, new ArrayList<String>());
		}
		if(plugin.getConfig().isList("Holograms." + key)){
			list = plugin.getConfig().getStringList("Holograms." + key);
		} else {
			list = new ArrayList<String>();
		}
		if(!list.contains(stringLoc))
			list.add(stringLoc);
		
		plugin.getConfig().set("Holograms." + key, list);		
		plugin.saveConfig();
	}
	
	public static void playCircularEffect(Player p,Block clickedBlock,Effect effect, int i)
	{
		clickedBlock = p.getTargetBlock(null, 10);
  		Location bloc = clickedBlock.getLocation();
  		Location center = Utility.getCenter(bloc);
  		Double x = center.getX();
  		Double y = center.getY();
  		Double z = center.getZ();
  		int mod = i/2;
  		for(;i < 12; i++){

  			Double xx = Math.sin(i *(Math.PI/mod)) * 1; 
  			Double zz = Math.cos(i *(Math.PI/mod)) * 1;
  			Double yy = 0.0;
  			x += xx;
  			y += yy;
  			z += zz;
  			Location pos = new Location(p.getWorld(),x,y,z);
  			p.playEffect(pos, effect, i);
  			y -= yy;
  			x -= xx;
  			z -= zz;
  		}
	}
	
	public static void addFloatingItemToStartList(Location loc, String key, ItemStack is){		
		String stringLoc = serializeLocation(loc);
		List<String> list;
		if(!plugin.getConfig().contains("FloatingItems")){
			if(!plugin.getConfig().contains("FloatingItems." + key))
				plugin.getConfig().set("FloatingItems." + key, new ArrayList<String>());
		}
		if(plugin.getConfig().isList("FloatingItems." + key)){
			list = plugin.getConfig().getStringList("FloatingItems." + key);
		} else {
			list = new ArrayList<String>();
		}
		if(!list.contains(stringLoc))
			list.add(stringLoc);
		
		plugin.getConfig().set("FloatingItems." + key, list);		
		plugin.saveConfig();
	}
	
	public static void removeFloatingItemFromStartList(Location loc, String key){
		String stringLoc = serializeLocation(loc);
		if(plugin.getConfig().contains("FloatingItems." + key)){
			List<String> list = plugin.getConfig().getStringList("FloatingItems." + key);
			if(list.size() != 0){
				if(list.contains(stringLoc)){
					list.remove(stringLoc);
					if(plugin.hologramList.containsKey(stringLoc)){
						Hologram holo = plugin.hologramList.get(stringLoc);
						holo.delete();
					}
				}
			}
		}
	}
	
	public static WorldGuardPlugin getWorldGuard1() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
	    return null;
	    }
	 
	    return (WorldGuardPlugin)plugin;
	}

	public static boolean isInRegion(Location loc, String region)
	{
	    if (getWorldGuard1() != null)
	    {
	        com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getBlockY(), loc.getZ());
	        List<String> list = getWorldGuard1().getRegionManager(loc.getWorld()).getApplicableRegionsIDs(v);
	        for(String s : list){
	        }
	        return getWorldGuard1().getRegionManager(loc.getWorld()).getApplicableRegionsIDs(v).contains(region);
	    }
	 
	    return false;
	}
	
	public static boolean isOnlyInRegions(Location loc, List<String> regions){
		if(getWorldGuard1() != null){
	        com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getBlockY(), loc.getZ());
	        List<String> list = getWorldGuard1().getRegionManager(loc.getWorld()).getApplicableRegionsIDs(v);
	        for(String s : list){
	        	if(!regions.contains(s)) return false;
	        }
	        if(list.containsAll(regions)){
	        	return true;
	        } else {
	        	return false;
	        }
		}
		return false;
	}
		
	public static boolean isInRegions(Location loc, List<String> regions){
		if(getWorldGuard1() != null){
	        com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(loc.getX(), loc.getBlockY(), loc.getZ());
	        List<String> list = getWorldGuard1().getRegionManager(loc.getWorld()).getApplicableRegionsIDs(v);
	        for(String s : list){
	        	if(regions.contains(s)) return true;
	        }
	        return false;
		}
		return false;
	}
	
	public static Location getCenter(Location loc) {
	    return new Location(loc.getWorld(),
	        getRelativeCoord(loc.getBlockX()),
	        getRelativeCoord(loc.getBlockY()),
	        getRelativeCoord(loc.getBlockZ()));
	}
	 
	private static double getRelativeCoord(int i) {
	    double d = i;
	    d = d < 0 ? d + .5 : d + .5;
	    return d;
	}
	
	public static void removeHologramFromStartList(Location loc, String key){
		String stringLoc = serializeLocation(loc);
		if(plugin.getConfig().contains("Holograms." + key)){
			List<String> list = plugin.getConfig().getStringList("Holograms." + key);
			if(list.size() != 0){
				if(list.contains(stringLoc)){
					list.remove(stringLoc);
					if(plugin.hologramList.containsKey(stringLoc)){
						Hologram holo = plugin.hologramList.get(stringLoc);
						holo.delete();
					}
				}
			}
		}
	}
	
	public static Hologram createDailyChestHologram(Location loc){
		Hologram holo = (Hologram) HolographicDisplaysAPI.createHologram(plugin, loc, ChatColor.YELLOW + "" + ChatColor.BOLD + "Daily Chest");
		Bukkit.broadcastMessage("YO");
		return holo;
	}
	
	public static void loadHolograms(){
		Set<String> set = plugin.getConfig().getConfigurationSection("Holograms").getKeys(true);
		for(String s : set){
			List<String> list;
			list = plugin.getConfig().getStringList("Holograms." + s);
			if(list.size() != 0){
				int size = plugin.hologramList.size();
				for(int i = size; i < size + list.size(); i++){
					if(s.equalsIgnoreCase("stands")){
						plugin.hologramList.put(list.get(i),(Hologram) HolographicDisplaysAPI.createHologram(plugin, deserializeLocation(list.get(i)), ChatColor.GOLD + "Cooker" , ChatColor.GRAY + "Sneak + Drug to cook!"));
					}
					if(s.equalsIgnoreCase("dailychests")){
						plugin.hologramList.put(list.get(i), createDailyChestHologram(deserializeLocation(list.get(i))));
					}
				}
			}
		}
	}
	
	public static void clearPathfinders(org.bukkit.entity.Entity entity){
		LivingEntity le = (LivingEntity) entity;
		if(entity instanceof Zombie){
			Zombie zombie = (Zombie) entity;
			EntityZombie entityZombie = ((CraftZombie)zombie).getHandle();
		}
	}

	public static void addWolfsAfterInit(CustomEntityZombie customEntityZombie) {
		new BukkitRunnable(){
			@Override
			public void run() {
				World nmsWorld = customEntityZombie.getWorld();
				Location loc = new Location(Bukkit.getWorld("Map"),customEntityZombie.locX,customEntityZombie.locY,customEntityZombie.locZ);
		        for(int i = 0; i < 1; i++)
		        {
		            CustomEntityWolf wolf = new CustomEntityWolf(nmsWorld, true);
		        	wolf.setPosition(loc.getX(), loc.getY(), loc.getZ());
		        	nmsWorld.addEntity(wolf);
		        	wolf.setOwner(customEntityZombie);
		        }
			}		
		}.runTaskLater(plugin, 5L);
	}	
}
