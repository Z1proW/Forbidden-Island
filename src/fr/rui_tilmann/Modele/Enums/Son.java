package fr.rui_tilmann.Modele.Enums;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public enum Son
{

	EAU("eau.wav"),
	CARTE("carte.wav");

	private String fileName;

	Son(String fileName)
	{
		this.fileName = fileName;
	}

	public void jouerSon()
	{
		try
		{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("src/fr/rui_tilmann/audio/" + this.fileName)));
			clip.start();
		}
		catch(Exception e) {e.getMessage();}

	}

}
