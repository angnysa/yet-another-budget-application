package org.angnysa.yaba.dto;

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

import java.util.SortedMap;
import java.util.TreeMap;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;

/**
 * Defines the details of a transaction: Occurrences, amount, reconciliations.
 * 
 * @author angnysa
 *
 */
public class TransactionDefinition {
	/**
	 * The transaction ID. null if new transaction.
	 */
	private Integer id;
	
	/**
	 * The userland label of the transaction.
	 */
	private String label;

	/**
	 * The date on which the transaction occurs, or the start date for repeating transactions.
	 */
	private LocalDate date;

	/**
	 * The date on which the repeating transaction ends.
	 */
	private LocalDate end;
	
	/**
	 * How much money was added/removed by this transaction.
	 */
	private double amount;

	/**
	 * Defines a repeating pattern for repeating transactions.
	 */
	private ReadablePeriod period;
	
	/**
	 * Holds the real amounts for specific dates, in case they are different from reality.
	 * eg. you budget $10 per day, spend $8 on 2010-01-05 and $15 on 2010-01-06.
	 */
	//TODO allow to change the date of one occurrence
	//TODO allow to add/remove occurrences
	private SortedMap<LocalDate, Double> reconciliation;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public ReadablePeriod getPeriod() {
		return period;
	}

	public void setPeriod(ReadablePeriod period) {
		this.period = period;
	}
	
	public SortedMap<LocalDate, Double> getReconciliation() {
		if (reconciliation == null) {
			reconciliation = new TreeMap<LocalDate, Double>();
		}
		return reconciliation;
	}

	public void setReconciliation(SortedMap<LocalDate, Double> reconciliation) {
		if (reconciliation == null) {
			throw new NullPointerException("reconciliation");
		}
		this.reconciliation = reconciliation;
	}

	public Double getReconciliation(LocalDate date) {
		return getReconciliation().get(date);
	}

	public void addReconciliation(LocalDate date, Double value) {
		if (value == null) {
			getReconciliation().remove(date);
		} else {
			getReconciliation().put(date, value);
		}
	}
	
	@Override
	public String toString() {
		return "TD{i:"+getId()+"; l:"+getLabel()+"; d:"+getDate()+"; p:"+getPeriod()+"; e:"+getEnd()+"; a:"+getAmount()+"}";
	}
}
