package fr.rui_tilmann.Controleur;

import fr.rui_tilmann.Vue.TitleBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControleurFenetre extends MouseAdapter
{

	private final JFrame f;
	private final TitleBar titleBar;
	private int x, y;
	private boolean titleBarClicked = false;

	public ControleurFenetre(JFrame f, TitleBar titleBar)
	{
		this.f = f;
		this.titleBar = titleBar;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(cross(e)) titleBar.setHovered(true);
		else titleBar.setHovered(false);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		titleBar.setHovered(false);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(titleBar(e))
		{
			x = e.getX();
			y = e.getY();
			titleBarClicked = true;
		}
		else titleBarClicked = false;
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(cross(e)) f.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(!titleBarClicked) return;
		f.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
	}

	private boolean cross(MouseEvent e)
	{
		return TitleBar.left < e.getX() && e.getX() < TitleBar.right
			&& TitleBar.top  < e.getY() && e.getY() < TitleBar.bottom;
	}

	private boolean titleBar(MouseEvent e)
	{
		return 0 < e.getX() && e.getX() < TitleBar.width
			&& 0 < e.getY() && e.getY() < TitleBar.height
			&& !cross(e);
	}

}