/*
 * Copyright 2018 Broadband Forum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model;

import org.opendaylight.yangtools.yang.model.api.SchemaPath;

public class ThreadLocalSubsystemRegistry implements SubSystemRegistry {

    private static ThreadLocal<SubSystemRegistryImpl> c_registry = new ThreadLocal<>();

    private  CompositeSubSystem m_compositeSubSystem;

    public static void setSubsystemRegistry(SubSystemRegistryImpl registry) {
        c_registry.set(registry);
    }

    public static SubSystemRegistry getRegistry() {
        return c_registry.get();
    }

    @Override
    public void register(String componentId, SchemaPath schemaPath, SubSystem subSystem) {
        getRegistry().register(componentId, schemaPath, subSystem);
    }

    @Override
    public SubSystem lookupSubsystem(SchemaPath schemaPath) {
        return getRegistry().lookupSubsystem(schemaPath);
    }

    @Override
    public void undeploy(String componentId) {
        getRegistry().undeploy(componentId);
    }

    @Override
    public CompositeSubSystem getCompositeSubSystem() {
        return m_compositeSubSystem;
    }

    @Override public void setCompositeSubSystem(CompositeSubSystem compositeSubSystem) {
        m_compositeSubSystem = compositeSubSystem;
    }

    public static void clearRegistry() {
        c_registry.remove();
    }

    @Override
    public SubSystemRegistry unwrap() {
        return this;
    }
}