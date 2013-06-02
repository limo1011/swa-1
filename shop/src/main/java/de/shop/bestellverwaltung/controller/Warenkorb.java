package de.shop.bestellverwaltung.controller;

import javax.enterprise.context.SessionScoped;
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

		// Parametertest
		if (produktdaten == null) {
			return;
		}

		// Neuen Bestellposten mit Produktdaten und Anzahl=1 anlegen
		Bestellposten neuerBestellposten = new Bestellposten();
		neuerBestellposten.setAnzahl(Integer.valueOf(1));
		neuerBestellposten.setProduktdaten(produktdaten);
		positionen.add(neuerBestellposten);
	}

	public boolean isEmpty() {
		return positionen.isEmpty();
	}

	public void reset() {
		positionen.clear();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// GETTER & SETTER

	public List<Bestellposten> getPositionen() {
		return positionen;
	}

	public int getSize() {
		return positionen.size();
	}
}