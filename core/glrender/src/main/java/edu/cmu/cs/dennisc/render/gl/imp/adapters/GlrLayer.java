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

package edu.cmu.cs.dennisc.render.gl.imp.adapters;

import edu.cmu.cs.dennisc.java.util.Lists;
import edu.cmu.cs.dennisc.render.Graphics2D;
import edu.cmu.cs.dennisc.render.RenderTarget;
import edu.cmu.cs.dennisc.scenegraph.AbstractCamera;
import edu.cmu.cs.dennisc.scenegraph.Graphic;
import edu.cmu.cs.dennisc.scenegraph.Layer;
import edu.cmu.cs.dennisc.scenegraph.event.GraphicAddedEvent;
import edu.cmu.cs.dennisc.scenegraph.event.GraphicRemovedEvent;

import java.awt.Rectangle;
import java.util.List;

/**
 * @author Dennis Cosgrove
 */
public class GlrLayer extends GlrElement<Layer> {
	/*package-private*/static void handleGraphicAdded( GraphicAddedEvent e ) {
		GlrLayer layerAdapter = AdapterFactory.getAdapterFor( e.getTypedSource() );
		GlrGraphic<? extends Graphic> graphicAdapter = AdapterFactory.getAdapterFor( e.getChild() );
		layerAdapter.handleGraphicAdded( graphicAdapter );
	}

	/*package-private*/static void handleGraphicRemoved( GraphicRemovedEvent e ) {
		GlrLayer layerAdapter = AdapterFactory.getAdapterFor( e.getTypedSource() );
		GlrGraphic<? extends Graphic> graphicAdapter = AdapterFactory.getAdapterFor( e.getChild() );
		layerAdapter.handleGraphicRemoved( graphicAdapter );
	}

	private void handleGraphicAdded( GlrGraphic<? extends Graphic> graphicAdapter ) {
		synchronized( this.activeGlrGraphics ) {
			this.activeGlrGraphics.add( graphicAdapter );
		}
	}

	private void handleGraphicRemoved( GlrGraphic<? extends Graphic> graphicAdapter ) {
		synchronized( this.forgetGlrGraphics ) {
			this.forgetGlrGraphics.add( graphicAdapter );
		}
	}

	@Override
	public void initialize( Layer sgLayer ) {
		super.initialize( sgLayer );
		for( Graphic sgGraphic : sgLayer.getGraphics() ) {
			GlrGraphic<?> glrGraphic = AdapterFactory.getAdapterFor( sgGraphic );
			handleGraphicAdded( glrGraphic );
		}
	}

	/* package-private */void render( Graphics2D g2, RenderTarget renderTarget, Rectangle actualViewport, AbstractCamera camera ) {
		synchronized( this.forgetGlrGraphics ) {
			for( GlrGraphic<? extends Graphic> graphicAdapter : this.forgetGlrGraphics ) {
				graphicAdapter.forget( g2 );
				synchronized( this.activeGlrGraphics ) {
					this.activeGlrGraphics.remove( graphicAdapter );
				}
			}
			this.forgetGlrGraphics.clear();
		}
		synchronized( this.activeGlrGraphics ) {
			for( GlrGraphic<? extends Graphic> graphicAdapter : this.activeGlrGraphics ) {
				graphicAdapter.render( g2, renderTarget, actualViewport, camera );
			}
		}
	}

	private final List<GlrGraphic<?>> activeGlrGraphics = Lists.newLinkedList();
	private final List<GlrGraphic<?>> forgetGlrGraphics = Lists.newLinkedList();
}
