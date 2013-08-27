/**
 * Copyright (c) 2006-2012, Carnegie Mellon University. All rights reserved.
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
package org.alice.stageide.gallerybrowser.uri.merge;

import org.alice.stageide.gallerybrowser.uri.merge.data.DifferentImplementation;
import org.alice.stageide.gallerybrowser.uri.merge.data.DifferentSignature;
import org.alice.stageide.gallerybrowser.uri.merge.data.Identical;
import org.alice.stageide.gallerybrowser.uri.merge.data.ImportOnly;
import org.alice.stageide.gallerybrowser.uri.merge.data.ProjectOnly;

/**
 * @author Dennis Cosgrove
 */
public abstract class AddMembersComposite<V extends org.alice.stageide.gallerybrowser.uri.merge.views.AddMembersView, M extends org.lgna.project.ast.Member> extends org.lgna.croquet.ToolPaletteCoreComposite<V> {
	private final java.net.URI uriForDescriptionPurposesOnly;
	private final java.util.List<M> unusedProjectMembers;
	private final java.util.List<ImportOnly<M>> importOnlys = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
	private final java.util.List<DifferentSignature<M>> differentSignatures = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
	private final java.util.List<DifferentImplementation<M>> differentImplementations = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
	private final java.util.List<Identical<M>> identicals = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
	private java.util.List<ProjectOnly<M>> projectOnlys;

	private final edu.cmu.cs.dennisc.java.util.InitializingIfAbsentMap<M, MemberPopupCoreComposite> mapMemberToPopupComposite = edu.cmu.cs.dennisc.java.util.Collections.newInitializingIfAbsentHashMap();

	private final org.lgna.croquet.PlainStringValue addHeader = this.createStringValue( this.createKey( "addHeader" ) );
	private final org.lgna.croquet.PlainStringValue existingHeader = this.createStringValue( this.createKey( "existingHeader" ) );
	private final org.lgna.croquet.PlainStringValue resultHeader = this.createStringValue( this.createKey( "resultHeader" ) );

	public AddMembersComposite( java.util.UUID migrationId, java.net.URI uriForDescriptionPurposesOnly, java.util.List<M> projectMembers ) {
		super( migrationId, org.lgna.croquet.Application.INHERIT_GROUP, true );
		this.uriForDescriptionPurposesOnly = uriForDescriptionPurposesOnly;
		this.unusedProjectMembers = projectMembers;
	}

	@Override
	protected String modifyLocalizedText( org.lgna.croquet.Element element, String localizedText ) {
		String rv = super.modifyLocalizedText( element, localizedText );
		if( rv != null ) {
			if( element == this.addHeader ) {
				java.io.File file = new java.io.File( this.uriForDescriptionPurposesOnly );
				rv = rv.replaceAll( "</filename/>", file.getName() );
			}
		}
		return rv;
	}

	public MemberPopupCoreComposite getPopupMemberFor( M member ) {
		return this.mapMemberToPopupComposite.getInitializingIfAbsent( member, new edu.cmu.cs.dennisc.java.util.InitializingIfAbsentMap.Initializer<M, MemberPopupCoreComposite>() {
			public org.alice.stageide.gallerybrowser.uri.merge.MemberPopupCoreComposite initialize( M key ) {
				return new MemberPopupCoreComposite( key );
			}
		} );
	}

	@Override
	protected org.lgna.croquet.components.ScrollPane createScrollPaneIfDesired() {
		return new org.lgna.croquet.components.ScrollPane();
	}

	public void addImportOnlyMember( M method ) {
		this.importOnlys.add( new ImportOnly<M>( method ) );
	}

	public void addDifferentSignatureMembers( M projectMember, M importMember ) {
		this.differentSignatures.add( new DifferentSignature<M>( projectMember, importMember ) );
		this.unusedProjectMembers.remove( projectMember );
	}

	public void addDifferentImplementationMembers( M projectMember, M importMember ) {
		this.differentImplementations.add( new DifferentImplementation<M>( projectMember, importMember ) );
		this.unusedProjectMembers.remove( projectMember );
	}

	public void addIdenticalMembers( M projectMember, M importMember ) {
		this.identicals.add( new Identical<M>( projectMember, importMember ) );
		this.unusedProjectMembers.remove( projectMember );
	}

	public void reifyProjectOnly() {
		this.projectOnlys = edu.cmu.cs.dennisc.java.util.Collections.newLinkedList();
		for( M method : this.unusedProjectMembers ) {
			this.projectOnlys.add( new ProjectOnly<M>( method ) );
		}
	}

	public java.util.List<ImportOnly<M>> getImportOnlys() {
		return this.importOnlys;
	}

	public java.util.List<DifferentSignature<M>> getDifferentSignatures() {
		return this.differentSignatures;
	}

	public java.util.List<DifferentImplementation<M>> getDifferentImplementations() {
		return this.differentImplementations;
	}

	public java.util.List<Identical<M>> getIdenticals() {
		return this.identicals;
	}

	public java.util.List<ProjectOnly<M>> getProjectOnlys() {
		return this.projectOnlys;
	}

	public org.lgna.croquet.PlainStringValue getAddHeader() {
		return this.addHeader;
	}

	public org.lgna.croquet.PlainStringValue getExistingHeader() {
		return this.existingHeader;
	}

	public org.lgna.croquet.PlainStringValue getResultHeader() {
		return this.resultHeader;
	}

	//	public int getActionItemCount() {
	//		return this.differentSignatures.size() + this.differentImplementations.size();
	//	}
	//
	//	public int getAddCount() {
	//		return this.importOnlys.size() + this.differentSignatures.size() + this.differentImplementations.size();
	//	}
	//
	//	public int getKeepCount() {
	//		return this.differentImplementations.size() + this.differentImplementations.size() + this.identicals.size() + this.projectOnlys.size();
	//	}

	public int getTotalCount() {
		assert this.projectOnlys != null : this;
		return this.importOnlys.size() + this.differentSignatures.size() + this.differentImplementations.size() + this.identicals.size() + this.projectOnlys.size();
	}

	public void appendStatusPreRejectorCheck( StringBuffer sb, org.lgna.croquet.history.CompletionStep<?> step ) {
		for( DifferentSignature<M> differentSignatureMember : this.differentSignatures ) {
			differentSignatureMember.appendStatusPreRejectorCheck( sb, step );
		}
		for( DifferentImplementation<M> differentImplementationMember : this.differentImplementations ) {
			differentImplementationMember.appendStatusPreRejectorCheck( sb, step );
		}
	}
}
