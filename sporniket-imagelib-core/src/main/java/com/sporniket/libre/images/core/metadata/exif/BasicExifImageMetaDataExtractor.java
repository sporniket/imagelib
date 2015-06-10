/**
 * 
 */
package com.sporniket.libre.images.core.metadata.exif;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageInputStream;

import org.w3c.dom.Attr;
import org.w3c.dom.NodeList;

import com.sporniket.libre.images.core.ImageFileUtils;
import com.sporniket.libre.images.core.ImageFileUtils.ReaderNotFoundException;
import com.sporniket.libre.images.core.JpegUtils;
import com.sporniket.libre.images.core.metadata.ImageMetaData;
import com.sporniket.libre.images.core.metadata.ImageMetaDataException;
import com.sporniket.libre.images.core.metadata.ImageMetaDataExtractor;
import com.sporniket.libre.lang.DataTools;

/**
 * Crude exif data reader.
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
 * @see <a href="http://www.media.mit.edu/pia/Research/deepview/exif.html">http://www.media.mit.edu/pia/Research/deepview/exif.html</a>
 * @see <a href="http://www.awaresystems.be/imaging/tiff/tifftags.html">http://www.awaresystems.be/imaging/tiff/tifftags.html</a>
 */
public class BasicExifImageMetaDataExtractor implements ImageMetaDataExtractor<IIOMetadata>
{

	/**
	 * In the node named {@value #TAG_NAME__APP1_CONTAINER}, look for the attribute identifying the APP1 data ; the value of this
	 * attribute will be {@value #MARKER__APP1_DATA}.
	 */
	private static final String ATTRIBUTE_NAME__APP1_MARKER = "MarkerTag";

	private static final int INITIAL_CAPACITY__IMAGE_FILE_DIRECTORY_LIST = 10;

	private static final BasicExifImageMetaDataExtractor INSTANCE = new BasicExifImageMetaDataExtractor();

	private static final int MARKER__APP1_DATA = 0xE1;

	private static final byte[] MARKER__EXIF_START = "Exif\0\0".getBytes();

	private static final short MARKER__TIFF_HEADER__002A = 0x2A;

	private static final byte[] MARKER__TIFF_HEADER__ALIGNEMENT__INTEL = "II".getBytes();

	private static final byte[] MARKER__TIFF_HEADER__ALIGNEMENT__MOTOROLA = "MM".getBytes();

	private static final short MARKER__TIFF_HEADER__SIZE = 0x08;

	/**
	 * Path of node names to look for to find exif data.
	 * 
	 * It's a child of the tag {@value #TAG_NAME__APP1_CONTAINER__PARENT}.
	 */
	private static final String[] TAG_NAMES__APP1_CONTAINER =
	{
			"markerSequence", "unknown"
	};

	/**
	 * Format name to get the tree of metadata containing exif data.
	 * 
	 * @see IIOMetadata#getAsTree(String)
	 */
	private static final String TREE_NAME__JAVAX_IMAGEIO_JPEG_IMAGE_1_0 = "javax_imageio_jpeg_image_1.0";

	/**
	 * @return the instance
	 */
	public static BasicExifImageMetaDataExtractor getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Utility method to extract directly from a file, when you do not need to keep {@link IIOMetadata} nodes.
	 * 
	 * @param resource the file to read.
	 * @param metaData the metadata to populate.
	 * @return the populated metadata.
	 * @throws IOException when there is a problem.
	 * @throws ImageMetaDataException
	 * @since 15.06.00-SNAPSHOT
	 */
	public ImageMetaData appendFromResource(File resource, ImageMetaData metaData) throws IOException, ImageMetaDataException
	{
		System.out.println("---> Processing " + resource.getName());
		try
		{
			ImageReader _reader = JpegUtils.retrieveJpegReader();
			_reader.setInput(new FileImageInputStream(resource), false, false);
			IIOMetadata _metadata = _reader.getImageMetadata(0);
			return appendFromResource(_metadata, metaData);
		}
		catch (ReaderNotFoundException _exception)
		{
			throw new IOException("No reader available", _exception);
		}
	}

	/**
	 * @param resource metadata source.
	 * @param metaData metadata to populate.
	 * @return the populated metadata.
	 * @throws ImageMetaDataException when there is a problem.
	 * @since 15.06.00-SNAPSHOT
	 */
	public ImageMetaData appendFromResource(IIOMetadata resource, ImageMetaData metaData) throws ImageMetaDataException,
			ImageMetaDataException
	{
		IIOMetadataNode _rootNode = (IIOMetadataNode) resource.getAsTree(TREE_NAME__JAVAX_IMAGEIO_JPEG_IMAGE_1_0);
		byte[] _exifRawData = retrieveExifData(_rootNode);

		// prepare a byte buffer to hide endianness issues
		// the byte buffer does not include the exif marker, it contains
		// only the TIFF structure.
		ByteBuffer _bufferParser = ByteBuffer.wrap(_exifRawData);
		if (DataTools.matchSequence(MARKER__TIFF_HEADER__ALIGNEMENT__INTEL, _exifRawData))
		{
			_bufferParser.order(ByteOrder.LITTLE_ENDIAN);
		}
		else if (DataTools.matchSequence(MARKER__TIFF_HEADER__ALIGNEMENT__MOTOROLA, _exifRawData))
		{
			_bufferParser.order(ByteOrder.BIG_ENDIAN);
		}
		else
		{
			byte[] _excerpt =
			{
					_exifRawData[0], _exifRawData[1]
			};
			throw new ImageMetaDataException("Invalid alignement specification 0x" + DataTools.convertToPaddedHexString(_excerpt));
		}
		// starts after the endianness value
		_bufferParser.position(MARKER__TIFF_HEADER__ALIGNEMENT__INTEL.length);
		short _tiffHeader2a = _bufferParser.getShort();
		if (MARKER__TIFF_HEADER__002A != _tiffHeader2a)
		{
			throw new ImageMetaDataException("expected sequence (" + MARKER__TIFF_HEADER__002A + ") not found : " + _tiffHeader2a
					+ ".");
		}
		if (MARKER__TIFF_HEADER__SIZE != _bufferParser.getInt())
		{
			throw new ImageMetaDataException("expected sequence not found");
		}

		// ok, now retrieve the Image file directories
		List<ImageFileDirectory> _imageFileDirectories = new ArrayList<ImageFileDirectory>(
				INITIAL_CAPACITY__IMAGE_FILE_DIRECTORY_LIST);
		ImageFileDirectory _currentDirectory;
		int _currentOffset = MARKER__TIFF_HEADER__SIZE;
		do
		{
			System.out.println("Reading IFD directory...");
			_currentDirectory = ImageFileDirectory.parseRawTiffHeader(_bufferParser, _currentOffset);
			_imageFileDirectories.add(_currentDirectory);
			_currentOffset = (int) (_currentDirectory.getOffsetToNextDirectory());
		}
		while (0 < _currentDirectory.getOffsetToNextDirectory());

		// scan directories for subdirectories
		List<ImageFileDirectory> _subImageFileDirectories = new ArrayList<ImageFileDirectory>(
				INITIAL_CAPACITY__IMAGE_FILE_DIRECTORY_LIST);
		for (ImageFileDirectory _directory : _imageFileDirectories)
		{
			for (ImageFileDirectory.Entry _entry : _directory.getItems())
			{
				switch (_entry.getTag())
				{
					case 0x8769: // Offset to Exif Sub IFD
						_currentOffset = (int) _entry.getDataValueOrOffset();
						System.out.println("Exif Sub IFD at = " + _currentOffset);
						_currentDirectory = ImageFileDirectory.parseRawTiffHeader(_bufferParser, _currentOffset);
						_subImageFileDirectories.add(_currentDirectory);
				}
			}
		}
		if (!_subImageFileDirectories.isEmpty())
		{
			_imageFileDirectories.addAll(_subImageFileDirectories);
		}

		// scan Image file directory entries to extract interesting
		// metadata.
		for (ImageFileDirectory _directory : _imageFileDirectories)
		{
			int _width = 0;
			int _height = 0;
			for (ImageFileDirectory.Entry _entry : _directory.getItems())
			{
				switch (_entry.getTag())
				{
					case 0x0112:// Orientation
						System.out.println("orientation format = " + _entry.getDataFormat());
						System.out.println("orientation number of component = " + _entry.getNumberOfComponent());
						System.out.println("orientation value = " + _entry.getDataValueOrOffset());
						metaData.setImageOrientationFromExif((int) _entry.getDataValueOrOffset());
						break;
					case 0x011a:// XResolution
					{
						System.out.println("xresolution address = " + _entry.getDataValueOrOffset());
						_bufferParser.position((int) _entry.getDataValueOrOffset());
						long _numerator = DataTools.getUnsignedIntValue(_bufferParser.getInt());
						long _denominator = DataTools.getUnsignedIntValue(_bufferParser.getInt());
						System.out.println("xresolution value = " + _numerator + "/" + _denominator);
					}
						break;
					case 0xa002:// Image Width
						_width = (int) _entry.getDataValueOrOffset();
						System.out.println("Image Width = " + _width);
						break;
					case 0xa003:// Image Height
						_height = (int) _entry.getDataValueOrOffset();
						System.out.println("Image Height = " + _height);
						break;
				}
			}
			if (_width > 0 && _height > 0)
			{
				metaData.setUnrotatedDimension(new Dimension(_width, _height));
			}
		}

		return metaData;
	}

	public ImageMetaData extractFromResource(IIOMetadata resource) throws IOException, ImageMetaDataException
	{
		ImageMetaData _result = new ImageMetaData();
		return appendFromResource(resource, _result);
	}

	/**
	 * Gathers all descendant nodes that may contains exif data.
	 * 
	 * @param rootNode the root node.
	 * @return a list of possible container of the exif data.
	 */
	private List<IIOMetadataNode> findExifContainerCandidates(IIOMetadataNode rootNode)
	{
		List<NodeList> _candidatesPool = new LinkedList<NodeList>();
		int _candidatesCount = 0;
		NodeList _parentCandidates = ((IIOMetadataNode) rootNode).getElementsByTagName(TAG_NAMES__APP1_CONTAINER[0]);
		// find the parent nodes that may contains the app1 container node.
		for (int _parentIndex = 0; _parentIndex < _parentCandidates.getLength(); _parentIndex++)
		{
			NodeList _containerCandidates = ((IIOMetadataNode) _parentCandidates.item(_parentIndex))
					.getElementsByTagName(TAG_NAMES__APP1_CONTAINER[1]);
			if (0 < _containerCandidates.getLength())
			{
				_candidatesCount += _containerCandidates.getLength();
				_candidatesPool.add(_containerCandidates);
			}
		}
		List<IIOMetadataNode> _candidates = new ArrayList<IIOMetadataNode>(_candidatesCount);
		for (NodeList _containerCandidates : _candidatesPool)
		{
			// find the app1 container node
			for (int _containerIndex = 0; _containerIndex < _containerCandidates.getLength(); _containerIndex++)
			{
				IIOMetadataNode _container = (IIOMetadataNode) _containerCandidates.item(_containerIndex);
				_candidates.add(_container);
			}
		}
		return _candidates;
	}

	/**
	 * Find the Exif meta data from the provided {@link IIOMetadataNode}.
	 * 
	 * @param rootNode
	 *            the root node of a tree containing, somewhere, the exif metadata.
	 * @return a byte array that are the Exif meta data, without the exif marker, to be parsed.
	 * @throws ImageMetaDataException
	 *             if it cannot find any exif metadata.
	 */
	private byte[] retrieveExifData(IIOMetadataNode rootNode) throws ImageMetaDataException
	{
		List<IIOMetadataNode> _candidates = findExifContainerCandidates(rootNode);
		for (IIOMetadataNode _candidate : _candidates)
		{
			// check the container node using the attribute
			// ATTRIBUTE_NAME__APP1_MARKER
			Attr _marker = _candidate.getAttributeNode(ATTRIBUTE_NAME__APP1_MARKER);
			if (null == _marker)
			{
				continue;
			}

			// the attribute exists, check against MARKER__APP1_DATA
			int _markerValue = Integer.parseInt(_marker.getValue());
			if (MARKER__APP1_DATA == _markerValue)
			{
				if (_candidate.getUserObject() instanceof byte[])
				{
					// check that the data effectively start by the Exif marker.
					byte[] _rawData = (byte[]) _candidate.getUserObject();
					if (DataTools.matchSequence(MARKER__EXIF_START, _rawData))
					{
						byte[] _exifBodyData = Arrays.copyOfRange(_rawData, MARKER__EXIF_START.length, _rawData.length
								- MARKER__EXIF_START.length);
						return _exifBodyData;
					}
				}
			}
		}

		// no exif metadata in this node...
		throw new ImageMetaDataException("Could not find exif data container.");
	}

}
