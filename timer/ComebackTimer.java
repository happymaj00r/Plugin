package myvcrime.timer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public abstract class ComebackTimer {
	
	Long dura;
	Plugin plugin;
	TimerCondition condition;
	boolean countdown;
	Player p;
	
	public ComebackTimer(TimerCondition condition, Long dura, Plugin plugin){
		this.plugin = plugin;
		this.condition = condition;
		this.dura = dura;
		this.p = condition.getPlayer();
	}
	
	public void showCountdown(boolean bool){
		this.countdown = bool;
		init();
	}
	
	public void init(){
		new BukkitRunnable(){
			Long time = 0L;
			@Override
			public void run() {
				time+=20;
				if(countdown)
					p.sendMessage(ChatColor.RED + "Kehre zurück! [" + ChatColor.YELLOW + time/20 + ChatColor.RED +"]");					
				if(condition.isMet()){
					onConditionMet();
					this.cancel();
				} else {
					if(time >= dura){
						onTimeExpired();
						this.cancel();
					}
				}
			}		
		}.runTaskTimer(plugin, 20, 20);
	}
	
	abstract public void onTimeExpired();
	abstract public void onConditionMet();
}
