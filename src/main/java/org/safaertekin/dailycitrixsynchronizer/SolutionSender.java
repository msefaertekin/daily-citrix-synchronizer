package org.safaertekin.dailycitrixsynchronizer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileSystemException;
import org.tmatesoft.svn.core.SVNException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SolutionSender
{
    public static String CONFIG_FILE_PATH = "C:\\workspace\\DailyCitrixSynchronizer\\resources\\config\\sender.conf";
    public static String KEY_VALUE_SEPERATOR = "=";
    public static String VALUE_SEPERATOR = ",";
    public String m_sourceFolder = null;
    String m_sftpSite,m_sftpUserName, m_sftpPassword;
    String m_svnSite,m_svnUserName,m_svnPassword,m_svnTargetDirectory;
    public String[] m_svnSubDirectories = null;
    public String[] m_mvnSubDirectories = null;

    public String getSourceFolder() {
        return m_sourceFolder;
    }

    public String getSftpSite() {
        return m_sftpSite;
    }

    public String getSftpUserName() {
        return m_sftpUserName;
    }

    public String getSftpPassword() {
        return m_sftpPassword;
    }

    public String getSvnSite() {
        return m_svnSite;
    }

    public String getSvnUserName() {
        return m_svnUserName;
    }

    public String getSvnPassword() {
        return m_svnPassword;
    }

    public String getSvnTargetDirectory() {
        return m_svnTargetDirectory;
    }

    public String[] getSvnSubDirectories() {
        return m_svnSubDirectories;
    }

    public static void main(String[] args)
            throws IOException, SVNException
    {
        new SolutionSender().doMain(args);
    }

    public void doMain(String[] args)
            throws IOException,SVNException
    {
        readConfFile();
	    removeSourceFolder();
        svnUp();
//        mavenCompile();
        generateZipFiles();
        copyToFtp();
    }

    private void readConfFile()
            throws IOException
    {
        readConfFile(CONFIG_FILE_PATH);
    }

    protected void readConfFile(String confFile)
            throws IOException
    {
        File file = new File(confFile);
        BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
        String line;
        while((line = bufferedReader.readLine()) != null )
        {
            parseLine(line);
        }
    }

	private void removeSourceFolder()
			throws IOException
	{
			FileUtils.deleteDirectory( new File(m_sourceFolder) );
	}

    private void svnUp()
            throws SVNException
    {
        SVNUtils svnUtils = new SVNUtils(m_svnSite,m_svnUserName,m_svnPassword,m_svnTargetDirectory);
        for( String svnSubDirectory: m_svnSubDirectories )
        {
            svnUtils.updateDirectory( svnSubDirectory );
        }
    }

    private void mavenCompile()
    {
        MavenUtils mavenUtils = new MavenUtils();
        for( String mvnSubDirectory: m_mvnSubDirectories )
        {
            mavenUtils.compileDirectory( m_sourceFolder + mvnSubDirectory );
        }
    }

    private void parseLine(String line)
    {
        String[] keyValuePair = line.split(KEY_VALUE_SEPERATOR);
        String key = keyValuePair[0];
        String value = keyValuePair[1];
        if( key.equals( "FTP_SITE" ) )
        {
            m_sftpSite = value;

        }
        else if( key.equals( "FTP_USER_NAME" ) )
        {
            m_sftpUserName = value;

        }
        else if( key.equals( "FTP_PASSWORD" ) )
        {
            m_sftpPassword = value;

        }
        else if( key.equals( "SOURCE_FOLDER" ) )
        {
            m_sourceFolder = value;

        }
        else if( key.equals( "SVN_SITE" ) )
        {
            m_svnSite = value;

        }
        else if( key.equals( "SVN_USER_NAME" ) )
        {
            m_svnUserName = value;

        }
        else if( key.equals( "SVN_PASSWORD" ) )
        {
            m_svnPassword = value;

        }
        else if( key.equals( "SVN_TARGET_DIRECTORY" ) )
        {
            m_svnTargetDirectory = value;

        }
        else if( key.equals( "SVN_SUBDIRECTORIES" ) )
        {
            m_svnSubDirectories = value.split( VALUE_SEPERATOR );

        }
        else if( key.equals( "MVN_SUBDIRECTORIES" ) )
        {
            m_mvnSubDirectories = value.split( VALUE_SEPERATOR );

        }
        else
        {
            System.out.println( "UNKNOWN CONFIG VALUE:" + Arrays.toString( keyValuePair ) );

        }
    }

    private void generateZipFiles()
    {
            ZipUtils appZip = new ZipUtils( m_sourceFolder );
            appZip.generateFileList(new File( m_sourceFolder ));
            appZip.zipIt();
    }

    private void copyToFtp() throws FileSystemException {
        FTPUtils ftpUtils = new FTPUtils(m_sftpSite,m_sftpUserName, m_sftpPassword);

        ftpUtils.setFileName( org.safaertekin.dailycitrixsynchronizer.FileUtils.trimFolderName( m_sourceFolder ) + ".zip" );
        ftpUtils.sendFile();
    }
}
