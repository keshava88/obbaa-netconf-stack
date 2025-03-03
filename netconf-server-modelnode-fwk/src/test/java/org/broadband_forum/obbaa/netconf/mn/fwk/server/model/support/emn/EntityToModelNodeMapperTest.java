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

package org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.emn;

import static org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeRdn.CONTAINER;
import static org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.TestConstants.EMPTY_NODE_ID;
import static org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.TestConstants.PMA_CERT_NODE_ID;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.ALBUM_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.ALBUM_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.ARTIST_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.ARTIST_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.CA_CERT_CONTAINER_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.CA_CERT_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.CERTIFICATE_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.CERT_MGMT_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.JB_NS;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.JB_REVISION;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.JUKEBOX_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.JUKEBOX_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.JUKEBOX_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.LIBRARY_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.LIBRARY_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.NAME_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.PMA_CERTS_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.PMA_CERT_CONTAINER_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.PMA_CERT_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.SINGER_LOCAL_NAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.SINGER_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.SONG_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.SONG_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_CERT_BINARY_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_CERT_ID_QNAME;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_CERT_NS;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_CERT_REVISION;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_CERT_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_PMA_CERT_CONTAINER_SCHEMA_PATH;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.V3_PMA_CERT_NS;
import static org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.JukeboxConstants.YEAR_QNAME;
import static org.broadband_forum.obbaa.netconf.server.util.TestUtil.setUpUnwrap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.broadband_forum.obbaa.netconf.api.util.CryptUtil2;
import org.broadband_forum.obbaa.netconf.api.util.SchemaPathUtil;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaBuildException;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.schema.SchemaRegistryImpl;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.GetAttributeException;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNode;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeId;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.ModelNodeRdn;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDSMRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.ModelNodeDSMRegistryImpl;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.utils.AnnotationAnalysisException;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.datastore.utils.EntityRegistryBuilder;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ConfigAttributeHelper;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ConfigLeafAttribute;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.GenericConfigAttribute;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ModelNodeHelperRegistry;
import org.broadband_forum.obbaa.netconf.mn.fwk.server.model.support.ModelNodeWithAttributes;
import org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.certificates.v2.Certificate;
import org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.certificates.v2.PmaCerts;
import org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.certificates.v2.TrustedCaCerts;
import org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.certificates.v3.V3Certificate;
import org.broadband_forum.obbaa.netconf.mn.fwk.tests.persistence.entities.certificates.v3.V3PmaCerts;
import org.broadband_forum.obbaa.netconf.mn.fwk.util.NoLockService;
import org.broadband_forum.obbaa.netconf.persistence.EntityDataStoreManager;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Album;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Artist;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Jukebox;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Library;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Singer;
import org.broadband_forum.obbaa.netconf.persistence.test.entities.jukebox3.Song;
import org.broadband_forum.obbaa.netconf.server.RequestScopeJunitRunner;
import org.broadband_forum.obbaa.netconf.server.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.model.repo.api.YangTextSchemaSource;

/**
 * Created by keshava on 5/12/15.
 */
@RunWith(RequestScopeJunitRunner.class)
public class EntityToModelNodeMapperTest {

    public static final String TEST_CERTIFICATES = "test:certificates";
    private EntityRegistry m_entityRegistry = new EntityRegistryImpl();
    private ModelNodeId m_jukeboxNodeId = new ModelNodeId().addRdn(new ModelNodeRdn(CONTAINER, JB_NS, JUKEBOX_LOCAL_NAME));
    private ModelNodeId m_libraryNodeId = new ModelNodeId(m_jukeboxNodeId).addRdn(new ModelNodeRdn(CONTAINER, JB_NS, LIBRARY_LOCAL_NAME));
    private final ModelNodeId m_artistId = new ModelNodeId(m_libraryNodeId).addRdn(new ModelNodeRdn(CONTAINER, JB_NS, "artist"))
            .addRdn(new ModelNodeRdn("name", JB_NS, "keshava"));
    private final ModelNodeId m_albumId = new ModelNodeId(m_artistId).addRdn(new ModelNodeRdn(CONTAINER, JB_NS, "album"))
            .addRdn(new ModelNodeRdn("name", JB_NS, "Refactor Times"));
    private final ModelNodeId m_songId = new ModelNodeId(m_albumId).addRdn(new ModelNodeRdn(CONTAINER, JB_NS, "song"))
            .addRdn(new ModelNodeRdn("singer", JB_NS, "Entity Refactor"));

    private EntityToModelNodeMapper m_entityToModelNodeMapper;
    private ModelNodeId m_v3PmaCertsId;
    private ModelNodeId m_cert1NodeId;
    private ModelNodeId m_cert2NodeId;
    private ModelNodeHelperRegistry m_modelNodeHelperRegistry;
    private SchemaRegistry m_schemaRegistry;
    private ConfigAttributeHelper m_mockIdHelper;
    private ModelNodeDSMRegistry m_modelNodeDSMRegistry;

    @Before
    public void setUp() throws AnnotationAnalysisException, SchemaBuildException {
        List<String> sourcesFiles = Arrays.asList
                ("/yangs/v3-certificates@2015-12-08.yang", "/yangs/v3-pma-certificates@2015-12-08.yang", "/yangs/certificates@2015-12-08.yang");

        List<YangTextSchemaSource> byteSources = TestUtil.getByteSources(sourcesFiles);
        byteSources.addAll(TestUtil.getJukeBoxYangs());
        m_schemaRegistry = new SchemaRegistryImpl(byteSources, Collections.emptySet(), Collections.emptyMap(), new NoLockService());
        m_modelNodeDSMRegistry = new ModelNodeDSMRegistryImpl();
        List<Class> entityClasses = new ArrayList<>();
        entityClasses.add(PmaCerts.class);
        entityClasses.add(TrustedCaCerts.class);
        entityClasses.add(Certificate.class);
        entityClasses.add(Jukebox.class);
        entityClasses.add(Library.class);
        entityClasses.add(Artist.class);
        entityClasses.add(Album.class);
        entityClasses.add(Song.class);
        entityClasses.add(V3PmaCerts.class);
        entityClasses.add(V3Certificate.class);
        EntityRegistryBuilder.updateEntityRegistry("EntityToModelNodeMapperTest", entityClasses, m_entityRegistry, m_schemaRegistry, mock(EntityDataStoreManager.class), m_modelNodeDSMRegistry);
        m_modelNodeHelperRegistry = mock(ModelNodeHelperRegistry.class);
        Map<QName, ConfigAttributeHelper> helpers = new HashMap<>();
        m_mockIdHelper = mock(ConfigAttributeHelper.class);
        helpers.put(V3_CERT_ID_QNAME, m_mockIdHelper);
        when(m_modelNodeHelperRegistry.getNaturalKeyHelpers(V3_CERT_SCHEMA_PATH)).thenReturn(helpers);
        m_entityToModelNodeMapper = new EntityToModelNodeMapperImpl(m_entityRegistry, m_modelNodeHelperRegistry, null, m_schemaRegistry);
        prepareNodeIds();
        setUpUnwrap(m_modelNodeHelperRegistry);

        String keyFilePath = getClass().getResource("/domvisitortest/keyfile.plain").getPath();
        CryptUtil2 cryptUtil2 = new CryptUtil2();
        cryptUtil2.setKeyFilePathForTest(keyFilePath);
        cryptUtil2.initFile();

    }

    private void prepareNodeIds() {
        m_v3PmaCertsId = new ModelNodeId();
        m_v3PmaCertsId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_CERT_NS, CERT_MGMT_LOCAL_NAME));
        m_v3PmaCertsId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_PMA_CERT_NS, PMA_CERTS_LOCAL_NAME));

        m_cert1NodeId = new ModelNodeId();
        m_cert1NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_CERT_NS, CERT_MGMT_LOCAL_NAME));
        m_cert1NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_PMA_CERT_NS, PMA_CERTS_LOCAL_NAME));
        m_cert1NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_PMA_CERT_NS, CERTIFICATE_LOCAL_NAME));
        m_cert1NodeId.addRdn(new ModelNodeRdn(V3_CERT_ID_QNAME.getLocalName(), V3_PMA_CERT_NS, "pma-cert1"));

        m_cert2NodeId = new ModelNodeId();
        m_cert2NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_CERT_NS, CERT_MGMT_LOCAL_NAME));
        m_cert2NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_PMA_CERT_NS, PMA_CERTS_LOCAL_NAME));
        m_cert2NodeId.addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_PMA_CERT_NS, CERTIFICATE_LOCAL_NAME));
        m_cert2NodeId.addRdn(new ModelNodeRdn(V3_CERT_ID_QNAME.getLocalName(), V3_PMA_CERT_NS, "pma-cert2"));
    }

    @Test
    public void testEntityToModelNodeCertificatesV2() throws ModelNodeMapperException {
        PmaCerts pmaCerts = new PmaCerts();
        pmaCerts.setSchemaPath(SchemaPathUtil.toStringNoRev(PMA_CERT_CONTAINER_SCHEMA_PATH));
        Certificate cert1 = new Certificate();
        cert1.setSchemaPath(SchemaPathUtil.toStringNoRev(PMA_CERT_SCHEMA_PATH));
        cert1.setId("pma-cert1");
        cert1.setCerBinary("cert1-binary");
        pmaCerts.addCertificates(cert1);

        Certificate cert2 = new Certificate();
        cert1.setId("pma-cert2");
        cert1.setCerBinary("cert2-binary");
        pmaCerts.addCertificates(cert2);

        ModelNode modelNode = m_entityToModelNodeMapper.getModelNode(pmaCerts, null);
        QName pmaCertsQname = QName.create("test:certificates", "2015-12-08", "pma-certs");
        assertEquals(modelNode.getQName(), pmaCertsQname);

        TrustedCaCerts trustedCaCerts = new TrustedCaCerts();
        trustedCaCerts.setSchemaPath(SchemaPathUtil.toStringNoRev(CA_CERT_CONTAINER_SCHEMA_PATH));
        trustedCaCerts.setVisibility(true);

        cert1 = new Certificate();
        cert1.setSchemaPath(SchemaPathUtil.toStringNoRev(CA_CERT_SCHEMA_PATH));
        cert1.setId("ca-cert1");
        cert1.setCerBinary("cert1-binary");
        trustedCaCerts.addCertificates(cert1);

        cert2 = new Certificate();
        cert2.setSchemaPath(SchemaPathUtil.toStringNoRev(CA_CERT_SCHEMA_PATH));
        cert2.setId("ca-cert2");
        cert2.setCerBinary("cert2-binary");
        trustedCaCerts.addCertificates(cert2);

        modelNode = m_entityToModelNodeMapper.getModelNode(trustedCaCerts, null);
        QName caCertsQname = QName.create("test:certificates", "2015-12-08", "trusted-ca-certs");
        assertEquals(caCertsQname, modelNode.getQName());
        assertEquals(true, modelNode.isVisible());

        trustedCaCerts = new TrustedCaCerts();
        trustedCaCerts.setSchemaPath(SchemaPathUtil.toStringNoRev(CA_CERT_CONTAINER_SCHEMA_PATH));
        trustedCaCerts.setVisibility(false);

        modelNode = m_entityToModelNodeMapper.getModelNode(trustedCaCerts, null);
        assertEquals(false, modelNode.isVisible());
    }

    @Test
    public void testParentAndChildFromDiffNamespaces() throws GetAttributeException, ModelNodeMapperException {

        V3PmaCerts pmaCerts = new V3PmaCerts();
        ModelNodeId certMgmtNodeId = new ModelNodeId().addRdn(new ModelNodeRdn(ModelNodeRdn.CONTAINER, V3_CERT_NS, CERT_MGMT_LOCAL_NAME));
        pmaCerts.setParentId(certMgmtNodeId.getModelNodeIdAsString());
        pmaCerts.setSchemaPath(SchemaPathUtil.toStringNoRev(V3_PMA_CERT_CONTAINER_SCHEMA_PATH));
        V3Certificate cert1 = new V3Certificate();
        cert1.setSchemaPath(SchemaPathUtil.toStringNoRev(V3_CERT_SCHEMA_PATH));
        cert1.setId("pma-cert1");
        cert1.setCerBinary("cert1-binary");
        cert1.setParentId(m_v3PmaCertsId.getModelNodeIdAsString());

        pmaCerts.addCertificates(cert1);

        V3Certificate  cert2 = new V3Certificate();
        cert2.setSchemaPath(SchemaPathUtil.toStringNoRev(V3_CERT_SCHEMA_PATH));
        cert2.setParentId(m_v3PmaCertsId.getModelNodeIdAsString());
        cert2.setId("pma-cert2");
        cert2.setCerBinary("cert2-binary");
        pmaCerts.addCertificates(cert2);

        ModelNodeWithAttributes modelNode = m_entityToModelNodeMapper.getModelNode(pmaCerts, null);
        QName pmaCertsQname = QName.create(V3_PMA_CERT_NS, V3_CERT_REVISION, PMA_CERTS_LOCAL_NAME);
        assertEquals(modelNode.getQName(), pmaCertsQname);
        assertEquals(m_v3PmaCertsId, modelNode.getModelNodeId());
        assertEquals(V3_CERT_NS, modelNode.getModelNodeId().getRdns().get(0).getNamespace());
        assertEquals(V3_PMA_CERT_NS, modelNode.getModelNodeId().getRdns().get(1).getNamespace());

        modelNode = m_entityToModelNodeMapper.getModelNode(cert1, null);
        QName certsQname = QName.create(V3_PMA_CERT_NS, V3_CERT_REVISION, CERTIFICATE_LOCAL_NAME);
        assertEquals(certsQname, modelNode.getQName());
        assertEquals("cert1-binary", modelNode.getAttribute(V3_CERT_BINARY_QNAME).getStringValue());
        assertEquals("pma-cert1", modelNode.getAttribute(V3_CERT_ID_QNAME).getStringValue());
        when(m_mockIdHelper.getValue((ModelNode) anyObject())).thenReturn(new GenericConfigAttribute(V3_CERT_ID_QNAME.getLocalName(),
                V3_CERT_ID_QNAME.getNamespace().toString(), "pma-cert1"));
        ModelNodeId actualNodeId = modelNode.getModelNodeId();
        assertEquals(m_cert1NodeId, actualNodeId);
        //verify parent namespace
        assertEquals(V3_CERT_NS, actualNodeId.getRdns().get(0).getNamespace());
        //verify child namespace
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(1).getNamespace());
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(2).getNamespace());
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(3).getNamespace());

        modelNode = m_entityToModelNodeMapper.getModelNode(cert2, null);
        assertEquals(certsQname, modelNode.getQName());
        assertEquals("cert2-binary", modelNode.getAttribute(V3_CERT_BINARY_QNAME).getStringValue());
        assertEquals("pma-cert2", modelNode.getAttribute(V3_CERT_ID_QNAME).getStringValue());
        when(m_mockIdHelper.getValue((ModelNode) anyObject())).thenReturn(new GenericConfigAttribute(V3_CERT_ID_QNAME.getLocalName(),
                V3_CERT_ID_QNAME.getNamespace().toString(), "pma-cert2"));
        assertEquals(m_cert2NodeId, modelNode.getModelNodeId());
        //verify parent namespace
        assertEquals(V3_CERT_NS, actualNodeId.getRdns().get(0).getNamespace());
        //verify child namespace
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(1).getNamespace());
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(2).getNamespace());
        assertEquals(V3_PMA_CERT_NS, actualNodeId.getRdns().get(3).getNamespace());
    }


    @Test
    public void testModelNodeToEntityCertificatesV2(){

        ModelNodeWithAttributes certModelNode = new ModelNodeWithAttributes(PMA_CERT_CONTAINER_SCHEMA_PATH, null, null, null, m_schemaRegistry, null);
        Map<QName, ConfigLeafAttribute> configAttributes = new HashMap<>();
        configAttributes.put(QName.create(TEST_CERTIFICATES, "2015-12-08", "id"), new GenericConfigAttribute("id", TEST_CERTIFICATES, "pma-cert3"));
        configAttributes.put(QName.create(TEST_CERTIFICATES, "2015-12-08", "cert-binary"),new GenericConfigAttribute("cert-binary", TEST_CERTIFICATES, "pma-cert3-binary"));
        certModelNode.setAttributes(configAttributes);
        certModelNode.setVisibility(false);

        Object entity = m_entityToModelNodeMapper.getEntity(PMA_CERT_SCHEMA_PATH, certModelNode, Certificate.class, PMA_CERT_NODE_ID, -1);
        assertTrue(entity instanceof Certificate);
        Certificate certificate = (Certificate) entity;
        assertEquals("pma-cert3", certificate.getId());
        assertEquals("pma-cert3-binary", certificate.getCerBinary());
        assertEquals(false, certificate.getVisibility());
    }

    @Test
    public void testEntityToModelNodeJukebox3() throws ModelNodeMapperException {
        Jukebox jukebox = new Jukebox();
        jukebox.setSchemaPath(SchemaPathUtil.toStringNoRev(JUKEBOX_SCHEMA_PATH));
        jukebox.setParentId(EMPTY_NODE_ID.getModelNodeIdAsString());

        Library library = new Library();
        library.setSchemaPath(SchemaPathUtil.toStringNoRev(LIBRARY_SCHEMA_PATH));
        library.setParentId(m_jukeboxNodeId.getModelNodeIdAsString());
        jukebox.setLibrary(library);

        Artist artist = new Artist();
        artist.setSchemaPath(SchemaPathUtil.toStringNoRev(ARTIST_SCHEMA_PATH));
        artist.setName("keshava");
        artist.setParentId(m_libraryNodeId.getModelNodeIdAsString());
        library.addArtists(artist);

        Album refactorHits = new Album();
        refactorHits.setSchemaPath(SchemaPathUtil.toStringNoRev(ALBUM_SCHEMA_PATH));
        refactorHits.setName("Refactor Times");
        refactorHits.setParentId(m_artistId.getModelNodeIdAsString());
        refactorHits.setYear("2015");
        refactorHits.setGenre("Programming");
        artist.addAlbums(refactorHits);

        Song entitySong = new Song();
        entitySong.setSchemaPath(SchemaPathUtil.toStringNoRev(SONG_SCHEMA_PATH));
        entitySong.setParentId(m_albumId.getModelNodeIdAsString());
        entitySong.setName("Entity Refactor");
        entitySong.setFormat("mp3");
        entitySong.setLocation("My music");
        LinkedHashSet<Singer> singers = new LinkedHashSet<>();
        Singer singer1 = new Singer();
        singer1.setSinger("Singer1");
        singer1.setSchemaPath(SchemaPathUtil.toStringNoRev(SINGER_SCHEMA_PATH));
        singer1.setParentId(m_songId.getModelNodeIdAsString());
        
        Singer singer2 = new Singer();
        singer2.setSinger("Singer2");
        singer2.setSchemaPath(SchemaPathUtil.toStringNoRev(SINGER_SCHEMA_PATH));
        singer2.setParentId(m_songId.getModelNodeIdAsString());
        
        Singer singer3 = new Singer();
        singer3.setSinger("Singer3");
        singer3.setSchemaPath(SchemaPathUtil.toStringNoRev(SINGER_SCHEMA_PATH));
        singer3.setParentId(m_songId.getModelNodeIdAsString());
        
        singers.add(singer1);
        singers.add(singer2);
        singers.add(singer3);
        entitySong.setSingers(singers);
        refactorHits.addSongs(entitySong);

        ModelNode jukeboxNode = m_entityToModelNodeMapper.getModelNode(jukebox, null);
        assertEquals(JUKEBOX_QNAME, jukeboxNode.getQName());

        ModelNodeWithAttributes artistNode = m_entityToModelNodeMapper.getModelNode(artist, null);
        assertEquals(ARTIST_QNAME, artistNode.getQName());
        assertEquals(1, artistNode.getAttributes().size());
        assertEquals("keshava", artistNode.getAttributes().get(NAME_QNAME).getStringValue());

        ModelNodeWithAttributes albumNode = m_entityToModelNodeMapper.getModelNode(refactorHits, null);
        assertEquals(ALBUM_QNAME, albumNode.getQName());
        assertEquals(3, albumNode.getAttributes().size());
        assertEquals("Refactor Times", albumNode.getAttributes().get(NAME_QNAME).getStringValue());
        assertEquals("2015", albumNode.getAttributes().get(YEAR_QNAME).getStringValue());
        assertEquals("Programming", albumNode.getAttributes().get(QName.create("http://example.com/ns/example-jukebox", "2014-07-03",
                "genre")).getStringValue());

        ModelNodeWithAttributes songNode = m_entityToModelNodeMapper.getModelNode(entitySong, null);
        assertEquals(SONG_QNAME, songNode.getQName());
        assertEquals(3, songNode.getLeafList(QName.create(JB_NS,JB_REVISION, "singer")).size());
        assertEquals(3, songNode.getAttributes().size());
    }

    @Test
    public void testModelNodeToEntityJukebox3(){
        GenericConfigAttribute nameConfigAttr = new GenericConfigAttribute(NAME, JB_NS, "Entity Refactor");
        GenericConfigAttribute singer1 = new GenericConfigAttribute(SINGER_LOCAL_NAME, JB_NS, "singer1");
        GenericConfigAttribute singer2 = new GenericConfigAttribute(SINGER_LOCAL_NAME, JB_NS, "singer2");

        ModelNodeWithAttributes songModelNode = getSongModelNode(nameConfigAttr, singer1, singer2);

        Object songEntity = m_entityToModelNodeMapper.getEntity(SONG_SCHEMA_PATH, songModelNode, Song.class, m_artistId, -1);
        assertEquals(2, ((Song)songEntity).getSingers().size());
    }

    @Test
    public void testUpdateEntityJukebox3(){
        ModelNodeWithAttributes songModelNode = new ModelNodeWithAttributes(SONG_SCHEMA_PATH, m_artistId, null, null, m_schemaRegistry, null);
        Map<QName, ConfigLeafAttribute> configAttributes = new HashMap<>();
        configAttributes.put(NAME_QNAME, new GenericConfigAttribute(NAME, JB_NS, "Entity Refactor"));
        songModelNode.setAttributes(configAttributes);

        Set<Singer> singers = new HashSet<>();
        Singer singer1 = new Singer();
        singer1.setSinger("Singer1");
        Singer singer2 = new Singer();
        singer2.setSinger("Singer2");
        singers.add(singer1);
        singers.add(singer2);

        
        Object songEntity = new Song();
        ((Song)songEntity).setName("Init name");
        ((Song)songEntity).setSingers(singers);

        m_entityToModelNodeMapper.updateEntity(songEntity, SONG_SCHEMA_PATH, songModelNode, Song.class, m_artistId, -1);
        assertEquals(2, ((Song)songEntity).getSingers().size());
        assertTrue(((Song)songEntity).getSingers().contains(singer1));
        assertTrue(((Song)songEntity).getSingers().contains(singer2));
        assertEquals("Entity Refactor", ((Song)songEntity).getName());
    }

    @Test
    public void testGetOrUpdateEntityEncryptsPasswords(){
        GenericConfigAttribute nameConfigAttr = new GenericConfigAttribute(NAME, JB_NS, "dummysong");
        GenericConfigAttribute singer1 = new GenericConfigAttribute(SINGER_LOCAL_NAME, JB_NS, "$-0$xXIiL+xQgxTebSsvRs8RZspPklT/5f7eBsvWWssFV9M=");
        GenericConfigAttribute singer2 = new GenericConfigAttribute(SINGER_LOCAL_NAME, JB_NS, "singer2");
        ModelNodeWithAttributes songModelNode = getSongModelNode(nameConfigAttr, singer1, singer2);

        nameConfigAttr.setIsPassword(true);
        singer1.setIsPassword(true);
        singer2.setIsPassword(true);

        Song songEntity = (Song) m_entityToModelNodeMapper.getEntity(SONG_SCHEMA_PATH, songModelNode, Song.class, m_artistId, -1);
        assertEquals(2, songEntity.getSingers().size());
        assertEquals("dummysong", CryptUtil2.decrypt(songEntity.getName()));
        Iterator<Singer> iterator = songEntity.getSingers().iterator();
        assertEquals("$-0$xXIiL+xQgxTebSsvRs8RZspPklT/5f7eBsvWWssFV9M=", iterator.next().getSinger());
        assertEquals("singer2", CryptUtil2.decrypt(iterator.next().getSinger()));
    }

    private ModelNodeWithAttributes getSongModelNode(GenericConfigAttribute nameConfigAttr, GenericConfigAttribute singer1, GenericConfigAttribute singer2) {
        ModelNodeWithAttributes songModelNode = new ModelNodeWithAttributes(SONG_SCHEMA_PATH, m_artistId, null, null, m_schemaRegistry, null);
        Map<QName, ConfigLeafAttribute> configAttributes = new HashMap<>();
        configAttributes.put(NAME_QNAME, nameConfigAttr);
        songModelNode.setAttributes(configAttributes);
        songModelNode.setModelNodeId(m_songId);

        Map<QName, LinkedHashSet<ConfigLeafAttribute>> leafLists = new HashMap<>();
        LinkedHashSet<ConfigLeafAttribute> singers = new LinkedHashSet<>();
        singers.add(singer1);
        singers.add(singer2);
        leafLists.put(QName.create(JB_NS, JB_REVISION, SINGER_LOCAL_NAME), singers);

        songModelNode.setLeafLists(leafLists);
        return songModelNode;
    }
}
