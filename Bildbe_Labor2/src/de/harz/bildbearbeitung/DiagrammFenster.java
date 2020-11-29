package de.harz.bildbearbeitung;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class DiagrammFenster extends JFrame {

    class Diagramm extends JPanel {

        private int _maxWert=0;
        private int[] _grauwerte;


        public void einlesenDaten(int[] grauwerte) {
        	this._grauwerte = grauwerte;
        }
        
        public void ermittleMaxwert(int[] grauwerte) {
        	
        	for (int i=0; i<grauwerte.length; i++) {
        		_maxWert = Math.max(_maxWert, grauwerte[i]);
        	}
        }
        

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            super.paintComponent(g2);
//            // Linien etwas breiter
            g2.setStroke(new BasicStroke(2.0f));
            g2.setPaint(Color.MAGENTA);
//            // x-Achse zeichnen
            g2.draw(new Line2D.Double(5, 220, 537, 220));
//            // y-Achse zeichnen
            g2.draw(new Line2D.Double(15, 230, 15, 15));
            // Säulen zeichnen
            g2.setPaint(Color.darkGray);
            for (int i=0; i<_grauwerte.length;i++) {
            	Double hoehe =  Double.valueOf(_grauwerte[i])/Double.valueOf(_maxWert)*200d;
            	g2.fill(new Rectangle2D.Double(i*2+16,221-hoehe,2,hoehe));
            }
        }
    }
    public Diagramm dgramm;

    public DiagrammFenster(
        String titel, int x, int y, int w, int h) {
        super(titel);
        this.setSize(w, h);
        this.setLocation(x, y);
		setResizable(false);
        this.setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);
        dgramm = new Diagramm();
        this.setContentPane(dgramm);
    }
}
    


