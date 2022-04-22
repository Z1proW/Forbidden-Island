package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Enums.Artefact;
import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class VueArtefact extends JPanel implements Observer
{

	private final Modele modele;

	public VueArtefact(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(5*VueCartes.WIDTH, 8*VuePlateau.P - 4*VueCartes.HEIGHT));

		// TODO le bouton empêche le ControleurJoueur d'acceder au clavier
		// TODO fix quand on spam le bouton
		/*
		JButton boutonFinTour = new JButton("Fin de tour");
		boutonFinTour.addActionListener(e -> modele.finDeTour());
		add(boutonFinTour);
		*/
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		Artefact[] artefacts = Artefact.values();

		for(int i = 0; i < 4; i++)
		{
			Artefact artefact = artefacts[i];

			if(modele.hasArtefact(artefact))
			{
				Image img = artefact.getImage();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
			else
			{
				Image img = artefact.getContour();
				g.drawImage(img, i*(img.getWidth(null) + 5) + 5, 5, null);
			}
		}
	}

}
