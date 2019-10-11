package myvcrime;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_12_R1.util.UnsafeList;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityIronGolem;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPigZombie;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_12_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_12_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_12_R1.World;



public class CustomEntityZombieCop extends EntityZombie implements Cop{

	boolean raid;
	public Long spawnTime;
	boolean engaged;
	String raidTarget;
	String at;
	
	public CustomEntityZombieCop(World world,boolean raid) {
		super(world);
		
		spawnTime = System.currentTimeMillis();
		this.raid = raid;
		if(raid) engaged = true;
		this.setCustomName("Cop");
		this.setCustomNameVisible(true);	
		
		try {
			Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
			bField.setAccessible(true);
			Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
			cField.setAccessible(true);
			bField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			bField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(goalSelector, new UnsafeList<PathfinderGoalSelector>());
			cField.set(targetSelector, new UnsafeList<PathfinderGoalSelector>());
			} catch (Exception exc) {
			exc.printStackTrace();
			}
		
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, 1.0D, false));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 2.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.setSize(0.6F, 1.95F);
        n();
	}
	@Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(1.0D);
    }
	@Override
	public String getCopType() {
		return "Chief";
	}
	@Override
	public boolean isRaidSpawn() {
		return raid;
	}
	@Override
	public Long getSpawnTime() {
		return spawnTime;
	}
	@Override
	public boolean isEngaged() {
		return engaged;
	}
	
	public void setAt(String name){
		at = name;
	}
	
	public void removeTarget(){
		this.setGoalTarget(null);
	}
	
	@Override
	public void n() {
        if ( world.spigotConfig.zombieAggressiveTowardsVillager ) this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true)); // Spigot
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class}));
        if ( world.spigotConfig.zombieAggressiveTowardsVillager ) this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, false)); // Spigot
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
        if(raid==false){
        } else {
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        }
    }
	@Override
	public void setRaidTarget(EntityLiving livingPlayer) {
		this.setGoalTarget(livingPlayer, TargetReason.CLOSEST_PLAYER, false);
	}
	@Override
	public String getRaidTarget() {
		return raidTarget;
	}
	
	@Override
	public String getAt() {
		return at;
	}
	@Override
	public void setAt() {
		// TODO Auto-generated method stub
		
	}
}
