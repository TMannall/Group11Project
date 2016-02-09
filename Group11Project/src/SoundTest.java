public class SoundTest
{
	public static void main(String[] Args)
	{
		SoundClass audio = new SoundClass();
		System.out.println("Playing a sound w/o control...");
		audio.playSoundOnce("TEST_SOUND2");
		int i = 0;
		while(i<5)
		{
			i++;
			try {
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("5 seconds just passed.");
		while(i<10)
		{
			i++;
			try {
				Thread.sleep(1000); 
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("End of code.");
	}
}