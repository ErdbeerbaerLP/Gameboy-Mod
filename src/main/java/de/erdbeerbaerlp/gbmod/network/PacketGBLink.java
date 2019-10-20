package de.erdbeerbaerlp.gbmod.network;

import de.erdbeerbaerlp.gbmod.Gbmod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class PacketGBLink implements IMessage {
    private UUID sender;
    private UUID toSend;

    public PacketGBLink() {
    }

    public PacketGBLink(UUID toSend, UUID sender) {
        this.toSend = toSend;
        this.sender = sender;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Writes the int into the buf

        buf.writeLong(sender.getMostSignificantBits());
        buf.writeLong(sender.getLeastSignificantBits());
        buf.writeLong(toSend.getMostSignificantBits());
        buf.writeLong(toSend.getLeastSignificantBits());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        if (buf.readableBytes() < 32) System.err.println("Not enough bytes!!!");
        sender = new UUID(buf.readLong(), buf.readLong());
        toSend = new UUID(buf.readLong(), buf.readLong());
    }

    public static class PacketGBLinkHandler implements IMessageHandler<PacketGBLink, IMessage> {
        // Do note that the default constructor is required, but implicitly defined in this case

        @Override
        public IMessage onMessage(PacketGBLink message, MessageContext ctx) {
            if (ctx.side == Side.CLIENT) {
                if (Gbmod.INSTANCE.lastLinkRequest == null || !Gbmod.INSTANCE.lastLinkRequest.equals(message.sender)) {
                    String playername = "ERROR";
                    for (EntityPlayer p : Minecraft.getMinecraft().world.playerEntities) {
                        if (p.getUniqueID().equals(message.sender)) playername = p.getName();
                    }
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("command.link.request", playername)));
                } else if (Gbmod.INSTANCE.lastLinkRequest.equals(message.sender)) {
                    Minecraft.getMinecraft().player.sendMessage(new TextComponentString(I18n.format("command.link.success")));
                }
            }
            return null;
        }
    }
}
