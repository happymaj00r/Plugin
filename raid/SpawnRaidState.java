package myvcrime.raid;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import myvcrime.Utility;
import myvcrime.main;
import myvcrime.world.Spawner;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EntityZombie;

public class SpawnRaidState implements RaidState {

	private WaveRaid waveRaid;
	private boolean started = false;
	private boolean finished = false;
	private Spawner spawner;
	private ArrayList<Entity> spawnedEntitiesList = new ArrayList<Entity>();
	private int wave;
	
	public SpawnRaidState(WaveRaid waveRaid, int wave){
		this.waveRaid = waveRaid;
		this.wave = wave;
		this.spawner = new Spawner(waveRaid.getPlayer().getWorld());
	}

	@Override
	public void handle() {
		
		checkForTransition();
		
		if(started == false){
			started = true;
			spawnWaveEntities();
		}
	}
		
	private void spawnWaveEntities(){
		Set<Class<? extends Entity>> entitySet = waveRaid.shop.getEntityList(wave);
		if(entitySet == null) {
			return;
		}
		new BukkitRunnable(){

			Iterator<Class<? extends Entity>> iterator = entitySet.iterator();
			Class<? extends Entity> entityClazz = null;
			int counter = 0;
			@Override
			public void run() {
				if(counter == 0){
					if(iterator.hasNext()){
						entityClazz = iterator.next();
					} else {
						this.cancel();
						finished = true;
						return;
					}
				}
				if(entityClazz == null){
					this.cancel();
					finished = true;
					return;
				}
				Location loc;
				if(!waveRaid.shop.getSpawnLocations(wave, 0).isEmpty()){
					loc = getRandomLocationOfList(waveRaid.shop.getSpawnLocations(wave, 0));
					if(loc == null) Bukkit.broadcastMessage("LOC IS NULL AFTER: getRandomLocationOutOfRegion");
				} else {
					ProtectedRegion protectedRegion = waveRaid.shop.getMainRegion();
					loc = Utility.getRandomFreeLocationOutOfRegion(waveRaid.getPlayer().getWorld(), protectedRegion);
					if(loc == null) Bukkit.broadcastMessage("LOC IS NULL AFTER: getRandomLocationOutOfRegion");
				}
				
				if(loc != null){
					try {
						Entity entity = spawner.spawn(entityClazz, loc);
						spawnedEntitiesList.add(entity);
						
						if(entity instanceof EntityZombie){ // Make zombie attack the player
							EntityZombie zombie = (EntityZombie) entity;
							Player p = waveRaid.getPlayer();
							CraftPlayer craftPlayer = (CraftPlayer) p;
							EntityPlayer entityPlayer = craftPlayer.getHandle();
							zombie.setGoalTarget(entityPlayer, TargetReason.TARGET_ATTACKED_ENTITY, false);
						}
						
					} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
							| IllegalArgumentException | InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//Loc is null some error occurred. probably on the raidable shop class
					waveRaid.getPlayer().sendMessage("[ERROR] Pls report: No Spawn Location found in Raid of shop " + waveRaid.shop.getName());
				}
				
				counter++;
				
				if(counter >= waveRaid.shop.getWaveEntityAmount(wave, entityClazz)){
					counter = 0;
				}
			}			
		}.runTaskTimer(main.plugin, 20L, 20L);
	}
	
	private void checkForTransition(){
		if(!Utility.isInRegion(waveRaid.getPlayer().getLocation(), waveRaid.shop.getMainRegion().getId())){
			waveRaid.setRaidState(new PlayerOutOfCombatRaidState(waveRaid,spawnedEntitiesList,this));
			return;
		}
		
		if(waveRaid.getPlayer().isDead()){
			waveRaid.setRaidState(new PlayerLostRaidState(waveRaid,spawnedEntitiesList));
			return;
		}
		
		if(finished == true){
			waveRaid.setRaidState(new SpawnedRaidState(waveRaid, spawnedEntitiesList));
			return;
		}
	}
		
	
	private Location getRandomLocationOfList(ArrayList<Location> locationList){
		if(locationList.isEmpty())
			return null;
		Random random = new Random();
		int rand = random.nextInt(locationList.size());
		return locationList.get(rand);
	}

}
