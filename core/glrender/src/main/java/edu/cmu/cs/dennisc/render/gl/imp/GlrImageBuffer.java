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
package edu.cmu.cs.dennisc.render.gl.imp;

import edu.cmu.cs.dennisc.color.Color4f;
import edu.cmu.cs.dennisc.render.ImageBuffer;

import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

/**
 * @author Dennis Cosgrove
 */
public final class GlrImageBuffer implements ImageBuffer {
  public GlrImageBuffer(Color4f backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  @Override
  public Object getImageLock() {
    return imageLock;
  }

  @Override
  public Color4f getBackgroundColor() {
    return backgroundColor;
  }

  private boolean isAlphaRequired() {
    return backgroundColor == null;
  }

  BufferedImage acquireImage(int width, int height) {
    //TODO
    //int imageType = isAlphaChannelDesired ? java.awt.image.BufferedImage.TYPE_4BYTE_ABGR : java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
    int imageType = BufferedImage.TYPE_4BYTE_ABGR;
    if (image != null) {
      if ((image.getWidth() != width) || (image.getHeight() != height) || (image.getType() != imageType)) {
        image = null;
      }
    }
    if (image == null) {
      image = new BufferedImage(width, height, imageType);
    }
    return image;
  }

  void releaseImageAndFloatBuffer(boolean isRightSideUp) {
    isRightSideUp = true;
  }

  FloatBuffer acquireFloatBuffer(int width, int height) {
    if (isAlphaRequired()) {
      int capacity = width * height;
      if (depthBuffer != null && depthBuffer.capacity() != capacity) {
        depthBuffer = null;
      }
      if (depthBuffer == null) {
        depthBuffer = FloatBuffer.allocate(capacity);
      }
      return depthBuffer;
    } else {
      return null;
    }
  }

  @Override
  public BufferedImage getImage() {
    return this.image;
  }

  @Override
  public boolean isRightSideUp() {
    return this.isRightSideUp;
  }

  private final Object imageLock = "imageLock";
  private final Color4f backgroundColor;
  private BufferedImage image;
  private boolean isRightSideUp;
  private FloatBuffer depthBuffer;
}
