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
 *     Red Hat Inc - minor enhancements
 *******************************************************************************/
package org.eclipse.oneofour.asdu.types;

import org.eclipse.oneofour.ProtocolOptions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class InformationObjectAddress
{
    public static final InformationObjectAddress DEFAULT = new InformationObjectAddress ( 0 );

    private final int address;

    public static InformationObjectAddress valueOf ( final int address )
    {
        return new InformationObjectAddress ( address );
    }

    public InformationObjectAddress ( final int address )
    {
        if ( address < 0 || address > 0x00FFFFFF )
        {
            throw new IllegalArgumentException ( String.format ( "Address value must be between 0 and 0x00FFFFFF" ) );
        }
        this.address = address;
    }

    public int getAddress ()
    {
        return this.address;
    }

    public void encode ( final ProtocolOptions options, final ByteBuf out )
    {
        options.getInformationObjectAddressType ().write ( this.address, out );

    }

    public InformationObjectAddress increment ()
    {
        return increment ( 1 );
    }

    public InformationObjectAddress increment ( final int count )
    {
        return new InformationObjectAddress ( this.address + count );
    }

    public static InformationObjectAddress parse ( final ProtocolOptions options, final ByteBuf data )
    {
        return new InformationObjectAddress ( options.getInformationObjectAddressType ().read ( data ) );
    }

    @Override
    public int hashCode ()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.address;
        return result;
    }

    @Override
    public boolean equals ( final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass () != obj.getClass () )
        {
            return false;
        }
        final InformationObjectAddress other = (InformationObjectAddress)obj;
        if ( this.address != other.address )
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString ()
    {
        final int add[] = toArray ();
        return String.format ( "[%d-%d-%d # %d]", add[0], add[1], add[2], this.address );
    }

    public int[] toArray ()
    {
        final ByteBuf buf = Unpooled.buffer ( 4 );
        buf.writeMedium ( this.address );
        return new int[] { buf.getUnsignedByte ( 0 ), buf.getUnsignedByte ( 1 ), buf.getUnsignedByte ( 2 ) };
    }

    public static InformationObjectAddress fromArray ( final int[] data )
    {
        if ( data.length > 3 )
        {
            throw new IllegalArgumentException ( "Address may only have a maximum of 3 segments" );
        }

        int address = 0;
        for ( final int seg : data )
        {
            address = address << 8 | seg;
        }
        return valueOf ( address );
    }

    public static InformationObjectAddress fromString ( final String value )
    {
        int address = 0;
        for ( final String tok : value.split ( "-" ) )
        {
            address = address << 8 | Integer.parseInt ( tok );
        }
        return valueOf ( address );
    }

}
