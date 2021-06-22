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

public class UnnumberedControl extends APCIBase
{
    public static enum Function
    {
        STARTDT_ACT ( (byte)4 ),
        STARTDT_CONFIRM ( (byte)8 ),
        STOPDT_ACT ( (byte)16 ),
        STOPDT_CONFIRM ( (byte)32 ),
        TESTFR_ACT ( (byte)64 ),
        TESTFR_CONFIRM ( (byte)128 );

        private byte numericValue;

        Function ( final byte numericValue )
        {
            this.numericValue = numericValue;
        }

        public byte getNumericValue ()
        {
            return this.numericValue;
        }
    }

    private final Function function;

    public UnnumberedControl ( final Function function )
    {
        super ( APCIType.U );
        this.function = function;
    }

    public Function getFunction ()
    {
        return this.function;
    }

    @Override
    public String toString ()
    {
        return String.format ( "[APCI:U:%s]", this.function );
    }

}
