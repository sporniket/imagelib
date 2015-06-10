/**
 * 
 */
package com.sporniket.libre.images.core.thumbnails;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sporniket.libre.images.core.ImageFileFormat;

/**
 * Thumbnail generator.
 * 
 * <p>Thumbnails are saved using the jpg format.
 * <p>
 * &copy; Copyright 2013 David Sporn
 * </p>
 * <hr>
 * 
 * <p>
 * This file is part of <i>The Sporniket Image Library &#8211; core</i>.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * <p>
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Image Library &#8211; core</i>.
 * If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN
 *
 * @version 15.06.00-SNAPSHOT
 * @since 15.06.00-SNAPSHOT
 */
public class Generator
{
	/**
	 * The generated thumbnail will have dimension that fit inside {@link #myMaxSize}x{@link #myMaxSize}
	 */
	private int myMaxSize;

	/**
	 * @param maxSize the maximum size.
	 * @since 15.06.00-SNAPSHOT
	 */
	public Generator(int maxSize)
	{
		myMaxSize = maxSize;
	}

	/**
	 * Compute the scale factor so that any dimension of the thumbnail does not exceed {@link #getMaxSize()}.
	 * 
	 * @param sourceImage
	 *            the image to scale down.
	 * @return
	 */
	private Dimension computeThumbnailDimensions(BufferedImage sourceImage)
	{
		int _maxDimension = (sourceImage.getWidth() > sourceImage.getHeight()) ? sourceImage.getWidth() : sourceImage.getHeight();

		// Compute thumbnail dimesions
		double _scaleFactor = 1;
		if (_maxDimension > getMaxSize())
		{
			_scaleFactor = (double) getMaxSize() / _maxDimension;
		}

		Dimension _thumbnailDimensions = new Dimension((int) ((double) sourceImage.getWidth() * _scaleFactor),
				(int) ((double) sourceImage.getHeight() * _scaleFactor));
		return _thumbnailDimensions;
	}


	/**
	 * Generate the thumbnail.
	 * @param sourceImage the image to preview.
	 * @param destinationFile the file to save the thumbnail into.
	 * @throws IOException if a problem occurs.
	 */
	public void generateThumbnail(File sourceImage, File destinationFile) throws IOException
	{
		// Image loading
		BufferedImage _picture = null;
		_picture = ImageIO.read(sourceImage);

		Dimension _thumbnailDimensions = computeThumbnailDimensions(_picture);

		// Create the thumbnail
		BufferedImage _thumbnailImage = new BufferedImage(_thumbnailDimensions.width, _thumbnailDimensions.height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D _graphics = (Graphics2D) _thumbnailImage.getGraphics();
		_graphics.drawImage(_picture, 0, 0, _thumbnailImage.getWidth(), _thumbnailImage.getHeight(), 0, 0, _picture.getWidth(),
				_picture.getHeight(), null);

		// Save and return
		ImageIO.write(_thumbnailImage, ImageFileFormat.JPG.getImageIoName(), destinationFile);
	}

	/**
	 * Get maxSize.
	 * @return the maxSize
	 * @since 15.06.00-SNAPSHOT
	 */
	private int getMaxSize()
	{
		return myMaxSize;
	}
}
