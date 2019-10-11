package myvcrime.customEvents;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSoldEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	
	private Material type;
	private int amount;
	private Player p;
	
	public PlayerSoldEvent(Player p,Material type, int amount){
		this.p = p;
		this.type = type;
		this.amount = amount;
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
	
	public Player getPlayer(){
		return p;
	}
	
	public Material getItemType(){
		return type;
	}
	
	public int getAmount(){
		return amount;
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
