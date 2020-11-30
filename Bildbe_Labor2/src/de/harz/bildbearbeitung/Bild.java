package de.harz.bildbearbeitung;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Bild {
	
	private File _datei;
	private BufferedImage _bi;
	private BufferedImage _grauBild;
	private BufferedImage _tmpBild;
	private Double[] _grauwerte = new Double[] {255d, 0d , 0d};
	private Double _varianz = 0d;
	private Double _entropie = 0.0;
	private Double breiteBildAusgabeFenster = 320d;
	private Double hoeheBildAusgabeFenster = 180d;
	
	public Bild(File datei) {
		this._datei = datei;
	}
	
	public void berechneGrauwerte() {
		_grauwerte = ermittleGrauWerte();
		_varianz = ermittleVarianz(_grauwerte[2]);
		_entropie = ermittleEntopie();

	}
	
	public void einlesen() {
		try {
			_bi = ImageIO.read(_datei);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void erzeugeGrauwertbild(BufferedImage bild) {
		_grauBild = new BufferedImage(bild.getWidth(), bild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ColorConvertOp op  = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
		op.filter(bild, _grauBild);
		setTempBild(_grauBild);
	}
	
	
	private Double[] ermittleGrauWerte() {
		if (_grauBild == null) erzeugeGrauwertbild(_bi);
		setTempBild(_grauBild);
		Double[] grauwerte = new Double[] {255d, 0d , 0d};
		Double summeGrauwerte = 0d;
		Color rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				rgbWert = new Color(_grauBild.getRGB(i, j));
				//grauwert = (int)(rgbWert.getRed() * 0.299 + rgbWert.getGreen() * 0.587 + rgbWert.getBlue() * 0.114);
				grauwert = (rgbWert.getRed() + rgbWert.getGreen() + rgbWert.getBlue() ) / 3;
				grauwerte[0] = Math.min(grauwerte[0], grauwert);
				grauwerte[1] = Math.max(grauwerte[1], grauwert);
				summeGrauwerte += grauwert;

				}
		}
		grauwerte[2] = summeGrauwerte/(_grauBild.getWidth()*_grauBild.getHeight());
		return grauwerte;
	}
	
	private Double ermittleVarianz(Double mittelereGrauwert) {
		if (_grauBild == null) erzeugeGrauwertbild(_bi);
		Double summeGrauwertVarianz = 0d;
		int rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				rgbWert = _grauBild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				summeGrauwertVarianz += Math.pow((grauwert-mittelereGrauwert),2);
			}
		}
		return summeGrauwertVarianz/((_grauBild.getWidth()*_grauBild.getHeight())-1);
	}
	
	private double ermittleEntopie() {
		if (_grauBild == null) erzeugeGrauwertbild(_bi);
		double entropie=0;
		int[] zaehler = new int[256];
		int zaehlerPixel = 0;
		int rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				zaehlerPixel++;
				rgbWert = _grauBild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				zaehler[grauwert] +=1;
			}
		}
		for (int k=0; k<zaehler.length; k++) {
			if(zaehler[k] != 0) {
				Double zP = Double.valueOf(zaehlerPixel);
				Double za = Double.valueOf(zaehler[k]);
				Double logZahl = Math.log(zP/za) / Math.log(2) ;
				entropie += (za/zP)*logZahl;
			}
		}
		return entropie;
	}
	
	public BufferedImage erzeugeAequidensiten() {
		if (_grauBild == null) erzeugeGrauwertbild(_bi);
		BufferedImage aequidensitenBild = new BufferedImage(_grauBild.getWidth(), _grauBild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				rgbWert = _grauBild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				if (grauwert<12)
					aequidensitenBild.setRGB(i, j, new Color(0,0,0).getRGB());
				else if (grauwert>11 && grauwert<112)
					aequidensitenBild.setRGB(i, j, new Color(100,100,100).getRGB());
				else if (grauwert>111 && grauwert<178)
					aequidensitenBild.setRGB(i, j, new Color(200,200,200).getRGB());
				else
					aequidensitenBild.setRGB(i, j, new Color(255,255,255).getRGB());
			}
		}
		return aequidensitenBild;
	}
	
	public BufferedImage erzeugeBinaerBild(int schwellWert) {
		if (_grauBild == null) erzeugeGrauwertbild(_bi);
		BufferedImage BinaerBild = new BufferedImage(_grauBild.getWidth(), _grauBild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				rgbWert = _grauBild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				if (grauwert<schwellWert)
					BinaerBild.setRGB(i, j, new Color(0,0,0).getRGB());
				else 
					BinaerBild.setRGB(i, j, new Color(255,255,255).getRGB());
			}
		}
		return BinaerBild;
	}
	
	public int[] berechneHistoramm(BufferedImage bild) {
		//if (_grauBild == null) erzeugeGrauwertbild(_bi);
		
		int[] zaehler = new int[256];
		for (int i=0; i<zaehler.length; i++) {
			zaehler[i] = 0;
		}
		int rgbWert;
		int grauwert;
		for (int i=0; i<bild.getWidth(); i++) {
			for (int j=0; j<bild.getHeight(); j++) {
				rgbWert = bild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				zaehler[grauwert] +=1;
			}
		}
		return zaehler;
	}
	
	public BufferedImage erzeugeHistgramVerschiebung(BufferedImage bild, int verschiebeKonstante) {
		BufferedImage HistgramVerschiebungBild = new BufferedImage(_grauBild.getWidth(), _grauBild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int rgbWert;
		int grauwert;
		for (int i=0; i<_grauBild.getWidth(); i++) {
			for (int j=0; j<_grauBild.getHeight(); j++) {
				rgbWert = _grauBild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				int neuerGrauwert = grauwert+verschiebeKonstante;
				if (neuerGrauwert<0)
					HistgramVerschiebungBild.setRGB(i, j, new Color(0,0,0).getRGB());
				else if (neuerGrauwert>=0 && neuerGrauwert<=255)
					HistgramVerschiebungBild.setRGB(i, j, new Color(neuerGrauwert,neuerGrauwert,neuerGrauwert).getRGB());
				else
					HistgramVerschiebungBild.setRGB(i, j, new Color(255,255,255).getRGB());
			}
		}
		return HistgramVerschiebungBild;
	}
	
	public int[] berechneBildMassstab() {
		int[] neueBreite_Hoehe = new int[2];
		ImageIcon iI = new ImageIcon(_bi);
		Double masstabsFaktor = Math.max(iI.getIconWidth()/breiteBildAusgabeFenster, iI.getIconHeight()/hoeheBildAusgabeFenster);
		Double Breite = iI.getIconWidth() / masstabsFaktor;
		Double Hoehe = iI.getIconHeight() / masstabsFaktor;
		neueBreite_Hoehe[0] = Breite.intValue();
		neueBreite_Hoehe[1] = Hoehe.intValue();
		return neueBreite_Hoehe;
	}
	
	public Double getGmin() {
		return _grauwerte[0];
	}
	
	public Double getGmax() {
		return _grauwerte[1];
	}
	
	public Double getGmittel() {
		return _grauwerte[2];
	}
	
	public Double getVarianz() {
		return _varianz;
	}
	
	public Double getEntropie() {
		return _entropie;
	}
	
	public BufferedImage getGraubild() {
		return _grauBild;
	}
	
	public BufferedImage getOriBild() {
		return _bi;
	}
	
	public void setTempBild(BufferedImage _tmpBild) {
		this._tmpBild = _tmpBild;
	}
	
	public BufferedImage getTempBild() {
		return _tmpBild;
	}
	
}
