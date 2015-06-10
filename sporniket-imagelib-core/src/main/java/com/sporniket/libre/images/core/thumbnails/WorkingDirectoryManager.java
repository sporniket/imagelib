/**
 * 
 */
package com.sporniket.libre.images.core.thumbnails;

import java.io.File;

import com.sporniket.libre.io.FileTools;

/**
 * Interface defining the file management inside the directory that stores thumbnails.
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
public interface WorkingDirectoryManager
{
	/**
	 * Simple implementation.
	 * 
	 * @author dsporn
	 * 
	 * @version 15.06.00-SNAPSHOT
	 * @since 15.06.00-SNAPSHOT
	 */
	public static class SimpleManager implements WorkingDirectoryManager
	{
		/**
		 * Working directory.
		 * 
		 * @since 15.06.00-SNAPSHOT
		 */
		private File myWorkingDirectory;

		/**
		 * Get workingDirectory.
		 * 
		 * @return the workingDirectory.
		 * @since 15.06.00-SNAPSHOT
		 */
		public File getWorkingDirectory()
		{
			return myWorkingDirectory;
		}

		/**
		 * @param workingDirectory the working directory.
		 * @since 15.06.00-SNAPSHOT
		 */
		public SimpleManager(File workingDirectory)
		{
			setWorkingDirectory(workingDirectory);
		}

		/**
		 * Change the workingDirectory.
		 * 
		 * @param workingDirectory the working directory.
		 * @since 15.06.00-SNAPSHOT
		 */
		private void setWorkingDirectory(File workingDirectory)
		{
			if (!workingDirectory.exists() || !workingDirectory.isDirectory())
			{
				throw new IllegalArgumentException("The working directory MUST exist and MUST be a directory : "
						+ workingDirectory.getAbsolutePath());
			}
			myWorkingDirectory = workingDirectory;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.sporniket.libre.images.core.thumbnails.ThumbnailWorkingDirectoryManager#getStorageDirectory(java.lang.String)
		 * 
		 * @since 15.06.00-SNAPSHOT
		 */
		public File getStorageDirectory(String thumbnailFileName)
		{
			return FileTools.createFileBalancingDirectoryDescriptor(getWorkingDirectory(), thumbnailFileName, 2, 2);
		}

	}

	/**
	 * Compute a subdirectory to create, that will store the thumbnail.
	 * 
	 * <p>
	 * This allow file balancing.
	 * 
	 * @param thumbnailFileName the name of the thumbnail.
	 * @return a descriptor for a directory, to create using {@link File#mkdirs()}.
	 * @since 15.06.00-SNAPSHOT
	 */
	public File getStorageDirectory(String thumbnailFileName);
}
