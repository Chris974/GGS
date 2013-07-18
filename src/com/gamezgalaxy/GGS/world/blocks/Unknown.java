/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.world.blocks;

import com.gamezgalaxy.GGS.world.Block;

public class Unknown extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Unknown(byte ID, String name) {
		super(ID, name);
	}
	
	public Unknown() {
		super((byte)50, "NULL");
	}

}
