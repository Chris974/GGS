/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.API.player;

import net.mcforge.API.Event;
import net.mcforge.API.EventList;
import net.mcforge.iomodel.Player;

public abstract class PlayerEvent extends Event {
    
    private Player who;
    
    public PlayerEvent(Player who) {
        this.who = who;
    }
    
    public Player getPlayer() {
        return who;
    }
    /**
     * Get a list of registered listeners
     * @return The list of listeners
     */
    public abstract EventList getEvents();

}

