/**
 * Copyright (c) 2008-2011 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://www.sonatype.com/products/nexus/attributions.
 *
 * This program is free software: you can redistribute it and/or modify it only under the terms of the GNU Affero General
 * Public License Version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License Version 3
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License Version 3 along with this program.  If not, see
 * http://www.gnu.org/licenses.
 *
 * Sonatype Nexus (TM) Open Source Version is available from Sonatype, Inc. Sonatype and Sonatype Nexus are trademarks of
 * Sonatype, Inc. Apache Maven is a trademark of the Apache Foundation. M2Eclipse is a trademark of the Eclipse Foundation.
 * All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.plugins.bundlemaker.internal.capabilities;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.plugins.bundlemaker.BundleMaker;
import org.sonatype.nexus.plugins.capabilities.api.Capability;
import org.sonatype.nexus.plugins.capabilities.api.CapabilityFactory;
import org.sonatype.nexus.plugins.capabilities.api.CompositeCapability;
import org.sonatype.nexus.plugins.requestinterceptor.RequestInterceptors;

@Named( BundleMakerCapability.ID )
@Singleton
public class BundleMakerCapabilityFactory
    implements CapabilityFactory
{

    private final BundleMaker bundleMaker;

    private final RequestInterceptors requestInterceptors;

    @Inject
    BundleMakerCapabilityFactory( final BundleMaker bundleMaker, final RequestInterceptors requestInterceptors )
    {
        this.bundleMaker = bundleMaker;
        this.requestInterceptors = requestInterceptors;
    }

    @Override
    public Capability create( final String id )
    {
        final CompositeCapability capability = new CompositeCapability( id );
        capability.add( new BundleMakerCapability( id, bundleMaker ) );
        capability.add( new RecipeRequestInterceptorCapability( id, requestInterceptors ) );
        capability.add( new BundleRequestInterceptorCapability( id, requestInterceptors ) );
        return capability;
    }
}
