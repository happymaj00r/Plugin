package myvcrime.raid;

import org.bukkit.entity.Player;

public class WaveRaid extends Raid {
	
	protected RaidableShop shop;
	private RaidState raidState;
	protected int currentWave;
	
	public WaveRaid(RaidableShop shop,Player p) {
		super(shop, p);
		this.shop = shop;
		this.currentWave = 0;
	}

	@Override
	protected void logic(){	
		if(!this.isOver())
			raidState.handle();
	}	

	@Override
	protected void finished(){
		super.finished();
	}
	
	@Override
	protected void onStart(){
		this.raidState = new WarnRaidState(this);
	}
	
	protected void setRaidState(RaidState state){
		this.raidState = state;
	}
}
