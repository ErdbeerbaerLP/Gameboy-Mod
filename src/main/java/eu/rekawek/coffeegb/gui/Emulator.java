package eu.rekawek.coffeegb.gui;

import de.erdbeerbaerlp.gbmod.items.CapabilityGameBoy;
import eu.rekawek.coffeegb.Gameboy;
import eu.rekawek.coffeegb.GameboyOptions;
import eu.rekawek.coffeegb.controller.Joypad;
import eu.rekawek.coffeegb.cpu.SpeedMode;
import eu.rekawek.coffeegb.debug.Console;
import eu.rekawek.coffeegb.memory.cart.Cartridge;
import eu.rekawek.coffeegb.serial.SerialEndpoint;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Emulator {

    private static final int SCALE = 2;
    private final SwingController controller;
    private final SerialEndpoint serialEndpoint;
    private final SpeedMode speedMode;
    private final Optional<Console> console;
    private final CapabilityGameBoy item;
    private GameboyOptions options;
    private Cartridge rom;
    private AudioSystemSoundOutput sound;
    private SwingDisplay display;
    private Gameboy gameboy;
    private JFrame mainWindow;


    public Emulator(CapabilityGameBoy is) {
        this.item = is;
        final Properties prop = Main.loadProperties();
        this.options = new GameboyOptions(null, item);
        speedMode = new SpeedMode();
        serialEndpoint = SerialEndpoint.NULL_ENDPOINT;
        console = options.isDebug() ? Optional.of(new Console()) : Optional.empty();
        console.map(Thread::new).ifPresent(Thread::start);
        sound = new AudioSystemSoundOutput();
        display = new SwingDisplay(SCALE);
        controller = new SwingController(prop);

        console.ifPresent(c -> c.init(gameboy));
    }

    public SpeedMode getSpeedControls() {
        return speedMode;
    }

    public void run() throws Exception {
        if (this.isRunning()) return;
        if (options.isHeadless()) {
            gameboy.run();
        } else {
            SwingUtilities.invokeLater(this::startGui);
        }
    }

    public void reset() throws Exception {
        stop();
        display = new SwingDisplay(SCALE);
        switchRom(rom.getFile(), true);
        run();
    }

    public void stop() {
        if (gameboy != null) {
            display.stop();
            gameboy.stop();
            sound.stop();
        }

    }

    public boolean isRunning() {
        if (gameboy == null) return false;
        return gameboy.isRunning();
    }

    public void switchRom(File f, boolean hardSwitch) throws IOException {
        options = new GameboyOptions(f, item);
        rom = new Cartridge(options);
        if (gameboy == null || hardSwitch)
            gameboy = new Gameboy(options, rom, display, controller, sound, serialEndpoint, console);
        else gameboy.setRom(rom);
    }

    public Joypad getJoypad() {
        return gameboy.getJoypad();
    }

    public SwingDisplay getDisplay() {
        return display;
    }

    private void startGui() {
        display.setPreferredSize(new Dimension(160 * SCALE, 144 * SCALE));
        /*  NOPE, no window for you!
        mainWindow = new JFrame("Coffee GB: " + rom.getTitle());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        mainWindow.setContentPane(display);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.pack();

        mainWindow.addKeyListener(controller);
        */
        new Thread(display).start();
        new Thread(gameboy).start();
    }

    private void stopGui() {
        display.stop();
        gameboy.stop();
        mainWindow.dispose();
    }
}
