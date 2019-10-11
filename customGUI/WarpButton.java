package myvcrime.customGUI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import myvcrime.SpielerProfil;
import myvcrime.Utility;
import myvcrime.main;
import myvcrime.abilities.Effects;
import myvcrime.chat.Msg;

public class WarpButton extends InventoryButton {

	private WarpSetterButton warpSetterButton;
	private boolean warpInProgress = false;
	
	public WarpButton(ItemStack is, WarpSetterButton warpSetterButton) {
		super(is);
		this.warpSetterButton = warpSetterButton;
	}
	
	@Override
	public void click(Player p){
		if(SpielerProfil.getWarp(p, warpSetterButton.getUniqueWarpName()) != null && warpInProgress == false){
			warpInProgress = true;
			Location targetLocation = SpielerProfil.getWarp(p, warpSetterButton.getUniqueWarpName());
			Effects effects = new Effects(main.plugin);
			effects.teleportEffect(targetLocation, 6*20);
			effects.teleportEffect(p.getLocation(), 6*20);
			new BukkitRunnable(){
				int counter = 0;
				Location stayLoc = p.getLocation().clone();
				Location targetFinalLocation = targetLocation.clone();
				@Override
				public void run() {
					counter++;
					
					if(!(p.getLocation().getBlockX() == stayLoc.getBlockX() && p.getLocation().getBlockY() == stayLoc.getBlockY() && p.getLocation().getBlockZ() == stayLoc.getBlockZ())){
						Msg.ALERT.out(p, "Warp cancelled (Dont move!)");
						this.cancel();
						cancelEffectTask(effects, targetFinalLocation);
						cancelEffectTask(effects, stayLoc);
						warpInProgress = false;
					} else {	
						if(counter >= 6){
							p.teleport(targetFinalLocation.add(0, 0.2, 0));
							this.cancel();
							warpInProgress = false;
							Utility.sendTitle(p, "", "");
						} else {
							Utility.sendTitle(p, "Warp in..", "" + (6 - counter), 0, 20, 0);
						}
					}
				}
				
			}.runTaskTimer(main.plugin, 20, 20);
		}
	}
	
	public void cancelEffectTask(Effects effects, Location loc){
		for(String locElement : effects.teleportEffectTasks.keySet()){
			if(locElement.equalsIgnoreCase(Utility.serializeLocation(loc))){
				effects.teleportEffectTasks.get(locElement).cancel();
			}
		}
	}

}
