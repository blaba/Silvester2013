package program;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.Properties;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.net.ftp.*;

public class Backend {
	static int nrID,durchlauf,aufgabeID;
	static Properties prop = new Properties();
	static Connection connection = null;
	static String nachricht = "";
	static private String ftpServer,ftpUser, ftpPwd, sqlServer, sqlUser, sqlDatabase, sqlPwd;
	static FTPClient ftpClient = new FTPClient();
	public static void connectFTP(){
		try {
			prop.load(new FileInputStream("config.properties"));
			ftpServer = prop.getProperty("ftpUrl");
			ftpUser = prop.getProperty("ftpUser");
			ftpPwd = prop.getProperty("ftpPassword");
		}
		catch (Exception e){
			System.out.println("config.properties vorhanden?");
		}
		
		int port = 21;
		try {
			ftpClient.connect(ftpServer,port);
			System.out.println(ftpClient.getReplyString());
			ftpClient.login(ftpUser, ftpPwd);
			System.out.println(ftpClient.getReplyString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static String[] getFilesFTP(){
		String[] files = null;
		FTPFile[] ftpFiles = null;
		
		try{
			ftpFiles = ftpClient.listFiles("/html/silvester/pictures");
			files = ftpClient.listNames("/html/silvester/pictures");	
			
			}
		catch(Exception e){
			System.out.println(e);
		}
		return files;
		
		
	}
	public static Connection connect(){
		try {
			prop.load(new FileInputStream("config.properties"));
			sqlServer = prop.getProperty("sqlUrl");
			sqlDatabase = prop.getProperty("sqlDB");
			sqlUser = prop.getProperty("sqlUser");
			sqlPwd = prop.getProperty("sqlPassword");
		}
		catch (Exception e){
			System.out.println("config.properties vorhanden?");
		}
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectCommand = "jdbc:mysql://"+sqlServer+"/"+sqlDatabase+"?user="+sqlUser+"&password="+sqlPwd;
			connection = DriverManager.getConnection(connectCommand);
			System.out.println("Verbunden");
			String idInfo = "SELECT id FROM messages ORDER BY id DESC LIMIT 1";
			Statement stat0 = connection.createStatement();
			ResultSet idNr = stat0.executeQuery(idInfo);
			idNr.next();
			nrID = idNr.getInt(1);
		}
        catch (ClassNotFoundException e){ //Exception-Handling wenn Treiber nicht geladen
            System.out.println("Treiber nicht geladen");
            System.out.println(e);
            
        }
		catch (SQLException e){ //Exception-Handling wenn Anmeldung fehl schlug
			System.out.println(e);
        }
		catch (Exception e){
			System.out.println(e);
		}
		
		return connection;
	}
	public static String time(){
		String time="";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		time = sdf.format(new Date());
		return time;
	}
	public static String messages(){
		try {
			//System.out.println(nrID);
			String nachrichtAbruf = "SELECT message FROM messages WHERE id = " + nrID;
			Statement stat1 = connection.createStatement();
			Statement stat2 = connection.createStatement();
			String nameAbruf = "SELECT name FROM messages WHERE id = " + nrID +" AND name != '+00'";
			ResultSet nachrichten = stat1.executeQuery(nachrichtAbruf);
			ResultSet namen = stat2.executeQuery(nameAbruf);
			if(nachrichten.next()){
				if(namen.next()){
					byte[] namenBytes =namen.getString(1).getBytes();
					String namenString = new String (namenBytes,"utf-8");
					nachricht =namenString +" schrieb:\n" +nachrichten.getString(1)+"\n\n";	
					nrID++;
					return nachricht;
				}
				
			}
			
		}
		catch (Exception e){
			System.out.println(e);
		}
		return null;
	}
	public static String aufgaben(){
		String ausgabe = "";
		byte[] ausgabeBytes;
		try{
			String aufgabeAbruf = "SELECT aufgabe,id FROM aufgaben ORDER BY RAND() LIMIT 1";
			Statement stat0 = connection.createStatement();
			ResultSet aufgaben = stat0.executeQuery(aufgabeAbruf);
			
			String aufgabeName = "SELECT name FROM user ORDER BY RAND() LIMIT 1";
			Statement stat1 = connection.createStatement();
			ResultSet namen = stat1.executeQuery(aufgabeName);
			if(aufgaben.next()&&namen.next()){
				byte[] namenBytes =namen.getString(1).getBytes();
				String namenString = new String (namenBytes,"utf-8");
				ausgabe = "An "+namenString+":\n " +aufgaben.getString(1);
			}
			else 
				ausgabe = "Keine Aufgabe? :(";
		}
		catch(Exception e){
			System.out.println(e);
		}
		return ausgabe;
	}
	
	public static String teilnehmer(){
		String liste="";
		try{
			String teilnahme = "SELECT name FROM user";
			Statement stat1 = connection.createStatement();
			ResultSet teil = stat1.executeQuery(teilnahme);
			while(teil.next()){
				liste = liste + teil.getString(1)+"\n";
			}
			System.out.println(liste);
		}
		catch (Exception e){
			System.out.println(e);
		}
		return liste;
	}
	public static boolean sendMessage(String name, String nachricht){
		try{
			Statement st = connection.createStatement();
			nrID++;
			st.executeUpdate("INSERT INTO messages VALUES ("  +nrID + " , '" +name + "' , '" + nachricht +"', 'Computer')");
			System.out.println("Information eingefügt");
			
		}
		catch(Exception e){
			System.out.println(e);
		}
		return true;
		
	}
	public static String countdown(){
		String zeit="";
		Date sylvester2013= new java.util.Date(114,0,0,0,0);	//hier legen wir den Tag fest zu dem wir die differenz bestimmen wollen(109=jahre seit 1900, steht für 2009; 0=monat(0-11);1=tag; 0=stunden; 0=minuten)
		long curTime=System.currentTimeMillis();//+3600000; //wir holen uns den long für die aktuelle zeit(+3600000 um das ganze an unsere Zeitzone anzupassen)
		long diff=sylvester2013.getTime()-curTime; //rechnen die differenz zwischen den beiden zeitpunkten aus
		//rechnen das ganze in tage, studnen und minuten um( das spielchen kannst du natürlich auch bis auf die millisekunde weitertreiben)
		long day = diff/86400000;
		long hour = ((diff % 86400000)/3600000);
		long min = (diff % 3600000)/60000;
		long sek = (diff % 60000)/1000+1;
		//...und die Ausgabe
		if(diff < 1)
			return "Frohes neues Jahr!!!";
		
		if (hour > 0)
			zeit = hour +":";
		if (sek == 60){
			min = min+1;
		}
		if (min < 10 && hour < 0 )
			zeit = zeit +"0"+min+":";
		else if (min < 10 && hour >= 1)
			zeit = zeit +"0"+min+":";
		else if (min > 0)
			zeit = zeit + min + ":";
		if (sek == 60)
			zeit = zeit+"00";
		if (sek <10 && sek > 0 && sek != 60)
			zeit = zeit +"0"+sek;
		else if (sek != 60)
			zeit = zeit +sek;
		if (hour > 0)
			zeit = zeit + " Stunden";
		if (hour < 1 && min > 0)
			zeit = zeit + " Minuten";
		if(hour < 1 && min < 1)
			zeit = zeit + " Sekunden";
			
		return "Noch "+zeit+" bis 2014!";
	}
	
}
