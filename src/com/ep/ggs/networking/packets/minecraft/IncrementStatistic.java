/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.ep.ggs.networking.packets.minecraft;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.ep.ggs.iomodel.SMPPlayer;
import com.ep.ggs.networking.packets.PacketManager;
import com.ep.ggs.server.Server;

public class IncrementStatistic extends SMPPacket {

    public IncrementStatistic(String name, byte ID, PacketManager parent) {
        super(name, ID, parent);
    }
    
    public IncrementStatistic(PacketManager pm) {
        this("IncrementStatistic", (byte)0xC8, pm);
    }

	@Override
    public void handle(SMPPlayer player, Server server, DataInputStream reader) {
	}

    @Override
    public void write(SMPPlayer p, Server server, Object... obj) {
        if (obj.length >=  2) {
        	if (obj[0] instanceof Integer && obj[1] instanceof Byte) {
                ByteBuffer bb = ByteBuffer.allocate(6);
                
                bb.putInt((Integer)obj[0]);
                bb.put((Byte)obj[1]);
                
				try {
					p.writeData(bb.array());
				}
				catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
    }
}
