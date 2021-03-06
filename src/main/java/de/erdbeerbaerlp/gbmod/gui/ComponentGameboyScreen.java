package de.erdbeerbaerlp.gbmod.gui;

import de.erdbeerbaerlp.gbmod.items.CapabilityGameBoy;
import de.erdbeerbaerlp.guilib.components.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComponentGameboyScreen extends GuiComponent {

    private final int imgwidth, imgheight;
    final DynamicTexture img;
    private CapabilityGameBoy gb;

    @SuppressWarnings("SameParameterValue")
    protected ComponentGameboyScreen(int x, int y) {
        super(x, y, 0, 0);
        imgwidth = 160;
        imgheight = 144;
        img = new DynamicTexture(imgwidth, imgheight);
    }

    public void setGb(CapabilityGameBoy gb) {
        this.gb = gb;
    }

    /**
     * Draws the component
     *
     * @param mouseX
     * @param mouseY
     * @param partial
     */
    @Override
    public void draw(int mouseX, int mouseY, float partial) {
        if (gb == null || gb.getEmulator().getDisplay() == null) return;
        if (gb.getEmulator() != null && gb.getEmulator().getDisplay() != null && gb.getEmulator().getDisplay().img != null)
            gb.getEmulator().getDisplay().img.getRGB(0, 0, imgwidth, imgheight, img.getTextureData(), 0, imgwidth);
        img.updateDynamicTexture();
        Minecraft.getMinecraft().renderEngine.bindTexture(Minecraft.getMinecraft().renderEngine.getDynamicTextureLocation("gbmod_gameboyScreen", img));
        drawModalRectWithCustomSizedTexture(getX() - imgwidth / 2, getY() - imgheight / 2, 0, 0, imgwidth, imgheight, imgwidth, imgheight);
    }

    /**
     * Called on mouse click
     *
     * @param mouseX
     * @param mouseY
     * @param mouseButton
     */
    @Override
    public void mouseClick(int mouseX, int mouseY, int mouseButton) {

    }

    /**
     * Called on mouse release
     *
     * @param mouseX
     * @param mouseY
     * @param state
     */
    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {

    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     *
     * @param typedChar
     * @param keyCode
     */
    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     *
     * @param mouseX
     * @param mouseY
     * @param clickedMouseButton
     * @param timeSinceLastClick
     */
    @Override
    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {

    }
}
