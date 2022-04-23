package fr.rui_tilmann.vue;

import fr.rui_tilmann.controleur.ControleurCartes;
import fr.rui_tilmann.controleur.ControleurFenetre;
import fr.rui_tilmann.controleur.ControleurJoueur;
import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.enums.Difficulte;
import fr.rui_tilmann.modele.enums.GameOver;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class GameFrame extends JFrame
{

	public GameFrame(Difficulte difficulte, int nbJoueurs)
	{
		Modele modele = new Modele(difficulte, nbJoueurs, this);

		setLayout(new BorderLayout());
		setUndecorated(true);
		setBackground(new Color(100, 200, 255, 100));

		TitleBar titleBar = new TitleBar();
		add(titleBar, BorderLayout.NORTH);
		ControleurFenetre c = new ControleurFenetre(this, titleBar);
		addMouseListener(c);
		addMouseMotionListener(c);

		VueJoueurs vueJoueurs = new VueJoueurs(modele, titleBar);

		VuePlateau vuePlateau = new VuePlateau(modele, vueJoueurs);
		add(vuePlateau, BorderLayout.WEST);

		ControleurJoueur controleurJoueur = new ControleurJoueur(modele, vuePlateau);
		addMouseListener(controleurJoueur);
		addKeyListener(controleurJoueur);
		addMouseMotionListener(controleurJoueur);

		add(new VueEau(modele), BorderLayout.CENTER);

		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBackground(new Color(0, 0, 0, 0));

		VueCartes vueCartes = new VueCartes(modele);
		eastPanel.add(vueCartes, BorderLayout.NORTH);

		ControleurCartes controleurCartes = new ControleurCartes(modele, vueCartes, controleurJoueur);
		eastPanel.addMouseListener(controleurCartes);
		eastPanel.addMouseMotionListener(controleurCartes);

		VueArtefact vueArtefact = new VueArtefact(modele);
		eastPanel.add(vueArtefact);

		add(eastPanel, BorderLayout.EAST);

		setResizable(false);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
		setVisible(true);
	}

}
