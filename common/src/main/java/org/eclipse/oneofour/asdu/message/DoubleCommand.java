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
package org.eclipse.oneofour.asdu.message;

import org.eclipse.oneofour.ProtocolOptions;
import org.eclipse.oneofour.asdu.ASDUHeader;
import org.eclipse.oneofour.asdu.types.ASDU;
import org.eclipse.oneofour.asdu.types.Cause;
import org.eclipse.oneofour.asdu.types.InformationObjectAddress;
import org.eclipse.oneofour.asdu.types.InformationStructure;

import io.netty.buffer.ByteBuf;

@ASDU ( id = 46, name = "C_DC_NA_1", informationStructure = InformationStructure.SINGLE )
public class DoubleCommand extends AbstractInformationObjectMessage implements MirrorableMessage<DoubleCommand>
{
    private final boolean state;

    private final byte type;

    private final boolean execute;

    public DoubleCommand ( final ASDUHeader header, final InformationObjectAddress informationObjectAddress, final boolean state, final byte type, final boolean execute )
    {
        super ( header, informationObjectAddress );
        this.state = state;
        this.type = type;
        this.execute = execute;
    }

    public DoubleCommand ( final ASDUHeader header, final InformationObjectAddress informationObjectAddress, final boolean state )
    {
        this ( header, informationObjectAddress, state, (byte)0, true );
    }

    public byte getType ()
    {
        return this.type;
    }

    public boolean isState ()
    {
        return this.state;
    }

    public boolean getState ()
    {
        return this.state;
    }

    public boolean isExecute ()
    {
        return this.execute;
    }

    @Override
    public DoubleCommand mirror ( final Cause cause, final boolean positive )
    {
        return new DoubleCommand ( this.header.clone ( cause, positive ), this.informationObjectAddress, this.state, this.type, this.execute );
    }

    @Override
    public void encode ( final ProtocolOptions options, final ByteBuf out )
    {
        EncodeHelper.encodeHeader ( this, options, null, this.header, out );
        byte b = 0;

        b |= this.state ? 0b00000010 : 0b00000001;
        b |= this.type << 2 & 0b011111100;
        b |= this.execute ? 0 : 0b100000000;

        this.informationObjectAddress.encode ( options, out );

        out.writeByte ( b );
    }

    public static DoubleCommand parse ( final ProtocolOptions options, final byte length, final ASDUHeader header, final ByteBuf data )
    {
        final InformationObjectAddress address = InformationObjectAddress.parse ( options, data );

        final byte b = data.readByte ();

        final boolean state = ( b & 0b00000001 ) > 0;
        final byte type = (byte) ( ( b & 0b011111100 ) >> 2 );
        final boolean execute = ! ( ( b & 0b100000000 ) > 0 );

        return new DoubleCommand ( header, address, state, type, execute );
    }

}
