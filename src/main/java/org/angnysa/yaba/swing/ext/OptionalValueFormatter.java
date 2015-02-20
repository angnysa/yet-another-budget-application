package org.angnysa.yaba.swing.ext;

/*
 * #%L
 * Yet Another Budget Application
 * %%
 * Copyright (C) 2012 angnysa
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.text.Format;
import java.text.ParseException;

import javax.swing.text.InternationalFormatter;

public class OptionalValueFormatter extends InternationalFormatter {
	private static final long serialVersionUID = 1L;
	
	private boolean allowBlankField = true;
	private String blankRepresentation;
	
	public OptionalValueFormatter() {
		super();
	}
	
	public OptionalValueFormatter(Format format) {
		super(format);
		updateBlankRepresentation();
	}
	
	@Override
	public void setFormat(Format format) {
		super.setFormat(format);
		updateBlankRepresentation();
	}

	public void setAllowBlankField(boolean allowBlankField) {
		this.allowBlankField = allowBlankField;
	}
	
	public boolean isAllowBlankField() {
		return allowBlankField;
	}
	
	/**
	 * Override the stringToValue method to check the blank representation.
	 */
	@Override public Object stringToValue(String value) throws ParseException {
		Object result;
		if(isAllowBlankField() && (value == null || value.equals(blankRepresentation))) {
			// an empty field should have a 'null' value.
			result = null;
		}
		else {
			result = super.stringToValue(value);
		}
		return result;
	}
	
	private void updateBlankRepresentation() {
		try {
			// calling valueToString on the parent class with a null attribute will get the 'blank' 
			// representation.
			blankRepresentation = valueToString(null);
		}
		catch(ParseException e) {
			blankRepresentation = null;
		}
	}
}
