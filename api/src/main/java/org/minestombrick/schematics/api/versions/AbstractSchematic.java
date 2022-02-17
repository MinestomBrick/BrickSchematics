package org.minestombrick.schematics.api.versions;

import org.minestombrick.schematics.api.RegionBlock;
import org.minestombrick.schematics.api.Schematic;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.block.Block;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.*;
import java.util.regex.Pattern;

public class AbstractSchematic implements Schematic {

    protected final Set<RegionBlock> blocks = new HashSet<>();

    protected int width;
    protected int height;
    protected int length;

    protected Point offset = new Vec(0, 0, 0);

    //

    public Collection<RegionBlock> blocks() {
        return Collections.unmodifiableSet(blocks);
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public int length() {
        return length;
    }

    public Point offset() {
        return offset;
    }

    //

    protected List<Block> parsePalette(NBTCompound nbt) {
        return nbt.getKeys().stream()
                .sorted(Comparator.comparingInt(nbt::getInt))
                .map(this::parseState)
                .toList();
    }

    protected Block parseState(String state) {
        int bracket = state.indexOf("[");

        if (bracket > 0) {
            Block block = Block.fromNamespaceId(state.substring(0, bracket));

            String data = state.substring(bracket + 1, state.length() - 1);
            String[] properties = data.split(Pattern.quote(","));
            for (String property : properties) {
                String[] pair = property.split(Pattern.quote("="));
                block = block.withProperty(pair[0], pair[1]);
            }

            return block;
        }

        return Block.fromNamespaceId(state);
    }

}
