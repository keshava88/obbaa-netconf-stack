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

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support;

public class ModelNodeDeleteException extends Exception {

	private static final long serialVersionUID = -4567452590514478472L;

	public ModelNodeDeleteException() {
	}

	public ModelNodeDeleteException(String message) {
		super(message);
	}

	public ModelNodeDeleteException(Throwable cause) {
		super(cause);
	}

	public ModelNodeDeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModelNodeDeleteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
