/**
 * 
 */
package com.sporniket.libre.images.core.metadata;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Abstraction layer for pluging metadata extraction libraries with this API.
 * 
 * An implementation is bound to a type of resource (typically : {@link File} or {@link URL}).
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
public interface ImageMetaDataExtractor<ResourceType>
{
	/**
	 * Update a metadata structure with new information. This will allow to gather exif and xmp metadata, for instance, if there are
	 * two extractors.
	 * 
	 * @param resource
	 *            might be a file, an url, etc...
	 * @param metaData
	 *            an {@link ImageMetaData} structure to update
	 * @return the updated {@link ImageMetaData} structure.
	 * @throws IOException
	 *             if there is an I/O problem.
	 * @throws ImageMetaDataException
	 *             if there is a problem with the metadata.
	 */
	ImageMetaData appendFromResource(ResourceType resource, ImageMetaData metaData) throws IOException, ImageMetaDataException;

	/**
	 * Extract Metadata from the specified resource.
	 * 
	 * @param resource
	 *            might be a file, an url, etc...
	 * @return an {@link ImageMetaData} structure.
	 * @throws IOException
	 *             if there is an I/O problem.
	 * @throws ImageMetaDataException
	 *             if there is a problem with the metadata.
	 */
	ImageMetaData extractFromResource(ResourceType resource) throws IOException, ImageMetaDataException;
}
