/*******************************************************************************
 * Copyright (c) 2006, 2010 TH4 SYSTEMS GmbH and others.
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
 *******************************************************************************/
package org.eclipse.oneofour.utils.concurrent;

import java.util.concurrent.Future;

public interface NotifyFuture<T> extends Future<T>
{
    public void addListener ( FutureListener<T> listener );

    public void removeListener ( FutureListener<T> listener );

    public void addListener ( Runnable listener );

    public void removeListener ( Runnable listener );
}
