package org.safaertekin.dailycitrixsynchronizer;
/*
$Id: ZipUtilsTest.java 12/3/15 7:34 PM mehsaf $
*/

import junit.framework.TestCase;

import java.io.File;

public class ZipUtilsTest extends TestCase {
    private ZipUtils m_zipUtils;
    public ZipUtilsTest()
    {
        m_zipUtils = new ZipUtils("src/test/resources/testdir");
    }

    public void testZipIt()
    {
        m_zipUtils.zipIt();
        File file = new File("src/test/resources/testdir.zip");
        assertTrue(file.exists());
        file.delete();
    }
}
