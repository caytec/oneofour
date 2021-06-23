/*******************************************************************************
 * Copyright (c) 2014, 2016 IBH SYSTEMS GmbH and others.
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
 *     Red Hat Inc - change lifecycle model
 *******************************************************************************/
package org.eclipse.oneofour.server.data;

import org.eclipse.oneofour.apci.MessageChannel;
import org.eclipse.oneofour.asdu.MessageManager;
import org.eclipse.oneofour.asdu.message.MessageRegistrator;
import org.eclipse.oneofour.server.Server;
import org.eclipse.oneofour.server.ServerModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.socket.SocketChannel;

public class DataModule implements ServerModule
{
    private final static Logger logger = LoggerFactory.getLogger ( DataModule.class );

    private final DataModel dataModel;

    private final DataModuleOptions options;

    public DataModule ( final DataModuleOptions options, final DataModel dataModel )
    {
        this.options = options;
        this.dataModel = dataModel;
    }

    @Override
    public void initializeServer ( final Server server, final MessageManager manager )
    {
        new MessageRegistrator ().register ( manager );
    }

    @Override
    public void dispose ()
    {
        this.dataModel.stop ();
    }

    @Override
    public void initializeChannel ( final SocketChannel channel, final MessageChannel messageChannel )
    {
        logger.debug ( "Init channel: {}", channel );
        this.dataModel.start ();
        channel.pipeline ().addLast ( new DataModuleHandler ( this.options, messageChannel, this.dataModel ) );
    }
}
