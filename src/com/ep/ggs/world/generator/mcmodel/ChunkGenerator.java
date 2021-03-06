/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.ep.ggs.world.generator.mcmodel;

import com.ep.ggs.world.generator.Generator;
import com.ep.ggs.world.mcmodel.Chunk;

public interface ChunkGenerator extends Generator<Chunk> {
    
    public void generate(Chunk c);
}
