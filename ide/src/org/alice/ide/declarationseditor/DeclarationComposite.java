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

package org.alice.ide.declarationseditor;

/**
 * @author Dennis Cosgrove
 */
public class DeclarationComposite extends org.lgna.croquet.TabComposite< org.alice.ide.declarationseditor.components.DeclarationView > {
	private static java.util.Map< org.lgna.project.ast.AbstractDeclaration, DeclarationComposite > map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();
	public static synchronized DeclarationComposite getInstance( org.lgna.project.ast.AbstractDeclaration declaration ) {
		if( declaration != null ) {
			DeclarationComposite rv = map.get( declaration );
			if( rv != null ) {
				//pass
			} else {
				rv = new DeclarationComposite( declaration );
				map.put( declaration, rv );
			}
			return rv;
		} else {
			return null;
		}
	}
	private final org.lgna.project.ast.AbstractDeclaration declaration;
	public DeclarationComposite( org.lgna.project.ast.AbstractDeclaration declaration ) {
		super( java.util.UUID.fromString( "291f2cd6-893e-4c7f-a6fd-16c718576d7a" ) );
		this.declaration = declaration;
	}
	@Override
	public java.util.UUID getTabId() {
		return this.declaration.getId();
	}
	public org.lgna.project.ast.AbstractDeclaration getDeclaration() {
		return this.declaration;
	}
	
	@Override
	public boolean contains( org.lgna.croquet.Model model ) {
		edu.cmu.cs.dennisc.java.util.logging.Logger.todo( model );
		return false;
	}
	@Override
	public boolean isCloseable() {
		return this.declaration instanceof org.lgna.project.ast.AbstractCode;
	}
	@Override
	public void customizeTitleComponent( org.lgna.croquet.BooleanState booleanState, org.lgna.croquet.components.BooleanStateButton< ? > button ) {
		super.customizeTitleComponent( booleanState, button );
		if( this.declaration instanceof org.lgna.project.ast.AbstractCode ) {
			button.scaleFont( 1.2f );
		} else {
			button.scaleFont( 1.6f );
		}
	}
	
	@Override
	public String getTitleText() {
		return this.declaration.getName();
	}
	@Override
	public org.lgna.croquet.components.ScrollPane createScrollPane() {
		return null;
	}
	@Override
	protected org.alice.ide.declarationseditor.components.DeclarationView createView() {
		if( this.declaration instanceof org.lgna.project.ast.AbstractCode ) {
			return new org.alice.ide.declarationseditor.code.components.CodeDeclarationView( this );
		} else if( this.declaration instanceof org.lgna.project.ast.NamedUserType ){
			return new org.alice.ide.declarationseditor.type.components.TypeDeclarationView( this );
		} else {
			throw new RuntimeException( "todo" );
		}
	}
//	public java.util.List< org.lgna.croquet.DropReceptor > getPotentialDropReceptors() {
//		return null;
//	}
}
