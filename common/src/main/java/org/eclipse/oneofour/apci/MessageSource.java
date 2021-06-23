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
package org.eclipse.oneofour.apci;

import io.netty.channel.ChannelHandlerContext;

public interface MessageSource
{
    /**
     * The notify token which can be sent using the
     * {@link ChannelHandlerContext#write(Object)} methods to notify the
     * {@link MessageChannel} that
     * the source has new content.
     * <p>
     * <em>Note:</em> This does not mean that the MessageChannel will
     * immediately pick that content up.
     * </p>
     */
    public static final Object NOTIFY_TOKEN = new Object () {
        @Override
        public String toString ()
        {
            return "<NOTIFY_TOKEN>";
        }
    };

    /**
     * Poll the next message from the message source
     *
     * @return the next message to send, or <code>null</code> if there currently
     *         is no message
     */
    public Object poll ();
}
