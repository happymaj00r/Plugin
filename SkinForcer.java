package myvcrime;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkinForcer {
	
	Plugin plugin;
	
	public SkinForcer(Plugin plugin){
		this.plugin = plugin;
	}
	
	public void setSkin(Player p,String skinOf){
		String name = p.getName();
		clearGameProfile(p);
		Skin skin = getSkin(skinOf);
		setGameProperties(p,skin);
		updateSkin(p);
	}
	
	private Skin getSkin(String name){
		Skin skin = new Skin(getUUID(name));
		return skin;
	}
	
	private String getUUID(String name){
		return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replace("-", "");
	}
	
	private void updateSkin(final Player p){
		new BukkitRunnable(){
			public void run(){
				for(Player other : Bukkit.getOnlinePlayers()){
					other.hidePlayer(p);
				}
			}
		}.runTaskLater(plugin, 1);
		
		
		
		new BukkitRunnable(){
			public void run(){
				for(Player other : Bukkit.getOnlinePlayers()){
					other.showPlayer(p);
				}
			}
		}.runTaskLater(plugin, 1);
	}
	
	private void setStandardSkin(){
		
	}
	
	private void test(Player p){
	}
	
	private void setGameProperties(Player p, Skin skin){
		CraftPlayer cp = (CraftPlayer) p;
		GameProfile gp = cp.getProfile();
		gp.getProperties().put(skin.getSkinName(), new Property(skin.getSkinName(),skin.getSkinValue(),skin.getSkinSignatur()));
	}
	
	private void clearGameProfile(Player p){
		CraftPlayer cp = (CraftPlayer) p;
		GameProfile gp = cp.getProfile();
		gp.getProperties().clear();
	}
}
