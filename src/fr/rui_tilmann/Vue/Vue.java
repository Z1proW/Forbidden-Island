package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Controleur.ControleurClic;
import fr.rui_tilmann.Controleur.ControleurFenetre;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Vue
{

	public Vue(Modele modele)
	{
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout());
		f.setUndecorated(true);
		f.setBackground(new Color(255, 255, 255, 100));

		VuePlateau vuePlateau = new VuePlateau(modele);
		f.add(vuePlateau, BorderLayout.WEST);
		f.add(new VueEau(modele));

		f.addMouseListener(new ControleurClic(modele, vuePlateau));

		TitleBar titleBar = new TitleBar();
		f.add(titleBar, BorderLayout.NORTH);

		ControleurFenetre c = new ControleurFenetre(f, titleBar);
		f.addMouseListener(c);
		f.addMouseMotionListener(c);

		f.setTitle("Jeu de l'île Interdite");
		f.setResizable(false);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 20, 20));
		f.setVisible(true);
	}

}
