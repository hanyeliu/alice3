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
package org.alice.ide.stencil;

import edu.cmu.cs.dennisc.java.util.logging.Logger;
import org.alice.ide.IDE;
import org.alice.ide.croquet.models.IdeDragModel;
import org.alice.ide.perspectives.ProjectPerspective;
import org.lgna.croquet.DropReceptor;
import org.lgna.croquet.history.DragStep;
import org.lgna.croquet.views.AbstractWindow;
import org.lgna.croquet.views.AwtComponentView;
import org.lgna.croquet.views.DragComponent;
import org.lgna.croquet.views.LayerStencil;
import org.lgna.project.ast.AbstractType;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.util.List;

/**
 * @author Dennis Cosgrove
 * 
 */
public class PotentialDropReceptorsStencil extends LayerStencil {

	private static final Stroke THIN_STROKE = new BasicStroke( 1.0f );
	private static final Stroke THICK_STROKE = new BasicStroke( 3.0f );

	private List<DropReceptor> holes = null;
	private DragComponent<?> potentialDragSource;
	private AwtComponentView<?> currentDropReceptorComponent;

	public PotentialDropReceptorsStencil( AbstractWindow<?> window ) {
		super( window, JLayeredPane.POPUP_LAYER + 1 );
	}

	public void handleDragStarted( DragStep dragAndDropContext ) {
		this.potentialDragSource = null;
		if( this.holes != null ) {
			this.repaint();
		}
	}

	public void handleDragEnteredDropReceptor( DragStep dragAndDropContext ) {
	}

	public void handleDragExitedDropReceptor( DragStep dragAndDropContext ) {
		this.currentDropReceptorComponent = null;
		if( this.holes != null ) {
			this.repaint();
		}
	}

	public void handleDragStopped( DragStep dragAndDropContext ) {
	}

	private boolean isFauxStencilDesired() {
		return IDE.getActiveInstance().isDragInProgress();
	}

	public void setDragInProgress( boolean isDragInProgress ) {
		this.currentDropReceptorComponent = null;
	}

	public void showStencilOver( DragComponent<?> potentialDragSource, final AbstractType<?, ?, ?> type ) {
		IDE ide = IDE.getActiveInstance();
		ProjectPerspective idePerspective = (ProjectPerspective)ide.getPerspective();
		List<DropReceptor> dropReceptors = idePerspective.createListOfPotentialDropReceptors( (IdeDragModel)potentialDragSource.getModel() );
		if( dropReceptors.size() > 0 ) {
			this.holes = dropReceptors;
			this.potentialDragSource = potentialDragSource;
			this.setStencilShowing( true );
		}
	}

	public void hideStencil() {
		this.setStencilShowing( false );
		this.holes = null;
		this.potentialDragSource = null;
	}

	@Override
	protected boolean contains( int x, int y, boolean superContains ) {
		return superContains;
	}

	@Override
	protected void paintComponentPrologue( Graphics2D g2 ) {
		if( this.holes != null ) {
			synchronized( this.holes ) {
				Area area = new Area( new Rectangle( 0, 0, getWidth(), getHeight() ) );
				if( this.currentDropReceptorComponent != null ) {
					g2.setPaint( new Color( 0, 0, 127, 95 ) );
				} else {
					g2.setPaint( new Color( 0, 0, 127, 127 ) );
				}

				Rectangle potentialDragSourceBounds;
				if( this.potentialDragSource != null ) {
					potentialDragSourceBounds = SwingUtilities.convertRectangle( this.potentialDragSource.getParent().getAwtComponent(), this.potentialDragSource.getBounds(), this.getAwtComponent() );
				} else {
					potentialDragSourceBounds = null;
				}

				if( isFauxStencilDesired() ) {
					for( DropReceptor dropReceptor : this.holes ) {
						AwtComponentView<?> component = (AwtComponentView<?>)dropReceptor;
						Rectangle holeBounds = SwingUtilities.convertRectangle( component.getParent().getAwtComponent(), component.getBounds(), this.getAwtComponent() );
						area.subtract( new Area( holeBounds ) );
					}

					if( potentialDragSourceBounds != null ) {
						area.subtract( new Area( potentialDragSourceBounds ) );
					}
					g2.fill( area );
				}

				g2.setStroke( THICK_STROKE );
				final int BUFFER = 6;
				for( DropReceptor dropReceptor : this.holes ) {
					AwtComponentView<?> component = (AwtComponentView<?>)dropReceptor;
					Rectangle holeBounds = SwingUtilities.convertRectangle( component.getParent().getAwtComponent(), component.getBounds(), this.getAwtComponent() );
					holeBounds.x -= BUFFER;
					holeBounds.y -= BUFFER;
					holeBounds.width += 2 * BUFFER;
					holeBounds.height += 2 * BUFFER;

					g2.setColor( new Color( 0, 0, 0 ) );
					g2.draw( holeBounds );
					if( this.currentDropReceptorComponent == component ) {
						g2.setColor( new Color( 0, 255, 0 ) );
						g2.setStroke( THIN_STROKE );
						g2.draw( holeBounds );
						if( this.currentDropReceptorComponent == component ) {
							g2.setColor( new Color( 0, 255, 0 ) );
							g2.setStroke( THIN_STROKE );
							g2.draw( holeBounds );
							g2.setStroke( THICK_STROKE );
							g2.setColor( new Color( 191, 255, 191, 63 ) );
							g2.fill( holeBounds );
						}
					}
				}
			}
		} else {
			Logger.severe( this );
		}
	}

	@Override
	protected void paintComponentEpilogue( Graphics2D g2 ) {
	}

	@Override
	protected void paintEpilogue( Graphics2D g2 ) {
	}

	@Override
	protected LayoutManager createLayoutManager( JPanel jPanel ) {
		return null;
	}
}
