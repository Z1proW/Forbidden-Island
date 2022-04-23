package fr.rui_tilmann.vue;

import java.util.ArrayList;

public abstract class Observable
{

	private final ArrayList<Observer> observers;

	public Observable() {this.observers = new ArrayList<>();}

	public void addObserver(Observer o) {observers.add(o);}

	public void notifyObservers() {observers.forEach(Observer::update);}

}
