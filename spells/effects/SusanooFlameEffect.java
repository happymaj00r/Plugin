package myvcrime.spells.effects;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import myvcrime.main;

public class SusanooFlameEffect implements AbilityParticleEffect {
	
	private final double damage;
	
	public SusanooFlameEffect(double damage){
		this.damage = damage;
	}
	
	public void play(final Player p)
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
					v = EffectUtils.rotateAboutY(v, -yaw);
					
					x = v.getX();
					y = v.getY();
					z = v.getZ();	
					
					loc.add(x,y,z);
					
                    Collection<Entity> list = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
                    for(Entity e : list) 
                    {            
                    	if(e instanceof Player)
                    	{        
                    		Player other = (Player) e;
                    		if(!other.equals(p)) other.damage(damage);
                    	} else if (e instanceof LivingEntity ){
                    		((LivingEntity) e).damage(damage);
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
		}.runTaskTimer(main.plugin, 0, 1);
	}
}
