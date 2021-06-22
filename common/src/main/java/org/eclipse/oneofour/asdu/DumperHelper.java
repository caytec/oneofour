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
 *******************************************************************************/
package org.eclipse.oneofour.asdu;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class DumperHelper
{
    private DumperHelper ()
    {
    }

    public static String dump ( final Dumpable object )
    {
        final StringWriter sw = new StringWriter ();
        dump ( object, new Dumper ( new PrintWriter ( sw ) ) );
        return sw.toString ();
    }

    public static void dump ( final Dumpable object, final PrintWriter writer )
    {
        dump ( object, new Dumper ( writer ) );
    }

    public static void dump ( final Dumpable object, final Writer writer )
    {
        if ( writer instanceof PrintWriter )
        {
            dump ( object, (PrintWriter)writer );
        }
        else
        {
            dump ( object, new PrintWriter ( writer ) );
        }
    }

    public static void dump ( final Dumpable object, final Dumper dumper )
    {
        dumper.start ( object );
        object.dump ( dumper );
        dumper.end ();
    }
}
