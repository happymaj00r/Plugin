package myvcrime.raid;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;

public class WaveLocationContainer {
	
	final int waveAmount = 3;
	HashMap<Integer,ArrayList<Location>> wave1Map = new HashMap<Integer,ArrayList<Location>>();
	HashMap<Integer,ArrayList<Location>> wave2Map = new HashMap<Integer,ArrayList<Location>>();
	HashMap<Integer,ArrayList<Location>> wave3Map = new HashMap<Integer,ArrayList<Location>>();
	
	public WaveLocationContainer() {
		wave1Map.put(0, new ArrayList<Location>());
		wave2Map.put(0, new ArrayList<Location>());
		wave3Map.put(0, new ArrayList<Location>());
	}
	
	public boolean hasWave (int wave) {
		if(wave <= waveAmount - 1){
			return true;
		} else {
			return false;
		}
	}
	
	public void addLocation(int wave, int rank, Location location){
		if(hasWave(wave)){
			HashMap<Integer,ArrayList<Location>> map = getWaveMap(wave);
			ArrayList<Location> locationList = map.get(rank);
			locationList.add(location);
		}
	}
	
	private HashMap<Integer,ArrayList<Location>> getWaveMap(int wave){
		switch(wave){
		case 0:
			return wave1Map;
		case 1:
			return wave2Map;
		case 2:
			return wave3Map;
		}
		return null;
	}
	
	public ArrayList<Location> getLocationOfRank(int wave,int rank){
		if(hasWave(wave)){
			HashMap<Integer,ArrayList<Location>> map = getWaveMap(wave);
			return map.get(rank);
		}
		return null;
	}
	
}
