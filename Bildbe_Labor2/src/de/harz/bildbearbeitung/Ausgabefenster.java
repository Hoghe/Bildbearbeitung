package de.harz.bildbearbeitung;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.application.Platform;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DecimalFormat;


import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ausgabefenster extends JFrame {

	private JPanel contentPane;
	private JTextField _tfDateiname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ausgabefenster frame = new Ausgabefenster();
					frame.setTitle("Bildverarbeitung Labor2");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ausgabefenster() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblEingabedatei = new JLabel("Eingabedatei:");
		lblEingabedatei.setBounds(15, 15, 80, 20);
		contentPane.add(lblEingabedatei);
		
		_tfDateiname = new JTextField();
		_tfDateiname.setBounds(100, 15, 220, 20);
		contentPane.add(_tfDateiname);
		_tfDateiname.setColumns(10);
		
		
		JLabel lblGMin = new JLabel("G min:    ");
		lblGMin.setBounds(15, 50, 80, 20);
		contentPane.add(lblGMin);
		
		JLabel lblGMax = new JLabel("G max:    ");
		lblGMax.setBounds(15, 75, 80, 20);
		contentPane.add(lblGMax);
		
		JLabel lblGMittel = new JLabel("G mittel: ");
		lblGMittel.setBounds(15, 100, 80, 20);
		contentPane.add(lblGMittel);
		
		JLabel lblGVarianz = new JLabel("Varianz:  ");
		lblGVarianz.setBounds(180, 50, 120, 20);
		contentPane.add(lblGVarianz);
		
		JLabel lblEntropie = new JLabel("Entropie: ");
		lblEntropie.setBounds(180, 75, 120, 20);
		contentPane.add(lblEntropie);
		
		Button btAuswaehlen = new Button("Auswählen");
		btAuswaehlen.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				FileFilter filter = new FileNameExtensionFilter("Bilder", 
		                "gif", "png", "jpg", "bmp");
				JFileChooser fC = new JFileChooser();
				fC.addChoosableFileFilter(filter);
				fC.setAcceptAllFileFilterUsed(false);
				int rueckgabeWert = fC.showOpenDialog(null);
				if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
//					Platform.runLater(() -> {
//					contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//					});
					_tfDateiname.setText(fC.getSelectedFile().getPath());
					File Eingabedatei = fC.getSelectedFile();
					Bild _b = new Bild(Eingabedatei);
					_b.berechne();
					lblGMin.setText("G min:    "+_b.getGmin());
					lblGMax.setText("G max:    "+_b.getGmax());
					lblGMittel.setText("G mittel: "+_b.getGmittel());
					lblGVarianz.setText("Varianz:  "+_b.getVarianz());
					DecimalFormat format = new DecimalFormat("####0.###");
					lblEntropie.setText("Entropie: "+format.format(_b.getEntropie()));
//					contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
//		btAuswaehlen.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				FileFilter filter = new FileNameExtensionFilter("Bilder", 
//		                "gif", "png", "jpg", "bmp");
//				JFileChooser fC = new JFileChooser();
//				fC.addChoosableFileFilter(filter);
//				fC.setAcceptAllFileFilterUsed(false);
//				int rueckgabeWert = fC.showOpenDialog(null);
//				if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {		
//					_tfDateiname.setText(fC.getSelectedFile().getPath());
//					File Eingabedatei = fC.getSelectedFile();
//					Bild _b = new Bild(Eingabedatei);
//					_b.berechne();
//					lblGMin.setText("G min:    "+_b.getGmin());
//					lblGMax.setText("G max:    "+_b.getGmax());
//					lblGMittel.setText("G mittel: "+_b.getGmittel());
//					lblGVarianz.setText("Varianz:  "+_b.getVarianz());
//					DecimalFormat format = new DecimalFormat("####0.###");
//					lblEntropie.setText("Entropie: "+format.format(_b.getEntropie()));
//					
//				}
//			}
//		});
		btAuswaehlen.setBounds(325, 15, 80, 20);
		contentPane.add(btAuswaehlen);
		
		
		Button btBeenden = new Button("Beenden");
		btBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btBeenden.setBounds(265, 125, 140, 20);
		contentPane.add(btBeenden);
		
	}
}
