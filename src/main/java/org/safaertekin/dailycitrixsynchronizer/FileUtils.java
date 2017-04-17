package org.safaertekin.dailycitrixsynchronizer;

import java.io.File;

/**
 * Created by mehsaf on 12/3/2015.
 */
public class FileUtils
{
	public static String trimFolderName(String folderName)
	{
		File file= new File(folderName);
		return file.getName();
	}
}
