package myvcrime;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import net.md_5.bungee.api.ChatColor;


public class GUI {
	
	Plugin plugin;
	
	public GUI(Plugin plugin){
		this.plugin = plugin;
	}
	
	public Set<String> getScoreBoardEntries(Player p){
		Scoreboard board = p.getScoreboard();
		Set<String> list = board.getEntries();
		return list;
	}
	
	public void updateOf(Player p){
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective objective = sb.registerNewObjective(ChatColor.WHITE +  "" + ChatColor.BOLD +"MyV" + ChatColor.RED + ""+ ChatColor.BOLD +"Crime", "myvcrimeboard");		
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.getScore("   ").setScore(8);
		objective.getScore(ChatColor.GREEN +""+ ChatColor.BOLD + "Money").setScore(7);
		objective.getScore(ChatColor.GREEN + "" + ChatColor.BOLD + ">> " + ChatColor.RESET + "" +ChatColor.WHITE + "$" + main.economy.getBalance(p.getName())).setScore(6);
		objective.getScore("  ").setScore(5);
		if(SpielerProfil.prisoner.contains(p.getName()) || SpielerProfil.isFleeingPrisoner(p.getName())){
			objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + "Jailtime").setScore(4);
			objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.RESET + "" + ChatColor.WHITE + "" + SpielerProfil.getCrime(p.getName())/10 + " Minuten").setScore(3);	
		} else {
			objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Crime").setScore(4);
			objective.getScore(ChatColor.RED + "" + ChatColor.BOLD + ">> " + ChatColor.RESET + "" + ChatColor.WHITE + "" + SpielerProfil.getCrime(p.getName())).setScore(3);
		}
		objective.getScore(" ").setScore(2);
		objective.getScore(ChatColor.AQUA + "myvcrime.de").setScore(1);
		p.setScoreboard(sb);
	}
	
	
}
