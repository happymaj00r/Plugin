package myvcrime.spells.effects;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import myvcrime.main;

public class ShadowDispersionEffect implements AbilityParticleEffect {
	
	private int shadowAmount;
	private int duration;
	
	public ShadowDispersionEffect(int shadowAmount, int duration){
		this.shadowAmount = shadowAmount;
		this.duration = duration;
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
		new BukkitRunnable(){
				int counter = 0;
				double lastX = p.getLocation().getX();
				double lastY = p.getLocation().getY();
				double lastZ = p.getLocation().getZ();
				
				Location[] shadowLocations = new Location[shadowAmount];
				double[] amplifier = new double[shadowAmount];
				Random random = new Random();
				
			@Override
			public void run() {					
				Location loc = p.getLocation();
				
				if(counter >= duration) this.cancel();
				
				if(shadowLocations[0] == null){
					shadowLocations[0] = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
					shadowLocations[1] = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
					shadowLocations[2] = new Location(loc.getWorld(),loc.getX(),loc.getY(),loc.getZ());
					
					amplifier[0] = random.nextInt(4);
					amplifier[1] = random.nextInt(4);
					amplifier[2] = random.nextInt(4);
				}
				
				double deltaX;
				double deltaY;
				double deltaZ;
				
				deltaX = loc.getX() - lastX;
				deltaY = loc.getY() - lastY;
				deltaZ = loc.getZ() - lastZ;
				
				shadowEffect(loc,30);
							
				for(int i = 0; i < shadowLocations.length; i++){
					double newX = random.nextInt(3) > 0 ? deltaX * amplifier[i] : deltaX / amplifier[i];
					double newY = random.nextInt(3) > 0 ? deltaY * amplifier[i] : deltaY / amplifier[i];
					double newZ = random.nextInt(3) > 0 ? deltaZ * amplifier[i] : deltaZ / amplifier[i];
					shadowLocations[i] = shadowLocations[i].add(newX, newY, newZ);				
					shadowEffect(shadowLocations[i],30);
				}			
				
				lastX = loc.getX();
				lastY = loc.getY();
				lastZ = loc.getZ();
				
				counter+=5;			
			}
		}.runTaskTimer(main.plugin, 1, 5);
	}
}
