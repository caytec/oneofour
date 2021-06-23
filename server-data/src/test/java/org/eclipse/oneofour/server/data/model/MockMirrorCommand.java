/*******************************************************************************
 * Copyright (c) 2016, 2017 Red Hat Inc and others.
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.oneofour.server.data.model;

import org.eclipse.oneofour.io.MirrorCommand;

public final class MockMirrorCommand implements MirrorCommand
{
    private int positive;

    private int negative;

    private int termination;

    @Override
    public synchronized void sendActivationTermination ()
    {
        this.termination++;
    }

    @Override
    public synchronized void sendActivationConfirm ( final boolean positive )
    {
        if ( positive )
        {
            this.positive++;
        }
        else
        {
            this.negative++;
        }
    }

    public synchronized int getPositive ()
    {
        return this.positive;
    }

    public synchronized int getNegative ()
    {
        return this.negative;
    }

    public synchronized int getTermination ()
    {
        return this.termination;
    }
}
