/*
 * Copyright (c) 2006-2010, Carnegie Mellon University. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may 
 *    "Alice" appear in their name, without prior written permission of 
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software 
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is 
 *    contributed by Electronic Arts Inc. and may be used for personal, 
 *    non-commercial, and academic use only. Redistributions of any program 
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in 
 *    The Alice 3.0 Art Gallery License.
 * 
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.  
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A 
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND 
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO 
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.alice.ide.openprojectpane;

/**
 * @author Dennis Cosgrove
 */
public abstract class ListContentPanel< M extends org.alice.ide.openprojectpane.models.UriSelectionState > extends TabContentPanel {
	private final M state;
	public ListContentPanel( org.lgna.croquet.TabComposite< ? > composite, M state ) {
		super( composite );
		this.state = state;
		final org.lgna.croquet.components.List<java.net.URI> list = this.state.createList();
		list.setBackgroundColor( null );
		list.setCellRenderer( this.createListCellRenderer() );
		list.setLayoutOrientation( org.lgna.croquet.components.List.LayoutOrientation.HORIZONTAL_WRAP );
		list.setVisibleRowCount( -1 );
		
		edu.cmu.cs.dennisc.java.awt.event.LenientMouseClickAdapter mouseAdapter = new edu.cmu.cs.dennisc.java.awt.event.LenientMouseClickAdapter() {
			@Override
			protected void mouseQuoteClickedUnquote(java.awt.event.MouseEvent e, int quoteClickCountUnquote ) {
				if( quoteClickCountUnquote == 2 ) {
					org.lgna.croquet.components.Button defaultButton = list.getRoot().getDefaultButton();
					if( defaultButton != null ) {
						defaultButton.doClick();
					}
				}
			}
		};
		list.addMouseListener( mouseAdapter );
		list.addMouseMotionListener( mouseAdapter );
		list.addKeyListener( new java.awt.event.KeyListener() {
			public void keyPressed( java.awt.event.KeyEvent e ) {
				if( e.getKeyCode() == java.awt.event.KeyEvent.VK_F5 ) {
					ListContentPanel.this.state.refresh();
				}
			}
			public void keyReleased( java.awt.event.KeyEvent e ) {
			}
			public void keyTyped( java.awt.event.KeyEvent e ) {
			}
		} );
		this.addComponent(  list, Constraint.CENTER );
	}
	protected javax.swing.ListCellRenderer createListCellRenderer() {
		return new ProjectSnapshotListCellRenderer();
	}
	protected M getState() {
		return this.getState();
	}
	protected abstract String getTextForZeroProjects();
	@Override
	public java.net.URI getSelectedUri() {
		return this.state.getSelectedItem();
	}
}
