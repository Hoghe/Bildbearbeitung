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

import javax.swing.ImageIcon;
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
		setBounds(100, 100, 680, 395);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblEingabedatei = new JLabel("Eingabedatei:");
		lblEingabedatei.setBounds(15, 15, 80, 20);
		contentPane.add(lblEingabedatei);
		
		_tfDateiname = new JTextField();
		_tfDateiname.setBounds(100, 15, 475, 20);
		contentPane.add(_tfDateiname);
		_tfDateiname.setColumns(10);
		
		JLabel lblOrginaldatei = new JLabel();
		lblOrginaldatei.setBounds(15, 40, 320, 180);
		contentPane.add(lblOrginaldatei);
		
		JLabel lblBildtextOri = new JLabel();
		lblBildtextOri.setBounds(15, 225, 320, 20);
		contentPane.add(lblBildtextOri);
		
		JLabel lblBerechnetesbild = new JLabel();
		lblBerechnetesbild.setBounds(345, 40, 320, 180);
		contentPane.add(lblBerechnetesbild);
		
		JLabel lblBildtextBer = new JLabel();
		lblBildtextBer.setBounds(345, 225, 320
				, 20);
		contentPane.add(lblBildtextBer);
		
		
		JLabel lblGMin = new JLabel("G min:    ");
		lblGMin.setBounds(345, 250, 100, 20);
		contentPane.add(lblGMin);
		
		JLabel lblGMax = new JLabel("G max:    ");
		lblGMax.setBounds(345, 275, 100, 20);
		contentPane.add(lblGMax);
		
		JLabel lblGMittel = new JLabel("G mittel: ");
		lblGMittel.setBounds(345, 300, 100, 20);
		contentPane.add(lblGMittel);
		
		JLabel lblGVarianz = new JLabel("Varianz:  ");
		lblGVarianz.setBounds(510, 250, 120, 20);
		contentPane.add(lblGVarianz);
		
		JLabel lblEntropie = new JLabel("Entropie: ");
		lblEntropie.setBounds(510, 275, 120, 20);
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
					_bild.einlesen();
					ImageIcon iI = _bild.getAusgabebild(0);
					Double masstabsFaktor = Math.max(iI.getIconWidth()/320d, iI.getIconHeight()/180d);
					Double Breite = iI.getIconWidth() / masstabsFaktor;
					Double Hoehe = iI.getIconHeight() / masstabsFaktor;
					int neueBreite = Breite.intValue();
					int neueHoehe = Hoehe.intValue();
					iI.setImage(iI.getImage().getScaledInstance(neueBreite,neueHoehe,Image.SCALE_DEFAULT));
					lblOrginaldatei.setIcon(iI);
					lblBildtextOri.setText("Originalbild");

				}
			}
		});

		btAuswaehlen.setBounds(580, 15, 80, 20);
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
				ImageIcon iI = _bild.getAusgabebild(1);
				Double masstabsFaktor = Math.max(iI.getIconWidth()/320d, iI.getIconHeight()/180d);
				Double Breite = iI.getIconWidth() / masstabsFaktor;
				Double Hoehe = iI.getIconHeight() / masstabsFaktor;
				int neueBreite = Breite.intValue();
				int neueHoehe = Hoehe.intValue();
				iI.setImage(iI.getImage().getScaledInstance(neueBreite,neueHoehe,Image.SCALE_DEFAULT));
				lblBerechnetesbild.setIcon(iI); 
				lblBildtextBer.setText("berechnetes Graubild");
				contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
		});

		btBerechnen.setBounds(15, 325, 140, 20);
		contentPane.add(btBerechnen);
		
		
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
