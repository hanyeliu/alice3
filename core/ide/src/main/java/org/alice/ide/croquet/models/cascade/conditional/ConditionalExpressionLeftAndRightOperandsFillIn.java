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

package org.alice.ide.croquet.models.cascade.conditional;

/**
 * @author Dennis Cosgrove
 */
public class ConditionalExpressionLeftAndRightOperandsFillIn extends org.alice.ide.croquet.models.cascade.ExpressionFillInWithExpressionBlanks<org.lgna.project.ast.ConditionalInfixExpression> {
	private static java.util.Map<org.lgna.project.ast.ConditionalInfixExpression.Operator, ConditionalExpressionLeftAndRightOperandsFillIn> map = edu.cmu.cs.dennisc.java.util.Collections.newHashMap();

	public static ConditionalExpressionLeftAndRightOperandsFillIn getInstance( org.lgna.project.ast.ConditionalInfixExpression.Operator operator ) {
		synchronized( map ) {
			ConditionalExpressionLeftAndRightOperandsFillIn rv = map.get( operator );
			if( rv != null ) {
				//pass
			} else {
				rv = new ConditionalExpressionLeftAndRightOperandsFillIn( operator );
				map.put( operator, rv );
			}
			return rv;
		}
	}

	private final org.lgna.project.ast.ConditionalInfixExpression transientValue;

	private ConditionalExpressionLeftAndRightOperandsFillIn( org.lgna.project.ast.ConditionalInfixExpression.Operator operator ) {
		super( java.util.UUID.fromString( "f7faea38-4071-4918-bea0-98971ddbd7a7" ), org.alice.ide.croquet.models.cascade.ExpressionBlank.createBlanks( Boolean.class, Boolean.class ) );
		this.transientValue = org.alice.ide.ast.IncompleteAstUtilities.createIncompleteConditionalInfixExpression( operator );
	}

	@Override
	protected org.lgna.project.ast.ConditionalInfixExpression createValue( org.lgna.project.ast.Expression[] expressions ) {
		assert expressions.length == 2;
		return new org.lgna.project.ast.ConditionalInfixExpression( expressions[ 0 ], this.transientValue.operator.getValue(), expressions[ 1 ] );
	}

	@Override
	public org.lgna.project.ast.ConditionalInfixExpression getTransientValue( org.lgna.croquet.imp.cascade.ItemNode<? super org.lgna.project.ast.ConditionalInfixExpression, org.lgna.project.ast.Expression> step ) {
		return this.transientValue;
	}
}
