package keri.ninetaillib.multiblock;

import com.google.common.collect.Lists;
import keri.ninetaillib.util.CommonUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class MultiblockPattern {

    private List<List<EnumFacing>> offsets;
    private List<IBlockState> components;

    public MultiblockPattern(List<List<EnumFacing>> offsets, List<IBlockState> components){
        if(offsets.size() != components.size()){
            throw new MultiblockPatternException("Multiblock pattern is corrupted!");
        }

        this.offsets = offsets;
        this.components = components;
    }

    public boolean isValid(World world, BlockPos pos, EnumFacing side){
        int validBlocks = 0;
        int rotation = 0;

        switch(side){
            case NORTH:
                rotation = 180;
                break;
            case EAST:
                rotation = 90;
                break;
            case SOUTH:
                rotation = 0;
                break;
            case WEST:
                rotation = 270;
                break;
        }

        List<List<EnumFacing>> orientedOffsets = this.rotateAroundY(this.offsets, rotation);

        for(int i = 0; i < orientedOffsets.size(); i++){
            List<EnumFacing> offsetList = orientedOffsets.get(i);
            IBlockState componentBlockState = this.components.get(i);
            BlockPos currentComponentPosition = pos;

            if(offsetList != null){
                for(int j = 0; j < offsetList.size(); j++){
                    EnumFacing currentOffset = offsetList.get(j);

                    if(currentOffset != null){
                        currentComponentPosition = currentComponentPosition.offset(currentOffset);
                    }
                }
            }

            if(world.getBlockState(currentComponentPosition) == componentBlockState){
                validBlocks++;
            }
        }

        return validBlocks == this.components.size();
    }

    private List<List<EnumFacing>> rotateAroundY(List<List<EnumFacing>> offsets, int amount){
        List<List<EnumFacing>> output = Lists.newArrayList();

        for(int i = 0; i < offsets.size(); i++){
            if(offsets.get(i) != null){
                List<EnumFacing> currentList = offsets.get(i);
                List<EnumFacing> newList = Lists.newArrayList();

                for(int j = 0; j < currentList.size(); j++){
                    EnumFacing currentOffset = currentList.get(j);
                    newList.add(CommonUtils.rotateAroundY(currentOffset, amount));
                }

                output.add(newList);
            }
            else{
                output.add(null);
            }
        }

        return output;
    }

    private class MultiblockPatternException extends IllegalArgumentException {

        public MultiblockPatternException(String message) {
            super(message);
        }

    }

}
