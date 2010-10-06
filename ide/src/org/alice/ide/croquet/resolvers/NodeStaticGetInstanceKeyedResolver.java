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

package org.alice.ide.croquet.resolvers;

/**
 * @author Dennis Cosgrove
 */
public class NodeStaticGetInstanceKeyedResolver<T> extends edu.cmu.cs.dennisc.croquet.StaticGetInstanceKeyedResolver< T > implements edu.cmu.cs.dennisc.croquet.RetargetableResolver< T > {
	private edu.cmu.cs.dennisc.alice.ast.Node node;
	private Class< ? extends edu.cmu.cs.dennisc.alice.ast.Node > parameterType;
	
	public NodeStaticGetInstanceKeyedResolver( T instance, edu.cmu.cs.dennisc.alice.ast.Node node, Class< ? extends edu.cmu.cs.dennisc.alice.ast.Node > parameterType ) {
		super( instance );
		this.node = node;
		this.parameterType = parameterType;
	}
	public NodeStaticGetInstanceKeyedResolver( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
		super( binaryDecoder );
	}

	public void retarget( edu.cmu.cs.dennisc.croquet.Retargeter retargeter ) {
		Object[] arguments = this.getArguments();
		assert arguments != null : this;
		assert arguments.length == 1;
//		edu.cmu.cs.dennisc.print.PrintUtilities.println( "pre:", arguments[ 0 ] );
		arguments[ 0 ] = retargeter.retarget( arguments[ 0 ] );
//		edu.cmu.cs.dennisc.print.PrintUtilities.println( "pst:", arguments[ 0 ] );
	}
	@Override
	protected Class< ? >[] decodeParameterTypes( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
		Class<?> parameterType = this.decodeClass( binaryDecoder );
		return new Class[] { parameterType };
	}
	@Override
	protected void encodeParameterTypes( edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder ) {
		this.encodeClass( binaryEncoder, this.parameterType );
	}
	@Override
	protected Object[] decodeArguments( edu.cmu.cs.dennisc.codec.BinaryDecoder binaryDecoder ) {
		final java.util.UUID id = binaryDecoder.decodeId();
		org.alice.ide.IDE ide = org.alice.ide.IDE.getSingleton();
		return new Object[] { edu.cmu.cs.dennisc.alice.project.ProjectUtilities.lookupNode( ide.getProject(), id ) };
	}
	@Override
	protected void encodeArguments( edu.cmu.cs.dennisc.codec.BinaryEncoder binaryEncoder ) {
		binaryEncoder.encode( this.node.getUUID() );
	}
}
