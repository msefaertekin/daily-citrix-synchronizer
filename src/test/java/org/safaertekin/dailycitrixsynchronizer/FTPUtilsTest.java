package org.safaertekin.dailycitrixsynchronizer; /*
$Id: FTPUtilsTest.java 12/3/15 7:45 PM mehsaf $
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

import junit.framework.TestCase;
import org.apache.commons.vfs2.FileSystemException;
import org.junit.Ignore;
import org.junit.Test;

public class FTPUtilsTest extends TestCase {
    private FTPUtils m_ftpUtils;

    public FTPUtilsTest()
    {
        m_ftpUtils = new FTPUtils("test.rebex.net","demo","password");
    }

    @Test
    @Ignore
    public void testSendFile() throws FileSystemException {
        m_ftpUtils.setFileName("resources/empty.txt");
        m_ftpUtils.sendFile();
    }
}
