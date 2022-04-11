package fr.rui_tilmann.Vue;

import fr.rui_tilmann.Modele.Modele;

import javax.swing.*;
import java.awt.*;

public class TitleBar extends JPanel
{

	private boolean hovered = false;

	public static int width = Modele.LENGTH * VuePlateau.P + 2*VuePlateau.P;
	public static int height = 30;

	public static int left = width - height;
	public static int right = width;
	public static int top = 0;
	public static int bottom = height;

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
		if(hovered) g.setColor(Color.RED);
		else g.setColor(Color.BLACK);

		g.fillPolygon(new int[] {left+5, right-7, right-5, left+7}, new int[] {top+7, bottom-5, bottom-7, top+5}, 4);
		g.fillPolygon(new int[] {right-5, left+7, left+5, right-7}, new int[] {top+7, bottom-5, bottom-7, top+5}, 4);
	}

	public void setHovered(boolean hovered)
	{
		this.hovered = hovered;
		repaint();
	}

}
