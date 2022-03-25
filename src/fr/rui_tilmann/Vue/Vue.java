package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class Vue
{

	public Vue(Modele modele)
	{
		JFrame f = new JFrame();
		f.setTitle("Jeu de l'île Interdite");
		f.setLayout(new FlowLayout());

		JPanel[] vues = new JPanel[] {new VuePlateau(modele), new VueJoueurs(modele)};

		for(JPanel p: vues)
			f.add(p);

		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}
