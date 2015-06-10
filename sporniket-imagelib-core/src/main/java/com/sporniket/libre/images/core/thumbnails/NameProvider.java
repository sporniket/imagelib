/**
 * 
 */
package com.sporniket.libre.images.core.thumbnails;

import java.io.File;
import java.text.MessageFormat;

import com.sporniket.libre.lang.string.StringTools;

/**
 * Interface for a service that compute a filename for a thumbnail.
 * 
 * <p>The name should be unique.
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
public interface NameProvider
{
	/**
	 * Simple implementation that take into account the source image location and the maximum size of a thumbnail.
	 * @author dsporn
	 *
	 * @version 15.06.00-SNAPSHOT
	 * @since 15.06.00-SNAPSHOT
	 */
	public static class ByPathAndMaxSize implements NameProvider 
	{
		/**
		 * Build the final filename.
		 * @since 15.06.00-SNAPSHOT
		 */
		private MessageFormat myFormatterForFilename ;
		
		/**
		 * Format that will concatenates the thumbnail size and location of the source image.
		 * @since 15.06.00-SNAPSHOT
		 */
		private MessageFormat myNameNormalizer = new MessageFormat("{0};{1}");
		private Integer myThumbnailMaximumSize;

		/**
		 * @param thumbnailMaximumSize the maximum size of a thumbnail.
		 * @param fileExtension the file extension.
		 * @since 15.06.00-SNAPSHOT
		 */
		public ByPathAndMaxSize(int thumbnailMaximumSize, String fileExtension)
		{
			super();
			myThumbnailMaximumSize = thumbnailMaximumSize;
			setFormatterForFilename(new MessageFormat("{0}."+fileExtension));
		}

		/* (non-Javadoc)
		 * @see com.sporniket.libre.images.core.thumbnails.NameProvider#computeName(java.io.File)
		 * @since 15.06.00-SNAPSHOT
		 */
		public String computeName(File sourceFile)
		{
			Object[] _args = {StringTools.hash(getNormalizedSourceName(sourceFile))};
			return getFormatterForFilename().format(_args);
		}

		/**
		 * Get formatterForFilename.
		 * @return the formatterForFilename
		 * @since 15.06.00-SNAPSHOT
		 */
		private MessageFormat getFormatterForFilename()
		{
			return myFormatterForFilename;
		}

		/**
		 * Get nameNormalizer.
		 * @return the nameNormalizer
		 * @since 15.06.00-SNAPSHOT
		 */
		public MessageFormat getNameNormalizer()
		{
			return myNameNormalizer;
		}

		/**
		 * Compute the normalized source name.
		 * @param sourceImage
		 * @return the normalized source name.
		 * @since 15.06.00-SNAPSHOT
		 */
		private String getNormalizedSourceName(File sourceImage)
		{
			Object[] _args = {getThumbnailMaximumSize(), sourceImage.getAbsolutePath()};
			return getNameNormalizer().format(_args);
		}

		/**
		 * Get thumbnailMaximumSize.
		 * @return the thumbnailMaximumSize
		 * @since 15.06.00-SNAPSHOT
		 */
		public Integer getThumbnailMaximumSize()
		{
			return myThumbnailMaximumSize;
		}
		
		/**
		 * Change the formatterForFilename.
		 * @param formatterForFilename
		 * @since 15.06.00-SNAPSHOT
		 */
		private void setFormatterForFilename(MessageFormat formatterForFilename)
		{
			myFormatterForFilename = formatterForFilename;
		}
	}
	/**
	 * Compute the file name thumbnail for the source image file.
	 * @param sourceFile source image from which a thumbnail will be generated.
	 * @return the name of the thumbnail file.
	 * @since 15.06.00-SNAPSHOT
	 */
	String computeName(File sourceFile) ;
}
