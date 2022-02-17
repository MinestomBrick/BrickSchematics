package org.minestombrick.schematics.api.versions;

import org.minestombrick.schematics.api.RegionBlock;
import kotlin.Pair;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBT;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTReader;

import java.io.File;
import java.util.List;

/**
 * https://github.com/SpongePowered/Schematic-Specification/blob/master/versions/schematic-2.md
 */
public class SpongeV2Schematic extends AbstractSchematic {

    public SpongeV2Schematic(@NotNull File schematicFile) {
        try (NBTReader reader = new NBTReader(schematicFile)) {
            Pair<String, NBT> pair = reader.readNamed();
            NBTCompound nbt = (NBTCompound) pair.getSecond();

            if (!pair.getFirst().equals("Schematic")) {
                return;
            }

            if (!nbt.containsKey("BlockData")) {
                return;
            }

            width = nbt.getShort("Width");
            height = nbt.getShort("Height");
            length = nbt.getShort("Length");

            if (nbt.contains("Offset")) {
                int[] offset = nbt.getIntArray("Offset").copyArray();
                this.offset = new Vec(offset[0], offset[1], offset[2]);
            }

            byte[] blocks = nbt.getByteArray("BlockData").copyArray();
            List<Block> palette = parsePalette(nbt.getCompound("Palette"));

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    for (int z = 0; z < length; z++) {
                        int index = x + z * width + y * width * length;
                        int stateId = blocks[index];
                        if (stateId < 0 || stateId >= palette.size()) {
                            continue;
                        }

                        this.blocks.add(new RegionBlock(new Vec(x, y, z), palette.get(stateId)));
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}