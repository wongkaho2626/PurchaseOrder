package domain;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

public class Backupdbtosql {
	private static String ip;
	private static String port;
	private static String database;
	private static String user;
	private static String pass;
	private static String path = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump ";

	public static boolean export() throws SQLException{
		DBConnect connect = new DBConnect();
		ip = connect.getIP();
		port = connect.getport();
		database = connect.getdatabase();
		user = connect.getuser();
		pass = connect.getpass();
		connect.con.close();
		
		// parent component of the dialog
		JFrame parentFrame = new JFrame();

		JFileChooser fileChooser = new JFileChooser();
		FileSystemView fsv = FileSystemView.getFileSystemView();
		fileChooser.setDialogTitle("Specify a file to save"); 
		fileChooser.setCurrentDirectory(fsv.getHomeDirectory()); 

		int userSelection = fileChooser.showSaveDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());

			//Build sql file
			String filename = fileToSave.getAbsolutePath() + ".sql" ;

			String dumpCommand = path + database + " -h " + ip + " -u " + user +" -p" + pass;
			Runtime rt = Runtime.getRuntime();
			File test = new File(filename);
			PrintStream ps;

			try{
				Process child = rt.exec(dumpCommand);
				ps=new PrintStream(test);
				InputStream in = child.getInputStream();
				int ch;
				while ((ch = in.read()) != -1) {
					ps.write(ch);
					//				System.out.write(ch); //to view it by console
				}

				InputStream err = child.getErrorStream();
				while ((ch = err.read()) != -1) {
					System.out.write(ch);
				}
				System.out.println("Success");
				in.close();
				ps.close();
				return true;
			}catch(Exception exc) {
				exc.printStackTrace();
				return false;
			}
		}else{
			return false;
		}
	}
}