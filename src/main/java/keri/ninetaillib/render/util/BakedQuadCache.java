package keri.ninetaillib.render.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class BakedQuadCache {

    private Map<String, List<BakedQuad>> quadMap;
    private LoadingCache<String, List<BakedQuad>> quadCache;

    private BakedQuadCache(){
        this.quadMap = Maps.newHashMap();
        this.quadCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<BakedQuad>>() {
            @Override
            public List<BakedQuad> load(String key) throws Exception {
                return quadMap.get(key);
            }
        });
    }

    public void put(String key, List<BakedQuad> quads){
        if(key != null){
            this.quadCache.put(key, quads);
        }
        else{
            throw new IllegalArgumentException("Cache key cannot be null!");
        }
    }

    public List<BakedQuad> get(String key){
        if(key != null){
            return this.quadCache.getUnchecked(key);
        }
        else{
            throw new IllegalArgumentException("Cache key cannot be null!");
        }
    }

    public boolean isPresent(String key){
        if(key != null){
            return this.quadCache.getIfPresent(key) != null;
        }
        else{
            throw new IllegalArgumentException("Cache key cannot be null!");
        }
    }

    public void clear(){
        this.quadMap.clear();
        this.quadCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<BakedQuad>>() {
            @Override
            public List<BakedQuad> load(String key) throws Exception {
                return quadMap.get(key);
            }
        });
    }

    public static BakedQuadCache create(){
        return new BakedQuadCache();
    }

}
