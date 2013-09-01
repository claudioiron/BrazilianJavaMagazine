package br.com.javamagazine.client.gui;
import java.awt.EventQueue;

import javax.swing.JFrame;


public class MainFrame {

	private JFrame frame;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
            public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setContentPane(new ContatoPanel());
        frame.setBounds(100, 100, 500, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ExceptionHandler.setUncaughtExceptionHandler(frame);
	}

}
