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

import java.util.List;

import org.angnysa.yaba.dto.TransactionDefinition;


/**
 * Data Access interface for reading and writing {@link TransactionDefinition}s
 * 
 * @author angnysa
 *
 */
public interface TransactionDefinitionDao {
	/**
	 * Returns true if the DAO contains changes that have not be written to the
	 * underlying persistent storage
	 * 
	 * @return True if there is unsaved changes.
	 */
	public boolean isModified();
	
	/**
	 * Reload the Transactions from the underlying persistence storage.
	 * 
	 * @throws ReadException A Read error occurred. The cause holds the
	 *                       original exception.
	 */
    public void rollback() throws ReadException;
    
    /**
     * Write the transactions to the underlying persistent storage.
     * 
     * @throws WriteException A Write error occurred. The cause holds the
     *                        original exception.
     */
    public void commit() throws WriteException;
    
    /**
     * Returns the number of transactions managed by the DAO.
     * 
     * @return The count of all transactions.
     */
    public int count();
    
    /**
     * Returns all transactions.
     * 
     * @return All transactions.
     */
    public List<TransactionDefinition> getAll();
    
    /**
     * Returns a specific transaction by its ID.
     * 
     * @param id The transaction ID.
     * @return The transaction or null if not found.
     */
    public TransactionDefinition get(int id);
    
    /**
     * Adds a new transaction, or update an existing transaction.
     * Note that the transaction is explicitly written to the underlying
     * storage only when {@link #commit()} is called.
     * 
     * @param td The transaction to save.
     */
    public void save(TransactionDefinition td);
    
    /**
     * Delete a transaction.
     * Note that the transaction is explicitly written to the underlying
     * storage only when {@link #commit()} is called.
     * 
     * @param id the ID of the transaction to delete.
     */
    public void delete(int id);
}
