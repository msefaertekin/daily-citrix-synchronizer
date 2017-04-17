package org.safaertekin.dailycitrixsynchronizer; /*
$Id: ConfigReaderTest.java 12/3/15 9:16 PM mehsaf $
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

import java.io.IOException;

public class ConfigReaderTest extends TestCase {
    public SolutionSender m_solutionSender;

    public ConfigReaderTest()
    {
        m_solutionSender = new SolutionSender();
    }

    public void testReadConf()
            throws IOException
    {
        m_solutionSender.readConfFile("src/test/resources/sender_test.conf");
        assertEquals(m_solutionSender.getSftpPassword(),"xxx");
    }
}
