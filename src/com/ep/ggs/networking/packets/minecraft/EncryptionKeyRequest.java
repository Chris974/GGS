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
import java.security.PublicKey;

import com.ep.ggs.iomodel.SMPPlayer;
import com.ep.ggs.networking.packets.PacketManager;
import com.ep.ggs.server.Server;


public class EncryptionKeyRequest extends SMPPacket {

    public EncryptionKeyRequest(String name, byte ID, PacketManager parent) {
        super(name, ID, parent);
    }
    
    public EncryptionKeyRequest(PacketManager parent) {
        this("EKR", (byte)0xFD, parent);
    }

    @Override
    public void write(SMPPlayer client, Server server, Object... obj) {
        if (obj.length == 2 && obj[0] instanceof PublicKey && obj[1] instanceof byte[]) {
            String sid = "IRAIDYUS";
            PublicKey pk = (PublicKey)obj[0];
            byte[] verify = (byte[])obj[1];
            ByteBuffer bb = ByteBuffer.allocate(7 + pk.getEncoded().length + (sid.length() * 2) + verify.length);
            bb.put(ID);
            putMinecraftString(sid, bb);
            bb.putShort((short)pk.getEncoded().length);
            bb.put(pk.getEncoded());
            bb.putShort((short)verify.length);
            bb.put(verify);
            try {
                client.writeData(bb.array());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle(SMPPlayer client, Server server, DataInputStream reader) { }

}
