/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.connected;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.Vertex5;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

@SideOnly(Side.CLIENT)
public class ConnectedRenderContext {

    private static final double[] U = new double[]{-1D, 1D, 1D, -1D};
    private static final double[] V = new double[]{1D, 1D, -1D, -1D};
    private IBlockAccess world;
    private IBlockState currentBlockState;
    private boolean changeBounds = false;
    private List<Vertex5> vertices = Lists.newArrayList();

    public void setBlockAccess(IBlockAccess world){
        this.world = world;
    }

    public void setChangeBounds(boolean changeBounds){
        this.changeBounds = changeBounds;
    }

    public void setCurrentBlockState(IBlockState state){
        this.currentBlockState = state;
    }

    public CCModel getModel(){
        CCModel model = CCModel.quadModel(this.vertices.size());

        for(int i = 0; i < this.vertices.size(); i++){
            model.getVertices()[i] = this.vertices.get(i);
        }

        return model.computeNormals();
    }

    public List<Vertex5> getVertices(){
        return this.vertices;
    }

    private void renderSide(Vec3d pos, Vec3d posO, Vec3d posA, Vec3d posB, TextureAtlasSprite[] texture, EnumFacing side, Vec3d posR){
        byte[] i = new byte[4];
        boolean areSame = true;
        double ox = posO.xCoord * 2D - 1D;
        double oy = posO.yCoord * 2D - 1D;
        double oz = posO.zCoord * 2D - 1D;

        for(int j = 0; j < 4; j++){
            i[j] = this.getType(side, pos, posA.scale(U[j]), posB.scale(V[j]), new Vec3d(ox, oy, oz));

            if((areSame) && (j > 0) && (i[j] != i[0])){
                areSame = false;
            }
        }

        if(areSame){
            for(int j = 0; j < 4; j++){
                double cx = posR.xCoord + ox + U[j] * posA.xCoord * 0.5D + V[j] * posB.xCoord * 0.5D;
                double cy = posR.yCoord + oy + U[j] * posA.yCoord * 0.5D + V[j] * posB.yCoord * 0.5D;
                double cz = posR.zCoord + oz + U[j] * posA.zCoord * 0.5D + V[j] * posB.zCoord * 0.5D;
                double offsetX = 0;
                double offsetY = 0;
                double offsetZ = 0;

                if(this.changeBounds){
                    switch(side.getIndex()){
                        case 0:
                            offsetY -= 0.0004D;
                            break;
                        case 1:
                            offsetY += 0.0004D;
                            break;
                        case 2:
                            offsetZ -= 0.0004D;
                            break;
                        case 3:
                            offsetZ += 0.0004D;
                            break;
                        case 4:
                            offsetX -= 0.0004D;
                            break;
                        case 5:
                            offsetX += 0.0004D;
                            break;
                    }
                }

                this.vertices.add(new Vertex5(cx + offsetX, cy + offsetY, cz + offsetZ, texture[i[0]].getInterpolatedU(16.0D - (8.0D + U[j] * 8.0D)), texture[i[0]].getInterpolatedV(16.0D - (8.0D + V[j] * 8.0D))));
            }

            return;
        }

        for(int j = 0; j < 4; j++){
            double cx = posR.xCoord + ox + posA.xCoord * U[j] / 4D + posB.xCoord * V[j] / 4D;
            double cy = posR.yCoord + oy + posA.yCoord * U[j] / 4D + posB.yCoord * V[j] / 4D;
            double cz = posR.zCoord + oz + posA.zCoord * U[j] / 4D + posB.zCoord * V[j] / 4D;
            double offsetX = 0;
            double offsetY = 0;
            double offsetZ = 0;

            if(this.changeBounds) {
                switch (side.getIndex()) {
                    case 0:
                        offsetY -= 0.0004D;
                        break;
                    case 1:
                        offsetY += 0.0004D;
                        break;
                    case 2:
                        offsetZ -= 0.0004D;
                        break;
                    case 3:
                        offsetZ += 0.0004D;
                        break;
                    case 4:
                        offsetX -= 0.0004D;
                        break;
                    case 5:
                        offsetX += 0.0004D;
                        break;
                }
            }

            for(int k = 0; k < 4; k++){
                this.vertices.add(new Vertex5(
                        cx + U[k] * posA.xCoord * 0.25D + V[k] * posB.xCoord * 0.25D + offsetX,
                        cy + U[k] * posA.yCoord * 0.25D + V[k] * posB.yCoord * 0.25D + offsetY,
                        cz + U[k] * posA.zCoord * 0.25D + V[k] * posB.zCoord * 0.25D + offsetZ,
                        texture[i[j]].getInterpolatedU(16.0D - (8.0D + U[j] * 4.0D + U[k] * 4.0D)),
                        texture[i[j]].getInterpolatedV(16.0D - (8.0D + V[j] * 4.0D + V[k] * 4.0D))
                ));
            }
        }
    }

    public void renderStandardBlock(BlockPos pos, TextureAtlasSprite[] texture){
        this.renderFaceXNeg(pos, texture);
        this.renderFaceXPos(pos, texture);
        this.renderFaceYNeg(pos, texture);
        this.renderFaceYPos(pos, texture);
        this.renderFaceZNeg(pos, texture);
        this.renderFaceZPos(pos, texture);
    }

    public void renderFace(BlockPos pos, TextureAtlasSprite[] texture, EnumFacing side){
        switch(side){
            case DOWN:
                this.renderFaceYNeg(pos, texture);
                break;
            case UP:
                this.renderFaceYPos(pos, texture);
                break;
            case NORTH:
                this.renderFaceZNeg(pos, texture);
                break;
            case EAST:
                this.renderFaceZPos(pos, texture);
                break;
            case SOUTH:
                this.renderFaceXNeg(pos, texture);
                break;
            case WEST:
                this.renderFaceXPos(pos, texture);
                break;
        }
    }

    public void renderFaceYNeg(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(0))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(0.5D, 0D, 0.5D),
                    new Vec3d(-1D, 0D, 0D),
                    new Vec3d(0D, 0D, 1D),
                    texture,
                    EnumFacing.getFront(0),
                    new Vec3d(0.5D, 1D, 0.5D)
            );
        }
    }

    public void renderFaceYPos(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(1))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(0.5D, 1D, 0.5D),
                    new Vec3d(-1D, 0D, 0D),
                    new Vec3d(0D, 0D, -1D),
                    texture,
                    EnumFacing.getFront(1),
                    new Vec3d(0.5D, 0D, 0.5D)
            );
        }
    }

    public void renderFaceZNeg(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(2))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(0.5D, 0.5D, 0D),
                    new Vec3d(1D, 0D, 0D),
                    new Vec3d(0D, 1D, 0D),
                    texture,
                    EnumFacing.getFront(2),
                    new Vec3d(0.5D, 0.5D, 1D)
            );
        }
    }

    public void renderFaceZPos(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(3))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(0.5D, 0.5D, 1D),
                    new Vec3d(-1D, 0D, 0D),
                    new Vec3d(0D, 1D, 0D),
                    texture,
                    EnumFacing.getFront(3),
                    new Vec3d(0.5D, 0.5D, 0D)
            );
        }
    }

    public void renderFaceXNeg(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(4))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(0D, 0.5D, 0.5D),
                    new Vec3d(0D, 0D, -1D),
                    new Vec3d(0D, 1D, 0D),
                    texture,
                    EnumFacing.getFront(4),
                    new Vec3d(1D, 0.5D, 0.5D)
            );
        }
    }

    public void renderFaceXPos(BlockPos pos, TextureAtlasSprite[] texture){
        IBlockState state = this.world.getBlockState(pos);

        if(state.shouldSideBeRendered(this.world, pos, EnumFacing.getFront(5))){
            this.renderSide(
                    new Vec3d(pos.getX(), pos.getY(), pos.getZ()),
                    new Vec3d(1D, 0.5D, 0.5D),
                    new Vec3d(0D, 0D, 1D),
                    new Vec3d(0D, 1D, 0D),
                    texture,
                    EnumFacing.getFront(5),
                    new Vec3d(0D, 0.5D, 0.5D)
            );
        }
    }

    private byte getType(EnumFacing side, Vec3d pos, Vec3d posA, Vec3d posB, Vec3d posC){
        boolean a = (this.matchBlock(pos.add(posA))) && (!this.matchBlock(pos.add(posC))) && (!this.matchBlock(pos.add(posA).add(posC)));
        boolean b = (this.matchBlock(pos.add(posB))) && (!this.matchBlock(pos.add(posC))) && (!this.matchBlock(pos.add(posB).add(posC)));

        if(a){
            if(b){
                if(this.matchBlock(pos.add(posA).add(posB))){
                    if((this.matchBlock(pos.add(posA).add(posB).add(posC))) || (this.matchBlock(pos.add(posA).add(posB).add(posC))) || (this.matchBlock(pos.add(posB).add(posC))) || (this.matchBlock(pos.add(posA).add(posC)))) {
                        return 4;
                    }

                    return 3;
                }

                return 4;
            }

            return 2;
        }

        if(b){
            return 1;
        }

        return 0;
    }

    private boolean matchBlock(Vec3d pos){
        IBlockState state = this.world.getBlockState(new BlockPos(pos));
        return state == this.currentBlockState;
    }

}
