package eu.rekawek.coffeegb.serial;

import eu.rekawek.coffeegb.AddressSpace;
import eu.rekawek.coffeegb.Gameboy;
import eu.rekawek.coffeegb.cpu.InterruptManager;
import eu.rekawek.coffeegb.cpu.SpeedMode;

public class SerialPort implements AddressSpace {

    private final SerialEndpoint serialEndpoint;

    private final InterruptManager interruptManager;

    private final SpeedMode speedMode;

    private int sb;

    private int sc;

    private boolean transferInProgress;

    private int divider;

    public SerialPort(InterruptManager interruptManager, SerialEndpoint serialEndpoint, SpeedMode speedMode) {
        this.interruptManager = interruptManager;
        this.serialEndpoint = serialEndpoint;
        this.speedMode = speedMode;
    }

    public void tick() {
        if (!transferInProgress) {
            return;
        }
        if (++divider >= Gameboy.TICKS_PER_SEC / 8192 / speedMode.getSpeedMode()) {
            transferInProgress = false;
            sb = serialEndpoint.transfer(sb);
            interruptManager.requestInterrupt(InterruptManager.InterruptType.Serial);
        }
    }

    @Override
    public boolean accepts(int address) {
        return address == 0xff01 || address == 0xff02;
    }

    @Override
    public void setByte(int address, int value) {
        if (address == 0xff01) {
            sb = value;
        } else if (address == 0xff02) {
            sc = value;
            if ((sc & (1 << 7)) != 0) {
                startTransfer();
            }
        }
    }

    @Override
    public int getByte(int address) {
        if (address == 0xff01) {
            return sb;
        } else if (address == 0xff02) {
            return sc | 0b01111110;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void startTransfer() {
        transferInProgress = true;
        divider = 0;
    }
}
