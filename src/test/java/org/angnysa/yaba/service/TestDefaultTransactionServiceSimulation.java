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

import static org.junit.Assert.*;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.joda.time.Weeks;
import org.junit.Test;
import org.angnysa.yaba.dao.MockTransactionDao;
import org.angnysa.yaba.dao.TransactionDefinitionDao;
import org.angnysa.yaba.dto.SimulationDetail;
import org.angnysa.yaba.dto.TestTransactions;
import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;
import org.angnysa.yaba.service.impl.DefaultTransactionService;

public class TestDefaultTransactionServiceSimulation {

	protected TransactionDefinitionDao transactionDao = new MockTransactionDao();
	protected TransactionService service = new DefaultTransactionService(transactionDao);
	
	private void testSummary(LocalDate date, ReadablePeriod trsPeriod, long amount, LocalDate from, LocalDate to, ReadablePeriod period, SortedMap<LocalDate, Double> expected) {
		
		TransactionDefinition td = TestTransactions.newTransaction(1, "test", date, null, trsPeriod, amount);
		
		transactionDao.save(td);
		List<SimulationDetail> actual = service.simulate(0, from, to, period);
		
		SortedMap<LocalDate, Double> actualDates = new TreeMap<LocalDate, Double>();
		
		for (SimulationDetail simulationDetail : actual) {
			actualDates.put(simulationDetail.getDate(), simulationDetail.getRemainingAmount());
		}
		
		assertEquals(expected, actualDates);
	}
	
	@Test
	public void test_start_eq_end() {
		SortedMap<LocalDate, Double> expected = new TreeMap<LocalDate, Double>();
		expected.put(new LocalDate(2012, 3, 1), 100D);
		
		testSummary(new LocalDate(2012, 1, 1), Months.ONE, 100,
				new LocalDate(2012, 3, 1), new LocalDate(2012, 3, 1), Months.ONE, expected);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_start_gt_end() {
		testSummary(new LocalDate(2012, 1, 1), Weeks.ONE, 100,
				new LocalDate(2012, 3, 1), new LocalDate(2012, 1, 1), Months.ONE, null);
	}
	
	@Test
	public void test_async_periods() {
		SortedMap<LocalDate, Double> expected = new TreeMap<LocalDate, Double>();
		expected.put(new LocalDate(2012, 1 , 1), 100D);
		expected.put(new LocalDate(2012, 2 , 1), 100D);
		expected.put(new LocalDate(2012, 3 , 1), 200D);
		expected.put(new LocalDate(2012, 4 , 1), 200D);
		expected.put(new LocalDate(2012, 5 , 1), 300D);
		expected.put(new LocalDate(2012, 6 , 1), 400D);
		
		testSummary(new LocalDate(2012, 1, 1), Weeks.weeks(7), 100,
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1), Months.ONE, expected);
	}
	
	@Test
	public void test_async_periods2() {
		SortedMap<LocalDate, Double> expected = new TreeMap<LocalDate, Double>();
		expected.put(new LocalDate(2012, 1 , 1), 100D);
		expected.put(new LocalDate(2012, 2 , 1), 300D);
		expected.put(new LocalDate(2012, 3 , 1), 500D);
		expected.put(new LocalDate(2012, 4 , 1), 700D);
		expected.put(new LocalDate(2012, 5 , 1), 900D);
		expected.put(new LocalDate(2012, 6 , 1), 1100D);
		
		testSummary(new LocalDate(2012, 1, 1), Weeks.weeks(2), 100,
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1), Months.ONE, expected);
	}
	
	@Test
	public void test_sync_periods() {
		SortedMap<LocalDate, Double> expected = new TreeMap<LocalDate, Double>();
		expected.put(new LocalDate(2012, 1, 1), 100D);
		expected.put(new LocalDate(2012, 2, 1), 200D);
		expected.put(new LocalDate(2012, 3, 1), 300D);
		expected.put(new LocalDate(2012, 4, 1), 400D);
		expected.put(new LocalDate(2012, 5, 1), 500D);
		expected.put(new LocalDate(2012, 6, 1), 600D);
		
		testSummary(new LocalDate(2012, 1, 1), Months.ONE, 100,
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1), Months.ONE, expected);
	}
}
