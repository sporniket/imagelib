/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

import java.io.IOException;

import javax.imageio.metadata.IIOMetadata;

/**
 * Extract metadata from the {@link IIOMetadata} nodes returned by java ImageIO API.
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
public class ImageIoMetadataExtractor implements ImageMetaDataExtractor<IIOMetadata>
{

	public ImageMetaData appendFromResource(IIOMetadata resource, ImageMetaData metaData) throws IOException,
			ImageMetaDataException
	{
		// TODO Auto-generated method stub
		return metaData;
	}

	public ImageMetaData extractFromResource(IIOMetadata resource) throws IOException, ImageMetaDataException
	{
		ImageMetaData _result = new ImageMetaData();
		return appendFromResource(resource, _result);
	}

}
