package org.angnysa.yaba.swing;

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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.angnysa.yaba.dto.SimulationDetail;
import org.angnysa.yaba.service.TransactionService;

public class SimulationDataset extends AbstractDataset implements CategoryDataset{
	private static final long serialVersionUID = 1L;
	
	private static final String SIMULATION_KEY ="simulation";
	
	private TransactionService service;
	
	private double initial;
	private LocalDate start;
	private LocalDate end;
	private ReadablePeriod period;

	private List<SimulationDetail> data;

	public SimulationDataset(TransactionService service) {
		super();
		this.service = service;
	}

	public void updateDataset() {
		data = service.simulate(getInitial(), getStart(), getEnd(), getPeriod());
		
		fireDatasetChanged();
	}

	public double getInitial() {
		return initial;
	}

	public void setInitial(double initial) {
		this.initial = initial;
	}

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public ReadablePeriod getPeriod() {
		return period;
	}

	public void setPeriod(ReadablePeriod period) {
		this.period = period;
	}

	@Override
	public int getColumnIndex(@SuppressWarnings("rawtypes") Comparable key) {
		int min=0;
		int max=data.size()-1;
		
		while (min <= max) {
			int med = (min + max) / 2;
			int cmp = data.get(med).getDate().compareTo((LocalDate) key);
			
			if (cmp < 0) {
				max = med-1;
			} else if (cmp > 0) {
				min = med+1;
			} else {
				return cmp;
			}
		}
		
		return -1;
	}

	@Override
	public Comparable<?> getColumnKey(int column) {
		return data.get(column).getDate();
	}

	@Override
	public List<?> getColumnKeys() {
		List<LocalDate> result = new ArrayList<LocalDate>(data.size());
		
		for (SimulationDetail detail : data) {
			result.add(detail.getDate());
		}
		
		return result;
	}

	@Override
	public int getRowIndex(@SuppressWarnings("rawtypes") Comparable row) {
		return getRowKeys().indexOf(row);
	}

	@Override
	public Comparable<?> getRowKey(int row) {
		return (Comparable<?>) getRowKeys().get(row);
	}

	@Override
	public List<?> getRowKeys() {
		List<Comparable<?>> res = new ArrayList<Comparable<?>>(1);
		res.add(SIMULATION_KEY);
		return res;
	}

	@Override
	public Number getValue(@SuppressWarnings("rawtypes") Comparable rowKey, @SuppressWarnings("rawtypes") Comparable columnKey) {
		return getValue(getRowIndex(rowKey), getColumnIndex(columnKey));
	}

	@Override
	public int getColumnCount() {
		return data.size();
	}

	@Override
	public int getRowCount() {
		return getRowKeys().size();
	}

	@Override
	public Number getValue(int row, int column) {
		return data.get(column).getRemainingAmount();
	}

	public SimulationDetail getDetail(int column) {
		return data.get(column);
	}
}
