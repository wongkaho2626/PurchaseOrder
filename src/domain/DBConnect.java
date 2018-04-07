package domain;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.mysql.jdbc.PreparedStatement;

import UI.CreateGUILogin;

public class DBConnect {
	
	public Connection con;
	public Statement st;
	public ResultSet rs;
	
	private JPanel errorPanel;
	private String username;
	private String password;
	private String sql;
	
//	private static String ip = "192.168.1.101";
	private static String ip;
	private static String port;
	private static String database = "leeray";
	private static String user = "root";
	private static String pass = "root";
	
	public DBConnect(){
		IPandPort IPandPort = new IPandPort();
		ip = IPandPort.getIP();
		port = IPandPort.getPort();
//		System.out.println("ip: " + ip);
		try{
 			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + database, user, pass);
			st = con.createStatement();
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			
			JOptionPane
			.showMessageDialog(
					errorPanel,
					"Error: Connection error to Database.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	public String getIP(){
		return ip;
	}
	
	public String getport(){
		return port;
	}
	
	public String getdatabase(){
		return database;
	}
	
	public String getuser(){
		return user;
	}
	
	public String getpass(){
		return pass;
	}

}