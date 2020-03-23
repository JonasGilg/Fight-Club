package FileManagement;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.*;

public class SoundManagement {

	private Clip clip;

	public SoundManagement(String s) {

		try {

			URL url = getClass().getResource(s);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void play() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void loop() {
		if (clip == null)
			return;
		stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if (clip.isRunning())
			clip.stop();
	}

	public void close() {
		stop();
		clip.close();
	}

}
