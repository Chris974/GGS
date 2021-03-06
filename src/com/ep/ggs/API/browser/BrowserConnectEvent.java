/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.ep.ggs.API.browser;

import com.ep.ggs.API.EventList;
import com.ep.ggs.iomodel.Browser;

public class BrowserConnectEvent extends BrowserEvent {

    private static EventList events = new EventList();
    
    public BrowserConnectEvent(Browser b) {
        super(b);
    }

    @Override
    public EventList getEvents() {
        return events;
    }
    public static EventList getEventList() {
        return events;
    }

}

