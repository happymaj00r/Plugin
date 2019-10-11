package myvcrime.customEvents;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMinedEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private Material ore;
	private int amount;
	private boolean cancelled;
	private Player p;
	
	public PlayerMinedEvent(Player p,Material ore, int amount){
		this.ore = ore;
		this.amount = amount;
		this.p = p;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public Material getOreType(){
		return ore;
	}
	
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
}
