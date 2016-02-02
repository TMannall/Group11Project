public class SoundTest
{
	public static void main(String[] Args)
	{
		SoundClass audio = new SoundClass();
		System.out.println("Trying to crash the program...");
		audio.stopSound();
		audio.pauseSound();
		audio.resumeSound();
		audio.stopBackgroundMusic();
		audio.pauseBackgroundMusic();
		audio.resumeBackgroundMusic();
		audio.setMusicVolume((float)50);
		audio.setSoundVolume((float)50);
		audio.setMusicVolume((float)-50);
		audio.setSoundVolume((float)-50);
		audio.setMusicVolume((float)100.00000000);
		audio.setSoundVolume((float)100.00000000000000000000000);
		System.out.println("Playing background music...");
		audio.playBackgroundMusic("music.wav");
		int i = 0;
		while(i<10)
		{
			i++;
			try {
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		//audio.pauseBackgroundMusic();
		System.out.println("Playing a sound...");
		audio.playSound("sound2.wav");
		while(i<13)
		{
			i++;
			try {	
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Playing another sound before the previous one have been stopped...");
		audio.playSound("sound.wav");
		//System.out.println("Pausing the music.");
		//audio.pauseBackgroundMusic();
		System.out.println("Changing volume to 10.");
		audio.setMusicVolume((float) 10);
		//audio.resumeBackgroundMusic();
		while(i<20)
		{
			i++;
			try {	
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Changing volume to 100.");
		audio.setMusicVolume((float) 100);
		while(i<35)
		{
			i++;
			try {	
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		//System.out.println("Resuming background music...");
		//audio.resumeBackgroundMusic();
		while(i<99)
		{
			i++;
			try {	
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Muting all sounds and music.");
		audio.muteAll();
		System.out.println("End of code.");
	}
}