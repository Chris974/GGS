/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.ep.ggs.networking.packets.classicminecraft;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.ep.ggs.API.io.PacketPrepareEvent;
import com.ep.ggs.iomodel.Player;
import com.ep.ggs.networking.IOClient;
import com.ep.ggs.networking.packets.PacketManager;
import com.ep.ggs.server.Server;


public class Kick extends ClassicPacket {

    public Kick(String name, byte ID, PacketManager parent) {
        super(name, ID, parent);
    }
    
    public Kick(PacketManager pm) {
        super("Kick", (byte)0x0e, pm);
    }

    @Override
    public void Handle(byte[] message, Server server, IOClient player) {
    }

    @Override
    public void Write(IOClient p, Server server) {
        PacketPrepareEvent event = new PacketPrepareEvent(p, this, server);
        server.getEventSystem().callEvent(event);
        if (event.isCancelled())
            return;
        Player player;
        if (p instanceof Player) {
            player = (Player)p;
        }
        else
            return;
        try {
            while (player.kickreason.length() < 64) {
                player.kickreason += " ";
            }
            byte[] temp = player.kickreason.getBytes("US-ASCII");
            byte[] finals = new byte[1 + temp.length];
            finals[0] = ID;
            System.arraycopy(temp, 0, finals, 1, temp.length);
            player.writeData(finals);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

