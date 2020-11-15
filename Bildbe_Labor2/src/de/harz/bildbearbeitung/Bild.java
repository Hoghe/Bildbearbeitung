package de.harz.bildbearbeitung;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bild {
	
	private File _datei;
	private BufferedImage _bi;
	private BufferedImage _grauBild;
	private int[] _grauwerte = new int[] {255, 0 , 0};
	private int _varianz = 0;
	private double _entropie = 0;
	
	public Bild(File datei) {
		this._datei = datei;
	}
	
	public void berechne() {
		einlesen();
		_grauBild = erzeugeGrauwertbild(_bi);
		_grauwerte = ermittleGrauWerte(_grauBild);
		_varianz = ermittleVarianz(_grauBild, _grauwerte[2]);
		_entropie = ermittleEntopie(_grauBild);

	}
	
	public void einlesen() {
		try {
			_bi = ImageIO.read(_datei);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedImage erzeugeGrauwertbild(BufferedImage bild) {
		BufferedImage grauBild = new BufferedImage(bild.getWidth(), bild.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ColorConvertOp op  = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),null);
		op.filter(bild, grauBild);
		return grauBild;
	}
	
	private int[] ermittleGrauWerte(BufferedImage graubild) {
		int[] grauwerte = new int[] {255, 0 , 0};
		int summeGrauwerte = 0;
		int zaehlerPixel = 0;
		int rgbWert;
		int grauwert;
		for (int i=0; i<graubild.getWidth(); i++) {
			for (int j=0; j<graubild.getHeight(); j++) {
				zaehlerPixel++;
				rgbWert = graubild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				grauwerte[0] = Math.min(grauwerte[0], grauwert);
				grauwerte[1] = Math.max(grauwerte[1], grauwert);
				summeGrauwerte += grauwert;
				}
		}
		grauwerte[2] = summeGrauwerte/zaehlerPixel;
		return grauwerte;
	}
	
	private int ermittleVarianz(BufferedImage graubild, int mittelereGrauwert) {
		int summeGrauwertVarianz = 0;
		int zaehlerPixel = 0;
		int rgbWert;
		int grauwert;
		for (int i=0; i<graubild.getWidth(); i++) {
			for (int j=0; j<graubild.getHeight(); j++) {
				zaehlerPixel++;
				System.out.println();
				rgbWert = graubild.getRGB(i, j);
				grauwert = rgbWert & 0xFF;
				summeGrauwertVarianz += Math.pow((grauwert-mittelereGrauwert),2);
			}
		}
		return summeGrauwertVarianz/(zaehlerPixel-1);
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
	
	public int getGmin() {
		return _grauwerte[0];
	}
	
	public int getGmax() {
		return _grauwerte[1];
	}
	
	public int getGmittel() {
		return _grauwerte[2];
	}
	
	public int getVarianz() {
		return _varianz;
	}
	
	public double getEntropie() {
		return _entropie;
	}
	
}
