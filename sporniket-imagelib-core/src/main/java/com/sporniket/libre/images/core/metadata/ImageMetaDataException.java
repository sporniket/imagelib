/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

/**
 * Exception thrown when there is a problem on the metadata (unparsable, for instance).
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
 */
public class ImageMetaDataException extends Exception
{

	private static final long serialVersionUID = -2277167646645620602L;

	public ImageMetaDataException()
	{
	}

	public ImageMetaDataException(String message)
	{
		super(message);
	}

	public ImageMetaDataException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ImageMetaDataException(Throwable cause)
	{
		super(cause);
	}

}
