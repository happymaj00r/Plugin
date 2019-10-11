package myvcrime.spells.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import myvcrime.main;

public class ShadowWalkerEffect implements AbilityParticleEffect,Listener {
	
	private int duration;
	private int shadowAmount = 1;
	private HashMap<Player,Location> playerList = new HashMap<Player,Location>();
	private ArrayList<Player> playerRightclicked = new ArrayList<Player>();
	
	public ShadowWalkerEffect (int duration) {
		this.duration = duration;
	}
	
	public void drawLine(final Location fromTo, final Location goTo,double step){
		Location loc = new Location(fromTo.getWorld(),fromTo.getX(),fromTo.getY(),fromTo.getZ());
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		final double toX = goTo.getX();
		final double toY = goTo.getY();
		final double toZ = goTo.getZ();
		int counter = 0;
		while(true){	
			counter++;
			ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 1000);
			if(x < toX){
				if(x+step > toX){
					x = toX;
				} else {
					x+=step;
				}
			} else {
				if(x-step < toX){
					x = toX;
				} else {
					x-=step;
				}
			}
			
			if(y < toY){
				if(y+step > toY){
					y = toY;
				} else {
					y+=step;
				}
			} else {
				if(y-step < toY){
					y = toY;
				} else {
					y-=step;
				}
			}
			
			if(z < toZ){
				if(z+step > toZ){
					z = toZ;
				} else {
					z+=step;
				}
			} else {
				if(z-step < toZ){
					z = toZ;
				} else {
					z-=step;
				}
			}
			
			if(x == toX && y == toY && z == toZ){
				Bukkit.broadcastMessage("dist" + loc.distance(goTo));
				Bukkit.broadcastMessage("LOL");
				break;
			}
		}
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e){
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			Player p = e.getPlayer();
			if(playerList.containsKey(p) && !playerRightclicked.contains(p)){
				Location loc = playerList.get(p);
				drawLine(p.getLocation(),loc,0.5);
				p.teleport(loc);
				playerList.remove(p);
				playerRightclicked.add(p);
				Bukkit.broadcastMessage("Rightclicked");
			}
		}
	}
	
	private void shadowEffect(final Location loc, final int duration ){
		BukkitTask task = new BukkitRunnable()
		{
			double currentY = 0;
			int ticks = 0;
			int counter = 0;
			double effectCounter = 0;
			final double RADIUS = 1;
			final double CIRCLEAMOUNT = 1;
			final double CIRCLEDENSITY = Math.PI/4;
			@Override
			public void run() {			
				
				if(counter == 0){
					double x = 0;
					double y = 0;
					double z = 0;
					for(double i = 0; i < CIRCLEAMOUNT; i++){
						for(double j = 0; j < Math.PI*2; j+=CIRCLEDENSITY){
							x = Math.sin(j) * RADIUS;
							y = i/3;
							z = Math.cos(j) * RADIUS;
							loc.add(x, y, z);
							ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 100);
							loc.subtract(x, y, z);
						}
					}
				}
				
				if(effectCounter >= Math.PI*2){ //Circle is complete, start next
					currentY+=1.0/3.0;
					effectCounter = 0;
					if(currentY > 2){ //Effect is finished. 
						this.cancel();
						currentY = 2;
					}
				}
				
				double x = Math.sin(effectCounter) * RADIUS;
				double y = currentY;
				double z = Math.cos(effectCounter) * RADIUS;
				
				loc.add(x, y, z);
				loc.subtract(x, y, z);
				
				if(counter >= 10){
					counter = 0;
				} else {
					counter++;
				}
				
				if(ticks >= duration) this.cancel();
				
				effectCounter+=CIRCLEDENSITY;
			}
			
		}.runTaskTimer(main.plugin, 2, 1);
	}
	
	@Override
	public void play(final Player p){
		playerList.put(p, p.getLocation());
		new BukkitRunnable(){
				int counter = 0;
				double lastX = p.getLocation().getX();
				double lastY = p.getLocation().getY();
				double lastZ = p.getLocation().getZ();
				
				Location[] shadowLocations = new Location[1];
				double[] amplifier = new double[1];
				Random random = new Random();
				
			@Override
			public void run() {					
				Location loc = p.getLocation();
				
				if(counter >= duration){
					if(playerList.containsKey(p)) playerList.remove(p);
					if(playerRightclicked.contains(p)) playerRightclicked.remove(p);
					this.cancel();
				}
				
				if(shadowLocations[0] == null){
					shadowLocations[0] = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
					
					amplifier[0] = 2.6;
				}
				
				double deltaX;
				double deltaY;
				double deltaZ;
				
				deltaX = loc.getX() - lastX;
				deltaY = loc.getY() - lastY;
				deltaZ = loc.getZ() - lastZ;			
							
				for(int i = 0; i < shadowLocations.length; i++){
					double newX = deltaX * amplifier[i];
					double newY = deltaY * amplifier[i];
					double newZ = deltaZ * amplifier[i];
					shadowLocations[i] = shadowLocations[i].add(newX, newY, newZ);	
					if(playerRightclicked.contains(p)){
						playerRightclicked.remove(p);
						this.cancel();
					} else {
						shadowEffect(shadowLocations[i],30);
						playerList.put(p, shadowLocations[i]);
					}
				}			
				
				lastX = loc.getX();
				lastY = loc.getY();
				lastZ = loc.getZ();
				
				counter+=5;			
			}
		}.runTaskTimer(main.plugin, 1, 5);
	}
}
