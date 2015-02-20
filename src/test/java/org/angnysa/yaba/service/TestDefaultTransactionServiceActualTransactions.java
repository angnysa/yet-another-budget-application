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

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.junit.Test;
import org.angnysa.yaba.dao.MockTransactionDao;
import org.angnysa.yaba.dto.Transaction;
import org.angnysa.yaba.dto.TestTransactions;
import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;
import org.angnysa.yaba.service.impl.DefaultTransactionService;


public class TestDefaultTransactionServiceActualTransactions {

	protected TransactionService service = new DefaultTransactionService(new MockTransactionDao());
	

	private void testSingleActualTransactions(LocalDate date, LocalDate from, LocalDate to, LocalDate expected) {
		TransactionDefinition td = TestTransactions.newTransaction(1, "test", date, null, null, 100);
		
		if (expected == null) {
			testActualTransactions(td, from, to);
		} else {
			testActualTransactions(td, from, to, expected);
		}
	}
	
	
	private void testActualTransactions(LocalDate date, LocalDate end, LocalDate from, LocalDate to, LocalDate... expected) {
		TransactionDefinition td = TestTransactions.newTransaction(1, "test", date, end, Months.TWO, 100);
		testActualTransactions(td, from, to, expected);
	}
	
	
	private void testActualTransactions(TransactionDefinition td, LocalDate from, LocalDate to, LocalDate... expected) {
		List<Transaction> tds = service.computeTransactions(td, from, to);
		
		assertEquals("(length)", expected.length, tds.size());
		int i=0;
		for (Transaction t : tds) {
			assertEquals("(id."+i+")"    , td.getId().intValue()            , t.getTransactionId());
			assertEquals("(date."+i+")"  , expected[i]                     , t.getDate());
			assertEquals("(amount."+i+")", td.getAmount()                   , t.getAmount(), 0.001);
			i++;
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_from_gt_to() {
		TransactionDefinition testTransaction = TestTransactions.newTransaction(1, "test", new LocalDate(2012, 01, 01), new LocalDate(2013, 01, 01), Months.TWO, 100);
		service.computeTransactions(testTransaction, new LocalDate(2013, 01, 01), new LocalDate(2012, 01, 01));
	}

	@Test
	public void test_from_eq_end_to_eq_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2013, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_eq_end_to_gt_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2013, 1, 1), new LocalDate(2013, 6, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_eq_start_to_eq_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2012, 1, 1),
				new LocalDate(2012, 1, 1));
	}

	@Test
	public void test_from_eq_start_to_gt_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1));
	}

	@Test
	public void test_from_eq_start_to_eq_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_eq_start_to_gt_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2013, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_eq_start_end_null() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), null,
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1));
	}

	@Test
	public void test_from_gt_end_to_gt_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2013, 6, 1), new LocalDate(2014, 1, 1));
	}

	@Test
	public void test_from_gt_start_to_gt_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 6, 1), new LocalDate(2012, 6, 1));
	}

	@Test
	public void test_from_gt_start_to_eq_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 6, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_gt_start_to_gt_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 6, 1), new LocalDate(2013, 6, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_gt_start_end_null() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), null,
				new LocalDate(2012, 6, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_lt_start_to_eq_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2012, 1, 1),
				new LocalDate(2012, 1, 1));
	}

	@Test
	public void test_from_lt_start_to_lt_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 1, 1), new LocalDate(2011, 6, 1));
	}

	@Test
	public void test_from_lt_start_to_gt_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1));
	}

	@Test
	public void test_from_lt_start_to_eq_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_lt_start_to_gt_end() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2013, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1),
				new LocalDate(2012, 7, 1),
				new LocalDate(2012, 9, 1),
				new LocalDate(2012, 11, 1),
				new LocalDate(2013, 1, 1));
	}

	@Test
	public void test_from_lt_start_end_null() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 3, 1),
				new LocalDate(2012, 5, 1));
	}

	@Test
	public void test_single_from_eq_start_to_eq_start() {
		testActualTransactions(
				new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2012, 1, 1),
				new LocalDate(2012, 1, 1));
	}

	@Test
	public void test_single_from_eq_start_to_gt_start() {
		testSingleActualTransactions(
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 1, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1));
	}

	@Test
	public void test_single_from_gt_start_to_gt_start() {
		testSingleActualTransactions(
				new LocalDate(2012, 1, 1),
				new LocalDate(2012, 6, 1), new LocalDate(2013, 1, 1),
				null);
	}

	@Test
	public void test_single_from_lt_start_to_eq_start() {
		testSingleActualTransactions(
				new LocalDate(2012, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2012, 1, 1),
				new LocalDate(2012, 1, 1));
	}

	@Test
	public void test_single_from_lt_start_to_lt_start() {
		testSingleActualTransactions(
				new LocalDate(2012, 1, 1),
				new LocalDate(2011, 1, 1), new LocalDate(2011, 6, 1),
				null);
	}

	@Test
	public void test_single_from_lt_start_to_gt_start() {
		testSingleActualTransactions(
				new LocalDate(2012, 1, 1),
				new LocalDate(2011, 6, 1), new LocalDate(2012, 6, 1),
				new LocalDate(2012, 1, 1));
	}
}
