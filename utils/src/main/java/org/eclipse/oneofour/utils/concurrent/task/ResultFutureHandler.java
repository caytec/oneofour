/*******************************************************************************
 * Copyright (c) 2006, 2013 TH4 SYSTEMS GmbH and others.
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     TH4 SYSTEMS GmbH - initial API and implementation
 *     Jens Reimann - further development
 *******************************************************************************/
package org.eclipse.oneofour.utils.concurrent.task;

import java.util.concurrent.Future;

import org.eclipse.oneofour.utils.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultFutureHandler<T> implements FutureListener<T>
{

    private final static Logger logger = LoggerFactory.getLogger ( ResultFutureHandler.class );

    private final ResultHandler<T> resultHandler;

    public ResultFutureHandler ( final ResultHandler<T> resultHandler )
    {
        this.resultHandler = resultHandler;
    }

    @Override
    public void complete ( final Future<T> future )
    {
        try
        {
            this.resultHandler.completed ( future.get () );
        }
        catch ( final Throwable e )
        {
            logger.debug ( "Failed to complete", e );
            this.resultHandler.failed ( e );
        }
    }
}