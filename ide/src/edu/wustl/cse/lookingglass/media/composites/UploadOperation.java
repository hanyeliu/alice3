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
package edu.wustl.cse.lookingglass.media.composites;

import java.io.File;
import java.io.IOException;

import org.alice.ide.IDE;
import org.alice.media.ExportToYouTubeWizardDialogComposite;
import org.lgna.croquet.ActionOperation;
import org.lgna.croquet.history.Transaction;
import org.lgna.croquet.triggers.Trigger;

import edu.wustl.cse.lookingglass.media.FFmpegProcess;
import edu.wustl.cse.lookingglass.media.FFmpegProcessException;
import edu.wustl.cse.lookingglass.media.ImagesToWebmEncoder;

/**
 * @author Matt May
 */
public class UploadOperation extends ActionOperation {

	public UploadOperation() {
		super( IDE.EXPORT_GROUP, java.util.UUID.fromString( "9a855203-b1ce-4ba3-983d-b941a36b2c10" ) );
	}

	@Override
	protected void perform( Transaction transaction, Trigger trigger ) {
		File encodedVideo = null;
		FFmpegProcess process = null;
		try {
			encodedVideo = java.io.File.createTempFile( "project", "." + ImagesToWebmEncoder.WEBM_EXTENSION );
		} catch( IOException e ) {
			e.printStackTrace();
			return;
		}
		process = new FFmpegProcess( "-y", "-r", String.format( "%d", 1 ), "-f", "image2pipe", "-vcodec", "ppm", "-i", "-", "-vf", "vflip", "-vcodec", "libvpx", "-quality", "good", "-cpu-used", "0", "-b:v", "500k", "-qmin", "10", "-qmax", "42", "-maxrate", "500k", "-bufsize", "1000k", "-pix_fmt", "yuv420p", encodedVideo.getAbsolutePath() );
		Process start = null;
		try {
			start = process.start();
		} catch( FFmpegProcessException e ) {
			FFmpegProcessExceptionDialog dialog = new FFmpegProcessExceptionDialog( e );
			dialog.getOperation().fire();
			if( dialog.getIsFixed() ) {
				//pass
			} else {
				System.out.println( "RETURNING" );
				return;
			}
		}
		if( start != null ) {
			process.stop();
		}
		ExportToYouTubeWizardDialogComposite.getInstance().getOperation().fire( trigger );
	}
}
