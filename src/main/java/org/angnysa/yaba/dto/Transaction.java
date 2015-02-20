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

import org.joda.time.LocalDate;

/**
 * Represents a real money transfert
 * 
 * @author angnysa
 *
 */
public class Transaction {
	/**
	 * ID of the original transaction.
	 */
	private int transactionId;
	
	/**
	 * Date on which this transaction occured
	 */
	private LocalDate date;
	
	/**
	 * The real amount moved by this transaction.
	 */
	private double amount;

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "T{t:"+getTransactionId()+"; d:"+getDate()+"; a:"+getAmount()+"}";
	}
}
