/*
 * Copyright (c) 2017 KitsuneAlex. All rights reserved!
 * Do not distribute or redistribute in any way except you got
 * the explicit permission from the developer of this software!
 */

package keri.ninetaillib.lib.render.ctm;

import keri.ninetaillib.lib.util.BlockAccessUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class RenderCTM {

    public static boolean renderBlock(VertexBuffer buffer, IBlockAccess world, BlockPos pos){
        Block block = world.getBlockState(pos).getBlock();
        int meta = BlockAccessUtils.getBlockMetadata(world, pos);

        try {
            BlockRenderer rendererOld = new BlockRenderer(buffer);
            rendererOld.setBlockAccess(world);
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            IBlockRenderer rb = getContext(buffer, rendererOld, block, world, ((ICTMBlock<?>)block).getManager(world, x, y, z, meta), meta);
            boolean ret = rb.renderStandardBlock(block, x, y, z);
            //rb.unlockBlockBounds();
            return ret;
        }
        catch (Throwable t) {
            CrashReport crashreport = CrashReport.makeCrashReport(t, "Rendering CTM Block");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being rendered");
            crashreportcategory.addCrashSection("Block name", block.getLocalizedName());
            crashreportcategory.addCrashSection("Block metadata", meta);
            throw new ReportedException(crashreport);
        }
    }

    private static IBlockRenderer getContext(VertexBuffer buffer, IBlockRenderer rendererOld, Block block, IBlockAccess world, ISubmapManager manager, int meta) {
        if(manager != null) {
            IBlockRenderer rb = manager.createRenderContext(buffer, rendererOld, block, world);

            if (rb != null && rb != rendererOld) {
                rb.setBlockAccess(world);

                if (rb instanceof BlockRendererCTM) {
                    BlockRendererCTM rbctm = (BlockRendererCTM)rb;
                    rbctm.manager = rbctm.manager == null ? manager : rbctm.manager;
                    rbctm.rendererOld = rbctm.rendererOld == null ? rendererOld : rbctm.rendererOld;
                }

                return rb;
            }
        }

        return rendererOld;
    }

}
