/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.FileImageInputStream;

import com.sporniket.libre.images.core.ImageFileUtils.ReaderNotFoundException;
import com.sporniket.libre.images.core.JpegUtils;

/**
 * Utilities.
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
public class MetadataUtils
{
	/**
	 * Macro to get the metadata as provided by the ImageIO API.
	 * 
	 * @param resource
	 *            the file to read.
	 * @return
	 * @throws ReaderNotFoundException
	 *             if an ImageReader is not found.
	 * @throws FileNotFoundException
	 *             if the file is not found.
	 * @throws IOException
	 *             if there is a problem.
	 * @since 15.06.00-SNAPSHOT
	 */
	public static IIOMetadata extractMetadata(File resource) throws ReaderNotFoundException, FileNotFoundException, IOException
	{
		ImageReader _reader = JpegUtils.retrieveJpegReader();
		_reader.setInput(new FileImageInputStream(resource), false, false);
		IIOMetadata _metadata = _reader.getImageMetadata(0);
		return _metadata;
	}
}
