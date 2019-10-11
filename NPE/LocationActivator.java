package myvcrime.NPE;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationActivator implements Activator {

	private Location loc;
	private TrainingZone zone;
	private GuideType guideType;
	
	public LocationActivator(TrainingZone zone, Location loc,GuideType guideType){
		this.loc = loc;
		this.zone = zone;
		this.guideType = guideType;
	}
	
	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public void activate() {
		for(Player p : zone.getPlayers()){
			if(p.getLocation().distance(loc) <= 1){
				switch(guideType){
					case ROBBERY:
							Guide guide = new Guide();
							Stage robberyStage = new RobberyStage(p,zone.getFreeRaidableShop(),guide);
							guide.addStage(robberyStage);
							guide.start();
						break;
				}
			}
		}
	}

	@Override
	public boolean canBeActivated() {
		return true;
	}

}
