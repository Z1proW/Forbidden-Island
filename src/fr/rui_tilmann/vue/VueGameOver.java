package fr.rui_tilmann.vue;

import fr.rui_tilmann.modele.enums.GameOver;
import fr.rui_tilmann.modele.enums.Son;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VueGameOver extends JPanel
{

	private final GameOver state;
	private final Image eau = new ImageIcon("src/fr/rui_tilmann/images/game_over/eau.png").getImage();
	private int y = 620;

	public VueGameOver(GameOver state)
	{
		this.state = state;
		setVisible(true);

		Son.EAU.jouerSon();

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				y -= 3;
				if(y <= 0) cancel();
			}
		}, 0, 1);
	}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		if(state != GameOver.GAGNE)
		{
			g.drawImage(eau, 0, y, null);
		}
	}

}
