package org.minestombrick.schematics.api;

import net.minestom.server.coordinate.Point;

import java.util.Collection;

public interface Schematic {

    /**
     * x-axis
     */
    int width();

    /**
     * y-axis
     */
    int height();

    /**
     * z-axis
     */
    int length();

    Point offset();

    /**
     * Returns the blockstates with their relative position.
     *
     * @return collection of blocks
     */
    Collection<RegionBlock> blocks();

}
