package org.angnysa.yaba.service;

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

import java.util.List;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dto.Transaction;
import org.angnysa.yaba.dto.SimulationDetail;
import org.angnysa.yaba.dto.TransactionDefinition;

/**
 * Service for accessing and analyzing transactions.
 * 
 * @author angnysa
 *
 */
public interface TransactionService {
	/**
	 * Reloads the data from the internal data store, erasing all unsaved
	 * changes.
	 * 
	 * @throws ReadException If an error occurred while reading.
	 */
    public void rollback() throws ReadException;
    
    /**
     * Writes the changes to the internal data store.
     * 
     * @throws WriteException If an error occurred while writing.
     */
    public void commit() throws WriteException;
    
    /**
     * Returns the number of transactions.
     * 
     * @return The number of transactions.
     */
    public int count();
    
    /**
     * Returns all transactions.
     * 
     * @return All transactions.
     */
    public List<TransactionDefinition> getAll();
    
    /**
     * Returns a single transaction specified by its ID.
     * 
     * @param id The transaction ID.
     * 
     * @return The requested transaction, or null if not found.
     */
    public TransactionDefinition get(int id);
    
    /**
     * Adds or modifies a transaction in the internal DAO.
     * 
     * @param td The target transaction.
     */
    public void save(TransactionDefinition td);
    
    /**
     * Deletes a given transaction.
     * 
     * @param id The ID of the transaction to delete.
     */
    public void delete(int id);
    
    /**
     * Computes and returns the average yearly budget for the given transaction.
     * 
     * @param td The transaction
     * 
     * @return The average yearly budget
     */
    public double computeAvgYearlyBudget(TransactionDefinition td);
    
    /**
     * Computes and returns the average monthly budget for the given transaction.
     * 
     * @param t The transaction
     * 
     * @return The average monthly budget
     */
    public double computeAvgMonthlyBudget(TransactionDefinition td);
    
    /**
     * Computes and returns the average weekly budget for the given transaction.
     * 
     * @param td The transaction
     * 
     * @return The average weekly budget
     */
    public double computeAvgWeeklyBudget(TransactionDefinition td);
    
    /**
     * Computes and returns all the occurrences of a given transaction over a given time period.
     * The result is sorted by dates increasing.
     * 
     * @param td The transaction to analyze
     * @param start
     * @param end
     * 
     * @return The list of transactions that take place during the given period
     */
    public List<Transaction> computeTransactions(TransactionDefinition td, LocalDate start, LocalDate end);

    /**
     * Computes and returns the incomes/expenses summary over a given period,
     * for all transactions,
     * computing totals for every {@code period} (e.g. every 2 months, ...)
     * 
     * @param initial The initial amount
     * @param start   When the simulation begins
     * @param end     When the simulation ends
     * @param period  
     * 
     * @return
     */
    public List<SimulationDetail> simulate(double initial, LocalDate start, LocalDate end, ReadablePeriod period);
}
