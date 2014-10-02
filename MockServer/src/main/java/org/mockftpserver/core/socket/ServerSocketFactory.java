/*
 * Copyright 2007 the original author or authors.
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
package org.mockftpserver.core.socket;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Interface for factory that creates new {@link ServerSocket} instances.
 * Using this abstraction enables unit testing.
 * 
 * @version $Revision: 8 $ - $Date: 2007-12-19 03:42:32 +0000 (Wed, 19 Dec 2007) $
 * 
 * @author Chris Mair
 */
public interface ServerSocketFactory {
    
    /**
     * Create a new ServerSocket for the specified port
     * @param port - the port
     * @return a new ServerSocket
     * @throws IOException
     */
    public ServerSocket createServerSocket(int port) throws IOException;
}