/*******************************************************************************
 * Copyright (c) 2014 IBH SYSTEMS GmbH and others.
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBH SYSTEMS GmbH - initial API and implementation
 *******************************************************************************/
package org.eclipse.oneofour.server.testing;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.oneofour.ProtocolOptions;
import org.eclipse.oneofour.ProtocolOptions.Builder;
import org.eclipse.oneofour.server.Server;
import org.eclipse.oneofour.server.ServerModule;
import org.eclipse.oneofour.server.data.DataModule;
import org.eclipse.oneofour.server.data.DataModuleOptions;
import org.eclipse.oneofour.server.data.testing.SineDataModel;

public class Application
{
    public static void main ( final String[] args ) throws Exception
    {
        final List<ServerModule> modules = new LinkedList<> ();

        final Builder builder = new ProtocolOptions.Builder ();

        final DataModuleOptions dataModuleOptions = new DataModuleOptions.Builder ().build ();

        builder.setMaxUnacknowledged ( (short)10 );
        builder.setAcknowledgeWindow ( (short) ( builder.getMaxUnacknowledged () * 0.66 ) );

        final SineDataModel dataModel = new SineDataModel ( 1000 );
        modules.add ( new DataModule ( dataModuleOptions, dataModel ) );
        try ( Server server = new Server ( (short)2404, builder.build (), modules ) )
        {
            while ( true )
            {
                Thread.sleep ( 1000 );
            }
        }
    }
}
