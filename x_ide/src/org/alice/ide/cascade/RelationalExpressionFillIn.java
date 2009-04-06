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
package org.alice.ide.cascade;

/**
 * @author Dennis Cosgrove
 */
class RelationalInfixExpressionOperatorFillIn extends InfixExpressionOperatorFillIn< edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression, edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator > {
	private static edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression createRelationalInfixExpression( edu.cmu.cs.dennisc.alice.ast.AbstractType operandType ) {
		edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression rv = new edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression();
		rv.leftOperandType.setValue( operandType );
		rv.rightOperandType.setValue( operandType );
		return rv;
	}
	public RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator operator, edu.cmu.cs.dennisc.alice.ast.AbstractType operandType ) {
		super( createRelationalInfixExpression( operandType ), Double.class, operator, Double.class );
	}
}
/**
 * @author Dennis Cosgrove
 */
class RelationalInfixExpressionOperatorBlank extends cascade.Blank {
	private edu.cmu.cs.dennisc.alice.ast.AbstractType operandType;
	public RelationalInfixExpressionOperatorBlank( edu.cmu.cs.dennisc.alice.ast.AbstractType operandType ) {
		this.operandType = operandType;
	}
	@Override
	protected void addChildren() {
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.EQUALS, this.operandType ) );
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.NOT_EQUALS, this.operandType ) );
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.LESS, this.operandType ) );
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.LESS_EQUALS, this.operandType ) );
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.GREATER, this.operandType ) );
		this.addChild( new RelationalInfixExpressionOperatorFillIn( edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression.Operator.GREATER_EQUALS, this.operandType ) );
	}
}
/**
 * @author Dennis Cosgrove
 */
public class RelationalExpressionFillIn extends InfixExpressionFillIn< edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression > {
	private edu.cmu.cs.dennisc.alice.ast.AbstractType type;
	public RelationalExpressionFillIn( String menuText, Class<?> cls ) {
		super( menuText );
		type = edu.cmu.cs.dennisc.alice.ast.TypeDeclaredInJava.get( cls );
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.AbstractType getLeftOperandType() {
		return this.type;
	}
	@Override
	protected cascade.Blank createOperatorBlank() {
		return new RelationalInfixExpressionOperatorBlank( this.type );
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.AbstractType getRightOperandType() {
		return this.type;
	}
	@Override
	protected edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression createValue( edu.cmu.cs.dennisc.alice.ast.Expression left, Object operatorContainingExpression, edu.cmu.cs.dennisc.alice.ast.Expression right ) {
		edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression aie = (edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression)operatorContainingExpression;
		return new edu.cmu.cs.dennisc.alice.ast.RelationalInfixExpression( left, aie.operator.getValue(), right, this.type, this.type );
	}
}
