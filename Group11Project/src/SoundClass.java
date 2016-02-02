import org.jsfml.audio.*;
import java.nio.file.*;
import java.io.IOException;

/*
	supported audio file formats: 
	ogg, wav, flac, aiff, 
	au, raw, paf, svx, nist, 
	voc, ircam, w64, mat4, 
	mat5 pvf, htk, sds, avr,
	sd2, caf, wve, mpc2k, rf64
	
	sounds stop automatically when finished playing
	background music is in a loop (when finished playing 
	it starts again from the begining) so it has to be stopped manually
	
	if you try to play a sound file that doesn't exist it will crash
*/

public class SoundClass
{
	/* Vars */
	private Sound music = new Sound();
	private Sound sound = new Sound();
	
	/* Cons */
	public SoundClass()
	{
		//load all sound samples, throw exception if any of the samples is missing,
		//fill an array with sample data ???

		//- Init (load a config file that includes the default settings + list of all sound samples)
		//		** COULD BE DONE IN CONS **		
	}
	
	/* Funcs */
	public void setSoundVolume(float newVolume)
	{
		if(0 <= newVolume && newVolume <= 100)
		{
			sound.setVolume(newVolume);
		}
	}
	
	public void setMusicVolume(float newVolume)
	{
		if(0 <= newVolume && newVolume <= 100)
		{
			music.setVolume(newVolume);
		}
	}
	
	public float getSoundVolume()
	{
		return sound.getVolume();
	}
	
	public float getMusicVolume()
	{
		return music.getVolume();
	}

	public void playBackgroundMusic(String songName)
	{
		music.setLoop(true);
		SoundBuffer tempSoundBuffer = new SoundBuffer();
		try {
			tempSoundBuffer.loadFromFile(Paths.get(songName));
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		music.setBuffer(tempSoundBuffer);
		music.play();
	}

	public void pauseBackgroundMusic()
	{
		music.pause();
	}

	public void resumeBackgroundMusic()
	{
		music.play();
	}

	public void stopBackgroundMusic()
	{
		music.stop();
	}

	public void playSound(String soundName)
	{
		SoundBuffer tempSoundBuffer = new SoundBuffer();
		try {
			tempSoundBuffer.loadFromFile(Paths.get(soundName));
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		sound.setBuffer(tempSoundBuffer);
		sound.play();
	}

	public void pauseSound()
	{
		sound.pause();
	}

	public void resumeSound()
	{
		sound.play();
	}

	public void stopSound()
	{
		sound.stop();
	}

	public void muteAll()
	{
		music.stop();
		sound.stop();
	}
}