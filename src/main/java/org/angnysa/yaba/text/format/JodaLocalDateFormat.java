package org.angnysa.yaba.text.format;

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

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class JodaLocalDateFormat extends Format {
	private static final long serialVersionUID = 1L;
	
	protected DateTimeFormatter formatter;
	
	public JodaLocalDateFormat() {
		this(ISODateTimeFormat.date());
	}

	public JodaLocalDateFormat(DateTimeFormatter formatter) {
		super();
		this.formatter = formatter;
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		
		if (obj instanceof LocalDate) {
			toAppendTo.append(formatter.print((LocalDate) obj));
		} else if (obj != null) {
			throw new IllegalArgumentException("obj");
		}
		
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {

		if (pos == null) {
			throw new NullPointerException("pos");
		}
		
		try {
			if (source == null || source.length() == 0) {
				return null;
			} else {
        		LocalDate parsed = formatter.parseLocalDate(source);
        		pos.setIndex(source.length());
        		return parsed;
			}
		} catch (IllegalArgumentException e) {
			pos.setErrorIndex(0);
			return null;
		}
	}
}
