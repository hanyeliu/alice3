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
package org.alice.ide.ast.declaration;

import edu.cmu.cs.dennisc.javax.swing.UIManagerUtilities;
import org.alice.ide.IDE;
import org.alice.ide.ProjectDocumentFrame;
import org.alice.ide.croquet.edits.ast.DeclareMethodEdit;
import org.alice.ide.croquet.models.ui.preferences.IsEmphasizingClassesState;
import org.alice.ide.members.MembersComposite;
import org.alice.stageide.icons.PlusIconFactory;
import org.lgna.croquet.OwnedByCompositeOperationSubKey;
import org.lgna.croquet.edits.Edit;
import org.lgna.croquet.history.CompletionStep;
import org.lgna.project.ast.AstUtilities;
import org.lgna.project.ast.StaticAnalysisUtilities;
import org.lgna.project.ast.UserMethod;
import org.lgna.project.ast.UserType;

import java.awt.Dimension;
import java.util.UUID;

/**
 * @author Dennis Cosgrove
 */
public abstract class AddMethodComposite extends DeclarationLikeSubstanceComposite<UserMethod> {
	private final UserType<?> declaringType;

	public AddMethodComposite( UUID migrationId, Details details, UserType<?> declaringType ) {
		super( migrationId, details );
		ProjectDocumentFrame projectDocumentFrame = IDE.getActiveInstance().getDocumentFrame();
		if( IsEmphasizingClassesState.getInstance().getValue() ) {
			this.getLaunchOperation().addContextFactory( projectDocumentFrame.getDeclarationsEditorComposite().getTabState() );
		} else {
			this.getLaunchOperation().addContextFactory( projectDocumentFrame.getInstanceFactoryState() );
			this.getLaunchOperation().addContextFactory( MembersComposite.getInstance().getTabState() );
		}
		this.declaringType = declaringType;
	}

	@Override
	protected void localize() {
		super.localize();
		int size = UIManagerUtilities.getDefaultFontSize() + 4;
		this.getLaunchOperation().setSmallIcon( PlusIconFactory.getInstance().getIcon( new Dimension( size, size ) ) );
	}

	@Override
	public String modifyNameIfNecessary( OwnedByCompositeOperationSubKey key, String text ) {
		text = super.modifyNameIfNecessary( key, text );
		if( text != null ) {
			String declaringTypeName;
			if( this.declaringType != null ) {
				declaringTypeName = this.declaringType.getName();
			} else {
				declaringTypeName = "";
			}
			text = text.replace( "</declaringType/>", declaringTypeName );
		}
		return text;
	}

	@Override
	public UserType<?> getDeclaringType() {
		return this.declaringType;
	}

	@Override
	public UserMethod getPreviewValue() {
		return AstUtilities.createMethod( this.getDeclarationLikeSubstanceName(), this.getValueType() );
	}

	@Override
	protected Edit createEdit( CompletionStep<?> completionStep ) {
		return new DeclareMethodEdit( completionStep, this.getDeclaringType(), this.getDeclarationLikeSubstanceName(), this.getValueType() );
	}

	@Override
	protected boolean isNameAvailable( String name ) {
		return StaticAnalysisUtilities.isAvailableMethodName( name, this.getDeclaringType() );
	}
}
