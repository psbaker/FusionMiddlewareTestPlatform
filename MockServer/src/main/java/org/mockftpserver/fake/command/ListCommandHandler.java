/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mockftpserver.fake.command;

import org.mockftpserver.core.command.Command;
import org.mockftpserver.core.command.ReplyCodes;
import org.mockftpserver.core.session.Session;
import org.mockftpserver.core.util.StringUtil;
import org.mockftpserver.fake.filesystem.FileSystemEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CommandHandler for the LIST command. Handler logic:
 * <ol>
 * <li>If the user has not logged in, then reply with 530 and terminate</li>
 * <li>Send an initial reply of 150</li>
 * <li>If the current user does not have read access to the file or directory to be listed, then reply with 550 and terminate</li>
 * <li>If an error occurs during processing, then send a reply of 451 and terminate</li>
 * <li>If the optional pathname parameter is missing, then send a directory listing for
 * the current directory across the data connection</li>
 * <li>Otherwise, if the optional pathname parameter specifies a directory or group of files,
 * then send a directory listing for the specified directory across the data connection</li>
 * <li>Otherwise, if the optional pathname parameter specifies a filename, then send information
 * for the specified file across the data connection</li>
 * <li>Send a final reply with 226</li>
 * </ol>
 *
 * @author Chris Mair
 * @version $Revision: 220 $ - $Date: 2009-02-08 04:05:06 +0000 (Sun, 08 Feb 2009) $
 */
public class ListCommandHandler extends AbstractFakeCommandHandler {

    protected void handle(Command command, Session session) {
    	
        verifyLoggedIn(session);
        sendReply(session, ReplyCodes.TRANSFER_DATA_INITIAL_OK);

        String path = getRealPath(session, command.getParameter(0));

        // User must have read permission to the path
        if (getFileSystem().exists(path)) {
        	//System.out.println("FileSystem path exists: " + getFileSystem().exists(path));
            this.replyCodeForFileSystemException = ReplyCodes.READ_FILE_ERROR;
            verifyReadPermission(session, path);
        }
        
        //System.out.println("Read permission verified");

        this.replyCodeForFileSystemException = ReplyCodes.SYSTEM_ERROR;
        List fileEntries = getFileSystem().listFiles(path);
        Iterator iter = fileEntries.iterator();
        List lines = new ArrayList();
        while (iter.hasNext()) {
            FileSystemEntry entry = (FileSystemEntry) iter.next();
            lines.add(getFileSystem().formatDirectoryListing(entry));
            
        }
        String result = StringUtil.join(lines, endOfLine());
        result += result.length() > 0 ? endOfLine() : "";

      //  System.out.println("RESULT" + result);
        
        session.openDataConnection();
        LOG.info("Sending [" + result + "]");
        session.sendData(result.getBytes(), result.length());
        

        sendReply(session, ReplyCodes.TRANSFER_DATA_FINAL_OK);
        
        session.closeDataConnection();
    }

}