package keri.ninetaillib.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class EntityProjectileBase extends EntityArrow {

    protected int blockX = -1;
    protected int blockY = -1;
    protected int blockZ = -1;
    protected Block inBlock;
    protected int inMeta;
    public boolean inGround;
    public int ticksInGround;
    public int ticksInAir;
    private int tickLimit = 40;
    private static final DataParameter<String> dataMarkerShooter = EntityDataManager.<String>createKey(EntityProjectileBase.class, DataSerializers.STRING);

    public EntityProjectileBase(World world){
        super(world);
        this.setSize(.125f, .125f);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityProjectileBase(World world, double x, double y, double z, double ax, double ay, double az){
        super(world);
        this.setSize(0.125F, 0.125F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    public EntityProjectileBase(World world, EntityLivingBase living, double ax, double ay, double az){
        super(world);
        this.setSize(0.125F, 0.125F);
        this.setLocationAndAngles(living.posX, living.posY + living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = this.motionY = this.motionZ = 0.0D;
        this.motionX = ax;
        this.motionY = ay;
        this.motionZ = az;
        this.shootingEntity = living;
        this.setShooterSynced();
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2 * 1.5F, 1.0F);
        this.pickupStatus = PickupStatus.DISALLOWED;
    }

    @Override
    protected void entityInit(){
        super.entityInit();
        this.dataManager.register(dataMarkerShooter, "");
    }

    public void setTickLimit(int limit){
        this.tickLimit = limit;
    }

    public void setShooterSynced(){
        this.dataManager.set(dataMarkerShooter, this.shootingEntity.getName());
    }

    public EntityLivingBase getShooterSynced(){
        return this.worldObj.getPlayerEntityByName(this.dataManager.get(dataMarkerShooter));
    }

    public Entity getShooter(){
        return shootingEntity;
    }

    @Override
    protected ItemStack getArrowStack(){
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onUpdate(){
        if(this.getShooter() == null && this.worldObj.isRemote){
            this.shootingEntity = getShooterSynced();
        }

        this.onEntityUpdate();
        BlockPos blockpos = new BlockPos(this.blockX, this.blockY, this.blockZ);
        IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
        Block block = iblockstate.getBlock();

        if(block.getMaterial(iblockstate) != Material.AIR){
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBox(iblockstate, this.worldObj, blockpos);
            if(axisalignedbb != null && axisalignedbb.isVecInside(new Vec3d(this.posX, this.posY, this.posZ))){
                this.inGround = true;
            }
        }

        super.onUpdate();

        if(this.inGround){
            int j = block.getMetaFromState(iblockstate);

            if(block==this.inBlock && j==this.inMeta){
                this.ticksInGround++;

                if (this.ticksInGround>=getMaxTicksInGround()){
                    this.setDead();
                }
            }
            else{
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }
        }
        else{
            ++this.ticksInAir;

            if(ticksInAir >= tickLimit){
                this.setDead();
                return;
            }

            Vec3d currentPos = new Vec3d(this.posX, this.posY, this.posZ);
            Vec3d nextPos = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            RayTraceResult mop = this.worldObj.rayTraceBlocks(currentPos, nextPos, false, true, false);
            currentPos = new Vec3d(this.posX, this.posY, this.posZ);

            if(mop != null){
                nextPos = new Vec3d(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
            }
            else{
                nextPos = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            }

            if(mop == null || mop.entityHit == null){
                Entity entity = null;
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
                double d0 = 0.0D;

                for (int i = 0; i < list.size(); ++i){
                    Entity entity1 = (Entity)list.get(i);

                    if(entity1.canBeCollidedWith() && (!entity1.isEntityEqual(this.shootingEntity) || this.ticksInAir > 5)){
                        float f = 0.3F;
                        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f, (double)f, (double)f);
                        RayTraceResult movingobjectposition1 = axisalignedbb.calculateIntercept(currentPos, nextPos);

                        if (movingobjectposition1 != null){
                            double d1 = currentPos.distanceTo(movingobjectposition1.hitVec);

                            if (d1 < d0 || d0 == 0.0D){
                                entity = entity1;
                                d0 = d1;
                            }
                        }
                    }
                }

                if(entity != null){
                    mop = new RayTraceResult(entity);
                }
            }

            if(mop!=null){
                if(!this.isBurning() && this.canIgnite() && mop.entityHit != null && mop.entityHit.isBurning()){
                    this.setFire(3);
                }

                if(mop.entityHit instanceof EntityLivingBase){
                    this.onImpact(mop);
                    this.setDead();
                }
                else if(mop.typeOfHit == RayTraceResult.Type.BLOCK){
                    this.onImpact(mop);
                    this.blockX = mop.getBlockPos().getX();
                    this.blockY = mop.getBlockPos().getY();
                    this.blockZ = mop.getBlockPos().getZ();
                    IBlockState state = this.worldObj.getBlockState(mop.getBlockPos());
                    this.inBlock = state.getBlock();
                    this.inMeta = inBlock.getMetaFromState(state);
                    this.motionX = mop.hitVec.xCoord - this.posX;
                    this.motionY = mop.hitVec.yCoord - this.posY;
                    this.motionZ = mop.hitVec.zCoord - this.posZ;
                    float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                    this.inGround = true;
                    if(this.inBlock.getMaterial(state) != Material.AIR){
                        this.inBlock.onEntityCollidedWithBlock(this.worldObj, mop.getBlockPos(), state, this);
                    }
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float motion = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)motion) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
            while (this.rotationPitch - this.prevRotationPitch >= 180.0F){
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F){
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F){
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float movementDecay = getMotionDecayFactor();

            if(this.isInWater()){
                for(int j = 0; j < 4; ++j){
                    float f3 = 0.25F;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
                }
                movementDecay *= 0.8F;
            }

            this.motionX *= movementDecay;
            this.motionY *= movementDecay;
            this.motionZ *= movementDecay;
            this.motionY -= getGravity();
            this.setPosition(this.posX, this.posY, this.posZ);
            this.doBlockCollisions();
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player){}

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double range){
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return range < d1 * d1;
    }

    public double getGravity(){
        return 0.05F;
    }

    public boolean canIgnite(){
        return false;
    }

    public int getMaxTicksInGround(){
        return 100;
    }

    protected float getMotionDecayFactor(){
        return 0.99F;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag){
        super.writeEntityToNBT(tag);
        tag.setShort("xTile", (short)this.blockX);
        tag.setShort("yTile", (short)this.blockY);
        tag.setShort("zTile", (short)this.blockZ);
        tag.setByte("inTile", (byte)Block.getIdFromBlock(this.inBlock));
        tag.setByte("inMeta", (byte)this.inMeta);
        tag.setByte("inGround", (byte)(this.inGround ? 1 : 0));

        if(this.shootingEntity != null){
            tag.setString("shootingEntity", this.shootingEntity.getName());
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag){
        super.readEntityFromNBT(tag);
        this.blockX = tag.getShort("xTile");
        this.blockY = tag.getShort("yTile");
        this.blockZ = tag.getShort("zTile");
        this.inBlock = Block.getBlockById(tag.getByte("inTile") & 255);
        this.inGround = tag.getByte("inGround") == 1;

        if(this.worldObj != null){
            this.shootingEntity = this.worldObj.getPlayerEntityByName(tag.getString("shootingEntity"));
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float f){
        return false;
    }

    public abstract void onImpact(RayTraceResult mop);

}
