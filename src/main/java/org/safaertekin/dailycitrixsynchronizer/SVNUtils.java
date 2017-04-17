package org.safaertekin.dailycitrixsynchronizer; /*
$Id: SVNUtils.java 12/2/15 11:23 PM mehsaf $
    * Copyright (c) 2003-2015 by SMARTS Group - A NASDAQ Company.
    *
    * GPO Box 970, Sydney NSW 2001, Australia
    *
    * All rights reserved.
    *
    * This software is the confidential and proprietary information of the SMARTS
    * Group. You shall not disclose such Confidential Information and shall use it
    * only in accordance with the terms of the license agreement you entered into with
    * SMARTS Group.
    *
    * Parts of this work are derived from or based on original software copyright from
    * SMARTS Limited, used with permission.
*/

import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.fs.FSRepository;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.io.*;
import org.tmatesoft.svn.core.io.diff.SVNDeltaProcessor;
import org.tmatesoft.svn.core.io.diff.SVNDiffWindow;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class SVNUtils {
    SVNRepository m_repository;
    String m_site,m_userName,m_password;
    String m_targetDirectory;

    public SVNUtils(String site, String userName, String password, String targetDirectory)
    {
        m_site = site;
        m_userName = userName;
        m_password = password;
        m_targetDirectory = targetDirectory;
    }

    private void authenticate(String subDirectory)
    {
        try {
            ISVNAuthenticationManager authManager = new BasicAuthenticationManager( m_userName , m_password );

            SVNURL url = SVNURL.parseURIDecoded(m_site + subDirectory);
            m_repository = SVNRepositoryFactory.create(url, null);

            //set an auth manager which will provide user credentials
            m_repository.setAuthenticationManager(authManager);


        } catch (SVNException e) {
            //handle exception
        }
    }

    public void updateDirectory(String subDirectory)
            throws SVNException
    {
        authenticate(subDirectory);

        long latestRevision = m_repository.getLatestRevision( );

        ISVNReporterBaton reporterBaton = new ExportReporterBaton( latestRevision );
//        Prepare filesystem directory (export destination).
        File exportDir = new File( m_targetDirectory + subDirectory );
        if ( !exportDir.exists( ) ) {
            exportDir.mkdirs( );
        }

        //Get latest repository revision. We will export repository contents at this very revision.
        ISVNEditor exportEditor = new ExportEditor( exportDir );

        m_repository.update(latestRevision, null, true, reporterBaton, exportEditor);
        System.out.println( "Exported revision: " + latestRevision );
    }

    public class ExportReporterBaton implements ISVNReporterBaton {

        private long exportRevision;

        public ExportReporterBaton( long revision ){
            exportRevision = revision;
        }

        public void report( ISVNReporter reporter ) throws SVNException {
            try {
                reporter.setPath( "" , null , exportRevision , true );
                reporter.finishReport( );
            } catch( SVNException svne ) {
                reporter.abortReport( );
                System.out.println( "Report failed" );
            }
        }
    }

    public class ExportEditor implements ISVNEditor {

        private File myRootDirectory;
        private SVNDeltaProcessor myDeltaProcessor;

        public ExportEditor( File root ) {
            myRootDirectory = root;

        /*
         * Utility class that will help us to transform 'deltas' sent by the
         * server to the new file contents.
         */
            myDeltaProcessor = new SVNDeltaProcessor( );
        }

        public void targetRevision( long revision ) throws SVNException {
        }

        public void openRoot( long revision ) throws SVNException {
        }

        public void addDir( String path , String copyFromPath , long copyFromRevision ) throws SVNException {
            File newDir = new File( myRootDirectory , path );
            if ( !newDir.exists( ) ) {
                if ( !newDir.mkdirs( ) ) {
                    SVNErrorMessage err = SVNErrorMessage.create( SVNErrorCode.IO_ERROR , "error: failed to add the directory ''{0}''." , newDir );
                    throw new SVNException( err );
                }
            }
            System.out.println( "dir added: " + path );
        }

        public void openDir(String s, long l) throws SVNException {

        }

        public void changeDirProperty(String s, SVNPropertyValue svnPropertyValue) throws SVNException {

        }


        public void addFile( String path , String copyFromPath , long copyFromRevision ) throws SVNException {
            File file = new File( myRootDirectory , path );
            if ( file.exists( ) ) {
                SVNErrorMessage err = SVNErrorMessage.create( SVNErrorCode.IO_ERROR , "error: exported file ''{0}'' already exists!" , file );
                throw new SVNException( err );
            }

            try {
                file.createNewFile( );
            } catch ( IOException e ) {
                SVNErrorMessage err = SVNErrorMessage.create( SVNErrorCode.IO_ERROR , "error: cannot create new  file ''{0}''" , file );
                throw new SVNException( err );
            }
        }

        public void openFile( String path , long revision ) throws SVNException {
        }

        public void changeFileProperty(String s, String s1, SVNPropertyValue svnPropertyValue) throws SVNException {

        }

        public void changeFileProperty( String path , String name , String value ) throws SVNException {
        }

        public void applyTextDelta( String path , String baseChecksum ) throws SVNException {
            File file = null;
            myDeltaProcessor.applyTextDelta( file , new File( myRootDirectory , path ) , false );
        }

        public OutputStream textDeltaChunk( String path , SVNDiffWindow diffWindow )   throws SVNException {
            return myDeltaProcessor.textDeltaChunk( diffWindow );
        }

        public void textDeltaEnd(String path) throws SVNException {
            myDeltaProcessor.textDeltaEnd( );
        }

        public void closeFile( String path , String textChecksum ) throws SVNException {
            System.out.println( "file added: " + path );
        }

        public void closeDir( ) throws SVNException {
        }

        public void deleteEntry( String path , long revision ) throws SVNException {
        }

        public void absentDir( String path ) throws SVNException {
        }

        public void absentFile( String path ) throws SVNException {
        }

        public SVNCommitInfo closeEdit( ) throws SVNException {
            return null;
        }

        public void abortEdit( ) throws SVNException {
        }
    }

}
