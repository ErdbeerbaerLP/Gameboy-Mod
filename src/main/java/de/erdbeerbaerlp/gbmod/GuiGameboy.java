package de.erdbeerbaerlp.gbmod;

import de.erdbeerbaerlp.guilib.components.Button;
import de.erdbeerbaerlp.guilib.gui.BetterGuiScreen;
import eu.rekawek.coffeegb.controller.ButtonListener;
import org.lwjgl.input.Keyboard;

import java.io.IOException;


@SuppressWarnings("FieldCanBeLocal")
public class GuiGameboy extends BetterGuiScreen {

    ComponentGameboyScreen screen;
    Button btnReset;
    Button btnStop;
    Button btnStart;
    private final CapabilityGameBoy gb;

    public GuiGameboy(CapabilityGameBoy cap) {
        super();
        this.gb = cap;
        screen.setGb(cap);
    }

    /**
     * Add your components here!
     */
    @Override
    public void buildGui() {
        screen = new ComponentGameboyScreen(0, 0);
        btnStart = new Button(0,0,"Resume", Button.DefaultButtonIcons.PLAY);
        btnReset = new Button(0,0,"Reset");
        btnStop = new Button(0,0,"Pause",Button.DefaultButtonIcons.PAUSE);
        btnReset.setClickListener(()->{
            try {
                gb.getEmulator().reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnStart.setClickListener(()->{
            try {

                gb.getEmulator().run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnStop.setClickListener(() -> gb.getEmulator().stop());
        /*btnSetRom.setClickListener(()->{
            try{
                gb.cap.getEmulator().switchRom(new File("./config/gameboy-roms/pokemon-debug-yellow/DebugYellow.gbc"));
            }catch (Exception e){
                e.printStackTrace();
            }
        });*/
        addAllComponents(screen, btnReset, btnStart, btnStop);
    }

    /**
     * Gets called often to e.g. update components postions
     */
    @Override
    public void updateGui() {
        screen.setPosition(width/2, height/2);
        btnStop.setPosition(width/20, height/2);
        btnStart.setPosition(btnStop.getX(), btnStop.getY()-btnStart.getHeight()-10);
        btnReset.setPosition(btnStop.getX(), btnStop.getY()+btnStop.getHeight()+10);
        //btnSetRom.setPosition(btnStop.getX(), btnStart.getY()-btnSetRom.getHeight()-10);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Should pressing ESC close the GUI?
     */
    @Override
    public boolean doesEscCloseGui() {
        return true;
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
        final int key = Keyboard.getEventKey();
        final ButtonListener.Button btn = getButton(key);
        boolean press = Keyboard.isKeyDown(key);
        if(btn != null)
        if(press)
            gb.getEmulator().getJoypad().buttonPress(btn);
        else
            gb.getEmulator().getJoypad().buttonRelease(btn);
    }
    private ButtonListener.Button getButton(int key){
        if(key == Gbmod.keyGBRight.getKeyCode())
            return ButtonListener.Button.RIGHT;
        if(key == Gbmod.keyGBUp.getKeyCode())
            return ButtonListener.Button.UP;
        if(key == Gbmod.keyGBDown.getKeyCode())
            return ButtonListener.Button.DOWN;
        if(key == Gbmod.keyGBLeft.getKeyCode())
            return ButtonListener.Button.LEFT;
        if(key == Gbmod.keyGBA.getKeyCode())
            return ButtonListener.Button.A;
        if(key == Gbmod.keyGBB.getKeyCode())
            return ButtonListener.Button.B;
        if(key == Gbmod.keyGBStart.getKeyCode())
            return ButtonListener.Button.START;
        if(key == Gbmod.keyGBSelect.getKeyCode())
            return ButtonListener.Button.SELECT;
        return null;
    }
}
