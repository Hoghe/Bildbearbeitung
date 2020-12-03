package de.harz.bildbearbeitung;


import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JRadioButton;
import javax.swing.JSlider;


public class Ausgabefenster extends JFrame {

	private JPanel contentPane;
	private JTextField _tfDateiname;
	private JTextField _tfschwellwert;
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
		setBounds(100, 100, 690, 400);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblEingabedatei = new JLabel("Eingabedatei:");
		lblEingabedatei.setBounds(15, 15, 90, 20);
		contentPane.add(lblEingabedatei);
		
		_tfDateiname = new JTextField();
		_tfDateiname.setBounds(110, 15, 455, 20);
		contentPane.add(_tfDateiname);
		_tfDateiname.setColumns(10);
		
		JLabel lblOrginaldatei = new JLabel();
		lblOrginaldatei.setBounds(15, 40, 320, 180);
		contentPane.add(lblOrginaldatei);
		
		JLabel lblBildtextOri = new JLabel();
		lblBildtextOri.setEnabled(false);
		lblBildtextOri.setBounds(15, 225, 320, 20);
		contentPane.add(lblBildtextOri);
		
		JLabel lblBerechnetesbild = new JLabel();
		lblBerechnetesbild.setBounds(345, 40, 320, 180);
		contentPane.add(lblBerechnetesbild);
		
		JLabel lblBildtextBer = new JLabel();
		lblBildtextBer.setBounds(345, 225, 320, 20);
		contentPane.add(lblBildtextBer);
		
		
		JLabel lblGMin = new JLabel("");
		lblGMin.setBounds(345, 250, 100, 20);
		contentPane.add(lblGMin);
		
		JLabel lblGMax = new JLabel("");
		lblGMax.setBounds(345, 275, 100, 20);
		contentPane.add(lblGMax);
		
		JLabel lblGMittel = new JLabel("");
		lblGMittel.setBounds(345, 300, 100, 20);
		contentPane.add(lblGMittel);
		
		JLabel lblGVarianz = new JLabel("");
		lblGVarianz.setBounds(510, 250, 120, 20);
		contentPane.add(lblGVarianz);
		
		JLabel lblEntropie = new JLabel("");
		lblEntropie.setBounds(510, 275, 120, 20);
		contentPane.add(lblEntropie);
		
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton rdbtnGrauwerte = new JRadioButton("Grauwerte berechnen", true);
		rdbtnGrauwerte.setBounds(15, 250, 160, 20);
		bg.add(rdbtnGrauwerte);
		contentPane.add(rdbtnGrauwerte);
		
		JRadioButton rdbtnBinarisierung = new JRadioButton("Binarisierung");
		rdbtnBinarisierung.setBounds(185, 275, 110, 20);
		bg.add(rdbtnBinarisierung);
		contentPane.add(rdbtnBinarisierung);
		
		_tfschwellwert = new JTextField();
		_tfschwellwert.setToolTipText("Bitte Schwellwert zwischen 0 und 255 eingeben (Default-Wert: 127");
		_tfschwellwert.setBounds(295, 275, 40, 20);
		contentPane.add(_tfschwellwert);
		_tfschwellwert.setColumns(10);
		
		
		JRadioButton rdbtnAequidensiten = new JRadioButton("Äquidensiten");
		rdbtnAequidensiten.setBounds(185, 250, 160, 20);
		bg.add(rdbtnAequidensiten);
		contentPane.add(rdbtnAequidensiten);
		
		JRadioButton rdbtnHistogramm = new JRadioButton("Histogramm");
		rdbtnHistogramm.setBounds(15, 275, 160, 20);
		bg.add(rdbtnHistogramm);
		contentPane.add(rdbtnHistogramm);
		
		JRadioButton rdbtnHistogrammVer = new JRadioButton("Histogr.Verschiebung");
		rdbtnHistogrammVer.setBounds(15, 300, 160, 20);
		bg.add(rdbtnHistogrammVer);
		contentPane.add(rdbtnHistogrammVer);
		
		JSlider slider = new JSlider();
		slider.setBounds(185, 300, 140, 20);
		slider.setMinimum(-255);
		slider.setMaximum(255);
		slider.setValue(0);
		contentPane.add(slider);
		
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
					_bild.einlesen();
					ImageIcon iI = new ImageIcon(_bild.getOriBild());
					int [] neueBreite_Hoehe = _bild.berechneBildMassstab();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite_Hoehe[0],neueBreite_Hoehe[1],Image.SCALE_DEFAULT));
					lblOrginaldatei.setIcon(iI);
					lblBildtextOri.setText("Originalbild");

				}
			}
		});

		btAuswaehlen.setBounds(570, 15, 90, 20);
		contentPane.add(btAuswaehlen);
		
		Button btAusfuehren = new Button("Ausführen");
		btAusfuehren.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (_bild == null) return;
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				if (rdbtnGrauwerte.isSelected()) {
					_bild.berechneGrauwerte();
					DecimalFormat format_ohne = new DecimalFormat("##0");
					DecimalFormat format_3 = new DecimalFormat("####0.###");
					lblGMin.setText("G min:     "+format_ohne.format(_bild.getGmin()));
					lblGMax.setText("G max:    "+format_ohne.format(_bild.getGmax()));
					lblGMittel.setText("G mittel: "+format_3.format(_bild.getGmittel()));
					lblGVarianz.setText("Varianz:  "+format_3.format(_bild.getVarianz()));
					lblEntropie.setText("Entropie: "+format_3.format(_bild.getEntropie()));		
					ImageIcon iI = new ImageIcon(_bild.getGraubild());
					int [] neueBreite_Hoehe = _bild.berechneBildMassstab();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite_Hoehe[0],neueBreite_Hoehe[1],Image.SCALE_DEFAULT));
					lblBerechnetesbild.setIcon(iI); 
					lblBildtextBer.setText("berechnetes Graubild");
				}else if (rdbtnAequidensiten.isSelected()) {
					BufferedImage Aequidenbild = _bild.erzeugeAequidensiten();
					_bild.setTempBild(Aequidenbild);
					ImageIcon iI = new ImageIcon(Aequidenbild);
					int [] neueBreite_Hoehe = _bild.berechneBildMassstab();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite_Hoehe[0],neueBreite_Hoehe[1],Image.SCALE_DEFAULT));
					lblBerechnetesbild.setIcon(iI);
					lblBildtextBer.setText("Äquidensiten-Bild");
					lblGMin.setText("");
					lblGMax.setText("");
					lblGMittel.setText("");
					lblGVarianz.setText("");
					lblEntropie.setText("");	
				}else if (rdbtnBinarisierung.isSelected()) {
					String eingabe = _tfschwellwert.getText();
					int schwellWert = 127;
				    try
				    {
				      schwellWert = Integer.parseInt(eingabe);
				    }
				    catch(NumberFormatException ex)
				    {
				    	_tfschwellwert.setText("0-255");
				    }
					BufferedImage BinaerBild = _bild.erzeugeBinaerBild(schwellWert);
					_bild.setTempBild(BinaerBild);
					ImageIcon iI = new ImageIcon(BinaerBild);
					int [] neueBreite_Hoehe = _bild.berechneBildMassstab();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite_Hoehe[0],neueBreite_Hoehe[1],Image.SCALE_DEFAULT));
					lblBerechnetesbild.setIcon(iI);
					lblBildtextBer.setText("Binär-Bild");
					lblGMin.setText("");
					lblGMax.setText("");
					lblGMittel.setText("");
					lblGVarianz.setText("");
					lblEntropie.setText("");
				} else if (rdbtnHistogramm.isSelected()) {
					if (_bild.getTempBild() == null) {
						contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						return;
					}
			        String titel = "Histogramm";
			        int x = 100;
			        int y = 100;
			        int w = 567;
			        int h = 280;
			        DiagrammFenster f = new DiagrammFenster(titel, x, y, w, h);
			        int [] werte = _bild.berechneHistoramm(_bild.getTempBild());
			        f.dgramm.einlesenDaten(werte);
			        f.dgramm.ermittleMaxwert(werte);
			        f.setVisible(true);

				} else if (rdbtnHistogrammVer.isSelected()) {
					if (_bild.getTempBild() == null) {
						contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						return;
					}		
					BufferedImage HistoVerschBild = _bild.erzeugeHistgramVerschiebung(_bild.getTempBild(), slider.getValue());
					_bild.setTempBild(HistoVerschBild);
					ImageIcon iI = new ImageIcon(HistoVerschBild);
					int [] neueBreite_Hoehe = _bild.berechneBildMassstab();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite_Hoehe[0],neueBreite_Hoehe[1],Image.SCALE_DEFAULT));
					lblBerechnetesbild.setIcon(iI);
					lblBildtextBer.setText("Histor.VerschiebungsBild");
					lblGMin.setText("");
					lblGMax.setText("");
					lblGMittel.setText("");
					lblGVarianz.setText("");
					lblEntropie.setText("");
					
				}
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});

		btAusfuehren.setBounds(15, 325, 140, 20);
		contentPane.add(btAusfuehren);
		

		Button btBeenden = new Button("Beenden");
		btBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btBeenden.setBounds(520, 325, 140, 20);
		contentPane.add(btBeenden);
		


	}
}
