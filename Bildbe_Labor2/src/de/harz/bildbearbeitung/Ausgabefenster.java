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
	private Bild _bild;


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
		lblGMin.setBounds(15, 50, 100, 20);
		contentPane.add(lblGMin);
		
		JLabel lblGMax = new JLabel("G max:    ");
		lblGMax.setBounds(15, 75, 100, 20);
		contentPane.add(lblGMax);
		
		JLabel lblGMittel = new JLabel("G mittel: ");
		lblGMittel.setBounds(15, 100, 100, 20);
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
					_tfDateiname.setText(fC.getSelectedFile().getPath());
					File Eingabedatei = fC.getSelectedFile();
					_bild = new Bild(Eingabedatei);
				}
			}
		});

		btAuswaehlen.setBounds(325, 15, 80, 20);
		contentPane.add(btAuswaehlen);
		
		Button btBerechnen = new Button("Werte berechnen");
		btBerechnen.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				_bild.berechne();
				DecimalFormat format_ohne = new DecimalFormat("##0");
				DecimalFormat format_3 = new DecimalFormat("####0.###");
				lblGMin.setText("G min:     "+format_ohne.format(_bild.getGmin()));
				lblGMax.setText("G max:    "+format_ohne.format(_bild.getGmax()));
				lblGMittel.setText("G mittel: "+format_3.format(_bild.getGmittel()));
				lblGVarianz.setText("Varianz:  "+format_3.format(_bild.getVarianz()));
				lblEntropie.setText("Entropie: "+format_3.format(_bild.getEntropie()));
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});

		btBerechnen.setBounds(15, 125, 140, 20);
		contentPane.add(btBerechnen);
		
		
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
