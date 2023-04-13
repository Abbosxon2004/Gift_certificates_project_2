package com.epam.esm.repositoryImpl;

import java.util.*;

import com.epam.esm.config.ConfigurationTest;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.SearchPlace;
import com.epam.esm.filter.search.SearchType;
import com.epam.esm.filter.sort.SortOrder;
import com.epam.esm.filter.sort.SortType;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.EntityHolder;
import lombok.extern.slf4j.Slf4j;
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
import static com.epam.esm.util.EntityHolder.tag;
import static org.junit.jupiter.api.Assertions.*;


//@Slf4j
@Transactional
@Rollback(value = true)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConfigurationTest.class})
public class TagImplTest {
    private final TagRepository tagRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagImplTest(TagRepository tagRepository, JdbcTemplate jdbcTemplate) {
        this.tagRepository = tagRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testFindAll() {
        List<Tag> tagList = tagRepository.findAll();
        assertEquals(6, tagList.size());
        assertEquals(tag, tagList.get(0));
    }

    @Test
    public void testFindById() {
        Optional<Tag> optionalTag = tagRepository.findById(1L);
        assertNotNull(optionalTag);
        assertEquals(tag, optionalTag.get());
    }

    @Test
    public void testInsert() {
        Tag tag = Tag.builder()
                .id(100l)
                .name("testTag")
                .build();
        int previousSize = tagRepository.findAll().size();
        Long id = tagRepository.insert(tag);
        Tag insertedTag = tagRepository.findAll().get(previousSize);
        assertEquals(previousSize + 1, tagRepository.findAll().size());
        assertEquals(insertedTag, tagRepository.findById(id).get());
    }

    @Test
    void testDelete() {
        assertTrue(tagRepository.findById(1L).isPresent());
        int size = tagRepository.findAll().size();
        tagRepository.delete(1L);
        assertEquals(size - 1, tagRepository.findAll().size());
        assertThrows(EmptyResultDataAccessException.class,
                () -> tagRepository.findById(1L));
    }

    @Test
    void testInsertSameTagsHandleExceptions() {
        Tag tag1 = Tag.builder()
                .id(10L)
                .name("Tag11").build();

        tagRepository.insert(tag1);
        Exception dataAccessException = assertThrows(DataAccessException.class, () -> tagRepository.insert(tag1));
        String expectedDataAccessExceptionMessage = "ERROR: duplicate key value violates unique constraint \"tag_name_key\"";

        //viewing exception message
//        System.out.println(dataAccessException.getMessage());

        String actualDataAccessExceptionMessage = dataAccessException.getMessage();
        assertTrue(actualDataAccessExceptionMessage.contains(expectedDataAccessExceptionMessage));
    }
}