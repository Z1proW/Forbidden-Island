package fr.rui_tilmann.vue.menu;

import fr.rui_tilmann.controleur.Bouton;
import fr.rui_tilmann.controleur.Slider;
import fr.rui_tilmann.modele.enums.Difficulte;
import fr.rui_tilmann.vue.jeu.Vue;

import javax.swing.*;
import java.awt.*;

public class VueMenu extends JFrame
{

	private Difficulte difficulte = Difficulte.NOVICE;
	private int nbJoueurs = 4;

	private final String sDif = "Difficulté: ";
	private final String sJrs = "Joueurs: ";

	public VueMenu()
	{
		setTitle("L'île Interdite");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MenuPanel mainMenu = new MenuPanel();
		setContentPane(mainMenu);

		int x = 500;
		int dx = 200;
		int dy = 50;

		Bouton boutonJouer = new Bouton("Jouer", x, 100, dx, dy);
		Slider sliderDifficulte = new Slider(0, Difficulte.values().length - 1, difficulte.ordinal(), x, 168, dx, dy);
		Bouton boutonDifficulte = new Bouton(sDif + difficulte.toString(), x, 200, dx, dy);
		Slider sliderJoueurs = new Slider(2, 4, nbJoueurs, x, 268, dx, dy);
		Bouton boutonJoueurs = new Bouton(sJrs + nbJoueurs, x, 300, dx, dy);
		Bouton boutonQuitter = new Bouton("Quitter", x, 400, dx, dy);

		boutonJouer.addActionListener(e -> {
			dispose();
			new Vue(difficulte, nbJoueurs);
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
			nbJoueurs = nbJoueurs % 4 + 1;
			boutonJoueurs.setText(sJrs + nbJoueurs);
			sliderJoueurs.setValue(nbJoueurs);
		});

		sliderJoueurs.addChangeListener(e -> {
			nbJoueurs = sliderJoueurs.getValue();
			boutonJoueurs.setText(sJrs + nbJoueurs);
		});

		boutonQuitter.addActionListener(e -> System.exit(0));

		add(boutonJouer);
		add(boutonDifficulte);
		add(sliderDifficulte);
		add(boutonJoueurs);
		add(sliderJoueurs);
		add(boutonQuitter);

		setLayout(new BorderLayout());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
