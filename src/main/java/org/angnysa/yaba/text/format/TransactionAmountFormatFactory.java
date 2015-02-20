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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TransactionAmountFormatFactory {
	
	private TransactionAmountFormatFactory() {
	}
	
	public static NumberFormat getFormat() {
		return customizeFormat(NumberFormat.getCurrencyInstance());
	}
	
	public static NumberFormat getFormat(Locale loc) {
		return customizeFormat(NumberFormat.getCurrencyInstance(loc));
	}

	private static NumberFormat customizeFormat(NumberFormat format) {
		
		DecimalFormat diffFormat = (DecimalFormat) format;
		
		diffFormat.setNegativePrefix("- "+diffFormat.getPositivePrefix());
		diffFormat.setNegativeSuffix(diffFormat.getPositiveSuffix());
		
		diffFormat.setPositivePrefix("+ "+diffFormat.getPositivePrefix());
		diffFormat.setPositiveSuffix(diffFormat.getPositiveSuffix());
		
		return diffFormat;
	}
	
}
