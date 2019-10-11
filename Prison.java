package myvcrime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.potionEffects.CustomPotionEffectType;

public class Prison {
	
	String[] prisonRegions = {"prison","prison1","prison2","prison3","prison4","prison5","prison6","prison7","prison8","prison9","prison10"};
	
	Plugin plugin;
	
	public Prison(Plugin plugin){
		this.plugin = plugin;
	}
	
	public boolean onPlayerDamageByPlayer(Player p,Player attacker){
		Set<String> tags = attacker.getScoreboardTags();
		if(tags.contains("handschellenEffect")){
			return true;
		}
		return false;
	}
	
	private ArrayList<String> playerLeftPrisonList = new ArrayList<String>();
	
	public boolean onPlayerLeaveRegion(Player p, String region){
		String name = p.getName();
		if(SpielerProfil.prisoner.contains(name)){
			for(String s : prisonRegions)
			{
				if(s.equalsIgnoreCase(region)){
					if(!playerLeftPrisonList.contains(name)){
						playerLeftPrisonList.add(name);
						List<String> list = new ArrayList<String>();
						for(String f : prisonRegions){
							list.add(f);
						}
						
						new BukkitRunnable(){
							@Override
							public void run() {
								playerLeftPrisonList.remove(name);
								if(!Utility.isInRegions(p.getLocation(), list) && SpielerProfil.prisoner.contains(name))
									onPrisonBreak(p,name);
							}					
						}.runTaskLater(plugin, 20L);
					}
				}
			}
		}
		return false;
	}
	
	public boolean onPlayerEnterRegion(Player p, String region){
		return false;
	}
	
	
	public void onPrisonBreak(Player p, String name){
		ItemStack freiticket = CustomItems.getCustomItem(CustomItemType.FREIBRIEF);
		if(p.getInventory().contains(freiticket)){
			p.getInventory().remove(freiticket);
		}
		if(SpielerProfil.prisoner.contains(name)){
			SpielerProfil.prisoner.remove(name);
			SpielerProfil.setRank(name, "Citizen");
			SpielerProfil.setSpawnByRank(name);
			SpielerProfil.setFleeingPrisoner(name, true);
		}
		if(SpielerProfil.hasHandschellenEffect(p) && !p.hasPotionEffect(PotionEffectType.SLOW)){
			PotionEffect effect = new PotionEffect(CustomPotionEffectType.HANDSCHELLEN.getType(), 1200*120, 1);
			p.addPotionEffect(effect);
		}
	}
	
}
