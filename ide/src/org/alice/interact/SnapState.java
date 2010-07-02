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

package org.alice.interact;

import org.alice.app.ProjectApplication;

import edu.cmu.cs.dennisc.croquet.BooleanState;
import edu.cmu.cs.dennisc.math.AngleInDegrees;

import edu.cmu.cs.dennisc.math.Angle;

public class SnapState 
{
	public static Integer[] ANGLE_SNAP_OPTIONS = {new Integer(15), new Integer(30), new Integer(45), new Integer(60), new Integer(90), new Integer(120), new Integer(180)};
	
	
	
	private BooleanState isSnapEnabled = new BooleanState( ProjectApplication.IDE_GROUP, java.util.UUID.fromString( "c4db1a3d-9d27-4c21-971d-78059b37abed" ), false, "Use snap" );
	private BooleanState isSnapToGroundEnabled = new BooleanState( ProjectApplication.IDE_GROUP, java.util.UUID.fromString( "46b05c1f-b980-45c2-a587-4000400f7add" ), true, "Snap to ground" );
	private BooleanState isSnapToGridEnabled = new BooleanState( ProjectApplication.IDE_GROUP, java.util.UUID.fromString( "60d5a5be-e1ec-4932-be62-f90e9bad22c7" ), true, "Snap to grid" );
	private BooleanState isRotationSnapEnabled = new BooleanState( ProjectApplication.IDE_GROUP, java.util.UUID.fromString( "6933d462-d5c5-4ff6-9918-240511d2c731" ), true, "Snap rotation" );
	private double gridSpacing = .5d;
	private BooleanState showSnapGrid = new BooleanState( ProjectApplication.IDE_GROUP, java.util.UUID.fromString( "6537de4c-f4e9-475d-86ae-5d1ca873923e" ), true, "Show snap grid" );
	private Angle rotationSnapAngle = new AngleInDegrees(45); //In degrees
	
	
	public SnapState()
	{
	}
	
	public static Integer getAngleOptionForAngle(int angle)
	{
		for (Integer angleOption : ANGLE_SNAP_OPTIONS)
		{
			if (angleOption.equals(angle))
			{
				return angleOption;
			}
		}
		return null;
	}
	
	public BooleanState getIsSnapEnabledState()
	{
		return this.isSnapEnabled;
	}
	
	public BooleanState getIsSnapToGroundEnabledState()
	{
		return this.isSnapToGroundEnabled;
	}
	
	public BooleanState getIsSnapToGridEnabledState()
	{
		return this.isSnapToGridEnabled;
	}
	
	public BooleanState getIsRotationSnapEnabledState()
	{
		return this.isRotationSnapEnabled;
	}
	
	public BooleanState getShowSnapGridState()
	{
		return this.showSnapGrid;
	}
	
	public void setShouldSnapToGroundEnabled(boolean shouldSnapToGround)
	{
		this.isSnapToGroundEnabled.setValue(shouldSnapToGround);
	}
	
	public boolean shouldSnapToGround()
	{
		return this.isSnapEnabled.getValue() && this.isSnapToGroundEnabled.getValue();
	}
	
	public boolean isSnapToGroundEnabled()
	{
		return this.isSnapToGroundEnabled.getValue();
	}
	
	public void setShouldSnapToGridEnabled(boolean shouldSnapToGround)
	{
		this.isSnapToGridEnabled.setValue(shouldSnapToGround);
	}
	
	public boolean shouldSnapToGrid()
	{
		return this.isSnapEnabled.getValue() && this.isSnapToGridEnabled.getValue();
	}
	
	public boolean isSnapToGridEnabled()
	{
		return this.isSnapToGridEnabled.getValue();
	}
	
	public void setGridSpacing(double gridSpacing)
	{
		this.gridSpacing = gridSpacing;
	}
	
	public double getGridSpacing()
	{
		return this.gridSpacing;
	}
	
	public void setSnapEnabled(boolean snapEnabled)
	{
		this.isSnapEnabled.setValue(snapEnabled);
	}
	
	public boolean isSnapEnabled()
	{
		return this.isSnapEnabled.getValue();
	}
	
	public void setRotationSnapEnabled(boolean rotationSnapEnabled)
	{
		this.isRotationSnapEnabled.setValue(rotationSnapEnabled);
	}
	
	public boolean shouldSnapToRotation()
	{
		return this.isSnapEnabled.getValue() && this.isRotationSnapEnabled.getValue();
	}
	
	public boolean isRotationSnapEnabled()
	{
		return this.isRotationSnapEnabled.getValue();
	}
	
	public void setRotationSnapAngleInDegrees(double degrees)
	{
		this.rotationSnapAngle.setAsDegrees(degrees);
	}
	
	public void setRotationSnapAngle(Angle snapAngle)
	{
		this.rotationSnapAngle = new AngleInDegrees(snapAngle);
	}
	
	public Angle getRotationSnapAngle()
	{
		return this.rotationSnapAngle;
	}
	
	public boolean shouldShowSnapGrid()
	{
		return this.isSnapEnabled.getValue() && this.showSnapGrid.getValue();
	}
	
	public boolean isShowSnapGridEnabled()
	{
		return this.showSnapGrid.getValue();
	}
	
	public void setShowSnapGrid( boolean showSnapGrid )
	{
		this.showSnapGrid.setValue(showSnapGrid);
	}
	
}
