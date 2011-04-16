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
public class StringStateContext extends StateContext<StringState> {
	public static abstract class DocumentEvent extends ModelEvent<StringStateContext> {
		private javax.swing.event.DocumentEvent documentEvent;
		public DocumentEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
			super( binaryDecoder );
		}
		private DocumentEvent( javax.swing.event.DocumentEvent documentEvent ) {
			this.documentEvent = documentEvent;
		}
		public javax.swing.event.DocumentEvent getDocumentEvent() {
			return this.documentEvent;
		}
		@Override
		public State getState() {
			return null;
		}
	}
	public static class DocumentChangeEvent extends DocumentEvent {
		public DocumentChangeEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
			super( binaryDecoder );
		}
		private DocumentChangeEvent( javax.swing.event.DocumentEvent documentEvent ) {
			super( documentEvent );
		}
	}
	public static class DocumentInsertEvent extends DocumentEvent {
		public DocumentInsertEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
			super( binaryDecoder );
		}
		private DocumentInsertEvent( javax.swing.event.DocumentEvent documentEvent ) {
			super( documentEvent );
		}
	}
	public static class DocumentRemoveEvent extends DocumentEvent {
		public DocumentRemoveEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
			super( binaryDecoder );
		}
		private DocumentRemoveEvent( javax.swing.event.DocumentEvent documentEvent ) {
			super( documentEvent );
		}
	}
	
	
//	private static class FocusEvent extends ModelEvent< StringStateContext > {
//		private java.awt.event.FocusEvent focusEvent;
//		public FocusEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
//			super( binaryDecoder );
//		}
//		private FocusEvent( java.awt.event.FocusEvent focusEvent ) {
//			this.focusEvent = focusEvent;
//		}
//		public java.awt.event.FocusEvent getFocusEvent() {
//			return this.focusEvent;
//		}
//		@Override
//		public State getState() {
//			return null;
//		}
//	}
//	public static class FocusGainedEvent extends FocusEvent {
//		public FocusGainedEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
//			super( binaryDecoder );
//		}
//		private FocusGainedEvent( java.awt.event.FocusEvent focusEvent ) {
//			super( focusEvent );
//		}
//	}
//	public static class FocusLostEvent extends FocusEvent {
//		public FocusLostEvent( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
//			super( binaryDecoder );
//		}
//		private FocusLostEvent( java.awt.event.FocusEvent focusEvent ) {
//			super( focusEvent );
//		}
//	}
	
	private String previousValue;
	private String nextValue;
	/*package-private*/ StringStateContext( StringState stringState, java.util.EventObject e, ViewController< ?,? > viewController, String previousValue ) {
		super( stringState, e, viewController );
		this.previousValue = previousValue;
	}
	public StringStateContext( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
		super( binaryDecoder );
	}

//	/*package-private*/ void handleFocusGained( java.awt.event.FocusEvent e ) {
//		this.addChild( new FocusGainedEvent( e ) );
//	}
//	/*package-private*/ void handleFocusLost( java.awt.event.FocusEvent e ) {
//		this.addChild( new FocusLostEvent( e ) );
//	}
	/*package-private*/ void handleDocumentEvent( javax.swing.event.DocumentEvent e, String nextValue ) {
		javax.swing.event.DocumentEvent.EventType eventType = e.getType();
		DocumentEvent documentEvent;
		if( eventType == javax.swing.event.DocumentEvent.EventType.CHANGE ) {
			documentEvent = new DocumentChangeEvent( e );
		} else if( eventType == javax.swing.event.DocumentEvent.EventType.INSERT ) {
			documentEvent = new DocumentInsertEvent( e );
		} else if( eventType == javax.swing.event.DocumentEvent.EventType.REMOVE ) {
			documentEvent = new DocumentRemoveEvent( e );
		} else {
			documentEvent = null;
		}
		if( documentEvent != null ) {
			this.addChild( documentEvent );
		}
		this.nextValue = nextValue;
	}
	/*package-private*/ void handlePop() {
		org.lgna.croquet.steps.TransactionManager.addStringStateChangeStep( this.getModel() );
		this.commitAndInvokeDo( new StringStateEdit( this.previousValue, this.nextValue ) );
	}
	
	
}
