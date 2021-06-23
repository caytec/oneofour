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
package org.eclipse.oneofour.asdu.types;


/**
 * A value for the cause of transmission
 * <p>
 * <em>Note:</em> There are two implementations of {@link Cause}:
 * {@link StandardCause} and {@link CustomCause}. No custom subclasses are
 * allowed.
 * </p>
 * <p>
 * Either the enum {@link StandardCause} or the method
 * {@link Causes#valueOf(int)} can be used to get a cause instance. The value
 * for the cause must be between 0 and 63 (both inclusive).
 * </p>
 * <p>
 * Since the {@link Causes} class caches the object instances internally, causes
 * can be compared either by using {@link Object#equals} or their instance.
 * </p>
 */
public interface Cause
{
    public short getValue ();
}
