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
package edu.cmu.cs.dennisc.croquet;

/**
 * @author Dennis Cosgrove
 */
public class MenuModel extends Model {
	private static final Group MENU_GROUP = new Group( java.util.UUID.fromString( "4ed42b1f-b4ea-4f70-99d1-5bb2c3f11081" ), "MENU_GROUP" );
	public static final Model SEPARATOR = null;
	private class MenuListener implements javax.swing.event.MenuListener {
		private Menu<?> menu;
		public MenuListener( Menu<?> menu ) {
			this.menu = menu;
		}
		public void menuSelected( javax.swing.event.MenuEvent e ) {
			Component< ? > parent = this.menu.getParent();
			ModelContext< ? > parentContext;
			if( parent instanceof MenuBar ) {
				MenuBar menuBar = (MenuBar)parent;
				parentContext = menuBar.createMenuBarContext();
			} else {
				Application application = Application.getSingleton();
				parentContext = application.getCurrentContext();
			}
			MenuModelContext context = parentContext.createMenuModelContext( MenuModel.this, this.menu );
			context.handleMenuSelected( e );
		}
		public void menuDeselected( javax.swing.event.MenuEvent e ) {
			Application application = Application.getSingleton();
			edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: menuDeselected" );
			MenuModelContext context = (MenuModelContext)application.getCurrentContext();
			context.handleMenuDeselected( e );
		}
		public void menuCanceled( javax.swing.event.MenuEvent e ) {
			Application application = Application.getSingleton();
			edu.cmu.cs.dennisc.print.PrintUtilities.println( "todo: menuCanceled" );
			MenuModelContext context = (MenuModelContext)application.getCurrentContext();
			context.handleMenuCanceled( e );
		}
	};
	private java.util.Map< Menu, MenuListener > mapMenuToListener = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	private String name;
	private int mnemonic;
	private Model[] models;
	
	private static final int NULL_MNEMONIC = 0;
	public MenuModel( java.util.UUID individualId, Model... models ) {
		super( MENU_GROUP, individualId );
		this.name = this.getLocalizedName();
		this.mnemonic = this.getLocalizedMnemonic();
		this.models = models;
	}
	public MenuModel( java.util.UUID individualId, java.util.List< Model > models ) {
		this( individualId, edu.cmu.cs.dennisc.java.util.CollectionUtilities.createArray(models, Model.class) );
	}

	private String getLocalizedName() {
		return this.getLocalizedText( "name" );
	}
	private int getLocalizedMnemonic() {
		String fieldName = this.getLocalizedText( "mnemonic" );
		if( fieldName != null ) {
			try {
				java.lang.reflect.Field field = java.awt.event.KeyEvent.class.getField( fieldName );
				Object value = field.get( null );
				if( value instanceof Integer ) {
					return (Integer)value;
				}
			} catch( NoSuchFieldException nsfe ) {
				nsfe.printStackTrace();
			} catch( IllegalAccessException iae ) {
				iae.printStackTrace();
			}
		}
		return NULL_MNEMONIC;
	}
	public Model[] getOperations() {
		return this.models;
	}
	public Menu<MenuModel> createMenu() {
		Menu<MenuModel> rv = new Menu<MenuModel>( this ) {
			@Override
			protected void handleAddedTo(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
				super.handleAddedTo( parent );
				this.setText( MenuModel.this.name );
				this.setMnemonic( MenuModel.this.mnemonic );
				assert mapMenuToListener.containsKey( this ) == false;
				MenuListener menuListener = new MenuListener( this );
				MenuModel.this.mapMenuToListener.put( this, menuListener );
				this.getAwtComponent().addMenuListener( menuListener );
				this.getAwtComponent().addActionListener( new java.awt.event.ActionListener() {
					public void actionPerformed( java.awt.event.ActionEvent e ) {
						edu.cmu.cs.dennisc.print.PrintUtilities.println( "Menu actionPerformed", e );
					}
				} );
			}

			@Override
			protected void handleRemovedFrom(edu.cmu.cs.dennisc.croquet.Component<?> parent) {
				MenuModel.this.removeComponent( this );
				MenuListener menuListener = MenuModel.this.mapMenuToListener.get( this );
				assert menuListener != null;
				this.getAwtComponent().removeMenuListener( menuListener );
				MenuModel.this.mapMenuToListener.remove( this );
				this.setMnemonic( 0 );
				this.setText( null );
				super.handleRemovedFrom( parent );
			}
		};
		Application.addMenuElements( rv, this.getOperations() );
		return rv;
	}
	
}
