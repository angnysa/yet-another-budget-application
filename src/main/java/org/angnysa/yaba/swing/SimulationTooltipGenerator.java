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

import java.text.NumberFormat;

import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.data.category.CategoryDataset;
import org.angnysa.yaba.dto.Transaction;
import org.angnysa.yaba.dto.SimulationDetail;
import org.angnysa.yaba.service.TransactionService;
import org.angnysa.yaba.text.format.TransactionAmountFormatFactory;

public class SimulationTooltipGenerator implements CategoryToolTipGenerator {
	
	private TransactionService service;
	private NumberFormat totalFormat = NumberFormat.getCurrencyInstance();
	private NumberFormat diffFormat = TransactionAmountFormatFactory.getFormat();

	public SimulationTooltipGenerator(TransactionService service) {
		this.service = service;
	}

	@Override
	public String generateToolTip(CategoryDataset dataset, int row, int column) {
		StringBuffer tooltip = new StringBuffer();
		
		SimulationDetail detail = ((SimulationDataset) dataset).getDetail(column);

		tooltip.append("<html><head /><body><b>");
		tooltip.append(detail.getDate());
		tooltip.append(" : ");
		tooltip.append(totalFormat.format(detail.getRemainingAmount()));
		tooltip.append("</b>");
		
		for (Transaction t : detail.getTransactions()) {
			tooltip.append("<br />");
			tooltip.append(service.get(t.getTransactionId()).getLabel());
			tooltip.append(" (");
			tooltip.append(t.getDate());
			tooltip.append(") : ");
			tooltip.append(diffFormat.format(t.getAmount()));
		}

		tooltip.append("</body></html>");
		
		return tooltip.toString();
	}

}
