package de.erdbeerbaerlp.gbmod;

import eu.rekawek.coffeegb.gui.Emulator;

public interface IGameboy {
    Emulator getEmulator();

    void initEmulator();
    int getRomIndex();

    ROM getRom();
    void setRom(int rom);

    void nextRom();
}
