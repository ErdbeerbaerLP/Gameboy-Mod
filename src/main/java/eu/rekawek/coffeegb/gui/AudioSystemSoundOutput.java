package eu.rekawek.coffeegb.gui;

import com.google.common.base.Preconditions;
import eu.rekawek.coffeegb.Gameboy;
import eu.rekawek.coffeegb.sound.SoundOutput;

import javax.sound.sampled.*;

public class AudioSystemSoundOutput implements SoundOutput {


    private static final int SAMPLE_RATE = 22050;

    private static final int BUFFER_SIZE = 1024;

    private static final AudioFormat FORMAT = new AudioFormat(AudioFormat.Encoding.PCM_UNSIGNED, SAMPLE_RATE, 8, 2, 2, SAMPLE_RATE, false);

    private SourceDataLine line;

    private byte[] buffer;

    private int i;

    private int tick;

    private int divider;

    // Not YET used, but planned
    public FloatControl volume;

    @Override
    public void start() {
        if (line != null) {
            //LOG.debug("Sound already started");
            return;
        }
        //LOG.debug("Start sound");
        try {
            line = AudioSystem.getSourceDataLine(FORMAT);
            //volume = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            line.open(FORMAT, BUFFER_SIZE);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        line.start();
        buffer = new byte[line.getBufferSize()];
        divider = (int) (Gameboy.TICKS_PER_SEC / FORMAT.getSampleRate());
    }

    @Override
    public void stop() {
        if (line == null) {
            //System.out.println("Can't stop - sound wasn't started");
        }
        //LOG.debug("Stop sound");
        if (line != null) {
            line.drain();
            line.stop();
        }
        line = null;
    }

    @Override
    public void play(int left, int right) {
        if (tick++ != 0) {
            tick %= divider;
            return;
        }

        Preconditions.checkArgument(left >= 0);
        Preconditions.checkArgument(left < 256);
        Preconditions.checkArgument(right >= 0);
        Preconditions.checkArgument(right < 256);

        buffer[i++] = (byte) (left);
        buffer[i++] = (byte) (right);
        if (i > BUFFER_SIZE / 2) {
            line.write(buffer, 0, i);
            i = 0;
        }
    }
}
