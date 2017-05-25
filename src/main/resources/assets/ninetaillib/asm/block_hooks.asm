list blockFence_n_canConnectTo
IRETURN

list blockFence_r_canConnectTo
ALOAD 1
ALOAD 2
INVOKESTATIC keri/ninetaillib/lib/hooks/BlockHooks.canFenceConnectTo (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z
IRETURN

list blockWall_n_canConnectTo
IRETURN

list blockWall_r_canConnectTo
ALOAD 1
ALOAD 2
INVOKESTATIC keri/ninetaillib/lib/hooks/BlockHooks.canWallConnectTo (Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Z
IRETURN