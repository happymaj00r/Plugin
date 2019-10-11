package myvcrime;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class Groupmanager {
	
	Plugin plugin;
	
	public enum Rank {
		
		NOVICE("Novice", new String[] {}),
		COLEADER("Co-Leader", new String[] {"kick","invite","withdraw","deposit"}),
		CUSTOM("CUSTOM", new String[] {}),
		LEADER("Leader", new String[] {"kick","invite","withdraw","deposit"});
			
		private String rank;
		private String[] permissions;
		
		private Rank(String rank, String[] permissions){
			this.rank = rank;
			this.permissions = permissions;
		}
		
		private String[] getPermissions(){
			return permissions;
		}
		
		private String getName(){
			return rank;
		}
	}
	
	
	public Groupmanager(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean addMembers(String gName, String pName){
		if(plugin.getConfig().contains("MyVCrime.Groups." + pName)){
			return true;
		} else {
			Bukkit.getPlayer(pName).sendMessage(ChatColor.RED + "Diese Spieler sind schon in dieser Gruppe");;
			
			return false;	
		}
	}
	
	public boolean createGroup(String name, String playerName){
		ArrayList<String> list = new ArrayList<String>();
		list.add(playerName);
		if(plugin.getConfig().contains("MyVCrime.Groups." + name))
		{
			Bukkit.getPlayer(playerName).sendMessage(ChatColor.RED + "Dieser Gruppenname existert schon!");
			return false;
		} else {
		plugin.getConfig().set("MyVCrime.SpielerProfile." + playerName + ".Group", name);
		plugin.getConfig().set("MyVCrime.Groups." + name + ".Members", list);
		plugin.getConfig().set("MyVCrime.Groups." + name + ".Konto", 0);
		plugin.saveConfig();
		return true;
		}
	}
	
	public Rank[] getRanks(){
		Rank[] s = {Rank.NOVICE,Rank.COLEADER,Rank.LEADER,Rank.CUSTOM};
		return s;
	}
	
	public String[] getPermissions(Rank s){
		return s.getPermissions();
	}
	
	public ArrayList<String> getAllMembers(String n){
		@SuppressWarnings("unchecked")
		ArrayList<String> list = (ArrayList<String>) plugin.getConfig().get("MyVCrime.Groups." + n);
		return list;
	}
		
	public boolean addMember(String gName, String pName){
		Player p = Bukkit.getPlayer(pName);
		if(plugin.getConfig().contains("MyVCrime.Groups." + gName + ".Members")){
			ArrayList<String> list = (ArrayList<String>) plugin.getConfig().getStringList("MyVCrime.Groups." + gName + ".Members");
			if(list.contains(pName)){
				p.sendMessage(ChatColor.RED + "Du bist schon in dieser Gruppe!");
				return false;
			} else {
				list.add(pName);
				plugin.getConfig().set("MyVCrime.SpielerProfile." + pName +".Group", gName);
				plugin.getConfig().set("MyVCrime.Groups." + gName + ".Members", list);
				plugin.saveConfig();
				return true;
			}
		} else {
			p.sendMessage(ChatColor.RED + "Diese Gruppe existiert nicht!");
			return false;
		}
	}
	
	public void setMoney(String n, int a){
		if(plugin.getConfig().contains("MyVCrime.Groups." + n)){
			plugin.getConfig().set("MyVCrime.Groups." + n + ".Konto", a);
			plugin.saveConfig();
		} 
	}
	
	public ArrayList<String> getGroups(){
		ArrayList<String> list = new ArrayList<String>();
		if(plugin.getConfig().contains("MyVCrime.GroupNames")){
			list = (ArrayList<String>) plugin.getConfig().getStringList("MyVCrime.GroupNames");
			return list;
		}
		return list;
	}
	
	public void addMoney(String n, int a){
		if(plugin.getConfig().contains("MyVCrime.Groups." + n)){
			int money = plugin.getConfig().getInt("MyVCrime.Groups." + n + ".Konto");
			money = money + a;
			plugin.getConfig().set("MyVCrime.Groups."+n+".Konto", money);
		}
	}
	
	public Integer getMoney(String n){
		if(plugin.getConfig().contains("MyVCrime.Groups."+n+".Konto")){
			int a = plugin.getConfig().getInt("MyVCrime.Groups."+n+".Konto");
			return a;
		} else {
			return 0;
		}
	}
	
	public String getGroupOfPlayer(String n){
		String name = plugin.getConfig().getString("MyVCrime.SpielerProfile." + n + ".Group");
		return name;
	}
	
	public void sendGroupMessage(String g,String m){
		List<String> list = this.getAllMembers(g);
		for(String s : list){
			Player p = Bukkit.getPlayer(s);
			p.sendMessage("[" + ChatColor.GREEN + "G" + ChatColor.WHITE + "] " + m);
		}
	}
	
	public boolean setGroupRank(String g, String p, Rank rank){
		if(plugin.getConfig().contains("MyVCrime.Groups." + g + "." + p)){
			plugin.getConfig().set("MyVCrime.Groups." + g + "." + p, rank.getName());
			plugin.saveConfig();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeMember(String gName, String pName){
		Player p = Bukkit.getPlayer(pName);
		if(plugin.getConfig().contains("MyVCrime.Groups." + gName + ".Members")){
			@SuppressWarnings("unchecked")
			ArrayList<String> list = (ArrayList<String>) plugin.getConfig().get("MyVCrime.Groups." + gName + ".Members");
			if(list.contains(pName)){
				list.remove(pName);
				plugin.getConfig().set("MyVCrime.Groups." + gName + ".Members", list);
				plugin.saveConfig();
				return true;
			} else {
				p.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht in der Gruppe!");
				return false;
			}
		} else {
			p.sendMessage("Diese Gruppe existiert nicht!");
			return false;
		}
	}
	
	public boolean removeGroup(String n){
		if(plugin.getConfig().contains("MyVCrime.Groups." + n)){
			plugin.getConfig().set("MyVCrime.Groups." + n, null);
			plugin.saveConfig();
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<String> getGroupss(){
		ArrayList<String> list = (ArrayList<String>) plugin.getConfig().getStringList("MyVCrime.Groups");
		return list;			     
	}
}
