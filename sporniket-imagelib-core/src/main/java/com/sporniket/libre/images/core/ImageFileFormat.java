/**
 * 
 */
package com.sporniket.libre.images.core;

import javax.imageio.ImageIO;

/**
 * Enumeration of image file format, to be used when using java api asking for a format.
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
public enum ImageFileFormat
{
	JPG("JPG");
	
	/**
	 * Name of the format for the {@link ImageIO} API.
	 * 
	 * @since 15.06.00-SNAPSHOT
	 */
	private final String myImageIoName;

	/**
	 * @param imageIoName
	 * @since 15.06.00-SNAPSHOT
	 */
	private ImageFileFormat(final String imageIoName)
	{
		myImageIoName = imageIoName;
	}

	/**
	 * @return the imageIoName
	 * @since 15.06.00-SNAPSHOT
	 */
	public String getImageIoName()
	{
		return myImageIoName;
	}
}
