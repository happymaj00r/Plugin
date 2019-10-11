package myvcrime.spells.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class Effects 
{

	Plugin plugin;
	
	public Effects(Plugin main)
	{
		plugin = main;
	}
	
	public void drawLine(final Location fromTo, final Location goTo,double step){
		Location loc = new Location(fromTo.getWorld(),fromTo.getX(),fromTo.getY(),fromTo.getZ());
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double toX = goTo.getX();
		double toY = goTo.getY();
		double toZ = goTo.getZ();
		while(true){	
			
			ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 1000);
			
			if(x < toX){
				if(x+step > toX){
					x = toX;
				} else {
					x+=step;
				}
			} else {
				if(x+step < toX){
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
				if(y+step < toY){
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
				if(z+step < toZ){
					z = toZ;
				} else {
					z-=step;
				}
			}
			
			if(loc.distance(goTo) <= 0.5) break;
		}
	}
	
	public void playFireball(final Player player)
	{
        new BukkitRunnable()
        {
            Location loc2 = player.getEyeLocation();
            Location loc = player.getEyeLocation();
            Location loc3 = player.getEyeLocation();
            double t = 0;
            public void run()
            {
                t = t + Math.PI/2.5;
                
                double x3 = 0;
                double y3 = 0;
                double z3 = t;
                
                double x = Math.sin(t);
                double y = Math.cos(t);
                double z = t;
                
                double x2 = Math.cos(t);
                double y2 = Math.sin(t);
                double z2 = t -4;
                
                Vector v2 = new Vector(x2,y2,z2);
                Vector v = new Vector(x,y,z);
                Vector v3 = new Vector(x3,y3,z3);
                
                v = rotateEffect(v,loc);
                v2 = rotateEffect(v2, loc2);
                v3 = rotateEffect(v3, loc3);
                                            
                x = v.getX();
                y = v.getY();
                z = v.getZ();
                
                x2 = v2.getX();
                y2 = v2.getY();
                z2 = v2.getZ();
                
                x3 = v3.getX();
                y3 = v3.getY();
                z3 = v3.getZ();
                
                loc3.add(x3,y3,z3);
                loc2.add(x2,y2,z2);
                loc.add(x,y,z);
                if(!(loc.getBlock().getType() == Material.AIR))
                {
                	loc.getWorld().createExplosion(loc, 3);
                }
                
                Collection<Entity> list = loc.getWorld().getNearbyEntities(loc, 5, 5, 5); // Setting the damage
                for(Entity e : list)
                {
                	if(e instanceof Player)
                	{
                		Player p = (Player) e;
                	}
                }
                
                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, loc, 1000);
                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, loc2, 1000);
                ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, loc3, 1000);
                loc.subtract(x,y,z);
                loc2.subtract(x2,y2,z2);
                loc3.subtract(x3,y3,z3);
               
        if (t > Math.PI*16)
        {
                this.cancel();
        }
            }
    }.runTaskTimer(plugin, 0,  (long) 0.1);
	}
	
	public void shotgun(final Player p){
			double t = 1;
			Location loc = p.getEyeLocation().subtract(0, 0, 0).add(0, 0, 0);
			
			final double BULLET_DAMAGE = 1.0;
			final int BULLETS = 12;
			final int STREU = 1;
			int finalDamage = 0;
			
			HashMap<LivingEntity,Double> entityDamageMap = new HashMap<LivingEntity,Double>();
			
			int[] pitch = new int[BULLETS];
			int[] yaw = new int[BULLETS];
			boolean[] bulletHits = new boolean[BULLETS];
		
			for(int yy = 0; yy < 15; yy++){
				if(t == 1){
					for(int i = 0; i < BULLETS; i++){
						Random random = new Random();
						int rand = random.nextInt(1 + i);
						pitch[i] = rand;
						rand = random.nextInt(1 + i);
						yaw[i] = rand;
					}
				}
				
				for(int i = 0; i < BULLETS; i++){
					
					if(bulletHits[i] == true)
						continue;
					
					double x = 0;
					double y = 0;
					double z = t;
					
					int deltaPitch = STREU * pitch[i];
					
					
					int deltaYaw = STREU * yaw[i];
					
					loc.setPitch(loc.getPitch() + deltaPitch);
					loc.setYaw(loc.getYaw() + deltaYaw);
					
					Vector v = new Vector(x,y,z);
					v = rotateEffect(v,loc);
					
				
					x = v.getX();
					y = v.getY();
					z = v.getZ();
				
					loc.add(x,y,z);
					
					if(t >= 4){
		                ParticleEffect.SPELL_MOB.display(0, 0, 0, 0, 1, loc, 100);
		                ParticleEffect.FLAME.display(0, 0, 0, 0, 1, loc, 100);
					}
					
					if(t == 3){
						ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 0, 1, loc, 100);
					}
					
					if(bulletHits[i] == false){
						List<Entity> hitEntities = new ArrayList(loc.getWorld().getNearbyEntities(loc, 2, 2, 2));
						if(!hitEntities.isEmpty()){
							for(Entity entity : hitEntities){
								if(entity instanceof Player){
									Player otherPlayer = (Player) entity;
									if(otherPlayer.equals(p))
										continue;
								}
								if(entity instanceof LivingEntity){
									LivingEntity livingEntity = (LivingEntity) entity;
									if(entityDamageMap.containsKey(livingEntity)){
										entityDamageMap.put(livingEntity, entityDamageMap.get(livingEntity) + BULLET_DAMAGE);
									} else {
										entityDamageMap.put(livingEntity, BULLET_DAMAGE);
									}
									bulletHits[i] = true;
								}
							}
						}
					}
					
					loc.subtract(x,y,z);
					
					loc.setPitch(loc.getPitch() - deltaPitch);
					loc.setYaw(loc.getYaw() - deltaYaw);
				}
				
				t+=2;
			}
			
			for(LivingEntity livingEntity : entityDamageMap.keySet()){
				livingEntity.damage(entityDamageMap.get(livingEntity));
			}
			entityDamageMap.clear();
	}
	
	public void rocketLauncher(final Player p)
	{
		Block block = p.getTargetBlock((Set<Material>) null, 50);
		final double distance = p.getLocation().distance(block.getLocation());
		//p.sendMessage("" + distance);
		new BukkitRunnable()
		{
			final Location loc = p.getEyeLocation();
			double t = 0;
			double amplitude = 1  + 0.1* distance;
			double stretch = 0.3 * distance;
			int counter = 0;
			int alpha = 0;
			public void run() 
			{			
				t += Math.PI/4;//8
				//p.sendMessage("" + t/stretch);
				counter++;
				//p.sendMessage(counter+"");
				p.sendMessage("distanz: " + distance);
				double x = amplitude * Math.sin(t/stretch)-1;
				double y = amplitude * Math.sin(t/stretch)+1;
				double z = t+1;
				
				x+=1;
				y-=1;
				z+=0.5;
				// ROtiere nach Spielerrichtung
				Vector v = new Vector(x,y,z);
				v = rotateEffect(v,loc);
				
				x = v.getX();
				y = v.getY();
				z = v.getZ();
				
				loc.add(x,y,z);
                ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 100);
                ParticleEffect.SPELL_MOB.display(0, 0, 0, 0, 1, loc, 100);
				loc.subtract(x,y,z);
				
				x = amplitude * Math.sin(t/stretch + Math.PI) -1;
				y = amplitude * Math.sin(t/stretch + Math.PI)+1;
				z = t-1;
				
				x+=1;
				y+=-1;
				z+=2;
				
				//Rotiere nach Spielerrichtung
				v = new Vector(x,y,z);
				v = rotateEffect(v,loc);
				
				x = v.getX();
				y = v.getY();
				z = v.getZ();
				
				
				loc.add(x,y,z);
                ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 100);
                ParticleEffect.SPELL_MOB.display(0, 0, 0, 0, 1, loc, 100);
				loc.subtract(x,y,z);
				
				//Kurze oben rechts
				x = amplitude * Math.sin(t/stretch)-1;
				y = amplitude * Math.sin(t/stretch)+1;
				z = t-1;
				
				x+=1;
				y-=1;
				z+=1.5;
				
				// Rotiere um Z
				v = new Vector(x,y,z);
				v = rotateAboutZ(v,90);
				
				x = v.getX();
				y = v.getY();
				z = v.getZ();
				
				// Rotiere nach Spielerrichtung
				
				v = new Vector(x,y,z);
				v = rotateEffect(v,loc);
				
				x = v.getX();
				y = v.getY();
				z = v.getZ();
				
				loc.add(x,y,z);
                ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 100);
                ParticleEffect.SPELL_MOB.display(0, 0, 0, 0, 1, loc, 100);
				loc.subtract(x,y,z);
				
				x = amplitude * Math.sin(t/stretch + Math.PI) -1;
				y = amplitude * Math.sin(t/stretch + Math.PI)+1;
				z = t-1;
				
				x+=1;
				y+=-1;
				z+=2;
				
				// Rotiere um Z
				Vector v2 = new Vector(x,y,z);
				v2 = rotateAboutZ(v2, 90);
				
				x = v2.getX();
				y = v2.getY();
				z = v2.getZ();
				
				// Rotiere alles zu Spielerrichtung
				v2 = new Vector(x,y,z);
				v2 = rotateEffect(v2, loc);
				
				x = v2.getX();
				y = v2.getY();
				z = v2.getZ();		
				
				loc.add(x,y,z);
                ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 100);
                ParticleEffect.SPELL_MOB.display(0, 0, 0, 0, 1, loc, 100);
				loc.subtract(x,y,z);
				
				if(Math.sin(t/stretch) <= 0 && t/5 >= 0.2)
				{
					x = v2.getX();
					y = v2.getY();
					z = v2.getZ();		
					
					loc.add(x,y,z);
					
					pressureWave(loc,30);
					
					this.cancel();
				}
			}		
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public void radialWave(final Player player)
	{
        new BukkitRunnable()
        {
                double t = Math.PI/4;
                Location loc = player.getLocation();
                public void run()
                {
                        t = t + 0.1*Math.PI;
                        for (double theta = 0; theta <= 2*Math.PI; theta = theta + Math.PI/32)
                        {
                                double x = t*Math.cos(theta);
                                double y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                                double z = t*Math.sin(theta);
                                loc.add(x,y,z);
                                if(loc.getBlock().getType() != Material.AIR)
                                {
                                	loc.getBlock().getWorld().createExplosion(loc, 3);
                                	// SETTING THE DAMAGE:
                                    Collection<Entity> list = loc.getWorld().getNearbyEntities(loc, 5, 5, 5);
                                    for(Entity e : list) 
                                    {
                                    	if(e instanceof Player)
                                    	{
                                    		Player p = (Player) e;
                                    	}
                                    }
                                }
                                ParticleEffect.CLOUD.display(0,0,0,0,1,loc,100);
                                loc.subtract(x,y,z);
                               
                                theta = theta + Math.PI/64;
                               
                                x = t*Math.cos(theta);
                                y = 2*Math.exp(-0.1*t) * Math.sin(t) + 1.5;
                                z = t*Math.sin(theta);
                                loc.add(x,y,z);
                                ParticleEffect.LAVA.display(0, 0, 0, 0, 1, loc, 1000);
                                loc.subtract(x,y,z);
                        }
                        if (t > 20)
                        {
                                this.cancel();
                        }
                }
                                       
        }.runTaskTimer(plugin, 0, 1);
	}
	
	
	public void pressureWave(final Player p, int amount)
	{
		final Location loc = p.getLocation();
		loc.setY(loc.getY()+1);

				for(double phi = 0; phi < 2*Math.PI;phi += Math.PI/10)
				{
				for(double t = 0; t <= Math.PI*2; t+= Math.PI/amount)
				{
					double x = 1.5 * Math.sin(t) * Math.cos(phi);
					double y = 1.5 * Math.sin(phi);
					double z = 1.5 * Math.cos(t) * Math.cos(phi);
					
					loc.add(x,y,z);
					ParticleEffect.CLOUD.display(0, 0, 0, 2, 1, loc, 100);
					loc.subtract(x,y,z);
				}
				}
	}
	
	public void pressureWave(Location loc, int amount)
	{
		loc.setY(loc.getY()+1);

				for(double phi = 0; phi < 2*Math.PI;phi += Math.PI/10)
				{
				for(double t = 0; t <= Math.PI*2; t+= Math.PI/amount)
				{
					double x = 1.5 * Math.sin(t) * Math.cos(phi);
					double y = 1.5 * Math.sin(phi);
					double z = 1.5 * Math.cos(t) * Math.cos(phi);
					
					loc.add(x,y,z);
					ParticleEffect.CLOUD.display(0, 0, 0, 2, 1, loc, 100);
					loc.subtract(x,y,z);
				}
				}
	}
	
	public void amaterasuCreate(final Location p, int amount)
	{
		final Location loc = p;
		loc.setY(loc.getY()+1);

				for(double phi = 0; phi < 2*Math.PI;phi += Math.PI/10)
				{
				for(double t = 0; t <= Math.PI*2; t+= Math.PI/amount)
				{
					double x = 1.5 * Math.sin(t) * Math.cos(phi);
					double y = 1.5 * Math.sin(phi);
					double z = 1.5 * Math.cos(t) * Math.cos(phi);
					
					loc.add(x,y,z);
					ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 2, 1, loc, 100);
					loc.subtract(x,y,z);
				}
				}
	}
	
	public void susanooWave(final Player p)
	{
		new BukkitRunnable()		
		{
			int time = 0;
			@Override
			public void run() 
			{
			   time++;
			   Location loc = p.getLocation();
			   for(double i = 0; i <= Math.PI*2; i+= Math.PI/16)
			   {
				   double x = 6 * Math.sin(i);
				   double y = 0;
				   double z = 6 * Math.cos(i);
				   
				   loc.add(x,y,z);
				   ParticleEffect.SPELL_WITCH.display(0, 0, 0, 2, 1, loc, 100);
				   loc.subtract(x,y,z);
			   }
			   if(time >= 500)
			   {
				   this.cancel();
			   }
			}
		}.runTaskTimer(plugin, 0, 1);				
	}
	
	public void susanooFlame(final Player p)
	{
		new BukkitRunnable()
		{
			double time = 0;
			Location loc = p.getLocation();
			@Override
			public void run() 
			{
				time+=2;
				for(int i = 0; i < 2 + time; i++)
				{
					double x = (-time + (i*2))/8;
					double y = 0;
					double z = time*0.5;
					
					Vector v = new Vector(x,y,z);
					double yaw = loc.getYaw()/180.0*Math.PI;
					v = rotateAboutY(v, -yaw);
					
					x = v.getX();
					y = v.getY();
					z = v.getZ();	
					
					loc.add(x,y,z);
					
                    Collection<Entity> list = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    for(Entity e : list) 
                    {
                    	if(e instanceof Player)
                    	{
                    		Player p = (Player) e;
                    		p.damage(1.0);
                    	}
                    }
					
					ParticleEffect.SPELL_WITCH.display(0, 0, 0, 2, 1, loc, 100);
					loc.subtract(x,y,z);
				}
				if(time * 0.5 >= 15)
				{
					this.cancel();
				}
			}			
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public void slowFireball(final Player p)
	{
		new BukkitRunnable()
		{
			Location loc = p.getEyeLocation();
			double t = 0;
			
			double counter = 0;
			@Override
			public void run() 
			{
				t+= Math.PI/4;
				
				for(double phi = 0; phi < Math.PI*2; phi+= Math.PI/10)
				{
					double x = Math.sin(phi) * (t/4);
					double y = Math.cos(phi) * (t/4);
					double z = t;
					
					Vector vec = new Vector(x,y,z);
					vec = rotateEffect(vec, loc);
					
					x = vec.getX();
					y = vec.getY();
					z = vec.getZ();				
					
					loc.add(x,y,z);
					loc.getBlock().breakNaturally();
					FallingBlock fall = loc.getWorld().spawnFallingBlock(loc, Material.LAVA, (byte) 1);
					fall.setVelocity(vec);
					
					if(counter == 0)
					{
					    ParticleEffect.FLAME.display(0, 0, 0, 0, 1, loc, 50);
					}
					else
					{
						ParticleEffect.SMOKE_LARGE.display(0, 0, 0, 0, 1, loc, 50);
					}
					loc.subtract(x,y,z);
				}
				if(counter == 0)
				{
					counter = 1;
				}
				else
				{
					counter = 0;
				}
				
				if(t>= Math.PI * 10)
				{
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 1);
	}
	
	
	public void earthQuake(final Player p)
	{
		final double yOffset = 0;
		final Location loc = p.getLocation();
		new BukkitRunnable()
		{
			double height = 0;
			@Override
			public void run() 
			{
				height += Math.PI;
				for(double phi = 0; phi < Math.PI * 2; phi+= Math.PI/32)
				{
					double x = 0.7 * Math.sin(phi) * height;
					double y = yOffset + height;
					double z = 0.7 * Math.cos(phi) * height;
					
					loc.add(x,y,z);
					FallingBlock falling = loc.getWorld().spawnFallingBlock(loc, Material.LAVA, (byte) 1);
					Vector vec = new Vector(x,y,z).multiply(0.05);
					falling.setVelocity(vec);
					loc.subtract(x,y,z);
				}
				if(height >= 12 + yOffset)
				{
					this.cancel();
				}
			}			
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public void fallingHull(final Player p)
	{
		double yOffset = 1;
		Location loc = p.getLocation();
		for(double t = 0; t < Math.PI; t+= Math.PI/10)
		{
			for(double i = 0; i < Math.PI * 2; i+= Math.PI/12)
			{
				double x = Math.cos(i) * Math.sin(t);
				double y = yOffset + Math.cos(t);
				double z = Math.sin(i) * Math.sin(t);
				
				loc.add(x,y,z);
				ParticleEffect.LAVA.display(0, 0, 0, 0, 1, loc, 100);
				loc.subtract(x,y,z);
			}
		}
	}
	
	public HashMap<String,BukkitTask> teleportEffectTasks = new HashMap<String,BukkitTask>();
		
	public void teleportEffect(final Location loc, final int duration ){
		BukkitTask task = new BukkitRunnable()
		{
			double currentY = 0;
			int ticks = 0;
			int counter = 0;
			double effectCounter = 0;
			final double RADIUS = 1;
			final double CIRCLEAMOUNT = 6;
			final double CIRCLEDENSITY = Math.PI/8;
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
							loc.getWorld().playEffect(loc, Effect.LAVADRIP, 1);
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
				loc.getWorld().playEffect(loc, Effect.WATERDRIP, 1);
				loc.subtract(x, y, z);
				
				if(counter >= 10){
					counter = 0;
				} else {
					counter++;
				}
				
				if(ticks >= duration) this.cancel();
				
				effectCounter+=CIRCLEDENSITY;
			}
			
		}.runTaskTimer(plugin, 2, 1);
		teleportEffectTasks.put(serializeLocation(loc), task);
	}
	
	public static String serializeLocation(Location loc){
		String serializedString = loc.getX() + ":" + loc.getY() + ":" + loc.getZ();
		return serializedString;
	}
	
	public static String serializeLocationForPath(Location loc){
		String serializedString = loc.getX() + "#" + loc.getY() + "#" + loc.getZ();
		serializedString = serializedString.replace(".", "+");
		return serializedString;
	}
	
	public static Location deserializeLocation(String stringLoc){
		Location loc;		
		String[] arry;
		String x = "";
		String y = "";
		String z = "";
		arry = stringLoc.split(":");
		for(int i = 0; i < 3; i++){
			x = i == 0 ? arry[i] : x;
			y = i == 1 ? arry[i] : y;
			z = i == 2 ? arry[i] : z;
		}	
		Double xx = Double.parseDouble(x);
		Double yy = Double.parseDouble(y);
		Double zz = Double.parseDouble(z);
		
		loc = new Location(Bukkit.getWorld("Map"),xx,yy,zz);
		return loc;
	}
	
	public void ddd(final Location loc)
	{	//ssss
		new BukkitRunnable()
		{
			double yOffset = 1;
			double t = 0;
			
			@Override
			public void run() 
			{										
				for(double i = 0; i < Math.PI*2; i+= Math.PI/8)
				{
					double x = Math.cos(i) * Math.cos(t-Math.PI/2);
					double y = yOffset + Math.sin(t -Math.PI/2);
					double z = Math.sin(i) * Math.cos(t-Math.PI/2);
					
					loc.add(x,y,z);//
					ParticleEffect.PORTAL.display(0, 0, 0, 0, 1, loc, 100);
					loc.subtract(x,y,z);
				}
				t+= Math.PI/32;
				if(t >= Math.PI)
				{
					this.cancel();
				}
			}			
		}.runTaskTimer(plugin, 0, 1);
	}
	
	public void susanoo(final Player player)
	{
        new BukkitRunnable()
        {
        	double gap = 0;
			@Override
			public void run() 
			{
	        	Location loc = player.getLocation();
				for(double phi = 0; phi <= Math.PI * 2; phi += Math.PI/8)
				{
					for(double t = 0; t <= Math.PI * 2; t += Math.PI/32)
					{
						if(!(Math.sin(phi) <= 0))
						{
						double x = 1.4 * Math.sin(t) * Math.cos(phi);
						double y = 2.6 * Math.sin(phi);
						double z = 1.4 * Math.cos(t) * Math.cos(phi);
						
						if(!(1.4 * Math.cos(t) * Math.cos(phi) >= 1.4 * Math.cos(Math.PI - Math.PI/9) * Math.cos(phi)))
						{							
							Vector v = new Vector(x,y,z);
							double yaw = loc.getYaw()/180.0*Math.PI;
							v = rotateAboutY(v, -yaw);
							
							x = v.getX();
							y = v.getY();
							z = v.getZ();
																					
							loc.add(x,y,z);
							
			                ParticleEffect.CRIT_MAGIC.display(0, 0, 0, 0, 1, loc, 100);
			                ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0, 1, loc, 100);
							loc.subtract(x,y,z);
						}
						}
					}
				}
				
				if(gap >= 100)
				{
					this.cancel();
				}
			}

                                       
        }.runTaskTimer(plugin, 0, 2);
	}
	
	public void portal(final Location loc, final long duration)
	{
        new BukkitRunnable()
        {
        	double gap=0;
			@Override
			public void run() 
			{
				gap+=2;
				for(double phi = 0; phi <= Math.PI * 2; phi += Math.PI/4)
				{
					for(double t = 0; t <= Math.PI * 2; t += Math.PI/16)
					{
						if(!(Math.sin(phi) <= 0))
						{
						double x = 1.4 * Math.sin(t) * Math.cos(phi);
						double y = 2.6 * Math.sin(phi);
						double z = 1.4 * Math.cos(t) * Math.cos(phi);
						
						if(!(1.4 * Math.cos(t) * Math.cos(phi) >= 1.4 * Math.cos(Math.PI - Math.PI/9) * Math.cos(phi)))
						{							
							Vector v = new Vector(x,y,z);
							double yaw = loc.getYaw()/180.0*Math.PI;
							v = rotateAboutY(v, -yaw);
							
							x = v.getX();
							y = v.getY();
							z = v.getZ();
																					
							loc.add(x,y,z);
							
			                ParticleEffect.PORTAL.display(0, 0, 0, 0, 1, loc, 100);
			                ParticleEffect.SPELL_WITCH.display(0, 0, 0, 0, 1, loc, 100);
							loc.subtract(x,y,z);
						}
						}
					}
				}
				
				if(gap >= duration)
				{
					this.cancel();
				}
			}

                                       
        }.runTaskTimer(plugin, 0, 2);
	}
	
	public Vector rotateEffect(Vector v, Location loc)
	{
		double yaw = loc.getYaw()/180.0*Math.PI;
		double pitch = loc.getPitch()/180.0*Math.PI;
		
		v = rotateAboutX(v, pitch);
		v = rotateAboutY(v, -yaw);
		return v;
	}
	
	private Vector rotateAboutY(Vector v, double yaw) 
	{
		double X = Math.cos(yaw) * v.getX() + Math.sin(yaw) * v.getZ();
		double Z = -Math.sin(yaw) * v.getX() + Math.cos(yaw) * v.getZ();
		v.setX(X);
		v.setZ(Z);
		return v;
	}

	public Vector rotateAboutX(Vector v, double pitch)
	{
		double Y = v.getY()*Math.cos(pitch) - Math.sin(pitch) * v.getZ();
		double Z = v.getY()*Math.sin(pitch) + Math.cos(pitch)*v.getZ();
		v.setY(Y);
		v.setZ(Z);
		return v;
	}
	private Vector rotateAboutZ(Vector v, double g)
	{
		double X = v.getX()*Math.cos(g) - Math.sin(g) * v.getY();
		double Y = v.getX()*Math.sin(g) + Math.cos(g) * v.getY();
		v.setX(X);
		v.setY(Y);
		return v;
	}
}
