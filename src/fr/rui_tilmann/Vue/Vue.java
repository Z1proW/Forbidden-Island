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
		f.setBackground(new Color(0, 0, 0, 100));

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
		f.setVisible(false); // TODO

		JFrame M = new JFrame("ILE INTERDITE");
		MainMenu mainMenu = new MainMenu(modele);;
		JButton play = new JButton("Play");
		JButton difficulte = new JButton("Difficulte:NOVICE");
		JButton nbJoueur = new JButton("Nombre de joueur:4");
		JButton Exit = new JButton("Exit");
		M.setContentPane(mainMenu);
		play.setSize(200,50);
		difficulte.setSize(200,50);
		nbJoueur.setSize(200,50);
		Exit.setSize(200,50);
		play.setLocation(500,100);
		difficulte.setLocation(500,200);
		nbJoueur.setLocation(500,300);
		Exit.setLocation(500,400);

		play.addActionListener(e -> {
				M.setVisible(false);
				f.setVisible(true);

			});
		difficulte.addActionListener(e -> {
			switch (((JButton) e.getSource()).getText()){
				case "Difficulte:NOVICE":
					modele.setDifficulte(Difficulte.NORMAL);
					difficulte.setText("Difficulte:NORMAL");
					break;
				case "Difficulte:NORMAL":
					modele.setDifficulte(Difficulte.ELITE);
					difficulte.setText("Difficulte:ELITE");
					break;
				case "Difficulte:ELITE":
					modele.setDifficulte(Difficulte.LEGENDAIRE);
					difficulte.setText("Difficulte:LEGENDAIRE");
					break;
				case "Difficulte:LEGENDAIRE":
					modele.setDifficulte(Difficulte.NOVICE);
					difficulte.setText("Difficulte:NOVICE");
					break;
			}
		});
		nbJoueur.addActionListener(e -> {
			switch (((JButton) e.getSource()).getText()){
				case "Nombre de joueur:2":
					nbJoueur.setText("Nombre de joueur:3");break;
				case "Nombre de joueur:3":
					nbJoueur.setText("Nombre de joueur:4");break;
				case "Nombre de joueur:4":
					nbJoueur.setText("Nombre de joueur:2");break;

			}
		});
		Exit.addActionListener(e -> System.exit(0));

		M.add(play);
		M.add(difficulte);
		M.add(nbJoueur);
		M.add(Exit);
		M.setLayout(new BorderLayout(3,1));
		M.setVisible(true);
		M.pack();
		M.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		M.setSize(1200,580);

	}

}
