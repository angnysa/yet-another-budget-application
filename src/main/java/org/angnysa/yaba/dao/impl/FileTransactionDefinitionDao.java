package org.angnysa.yaba.dao.impl;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dto.TransactionDefinition;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.SingleValueConverter;

/**
 * DAO backed by a XML file on the local filesystem.
 * 
 * @author angnysa
 *
 */
public class FileTransactionDefinitionDao extends AbstractTransactionDefinitionDao {
	
	/**
	 * The file where all transactions are stored.
	 */
	private File source;
	/**
	 * Creates a new DAO with no underlying file.
	 * The source file MUST be set via {@link #setSource(File)} before calling
	 * {@link #commit()} or {@link #rollback()}.
	 */
	public FileTransactionDefinitionDao() {
	}

	/**
	 * Set the source file for the DAO.
	 * It does not change the internal store, only the reference to the file.
	 * To reload the data, call {@link #rollback()}.
	 * To write it, call {@link #commit()}.
	 * 
	 * @param source The new source file
	 */
	public void setSource(File source) {
		this.source = source;
	}
	
	/**
	 * Returns the file where the transactions are stored, or null if not set.
	 * 
	 * @return The file where the transactions are stored.
	 */
	public File getSource() {
		return source;
	}

	@SuppressWarnings("unchecked")
	public void rollback() throws ReadException {
		if (source == null) {
			throw new ReadException("Source is not defined");
		}

		try {
			InputStreamReader r = new InputStreamReader(
				     new FileInputStream(source),
				     Charset.forName("UTF-8").newDecoder() 
				 );
			Collection<TransactionDefinition> values = (Collection<TransactionDefinition>) newXStream().fromXML(source, r);
		
    		setMaxId(0);
    		setData(new TreeMap<Integer, TransactionDefinition>());
    		for (TransactionDefinition td : values) {
    			if (td.getId() > getMaxId()) {
    				setMaxId(td.getId());
    			}
    			getData().put(td.getId(), td);
    		}
    		
    		setModified(false);
    		
		} catch (Throwable t) {
			throw new ReadException(t);
		}
	}

	public void commit() throws WriteException {
		if (source == null) {
			throw new WriteException("Source is not defined");
		}
		
		try {
			OutputStreamWriter w = new OutputStreamWriter(
				     new FileOutputStream(source),
				     Charset.forName("UTF-8").newEncoder() 
				 );
			newXStream().toXML(new ArrayList<TransactionDefinition>(getData().values()), w);
			
    		setModified(false);
    		
		} catch (Throwable t) {
			throw new WriteException(t);
		}
	}

	private XStream newXStream() {
		XStream xs = new XStream();
		
		xs.alias("transaction", TransactionDefinition.class);
		xs.alias("date", LocalDate.class);
		
		xs.useAttributeFor(TransactionDefinition.class , "id");
		xs.useAttributeFor(TransactionDefinition.class, "label");
		xs.useAttributeFor(TransactionDefinition.class, "date");
		xs.useAttributeFor(TransactionDefinition.class, "period");
		xs.useAttributeFor(TransactionDefinition.class, "end");
		xs.useAttributeFor(TransactionDefinition.class, "amount");
		
		xs.registerConverter(new SingleValueConverter() {

			@Override
			public boolean canConvert(@SuppressWarnings("rawtypes") Class c) {
				return c.equals(LocalDate.class);
			}

			@Override
			public String toString(Object obj) {
				return ((LocalDate) obj).toString();
			}

			@Override
			public Object fromString(String str) {
				return LocalDate.parse(str);
			}
		});
		
		xs.registerConverter(new SingleValueConverter() {

			@Override
			public boolean canConvert(@SuppressWarnings("rawtypes") Class c) {
				return c.equals(ReadablePeriod.class);
			}

			@Override
			public String toString(Object obj) {
				return ((ReadablePeriod) obj).toString();
			}

			@Override
			public Object fromString(String str) {
				return Period.parse(str);
			}
		});
		
		return xs;
	}
}
