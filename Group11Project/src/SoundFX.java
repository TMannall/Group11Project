import org.jsfml.audio.*;
import java.nio.file.*;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.io.File;

/*
  Notes:
	- Supported audio file formats: ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64.
	- Sounds stop automatically when finished playing, so there is no need to call the stop function.
	- Background music is in a loop (when finished playing it starts again from the begining) so it has to be stopped manually.
*/

public class SoundFX
{
	private static final int MAX_SAMPLES = 100; //max number of samples to be loaded from the config file
	private float soundVolume = (float) 100; //default sound volume
	private float musicVolume = (float) 100; //default music volume
	private Sound music = new Sound();
	private Sound sound = new Sound();
	private String[] samplePath = new String[MAX_SAMPLES];
	private String[] sampleName = new String[MAX_SAMPLES];
	private int numberOfSamples = 0;
	
	/**
	* Load the SoundFX and the list of sound samples using the config file: 'sound_config.ini'.
	**/
	public SoundFX()
	{
	  //Read the config file
		try
		{
			Scanner s = new Scanner(new FileReader("src/sound_config.ini"));
			while (s.hasNext() && numberOfSamples<MAX_SAMPLES)
			{
				String lineText = s.next();
				String[] temp = lineText.split(",");
				samplePath[numberOfSamples] = temp[0];
				sampleName[numberOfSamples] = temp[1];
				numberOfSamples++;
			}
			s.close();
		}
		catch (Exception p)
		{
			System.out.println("Can't find the sound_config.ini file! It should be in the same folder as this class file.");
		}
		//Check if the sound files are there
		for (int i=0; i<=numberOfSamples; i++)
		{
			try {
				File f = new File(samplePath[i]);
				if(f.isFile() == false)
				{
					System.out.println("ERROR: Sample " + samplePath[i] + " is missing. Check the configuration file.");
					sampleName[i] = null;
				}
			} catch(Exception e) {
      }
		}
		//Set the default volume levels
		sound.setVolume(soundVolume);
		music.setVolume(musicVolume);
	}
	
	/**
	* A method to set the sound volume (used by: playSound(), playSoundOnce(), playSoundFor()).
	* @param newVolume Float value from 0 (muted volume) to 100 (max volume)
	**/
	public void setSoundVolume(float newVolume)
	{
		if(0 <= newVolume && newVolume <= 100)
		{
			sound.setVolume(newVolume);
			soundVolume = newVolume;
		}
	}
	
	/**
	* A method to set the music volume (used by: playBackgroundMusic()).
	* @param newVolume Float value from 0 (muted volume) to 100 (max volume)
	**/
	public void setMusicVolume(float newVolume)
	{
		if(0 <= newVolume && newVolume <= 100)
		{
			music.setVolume(newVolume);
			musicVolume = newVolume;
		}
	}
	
	/**
	* A method to get the sound volume level (used by: playSound(), playSoundOnce(), playSoundFor()).
	* @return Sound volume level as float value from 0 (muted volume) to 100 (max volume)
	**/
	public float getSoundVolume()
	{
		return soundVolume;
	}
	
	/**
	* A method to get the music volume level (used by: playBackgroundMusic()).
	* @return Music volume level as float value from 0 (muted volume) to 100 (max volume)
	**/
	public float getMusicVolume()
	{
		return musicVolume;
	}

	/** A method to play a sound once without the control to pause or stop it.
	* Supported audio file formats: ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64.
	* @param soundName Name of the sound sample as specified in the config file.
	**/
	public void playSoundOnce(String soundName)
	{
		int songLocation = searchForSample(soundName);
		if(songLocation != -1)
		{
		Sound tempSound = new Sound();
			SoundBuffer tempSoundBuffer = new SoundBuffer();
			try {
				tempSoundBuffer.loadFromFile(Paths.get(samplePath[songLocation]));
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			tempSound.setBuffer(tempSoundBuffer);
			tempSound.setVolume(soundVolume);
			tempSound.setLoop(false);
			tempSound.play();
		}
	}

	/** A method to play a sound for a specific time without the control to pause or stop it. The sound is played in a loop.
	* Supported audio file formats: ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64.
	* @param soundName Name of the sound sample as specified in the config file.
	* @param time The time (in milliseconds) for how long the sound will be played.
	**/
	public void playSoundFor(String soundName, int time)
	{
		if(0 < time)
		{
			int songLocation = searchForSample(soundName);
			if(songLocation != -1)
			{
				Sound tempSound = new Sound();
				SoundBuffer tempSoundBuffer = new SoundBuffer();
				try {
					tempSoundBuffer.loadFromFile(Paths.get(samplePath[songLocation]));
				} catch(IOException ex) {
					ex.printStackTrace();
				}
				tempSound.setBuffer(tempSoundBuffer);
				tempSound.setVolume(soundVolume);
				tempSound.setLoop(true);
				tempSound.play();
				Timer tempTimer = new Timer();
				TimerTask tempTask = new TimerTask() {
						@Override
						public void run() {
							tempSound.stop();
							tempTimer.cancel();
						};
				};
				tempTimer.schedule(tempTask, time);
			}
		}
	}

	/** A method to play a sound in a loop with the control to pause and stop it. It uses the music volume level.
	* If background music is already playing it wil be replaced by the new background music.
	* Supported audio file formats: ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64.
	* @param songName Name of the sound sample as specified in the config file.
	**/
	public void playBackgroundMusic(String songName)
	{
		int songLocation = searchForSample(songName);
		if(songLocation != -1)
		{
			music.setLoop(true);
			SoundBuffer tempSoundBuffer = new SoundBuffer();
			try {
				tempSoundBuffer.loadFromFile(Paths.get(samplePath[songLocation]));
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			music.setBuffer(tempSoundBuffer);
			music.play();
		}
	}

	/**
	* A method to pause the background music. It can be resumed by resumeBackgroundMusic() function.
	**/
	public void pauseBackgroundMusic()
	{
		music.pause();
	}

	/**
	* A method to resume the background music. It can be paused again by pauseBackgroundMusic() function.
	**/
	public void resumeBackgroundMusic()
	{
		music.play();
	}

	/**
	* A method to stop the background music. Once stopped it can't be resumed by resumeBackgroundMusic().
	**/
	public void stopBackgroundMusic()
	{
		music.stop();
	}

	/** A method to play a sound once with the control to pause or stop it anytime.
	* Supported audio file formats: ogg, wav, flac, aiff, au, raw, paf, svx, nist, voc, ircam, w64, mat4, mat5 pvf, htk, sds, avr, sd2, caf, wve, mpc2k, rf64.
	* @param soundName Name of the sound sample as specified in the config file.
	**/
	public void playSound(String soundName)
	{
		int songLocation = searchForSample(soundName);
		if(songLocation != -1)
		{
			SoundBuffer tempSoundBuffer = new SoundBuffer();
			try {
				tempSoundBuffer.loadFromFile(Paths.get(samplePath[songLocation]));
			} catch(IOException ex) {
				ex.printStackTrace();
			}
			sound.setBuffer(tempSoundBuffer);
			sound.play();
		}
	}

	/**
	* A method to pause the sound played using playSound(). It can be resumed by resumeSound() function.
	**/
	public void pauseSound()
	{
		sound.pause();
	}

	/**
	* A method to resume the sound played using playSound(). It can be paused again by pauseSound() function.
	**/
	public void resumeSound()
	{
		sound.play();
	}

	/**
	* A method to stop the sound played using playSound(). Once stopped it can't be resumed by resumeSound().
	**/
	public void stopSound()
	{
		sound.stop();
	}
	
	private int searchForSample(String name)
	{
		int found = -1;
		for(int i=0; i<=numberOfSamples; i++)
		{
			if(name.equals(sampleName[i]))
			{
				found = i;
			}
		}
		return found;
	}
}