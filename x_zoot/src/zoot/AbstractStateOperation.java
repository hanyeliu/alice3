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
package zoot;

/**
 * @author Dennis Cosgrove
 */
public abstract class AbstractStateOperation<E> extends AbstractOperation implements StateOperation< E > {
	private E state;
	public AbstractStateOperation( E initialState ) {
		this.state = initialState;
	}
	public E getState() {
		return this.state;
	}
	protected abstract void handleStateChange( zoot.StateContext< E > stateContext );
	public final void performStateChange( zoot.StateContext< E > stateContext ) {
		this.state = stateContext.getNextValue();
		this.handleStateChange( stateContext );
	}
	private javax.swing.Action actionForConfiguringSwingComponents = new javax.swing.AbstractAction() {
		public void actionPerformed( java.awt.event.ActionEvent e ) {
		}
	};
	public javax.swing.Action getActionForConfiguringSwing() {
		return this.actionForConfiguringSwingComponents;
	}
	protected void putValue( String key, Object value ) {
		this.actionForConfiguringSwingComponents.putValue( key, value );
	}
//	public void addStateChangeListener( javax.swing.event.ChangeListener l ) {
//	}
//	public void removeStateChangeListener( javax.swing.event.ChangeListener l ) {
//	}
}
