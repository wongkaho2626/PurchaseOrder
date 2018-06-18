package domain;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class Restoredbtosql {
	public static Connection con;
	public static Statement st;
	public ResultSet rs;

	private static String ip;
	private static String port;
	private static String database = "leeray";
	private static String user = "root";
	private static String pass = "root";
//	private static String path = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysql";
	
	//for development
	private static String path = "/Applications/MAMP/Library/bin/mysql";

	public static boolean restore() throws SQLException{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("leeray.config.properties");

			// load a properties file
			prop.load(input);
			ip = prop.getProperty("ip");
			port = prop.getProperty("port");
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//for development
//		ip = "localhost";
//		port = "3306";
		
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port, user, pass);

			//STEP 4: Execute a query
			System.out.println("Creating database...");
			st = con.createStatement();

			String sql = "CREATE DATABASE leeray";
			st.executeUpdate(sql);

			System.out.println("Database created successfully...");
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}

		// parent component of the dialog
		JFrame parentFrame = new JFrame();
		
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("sql file", "sql");
		FileSystemView fsv = FileSystemView.getFileSystemView();
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(fsv.getHomeDirectory());  
		int returnVal = chooser.showOpenDialog(parentFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getPath());
			String source = chooser.getSelectedFile().getPath();

			String[] executeCmd = new String[]{path, "--user=" + user, "--password=" + pass, database,"-e", " source "+source};  
			Process runtimeProcess;  
			try {  
				runtimeProcess = Runtime.getRuntime().exec(executeCmd);  
				int processComplete = runtimeProcess.waitFor();  
				if (processComplete == 0) {  
					System.out.println("Backup restored successfully");  
					return true;  
				}  else {  
					System.out.println("Could not restore the backup");  
				}  
			} catch (Exception ex) {  
				ex.printStackTrace();  
			}  
			return false;  
		}  
		return false;
	}
}