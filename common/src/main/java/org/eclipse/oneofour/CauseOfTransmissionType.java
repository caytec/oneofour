/*******************************************************************************
 * Copyright (c) 2014, 2015 IBH SYSTEMS GmbH and others.
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
package org.eclipse.oneofour;

import io.netty.buffer.ByteBuf;

public enum CauseOfTransmissionType
{
    SIZE_1
    {
        @Override
        public byte readCause ( final ByteBuf data )
        {
            return data.readByte ();
        }

        @Override
        public byte readOriginatorAddress ( final ByteBuf data )
        {
            return 0x00; // defaults to zero
        }

        @Override
        public void write ( final byte cause, final byte originatorAddress, final ByteBuf out )
        {
            out.writeByte ( cause );
        }
    },
    SIZE_2
    {
        @Override
        public byte readCause ( final ByteBuf data )
        {
            return data.readByte ();
        }

        @Override
        public byte readOriginatorAddress ( final ByteBuf data )
        {
            return data.readByte ();
        }

        @Override
        public void write ( final byte cause, final byte originatorAddress, final ByteBuf out )
        {
            out.writeByte ( cause );
            out.writeByte ( originatorAddress );
        }
    };

    public abstract byte readCause ( ByteBuf data );

    public abstract byte readOriginatorAddress ( ByteBuf data );

    public abstract void write ( final byte cause, byte originatorAddress, final ByteBuf out );
}
