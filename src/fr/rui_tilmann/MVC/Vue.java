package fr.rui_tilmann.MVC;

import javax.swing.*;
import java.awt.*;

public class Vue
{
	private JFrame frame;
	private VuePlateau grille;
	private VueCommandes commandes;

	public CVue(CModele modele)
	{
		frame = new JFrame();
		frame.setTitle("Jeu de l'île Interdite");
		frame.setLayout(new FlowLayout());
		grille = new VuePlateau(modele);
		frame.add(grille);
		commandes = new VueCommandes(modele);
		frame.add(commandes);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

public class VuePlateau extends JPanel implements Observer
{
}

public class VueJoueur
{
}

public class VueCommandes extends JPanel
{
	private Modele modele;

	public VueCommandes(Modele modele) {
		this.modele = modele;
		JButton boutonAvance = new JButton(">");
		this.add(boutonAvance);
		boutonAvance.addActionListener(e -> { modele.avance(); });
}

public class VueBouton
{
}
