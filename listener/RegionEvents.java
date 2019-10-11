package myvcrime.listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.mewin.WGRegionEvents.events.RegionEnterEvent;
import com.mewin.WGRegionEvents.events.RegionLeaveEvent;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.chat.Msg;
import myvcrime.raid.Raid;
import myvcrime.raid.RaidableShop;
import myvcrime.raid.ShopManager;
import myvcrime.raid.WaveRaid;
import myvcrime.timer.ComebackTimer;
import myvcrime.timer.TimerCondition;
import myvcrime.timer.TimerDontStayInRegions;
import myvcrime.timer.TimerRegionalCondition;
import net.md_5.bungee.api.ChatColor;

public class RegionEvents implements Listener{
	
	private HashMap<String,String> currentRegionMap = new HashMap<String, String>();
	
	Plugin plugin;
	ShopManager shopManager;
	CustomEventInstances CEI;
	String[] prisonRegions = {"prison","prison1","prison2","prison3","prison4","prison5","prison6","prison7","prison8","prison9","prison10"};
	
	public RegionEvents(Plugin plugin,CustomEventInstances CEI,ShopManager shopManager){
		this.shopManager = shopManager;
		this.plugin = plugin;
		this.CEI = CEI;
	}

	@EventHandler
	public void onRegionLeave(RegionLeaveEvent e){
		String region = e.getRegion().getId();
		final Player p = e.getPlayer();
		String name = p.getName();
		
		CEI.PrisonClass.onPlayerLeaveRegion(p, region);
	}
	
	private ArrayList<String> comebackTimerList = new ArrayList<String>();
	
	@EventHandler
	public void onRegionEnter(RegionEnterEvent e){
		String region = e.getRegion().getId();
		Player p = e.getPlayer();
		String name = p.getName();					
		
		CEI.PrisonClass.onPlayerEnterRegion(p, region);
			
		for(String s : prisonRegions){
			if(region.equalsIgnoreCase(s)){
				if(!p.getScoreboardTags().isEmpty()){
					if(p.getScoreboardTags().contains("handschellenEffect")){
						if(!comebackTimerList.contains(name)){
							if(currentRegionMap.containsKey(name)){
								if(SpielerProfil.prisoner.contains(name))
									return;
								String lastRegion = currentRegionMap.get(name);
								TimerCondition condition = new TimerDontStayInRegions(p,prisonRegions);
								comebackTimerList.add(name);
								p.sendMessage(ChatColor.RED + "Kehre innerhalb von 10 Sekunden zurück oder du wirst wieder Prisoner!");
								new ComebackTimer(condition,200L,plugin){

									@Override
									public void onTimeExpired() {
										Utility.sendTitle(p, ChatColor.RED + "Willkommen", ChatColor.RED + "" +ChatColor.BOLD + "zurück ;)");
										p.sendMessage(ChatColor.RED + "Willkommen zurück!");
										SpielerProfil.setPrisoner(p);
										comebackTimerList.remove(name);
										if(SpielerProfil.isFleeingPrisoner(name))
											SpielerProfil.setFleeingPrisoner(name, false);
									}

									@Override
									public void onConditionMet() {
										comebackTimerList.remove(name);
										p.sendMessage(ChatColor.GREEN + "Eingliederung abgebrochen!");
									}
									
								}.showCountdown(true);
							}
						}
					}
				}
			}
		}
		if(SpielerProfil.weaponInHand(p.getName())){	
			for(String s : main.shops){
				if(s.equalsIgnoreCase(region)){
					if(shopManager.getRaidableShop(s) != null){
						RaidableShop shop = shopManager.getRaidableShop(s);
						if(shop.isRaidable()){
							WaveRaid waveRaid = new WaveRaid(shop, p);
							waveRaid.start();
						} else {
							if(shop.hasRaid()){
								
							} else {
								Msg.INFO.out(p, shop.getName() + " got already robbed!");
							}
						}
					}
				}
			}
		}
		currentRegionMap.put(name, region);
	}
}
