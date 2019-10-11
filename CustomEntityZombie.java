package myvcrime;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

import myvcrime.listener.Jobs;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPigZombie;
import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.ItemStack;
import net.minecraft.server.v1_12_R1.Items;
import net.minecraft.server.v1_12_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_12_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_12_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_12_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_12_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_12_R1.World;



public class CustomEntityZombie extends EntityZombie implements Cop{

	public boolean raid;
	public Long spawnTime;
	boolean engaged;
	String raidTarget;
	String at;
	
	
	public enum Type
	{		
		CHIEF("Chief",1,1.0) {
			@Override
			void arm(CustomEntityZombie z) {
				z.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				z.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				z.setSlot(EnumItemSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				z.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
				z.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
			}
		},
		SMALL_COP("Cop",1,1.0) {
			@Override
			void arm(CustomEntityZombie z) {
				z.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.STICK));
				z.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				z.setSlot(EnumItemSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				z.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
				z.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
			}
		},
		MIDDLE_COP("Officer",1,1.0) {
			@Override
			void arm(CustomEntityZombie z) {
				z.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				z.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
				z.setSlot(EnumItemSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));
				z.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
				z.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
			}
		},
		BIG_COP("Swat",1,1.0) {
			@Override
			void arm(CustomEntityZombie z) {
				z.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				z.setSlot(EnumItemSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
				z.setSlot(EnumItemSlot.FEET, new ItemStack(Items.IRON_BOOTS));
				z.setSlot(EnumItemSlot.HEAD, new ItemStack(Items.IRON_HELMET));
				z.setSlot(EnumItemSlot.LEGS, new ItemStack(Items.IRON_LEGGINGS));
			}
		};
		
		private String name;
		private int speed;
		private double size;
		
		private Type(String name, int speed, double size){
			this.name = name;
			this.speed = speed;
			this.size = size;
		}
		
		public String getName(){
			return name;
		}
		
		public int getSpeed(){
			return speed;
		}
		
		abstract void arm(CustomEntityZombie z);
	}
	
    private static final DataWatcherObject<Boolean> bx = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.h);
    private static final DataWatcherObject<Integer> by = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> bz = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.h);

	private Type type;
	public CustomEntityZombie(World world,boolean raid,Type type) {
		super(world);
		this.type = type;
		this.setCustomPaths();
		spawnTime = System.currentTimeMillis();
		this.raid = raid;
		if(raid) engaged = true;
		this.setCustomName(type.getName());
		this.setCustomNameVisible(true);	
		type.arm(this);
	}
	
	public CustomEntityZombie(World world) {
		super(world);
		this.raid = false;
		int rand = Jobs.zufall(0, 50);
		if(rand == 25){
			this.type = Type.CHIEF;
			Utility.addWolfsAfterInit(this);
		} else if (rand >= 20 && rand <= 24){
			this.type = Type.MIDDLE_COP;
		} else {
			this.type = Type.SMALL_COP;
		}
		spawnTime = System.currentTimeMillis();
		if(raid) engaged = true;
		this.setCustomName(type.getName());
		this.setCustomNameVisible(true);	
		type.arm(this);
		
	}
		
	protected void setEquipment(int i, ItemStack itemStack) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public boolean canSpawn() {
        return true;
    }
	
	@Override
    public boolean P() {
		return true;
    }
	
	protected void setCustomPaths(){
        if(this.type.getName() != "Chief"){
        	this.goalSelector.a(2, new CustomPathfinderGoalZombieAttack(this, 1.0D, false));
        } else {
        	//this.goalSelector.a(2, new CustomPathfinderGoalZombieAttack(this, 1.0D, false));
        	this.goalSelector.a(2, new CustomPathfinderGoalZombieAttack(this, 1.0D, false));
        }
	}
	
	private CustomPathfinderGoalZombieAttack aggroFinder;
	
	@Override
    protected void r() {
    	this.goalSelector.a(2, aggroFinder = new CustomPathfinderGoalZombieAttack(this, 1.0D, false));
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.do_();
    }
	
	public void removeAggression(){
		this.setGoalTarget((EntityLiving) null);
	}	
	
	@Override
    protected void do_() {
        this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class}));
        if(this.raid){
        	 this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        } else {
        	 this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));	
        }
    }
	
	@Override
    protected void i() {
		super.i();
    }
	
	@Override
	public String getCopType() {
		return type.getName();
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
	
	@Override
	public String toString(){
		return this.type.toString();
	}

}
