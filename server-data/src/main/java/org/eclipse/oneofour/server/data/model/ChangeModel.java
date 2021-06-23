/*******************************************************************************
 * Copyright (c) 2016 IBH SYSTEMS GmbH and others.
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
package org.eclipse.oneofour.server.data.model;

import org.eclipse.oneofour.asdu.types.ASDUAddress;
import org.eclipse.oneofour.asdu.types.CauseOfTransmission;
import org.eclipse.oneofour.asdu.types.InformationObjectAddress;
import org.eclipse.oneofour.asdu.types.Value;

public interface ChangeModel
{
    public void notifyChange ( CauseOfTransmission cause, ASDUAddress asduAddress, InformationObjectAddress informationObjectAddress, final Value<?> value );

    public Runnable dispose ();
}
