package de.erdbeerbaerlp.gbmod.items;

import de.erdbeerbaerlp.gbmod.util.ROM;
import eu.rekawek.coffeegb.gui.Emulator;

public interface IGameBoy {
    Emulator getEmulator();

    boolean hasEmulator();
    void initEmulator();
    int getRomIndex();

    ROM getRom();
    void setRom(int rom);

    void nextRom();
}
