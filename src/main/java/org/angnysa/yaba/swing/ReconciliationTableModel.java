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

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.table.AbstractTableModel;
import org.joda.time.LocalDate;
import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;

//TODO find a way of using the transactions, so that the dates are defaulted
public class ReconciliationTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	private static final int COL_DATE   = 0;
	private static final int COL_AMOUNT = 1;
	
	private static final String[] COLUMNS_NAME = new String[] {Messages.getString("reconciliation.col.date"), Messages.getString("reconciliation.col.amount")}; //$NON-NLS-1$ //$NON-NLS-2$
	private static final Class<?>[] COLUMNS_CLASS = new Class<?>[] {LocalDate.class, Double.class};

	private TransactionService transactionService;
	private int trsid = -1;

	private SortedMap<LocalDate, Double> data;

	public ReconciliationTableModel(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public void setCurrentTransactionId(int id) {
		
		save();
		
		// load new transaction
		this.trsid = id;
		if (id > 0) {
			data = transactionService.get(id).getReconciliation();
		} else {
			data = new TreeMap<LocalDate, Double>();
		}
		fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS_NAME.length;
	}

	@Override
	public int getRowCount() {
		// add row is implemented as an empty row
		if (trsid >= 0) {
			return data.size()+1;
		} else {
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		LocalDate date = getDateForRow(rowIndex);
		
		if (date != null) {
    		switch(columnIndex) {
    		case COL_DATE  : return date;
    		case COL_AMOUNT: return data.get(date);
    		
    		default: throw new IndexOutOfBoundsException("columnIndex"); //$NON-NLS-1$
    		}
		} else {
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMNS_CLASS[columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNS_NAME[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// for existing rows, only the amount can be modified.
		// for new row, only the date can be set
		return (rowIndex == getRowCount()-1 && columnIndex == COL_DATE)
				|| (rowIndex < getRowCount()-1 && columnIndex == COL_AMOUNT);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		if (aValue == null) {
			throw new NullPointerException("aValue"); //$NON-NLS-1$
		}
		
		TransactionDefinition td = transactionService.get(trsid);
		
		if (columnIndex == COL_DATE) {
			data.put((LocalDate) aValue, td.getAmount());
			save();
			fireTableRowsInserted(rowIndex, rowIndex);
		} else {
			data.put(getDateForRow(rowIndex), ((Number) aValue).doubleValue());
			save();
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}
	
	public void deleteRow(int rowIndex) {
		
		LocalDate date = getDateForRow(rowIndex);
		if (date != null) {
			data.remove(date);
			save();
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
		
	}
	
	private LocalDate getDateForRow(int rowIndex) {
		Set<LocalDate> keySet = data.keySet();
		int i=0;
		for (LocalDate localDate : keySet) {
			if (i == rowIndex) {
				return localDate;
			}
			i++;
		}
		
		return null;
	}
	
	private void save() {
		if (trsid >= 0) {
    		// save changes
    		TransactionDefinition td = transactionService.get(trsid);
    		td.setReconciliation(data);
    		transactionService.save(td);
		}
	}
}
