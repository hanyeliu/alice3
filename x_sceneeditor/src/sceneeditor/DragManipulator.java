/**
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

package sceneeditor;

/**
 * @author David Culyba
 */
public abstract class DragManipulator {
	
	protected edu.cmu.cs.dennisc.scenegraph.Transformable manipulatedTransformable = null;
	
	protected void setManipulatedTransformable( edu.cmu.cs.dennisc.scenegraph.Transformable manipulatedTransformable)
	{
		this.manipulatedTransformable = manipulatedTransformable;
	}
	
	protected abstract void startManipulator( java.awt.Point current );
	
	protected abstract void updateManipulator( java.awt.Point current, int xDeltaSincePrevious, int yDeltaSincePrevious );
	
	protected abstract void endManipulator( java.awt.Point current );
	

}
