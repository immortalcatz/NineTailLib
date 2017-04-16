package keri.ninetaillib.util;

/**
 * This is a ported class from vanilla 1.7.10!!!
 * But, it does what it needs to do =)
 */
public class ChunkCoordIntPair {

    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkCoordIntPair(int chunkX, int chunkZ){
        this.chunkXPos = chunkX;
        this.chunkZPos = chunkZ;
    }

    public static long chunkXZ2Int(int chunkX, int chunkZ){
        return (long)chunkX & 4294967295L | ((long)chunkZ & 4294967295L) << 32;
    }

    public int hashCode(){
        int i = 1664525 * this.chunkXPos + 1013904223;
        int j = 1664525 * (this.chunkZPos ^ -559038737) + 1013904223;
        return i ^ j;
    }

    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        else if (!(object instanceof ChunkCoordIntPair)){
            return false;
        }
        else{
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)object;
            return this.chunkXPos == chunkcoordintpair.chunkXPos && this.chunkZPos == chunkcoordintpair.chunkZPos;
        }
    }

    public int getCenterXPos(){
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZPosition(){
        return (this.chunkZPos << 4) + 8;
    }

    public ChunkPosition func_151349_a(int p_151349_1_){
        return new ChunkPosition(this.getCenterXPos(), p_151349_1_, this.getCenterZPosition());
    }

    public String toString()
    {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }

}
