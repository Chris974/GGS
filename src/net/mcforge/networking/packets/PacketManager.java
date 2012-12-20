/*******************************************************************************
 * Copyright (c) 2012 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.networking.packets;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import net.mcforge.networking.IOClient;
import net.mcforge.networking.packets.browser.GET;
import net.mcforge.networking.packets.clients.BrowserClient;
import net.mcforge.networking.packets.clients.ClassicClient;
import net.mcforge.networking.packets.minecraft.Connect;
import net.mcforge.networking.packets.minecraft.DespawnPlayer;
import net.mcforge.networking.packets.minecraft.FinishLevelSend;
import net.mcforge.networking.packets.minecraft.GlobalPosUpdate;
import net.mcforge.networking.packets.minecraft.Kick;
import net.mcforge.networking.packets.minecraft.LevelSend;
import net.mcforge.networking.packets.minecraft.LevelStartSend;
import net.mcforge.networking.packets.minecraft.MOTD;
import net.mcforge.networking.packets.minecraft.Message;
import net.mcforge.networking.packets.minecraft.Ping;
import net.mcforge.networking.packets.minecraft.PosUpdate;
import net.mcforge.networking.packets.minecraft.SetBlock;
import net.mcforge.networking.packets.minecraft.SpawnPlayer;
import net.mcforge.networking.packets.minecraft.TP;
import net.mcforge.networking.packets.minecraft.UpdateUser;
import net.mcforge.networking.packets.minecraft.Welcome;
import net.mcforge.networking.packets.minecraft.extend.ClickDistancePacket;
import net.mcforge.networking.packets.minecraft.extend.ExtAddPlayerNamePacket;
import net.mcforge.networking.packets.minecraft.extend.ExtEntryPacket;
import net.mcforge.networking.packets.minecraft.extend.ExtInfoPacket;
import net.mcforge.networking.packets.minecraft.extend.ExtPlayerPacket;
import net.mcforge.networking.packets.minecraft.extend.ExtRemovePlayerNamePacket;
import net.mcforge.networking.packets.minecraft.extend.HoldThisPacket;
import net.mcforge.server.Server;

public class PacketManager {

    protected Packet[] packets;
    
    protected IClient[] clients = new IClient[] {
            new BrowserClient(),
            new ClassicClient()
    };

    protected ServerSocket serverSocket;

    protected Thread reader;
    
    protected ArrayList<IOClient> connectedclients = new ArrayList<IOClient>();

    /**
     * The server this PacketManager belongs to
     */
    public Server server;

    /**
     * The constructor for the PacketManager
     * @param instance
     *                The server that this PacketManager will belong to
     */
    public PacketManager(Server instance) {
        this.server = instance;
        initPackets();
        try {
            serverSocket = new ServerSocket(this.server.Port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void initPackets() {
        packets = new Packet[] {
                new Connect(this),
                new DespawnPlayer(this),
                new FinishLevelSend(this),
                new GlobalPosUpdate(this),
                new Kick(this),
                new LevelSend(this),
                new LevelStartSend(this),
                new Message(this),
                new MOTD(this),
                new Ping(this),
                new PosUpdate(this),
                new SetBlock(this),
                new SpawnPlayer(this),
                new TP(this),
                new UpdateUser(this),
                new Welcome(this),
                new GET(this),
                new ClickDistancePacket(this),
                new ExtEntryPacket(this),
                new ExtInfoPacket(this),
                new HoldThisPacket(this),
                new ExtPlayerPacket(this),
                new ExtAddPlayerNamePacket(this),
                new ExtRemovePlayerNamePacket(this)
        };
    }

    /**
     * Get a packet this PacketManager handles.
     * @param opCode
     *              The OpCode for the packet
     * @return
     *        The packet found, if no packet is found, then it will
     *        return null.
     */
    public Packet getPacket(byte opCode) {
        for (Packet p : packets) {
            if (p.ID == opCode)
                return p;
        }
        return null;
    }

    /**
     * Get a packet this PacketManager handles.
     * @param name
     *            The name of the packet
     * @return
     *        The packet found, if no packet is found, then it will
     *        return null.
     */
    public Packet getPacket(String name) {
        for (Packet p : packets) {
            if (p.name.equalsIgnoreCase(name))
                return p;
        }
        return null;
    }

    /**
     * Have the PacketManager start listening for clients
     * on the port provided by the {@link PacketManager#server}
     */
    public void StartReading() {
        reader = new Read();
        reader.start();
        server.Log("Listening on port " + server.Port);
    }

    /**
     * Stop listening for clients.
     */
    public void StopReading() {
        reader.interrupt();
        try {
            serverSocket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Check if an IP is within the localhost range
     * @param addr
     *            The IP
     * @return
     *        True if the connection is local.
     *        False if its not.
     */
    public static boolean isLocalConnection(InetAddress addr) {
        if (addr.isAnyLocalAddress() || addr.isLoopbackAddress())
            return true;
        try {
            return NetworkInterface.getByInetAddress(addr) != null;
        } catch (SocketException e) {
            return false;
        }
    }
    
    /**
     * Remove an {@link IOClient} from the {@link PacketManager#getConnectedClients()} list.
     * @param client
     *              The client to remove
     * @return
     *        Weather the client was removed or not.
     */
    public boolean disconnect(IOClient client) {
       if (connectedclients.contains(client)) {
           connectedclients.remove(client);
           return true;
       }
       return false;
    }
    
    /**
     * Get all the connected clients
     * @return
     *        An {@link ArrayList} of IOClients
     */
    public ArrayList<IOClient> getConnectedClients() {
        return connectedclients;
    }
    
    private IClient findClient(byte OPCode) {
        for (IClient c : clients) {
            if (c.getOPCode() == OPCode)
                return c;
        }
        return null;
    }

    private void Accept(Socket connection) throws IOException {
        DataInputStream reader = new DataInputStream(connection.getInputStream());
        byte firstsend = (byte)reader.read();
        IClient client = findClient(firstsend);
        if (client == null)
            return;
        connectedclients.add(client.create(connection, this));
    }

    private class Read extends Thread {

        @Override
        public void run() {
            Socket connection = null;
            while (server.Running) {
                if (serverSocket.isClosed())
                    break;
                try {
                    connection = serverSocket.accept();
                    connection.setSoTimeout(300000);
                    server.Log("Connection made from " + connection.getInetAddress().toString());
                    new AcceptThread(connection).start();
                } catch (IOException e) {
                    if (e.getMessage().indexOf("socket closed") == -1) //Happens when the socket is shutdown
                        e.printStackTrace();
                }
            }
        }
    }

    private class AcceptThread extends Thread {
        private Socket connection;
        public AcceptThread(Socket connection) { this.connection = connection; }

        @Override
        public void run() {
            try {
                Accept(connection);
            } catch (IOException e) {
                if (!e.getMessage().contains("Connection reset")) //Mostly happens when xwom connects
                    e.printStackTrace();
            }
        }
    }

}

