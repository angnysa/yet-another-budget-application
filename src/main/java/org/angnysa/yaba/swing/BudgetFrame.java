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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.joda.time.Years;

import org.angnysa.yaba.dto.TransactionDefinition;
import org.angnysa.yaba.service.TransactionService;
import org.angnysa.yaba.service.impl.DefaultTransactionService;
import org.angnysa.yaba.swing.ext.CustomCellEditor;
import org.angnysa.yaba.swing.ext.FormattedTableCellRenderer;
import org.angnysa.yaba.swing.ext.OptionalValueFormatter;
import org.angnysa.yaba.text.format.JodaLocalDateFormat;
import org.angnysa.yaba.text.format.JodaPeriodFormat;
import org.angnysa.yaba.text.format.TransactionAmountFormatFactory;;

public class BudgetFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private JTable reconciliationTable;
	private ReconciliationTableModel reconciliationModel;
	
	private JTable transactionTable;
	private TransactionTableModel transactionModel;

	private JPanel simulationPanel;
	private SimulationDataset simulationDataset;

	private JFormattedTextField amountFld;
	private JFormattedTextField fromFld;
	private JFormattedTextField toFld;
	private JFormattedTextField periodFld;
	
	private TransactionService service;

	
	public BudgetFrame(int id, DefaultTransactionService service) {
		super();
		
		this.id = id;
		this.service = service;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				SwingMain.close(BudgetFrame.this.id);
			}
		});
		
		buildTransactionTable();
		buildReconciliationTable();
		buildSimulationPanel();
		buildMenubar();
		
		layoutComponents();
	}

	private void buildTransactionTable() {
		transactionModel = new TransactionTableModel(service);
		transactionTable = new JTable(transactionModel);
		transactionTable.setRowHeight((int) (transactionTable.getRowHeight()*1.2));
		transactionTable.getColumnModel().getColumn(TransactionTableModel.COL_END).setCellEditor(new CustomCellEditor(new JFormattedTextField(new OptionalValueFormatter(new JodaLocalDateFormat()))));
		transactionTable.setDefaultEditor  (LocalDate.class     , new CustomCellEditor(new JFormattedTextField(new JodaLocalDateFormat())));
		transactionTable.setDefaultEditor  (ReadablePeriod.class, new CustomCellEditor(new JFormattedTextField(new OptionalValueFormatter(new JodaPeriodFormat()))));
		transactionTable.setDefaultEditor  (Double.class        , new CustomCellEditor(new JFormattedTextField(NumberFormat.getNumberInstance())));
		transactionTable.setDefaultRenderer(LocalDate.class     , new FormattedTableCellRenderer(new JodaLocalDateFormat()));
		transactionTable.setDefaultRenderer(ReadablePeriod.class, new FormattedTableCellRenderer(new JodaPeriodFormat()));
		transactionTable.setDefaultRenderer(Double.class        , new FormattedTableCellRenderer(TransactionAmountFormatFactory.getFormat()));
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setAutoCreateRowSorter(true);
		transactionTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete"); //$NON-NLS-1$
		transactionTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete"); //$NON-NLS-1$
		transactionTable.getActionMap().put("delete", new AbstractAction() { //$NON-NLS-1$
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = transactionTable.getSelectedRow();
				if (row >= 0) {
					row = transactionTable.getRowSorter().convertRowIndexToModel(row);
					transactionModel.deleteRow(row);
				}
			}
		});
	}

	private void buildReconciliationTable() {
		reconciliationModel = new ReconciliationTableModel(service);
		reconciliationTable = new JTable(reconciliationModel);
		reconciliationTable.setRowHeight((int) (reconciliationTable.getRowHeight()*1.2));
		reconciliationTable.setDefaultEditor  (LocalDate.class, new CustomCellEditor(new JFormattedTextField(new JodaLocalDateFormat())));
		reconciliationTable.setDefaultEditor  (Double.class   , new CustomCellEditor(new JFormattedTextField(NumberFormat.getNumberInstance())));
		reconciliationTable.setDefaultRenderer(LocalDate.class, new FormattedTableCellRenderer(new JodaLocalDateFormat()));
		reconciliationTable.setDefaultRenderer(Double.class   , new FormattedTableCellRenderer(TransactionAmountFormatFactory.getFormat()));
		reconciliationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reconciliationTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete"); //$NON-NLS-1$
		reconciliationTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete"); //$NON-NLS-1$
		reconciliationTable.getActionMap().put("delete", new AbstractAction() { //$NON-NLS-1$
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {

				int row = reconciliationTable.getSelectedRow();
				if (row >= 0) {
					reconciliationModel.deleteRow(row);
				}
			}
		});
		transactionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (! e.getValueIsAdjusting()) {
					int row = transactionTable.getSelectedRow();
					if (row >= 0) {
						row = transactionTable.getRowSorter().convertRowIndexToModel(row);
						TransactionDefinition td = transactionModel.getTransactionForRow(row);
						if (td != null) {
							reconciliationModel.setCurrentTransactionId(td.getId());
						} else {
							reconciliationModel.setCurrentTransactionId(-1);
						}
					} else {
						reconciliationModel.setCurrentTransactionId(-1);
					}
				}
			}
		});
	}

	private void buildSimulationPanel() {
		
		simulationPanel = new JPanel();
		SpringLayout springLayout = new SpringLayout();
		simulationPanel.setLayout(springLayout);
		
		// chart data
		simulationDataset = new SimulationDataset(service);
		simulationDataset.setInitial(0D);
		simulationDataset.setStart(new LocalDate());
		simulationDataset.setEnd(new LocalDate().plus(Years.ONE));
		simulationDataset.setPeriod(Period.months(1));
		simulationDataset.updateDataset();
		transactionModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				simulationDataset.updateDataset();
			}
		});
		reconciliationModel.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				simulationDataset.updateDataset();
			}
		});

		// initial amount label
		JLabel amountLbl = new JLabel(Messages.getString("simulation.field.initial-amount")); //$NON-NLS-1$
		springLayout.putConstraint(SpringLayout.WEST, amountLbl, 10, SpringLayout.WEST, simulationPanel);
		simulationPanel.add(amountLbl);
		
		// initial amount field
		amountFld = new JFormattedTextField(
				new DefaultFormatterFactory(
						new NumberFormatter(NumberFormat.getNumberInstance()),
						new NumberFormatter(NumberFormat.getCurrencyInstance())));
		amountFld.setColumns(8);
		amountFld.setValue(simulationDataset.getInitial());
		amountFld.addPropertyChangeListener("value", new PropertyChangeListener() { //$NON-NLS-1$
			
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				simulationDataset.setInitial(((Number) amountFld.getValue()).doubleValue());
				simulationDataset.updateDataset();
			}
		});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, amountLbl, 0, SpringLayout.VERTICAL_CENTER, amountFld);
		springLayout.putConstraint(SpringLayout.WEST, amountFld, 0, SpringLayout.EAST, amountLbl);
		springLayout.putConstraint(SpringLayout.NORTH, amountFld, 10, SpringLayout.NORTH, simulationPanel);
		simulationPanel.add(amountFld);

		// start date label
		JLabel fromLbl = new JLabel(Messages.getString("simulation.field.start-date")); //$NON-NLS-1$
		springLayout.putConstraint(SpringLayout.WEST, fromLbl, 10, SpringLayout.EAST, amountFld);
		simulationPanel.add(fromLbl);
		
		// start date field
		fromFld = new JFormattedTextField(new JodaLocalDateFormat());
		fromFld.setColumns(8);
		fromFld.setValue(simulationDataset.getStart());
		fromFld.addPropertyChangeListener("value", new PropertyChangeListener() { //$NON-NLS-1$
			
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				simulationDataset.setStart((LocalDate) fromFld.getValue());
				simulationDataset.updateDataset();
			}
		});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, fromLbl, 0, SpringLayout.VERTICAL_CENTER, fromFld);
		springLayout.putConstraint(SpringLayout.WEST, fromFld, 0, SpringLayout.EAST, fromLbl);
		springLayout.putConstraint(SpringLayout.NORTH, fromFld, 10, SpringLayout.NORTH, simulationPanel);
		simulationPanel.add(fromFld);

		// end date label
		JLabel toLbl = new JLabel(Messages.getString("simulation.field.end-date")); //$NON-NLS-1$
		springLayout.putConstraint(SpringLayout.WEST, toLbl, 10, SpringLayout.EAST, fromFld);
		simulationPanel.add(toLbl);
		
		// end date field
		toFld = new JFormattedTextField(new JodaLocalDateFormat());
		toFld.setColumns(8);
		toFld.setValue(simulationDataset.getEnd());
		toFld.addPropertyChangeListener("value", new PropertyChangeListener() { //$NON-NLS-1$
			
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				simulationDataset.setEnd((LocalDate) toFld.getValue());
				simulationDataset.updateDataset();
			}
		});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, toLbl, 0, SpringLayout.VERTICAL_CENTER, toFld);
		springLayout.putConstraint(SpringLayout.WEST, toFld, 0, SpringLayout.EAST, toLbl);
		springLayout.putConstraint(SpringLayout.NORTH, toFld, 10, SpringLayout.NORTH, simulationPanel);
		simulationPanel.add(toFld);

		// period label
		JLabel periodLbl = new JLabel(Messages.getString("simulation.field.period")); //$NON-NLS-1$
		springLayout.putConstraint(SpringLayout.WEST, periodLbl, 10, SpringLayout.EAST, toFld);
		simulationPanel.add(periodLbl);
		
		// period field
		periodFld = new JFormattedTextField(new JodaPeriodFormat());
		periodFld.setColumns(5);
		periodFld.setValue(simulationDataset.getPeriod());
		periodFld.addPropertyChangeListener("value", new PropertyChangeListener() { //$NON-NLS-1$
			
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				simulationDataset.setPeriod((ReadablePeriod) periodFld.getValue());
				simulationDataset.updateDataset();
			}
		});
		springLayout.putConstraint(SpringLayout.VERTICAL_CENTER, periodLbl, 0, SpringLayout.VERTICAL_CENTER, periodFld);
		springLayout.putConstraint(SpringLayout.WEST, periodFld, 0, SpringLayout.EAST, periodLbl);
		springLayout.putConstraint(SpringLayout.NORTH, periodFld, 10, SpringLayout.NORTH, simulationPanel);
		simulationPanel.add(periodFld);
		
		// chart panel
		JFreeChart chart = ChartFactory.createLineChart("", Messages.getString("simulation.chart.date-axis-label"), Messages.getString("simulation.chart.amount-axis-label"), simulationDataset, PlotOrientation.VERTICAL, false, true, false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesFilled(true);
		renderer.setBaseShapesVisible(true);
		renderer.setBaseToolTipGenerator(new SimulationTooltipGenerator(service));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setDismissDelay(3600000);
		chartPanel.setInitialDelay(0);
		springLayout.putConstraint(SpringLayout.NORTH, chartPanel, 15, SpringLayout.SOUTH , periodFld);
		springLayout.putConstraint(SpringLayout.WEST , chartPanel, 10, SpringLayout.WEST , simulationPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, chartPanel, -10, SpringLayout.SOUTH, simulationPanel);
		springLayout.putConstraint(SpringLayout.EAST , chartPanel, -10, SpringLayout.EAST , simulationPanel);
		simulationPanel.add(chartPanel);
	}

	private void buildMenubar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(Messages.getString("menu.file.label")); //$NON-NLS-1$
		JMenuItem item;
		
		// new
		item = new JMenuItem(Messages.getString("menu.file.new")); //$NON-NLS-1$
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingMain.newEditor(null);
			}
		});
		fileMenu.add(item);
		
		// open
		item = new JMenuItem(Messages.getString("menu.file.open")); //$NON-NLS-1$
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingMain.open(id);
			}
		});
		fileMenu.add(item);
		
		// save
		item = new JMenuItem(Messages.getString("menu.file.save")); //$NON-NLS-1$
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingMain.save(id);
			}
		});
		fileMenu.add(item);
		
		// save as
		item = new JMenuItem(Messages.getString("menu.file.save-as")); //$NON-NLS-1$
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingMain.saveas(id);
			}
		});
		fileMenu.add(item);
		
		// close
		item = new JMenuItem(Messages.getString("menu.file.close")); //$NON-NLS-1$
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingMain.close(id);
			}
		});
		fileMenu.add(item);
		
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	private void layoutComponents() {
		setLayout(new BorderLayout());
		
		JSplitPane subSplitPane = new JSplitPane(
				JSplitPane.VERTICAL_SPLIT, true,
				new JScrollPane(transactionTable),
				new JScrollPane(reconciliationTable));
		subSplitPane.setPreferredSize(new Dimension(400, 600));
		
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, true,
				subSplitPane, simulationPanel);
		splitPane.setPreferredSize(new Dimension(1000, 600));
		
		add(splitPane, BorderLayout.CENTER);
		
		pack();

		subSplitPane.setDividerLocation(0.5);
		splitPane.setDividerLocation(0.4);
	}
}
