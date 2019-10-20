package de.erdbeerbaerlp.gbmod.util;

import eu.rekawek.coffeegb.memory.cart.Cartridge;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public final class ROM {
    private final File romFile;
    private final Cartridge.GameboyTypeFlag type;
    private final String romName;
    public ROM(File romFile) throws IOException {
        this.romFile = romFile;
        this.type = Cartridge.getRomType(romFile);
        this.romName = FilenameUtils.getBaseName(romFile.getName());
    }

    public File getRomFile() {
        return romFile;
    }

    public Cartridge.GameboyTypeFlag getType() {
        return type;
    }

    public String getRomName() {
        return romName;
    }
}
