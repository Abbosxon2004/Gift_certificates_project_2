package com.epam.esm.repository.implementation;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.util.builder.CertificateBuilder;
import com.epam.esm.util.builder.TagBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.DatabaseQueries.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagBuilder tagBuilder;
    private final CertificateBuilder certificateBuilder;

    @Override
    public List<Certificate> findAll() {
        log.info("********** querying a rows from certificate table ***********");
        List<Certificate> certificateList = jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL, certificateBuilder);
        for (Certificate certificate : certificateList) {
            setCertificateTags(certificate);
        }
        return certificateList;
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        log.info("********** querying a row from certificate table by id***********");
        Certificate certificate = jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_ID, certificateBuilder, id);
        if (certificate == null)
            return Optional.empty();
        setCertificateTags(certificate);
        return Optional.of(certificate);
    }

    @Override
    public List<Certificate> findByTag(Tag tag) {
        log.info("********** querying a rows from certificate table by tag***********");
        List<Certificate> certificateList = jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_TAG_NAME, certificateBuilder, tag.getName());
        for (Certificate certificate : certificateList) {
            setCertificateTags(certificate);
        }
        return certificateList;
    }

    @Override
    public List<Certificate> findBySearchFilter(FilterSearch searchFilter) {
        log.info("********** querying a rows from certificate table by search filter ***********");
        String searchPlace = searchFilter.getSearchPlace().getStringValue(searchFilter.getSearchValue());
        String searchType = searchFilter.getSearchType().getStringValue();
        List<Certificate> certificateList = jdbcTemplate.query(String.format(GIFT_CERTIFICATE_FIND_BY_TYPE, searchType, searchPlace), certificateBuilder);
        certificateList.forEach(this::setCertificateTags);
        return certificateList;
    }

    @Override
    public List<Certificate> findBySortFilter(FilterSort sortFilter) {
        log.info("********** querying a rows from certificate table by sort filter***********");
        String sortType = sortFilter.getSortType().getStringValue();
        String sortOrder = sortFilter.getSortOrder().getStringValue();
        List<Certificate> certificateList = jdbcTemplate.query(String.format(FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE, sortType, sortOrder), certificateBuilder);
        certificateList.forEach(this::setCertificateTags);
        return certificateList;
    }

    @Override
    public void update(Certificate certificate) {
        log.info("********** updating a row certificate table ***********");

        jdbcTemplate.update(GIFT_CERTIFICATE_UPDATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(), certificate.getId());
        jdbcTemplate.update(
                CERTIFICATE_TAG_DELETE,
                certificate.getId());
    }

    @Override
    public Long insert(Certificate certificate) {
        log.info("********** adding a row into certificate table ***********");

        jdbcTemplate.update(GIFT_CERTIFICATE_INSERT,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration());

        List<Certificate> all = findAll();
        return all.get(all.size()-1).getId();
    }

    @Override
    public void insertTags(Certificate certificate) {
        log.info("********** adding tags to certificate table ***********");

        certificate.getTags().forEach(tag -> jdbcTemplate.update(CERTIFICATE_TAG_INSERT, certificate.getId(), tag.getId()));
        setCertificateTags(certificate);
    }

    @Override
    public void delete(Long id) {
        log.info("********** deleting a row from certificate table ***********");

        jdbcTemplate.update(GIFT_CERTIFICATE_DELETE, id);
    }

    private void setCertificateTags(Certificate certificate) {
        certificate.setTags(new HashSet<>(jdbcTemplate.query(CERTIFICATE_TAGS_FIND_ALL_BY_CERTIFICATE_ID, tagBuilder, certificate.getId())));
    }
}
