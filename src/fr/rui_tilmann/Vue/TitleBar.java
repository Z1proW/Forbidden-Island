package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Joueur;
import fr.rui_tilmann.Modele.Modele;
import fr.rui_tilmann.Modele.Plateau;

import javax.swing.*;
import java.awt.*;

public class TitleBar extends JPanel
{

	private boolean hovered = false;

	public static int width = Plateau.LENGTH * VuePlateau.P + 2*VueEau.P + 5*VueCartes.WIDTH;
	public static int height = 30;

	public static int left = width - height;
	public static int right = width;
	public static int top = 0;
	public static int bottom = height;

	private String title = "";
	private Color titleColor = Color.MAGENTA;

	public TitleBar()
	{
		this.setPreferredSize(new Dimension(width, height));
	}

	public void paintComponent(Graphics g)
	{
		super.repaint();
		paint(g);
	}

	public void paint(Graphics g)
	{
		// arrière plan blanc
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);



		// ombre titre
		g.setFont(new Font("Calibri", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(title, 10 + 1, height/2 + 6 + 1);

		// titre
		g.setColor(titleColor);
		g.drawString(title, 10, height/2 + 6);


		// croix
		if(hovered) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);

		g.fillPolygon(new int[] {left + 10 - 1, 	right - 10 - 1, 	right - 10, 		left + 10},
					  new int[] {top + 10, 			bottom - 10, 		bottom - 10 - 1, 	top + 10 - 1},
					  4);

		g.fillPolygon(new int[] {right - 10, 	left + 10, 		left + 10 - 1, 		right - 10 - 1},
					  new int[] {top + 10, 		bottom - 10, 	bottom - 10 - 1, 	top + 10 - 1},
					  4);
	}

	public void setHovered(boolean hovered)
	{
		this.hovered = hovered;
		repaint();
	}

	public void setTitle(String title, Color color)
	{
		this.title = title; this.titleColor = color;
		repaint();
	}

}
