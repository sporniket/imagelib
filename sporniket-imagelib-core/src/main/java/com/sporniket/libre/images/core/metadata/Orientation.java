/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

/**
 * Enumerate available orientation (CCW is <em>Counter-Clock-wise</em> rotation).
 * 
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
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Image Library &#8211;
 * core</i>. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>. 2
 * 
 * <hr>
 * 
 * @author David SPORN
 * 
 * @version 15.06.00-SNAPSHOT
 * @since 15.06.00-SNAPSHOT
 * @see <a href="http://sylvana.net/jpegcrop/exif_orientation.html">http://sylvana.net/jpegcrop/exif_orientation.html</a>
 */
public enum Orientation
{
	ROT_000(Math.toRadians(0), 1, false, 1),
	ROT_090_CCW(Math.toRadians(-90), 1, true, 8),
	ROT_180_CCW(Math.toRadians(-180), 1, false, 3),
	ROT_270_CCW(Math.toRadians(-270), 1, true, 6),
	MIRROR_ROT_000(Math.toRadians(0), -1, false, 2),
	MIRROR_ROT_090_CCW(Math.toRadians(-90), -1, true, 7),
	MIRROR_ROT_180_CCW(Math.toRadians(-180), -1, false, 4),
	MIRROR_ROT_270_CCW(Math.toRadians(-270), -1, true, 5);
	/**
	 * Map exif value to an Orientation value.
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	public static final Orientation[] FROM_EXIF_VALUE =
	{
			ROT_000,
			ROT_000,
			MIRROR_ROT_000,
			ROT_180_CCW,
			MIRROR_ROT_180_CCW,
			MIRROR_ROT_270_CCW,
			ROT_270_CCW,
			MIRROR_ROT_090_CCW,
			ROT_090_CCW,
			ROT_000
	};

	/**
	 * Rotation angle in radians.
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	private final double myRotationAngle;

	/**
	 * Horizontal scale factor, for mirroring.
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	private final double myScaleX;

	/**
	 * Flag that is <code>true</code> if the orientation require a rotation of 90 or 270 degrees (+/-90 degrees).
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	private final boolean myQuarterTurnRequired;

	/**
	 * Corresponding exif value.
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	private final int myExifValue;

	/**
	 * Get exifValue.
	 * 
	 * @return the exifValue
	 * @since 15.06.00-SNAPSHOT
	 */
	public int getExifValue()
	{
		return myExifValue;
	}

	/**
	 * @return the quarterTurnRequired
	 * @since 15.06.00-SNAPSHOT
	 */
	public boolean isQuarterTurnRequired()
	{
		return myQuarterTurnRequired;
	}

	/**
	 * @return the scaleX
	 * @since 15.06.00-SNAPSHOT
	 */
	public double getScaleX()
	{
		return myScaleX;
	}

	/**
	 * @param rotationAngle
	 * @param scaleX
	 * @param quarterTurnRequired
	 * @param exifValue
	 * @since 15.06.00-SNAPSHOT
	 */
	private Orientation(double rotationAngle, double scaleX, boolean quarterTurnRequired, int exifValue)
	{
		myRotationAngle = rotationAngle;
		myScaleX = scaleX;
		myQuarterTurnRequired = quarterTurnRequired;
		myExifValue = exifValue;
	}

	/**
	 * @return the rotationAngle
	 * @since 15.06.00-SNAPSHOT
	 */
	public double getRotationAngle()
	{
		return myRotationAngle;
	}
}
