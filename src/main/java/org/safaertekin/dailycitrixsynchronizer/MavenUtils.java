package org.safaertekin.dailycitrixsynchronizer; /*
$Id: MavenUtils.java 12/4/15 4:30 AM mehsaf $
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

import org.apache.maven.cli.MavenCli;
import org.apache.commons.logging.*;

public class MavenUtils {
    public MavenUtils()
    {
    }

    public void compileDirectory(String directory)
    {
        MavenCli mavenCli = new MavenCli();
        int result = mavenCli.doMain(new String[]{"compile"},
                directory,
                System.out, System.out);
        System.out.println("result: " + result);
    }
}
