package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Controleur.ControleurCartes;
import fr.rui_tilmann.Controleur.ControleurFenetre;
import fr.rui_tilmann.Controleur.ControleurJoueur;
import fr.rui_tilmann.Modele.Enums.Difficulte;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Vue
{

	private Difficulte difficulte = Difficulte.NOVICE;
	private int nbJoueurs = 4;

	String sDif = "Difficulté: ";
	String sJrs = "Joueurs: ";

	public Vue()
	{
		afficherMenu();
	}

	private void afficherMenu()
	{
		JFrame f = new JFrame("L'île Interdite");
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainMenu mainMenu = new MainMenu();
		f.setContentPane(mainMenu);

		Dimension buttonDim = new Dimension(200, 50);

		JButton boutonJouer = new JButton("Jouer");
		boutonJouer.setSize(buttonDim);
		boutonJouer.setLocation(500, 100);

		JButton boutonDifficulte = new JButton(sDif + difficulte.toString());
		boutonDifficulte.setSize(buttonDim);
		boutonDifficulte.setLocation(500, 200);

		JSlider sliderDifficulte = new JSlider(0, Difficulte.values().length - 1, difficulte.ordinal());
		sliderDifficulte.setSize(buttonDim);
		sliderDifficulte.setLocation(500, 168);
		sliderDifficulte.setBackground(new Color(0, 0, 0, 0));

		JButton boutonJoueurs = new JButton("Joueurs: " + nbJoueurs);
		boutonJoueurs.setSize(buttonDim);
		boutonJoueurs.setLocation(500, 300);

		JSlider sliderJoueurs = new JSlider(2, 4, nbJoueurs);
		sliderJoueurs.setSize(buttonDim);
		sliderJoueurs.setLocation(500, 268);
		sliderJoueurs.setBackground(new Color(0, 0, 0, 0));

		JButton boutonQuitter = new JButton("Quitter");
		boutonQuitter.setSize(buttonDim);
		boutonQuitter.setLocation(500, 400);

		boutonJouer.addActionListener(e -> {
			f.dispose();
			afficherJeu(new Modele(difficulte, nbJoueurs));
		});

		boutonDifficulte.addActionListener(e -> {
			Difficulte[] values = Difficulte.values();
			difficulte = values[(difficulte.ordinal() + 1) % values.length];
			boutonDifficulte.setText(sDif + difficulte.toString());
			sliderDifficulte.setValue(difficulte.ordinal());
		});

		sliderDifficulte.addChangeListener(e -> {
			difficulte = Difficulte.values()[sliderDifficulte.getValue()];
			boutonDifficulte.setText(sDif + difficulte.toString());
		});

		boutonJoueurs.addActionListener(e -> {
			nbJoueurs = nbJoueurs % 3 + 2;
			boutonJoueurs.setText(sJrs + nbJoueurs);
			sliderJoueurs.setValue(nbJoueurs);
		});

		sliderJoueurs.addChangeListener(e -> {
			nbJoueurs = sliderJoueurs.getValue();
			boutonJoueurs.setText(sJrs + nbJoueurs);
		});

		boutonQuitter.addActionListener(e -> System.exit(0));

		f.add(boutonJouer);
		f.add(boutonDifficulte);
		f.add(sliderDifficulte);
		f.add(boutonJoueurs);
		f.add(sliderJoueurs);
		f.add(boutonQuitter);

		f.setLayout(new BorderLayout());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

	private void afficherJeu(Modele modele)
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
		eastPanel.add(vueArtefact);

		f.add(eastPanel, BorderLayout.EAST);

		f.setResizable(false);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setShape(new RoundRectangle2D.Double(0, 0, f.getWidth(), f.getHeight(), 20, 20));
		f.setVisible(true);
	}

}
