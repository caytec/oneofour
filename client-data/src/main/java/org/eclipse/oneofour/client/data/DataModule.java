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
package org.eclipse.oneofour.client.data;

import io.netty.channel.socket.SocketChannel;

import org.eclipse.oneofour.apci.MessageChannel;
import org.eclipse.oneofour.asdu.MessageManager;
import org.eclipse.oneofour.asdu.message.MessageRegistrator;
import org.eclipse.oneofour.client.Client;
import org.eclipse.oneofour.client.ClientModule;

public class DataModule implements ClientModule
{
    private final DataHandler dataHandler;

    private final DataModuleOptions options;

    private Runnable startTimers;

    public DataModule ( final DataHandler dataHandler, final DataModuleOptions options )
    {
        this.dataHandler = dataHandler;
        this.options = options;
    }

    @Override
    public void initializeClient ( final Client client, final MessageManager manager )
    {
        new MessageRegistrator ().register ( manager );
    }

    @Override
    public void initializeChannel ( final SocketChannel channel, final MessageChannel messageChannel )
    {
        channel.pipeline ().addLast ( new DataModuleHandler ( this.dataHandler, this.options ) );
        startTimers = new Runnable () {
            @Override
            public void run ()
            {
                messageChannel.startTimers ();
            }
        };
    }

    @Override
    public void dispose ()
    {
    }

    @Override
    public void requestStartData ()
    {
        if ( this.dataHandler != null && this.startTimers != null )
        {
            this.startTimers.run ();
            this.dataHandler.requestStartData ();
        }
    }
}
