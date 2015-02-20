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

import java.util.TreeMap;

import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dao.impl.AbstractTransactionDefinitionDao;
import org.angnysa.yaba.dto.TransactionDefinition;

public class MockTransactionDao extends AbstractTransactionDefinitionDao {

	@Override
	public void rollback() throws ReadException {
		setData(new TreeMap<Integer, TransactionDefinition>());
		setModified(false);
		
	}

	@Override
	public void commit() throws WriteException {
		setModified(false);
	}
}
