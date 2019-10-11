package myvcrime.crime;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import myvcrime.SpielerProfil;
import myvcrime.main;
import myvcrime.customEvents.PlayerCrimeDepletedEvent;

public class CrimeTick {
	
	private final int DELTACRIME = 10;
	private Plugin plugin;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private HashMap<Player,BukkitTask> playerBukkitTaskMap = new HashMap<Player,BukkitTask>();
	
	public CrimeTick(Plugin plugin){
		this.plugin = plugin;
		repeat();
	}
	
		
	private void repeat(){
		new BukkitRunnable(){
			@Override
			public void run() {
				for(Player p : playerList){
					if(!isCrimeTickStoppedForPlayer(p))
						reduceCrimeOfPlayer(p);
				}
			}
		}.runTaskTimer(plugin, 20L, 1200L);
	}
		
	
	public void addPlayer(Player p){
		if(playerList.contains(p))
			return;
		if(p.isOnline())
			playerList.add(p);
	}
	
		
	public void removePlayer(Player p){
		if(playerList.contains(p))
			playerList.remove(p);
	}
	
	private boolean isCrimeTickStoppedForPlayer(Player p){
		if(playerBukkitTaskMap.containsKey(p)){
			return true;
		}
		return false;
	}
	

	public void stopForPlayer(Player p,int seconds){
		if(playerBukkitTaskMap.containsKey(p)){
			playerBukkitTaskMap.get(p).cancel();
			playerBukkitTaskMap.remove(p);
		}
		
		BukkitTask task = new BukkitRunnable(){
			@Override
			public void run() {
				playerBukkitTaskMap.remove(p);
			}
			
		}.runTaskLater(main.getPlugin(), 20L * seconds);
		
		playerBukkitTaskMap.put(p, task);
	}
	
	
	public void reduceCrimeOfPlayer(Player p){
		String name = p.getName();
		int currentCrime = SpielerProfil.getCrime(name);
		int newCrime = currentCrime < DELTACRIME ? 0 : currentCrime - DELTACRIME;
			
		if(currentCrime > 0 && newCrime == 0)
			Bukkit.getPluginManager().callEvent(new PlayerCrimeDepletedEvent(p));
			
		SpielerProfil.setCrime(newCrime, name);
	}
}
