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
package org.alice.ide.templates;

/**
 * @author Dennis Cosgrove
 */
public abstract class ExpressionTemplate extends org.alice.ide.common.ExpressionCreatorPane {
	private edu.cmu.cs.dennisc.alice.ast.Expression expression;
	public ExpressionTemplate( edu.cmu.cs.dennisc.alice.ast.Expression expression ) {
		this.expression = expression;
		this.add( getIDE().getTemplatesFactory().createComponent( this.expression ) );
		this.setBackground( org.alice.ide.IDE.getColorForASTInstance( expression ) );
		this.setDragAndDropOperation( new org.alice.ide.operations.DefaultDragAndDropOperation() );
		this.setPopupOperation( new org.alice.ide.operations.AbstractActionOperation() {
			public void perform( zoot.ActionContext actionContext ) {
				actionContext.cancel();
			}
		} );
	}
	protected edu.cmu.cs.dennisc.alice.ast.Expression getExpression() {
		return this.expression;
	}
	@Override
	public edu.cmu.cs.dennisc.alice.ast.AbstractType getExpressionType() {
		return this.expression.getType();
	}
	@Override
	protected boolean isPressed() {
		return false;
	}
	@Override
	protected boolean isKnurlDesired() {
		return true;
	}
}
