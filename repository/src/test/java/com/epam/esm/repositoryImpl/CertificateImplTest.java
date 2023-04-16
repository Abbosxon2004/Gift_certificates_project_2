package com.epam.esm.repositoryImpl;

import java.util.*;

import com.epam.esm.config.ConfigurationTest;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.SearchPlace;
import com.epam.esm.filter.search.SearchType;
import com.epam.esm.filter.sort.SortOrder;
import com.epam.esm.filter.sort.SortType;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.util.EntityHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.epam.esm.entity.Certificate;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.epam.esm.util.EntityHolder.certificate;
import static org.junit.jupiter.api.Assertions.*;


//@Slf4j
@Transactional
@Rollback(value = true)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConfigurationTest.class})
public class CertificateImplTest {
    private final CertificateRepository certificateRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateImplTest(CertificateRepository certificateRepository, JdbcTemplate jdbcTemplate) {
        this.certificateRepository = certificateRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testFindAll() {
        List<Certificate> certificateList = certificateRepository.findAll();
        assertEquals(6, certificateList.size());
        assertEquals(certificate, certificateList.get(0));
    }

    @Test
    public void testFindById() {
        Optional<Certificate> optionalCertificate = certificateRepository.findById(1L);
        assertNotNull(optionalCertificate);
        assertEquals(certificate, optionalCertificate.get());
    }

    @Test
    public void testFindByTag() {
        List<Certificate> certificateList = certificateRepository.findByTag(EntityHolder.tag);
        assertEquals(1, certificateList.size());
        assertEquals(certificate, certificateList.get(0));
    }

    @Test
    public void testFindBySearchFilter() {
        FilterSearch searchFilter = FilterSearch.builder()
                .searchValue("certificate1")
                .searchType(SearchType.NAME)
                .searchPlace(SearchPlace.CONTAINS)
                .build();
        List<Certificate> certificateList = certificateRepository.findBySearchFilter(searchFilter);
        assertEquals(1, certificateList.size());
        assertEquals(certificate, certificateList.get(0));
    }

    @Test
    public void testFindBySortFilter() {
        FilterSort sortFilter = FilterSort.builder()
                .sortType(SortType.NAME)
                .sortOrder(SortOrder.DESC)
                .build();
        List<Certificate> certificateList = certificateRepository.findBySortFilter(sortFilter);
        assertEquals(6, certificateList.size());
    }

    @Test
    public void testInsert() {
        Long id = certificateRepository.insert(certificate);
        int size = certificateRepository.findAll().size();
        Certificate insertedCertificate = certificateRepository.findAll().get(size - 1);
        assertEquals(insertedCertificate, certificateRepository.findById(id).get());
    }

    @Test
    public void testUpdate() {
        Certificate certificate = Certificate.builder()
                .name("Certificate 1")
                .description("This is certificate 1")
                .price(50.00)
                .duration(30).build();

        Long id = certificateRepository.insert(certificate);

        certificate = Certificate.builder()
                .id(id)
                .name("Certificate 1 Updated")
                .description("This is certificate 1 Updated")
                .price(75.00)
                .duration(60).build();

        certificateRepository.update(certificate);

        Certificate updatedCertificate = certificateRepository.findById(id).get();
        assertEquals("Certificate 1 Updated", updatedCertificate.getName());
        assertEquals("This is certificate 1 Updated", updatedCertificate.getDescription());
        assertEquals(75.00, updatedCertificate.getPrice(), 0.0);
        assertEquals(60, updatedCertificate.getDuration());
    }

    @Test
    void testDelete() {
        assertTrue(certificateRepository.findById(1L).isPresent());
        int size = certificateRepository.findAll().size();
        certificateRepository.delete(1L);
        assertEquals(size - 1, certificateRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> certificateRepository.findById(1L));
    }

    @Test
    void testInsertTagsHandleExceptionsIfNotExists() {
        Tag tag1 = Tag.builder()
                .id(10L)
                .name("Tag11").build();
        Tag tag2 = Tag.builder()
                .id(20L)
                .name("Tag22").build();
        Set<Tag> tags = Set.of(tag1, tag2);
        Certificate certificate_tags = Certificate.builder()
                .id(1L)
                .name("testingCertificate")
                .description("testingDescription")
                .price(0.0)
                .duration(0)
                .tags(tags).build();

        Exception dataAccessException = assertThrows(DataAccessException.class, () -> certificateRepository.insertTags(certificate_tags));
        String expectedDataAccessExceptionMessage = "ERROR: insert or update on table \"certificate_tag\" violates foreign key constraint \"certificate_tag_tag_id_fkey\"";

        //viewing exception message
//        System.out.println(dataAccessException.getMessage());

        String actualDataAccessExceptionMessage = dataAccessException.getMessage();
        assertTrue(actualDataAccessExceptionMessage.contains(expectedDataAccessExceptionMessage));
    }
}