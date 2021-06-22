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
package org.eclipse.oneofour.server.data.event;

import org.eclipse.oneofour.asdu.ASDUHeader;
import org.eclipse.oneofour.asdu.message.MeasuredValueShortFloatingPointSequence;
import org.eclipse.oneofour.asdu.message.MeasuredValueShortFloatingPointSingle;
import org.eclipse.oneofour.asdu.message.MeasuredValueShortFloatingPointTimeSingle;

public class SimpleFloatBuilder implements MessageBuilderFactory<Float>
{
    private final boolean withTimestamps;

    public SimpleFloatBuilder ( final boolean withTimestamps )
    {
        this.withTimestamps = withTimestamps;
    }

    @Override
    public MessageBuilder<Float, ?> create ()
    {
        return new AbstractMessageBuilder<Float, Object> ( Float.class, 20, 20, this.withTimestamps ? 10 : -1 ) {
            @Override
            public Object build ()
            {
                validateStart ();

                final ASDUHeader header = new ASDUHeader ( this.causeOfTransmission, this.asduAddress );

                if ( isWithTimestamps () )
                {
                    return MeasuredValueShortFloatingPointTimeSingle.create ( header, this.entries );
                }
                else if ( isContinuous () )
                {
                    return MeasuredValueShortFloatingPointSequence.create ( getStartAddress (), header, getValues () );
                }
                else
                {
                    return MeasuredValueShortFloatingPointSingle.create ( header, this.entries );
                }
            }
        };
    }

}
