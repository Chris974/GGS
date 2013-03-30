/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.world.blocks.classicmodel;

import net.mcforge.iomodel.Player;
import net.mcforge.server.Server;

public class Grass extends PhysicsBlock {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public Grass(byte ID, String name) {
        this(ID, name, null);
    }
    
    public Grass() {
        this((byte)2, "Grass");
    }
    
    public Grass(Server s) {
        this((byte)2, "Grass", s);
    }
    
    public Grass(byte ID, String name, Server s) {
        super((byte)2, "Grass", s);
    }

    @Override
    public boolean initAtStart() {
        return true;
    }

    @Override
    public PhysicsBlock clone(Server s) {
        return new Grass(s);
    }

    @Override
    public void tick() {
        if (getLevel().getTile(getX(), getY() - 1, getZ()).getVisibleBlock() == 2)
            Player.GlobalBlockChange((short)getX(), (short)(getY() - 1), (short)getZ(), ClassicBlock.getBlock("Dirt"), getLevel(), getServer());
        if (!getLevel().getTile(getX(), getY() + 1, getZ()).canWalkThrough())
            super.change(ClassicBlock.getBlock("Dirt"));
        else
            super.stopTick();
    }

    @Override
    public boolean inSeperateThread() {
        return false;
    }

    @Override
    public int getTimeout() {
        return 1;
    }
}

