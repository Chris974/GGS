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

import com.ep.ggs.iomodel.SMPPlayer;
import com.ep.ggs.networking.packets.PacketManager;
import com.ep.ggs.server.Server;

public class UseEntity extends SMPPacket {

    public UseEntity(String name, byte ID, PacketManager parent) {
        super(name, ID, parent);
    }

    public UseEntity(PacketManager pm) {
        super("UseEntity", (byte)0x07, pm);
    }

    @SuppressWarnings("unused") //Temp
	@Override
    public void handle(SMPPlayer p, Server server, DataInputStream dis) {
    	try {
			int user = dis.readInt();
			int target = dis.readInt();
			boolean mouse = dis.readBoolean();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

    }

    @Override
    public void write(SMPPlayer player, Server server, Object... obj) { }

}
