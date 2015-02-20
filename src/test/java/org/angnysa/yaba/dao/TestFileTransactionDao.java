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

import java.io.File;
import java.io.IOException;

import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.junit.Test;
import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dao.impl.AbstractTransactionDefinitionDao;
import org.angnysa.yaba.dao.impl.FileTransactionDefinitionDao;
import org.angnysa.yaba.dto.TestTransactions;
import org.angnysa.yaba.dto.TransactionDefinition;

import static org.junit.Assert.*;

public class TestFileTransactionDao {

	@Test
	public void testCrudOnNewDao() {
		AbstractTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
		
		// create
		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), new LocalDate(2013, 1, 1), Months.TEN, 100);
		dao.save(td);
		assertEquals(new Integer(1), td.getId());
		assertNotEquals(td, dao.get(1));
		assertEquals(1, dao.count());
		
		// read
		td = dao.get(1);
		assertNotNull(td);
		assertEquals(new Integer(1), td.getId());
		assertEquals(new LocalDate(2012, 1, 1), td.getDate());
		assertEquals(new LocalDate(2013, 1, 1), td.getEnd());
		assertEquals(Months.TEN, td.getPeriod());
		assertEquals(100, td.getAmount(), 0.001);
		
		// update
		td.setEnd(new LocalDate(2014, 1, 1));
		dao.save(td);
		assertEquals(1, dao.count());
		assertEquals(new LocalDate(2014, 1, 1), dao.get(1).getEnd());
		
		// delete
		dao.delete(1);
		assertEquals(0, dao.count());
		assertNull(dao.get(1));
	}
	
	@Test(expected=WriteException.class)
	public void testCommitNewNoSource() throws WriteException {
		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), null, null, 100);
		dao.save(td);
		
		dao.commit();
	}
	
	@Test
	public void testCommitNew() throws IOException, WriteException, ReadException {
		File f = null;
		
		try {
    		f = File.createTempFile("testTransaction", null);
		
    		// test write
    		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
    		dao.setSource(f);
    		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), null, null, 100);
    		dao.save(td);
    		dao.commit();
    		assertNotEquals(0, f.length());
    		
    		// test read
    		dao = new FileTransactionDefinitionDao();
    		dao.setSource(f);
    		dao.rollback();
    		assertEquals(1, dao.count());
    		assertNotNull(dao.get(1));
		
		} finally {
			if (f != null) f.delete();
		}
	}
	
	@Test
	public void testCommitAndUpdate() throws IOException, ReadException, WriteException {
		File f = null;
		
		try {
    		f = File.createTempFile("testTransaction", null);
    		
    		// test write
    		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
    		dao.setSource(f);
    		TransactionDefinition td = TestTransactions.newTransaction(null, "test", new LocalDate(2012, 1, 1), null, null, 100);
    		td.addReconciliation(new LocalDate(2012, 5, 1), 20D);
    		dao.save(td);
    		dao.commit();
    		td.setEnd(new LocalDate(2013, 1, 1));
    		dao.save(td);
    		dao.commit();
    		
    		// test read
    		dao = new FileTransactionDefinitionDao();
    		dao.setSource(f);
    		dao.rollback();
    		assertEquals(1, dao.count());
    		assertNotNull(dao.get(1));
    		TransactionDefinition td2 = dao.get(1);
    		assertEquals(new Integer(1)       , td.getId()             );
    		assertEquals(td.getId()            , td2.getId()            );
    		assertEquals(td.getLabel()         , td2.getLabel()         );
    		assertEquals(td.getDate()          , td2.getDate()          );
    		assertEquals(td.getPeriod()        , td2.getPeriod()        );
    		assertEquals(td.getEnd()           , td2.getEnd()           );
    		assertEquals(td.getReconciliation(), td2.getReconciliation());
    		assertEquals(td.getAmount()        , td2.getAmount(), 0.001 );
		
		} finally {
			if (f != null) f.delete();
		}
	}

	
	@Test
	public void testCommitChangedSource() throws WriteException, ReadException, IOException {
		File f1 = null;
		File f2 = null;
		
		try {

    		f1 = File.createTempFile("testTransaction", null);
    		f2 = File.createTempFile("testTransaction", null);
    		
    		// write
    		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
    		dao.setSource(f1);
    		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), null, null, 100);
    		dao.save(td);
    		dao.commit();
    		dao.setSource(f2);
    		dao.commit();
    		
    		assertNotEquals(0, f1.length());
    		assertEquals(f1.length(), f2.length());
    		
		} finally {
			if (f1 != null) f1.delete();
			if (f2 != null) f2.delete();
		}
	}
	
	@Test(expected=ReadException.class)
	public void testRollbackNew() throws ReadException {
		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), null, null, 100);
		dao.save(td);
		dao.rollback();
	}
	
	@Test
	public void testRollbackUpdated() throws IOException, WriteException, ReadException {
		File f = null;
		
		try {
    		f = File.createTempFile("testTransaction", null);
		
    		// create
    		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
    		dao.setSource(f);
    		TransactionDefinition td = TestTransactions.newTransaction(0, "test", new LocalDate(2012, 1, 1), null, null, 100);
    		dao.save(td);
    		dao.commit();
    		td.setEnd(new LocalDate(2013, 1, 1));
    		dao.save(td);
    		dao.commit();
    		
    		// rollback
    		dao.save(TestTransactions.newTransaction(0, "test2", new LocalDate(2012, 1, 1), null, null, 100));
    		assertEquals(2, dao.count());
    		assertNotNull(dao.get(2));
    		
    		dao.rollback();
    		assertEquals(1, dao.count());
    		assertNotNull(dao.get(1));
    		assertNull(dao.get(2));
		
		} finally {
			if (f != null) f.delete();
		}
	}
}
