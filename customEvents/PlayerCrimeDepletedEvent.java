package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCrimeDepletedEvent extends Event implements Cancellable{

	private Player p;
	private boolean cancelled;
	
	private static final HandlerList handlers = new HandlerList();
	
	public PlayerCrimeDepletedEvent(Player p){
		this.p = p;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
}
