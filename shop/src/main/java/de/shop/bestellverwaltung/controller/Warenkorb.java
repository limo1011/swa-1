package de.shop.bestellverwaltung.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import de.shop.bestellverwaltung.domain.Bestellposten;
import de.shop.produktverwaltung.domain.Produktdaten;
import de.shop.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("wk")
@Log
@SessionScoped
public class Warenkorb implements Serializable {

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// ATTRIBUTES

	private static final long serialVersionUID = 2822410085540035488L;

	private List<Bestellposten> positionen = new ArrayList<>();

	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS

	public void add(Produktdaten produktdaten) {
		
		System.out.println("Add: " + produktdaten);

		// Parametertest
		if (produktdaten == null) {
			return;
		}
		

		// Neuen Bestellposten mit Produktdaten und Anzahl=1 anlegen
		final Bestellposten neuerBestellposten = new Bestellposten();
		neuerBestellposten.setAnzahl(Integer.valueOf(1));
		neuerBestellposten.setProduktdaten(produktdaten);
		boolean x = false;
		for (Bestellposten posten : positionen) {
			if (posten.getProduktdaten() == neuerBestellposten.getProduktdaten()) {
				posten.setAnzahl(posten.getAnzahl() + 1);
				x = true;
				break;
			}
		}
		if (x == false)
			positionen.add(neuerBestellposten);
	}
	
	public void spinner(ValueChangeEvent e) { //Bestellposten neuerBestellposten) {
//		for (Bestellposten posten : positionen) {
//			if (posten.getProduktdaten() == neuerBestellposten.getProduktdaten()) {
//				posten.setAnzahl(neuerBestellposten.getAnzahl());
//			}
//		}
		
		
	}
	

	public boolean isEmpty() {
		return positionen.isEmpty();
	}

	public void reset() {
		positionen.clear();
	}

	public void delete(Bestellposten bestellposten) {
		System.out.println(positionen.size());
		positionen.remove(bestellposten);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// GETTER & SETTER

	public List<Bestellposten> getPositionen() {
		return positionen;
	}
	public double getGesamtpreis() {
		double x = 0;
		for (Bestellposten posten : positionen) {
			x += posten.getAnzahl() * posten.getProduktdaten().getPreis();
		}
		return x;
	}
	public int getSize() {
		return positionen.size();
	}
}
