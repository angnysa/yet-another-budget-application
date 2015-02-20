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

import org.junit.Test;
import org.angnysa.yaba.dao.MockTransactionDao;
import org.angnysa.yaba.dao.TransactionDefinitionDao;
import org.angnysa.yaba.dto.TestTransactions;
import org.angnysa.yaba.service.TransactionService;
import org.angnysa.yaba.service.impl.DefaultTransactionService;

import static org.junit.Assert.*;

public class TestDefaultTransactionServiceAvgBudget {

	protected TransactionDefinitionDao dao = new MockTransactionDao();
	protected TransactionService service = new DefaultTransactionService(dao);
	
	@Test
	public void testAvgYearlyBudget_SINGLE() {
		assertEquals(Double.NaN, service.computeAvgYearlyBudget(TestTransactions.SINGLE), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_SINGLE() {
		assertEquals(Double.NaN, service.computeAvgMonthlyBudget(TestTransactions.SINGLE), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_SINGLE() {
		assertEquals(Double.NaN, service.computeAvgWeeklyBudget(TestTransactions.SINGLE), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_YEARLY_1() {
		assertEquals(100D, service.computeAvgYearlyBudget(TestTransactions.YEARLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_YEARLY_1() {
		assertEquals(8.33333333333333D, service.computeAvgMonthlyBudget(TestTransactions.YEARLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_YEARLY_1() {
		assertEquals(1.91653497835408D, service.computeAvgWeeklyBudget(TestTransactions.YEARLY_1_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_YEARLY_3() {
		assertEquals(33.3333333333333D, service.computeAvgYearlyBudget(TestTransactions.YEARLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_YEARLY_3() {
		assertEquals(2.77777777777778D, service.computeAvgMonthlyBudget(TestTransactions.YEARLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_YEARLY_3() {
		assertEquals(0.638844992784693D, service.computeAvgWeeklyBudget(TestTransactions.YEARLY_3_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_MONTHLY_1() {
		assertEquals(1200D, service.computeAvgYearlyBudget(TestTransactions.MONTHLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_MONTHLY_1() {
		assertEquals(100D, service.computeAvgMonthlyBudget(TestTransactions.MONTHLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_MONTHLY_1() {
		assertEquals(22.9984188587035D, service.computeAvgWeeklyBudget(TestTransactions.MONTHLY_1_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_MONTHLY_3() {
		assertEquals(400D, service.computeAvgYearlyBudget(TestTransactions.MONTHLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_MONTHLY_3() {
		assertEquals(33.3333333333333D, service.computeAvgMonthlyBudget(TestTransactions.MONTHLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_MONTHLY_3() {
		assertEquals(7.66613961956782D, service.computeAvgWeeklyBudget(TestTransactions.MONTHLY_3_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_WEEKLY_1() {
		assertEquals(5217.7498D, service.computeAvgYearlyBudget(TestTransactions.WEEKLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_WEEKLY_1() {
		assertEquals(434.8125D, service.computeAvgMonthlyBudget(TestTransactions.WEEKLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_WEEKLY_1() {
		assertEquals(100D, service.computeAvgWeeklyBudget(TestTransactions.WEEKLY_1_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_WEEKLY_3() {
		assertEquals(1739.24993333333D, service.computeAvgYearlyBudget(TestTransactions.WEEKLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_WEEKLY_3() {
		assertEquals(144.9375D, service.computeAvgMonthlyBudget(TestTransactions.WEEKLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_WEEKLY_3() {
		assertEquals(33.3333333333333D, service.computeAvgWeeklyBudget(TestTransactions.WEEKLY_3_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_DAYLY_1() {
		assertEquals(36524.25D, service.computeAvgYearlyBudget(TestTransactions.DAYLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_DAYLY_1() {
		assertEquals(3043.6874D, service.computeAvgMonthlyBudget(TestTransactions.DAYLY_1_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_DAYLY_1() {
		assertEquals(700D, service.computeAvgWeeklyBudget(TestTransactions.DAYLY_1_NO_END), 0.001);
	}

	
	@Test
	public void testAvgYearlyBudget_DAYLY_3() {
		assertEquals(12174.75D, service.computeAvgYearlyBudget(TestTransactions.DAYLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgMonthlyBudget_DAYLY_3() {
		assertEquals(1014.56246666667D, service.computeAvgMonthlyBudget(TestTransactions.DAYLY_3_NO_END), 0.001);
	}
	
	@Test
	public void testAvgWeeklyBudget_DAYLY_3() {
		assertEquals(233.333333333333D, service.computeAvgWeeklyBudget(TestTransactions.DAYLY_3_NO_END), 0.001);
	}
}
