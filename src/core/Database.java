/**
 * 
 */
package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author nerd
 *
 */
public final class Database
{

	/**
	 * 
	 */
	private static volatile Connection conx = null;
	private Database() 
	{
		
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
         * Thread safe singleton Database Connection
	 */
	public static Connection getInstance() throws ClassNotFoundException, SQLException
	{
		if(conx == null)
		{
                    synchronized(Database.class)
                    {
                        if(conx == null)
                        {
                            Class.forName("com.mysql.jdbc.Driver");
                            conx = DriverManager.getConnection("jdbc:mysql://127.0.0.1/artemis","root","banter");
                        }
                    }
			
		}
		return conx;
	}

}
