/*
 * Copyright (c) 2006-2009, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 * 3. Products derived from the software may not be called "Alice",
 *    nor may "Alice" appear in their name, without prior written
 *    permission of Carnegie Mellon University.
 * 
 * 4. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    "This product includes software developed by Carnegie Mellon University"
 */
package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public abstract class TreeCellRenderer<E> extends javax.swing.tree.DefaultTreeCellRenderer {
	protected abstract javax.swing.JLabel getListCellRendererComponent( javax.swing.JLabel rv, javax.swing.JTree tree, E value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus );
	@Override
	public final java.awt.Component getTreeCellRendererComponent( javax.swing.JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus ) {
		java.awt.Component rv = super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus );
		if( rv instanceof javax.swing.JLabel ) {
			getListCellRendererComponent( (javax.swing.JLabel)rv, tree, (E)value, sel, expanded, leaf, row, hasFocus );
		} else {
			//todo
		}
		return rv;
	}
}
