/**
 * 
 */
package com.sporniket.libre.images.core;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import com.sporniket.libre.images.core.ImageFileUtils.ReaderFilter;
import com.sporniket.libre.images.core.ImageFileUtils.ReaderNotFoundException;

/**
 * Jpeg utilities.
 * 
 * @author dsporn
 *
 * @version 15.06.00-SNAPSHOT
 * @since 15.06.00-SNAPSHOT
 */
public class JpegUtils
{

	private static final String FILE_FORMAT__JPEG = "JPEG";

	/**
	 * Retrieve the first Jpeg reader available.
	 * 
	 * @return an {@link ImageReader}
	 * @throws ReaderNotFoundException
	 *             if there is no suitable reader.
	 */
	public static final ImageReader retrieveJpegReader() throws ReaderNotFoundException
	{
		return retrieveJpegReader(ImageFileUtils.READER_FILTER__FIRST);
	}

	/**
	 * Retrieve the first Jpeg reader accepted by the provided filter.
	 * 
	 * @param filter
	 *            the filter to select the suitable reader.
	 * @return an {@link ImageReader}
	 * @throws ReaderNotFoundException
	 *             if there is no suitable reader.
	 */
	public static final ImageReader retrieveJpegReader(ReaderFilter filter) throws ReaderNotFoundException
	{
		for (Iterator<ImageReader> readers = retrieveJpegReaders(); readers.hasNext();)
		{
			ImageReader _candidate = readers.next();
			if (filter.isSuitable(_candidate))
			{
				return _candidate;
			}
		}
		throw new ReaderNotFoundException();
	}

	/**
	 * Retrieve all the available readers for JPEG files.
	 * 
	 * @return an iterator over the available readers.
	 */
	public static final Iterator<ImageReader> retrieveJpegReaders()
	{
		return ImageIO.getImageReadersByFormatName(FILE_FORMAT__JPEG);
	}

}
