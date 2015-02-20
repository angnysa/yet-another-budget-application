package org.angnysa.yaba.swing.ext;

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

import java.awt.Toolkit;
import java.text.ParseException;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class CustomCellEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	
	class JFormattedTextFieldDelegate extends EditorDelegate {
		private static final long serialVersionUID = 1L;
		
		@Override
		public Object getCellEditorValue() {
			value = ((JFormattedTextField) editorComponent).getValue();
			return value;
		}

		@Override
		public void setValue(Object value) {
			this.value = value;
			((JFormattedTextField) editorComponent).setValue(value);
		}

		@Override
		public boolean stopCellEditing() {
			try {
				((JFormattedTextField) editorComponent).commitEdit();
				fireEditingStopped();
				return true;
			} catch (ParseException e) {
				Toolkit.getDefaultToolkit().beep();
				return false;
			}
		}
	}

	public CustomCellEditor(JFormattedTextField formattedTextField) {
		super(formattedTextField);
		formattedTextField.removeActionListener(delegate);
		this.delegate = new JFormattedTextFieldDelegate();
		formattedTextField.addActionListener(delegate);
	}
	
	public CustomCellEditor(JCheckBox checkBox) {
		super(checkBox);
	}
	
	public CustomCellEditor(JComboBox comboBox) {
		super(comboBox);
	}
	
	public CustomCellEditor(JTextField textField) {
		super(textField);
	}
}
