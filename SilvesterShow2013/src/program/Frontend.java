package program;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class Frontend extends JFrame implements ActionListener{
	static final long serialVersionUID = 1;
	static Properties prop = new Properties();
	public static int i = 0, j = 0, z = 0, b = 0, aktPic = 0;
    static JLabel time, countdown, anleitung, aufgabeHead, nachrichtHead, picture;
    static JTextPane message,message1, message2, aufgabe;
    GraphicsDevice[] device;
    private JButton kleiner,fullscreen;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) dim.getHeight();
    int y = (int) dim.getWidth();
    static Image img;
	Image pic;
    static String anleitungText1 ="Sende normale Nachricht an 0175 3255788 f�r eine normale Nachricht!";
    static String anleitungText2 ="Sende Musik am Anfang der Nachricht an 0175 3255788 f�r einen Musikwunsch!";
    static String anleitungText3 ="Sende Aufgabe am Anfang der Nachricht an 0175 3255788 um eine Aufgabe abzugeben!";
    static StyledDocument messageDoc;
    static String messageHeadline =" ", aufgabeHeadline= "",messageBuff = "", hol, messageOutput;
    static String[] pictureList;
    static byte[] messageBytes;
    static Insets inset1= new Insets(20, 5, 20, 5);
	static Border bord = new CompoundBorder(new MatteBorder(0,0,0,2,Color.white), new LineBorder(Color.black, 5,false));
	static Font font1 = new Font("Tahoma",0,20);
	static Font font2 = new Font("Tahoma", 0, 18);
	
	public Frontend(){
		Image background = null;
		try {
			background= ImageIO.read(new File("C:/pictures/background.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon backgroundIcon = new ImageIcon();
		try{
			this.setContentPane(
					new JLabel(backgroundIcon));
		}
		catch (Exception e) {
			System.out.println("Hintergrund?");
			System.out.println(e);
		}
		this.setSize(1280, 720);
		this.setBackground(Color.BLACK);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    device = ge.getScreenDevices();
	    
		kleiner = new JButton("Small");
		fullscreen = new JButton("Full");
		kleiner.setBorder(null);
		fullscreen.setBorder(null);
		kleiner.setFocusable(false);
		fullscreen.setFocusable(false);
		kleiner.setBackground(Color.black);
		fullscreen.setBackground(Color.black);
		kleiner.addActionListener(this);
		fullscreen.addActionListener(this);
		
		time = new JLabel();
		time.setBackground(Color.black);
		time.setForeground(Color.white);
		time.setHorizontalAlignment(SwingConstants.CENTER);
		
		countdown = new JLabel();
		countdown.setBackground(Color.black);
		countdown.setForeground(Color.white);
		countdown.setFont(new java.awt.Font("Tahoma", 0, 48));
		countdown.setHorizontalAlignment(SwingConstants.CENTER);
		
		nachrichtHead = new JLabel();
		nachrichtHead.setForeground(Color.white);
		nachrichtHead.setFont(font1);
		nachrichtHead.setText(messageHeadline);
		
		StyleContext.NamedStyle centerStyle = StyleContext.getDefaultStyleContext().new NamedStyle();
		StyleConstants.setAlignment(centerStyle,StyleConstants.ALIGN_CENTER);
		StyleConstants.setForeground(centerStyle, Color.white);
		StyleConstants.setFontSize(centerStyle, 22);
		StyleConstants.setFontFamily(centerStyle, "Tahoma");
		StyleConstants.setSpaceAbove(centerStyle, 5);
		
		
		message = new JTextPane();
		message.setText("Anfang der Nachrichten");
		message.setBackground(Color.black);
		messageDoc = message.getStyledDocument();
		messageDoc.setLogicalStyle(0, centerStyle);
		message.setStyledDocument(messageDoc);
		message.setFocusable(false);
		message.setBorder(bord);
		
		aufgabeHead = new JLabel();
		aufgabeHead.setForeground(Color.white);
		aufgabeHead.setFont(font1);
		aufgabeHead.setText(aufgabeHeadline);
		
		aufgabe = new JTextPane();
		aufgabe.setBackground(Color.black);
		aufgabe.setLogicalStyle(centerStyle);
		aufgabe.setFont(font2);
		aufgabe.setFocusable(false);
		
		anleitung = new JLabel();
		anleitung.setForeground(Color.white);
		anleitung.setText(anleitungText1);
		anleitung.setFont(font1);
		anleitung.setHorizontalAlignment(SwingConstants.CENTER);
		
		try{
			img = ImageIO.read(new File("C:/pictures/bt_silvesterparty_300px_2_4cb5b57f10b3c.jpg"));
			ImageIcon image = new ImageIcon(img);
			picture = new JLabel(image);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
				.addGroup(layout.createSequentialGroup()
						.addComponent(kleiner,20,20,20)
						.addComponent(fullscreen,20,20,20)
						.addComponent(time, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						)
				.addComponent(countdown,0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
								.addComponent(nachrichtHead, 0, countdown.getWidth()/2, Short.MAX_VALUE)
								.addComponent(message, 0, countdown.getWidth()/2, Short.MAX_VALUE)
								)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
								.addComponent(aufgabeHead, 0, countdown.getWidth()/2, Short.MAX_VALUE)
								.addComponent(aufgabe, 0, countdown.getWidth()/2, Short.MAX_VALUE)
								.addComponent(picture, 0, countdown.getWidth()/2, Short.MAX_VALUE)
								)
						)
				.addComponent(anleitung,0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
						.addComponent(kleiner,10,10,10)
						.addComponent(fullscreen,10,10,10)
						.addComponent(time)
						)
				.addComponent(countdown)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING,true)
						.addGroup(layout.createSequentialGroup()
								.addComponent(nachrichtHead)
								.addComponent(message, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								)
						.addGroup(layout.createSequentialGroup()
								.addComponent(aufgabeHead)
								.addComponent(aufgabe, 0, message.getHeight()/4, 150)
								.addComponent(picture, 0,(int) (message.getHeight()/1.5), Short.MAX_VALUE)
								)
						)
				.addComponent(anleitung)
				);
		fullscreen.setVisible(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args){
		Backend.connect();
		Backend.connectFTP();
		new Frontend().setBackground(Color.BLACK);
		TimerTask timertask = new TimerTask() {
			@Override
			public void run(){
				i ++;
				j ++;
				z ++;
			    time.setText(Backend.time());
				countdown.setText(Backend.countdown());
				if (i == 3){
					hol = Backend.messages();
					if (messageBuff.isEmpty() || !messageBuff.equals(hol)){
						if(!(hol == null)){
							messageBuff = hol;
							messageBytes = hol.getBytes();
							try{
								messageOutput = new String(messageBytes, "utf8");
							}
							catch(Exception e){
								System.out.println(e);
							}
							
							try {
								messageDoc.insertString(0, messageOutput, null);
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					i = 0;
					
				}
				
				if (j == 300){
					aufgabe.setText(Backend.aufgaben());
					j = 0;
				}
				if (z == 10)
					anleitung.setText(anleitungText1);
				if (z == 20)
					anleitung.setText(anleitungText2);
				if (z == 30){
					anleitung.setText(anleitungText3);
					z = 0;
				}
			}
		};
		
		TimerTask timerTask1 = new TimerTask() {

			@Override
			public void run() {
				b ++;
				if(b == 60){
						//getPictureFTP();
						getPictureLocal();
						b = 0;
					}
				}
				
			
			
		};
		Timer timer = new Timer();
		Timer timer1 = new Timer();
		timer.schedule(timertask, 1,1000);
		timer1.schedule(timerTask1,1,1000);
	}
	
	//Lokale Bild-Anzeige in chronologischer Reihenfolge
	public static void getPictureLocal(){
		String path = "C:/pictures/";
		File dir = new File(path);
		pictureList = dir.list();
		int anzahlPictures = pictureList.length-1;
		if(anzahlPictures >= aktPic){
			String picturePath = path+pictureList[aktPic];
			try{
				ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(picturePath));
				image.setImage(image.getImage().getScaledInstance(400, -1, Image.SCALE_SMOOTH));
				picture.setIcon(image);
			}
			catch(Exception e) {
				System.out.println(e);
			}	
			aktPic++;
		}
		
		
	}
	
	//Random FTP-Bildanzeige
	public static void getPictureFTP(){
		pictureList = Backend.getFilesFTP();
		String pictureURL = pictureList[(int)(pictureList.length*Math.random())];
		try{
			URL url = new URL(prop.getProperty("ftpUser")+":"+prop.getProperty("ftpPwd")+"@"+prop.getProperty("ftpServer")+pictureURL);
			//img = ImageIO.read(url);
			ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().createImage(url));
			image.setImage(image.getImage().getScaledInstance(350, -1 , Image.SCALE_DEFAULT));
			picture.setIcon(image);
			System.out.println(url);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == kleiner){
			fullscreen.setVisible(true);
			kleiner.setVisible(false);
			if(device.length == 1)
				device[0].setFullScreenWindow(null);
			else
				device[1].setFullScreenWindow(null);
			this.setSize(1280, 720);
		}
		if (e.getSource() == fullscreen){
			fullscreen.setVisible(false);
			kleiner.setVisible(true);
			if (device.length == 1 && device[0].isFullScreenSupported())
				device[0].setFullScreenWindow(this);
			else if (device[1].isFullScreenSupported())
				device[1].setFullScreenWindow(this);
		}
		
	}
}
