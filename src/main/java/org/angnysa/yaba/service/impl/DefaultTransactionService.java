package org.angnysa.yaba.service.impl;

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
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.TransactionDefinitionDao;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dto.Transaction;
import org.angnysa.yaba.dto.SimulationDetail;
import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;

public class DefaultTransactionService implements TransactionService {
    public static final float MONTH_PER_YEAR        = 12;
    public static final float DAY_PER_WEEK          = 7;
    public static final float WORKDAY_PER_WEEK      = 5;
    public static final float AVG_DAY_PER_YEAR      = 365.2425f;
    public static final float AVG_DAY_PER_MONTH     = AVG_DAY_PER_YEAR / MONTH_PER_YEAR;
    public static final float AVG_WEEK_PER_YEAR     = AVG_DAY_PER_YEAR / DAY_PER_WEEK;
    public static final float AVG_WEEK_PER_MONTH    = AVG_DAY_PER_MONTH / DAY_PER_WEEK;
    public static final float AVG_WORKDAY_PER_YEAR  = AVG_WEEK_PER_YEAR * WORKDAY_PER_WEEK;
    public static final float AVG_WORKDAY_PER_MONTH = AVG_WEEK_PER_MONTH * WORKDAY_PER_WEEK;
    
    private TransactionDefinitionDao transactionDao;
    
    public DefaultTransactionService(TransactionDefinitionDao transactionDao) {
        this.transactionDao = transactionDao;
    }
    
    public void rollback() throws ReadException {
        transactionDao.rollback();
    }
    
    public void commit() throws WriteException {
        transactionDao.commit();
    }
    
    public int count() {
        return transactionDao.count();
    }
    
    public List<TransactionDefinition> getAll() {
        return transactionDao.getAll();
    }
    
    public TransactionDefinition get(int id) {
        return transactionDao.get(id);
    }
    
    public void save(TransactionDefinition td) {
    	transactionDao.save(td);
    }
    
    public void delete(int id) {
        transactionDao.delete(id);
    }

	public double computeAvgYearlyBudget(TransactionDefinition td) {
		ReadablePeriod period = td.getPeriod();
		
		if (period instanceof Years) {
			return td.getAmount() / period.getValue(0);
		} else if (period instanceof Months) {
			return td.getAmount() * MONTH_PER_YEAR / period.getValue(0);
		} else if (period instanceof Weeks) {
			return td.getAmount() * AVG_WEEK_PER_YEAR / period.getValue(0);
		} else if (period instanceof Days) {
			return td.getAmount() * AVG_DAY_PER_YEAR / period.getValue(0);
		} else {
			return Double.NaN;
		}
	}

	public double computeAvgMonthlyBudget(TransactionDefinition td) {
		ReadablePeriod period = td.getPeriod();
		
		if (period instanceof Years) {
			return td.getAmount() / MONTH_PER_YEAR / period.getValue(0);
		} else if (period instanceof Months) {
			return td.getAmount() / period.getValue(0);
		} else if (period instanceof Weeks) {
			return td.getAmount() * AVG_WEEK_PER_MONTH / period.getValue(0);
		} else if (period instanceof Days) {
			return td.getAmount() * AVG_DAY_PER_MONTH / period.getValue(0);
		} else {
			return Double.NaN;
		}
	}

	public double computeAvgWeeklyBudget(TransactionDefinition td) {
		ReadablePeriod period = td.getPeriod();
		
		if (period instanceof Years) {
			return td.getAmount() / AVG_WEEK_PER_YEAR / period.getValue(0);
		} else if (period instanceof Months) {
			return td.getAmount() / AVG_WEEK_PER_MONTH / period.getValue(0);
		} else if (period instanceof Weeks) {
			return td.getAmount() / period.getValue(0);
		} else if (period instanceof Days) {
			return td.getAmount() * DAY_PER_WEEK / period.getValue(0);
		} else {
			return Double.NaN;
		}
	}

	public double computeAvgDaylyBudget(TransactionDefinition td) {
		ReadablePeriod period = td.getPeriod();
		
		if (period instanceof Years) {
			return td.getAmount() / AVG_DAY_PER_YEAR / period.getValue(0);
		} else if (period instanceof Months) {
			return td.getAmount() / AVG_DAY_PER_MONTH / period.getValue(0);
		} else if (period instanceof Weeks) {
			return td.getAmount() / DAY_PER_WEEK / period.getValue(0);
		} else if (period instanceof Days) {
			return td.getAmount() / period.getValue(0);
		} else {
			return Double.NaN;
		}
	}
    
	@Override
	public List<Transaction> computeTransactions(TransactionDefinition td, LocalDate start, LocalDate end) {
        
		if (start.isAfter(end)) {
			throw new IllegalArgumentException("start is after end");
		}
		
		List<Transaction> result = new ArrayList<Transaction>();
        
        if (td.getPeriod() == null) {
        	// non repeating
        	if ((td.getDate().isAfter(start) || td.getDate().isEqual(start))
        			&& (td.getDate().isBefore(end) || td.getDate().isEqual(end))) {
            
                Transaction at = new Transaction();
                at.setTransactionId(td.getId());
                at.setDate(td.getDate());
                at.setAmount(td.getAmount());
                result.add(at);
        	}
        } else {
        	// repeating
        	
        	// get first valid date
            LocalDate current = td.getDate();
            while (current.isBefore(start)) {
            	current = current.plus(td.getPeriod());
            }
            
            // get true last limit
            if (td.getEnd() != null && td.getEnd().isBefore(end)) {
            	end = td.getEnd();
            }
            
            
            // list occurrences
            while (current.isBefore(end) || current.isEqual(end)) {
                
                Transaction at = new Transaction();
                at.setTransactionId(td.getId());
                at.setDate(current);
                if (td.getReconciliation(current) == null) {
                	at.setAmount(td.getAmount());
                } else {
                	at.setAmount(td.getReconciliation(current));
                }
                result.add(at);
                
                current = current.plus(td.getPeriod());
            }
        }
        
        Collections.sort(result, new Comparator<Transaction>() {

			@Override
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}
		});
        
        return result;
    }

	@Override
	public List<SimulationDetail> simulate(double initial, LocalDate start, LocalDate end, ReadablePeriod period) {
		
		List<SimulationDetail> result = new ArrayList<SimulationDetail>();
		
		trs_loop: for (TransactionDefinition td : getAll()) {
			// current insertion point (CIP)
			int cip = 0;
			// the amount added/removed by the current actual transaction (AT)
			double delta = 0D;
			
			// init
			if (cip >= result.size()) {
				SimulationDetail detail = new SimulationDetail();
				detail.setDate(start);
				detail.setRemainingAmount(initial);
				result.add(detail);
			}
			
			for (Transaction t : computeTransactions(td, start, end)) {
				
				// if the AT occurs after the CIP,
				// update all CIP until we find the next valid CIP
				while (t.getDate().isAfter(result.get(cip).getDate())) {
					
					result.get(cip).setRemainingAmount(result.get(cip).getRemainingAmount() + delta);
					
					cip++;
					
					// init
					if (cip >= result.size()) {
						
						LocalDate date = result.get(cip-1).getDate().plus(period);
						// if the end date doesn't end a full period, we can have some transactions that cannot be properly used.
						// we stop here if it's the case.
						if (date.isAfter(end)) {
							continue trs_loop;
						}
						
						SimulationDetail detail = new SimulationDetail();
						detail.setDate(date);
						detail.setRemainingAmount(initial);
						result.add(detail);
					}
				}
				
				// store the AT in its CIP
				result.get(cip).getTransactions().add(t);
				
				// update the delta
				delta += t.getAmount();
			}
			
			// update all remaining points
			LocalDate date = result.get(cip).getDate();
			while (! date.isAfter(end)) {
				
				//init
				if (cip >= result.size()) {
					SimulationDetail detail = new SimulationDetail();
					detail.setDate(date);
					detail.setRemainingAmount(initial);
					result.add(detail);
				}
				
				result.get(cip).setRemainingAmount(result.get(cip).getRemainingAmount() + delta);
				
				date = date.plus(period);
				cip++;
			}
		}
		
		return result;
	}
}

