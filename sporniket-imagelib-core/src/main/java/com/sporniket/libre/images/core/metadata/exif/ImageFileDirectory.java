package com.sporniket.libre.images.core.metadata.exif;

import java.nio.ByteBuffer;

import com.sporniket.libre.lang.DataTools;

/**
 * Image file directory structure.
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
 * 
 */
final class ImageFileDirectory
{
	/**
	 * Storage of an Image File Directory entry.
	 * 
	 * The class uses ints and longs instead of shorts and ints to support unsigned values.
	 * 
	 * @author dsporn
	 * 
	 */
	static final class Entry
	{
		public static final int[] SIZE_OF_COMPONENT_BY_DATA_FORMAT =
		{
				0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8
		};

		private int myDataFormat;

		private long myDataValueOrOffset;

		private long myNumberOfComponent;

		private int myTag;

		/**
		 * If the tag contains a short string value (less than 4 bytes), stores it in this field.
		 * 
		 * @since 0-SNAPSHOT
		 */
		private String myShortStringValue;

		/**
		 * Get shortStringValue.
		 * 
		 * @return the shortStringValue
		 * @since 0-SNAPSHOT
		 */
		public String getShortStringValue()
		{
			return myShortStringValue;
		}

		public Entry(int tag, int dataFormat, long numberOfComponent, long dataValueOrOffset, String shortString)
		{
			super();
			myTag = tag;
			myDataFormat = dataFormat;
			myNumberOfComponent = numberOfComponent;
			myDataValueOrOffset = dataValueOrOffset;
			myShortStringValue = shortString;
		}

		/**
		 * @return the dataFormat
		 */
		public int getDataFormat()
		{
			return myDataFormat;
		}

		/**
		 * @return the dataValueOrOffset
		 */
		public long getDataValueOrOffset()
		{
			return myDataValueOrOffset;
		}

		/**
		 * @return the numberOfComponent
		 */
		public long getNumberOfComponent()
		{
			return myNumberOfComponent;
		}

		/**
		 * @return the tag
		 */
		public int getTag()
		{
			return myTag;
		}
	}

	private static final int SIZE_OF_INT = 4;

	public static final ImageFileDirectory parseRawTiffHeader(ByteBuffer buffer, int offset)
	{
		buffer.position(offset);
		short _rawEntryCount = buffer.getShort();
		int _entryCount = DataTools.getUnsignedShortValue(_rawEntryCount);
		ImageFileDirectory.Entry[] _entries = new ImageFileDirectory.Entry[_entryCount];
		for (int _index = 0; _index < _entryCount; _index++)
		{
			try
			{
				short _rawTag = buffer.getShort();
				int _tag = DataTools.getUnsignedShortValue(_rawTag);
				System.out.println("-->Reading tag " + Integer.toHexString(_tag));
				int _dataFormat = DataTools.getUnsignedShortValue(buffer.getShort());
				long _dataNumberOfComponent = DataTools.getUnsignedIntValue(buffer.getInt());
				long _dataValueOrOffset;
				String _shortString = null;
				if (_dataNumberOfComponent * Entry.SIZE_OF_COMPONENT_BY_DATA_FORMAT[_dataFormat] > SIZE_OF_INT)
				{
					_dataValueOrOffset = DataTools.getUnsignedIntValue(buffer.getInt());
				}
				else
				{
					switch (_dataFormat)
					{
						case 1: // unsigned byte
						case 7: // undefined
							_dataValueOrOffset = DataTools.getUnsignedByteValue(buffer.get());
							buffer.get();
							buffer.get();
							buffer.get();
							break;
						case 2:
							byte[] _stringData = new byte[(int) _dataNumberOfComponent];
							for (int _i = 0; _i < _dataNumberOfComponent; _i++)
							{
								System.out.print("#");
								_stringData[_i] = buffer.get();
							}
							for (int _i = (int) _dataNumberOfComponent; _i < SIZE_OF_INT; _i++)
							{
								System.out.print(".");
								buffer.get();
							}
							_shortString = new String(_stringData);
							_dataValueOrOffset = 0;
							break;
						case 3: // unsigned short
							_dataValueOrOffset = DataTools.getUnsignedShortValue(buffer.getShort());
							buffer.get();
							buffer.get();
							break;
						case 4: // unsigned long
							_dataValueOrOffset = DataTools.getUnsignedIntValue(buffer.getInt());
							break;
						case 6: // signed byte
							_dataValueOrOffset = buffer.get();
							buffer.get();
							buffer.get();
							buffer.get();
							break;
						case 8: // signed short
							_dataValueOrOffset = buffer.getShort();
							buffer.get();
							buffer.get();
							break;
						case 9: // signed long
							_dataValueOrOffset = buffer.getInt();
							break;
						default:
							buffer.get();
							buffer.get();
							buffer.get();
							buffer.get();
							_dataValueOrOffset = 0;
							System.err.println("Unsupported dataFormat (" + _dataFormat + ")");
					}

				}
				_entries[_index] = new Entry(_tag, _dataFormat, _dataNumberOfComponent, _dataValueOrOffset, _shortString);
				if (null != _shortString)
				{
					System.out.println("ShortString : " + _shortString);
				}
			}
			catch (Exception _exception)
			{
				_exception.printStackTrace();
			}
		}
		long _offsetToNextDirectory = DataTools.getUnsignedIntValue(buffer.getInt());
		return new ImageFileDirectory(_entries, _offsetToNextDirectory);
	}

	private ImageFileDirectory.Entry[] myItems;

	private long myOffsetToNextDirectory;

	public ImageFileDirectory(ImageFileDirectory.Entry[] items, long offsetToNextDirectory)
	{
		super();
		myItems = items;
		myOffsetToNextDirectory = offsetToNextDirectory;
	}

	/**
	 * @return the items
	 */
	public ImageFileDirectory.Entry[] getItems()
	{
		return myItems;
	}

	/**
	 * @return the offsetToNextDirectory
	 */
	public long getOffsetToNextDirectory()
	{
		return myOffsetToNextDirectory;
	}
}