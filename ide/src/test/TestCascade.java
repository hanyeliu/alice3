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

package test;

class EnumConstantFillIn< T extends Enum< T > > extends edu.cmu.cs.dennisc.croquet.CascadeFillIn< T, Void > {
	private static java.util.Map< Object, EnumConstantFillIn > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static synchronized < T extends Enum< T > > EnumConstantFillIn<T> getInstance( T value ) {
		EnumConstantFillIn rv = map.get( value );
		if( rv != null ) {
			//pass
		} else {
			rv = new EnumConstantFillIn( value );
			map.put( value, rv );
		}
		return rv;
	}
	private  T value;
	private EnumConstantFillIn( T value ) {
		super( java.util.UUID.fromString( "0128c434-7a3d-4e32-b56e-ff3a233c0af8" ) );
		this.value = value;
	}
	@Override
	public javax.swing.Icon getMenuItemIcon( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< T, Void > context ) {
		return null;
	}
	@Override
	public String getMenuItemText( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< T, Void > context ) {
		return this.value.name();
	}
	@Override
	public T createValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< T, Void > context ) {
		return this.value;
	}
	@Override
	public T getTransientValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< T, Void > context ) {
		return this.value;
	}
}

class EnumBlank< T extends Enum< T > > extends edu.cmu.cs.dennisc.croquet.CascadeBlank< T > {
	private final Class<T> cls;
	public EnumBlank( Class<T> cls ) {
		super( java.util.UUID.fromString( "6598e548-592b-420f-8619-16abcd9bfc99" ) );
		this.cls = cls;
	}
	@Override
	protected void addFillIns() {
		for( T value : this.cls.getEnumConstants() ) {
			this.addFillIn( EnumConstantFillIn.getInstance( value ) );
		}
	}
}

class IntegerLiteralFillIn extends edu.cmu.cs.dennisc.croquet.CascadeFillIn< Integer, Void > {
	private static java.util.Map< Integer, IntegerLiteralFillIn > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static synchronized IntegerLiteralFillIn getInstance( Integer value ) {
		IntegerLiteralFillIn rv = map.get( value );
		if( rv != null ) {
			//pass
		} else {
			rv = new IntegerLiteralFillIn( value );
			map.put( value, rv );
		}
		return rv;
	}
	private Integer value;
	private IntegerLiteralFillIn( Integer value ) {
		super( java.util.UUID.fromString( "8ac33874-dd22-4440-b768-234d10d41cad" ) );
		this.value = value;
	}
	@Override
	public javax.swing.Icon getMenuItemIcon( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return null;
	}
	@Override
	public String getMenuItemText( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return Integer.toString( this.value );
	}
	@Override
	public Integer createValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return this.value;
	}
	@Override
	public Integer getTransientValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return this.value;
	}
}

class CustomIntegerFillIn extends edu.cmu.cs.dennisc.croquet.CascadeFillIn< Integer, Void > {
	private static class SingletonHolder {
		private static CustomIntegerFillIn instance = new CustomIntegerFillIn();
	}
	public static CustomIntegerFillIn getInstance() {
		return SingletonHolder.instance;
	}
	private CustomIntegerFillIn() {
		super( java.util.UUID.fromString( "abfa96df-32be-4a94-8f5d-030f173b77e9" ) );
	}
	@Override
	public javax.swing.Icon getMenuItemIcon( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return null;
	}
	@Override
	public String getMenuItemText( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return "custom integer...";
	}
	@Override
	public Integer createValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return 42;
	}
	@Override
	public Integer getTransientValue( edu.cmu.cs.dennisc.croquet.CascadeFillInContext< Integer, Void > context ) {
		return null;
	}
}

class IntegerBlank extends edu.cmu.cs.dennisc.croquet.CascadeBlank< Integer > {
	private static class SingletonHolder {
		private static IntegerBlank instance = new IntegerBlank();
	}
	public static IntegerBlank getInstance() {
		return SingletonHolder.instance;
	}
	private IntegerBlank() {
		super( java.util.UUID.fromString( "7705a77c-b5a9-4955-966d-0350bac1ade9" ) );
	}
	@Override
	protected void addFillIns() {
		for( Integer value : new int[] { 1, 2, 3, 4, 5 } ) {
			this.addFillIn( IntegerLiteralFillIn.getInstance( value ) );
		}
		this.addSeparator();
		this.addFillIn( CustomIntegerFillIn.getInstance() );
	}
}

enum ZodiacSigns {
	ARIES,
	TAURUS,
	GEMINI,
	CANCER,
	LEO,
	VIRGO, 
	LIBRA, 
	SCORPIO,
	SAGITARIUS,
	CAPRICORN,
	AQUARIUS, 
	PISCES
}

class MyCascadeOperation extends edu.cmu.cs.dennisc.croquet.CascadeOperation< Object > {
	private static class SingletonHolder {
		private static MyCascadeOperation instance = new MyCascadeOperation();
	}
	public static MyCascadeOperation getInstance() {
		return SingletonHolder.instance;
	}
	private MyCascadeOperation() {
		super( null, java.util.UUID.fromString( "2c0ba898-1f06-48ff-bc15-65f6f350484b" ), Object.class, 
				new EnumBlank( ZodiacSigns.class ),
				IntegerBlank.getInstance(),
				IntegerBlank.getInstance(),
				new EnumBlank( ZodiacSigns.class ),
				IntegerBlank.getInstance()
		);
	}
	@Override
	protected void localize() {
		super.localize();
		this.setName( "test cascade" );
	}
	@Override
	protected edu.cmu.cs.dennisc.croquet.Edit< edu.cmu.cs.dennisc.croquet.CascadeOperation< Object >> createEdit( final Object[] values ) {
		return new edu.cmu.cs.dennisc.croquet.Edit() {
			@Override
			public edu.cmu.cs.dennisc.croquet.Edit.Memento createMemento() {
				return null;
			}
			
			@Override
			protected void doOrRedoInternal( boolean isDo ) {
				edu.cmu.cs.dennisc.print.PrintUtilities.println( values );
			}
			@Override
			protected void undoInternal() {
			}
			@Override
			protected StringBuilder updatePresentation( StringBuilder rv, java.util.Locale locale ) {
				return null;
			}
		};
	}
}

class CascadePanel extends edu.cmu.cs.dennisc.croquet.BorderPanel {
	public CascadePanel() {
		this.setMinimumPreferredWidth( 640 );
		this.setMinimumPreferredHeight( 480 );
		this.addComponent( MyCascadeOperation.getInstance().createButton(), Constraint.PAGE_START );
	}
}

/**
 * @author Dennis Cosgrove
 */
public class TestCascade extends edu.cmu.cs.dennisc.croquet.Application {
	@Override
	protected edu.cmu.cs.dennisc.croquet.Component< ? > createContentPane() {
		return new CascadePanel();
	}
	@Override
	public edu.cmu.cs.dennisc.croquet.DropReceptor getDropReceptor( edu.cmu.cs.dennisc.croquet.DropSite dropSite ) {
		return null;
	}
	@Override
	protected void handleAbout( java.util.EventObject e ) {
	}
	@Override
	protected void handlePreferences( java.util.EventObject e ) {
	}
	@Override
	protected void handleQuit( java.util.EventObject e ) {
		System.exit( 0 );
	}
	@Override
	protected void handleWindowOpened( java.awt.event.WindowEvent e ) {
	}
	
	public static void main( String[] args ) {
		TestCascade application = new TestCascade();
		application.initialize( args );
		application.getFrame().pack();
		application.getFrame().setVisible( true );
	}
}
