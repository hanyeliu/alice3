/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
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
 *******************************************************************************/
package org.alice.ide.member;

import edu.cmu.cs.dennisc.java.util.Lists;
import edu.cmu.cs.dennisc.property.event.ListPropertyEvent;
import edu.cmu.cs.dennisc.property.event.ListPropertyListener;
import edu.cmu.cs.dennisc.property.event.SimplifiedListPropertyAdapter;
import org.alice.ide.common.TypeIcon;
import org.alice.ide.croquet.models.ui.preferences.IsEmphasizingClassesState;
import org.alice.ide.member.views.UserMethodsSubView;
import org.lgna.croquet.Operation;
import org.lgna.project.ast.AbstractMethod;
import org.lgna.project.ast.ManagementLevel;
import org.lgna.project.ast.NamedUserType;
import org.lgna.project.ast.UserField;
import org.lgna.project.ast.UserMethod;

import java.util.List;
import java.util.UUID;

/**
 * @author Dennis Cosgrove
 */
public abstract class UserMethodsSubComposite extends MethodsSubComposite {
	private final NamedUserType type;
	private final Operation addMethodOperation;

	private final ListPropertyListener<UserMethod> methodPropertyListener = new SimplifiedListPropertyAdapter<UserMethod>() {
		@Override
		protected void changing( ListPropertyEvent<UserMethod> e ) {
		}

		@Override
		protected void changed( ListPropertyEvent<UserMethod> e ) {
			getView().refreshLater();
		}
	};
	private final ListPropertyListener<UserField> fieldPropertyListener = new SimplifiedListPropertyAdapter<UserField>() {
		@Override
		protected void changing( ListPropertyEvent<UserField> e ) {
		}

		@Override
		protected void changed( ListPropertyEvent<UserField> e ) {
			getView().refreshLater();
		}
	};

	public UserMethodsSubComposite( UUID migrationId, NamedUserType type, Operation addMethodOperation ) {
		super( migrationId, true );
		this.type = type;
		this.addMethodOperation = addMethodOperation;
		this.getOuterComposite().getIsExpandedState().setIconForBothTrueAndFalse( new TypeIcon( this.type ) );

		//todo: move to handlePreActivation/handlePostDeactivation
		type.methods.addListPropertyListener( this.methodPropertyListener );
		type.fields.addListPropertyListener( this.fieldPropertyListener );
	}

	public final Operation getAddMethodOperation() {
		return this.addMethodOperation;
	}

	protected abstract boolean isAcceptable( AbstractMethod method );

	protected abstract AbstractMethod getGetterOrSetter( UserField field );

	@Override
	protected boolean isMethodCountDesired( boolean isExpanded, int methodCount ) {
		return true;
	}

	@Override
	public List<? extends AbstractMethod> getMethods() {
		List<AbstractMethod> rv = Lists.newLinkedList();
		for( UserMethod method : this.type.getDeclaredMethods() ) {
			ManagementLevel managementLevel = method.managementLevel.getValue();
			if( ( managementLevel == ManagementLevel.NONE ) || ( managementLevel == ManagementLevel.GENERATED ) ) {
				if( this.isAcceptable( method ) ) {
					rv.add( method );
				}
			}
		}
		for( UserField field : this.type.getDeclaredFields() ) {
			AbstractMethod method = this.getGetterOrSetter( field );
			if( method != null ) {
				rv.add( method );
			}
		}
		return rv;
	}

	@Override
	protected UserMethodsSubView createView() {
		return new UserMethodsSubView( this );
	}

	public boolean isRelevant() {
		if( IsEmphasizingClassesState.getInstance().getValue() ) {
			return true;
		} else {
			return this.getMethods().size() > 0;
		}
	}

	//	@Override
	//	public void handlePreActivation() {
	//		super.handlePreActivation();
	//		type.methods.addListPropertyListener( this.methodPropertyListener );
	//	}
	//
	//	@Override
	//	public void handlePostDeactivation() {
	//		type.methods.removeListPropertyListener( this.methodPropertyListener );
	//		super.handlePostDeactivation();
	//	}
}
