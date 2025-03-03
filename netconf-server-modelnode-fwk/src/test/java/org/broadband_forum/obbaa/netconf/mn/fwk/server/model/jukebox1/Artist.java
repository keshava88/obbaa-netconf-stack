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

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model.jukebox1;

import org.broadband_forum.obbaa.netconf.persistence.jpa.YangTypeWithParentId;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.ArtistPK;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangList;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangListKey;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangParentId;
import org.broadband_forum.obbaa.netconf.stack.api.annotations.YangSchemaPath;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity(name = "getwithstatefilter_artist")
@Table(name = "getwithstatefilter_artist")
@IdClass(ArtistPK.class)
@YangList(name="artist", namespace = JukeboxConstants.JB_NS, revision= JukeboxConstants.JB_REVISION)
public class Artist extends YangTypeWithParentId {
	public static final String NAME = "name";

	@Id
	@Column(name=NAME)
	@YangListKey(name=NAME, namespace = JukeboxConstants.JB_NS, revision= JukeboxConstants.JB_REVISION)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
