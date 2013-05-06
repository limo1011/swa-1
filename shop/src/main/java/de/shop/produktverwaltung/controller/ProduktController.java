package de.shop.produktverwaltung.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import de.shop.produktverwaltung.domain.Produkt;
import de.shop.produktverwaltung.domain.Produktdaten;
import de.shop.produktverwaltung.service.ProduktService;
import de.shop.produktverwaltung.service.ProduktdatenService;
import de.shop.produktverwaltung.service.util.SuchFilter;
import de.shop.util.Log;
import de.shop.util.Transactional;

@Named("pc")
@SessionScoped
@Log
public class ProduktController implements Serializable {

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// ATTRIBUTES

	private static final String FLASH_KEY_FILTERSUCHE = "ergebnisFilterSuche";

	private static final String FLASH_KEY_PRODUKT = "produkt";
	
	private static final String FLASH_KEY_EDIT_PRODUKTDATEN = "editProduktdaten";

	private static final long serialVersionUID = 5513563371749151869L;

	@Inject
	private ProduktdatenService produktdatenService;

	@Inject
	private ProduktService produktService;

	@Inject
	private transient HttpServletRequest request;

	@Inject
	private Flash flash;

	@RequestScoped
	private Produkt produkt;

	@RequestScoped
	private Integer produktId;

	private List<Produkt> produkte;

	private Produkt neuesProdukt;

	private Produktdaten neueProduktdaten;

	@RequestScoped
	private SuchFilter suchFilter;

	private Produktdaten editProduktdaten;
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC METHODS

	@Transactional
	public String sucheProdukt() {

		if (produktId == null || produktId == 0) {
			return "";
		}

		Produkt produkt = produktService.findProduktByID(produktId.intValue(),
				ProduktService.FetchType.KOMPLETT, null);

		if (produkt == null) {
			return "";
		}

		flash.remove(FLASH_KEY_FILTERSUCHE);
		flash.put(FLASH_KEY_PRODUKT, produkt);
		request.setAttribute("produktId", produktId.intValue());
		request.setAttribute("anzahlProduktdaten", produkt.getProduktdaten()
				.size());
		produktId = null;

		return "";
	}

	@Transactional
	public void sucheAlleProdukte() {

		produkte = produktService.findProdukte();
	}

	public void createEmptyProdukt() {

		if (neuesProdukt != null) {
			return;
		}

		neuesProdukt = new Produkt();
		neueProduktdaten = new Produktdaten();

		neuesProdukt.addProduktdaten(neueProduktdaten);

	}

	@Transactional
	public String createProdukt() {

		produktService.addProdukt(neuesProdukt, null);
		neuesProdukt = null;

		return "";

	}

	@Transactional
	public String updateProdukt() {

		Produkt produkt = (Produkt) flash.get(FLASH_KEY_PRODUKT);

		if (produkt == null) {
			return "";
		}

		produktService.updateProdukt(produkt, null);

		return "/index";
	}

	public void createSuchfilter() {
		if (suchFilter != null) {
			return;
		}

		suchFilter = new SuchFilter();
	}

	@Transactional
	public void sucheMitFilter() {

		List<Produktdaten> result = produktdatenService
				.findProduktdatenByFilter(suchFilter, null);

		flash.remove(FLASH_KEY_PRODUKT);
		flash.put(FLASH_KEY_FILTERSUCHE, result);
	}

	public void ladeProduktdaten(Produktdaten pdaten)
	{
		flash.put(FLASH_KEY_EDIT_PRODUKTDATEN, pdaten);
		this.editProduktdaten = pdaten;
	}
	
	// //////////////////////////////////////////////////////////////////////////////////////////////
	// GETTER & SETTER

	public SuchFilter getSuchFilter() {
		return suchFilter;
	}

	public Produkt getProdukt() {
		return produkt;
	}

	public Integer getProduktId() {
		return produktId;
	}

	public void setProduktId(Integer produktId) {
		this.produktId = produktId;
	}

	public List<Produkt> getProdukte() {
		return produkte;
	}

	public Produkt getNeuesProdukt() {
		return neuesProdukt;
	}

	public Produktdaten getNeueProduktdaten() {
		return neueProduktdaten;
	}

	
	public Produktdaten getEditProduktdaten() {
		return editProduktdaten;
	}

	
}
