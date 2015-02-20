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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.angnysa.yaba.dao.TransactionDefinitionDao;
import org.angnysa.yaba.dto.TransactionDefinition;

/**
 * Base implementation for {@link TransactionDefinitionDao}s.
 * 
 * Provides an internal data cache for {@link TransactionDefinition}s, but leaves the
 * datastore access mechanisms to subclasses.
 * 
 * @author angnysa
 *
 */
public abstract class AbstractTransactionDefinitionDao implements TransactionDefinitionDao {

	/**
	 * Internal cache
	 */
	private SortedMap<Integer, TransactionDefinition> data = new TreeMap<Integer, TransactionDefinition>();

	/**
	 * IsModified flag
	 */
	private boolean modified = false;
	/**
	 * Current maximum id;
	 */
	private int maxId = 0;

	public AbstractTransactionDefinitionDao() {
		super();
	}

	public boolean isModified() {
		return modified;
	}

	protected void setModified(boolean modified) {
		this.modified = modified;
	}
	
	protected SortedMap<Integer, TransactionDefinition> getData() {
		return data;
	}

	protected void setData(SortedMap<Integer, TransactionDefinition> data) {
		this.data = data;
	}

	protected int getMaxId() {
		return maxId;
	}

	protected void setMaxId(int maxId) {
		this.maxId = maxId;
	}

	protected int getNextId() {
		return ++maxId;
	}

	public int count() {
		return data.size();
	}

	public List<TransactionDefinition> getAll() {
		List<TransactionDefinition> result = new ArrayList<TransactionDefinition>(data.size());
		
		for (TransactionDefinition transaction : data.values()) {
			result.add(copy(transaction));
		}
		
		return result;
	}

	public TransactionDefinition get(int id) {
		return copy(data.get(id));
	}

	public void save(TransactionDefinition td) {
		if (td.getId() == null || data.get(td.getId()) == null) td.setId(getNextId());
		data.put(td.getId(), copy(td));
		setModified(true);
	}

	public void delete(int id) {
		data.remove(id);
		setModified(true);
	}

	/**
	 * Creates a deep copy of a given transaction.
	 * 
	 * @param td The transaction to duplicate.
	 * 
	 * @return The duplicate transaction.
	 */
	protected TransactionDefinition copy(TransactionDefinition td) {
		if (td != null) {
			TransactionDefinition dup = new TransactionDefinition();
	
			dup.setId(td.getId());
			dup.setLabel(td.getLabel());
			dup.setDate(td.getDate());
			dup.setEnd(td.getEnd());
			dup.setAmount(td.getAmount());
			dup.setPeriod(td.getPeriod());
			
			dup.setReconciliation(new TreeMap<LocalDate, Double>(td.getReconciliation()));
			
			return dup;
		} else {
			return null;
		}
	}

}
