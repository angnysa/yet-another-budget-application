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

import javax.swing.table.AbstractTableModel;

import org.joda.time.LocalDate;
import org.joda.time.ReadablePeriod;
import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;

public class TransactionTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;

		public static final int COL_NAME   = 0;
		public static final int COL_DATE   = 1;
		public static final int COL_PERIOD = 2;
		public static final int COL_END    = 3;
		public static final int COL_AMOUNT = 4;
		
		private static final String[] COLUMNS_NAME = new String[] {Messages.getString("transaction.col.label"), Messages.getString("transaction.col.date"), Messages.getString("transaction.col.period"), Messages.getString("transaction.col.end"), Messages.getString("transaction.col.amount")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		private static final Class<?>[] COLUMNS_CLASS = new Class<?>[] {String.class, LocalDate.class, ReadablePeriod.class, LocalDate.class, Double.class};

		private TransactionService transactionService;

		public TransactionTableModel(TransactionService transactionService) {
			this.transactionService = transactionService;
		}

		@Override
		public int getColumnCount() {
			return COLUMNS_NAME.length;
		}

		@Override
		public int getRowCount() {
			// add row is implemented as an empty row
			return transactionService.count()+1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			TransactionDefinition td = getTransactionForRow(rowIndex);
			
			if (td != null) {
    			switch(columnIndex) {
    			case COL_NAME  : return td.getLabel ();
    			case COL_DATE  : return td.getDate  ();
    			case COL_PERIOD: return td.getPeriod();
    			case COL_END   : return td.getEnd   ();
    			case COL_AMOUNT: return td.getAmount();
    			
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
			return true;
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			TransactionDefinition td = getTransactionForRow(rowIndex);
			boolean isnew;
			
			if (td == null) {
				td = new TransactionDefinition();
				td.setDate(new LocalDate());
				isnew = true;
			} else {
				isnew = false;
			}
			
			try {
    			switch(columnIndex) {
    			case COL_NAME  : td.setLabel ((String)         aValue); break;
    			case COL_DATE  : td.setDate  ((LocalDate)      aValue); break;
    			case COL_PERIOD: td.setPeriod((ReadablePeriod) aValue); break;
    			case COL_END   : td.setEnd   ((LocalDate)      aValue); break;
    			case COL_AMOUNT: td.setAmount(((Number) aValue).doubleValue()); break;
    			
    			default: throw new IndexOutOfBoundsException("columnIndex"); //$NON-NLS-1$
    			}
			} catch (IllegalArgumentException e) {
				throw e;
			}
			
			transactionService.save(td);
			
			if (isnew) {
				fireTableRowsInserted(rowIndex, rowIndex);
			} else {
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		}
		
		public void deleteRow(int rowIndex) {
			TransactionDefinition td = getTransactionForRow(rowIndex);
			if (td != null) {
				transactionService.delete(td.getId());
				fireTableRowsDeleted(rowIndex, rowIndex);
			}
		}
		
		public TransactionDefinition getTransactionForRow(int rowIndex) {
			if (rowIndex >= 0 && rowIndex < transactionService.count()) {
				return transactionService.getAll().get(rowIndex);
			} else {
				return null;
			}
		}
	}
