package myvcrime.NPE;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.Utility;
import myvcrime.main;
import myvcrime.chat.Msg;
import myvcrime.raid.RaidableShop;
import net.md_5.bungee.api.ChatColor;

public class RobberyStage implements Stage {
	
	private Player p;
	private RaidableShop targetShop;
	private boolean isStarted;
	private boolean isPaused;
	private boolean finished;
	private Guide guide;
	
	public RobberyStage (Player p, RaidableShop targetShop, Guide guide){
		this.p = p;
		this.targetShop = targetShop;
		this.isStarted = false;
		this.isPaused = false;
		this.finished = false;
	}

	@Override
	public void start() {
		this.isStarted = true;
		Utility.sendTitle(p,ChatColor.YELLOW + "TrainingZone", "Robbery");
		Msg.INFO.out(p, "Guide: Robbery");
	}
	
	public void checkForStageCompletion(){
		new BukkitRunnable(){
			@Override
			public void run() {
				if(hasPlayerBeganRobbery()){
					if(hasRobSucceed()){
						finish();
						this.cancel();
					}
				}
			}		
		}.runTaskTimer(main.plugin, 0, 5);
	}
	
	public boolean hasRobSucceed(){
		return targetShop.getRaid().isSuccesfulFinished();
	}
	
	public boolean hasPlayerBeganRobbery(){
		if(targetShop.hasRaid()){
			if(targetShop.getRaid().getPlayer() == p){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasStarted() {
		return isStarted;
	}

	@Override
	public boolean isPaused() {
		return isPaused;
	}

	public void finish() {
		this.finished = true;
		this.guide.nextStage();
	}
	
	@Override
	public boolean hasFinished(){
		return finished;
	}
}
