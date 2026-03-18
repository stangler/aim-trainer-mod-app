package com.aimtrainer.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityAimDummy extends EntityCreature {

    public enum Difficulty {
        EASY   (0.07f, 50, 100),
        NORMAL (0.14f, 28,  50),
        HARD   (0.22f, 15,  20);

        public final float speed;
        public final int   changeTicks;
        public final int   jumpChance;

        Difficulty(float speed, int changeTicks, int jumpChance) {
            this.speed       = speed;
            this.changeTicks = changeTicks;
            this.jumpChance  = jumpChance;
        }
    }

    private Difficulty difficulty = Difficulty.NORMAL;
    private double moveX  = 0;
    private double moveZ  = 1;
    private int    moveTick = 0;

    public EntityAimDummy(World world) {
        super(world);
        this.setSize(0.6f, 1.8f);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.setHealth(200f);
        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();
        randomizeDirection();
    }

    public void setDifficulty(Difficulty diff) { this.difficulty = diff; }
    public Difficulty getDifficulty()          { return difficulty; }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.setHealth(this.getMaxHealth());

        moveTick++;
        int interval = difficulty.changeTicks + rand.nextInt(difficulty.changeTicks / 2 + 1);
        if (moveTick >= interval) {
            moveTick = 0;
            randomizeDirection();
        }

        this.motionX = moveX * difficulty.speed;
        this.motionZ = moveZ * difficulty.speed;

        if (this.onGround && rand.nextInt(difficulty.jumpChance) == 0) {
            this.motionY = 0.42;
        }

        this.rotationYaw     = (float) Math.toDegrees(Math.atan2(-moveX, moveZ));
        this.rotationYawHead = this.rotationYaw;
        this.prevRotationYaw = this.rotationYaw;
    }

    private void randomizeDirection() {
        double angle = rand.nextDouble() * Math.PI * 2;
        moveX = Math.sin(angle);
        moveZ = Math.cos(angle);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) { return false; }

    @Override protected String getLivingSound()                    { return null; }
    @Override protected String getHurtSound()                      { return null; }
    @Override protected String getDeathSound()                     { return null; }
    @Override protected boolean canTriggerWalking()                { return false; }
    @Override public    boolean canBePushed()                      { return false; }
    @Override public    boolean canBeCollidedWith()                { return true; }
    @Override protected void   dropFewItems(boolean b, int i)     {}
    @Override protected void   dropEquipment(boolean b, int i)    {}
}