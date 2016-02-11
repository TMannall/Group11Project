// STUBBED FILE
import java.sql.*;
import java.util.ArrayList;

// this is the class through which all Database calls go
public class DbUser extends DbBasic {

	private Statement stmt;
	private ResultSet rs = null;
	static private final int STR_SIZE = 25;

	// this method takes a String, converts it into an array of bytes;
	// copies those bytes into a bigger byte array (STR_SIZE worth), and
	// pads any remaining bytes with spaces. Finally, it converts the bigger
	// byte array back into a String, which it then returns.
	// e.g. if the String was "s_name", the new string returned is
	// "s_name                    " (the six characters followed by 18 spaces).
	private String pad(String in) {
		byte [] org_bytes = in.getBytes( );
		byte [] new_bytes = new byte[STR_SIZE];
		int upb = in.length( );

		if ( upb > STR_SIZE )
			upb = STR_SIZE;

		for ( int i = 0; i < upb; i++ )
			new_bytes[i] = org_bytes[i];

		for ( int i = upb; i < STR_SIZE; i++ )
			new_bytes[i] = ' ';

		return new String( new_bytes );
	}

	/*
	 * Creates a connection to the named database
	 */
	DbUser ( String dbName ){
		super( dbName );
		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * You need to complete the following methods
	 */
	
	public ArrayList getQuery(String query, String[] titles)
	{
		ArrayList<String> results = new ArrayList<>();
		try{
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next ()){
				for(int i = 0; i < titles.length; i++)
					results.add(rs.getString(titles[i]));
			}
		}catch (Exception e){
			System.out.println("Something went wrong ***DbUser getQuery***  " + query);
		}
		return results;
	}

	public void execute(String query)
	{
		try{
				stmt.executeUpdate(query);
		}catch (Exception e){
			System.out.println("Something went wrong ***DbUser execute***");
		}
	}
}
