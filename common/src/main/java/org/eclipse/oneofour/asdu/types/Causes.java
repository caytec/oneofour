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
 *     Red Hat Inc - minor cleanups
 *******************************************************************************/
package org.eclipse.oneofour.asdu.types;

public final class Causes
{
    private static final Cause[] causes = new Cause[64];

    static
    {
        for ( final Cause cause : StandardCause.values () )
        {
            causes[cause.getValue ()] = cause;
        }
        for ( int i = 14; i < 20; i++ )
        {
            causes[i] = new CustomCause ( i );
        }
        for ( int i = 42; i < 44; i++ )
        {
            causes[i] = new CustomCause ( i );
        }
        for ( int i = 49; i < 64; i++ )
        {
            causes[i] = new CustomCause ( i );
        }
    }

    private Causes ()
    {
    }

    public static Cause valueOf ( final int value )
    {
        return causes[value];
    }

    public static boolean isInterrogation ( final Cause cause )
    {
        final short value = cause.getValue ();
        return value >= StandardCause.STATION_REQUEST.getValue () && value <= StandardCause.GROUP_REQUEST_9.getValue ();
    }

}
