package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class VueEau extends JPanel implements Observer
{

	private final Modele modele;
	private final int P = VuePlateau.P;

	public VueEau(Modele modele)
	{
		this.modele = modele;
		modele.addObserver(this);

		this.setPreferredSize(new Dimension(2*P, P * Modele.LENGTH));
		this.setBackground(new Color(0, 0, 0));
	}

	@Override
	public void update() {repaint();}

	public void paintComponent(Graphics g)
	{
		super.repaint();
		paint(g);
	}

	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawString("Niveau des Eaux", 4*P/10, P/4);

		// TODO draw tete de mort

		g.setColor(Color.BLUE);
		g.fillRect(0, (10 - (modele.getniveauEau() + 1)) * P/2 + P, 2*P, P * Modele.LENGTH);

		for(int i = 0; i < 10; i++)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0 ,i * P/2 + P, P, P/16);
		}
	}

}
