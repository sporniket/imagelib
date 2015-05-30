/**
 * 
 */
package com.sporniket.libre.images.core;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

/**
 * Utilities for image file manipulation.
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
 * <i>The Sporniket Image Library &#8211; core</i> is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 * 
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with <i>The Sporniket Image Library &#8211; core</i>.
 * If not, see <http://www.gnu.org/licenses/>. 2
 * 
 * <hr />
 * 
 * @author David SPORN <david.sporn@sporniket.com>
 *
 * @version 0-SNAPSHOT
 * @since 0-SNAPSHOT
 */
public final class ImageFileUtils
{
	/**
	 * Interface to implement to select a suitable image reader.
	 * 
	 * @author DSPORN
	 * @see ImageFileUtils#retrieveJpegReader(ReaderFilter)
	 */
	public static interface ReaderFilter
	{
		/**
		 * Tells whether the given reader is suitable for the purpose.
		 * 
		 * @param reader
		 *            the reader to test
		 * @return <code>true</code> if the reader is suitable for the purpose.
		 */
		boolean isSuitable(ImageReader reader);
	}

	/**
	 * Exception thrown when there are no available image reader for the task.
	 * 
	 * @author DSPORN
	 * 
	 */
	public static class ReaderNotFoundException extends Exception
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -6083168773463135629L;

		/**
		 * 
		 */
		public ReaderNotFoundException()
		{
		}

		/**
		 * @param message
		 */
		public ReaderNotFoundException(String message)
		{
			super(message);
		}

		/**
		 * @param message
		 * @param cause
		 */
		public ReaderNotFoundException(String message, Throwable cause)
		{
			super(message, cause);
		}

		/**
		 * @param cause
		 */
		public ReaderNotFoundException(Throwable cause)
		{
			super(cause);
		}

	}

	/**
	 * Built-in {@link ReaderFilter} for selecting the first that can read raster data.
	 * 
	 * @see ImageFileUtils#retrieveJpegReader(ReaderFilter)
	 */
	public static final ReaderFilter READER_FILTER__CAN_READ_RASTER = new ReaderFilter()
	{

		public boolean isSuitable(ImageReader reader)
		{
			return reader.canReadRaster();
		}
	};

	/**
	 * Built-in {@link ReaderFilter} for selecting the first one.
	 * 
	 * @see ImageFileUtils#retrieveJpegReader(ReaderFilter)
	 */
	public static final ReaderFilter READER_FILTER__FIRST = new ReaderFilter()
	{

		public boolean isSuitable(ImageReader reader)
		{
			return true;
		}
	};
}
