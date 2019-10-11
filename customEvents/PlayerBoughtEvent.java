package myvcrime.customEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerBoughtEvent extends Event implements Cancellable {
	private Player p;
	private ItemStack item;
	private double price;
	private boolean cancelled;
	
	private static final HandlerList handlers = new HandlerList();
	
	public PlayerBoughtEvent(Player p,ItemStack item,double price){
		this.p = p;
		this.price = price;
		this.item = item;
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public double getPrice(){
		return price;
	}
	
	public int getAmount(){
		return item.getAmount();
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
