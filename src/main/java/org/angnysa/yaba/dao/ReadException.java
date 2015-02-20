package org.angnysa.yaba.dao;

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

/**
 * Defines an error while attempting to read the underlying datastore.
 * 
 * @author angnysa
 *
 */
public class ReadException extends Exception {
	private static final long serialVersionUID = 1L;

	public ReadException() {
	}

	public ReadException(String message) {
		super(message);
	}

	public ReadException(Throwable cause) {
		super(cause);
	}

	public ReadException(String message, Throwable cause) {
		super(message, cause);
	}

}
