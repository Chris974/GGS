/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.API.server;

import net.mcforge.API.EventList;
import net.mcforge.server.Server;

public class ServerStartedEvent extends ServerEvent {

    private static EventList events = new EventList();
    public ServerStartedEvent(Server server) {
        super(server);
    }

    @Override
    public EventList getEvents() {
        return events;
    }
    
    /**
     * Get a list of registered listeners
     * @return The list of listeners
     */
    public static EventList getEventList() {
        return events;
    }

}

