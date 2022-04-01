package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class Vue
{

	public Vue(Modele modele)
	{
		JFrame f = new JFrame();
		f.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

		f.add(new VuePlateau(modele));
		f.add(new VueEau(modele));

		f.setTitle("Jeu de l'île Interdite");
		f.setResizable(false);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
