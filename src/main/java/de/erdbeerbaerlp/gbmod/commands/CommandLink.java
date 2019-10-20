package de.erdbeerbaerlp.gbmod.commands;

import de.erdbeerbaerlp.gbmod.Gbmod;
import de.erdbeerbaerlp.gbmod.network.PacketGBLink;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandLink extends CommandBase {
    /**
     * Gets the name of the command
     */
    @Override
    public String getName() {
        return "link";
    }

    /**
     * Gets the usage string for the command.
     *
     * @param sender
     */
    @Override
    public String getUsage(ICommandSender sender) {
        return "command.link";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        final ArrayList<String> tabComps = new ArrayList<>();
        if (args.length == 1)
            for (final EntityPlayerMP p : server.getPlayerList().getPlayers())
                if (p.getName().startsWith(args[0])) tabComps.add(p.getName());
        return tabComps;
    }

    /**
     * Callback for when the command is executed
     *
     * @param server
     * @param sender
     * @param args
     */
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayerMP)) {
            sender.sendMessage(new TextComponentString("You must be a player to do this!"));
            return;
        }
        System.out.println(FMLCommonHandler.instance().getSide().name());
        if (args.length == 1) {
            final EntityPlayerMP p = server.getPlayerList().getPlayerByUsername(args[0]);
            if (p == null) {
                sender.sendMessage(new TextComponentTranslation("command.link.noPlayer", args[0]));
                return;
            }
            Gbmod.Channel.sendTo(new PacketGBLink(p.getUniqueID(), ((EntityPlayerMP) sender).getUniqueID()), p);
        } else sender.sendMessage(new TextComponentTranslation("command.link.invalidArgs"));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
