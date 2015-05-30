/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

import java.awt.Dimension;

/**
 * Storage class for image meta-data. Subject to change from version to version.
 * 
 * Non structured values return native type values, like String, int or float.
 * 
 * <p>
 * &copy; Copyright 2013 David Sporn
 * </p>
 * <hr />
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
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Image Library &#8211;
 * core</i>. If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 * 
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public class ImageMetaData
{
	/**
	 * Image orientation.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private Orientation myImageOrientation;

	/**
	 * Dimensions of the image after applying {@link #getImageOrientation()}.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private Dimension myRotatedDimensions;

	/**
	 * Dimension of the unrotated image.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private Dimension myUnrotatedDimension;

	/**
	 * @return the imageOrientation
	 */
	public Orientation getImageOrientation()
	{
		return myImageOrientation;
	}

	/**
	 * Get rotatedDimensions.
	 * 
	 * @return the rotatedDimensions
	 * @since 0-SNAPSHOT
	 */
	public Dimension getRotatedDimensions()
	{
		return myRotatedDimensions;
	}

	/**
	 * Get unrotatedDimension.
	 * 
	 * @return the unrotatedDimension
	 * @since 0-SNAPSHOT
	 */
	public Dimension getUnrotatedDimension()
	{
		return myUnrotatedDimension;
	}

	/**
	 * Change imageOrientation.
	 * 
	 * @param imageOrientation
	 *            the new value of imageOrientation.
	 * @since 0-SNAPSHOT
	 */
	public void setImageOrientation(Orientation imageOrientation)
	{
		myImageOrientation = imageOrientation;
		updateRotatedDimension();
	}

	/**
	 * @param imageOrientation
	 *            the imageOrientation to set
	 */
	public void setImageOrientationFromExif(int imageOrientation)
	{
		setImageOrientation(Orientation.FROM_EXIF_VALUE[imageOrientation]);
	}

	/**
	 * Change rotatedDimensions.
	 * 
	 * @param rotatedDimensions
	 *            the new value of rotatedDimensions.
	 * @since 0-SNAPSHOT
	 */
	private void setRotatedDimensions(Dimension rotatedDimensions)
	{
		myRotatedDimensions = rotatedDimensions;
	}

	/**
	 * Change unrotatedDimension.
	 * 
	 * @param unrotatedDimension
	 *            the new value of unrotatedDimension.
	 * @since 0-SNAPSHOT
	 */
	public void setUnrotatedDimension(Dimension unrotatedDimension)
	{
		myUnrotatedDimension = unrotatedDimension;
		updateRotatedDimension();
	}

	/**
	 * Apply the rotation to the image dimensions.
	 * 
	 * @since 0-SNAPSHOT
	 */
	private void updateRotatedDimension()
	{
		if (null != getUnrotatedDimension())
		{
			if (null != getImageOrientation() && getImageOrientation().isQuarterTurnRequired())
			{
				setRotatedDimensions(new Dimension(getUnrotatedDimension().height, getUnrotatedDimension().width));
			}
			else
			{
				setRotatedDimensions(new Dimension(getUnrotatedDimension()));
			}
		}
	}

}
