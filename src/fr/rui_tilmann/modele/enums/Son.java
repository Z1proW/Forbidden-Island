package fr.rui_tilmann.modele.enums;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public enum Son
{

	EAU("eau.wav"),
	CARTE("carte.wav");

	private final String fileName;

	Son(String fileName)
	{
		this.fileName = fileName;
	}

	public void jouerSon()
	{
		Clip clip;
		try
		{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("src/fr/rui_tilmann/audio/" + this.fileName)));
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		clip.start();
	}

}
