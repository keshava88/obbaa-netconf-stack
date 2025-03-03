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

package org.broadband_forum.obbaa.netconf.api.transport.security;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import java.security.Provider;

/**
 * A X509 KeyManagerFactory which lets the users supply an implementation of {@link KeyManager} via {@link X509KeyManagerFactorySpi}.
 * 
 * 
 * 
 */
public class X509KeyManagerFactory extends KeyManagerFactory {
    private static final Provider PROVIDER = new Provider("", 0.0, "") {
        private static final long serialVersionUID = 1L;
    };

    public X509KeyManagerFactory(X509KeyManagerFactorySpi spi) {
        super(spi, PROVIDER, KeyManagerFactory.getDefaultAlgorithm());
    }

}
