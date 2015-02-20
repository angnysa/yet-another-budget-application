package org.angnysa.yaba.dto;

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

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.angnysa.yaba.dto.TransactionDefinition;

public class TestTransactions {

	public static final TransactionDefinition SINGLE;
	public static final TransactionDefinition YEARLY_1_NO_END;
	public static final TransactionDefinition YEARLY_3_NO_END;
	public static final TransactionDefinition MONTHLY_1_NO_END;
	public static final TransactionDefinition MONTHLY_3_NO_END;
	public static final TransactionDefinition WEEKLY_1_NO_END;
	public static final TransactionDefinition WEEKLY_3_NO_END;
	public static final TransactionDefinition DAYLY_1_NO_END;
	public static final TransactionDefinition DAYLY_3_NO_END;
	public static final TransactionDefinition YEARLY_1_END;
	public static final TransactionDefinition YEARLY_3_END;
	public static final TransactionDefinition MONTHLY_1_END;
	public static final TransactionDefinition MONTHLY_3_END;
	public static final TransactionDefinition WEEKLY_1_END;
	public static final TransactionDefinition WEEKLY_3_END;
	public static final TransactionDefinition DAYLY_1_END;
	public static final TransactionDefinition DAYLY_3_END;
	public static final TransactionDefinition YEARLY_1_END_OFFSET;
	public static final TransactionDefinition YEARLY_3_END_OFFSET;
	public static final TransactionDefinition MONTHLY_1_END_OFFSET;
	public static final TransactionDefinition MONTHLY_3_END_OFFSET;
	public static final TransactionDefinition WEEKLY_1_END_OFFSET;
	public static final TransactionDefinition WEEKLY_3_END_OFFSET;
	public static final TransactionDefinition DAYLY_1_END_OFFSET;
	public static final TransactionDefinition DAYLY_3_END_OFFSET;
	public static final TransactionDefinition YEARLY_1_END_PERIOD;
	public static final TransactionDefinition YEARLY_3_END_PERIOD;
	public static final TransactionDefinition MONTHLY_1_END_PERIOD;
	public static final TransactionDefinition MONTHLY_3_END_PERIOD;
	public static final TransactionDefinition WEEKLY_1_END_PERIOD;
	public static final TransactionDefinition WEEKLY_3_END_PERIOD;
	public static final TransactionDefinition DAYLY_1_END_PERIOD;
	public static final TransactionDefinition DAYLY_3_END_PERIOD;
	public static final TransactionDefinition YEARLY_1_END_3PERIOD;
	public static final TransactionDefinition YEARLY_3_END_3PERIOD;
	public static final TransactionDefinition MONTHLY_1_END_3PERIOD;
	public static final TransactionDefinition MONTHLY_3_END_3PERIOD;
	public static final TransactionDefinition WEEKLY_1_END_3PERIOD;
	public static final TransactionDefinition WEEKLY_3_END_3PERIOD;
	public static final TransactionDefinition DAYLY_1_END_3PERIOD;
	public static final TransactionDefinition DAYLY_3_END_3PERIOD;
	public static final TransactionDefinition YEARLY_1_END_PERIOD_OFFSET;
	public static final TransactionDefinition YEARLY_3_END_PERIOD_OFFSET;
	public static final TransactionDefinition MONTHLY_1_END_PERIOD_OFFSET;
	public static final TransactionDefinition MONTHLY_3_END_PERIOD_OFFSET;
	public static final TransactionDefinition WEEKLY_1_END_PERIOD_OFFSET;
	public static final TransactionDefinition WEEKLY_3_END_PERIOD_OFFSET;
	public static final TransactionDefinition DAYLY_1_END_PERIOD_OFFSET;
	public static final TransactionDefinition DAYLY_3_END_PERIOD_OFFSET;
	public static final TransactionDefinition YEARLY_1_END_3PERIOD_OFFSET;
	public static final TransactionDefinition YEARLY_3_END_3PERIOD_OFFSET;
	public static final TransactionDefinition MONTHLY_1_END_3PERIOD_OFFSET;
	public static final TransactionDefinition MONTHLY_3_END_3PERIOD_OFFSET;
	public static final TransactionDefinition WEEKLY_1_END_3PERIOD_OFFSET;
	public static final TransactionDefinition WEEKLY_3_END_3PERIOD_OFFSET;
	public static final TransactionDefinition DAYLY_1_END_3PERIOD_OFFSET;
	public static final TransactionDefinition DAYLY_3_END_3PERIOD_OFFSET;

	static {
		int id=1;

		// single occurence
		SINGLE = newTransaction(id++, "single"  , new LocalDate(2012, 12, 1), null, null, 100);
		
		// repeating, no end date
		YEARLY_1_NO_END  = newTransaction(id++, " Years.ONE   ; end=null", new LocalDate(2012, 12, 1), null,  Years.ONE  , 100);
		YEARLY_3_NO_END  = newTransaction(id++, " Years.THREE ; end=null", new LocalDate(2012, 12, 1), null,  Years.THREE, 100);
		MONTHLY_1_NO_END = newTransaction(id++, "Months.ONE   ; end=null", new LocalDate(2012, 12, 1), null, Months.ONE  , 100);
		MONTHLY_3_NO_END = newTransaction(id++, "Months.THREE ; end=null", new LocalDate(2012, 12, 1), null, Months.THREE, 100);
		WEEKLY_1_NO_END  = newTransaction(id++, " Weeks.ONE   ; end=null", new LocalDate(2012, 12, 1), null,  Weeks.ONE  , 100);
		WEEKLY_3_NO_END  = newTransaction(id++, " Weeks.THREE ; end=null", new LocalDate(2012, 12, 1), null,  Weeks.THREE, 100);
		DAYLY_1_NO_END   = newTransaction(id++, "  Days.ONE   ; end=null", new LocalDate(2012, 12, 1), null,   Days.ONE  , 100);
		DAYLY_3_NO_END   = newTransaction(id++, "  Days.THREE ; end=null", new LocalDate(2012, 12, 1), null,   Days.THREE, 100);

		// repeating, end date = start
		YEARLY_1_END  = newTransaction(id++, " Years.ONE   ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),  Years.ONE  , 100);
		YEARLY_3_END  = newTransaction(id++, " Years.THREE ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),  Years.THREE, 100);
		MONTHLY_1_END = newTransaction(id++, "Months.ONE   ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1), Months.ONE  , 100);
		MONTHLY_3_END = newTransaction(id++, "Months.THREE ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1), Months.THREE, 100);
		WEEKLY_1_END  = newTransaction(id++, " Weeks.ONE   ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),  Weeks.ONE  , 100);
		WEEKLY_3_END  = newTransaction(id++, " Weeks.THREE ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),  Weeks.THREE, 100);
		DAYLY_1_END   = newTransaction(id++, "  Days.ONE   ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),   Days.ONE  , 100);
		DAYLY_3_END   = newTransaction(id++, "  Days.THREE ; end=start", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1),   Days.THREE, 100);
		
		// repeating, end date = start+offset
		YEARLY_1_END_OFFSET  = newTransaction(id++, " Years.ONE   ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1),  Years.ONE  , 100);
		YEARLY_3_END_OFFSET  = newTransaction(id++, " Years.THREE ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1),  Years.THREE, 100);
		MONTHLY_1_END_OFFSET = newTransaction(id++, "Months.ONE   ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1), Months.ONE  , 100);
		MONTHLY_3_END_OFFSET = newTransaction(id++, "Months.THREE ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1), Months.THREE, 100);
		WEEKLY_1_END_OFFSET  = newTransaction(id++, " Weeks.ONE   ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1),  Weeks.ONE  , 100);
		WEEKLY_3_END_OFFSET  = newTransaction(id++, " Weeks.THREE ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1),  Weeks.THREE, 100);
		DAYLY_1_END_OFFSET   = newTransaction(id++, "  Days.ONE   ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1),   Days.ONE  , 100);
		DAYLY_3_END_OFFSET   = newTransaction(id++, "  Days.THREE ; end=start+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1),   Days.THREE, 100);
		
		// repeating, end date = start+period
		YEARLY_1_END_PERIOD  = newTransaction(id++, " Years.ONE   ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Years.ONE  ),  Years.ONE  , 100);
		YEARLY_3_END_PERIOD  = newTransaction(id++, " Years.THREE ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Years.THREE),  Years.THREE, 100);
		MONTHLY_1_END_PERIOD = newTransaction(id++, "Months.ONE   ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(Months.ONE  ), Months.ONE  , 100);
		MONTHLY_3_END_PERIOD = newTransaction(id++, "Months.THREE ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(Months.THREE), Months.THREE, 100);
		WEEKLY_1_END_PERIOD  = newTransaction(id++, " Weeks.ONE   ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Weeks.ONE  ),  Weeks.ONE  , 100);
		WEEKLY_3_END_PERIOD  = newTransaction(id++, " Weeks.THREE ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Weeks.THREE),  Weeks.THREE, 100);
		DAYLY_1_END_PERIOD   = newTransaction(id++, "  Days.ONE   ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(  Days.ONE  ),   Days.ONE  , 100);
		DAYLY_3_END_PERIOD   = newTransaction(id++, "  Days.THREE ; end=start+period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(  Days.THREE),   Days.THREE, 100);
		
		// repeating, end date = start+3*period
		YEARLY_1_END_3PERIOD  = newTransaction(id++, " Years.ONE   ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Years.ONE  ).plus( Years.ONE  ).plus( Years.ONE  ),  Years.ONE  , 100);
		YEARLY_3_END_3PERIOD  = newTransaction(id++, " Years.THREE ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Years.THREE).plus( Years.THREE).plus( Years.THREE),  Years.THREE, 100);
		MONTHLY_1_END_3PERIOD = newTransaction(id++, "Months.ONE   ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(Months.ONE  ).plus(Months.ONE  ).plus(Months.ONE  ), Months.ONE  , 100);
		MONTHLY_3_END_3PERIOD = newTransaction(id++, "Months.THREE ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(Months.THREE).plus(Months.THREE).plus(Months.THREE), Months.THREE, 100);
		WEEKLY_1_END_3PERIOD  = newTransaction(id++, " Weeks.ONE   ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Weeks.ONE  ).plus( Weeks.ONE  ).plus( Weeks.ONE  ),  Weeks.ONE  , 100);
		WEEKLY_3_END_3PERIOD  = newTransaction(id++, " Weeks.THREE ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus( Weeks.THREE).plus( Weeks.THREE).plus( Weeks.THREE),  Weeks.THREE, 100);
		DAYLY_1_END_3PERIOD   = newTransaction(id++, "  Days.ONE   ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(  Days.ONE  ).plus(  Days.ONE  ).plus(  Days.ONE  ),   Days.ONE  , 100);
		DAYLY_3_END_3PERIOD   = newTransaction(id++, "  Days.THREE ; end=start+3*period", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plus(  Days.THREE).plus(  Days.THREE).plus(  Days.THREE),   Days.THREE, 100);
		
		// repeating, end date = start+period+offset
		YEARLY_1_END_PERIOD_OFFSET  = newTransaction(id++, " Years.ONE   ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1).plus( Years.ONE  ),  Years.ONE  , 100);
		YEARLY_3_END_PERIOD_OFFSET  = newTransaction(id++, " Years.THREE ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1).plus( Years.THREE),  Years.THREE, 100);
		MONTHLY_1_END_PERIOD_OFFSET = newTransaction(id++, "Months.ONE   ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1).plus(Months.ONE  ), Months.ONE  , 100);
		MONTHLY_3_END_PERIOD_OFFSET = newTransaction(id++, "Months.THREE ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1).plus(Months.THREE), Months.THREE, 100);
		WEEKLY_1_END_PERIOD_OFFSET  = newTransaction(id++, " Weeks.ONE   ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1).plus( Weeks.ONE  ),  Weeks.ONE  , 100);
		WEEKLY_3_END_PERIOD_OFFSET  = newTransaction(id++, " Weeks.THREE ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1).plus( Weeks.THREE),  Weeks.THREE, 100);
		DAYLY_1_END_PERIOD_OFFSET   = newTransaction(id++, "  Days.ONE   ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1).plus(  Days.ONE  ),   Days.ONE  , 100);
		DAYLY_3_END_PERIOD_OFFSET   = newTransaction(id++, "  Days.THREE ; end=start+period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1).plus(  Days.THREE),   Days.THREE, 100);
		
		// repeating, end date = start+3*period+offset
		YEARLY_1_END_3PERIOD_OFFSET  = newTransaction(id++, " Years.ONE   ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1).plus( Years.ONE  ).plus( Years.ONE  ).plus( Years.ONE  ),  Years.ONE  , 100);
		YEARLY_3_END_3PERIOD_OFFSET  = newTransaction(id++, " Years.THREE ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusMonths(1).plus( Years.THREE).plus( Years.THREE).plus( Years.THREE),  Years.THREE, 100);
		MONTHLY_1_END_3PERIOD_OFFSET = newTransaction(id++, "Months.ONE   ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1).plus(Months.ONE  ).plus(Months.ONE  ).plus(Months.ONE  ), Months.ONE  , 100);
		MONTHLY_3_END_3PERIOD_OFFSET = newTransaction(id++, "Months.THREE ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusWeeks (1).plus(Months.THREE).plus(Months.THREE).plus(Months.THREE), Months.THREE, 100);
		WEEKLY_1_END_3PERIOD_OFFSET  = newTransaction(id++, " Weeks.ONE   ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1).plus( Weeks.ONE  ).plus( Weeks.ONE  ).plus( Weeks.ONE  ),  Weeks.ONE  , 100);
		WEEKLY_3_END_3PERIOD_OFFSET  = newTransaction(id++, " Weeks.THREE ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusDays  (1).plus( Weeks.THREE).plus( Weeks.THREE).plus( Weeks.THREE),  Weeks.THREE, 100);
		DAYLY_1_END_3PERIOD_OFFSET   = newTransaction(id++, "  Days.ONE   ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1).plus(  Days.ONE  ).plus(  Days.ONE  ).plus(  Days.ONE  ),   Days.ONE  , 100);
		DAYLY_3_END_3PERIOD_OFFSET   = newTransaction(id++, "  Days.THREE ; end=start+3*period+offset", new LocalDate(2012, 12, 1), new LocalDate(2012, 12, 1).plusYears (1).plus(  Days.THREE).plus(  Days.THREE).plus(  Days.THREE),   Days.THREE, 100);
	}
	
	static public TransactionDefinition newTransaction(Integer id, String label, LocalDate date, LocalDate end, ReadablePeriod period, double amount) {
		TransactionDefinition td = new TransactionDefinition();
		if (id != null) td.setId(id);
		td.setLabel(label);
		td.setDate(date);
		td.setEnd(end);
		td.setPeriod(period);
		td.setAmount(amount);
		return td;
	}
	
	
	private TestTransactions() {
	}
}
