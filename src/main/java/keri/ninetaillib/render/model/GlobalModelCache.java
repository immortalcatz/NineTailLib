package keri.ninetaillib.render.model;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GlobalModelCache {

    private static Map<String, List<BakedQuad>> blockModelCache = Maps.newHashMap();
    private static Map<String, List<BakedQuad>> itemModelCache = Maps.newHashMap();
    private static LoadingCache<String, List<BakedQuad>> blockModelLoadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<BakedQuad>>() {
        @Override
        public List<BakedQuad> load(String key) throws Exception {
            return blockModelCache.get(key);
        }
    });
    private static LoadingCache<String, List<BakedQuad>> itemModelLoadingCache = CacheBuilder.newBuilder().build(new CacheLoader<String, List<BakedQuad>>() {
        @Override
        public List<BakedQuad> load(String key) throws Exception {
            return itemModelCache.get(key);
        }
    });

    public static void putBlockModel(String key, List<BakedQuad> quads){
        blockModelLoadingCache.put(key, quads);
    }

    public static void putItemModel(String key, List<BakedQuad> quads){
        itemModelLoadingCache.put(key, quads);
    }

    public static ImmutableList<BakedQuad> getBlockModel(String key){
        return ImmutableList.copyOf(blockModelLoadingCache.getUnchecked(key));
    }

    public static ImmutableList<BakedQuad> getItemModel(String key){
        return ImmutableList.copyOf(itemModelLoadingCache.getUnchecked(key));
    }

    public static boolean isBlockModelPresent(String key){
        return blockModelLoadingCache.getIfPresent(key) != null;
    }

    public static boolean isItemModelPresent(String key){
        return itemModelLoadingCache.getIfPresent(key) != null;
    }

}
