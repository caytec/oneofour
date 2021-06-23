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
package org.eclipse.oneofour.io;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.eclipse.oneofour.asdu.message.MirrorableMessage;
import org.eclipse.oneofour.asdu.types.Cause;
import org.eclipse.oneofour.asdu.types.StandardCause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;

public class AbstractModuleHandler extends ChannelInboundHandlerAdapter
{
    private final static Logger logger = LoggerFactory.getLogger ( AbstractModuleHandler.DefaultMirrorCommand.class );

    protected final class DefaultMirrorCommand<T extends MirrorableMessage<T>> extends MirrorCommandImpl<T>
    {
        public DefaultMirrorCommand ( final ChannelHandlerContext ctx, final T original )
        {
            super ( ctx, original );
        }

        @Override
        public T mirrorCommand ( final T original, final Cause newCause, final boolean positive )
        {
            return original.mirror ( newCause, positive );
        }
    }

    protected static abstract class MirrorCommandImpl<T> implements MirrorCommand
    {
        private final ChannelHandlerContext ctx;

        private final T original;

        public MirrorCommandImpl ( final ChannelHandlerContext ctx, final T original )
        {
            this.ctx = ctx;
            this.original = original;
        }

        protected abstract T mirrorCommand ( T original, Cause newCause, boolean positive );

        @Override
        public void sendActivationTermination ()
        {
            this.ctx.writeAndFlush ( mirrorCommand ( this.original, StandardCause.ACTIVATION_TERMINATION, true ) );
        }

        @Override
        public void sendActivationConfirm ( final boolean positive )
        {
            this.ctx.writeAndFlush ( mirrorCommand ( this.original, StandardCause.ACTIVATION_CONFIRM, positive ) );
        }
    }

    protected abstract class CloseOnFailureCallback implements FutureCallback<Void>
    {
        private final ChannelHandlerContext ctx;

        public CloseOnFailureCallback ( final ChannelHandlerContext ctx )
        {
            this.ctx = ctx;
        }

        @Override
        public void onFailure ( final Throwable t )
        {
            logger.warn ( "Failed", t );
            this.ctx.close ();
        }
    }

    public AbstractModuleHandler ()
    {
        super ();
    }
}