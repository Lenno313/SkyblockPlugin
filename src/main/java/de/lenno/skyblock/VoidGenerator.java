package de.lenno.skyblock;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    // Diese Methode lässt die Oberfläche leer
    @Override
    public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        // Hier passiert absichtlich NICHTS.
    }

    // Verhindert das Generieren von Bedrock ganz unten
    @Override
    public boolean shouldGenerateBedrock() {
        return false;
    }

    // Verhindert Höhlen, Strukturen (Dörfer etc.) und Deko (Bäume/Gras)
    @Override
    public boolean shouldGenerateCaves() { return false; }

    @Override
    public boolean shouldGenerateDecorations() { return false; }

    @Override
    public boolean shouldGenerateStructures() { return false; }
}