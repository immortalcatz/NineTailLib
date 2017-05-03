package keri.ninetaillib.internal.client.command;

import com.google.common.collect.Lists;
import keri.ninetaillib.render.model.GlobalModelCache;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.List;


public class CommandClearModelCache implements ICommand {

    private static final List<String> aliases = Lists.newArrayList(
            "nukentlmodelcache",
            "nukentlmc",
            "nukentlmodels"
    );

    @Override
    public String getCommandName() {
        return "nukentlmodelcache";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "nukentlmodelcache";
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Side effectiveSide = FMLCommonHandler.instance().getEffectiveSide();

        if(effectiveSide == Side.CLIENT){
            if(args != null){
                if(args.length == 1){
                    if(args[0].equals("blocks")){
                        GlobalModelCache.nukeBlockModels();
                    }
                    else if(args[0].equals("items")){
                        GlobalModelCache.nukeItemModel();
                    }
                }
            }
            else{
                GlobalModelCache.nukeBlockModels();
                GlobalModelCache.nukeItemModel();
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return aliases;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand command) {
        return 0;
    }

}
