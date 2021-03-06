package de.shop.util.exceptions;

import javax.ejb.ApplicationException;

import de.shop.util.AbstractShopException;

@ApplicationException(rollback = true)
public class NoMimeTypeException extends AbstractShopException {
	private static final long serialVersionUID = -6174243392956089668L;
	
	public static final String MESSAGE = "Kein gueltiger MIME-Type";

	public NoMimeTypeException() {
		super(MESSAGE);
	}
}
