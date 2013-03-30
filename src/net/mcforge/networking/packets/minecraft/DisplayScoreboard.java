package net.mcforge.networking.packets.minecraft;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import net.mcforge.iomodel.SMPPlayer;
import net.mcforge.networking.packets.PacketManager;
import net.mcforge.server.Server;

public class DisplayScoreboard extends SMPPacket {

    public DisplayScoreboard(String name, byte ID, PacketManager parent) {
        super(name, ID, parent);
    }
    
    public DisplayScoreboard(PacketManager pm) {
        this("DisplayScoreboard", (byte)0x0D, pm);
    }

    @Override
    public void handle(SMPPlayer p, Server server, DataInputStream reader) {
    }

    @Override
    public void write(SMPPlayer player, Server server, Object... obj) {
        if (obj.length >= 2) {
            ByteBuffer bb;
            
            if (obj[0] instanceof Byte && obj[1] instanceof String) {
                bb = ByteBuffer.allocate(4 + stringLength(obj[1]));
                
                bb.put(ID);
                bb.put((Byte)obj[0]);
                putMinecraftString((String)obj[1], bb);
                
				try {
					player.writeData(bb.array());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
            }
        }
    }
}