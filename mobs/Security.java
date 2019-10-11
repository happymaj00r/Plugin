package myvcrime.mobs;

import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.Items;
import net.minecraft.server.v1_12_R1.World;

public class Security extends EntityZombie {

	public Security(World world) {
		super(world);
		this.setCustomName("Cop");
		this.setCustomNameVisible(true);
		this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.STICK));
		this.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		this.setSlot(EnumItemSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
		this.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
		this.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
	}

}
