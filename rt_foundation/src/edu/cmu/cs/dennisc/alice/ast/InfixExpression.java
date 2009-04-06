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
package edu.cmu.cs.dennisc.alice.ast;

/**
 * @author Dennis Cosgrove
 */
public abstract class InfixExpression< E > extends Expression {
	public ExpressionProperty leftOperand = new ExpressionProperty( this ) {
		@Override
		public AbstractType getExpressionType() {
			return InfixExpression.this.getLeftOperandType();
		}
	};
	public edu.cmu.cs.dennisc.property.InstanceProperty< E > operator = new edu.cmu.cs.dennisc.property.InstanceProperty< E >( this, null );
	public ExpressionProperty rightOperand = new ExpressionProperty( this ) {
		@Override
		public AbstractType getExpressionType() {
			return InfixExpression.this.getRightOperandType();
		}
	};
	public InfixExpression() {
	}
	public InfixExpression( Expression leftOperand, E operator, Expression rightOperand ) {
		this.leftOperand.setValue( leftOperand );
		this.operator.setValue( operator );
		this.rightOperand.setValue( rightOperand );
	}
	protected abstract AbstractType getLeftOperandType();
	protected abstract AbstractType getRightOperandType();
}
