package myvcrime.chat;

import org.bukkit.entity.Player;

import myvcrime.Utility;
import net.md_5.bungee.api.ChatColor;

public enum Msg {
	
	WARN{
		public void out(Player p, String msg){
			String m = ChatColor.RED + "" + ChatColor.BOLD + "[!] " + ChatColor.WHITE + msg;
			p.sendMessage(m);
		}
	},
	INFO{
		public void out(Player p,String msg){
			String m = ChatColor.BLUE + "" + ChatColor.BOLD + "[!] " + ChatColor.WHITE + msg;
			p.sendMessage(m);
		}
	},
	ALERT{
		public void out(Player p, String msg){
			Utility.sendTitle(p, ChatColor.RED + "" + ChatColor.BOLD + "[!]", "",1L,1L,1L);
			String m = ChatColor.RED + "" + ChatColor.BOLD + "[!] " + ChatColor.WHITE + msg;
			p.sendMessage(m);
		}
	},
	SUCCESS{
		public void out(Player p,String msg){
			String m = ChatColor.GREEN + "" + ChatColor.BOLD + "[!] " + ChatColor.WHITE + msg;
			p.sendMessage(m);
		}
	},
	QUESTION{
		public void out(Player p,String msg){
			String m = ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "[?] " + ChatColor.WHITE + msg;
			p.sendMessage(m);
		}
	};
	
	public abstract void out(Player p,String msg);
}
