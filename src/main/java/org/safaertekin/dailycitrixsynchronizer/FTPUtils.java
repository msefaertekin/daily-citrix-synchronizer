package org.safaertekin.dailycitrixsynchronizer;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.File;

public class FTPUtils {
    private String m_ftpSite = "";
    private String m_user = "";
    private String m_password = "";
    private String m_fileName = "";

    public FTPUtils(String ftpSite, String user, String password) {
        this.m_ftpSite = ftpSite;
        this.m_user = user;
        this.m_password = password;
    }

    public void setFileName( String fileName )
    {
        m_fileName = fileName;
    }

    public void sendFile() throws FileSystemException {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        manager.init();
        //check if the file exists
        String filepath = m_fileName;
        File file = new File(filepath);
        if (!file.exists())
            throw new RuntimeException("Error. Local file not found");
        FileSystemOptions opts = new FileSystemOptions();
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, true);
        SftpFileSystemConfigBuilder.getInstance().setTimeout(opts, 10000);

        //Create the SFTP URI using the host name, userid, password,  remote path and file name
        String sftpUri = "sftp://" + m_user + ":" + m_password +  "@" + m_ftpSite + "/" +
                file.getName();

        // Create local file object
        FileObject localFile = manager.resolveFile(file.getAbsolutePath());

        // Create remote file object
        FileObject remoteFile = manager.resolveFile(sftpUri,opts);

        // Copy local file to sftp server
        remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
        System.out.println("File upload successful");

    }


}
