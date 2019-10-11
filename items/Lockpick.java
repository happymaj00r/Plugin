package myvcrime.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import myvcrime.SpielerProfil;

public final class Lockpick implements CrimeItem {
	
	private ItemStack item;
	private Type type;
	
	public Lockpick(ItemStack item){
		this.item = item;
		
		if(Type.WOOD.getMaterial() == item.getType()){
			this.type = Type.WOOD;
		}
		if(Type.STONE.getMaterial() == item.getType()){
			this.type = Type.STONE;
		}
		if(Type.IRON.getMaterial() == item.getType()){
			this.type = Type.IRON;
		}
		if(Type.GOLDEN.getMaterial() == item.getType()){
			this.type = Type.GOLDEN;
		}
		if(Type.DIAMOND.getMaterial() == item.getType()){
			this.type = Type.DIAMOND;
		}
		if(type == null)
			this.type = Type.WOOD;
	}
	
	
	@Override
	public void use(Event e) {		
		if(e instanceof PlayerInteractEntityEvent){
			decuffRightclickedPlayer((PlayerInteractEntityEvent)e);
			reduceDurability();
		}
	}
	
	private void reduceDurability(){
		item.setDurability((short) (item.getDurability() + type.getMaxDurability()/4));
		if(item.getDurability() == type.getMaxDurability()){
			item.setAmount(0);
			item.setType(Material.AIR);
		}
	}

	private void decuffRightclickedPlayer(PlayerInteractEntityEvent e){
		if(e.getRightClicked() instanceof Player){
			Player clickedPlayer = (Player) e.getRightClicked();
			String clickedName = clickedPlayer.getName();
			if(SpielerProfil.isFleeingPrisoner(clickedName)){
				if(SpielerProfil.hasHandschellenEffect(clickedPlayer)){
					SpielerProfil.removeHandschellenEffect(clickedPlayer);
					SpielerProfil.setFleeingPrisoner(clickedName, false);
				}
			}
		}
	}
	
	private enum Type {
		
		WOOD(Material.WOOD_SPADE,60),
		STONE(Material.STONE_SPADE,132),
		IRON(Material.IRON_SPADE,251),
		GOLDEN(Material.GOLD_SPADE,33),
		DIAMOND(Material.DIAMOND_SPADE,1562);
		
		private Material mat;
		private int maxDurability;
		
		Type(Material mat, int maxDurability){
			this.mat = mat;
			this.maxDurability = maxDurability;
		}
		
		private Material getMaterial(){
			return mat;
		}
		
		private int getMaxDurability(){
			return maxDurability;
		}
	}
}
