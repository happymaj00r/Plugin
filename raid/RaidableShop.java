package myvcrime.raid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;

import net.minecraft.server.v1_12_R1.Entity;

public class RaidableShop extends Shop {

	private Long lastRob;
	private final Long COOLDOWN = 120 * 1000L;
	private Raid raid;
	private String name;
	private HashMap<Integer, HashMap<Class<? extends Entity>, Integer>> waveEntityAmountMap = new HashMap<Integer,HashMap<Class<? extends Entity>,Integer>>();
	private WaveLocationContainer waveLocationContainer = new WaveLocationContainer();
	
	public RaidableShop(String name,String regionName, World world) {
		super(name, regionName, world);
	}
	
	public Raid getRaid(){
		return raid;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean hasRaid(){
		if(raid != null){
			return true;
		} 
		return false;
	}
	
	public String getName(){
		return name;
	}
	
	public Set<Class<? extends Entity>> getEntityList(int wave){
		if(waveEntityAmountMap.containsKey(wave)){
			return waveEntityAmountMap.get(wave).keySet();
		};
		return null;
	}
	
	public int getWaveEntityAmount(int wave,Class<? extends net.minecraft.server.v1_12_R1.Entity> entity){
		if(waveEntityAmountMap.containsKey(wave)){
			HashMap<Class<? extends Entity>,Integer> entityAmountMap = waveEntityAmountMap.get(wave);
			if(entityAmountMap.containsKey(entity)){
				return entityAmountMap.get(entity);
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
	
	
	public void addWaveSpawnLocations(int wave, int rank, ArrayList<Location> spawnLocationList){
		if(spawnLocationList == null) return;
		if(spawnLocationList.isEmpty()) return;
		for(Location loc : spawnLocationList){
			waveLocationContainer.addLocation(wave, rank, loc);
		}
	}
	
	public ArrayList<Location> getSpawnLocations(int wave, int rank){
		return waveLocationContainer.getLocationOfRank(wave, rank);
	}
	
	public void addEntityToWave(int wave,Class<? extends Entity> entity,int amount){
		if(waveEntityAmountMap.containsKey(wave)){
			HashMap<Class<? extends Entity>,Integer> entityAmountMap = waveEntityAmountMap.get(wave);
			if(entityAmountMap.containsKey(entity)){
				entityAmountMap.put(entity, entityAmountMap.get(entity) + amount);
			} else {
				entityAmountMap.put(entity, amount);
			}
		} else {
			HashMap<Class<? extends Entity>,Integer> entityAmountMap = new HashMap<Class<? extends Entity>,Integer>();
			entityAmountMap.put(entity,amount);
			waveEntityAmountMap.put(wave, entityAmountMap);
		}
	}
	
	protected boolean hasWave(int wave){
		if(waveEntityAmountMap.containsKey(wave)){
			return true;
		}
		return false;
	}
	
	public boolean isRaidable(){
		if(this.raid != null)
			return false;
		if(lastRob != null){
			if(lastRob + COOLDOWN < System.currentTimeMillis()){
				return true;
			}
		}
		if(lastRob == null)
			return true;
		return false;
	}
	
	public void setCooldown(){
		this.lastRob = System.currentTimeMillis();
	}
	
	protected void setRaid(Raid raid){
		this.raid = raid;
	}
	
	protected void removeRaid(){
		this.raid = null;
	}
}
