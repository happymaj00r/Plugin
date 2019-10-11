package myvcrime.customGUI;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import myvcrime.abilities.AbilityItems;
import myvcrime.abilities.Superhuman;
import myvcrime.abilities.SuperhumanManager;
import myvcrime.abilities.UpgradeManager;

public class UpgradeButton extends InventoryButton {

	private AbilityItems abilityItem;
	
	public UpgradeButton(ItemStack is,AbilityItems abilityItem) {
		super(is);
		this.abilityItem = abilityItem;
	}
	
	@Override
	public void click(Player p){
		Superhuman superhuman = SuperhumanManager.getSuperhuman(p);
		if(UpgradeManager.isBought(superhuman, abilityItem)){
			UpgradeManager.setUpgrade(superhuman, abilityItem);
			UpgradeManager.getUpgradeInventory(SuperhumanManager.getSuperhuman(p)).open(p);
		} else {
			UpgradeManager.buyAbilityItem(superhuman, abilityItem);
			UpgradeManager.getUpgradeInventory(superhuman).open(p);
		}
	}
}
