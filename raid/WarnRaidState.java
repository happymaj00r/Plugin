package myvcrime.raid;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.chat.Msg;
import myvcrime.customEvents.PlayerBoughtEvent;
import myvcrime.customEvents.PlayerRobEvent;
import net.md_5.bungee.api.ChatColor;

public class WarnRaidState implements RaidState {
	
	WaveRaid raid;
	boolean isActive = false;
	
	public WarnRaidState(WaveRaid raid){
		this.raid = raid;
	}

	@Override
	public void handle() {	
		if(isActive) return;
		Player p = raid.getPlayer();
		Msg.ALERT.out(p, ChatColor.RED + "[" + this.raid.shop.getName() + "]" + " Remove the gun or we will call the Cops!");
		isActive = true;
		new BukkitRunnable(){
			int counter = 10;
			@Override
			public void run() {
				if(raid.shop.isPlayerInShop(p) && SpielerProfil.weaponInHand(p.getName())){
					p.sendMessage(ChatColor.GRAY + "Cops coming in " + counter);
					counter--;
					if(counter <= 0){
						RaidState nextState = new SpawnRaidState(raid,0);
						raid.setRaidState(nextState);
						Utility.sendTitle(raid.getPlayer(),ChatColor.YELLOW + "Good luck!", "Wave 1 starts!");
						SpielerProfil.addCrime(50, raid.getPlayer().getName(), "Raub");
						this.cancel();
						raid.shop.setCooldown();
						main.plugin.getServer().getPluginManager().callEvent(new PlayerRobEvent(raid.getPlayer(),raid.shop,raid));
					}
				} else {
					raid.quit();
					this.cancel();
				}
			}
			
		}.runTaskTimer(main.plugin, 20L, 20L);
	}

}
