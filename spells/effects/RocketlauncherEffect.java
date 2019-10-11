package myvcrime.spells.effects;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import myvcrime.main;


public abstract class RocketlauncherEffect extends EffectHandler {

	private BukkitTask task;
	
	public void play(final Player player)
	{
       task = new BukkitRunnable()
        {
            Location loc2 = player.getEyeLocation();
            Location loc = player.getEyeLocation();
            Location loc3 = player.getEyeLocation();
            double t = 0;
            public void run()
            {        
            	if(this.isCancelled())
            		return;
            	
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
                
                v = EffectUtils.rotateEffect(v,loc);
                v2 = EffectUtils.rotateEffect(v2, loc2);
                v3 = EffectUtils.rotateEffect(v3, loc3);
                                            
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
                
                onTick(new Location[]{loc});
                
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
                onEnd();
        }
            }
    }.runTaskTimer(main.plugin, 0,  (long) 0.1);
	}
	
	@Override
	public void cancel(){
		task.cancel();
		onEnd();
	}
}
