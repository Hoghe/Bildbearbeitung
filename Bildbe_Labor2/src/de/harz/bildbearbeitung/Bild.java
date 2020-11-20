package de.harz.bildbearbeitung;

import java.awt.Color;
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
	private Double[] _grauwerte = new Double[] {255d, 0d , 0d};
	private Double _varianz = 0d;
	private Double _entropie = 0.0;
	
	public Bild(File datei) {
		this._datei = datei;
	}
	
	public void berechne() {
		//einlesen();
		_grauBild = erzeugeGrauwertbild(_bi);
		_grauwerte = ermittleGrauWerte(_grauBild);
		_varianz = ermittleVarianz(_grauBild, _grauwerte[2]);
		_entropie = ermittleEntopie(_grauBild);

	}
	
	public void einlesen() {
		try {
			_bi = ImageIO.read(_datei);
			//_ausgabebild = new ImageIcon(_bi).getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public ImageIcon getAusgabebildOri() {
//		ImageIcon _ausgabebildFenster = new ImageIcon(_bi);
//		return _ausgabebildFenster;
//	}

	private BufferedImage erzeugeGrauwertbild(BufferedImage bild) {
		BufferedImage grauBild = new BufferedImage(bild.getWidth(), bild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ColorConvertOp op  = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
		op.filter(bild, grauBild);
		return grauBild;
	}
	
	public ImageIcon getAusgabebild(int variante) {
		if (variante == 1) {
			ImageIcon _ausgabebildFenster = new ImageIcon(erzeugeGrauwertbild(_bi));
			return _ausgabebildFenster;
		} else {
			ImageIcon _ausgabebildFenster = new ImageIcon(_bi);
			return _ausgabebildFenster;
		}

	}
	
	
	
	private Double[] ermittleGrauWerte(BufferedImage graubild) {
		Double[] grauwerte = new Double[] {255d, 0d , 0d};
		Double summeGrauwerte = 0d;
		Color rgbWert;
		int grauwert;
		for (int i=0; i<graubild.getWidth(); i++) {
			for (int j=0; j<graubild.getHeight(); j++) {
				rgbWert = new Color(graubild.getRGB(i, j));
				//grauwert = (int)(rgbWert.getRed() * 0.299 + rgbWert.getGreen() * 0.587 + rgbWert.getBlue() * 0.114);
				grauwert = (rgbWert.getRed() + rgbWert.getGreen() + rgbWert.getBlue() ) / 3;
				grauwerte[0] = Math.min(grauwerte[0], grauwert);
				grauwerte[1] = Math.max(grauwerte[1], grauwert);
				summeGrauwerte += grauwert;

				}
		}
		grauwerte[2] = summeGrauwerte/(graubild.getWidth()*graubild.getHeight());
		return grauwerte;
	}
	
	private Double ermittleVarianz(BufferedImage graubild, Double mittelereGrauwert) {
		Double summeGrauwertVarianz = 0d;
		int rgbWert;
		int grauwert;
		for (int i=0; i<graubild.getWidth(); i++) {
			for (int j=0; j<graubild.getHeight(); j++) {
				rgbWert = graubild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				summeGrauwertVarianz += Math.pow((grauwert-mittelereGrauwert),2);
			}
		}
		return summeGrauwertVarianz/((graubild.getWidth()*graubild.getHeight())-1);
	}
	
	private double ermittleEntopie(BufferedImage graubild) {
		double entropie=0;
		int[] zaehler = new int[256];
		int zaehlerPixel = 0;
		int rgbWert;
		int grauwert;
		for (int i=0; i<graubild.getWidth(); i++) {
			for (int j=0; j<graubild.getHeight(); j++) {
				zaehlerPixel++;
				rgbWert = graubild.getRGB(i, j);
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
	
}
