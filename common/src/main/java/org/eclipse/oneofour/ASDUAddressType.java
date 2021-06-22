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
package org.eclipse.oneofour;

import io.netty.buffer.ByteBuf;

public enum ASDUAddressType
{
    SIZE_1
    {
        @Override
        public int read ( final ByteBuf data )
        {
            final short value = data.readUnsignedByte ();
            if ( value == 0xFF )
            {
                // since we use 16bit internally, we have to fill the other bits as well
                return 0xFFFF;
            }
            return value;
        }

        @Override
        public void write ( final int address, final ByteBuf out )
        {
            out.writeByte ( address );
        }
    },
    SIZE_2
    {
        @Override
        public int read ( final ByteBuf data )
        {
            return data.readUnsignedShort ();
        }

        @Override
        public void write ( final int address, final ByteBuf out )
        {
            out.writeShort ( address );
        }
    };

    public abstract int read ( ByteBuf data );

    public abstract void write ( final int address, final ByteBuf out );
}
