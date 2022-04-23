package fr.rui_tilmann.vue.menu;

import fr.rui_tilmann.modele.Modele;
import fr.rui_tilmann.modele.enums.Difficulte;
import fr.rui_tilmann.vue.GameFrame;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame
{

	private Difficulte difficulte = Difficulte.NOVICE;
	private int nbJoueurs = 4;

	private final String sDif = "Difficulté: ";
	private final String sJrs = "Joueurs: ";

	public MainMenuFrame()
	{
		setTitle("L'île Interdite");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainMenu mainMenu = new MainMenu();
		setContentPane(mainMenu);

		Bouton boutonJouer = new Bouton("Jouer", 100);
		Slider sliderDifficulte = new Slider(0, Difficulte.values().length - 1, difficulte.ordinal(), 168);
		Bouton boutonDifficulte = new Bouton(sDif + difficulte.toString(), 200);
		Slider sliderJoueurs = new Slider(2, 4, nbJoueurs, 268);
		Bouton boutonJoueurs = new Bouton(sJrs + nbJoueurs, 300);
		Bouton boutonQuitter = new Bouton("Quitter", 400);

		boutonJouer.addActionListener(e -> {
			dispose();
			new GameFrame(difficulte, nbJoueurs);
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
