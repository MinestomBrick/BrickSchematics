package org.minestombrick.schematics.api;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.block.Block;

public record RegionBlock(Point relativePosition, Block blockState) {}