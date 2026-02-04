package de.lenno.skyblock;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.List;

public class SkyblockBiomeProvider extends BiomeProvider {

    @Override
    public Biome getBiome(WorldInfo worldInfo, int x, int y, int z) {
        // Hier ist deine Logik: Alles ab 175 ist Frozen Peaks, darunter Plains

        if (worldInfo.getName().endsWith("_nether")) {
            if (y >= 175) {
                return Biome.SOUL_SAND_VALLEY;
            } else {
                return Biome.NETHER_WASTES;
            }
        }
        if (y >= 175) {
            return Biome.FROZEN_PEAKS;
        } else {
            return Biome.PLAINS;
        }
    }

    @Override
    public List<Biome> getBiomes(WorldInfo worldInfo) {
        // Du musst eine Liste aller Biome zurückgeben, die vorkommen können
        return Arrays.asList(Biome.PLAINS, Biome.FROZEN_PEAKS);
    }

}