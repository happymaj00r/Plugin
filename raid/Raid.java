package myvcrime.raid;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import net.md_5.bungee.api.ChatColor;

public class Raid {
	
	private RaidableShop shop;
	private BukkitTask task;
	private final Player p;
	private boolean isOver;
	private int moneyIntervall;
	private int money;
	private boolean paused = false;
	private int seconds = 0;
	private boolean hasSuccessfullFinished;
	private int deltedTime = 0;
	
	public Raid(RaidableShop shop,Player p){
		this.money = 1;
		this.isOver = false;
		this.moneyIntervall = 3;
		this.shop = shop;
		this.p = p;
		this.hasSuccessfullFinished = false;
		init();
	}
	
	public Player getPlayer(){
		return p;
	}
	
	private void init(){
		shop.setRaid(this);
	}
	
	/*
	 * @param moneyIntervall - the amount of seconds after the player gets gold nuggets
	 */
	
	public void setMoneyIntervall(int moneyIntervall){
		this.moneyIntervall = moneyIntervall;
	}
	
	/*
	 * @param money - the money the player gets after a intervall
	 */
	
	protected void logic(){	
		deltedTime++;
		seconds++;
		if(seconds >= moneyIntervall){
			seconds = 0;
			p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET));
			SpielerProfil.addCrime(1, p.getName());
		}
		if(deltedTime >= 60)
			finished();
		
	}
	
	public void setMoneyAmountAtIntervall(int money){
		this.money = money;
	}
	
	public void start(){
		onStart();
		task = new BukkitRunnable(){
			@Override
			public void run() {
				if(!paused) logic();
				
			}			
		}.runTaskTimer(main.plugin, 20L, 20L);
	}
	
	public boolean isOver(){
		return isOver;
	}
	
	protected void finished(){ // WON
		Utility.sendTitle(p, ChatColor.DARK_GREEN + "Congratulation", "You won!");
		SpielerProfil.giveRewardItem(p, new ItemStack(Material.GOLD_NUGGET), 30);
		this.hasSuccessfullFinished = true;
		shop.removeRaid();
		this.isOver = true;
		task.cancel();
	}
	
	protected void onStart(){
	}

	public void setPaused(boolean bool){
		this.paused = bool;
	}
	
	public boolean isSuccesfulFinished(){
		return this.hasSuccessfullFinished;
	}
	
	public void quit(){ // LOST OR QUIT
		this.isOver = true;
		shop.removeRaid();
		task.cancel();
	}
}
