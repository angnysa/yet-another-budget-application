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

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.angnysa.yaba.dao.ReadException;
import org.angnysa.yaba.dao.WriteException;
import org.angnysa.yaba.dao.impl.FileTransactionDefinitionDao;
import org.angnysa.yaba.service.impl.DefaultTransactionService;

public class SwingMain {

	private static final int FRAME_POSITION_OFFSET = 100;
	private static final int FRAME_POSITION_INCREMENT = 22;
	
	private static List<JFrame> frames = new ArrayList<JFrame>();
	private static List<FileTransactionDefinitionDao> daos = new ArrayList<FileTransactionDefinitionDao>();
	private static File currentDirectory;
	private static int frameX = FRAME_POSITION_OFFSET;
	private static int frameY = FRAME_POSITION_OFFSET;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					newEditor(null);
				}
			});
		} else {
			for (final String doc : args) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						newEditor(new File(doc));
					}
				});
			}
		}
	}
	
	public static void newEditor(File doc) {
		try {
    		FileTransactionDefinitionDao dao = new FileTransactionDefinitionDao();
    		
    		int id = frames.size();
    		DefaultTransactionService service = new DefaultTransactionService(dao);
    		JFrame frm = new BudgetFrame(id, service);
    		
    		daos.add(dao);
    		frames.add(frm);
    		
    		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    		Rectangle bounds = frm.getBounds();
    		frameX = frameX+FRAME_POSITION_INCREMENT;
    		frameY = frameY+FRAME_POSITION_INCREMENT;
    		
    		if (frameX > screen.getWidth() - FRAME_POSITION_OFFSET) frameX = FRAME_POSITION_OFFSET;
    		if (frameY > screen.getHeight() - FRAME_POSITION_OFFSET) frameY = FRAME_POSITION_OFFSET;
    		
    		bounds.setLocation(frameX, frameY);
    		frm.setBounds(bounds);
    		
    		setSource(id, doc);
    		if (doc != null) {
    			dao.rollback();
    		}
    		frm.setVisible(true);
   		}
		catch (ReadException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					null,
					Messages.getString("error.read.details", doc, e.getLocalizedMessage()),
					Messages.getString("error.read.title"),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void open(int id) {
		JFileChooser jfc = new JFileChooser(currentDirectory);
		int ret = jfc.showOpenDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			newEditor(jfc.getSelectedFile());
			currentDirectory = jfc.getCurrentDirectory();
		}
	}

	public static boolean save(int id) {
		if (daos.get(id).getSource() == null) {
			return saveas(id);
		} else {
			try {
				daos.get(id).commit();
				return true;
			} catch (WriteException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						null,
						Messages.getString("error.write.details", daos.get(id).getSource(), e.getLocalizedMessage()),
						Messages.getString("error.write.title"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	}

	public static boolean saveas(int id) {
		JFileChooser jfc = new JFileChooser(currentDirectory);
		while (true) {
    		int ret = jfc.showSaveDialog(frames.get(id));
    		if (ret == JFileChooser.APPROVE_OPTION) {
    			File f = jfc.getSelectedFile();
    			if (f.exists()) {
    				ret = JOptionPane.showConfirmDialog(
    						frames.get(id),
    						Messages.getString("warning.save-overwrite.details", f),
    						Messages.getString("warning.save-overwrite.title"),
    						JOptionPane.YES_NO_CANCEL_OPTION,
    						JOptionPane.WARNING_MESSAGE);
    				if (ret == JOptionPane.CANCEL_OPTION) {
    					return false;
    				} else if (ret == JOptionPane.NO_OPTION) {
    					continue;
    				}
    			}
    			setSource(id, f);
    			currentDirectory = jfc.getCurrentDirectory();
    			return save(id);
    		} else {
    			return false;
    		}
		}
	}

	private static void setSource(int id, File source) {

		daos.get(id).setSource(source);
		
		if (source == null) {
			frames.get(id).setTitle(Messages.getString("jframe.untitled", id+1));
		} else {
			frames.get(id).setTitle(Messages.getString("jframe.title", source));
		}
	}

	public static boolean close(int id) {
		if (daos.get(id).isModified()) {
    		int ret = JOptionPane.showConfirmDialog(
    				frames.get(id),
					Messages.getString("warning.close-unsaved-changes.details"),
					Messages.getString("warning.close-unsaved-changes.title"),
    				JOptionPane.YES_NO_CANCEL_OPTION,
    				JOptionPane.WARNING_MESSAGE);
    		if (ret == JOptionPane.CANCEL_OPTION) {
    			return false;
    		} else if (ret == JOptionPane.YES_OPTION) {
    			if (! save(id)) {
    				return false;
    			}
    		}
		}

		frames.get(id).dispose();
		daos.set(id, null);
		frames.set(id, null);
		
		return true;
	}
	
	public static boolean closeall() {
		for (int id = 0; id<daos.size(); id++) {
			if (daos.get(id) != null) {
				if (! close(id)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
