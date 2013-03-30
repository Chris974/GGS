/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.chat;

public class ColorFormatException extends IllegalArgumentException {
    /**
     * 
     */
    private static final long serialVersionUID = -1268801460803261859L;
    public ColorFormatException(String paramString)
    {
        super(paramString);
    }
    public static ColorFormatException forInputString(String paramString)
    {
        return new ColorFormatException("For input string: \"" + paramString + "\"");
    }
}

