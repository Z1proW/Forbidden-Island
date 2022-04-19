package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Controleur.ControleurCartes;
import fr.rui_tilmann.Controleur.ControleurJoueur;
import fr.rui_tilmann.Controleur.ControleurFenetre;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
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

		TitleBar titleBar = new TitleBar();
		f.add(titleBar, BorderLayout.NORTH);
		ControleurFenetre c = new ControleurFenetre(f, titleBar);
		f.addMouseListener(c);
		f.addMouseMotionListener(c);

		VueJoueurs vueJoueurs = new VueJoueurs(modele, titleBar);

		VuePlateau vuePlateau = new VuePlateau(modele, vueJoueurs);
		f.add(vuePlateau, BorderLayout.WEST);

		ControleurJoueur controleurJoueur = new ControleurJoueur(modele, vuePlateau);
		f.addMouseListener(controleurJoueur);
		f.addKeyListener(controleurJoueur);
		f.addMouseMotionListener(controleurJoueur);

		f.add(new VueEau(modele), BorderLayout.CENTER);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBackground(new Color(0, 0, 0, 0));

		VueCartes vueCartes = new VueCartes(modele);
		eastPanel.add(vueCartes, BorderLayout.NORTH);

		ControleurCartes controleurCartes = new ControleurCartes(modele, vueCartes, controleurJoueur);
		eastPanel.addMouseListener(controleurCartes);
		eastPanel.addMouseMotionListener(controleurCartes);

		VueArtefact vueArtefact = new VueArtefact(modele);
		eastPanel.add(vueArtefact, BorderLayout.SOUTH);

		f.add(eastPanel, BorderLayout.EAST);

		f.setTitle("Jeu de l'île Interdite");
		f.setResizable(false);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 20, 20));
		f.setVisible(true);
	}

}
