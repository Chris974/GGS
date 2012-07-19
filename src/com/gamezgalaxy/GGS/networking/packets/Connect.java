/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets;

import java.io.UnsupportedEncodingException;

import com.gamezgalaxy.GGS.networking.Packet;
import com.gamezgalaxy.GGS.networking.PacketManager;
import com.gamezgalaxy.GGS.networking.PacketType;
import com.gamezgalaxy.GGS.server.Player;
import com.gamezgalaxy.GGS.server.Server;

public class Connect extends Packet {

	public Connect(String name, byte ID, PacketManager parent, PacketType packetType) {
		super(name, ID, parent, packetType);
	}
	public Connect(PacketManager pm) {
		super("Player Connect", (byte)0x00, pm, PacketType.Client_to_Server);
		this.lenght = 130;
	}

	@Override
	public void Write(Player player, Server server) {
		// TODO Auto-generated method stub

	}
	@Override
	public void Handle(byte[] message, Server server, Player player) {
		try {
			byte version = message[0];
			byte[] name = new byte[64];
			for (int i = 1; i < 64; i++)
				name[i - 1] = message[i];
			player.username = new String(name, "US-ASCII");
			name = new byte[64];
			for (int i = 65; i < 65 + 32; i++)
				name[i - 65] = message[i];
			player.mppass = new String(name, "US-ASCII");
			name = null;
			player.ClientType = message[129];
			player.VerifyLogin();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
