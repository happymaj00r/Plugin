package myvcrime.spells.effects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class ShotgunEffect extends CollidingEffect {
	
	public void play(final Player p,double dmg,int bullets,int streu){
		double t = 1;
		Location loc = p.getEyeLocation().subtract(0, 0, 0).add(0, 0, 0);
		
		final double BULLET_DAMAGE = dmg;
		final int BULLETS = bullets;
		final int STREU = streu;
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
				v = EffectUtils.rotateEffect(v,loc);
				
			
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
	
}
