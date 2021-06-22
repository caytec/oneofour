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

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.oneofour.ProtocolOptions;
import org.eclipse.oneofour.asdu.ASDUHeader;
import org.eclipse.oneofour.asdu.types.DoublePoint;
import org.eclipse.oneofour.asdu.types.InformationEntry;
import org.eclipse.oneofour.asdu.types.InformationObjectAddress;
import org.eclipse.oneofour.asdu.types.TypeHelper;
import org.eclipse.oneofour.asdu.types.Value;

public abstract class AbstractDoublePointBaseSingle extends AbstractMessage
{
    private final List<InformationEntry<DoublePoint>> entries;

    private final boolean withTimestamp;

    public AbstractDoublePointBaseSingle ( final ASDUHeader header, final List<InformationEntry<DoublePoint>> entries, final boolean withTimestamp )
    {
        super ( header );
        this.withTimestamp = withTimestamp;
        this.entries = entries;
    }

    public List<InformationEntry<DoublePoint>> getEntries ()
    {
        return this.entries;
    }

    @Override
    public void encode ( final ProtocolOptions options, final ByteBuf out )
    {
        EncodeHelper.encodeHeader ( this, options, this.entries.size (), this.header, out );

        for ( final InformationEntry<DoublePoint> entry : this.entries )
        {
            entry.getAddress ().encode ( options, out );
            TypeHelper.encodeDoublePointValue ( options, out, entry.getValue (), this.withTimestamp );
        }
    }

    protected static List<InformationEntry<DoublePoint>> parseEntries ( final ProtocolOptions options, final byte length, final ByteBuf data, final boolean withTimestamp )
    {
        final List<InformationEntry<DoublePoint>> values = new ArrayList<> ( length );
        for ( int i = 0; i < length; i++ )
        {
            final InformationObjectAddress address = InformationObjectAddress.parse ( options, data );
            final Value<DoublePoint> value = TypeHelper.parseDoublePointValue ( options, data, withTimestamp );
            values.add ( new InformationEntry<> ( address, value ) );
        }
        return values;
    }

}
