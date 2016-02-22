import java.util.Date;
import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;

/**
 * Class used for managing the player leaderboard
 *
 * Notes:
 * - All data is saved in leaderboard.ini file,
 * - If the file is missing or is corrupted it will be replaced by default data (clean leaderboard),
 * - When submitting a new score using submit(), handle the return value (T/F) to give feedback to player so they know if their score was high enough to make it to the leaderboard,
**/

public class Leaderboard
{
	private int[] scores = new int[10];
	private String[] names = new String[10];
	private long[] timestamps = new long[10];
	
	/**
	* Loads the leaderboard data. If there is no data it creates a new file with default data.
	**/
	public Leaderboard()
	{
		if(!load())
		{
		  clear();
		  save();
		}
	}
	
	/**
	* Method to submit new score to the leaderboard.
	* @param name Name of the player (String).
	* @param score Player's score (Int).
	* @return Returns True if the score made it to the leaderboard, False if it wasn't high enough.
	**/
	public boolean submit(String name, int score)
	{
		if(score > scores[9]) //assuming scores are in order
		{
		  name = name.replaceAll(",", "");
		  names[9] = name;
			scores[9] = score;
			Date date = new Date();
			timestamps[9] = date.getTime();
			sortScores();
			save();
			return true;
		}
		else
		{
			return false; //submited score wasnt the highest
		}
	}
	
	/**
	* Method to get the name of the player at certain positon in the leaderboard.
	* @param position Position (1-10): 1-The first, 10-last.
	**/
	public String getName(int position)
	{
		if(1 <= position && position <= 10)
		{
			position = position-1;
			return names[position];
		}
		else 
		{
			return "Error";
		}
	}

	/**
	* Method to get the score of the player at certain positon in the leaderboard.
	* @param position Position (1-10): 1-The first, 10-last.
	**/	
	public int getScore(int position)
	{
		if(1 <= position && position <= 10)
		{
			position = position-1;
			return scores[position];
		}
		else 
		{
			return 0;
		}
	}
	
	/**
	* Method to get the submission date of the player at certain positon in the leaderboard.
	* @param position Position (1-10): 1-The first, 10-last.
	**/	
	public String getDate(int position)
	{
		if(1 <= position && position <= 10)
		{
			position = position-1;
			Date date = new Date(timestamps[position]);
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
			String dateString = DATE_FORMAT.format(date);		
			return dateString;
		}
		else 
		{
			return "Error";
		}
	}

	/**
	* Method to print the leaderboard into the console.
	**/
	public void display()
	{
	  for(int i=0; i<10; i++)
	  {
	    Date date = new Date(timestamps[i]);
	    System.out.println("#" + (i+1) + ": " + names[i] + " - " + scores[i] + " - " + date.toString());
	  }
	}

	/**
	* Method to sort the scores in the leaderboard from highest (1st) to lowest (last).
	**/
	private void sortScores() //1st ([0]) = the highest
	{
    for(int i=0; i<10; i++)
		{
			for(int j=i+1; j<10; j++)
			{
				if(scores[i] < scores[j]) //swap if necessary
				{
				  int tempScore = scores[j];
				  String tempName = names[j];
				  long tempTimestamp = timestamps[j];
				  scores[j] = scores[i];
				  names[j] = names[i];
				  timestamps[j] = timestamps[i];
				  scores[i] = tempScore;
				  names[i] = tempName;
				  timestamps[i] = tempTimestamp;
				}
			}
		}
	}

	/**
	* Method to encrypt data using simple caesar cipher (shift by 10).
	* @param plainText Plain text to be encrypted (String).
	* @return Cipher text (String). It will be the same length as the inputted plain text.
	**/
	private String encrypt(String plainText) //need to be tested before using
	{
	  String cipherText = "";
		for (int i=0; i<plainText.length(); i++)
		{
			int c = plainText.charAt(i);
			c = c + 10;
			if(c > 127)
			{
				c = c-127;
			}
			cipherText = cipherText + (char) c;
		}
	  return cipherText;
	}

	/**
	* Method to decrypt data using simple caesar cipher (shift by 10).
	* @param cipherText Cipher text to be decrypted (String).
	* @return Plain text (String). It will be the same length as the inputted cipher text.
	**/
	private String decrypt(String cipherText)
	{
	  String plainText = "";
		for (int i=0; i<cipherText.length(); i++)
		{
			int c = cipherText.charAt(i);		
			if(c < 10)
			{
				c = 127-(10-c);
			}
			else
			{
				c = c - 10;
			}
			plainText = plainText + (char) c;
		}
	  return plainText;
	}

	/**
	* Method to load leaderboard data from file: leaderboard.ini.
	* @return Returns True if successful, otherwise returns False (eg. if the file is missing).
	**/
	private boolean load() //changed to boolean - will it work with exceptions???
	{
		try
		{
			Scanner s = new Scanner(new FileReader("leaderboard.ini"));
			for (int i=0; i<10; i++)
			{
				String lineText = s.next();
				String[] temp = lineText.split(",");
				names[i] = decrypt(temp[0]);
				scores[i] = Integer.parseInt(decrypt(temp[1]));
				timestamps[i] = Long.parseLong(decrypt(temp[2]));
			}
			s.close();
			return true;
		}
		catch (Exception p)
		{
			System.out.println("Can't find the leaderboard.ini file! It should be in the same folder as this class file.");
			//System.out.println(p);
			return false;
		}
	}

	/**
	* Method to save the leaderboard data into file: leaderboard.ini.
	**/
	private void save()
	{
		String line;
		File file = new File ("leaderboard.ini");
		try
		{
			file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(int i=0; i<10; i++)
			{
				line = encrypt(names[i]) + ',' + encrypt(Integer.toString(scores[i])) + ',' + encrypt(Long.toString(timestamps[i])) + '\n'; //is \n needed?
				out.write(line);
			}
			out.close();
		}
		catch (Exception p)
		{
		}
	}

	/**
	* Method to clear the leaderboard (input default data).
	**/
	private void clear()
	{
		Date date = new Date();
	  for(int i=0; i<10; i++)
	  {
	    scores[i] = 0;
	    names[i] = "n/a";
	    timestamps[i] = date.getTime();
	  }
	}
}
/*

- Load
- Save
- Display???
- Submit new score?
- Encryption
- timestamps
- clear

- use L for long integers values eg long temp = 214389147231L;
*/