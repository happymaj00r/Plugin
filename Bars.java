package myvcrime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.gmail.filoghost.holographicdisplays.api.Hologram;

import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class Bars {
	
	private HashMap<String, String> bars = new HashMap<String, String>();
	private HashMap<String, String> barsInRaid = new HashMap<String, String>();
	private ArrayList<Inventory> list = new ArrayList<Inventory>();
	private HashMap<String, Integer> barsMoney = new HashMap<String, Integer>();
	public HashMap<String, Integer> barsCooldown = new HashMap<String, Integer>();
	private HashMap<String, Hologram> barHologram = new HashMap<String, Hologram>();
	
	main plugin;
	
	Bars(main plugin){
		this.plugin = plugin;
		createInventories();
		startScheduler();
	}
	
	private void createInventories(){
		for(String s : plugin.bars){
			Inventory inv = Bukkit.createInventory(null, 9, s);
			list.add(inv);
		}
	}
	
	private void changeBarHologram(String bar, String text, Location loc){
		deleteBarHologram(bar);
		setBarHologram(bar,text,loc);
	}
	
	private void setBarHologram(String bar, String text, Location loc){
		Hologram holo = (Hologram) HolographicDisplaysAPI.createHologram(plugin, loc, text);
		barHologram.put(bar, holo);
	}
	
	private void deleteBarHologram(String bar){
		if(barHologram.containsKey(bar)){
			barHologram.get(bar).delete();
			barHologram.remove(bar);
		}
	}
	
	private void resetRaid(String bar){
		barsInRaid.remove(bar);
	}
	
	public void raidBar(final Player p,final String bar){
			final String owner = getOwner(bar);
			String title = owner != "" ? ChatColor.YELLOW + owner + ChatColor.WHITE + " weiß bescheid!" : ChatColor.GREEN + "Kein Barbesitzer";
			String subTitle = owner != "" ? "Halte 5 Minuten durch" : "Sie gehört dir in einer Minute";
			Utility.sendTitleDelayed(p, title, subTitle, 20L);
			if(owner != ""){
				Bukkit.getPlayer(owner).sendMessage(ChatColor.RED + "Der Spieler " + ChatColor.YELLOW + p.getName() + " versucht deine Bar[" + bar + "] einzunehmen!");
			}
			new BukkitRunnable(){
				int timeLeft = owner != "" ? 300 : 60;
				int secondCounter = 0;
				int minuteCounter = 0;
				public void run(){
					if(Utility.isInRegion(p.getLocation(), bar)){
						
						timeLeft--;
						secondCounter++;
						if(secondCounter >= 60){
							secondCounter = 0;
							minuteCounter++;
							int restMinutes = 5 - minuteCounter;
							if(restMinutes <= 0){
								if(owner != "")
									Bukkit.getPlayer(owner).sendMessage(ChatColor.RED + "In " + restMinutes + " Minuten ist deine Bar eingenommen!");
							}							
							p.sendMessage(ChatColor.GREEN + "Noch " + restMinutes + " Minuten");
						}
						
						if(timeLeft <= 0){
							Utility.sendTitle(p, ChatColor.GREEN + "Eingenommen!", "Das Schutzgeld gehört nun dir!");
							p.sendMessage(ChatColor.GREEN + bar + " eingenommen!");
							setOwner(bar, p.getName());
							resetRaid(bar);
							this.cancel();
						}
						
					} else {
						Utility.sendTitle(p,ChatColor.RED + "Fehlgeschlagen", "");
						resetRaid(bar);
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 20, 20);
	}
	
	public boolean isBarInRaid(String bar){
		return barsInRaid.containsKey(bar);
	}
	
	public boolean isBar(String bar){
		for(String s : plugin.bars){
			if(s.equalsIgnoreCase(bar)) return true;
		}
		
		return false;
	}
	
	public void attemptToRaid(Player p, String bar){
		if(!isBarInRaid(bar)){
			if(!getOwner(bar).equalsIgnoreCase(p.getName())){
				startDecisionTime(p,bar);
				barsInRaid.put(bar, p.getName());
			}
		}
	}
	
	public void startDecisionTime(final Player p, final String bar) {
		String owner = getOwner(bar);
		String title = owner == "" ? ChatColor.RED + "Du bist dabei die Bar einzunehmen" : ChatColor.RED + "Du bist dabei die Bar von " + ChatColor.YELLOW + owner + ChatColor.RED + " einzunehmen!";
		Utility.sendTitle(p, "", title);
		new BukkitRunnable(){
			int timeLeft = 10;
			public void run(){
				if(Utility.isInRegion(p.getLocation(), bar)){
					timeLeft--;
					Utility.sendTitle(p,ChatColor.RED +"" + timeLeft, "");
					if(timeLeft<= 0){
						raidBar(p,bar);
						this.cancel();
					}
				} else {
					resetRaid(bar);
					
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 40L, 20L);
	}
		
	public ArrayList<Inventory> getInventories(){
		return list;
	}
	
	public boolean isOwner(String bar,String name){
		if(bars.get(bar).contains(name)) return true;
		return false;
	}
	
	public String getOwner(String bar){
		if(bars.containsKey(bar)){
			return bars.get(bar);
		} else {
			return "";
		}
	}
	
	public void setOwner(String bar, String name){
		if(bars.containsKey(bar))
			bars.remove(bar);
		
		bars.put(bar, name);
		barsCooldown.put(bar, 0);
		barsMoney.put(bar, 0);
	}
	
	public void removeListEntries(String bar){
		if(bars.containsKey(bar)){
			bars.remove(bar);
			barsCooldown.remove(bar, 0);
			barsMoney.remove(bar);
		}
	}
	
	public void removeOwner(String bar){
		removeListEntries(bar);
	}
	
	public Inventory getBarInventory(String bar){		
		for(Inventory inv : list){
			if(bar.equalsIgnoreCase(inv.getTitle())){				
				return inv;
			}
		}
		return null;
	}
	
	public String getBarOfNPC(Location loc){	
		List<String> list = new ArrayList<String>();
		for(String s : plugin.bars){
			list.add(s);
		}
		if(Utility.isInRegions(loc, list)){
			for(String s : plugin.bars){
				if(Utility.isInRegion(loc, s)) return s;
			}
		}
		return "";
	}
	
	public void addMoneyOfBar(String bar, int amount){
		for(Inventory inv : list){
			String title = inv.getTitle();
			if(title.equalsIgnoreCase(bar)){
				inv.addItem(new ItemStack(Material.GOLD_NUGGET,amount));
			}
		}
	}
	
	public int getMoneyOfBar(String bar){
		for(Inventory inv : list){
			String title = inv.getTitle();
			if(title.equalsIgnoreCase(bar)){
				if(inv.contains(Material.GOLD_NUGGET)){
					return Utility.countItems(Material.GOLD_NUGGET, inv);
				}
			}
		}
		return 0;
	}
	
	public void startScheduler(){
		new BukkitRunnable(){
			public void run(){
				for(String bar : bars.keySet()){
					int cd = barsCooldown.get(bar);
					int money = getMoneyOfBar(bar);
					if(money < 60){
						cd++;
						if(cd >= 5){
							if(money + 10 > 60){
								int diff = 60 - money;
								addMoneyOfBar(bar,diff);
							} else {
								addMoneyOfBar(bar,10);
							}
							barsCooldown.put(bar, 0);
						} else {
							barsCooldown.put(bar,cd);
						}
					}
				}
			}
		}.runTaskTimer(plugin, 20L, 1200L); //1200
	}
}
