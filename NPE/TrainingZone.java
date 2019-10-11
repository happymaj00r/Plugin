package myvcrime.NPE;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.raid.RaidableShop;
import net.md_5.bungee.api.ChatColor;

public class TrainingZone {
	
	private ProtectedRegion protectedRegion;
	private ArrayList<Player> playerList;
	private Location spawnPoint;
	private String name;
	private ArrayList<Activator> activatorList;
	private ArrayList<RaidableShop> raidableShopList; 
	
	public TrainingZone(String name, ProtectedRegion protectedRegion,Location spawnPoint){
		this.protectedRegion = protectedRegion;
		this.playerList = new ArrayList<Player>();
		this.spawnPoint = spawnPoint;
		this.name = name;
		this.activatorList = new ArrayList<Activator>();
		this.raidableShopList = new ArrayList<RaidableShop>();
		handleActivators();
	}
	
	public ProtectedRegion getRegion(){
		return protectedRegion;
	}
	
	public void sentPlayerToZone(Player p){
		playerList.add(p);
		p.teleport(spawnPoint);
		Utility.sendTitle(p,ChatColor.YELLOW + "Trainingzone", ChatColor.BOLD + this.name);
	}
	
	public RaidableShop getFreeRaidableShop(){
		for(RaidableShop shop : this.raidableShopList){
			if(shop.isRaidable()){
				return shop;
			}
		}
		return null;
	}
	
	private void handleActivators(){
		new BukkitRunnable(){
			@Override
			public void run() {
				for(Activator activator : activatorList){
					if(activator.canBeActivated()){
						activator.activate();
					}
				}
			}
		}.runTaskTimer(main.plugin, 0, 1);
	}
	
	public boolean hasPlayer(Player p){
		if(playerList.contains(p)) return true;
			return false;
	}
	
	public void removePlayerFromZone(Player p){
		playerList.remove(p);
		p.teleport(SpielerProfil.Locations.LOCATION_RATHAUS.getLocation());
	}
	
	public ArrayList<Player> getPlayers(){
		return playerList;
	}
	
}
