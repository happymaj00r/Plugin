package myvcrime.abilities;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SuperhumanManager implements Listener {
	
	private static HashMap<Player,Superhuman> superhumanMap = new HashMap<Player, Superhuman>();
	
	public SuperhumanManager(){
	}
	
	public void createSuperhuman(Player p){
		Superhuman superhuman = Superhuman.instance(p);
		superhumanMap.put(p, superhuman);
	}
	
	public static Superhuman getSuperhuman(Player p){
		if(superhumanMap.containsKey(p)){
			return superhumanMap.get(p);
		}
		return null;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e){
		Player p = e.getPlayer();
		this.createSuperhuman(p);
	}
}
