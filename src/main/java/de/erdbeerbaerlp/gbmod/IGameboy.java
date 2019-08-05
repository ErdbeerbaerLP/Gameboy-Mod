package de.erdbeerbaerlp.gbmod;

import eu.rekawek.coffeegb.gui.Emulator;

public interface IGameboy {
    Emulator getEmulator();
    int getRom();
    void setRom(int rom);
}
