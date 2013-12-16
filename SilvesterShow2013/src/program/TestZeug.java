package program;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class TestZeug extends JFrame {
	GraphicsDevice[] device;
	
	public TestZeug(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		device = ge.getScreenDevices();
		System.out.println(device[0]);
	}
	public static void main(String args[]){
		new TestZeug();
	}
}
