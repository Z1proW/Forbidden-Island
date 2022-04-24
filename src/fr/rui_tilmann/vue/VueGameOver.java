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
				if(y <= -100) cancel();
			}
		}, 0, 1);
	}

	public void paintComponent(Graphics g)
	{
		super.repaint();

		if(state != GameOver.GAGNE)
		{
			g.drawImage(eau, 0, y, null);

			// TODO faire mieux
			String s = "";
			switch(state)
			{
				case GAGNE: s = "Gagné"; break;
				case HELIPORT_SUBMERGE: s = "L'héliport a été submergé"; break;
				case NOYADE: s = "Un joueur s'est noyé"; break;
				case NIVEAU_EAU_TROP_HAUT: s = "Le niveau d'eau est trop haut"; break;
				case TRESOR_IRRECUPERABLE: s = "Un trésor est irrécupérable"; break;
			}
			g.setFont(new Font("", Font.BOLD, 30));

			g.setColor(Color.BLACK);
			g.drawString(s, getWidth()/2 - 180 + 5, getHeight()/2 + 5);

			g.setColor(Color.WHITE);
			g.drawString(s, getWidth()/2 - 180, getHeight()/2);
		}

		// TODO quand on gagne
	}

}
