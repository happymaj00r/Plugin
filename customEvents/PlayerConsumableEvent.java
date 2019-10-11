package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerConsumableEvent extends Event implements Cancellable{
	
	private Player p;
	private ItemStack item;
	private int amount;
	private boolean cancelled;
	
	private static final HandlerList handlers = new HandlerList();
	
	public PlayerConsumableEvent(Player p,ItemStack item){
		this.p = p;
		this.item = item;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public ItemStack getItem(){
		return item;
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
