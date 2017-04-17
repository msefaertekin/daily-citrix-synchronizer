package org.safaertekin.dailycitrixsynchronizer; /*
$Id: SVNUtilsTest.java 12/3/15 8:51 PM mehsaf $
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
import org.junit.Ignore;
import org.junit.Test;
import org.tmatesoft.svn.core.SVNException;

public class SVNUtilsTest extends TestCase {
    private SVNUtils m_svnUtils;

    public SVNUtilsTest()
    {
        m_svnUtils = new SVNUtils("http://","username","password","C:\\");
    }

    @Test
    @Ignore
    public void testUpdateDirectory()
            throws SVNException
    {
        m_svnUtils.updateDirectory("path/subpath");
    }
}
