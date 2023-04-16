package com.epam.esm.repository.implementation;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.util.builder.CertificateBuilder;
import com.epam.esm.util.builder.TagBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.DatabaseQueries.*;

/**
 * This class provides an implementation of the CertificateRepository interface, using Spring JDBC templates to interact with the database.
 */
@Repository
@RequiredArgsConstructor
public class CertificateRepositoryImpl implements CertificateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagBuilder tagBuilder;
    private final CertificateBuilder certificateBuilder;

    /**
     * Returns a list of all certificates from the database.
     *
     * @return List of all certificates
     */
    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificateList = jdbcTemplate.query(GIFT_CERTIFICATE_FIND_ALL, certificateBuilder);
        for (Certificate certificate : certificateList) {
            setCertificateTags(certificate);
        }
        return certificateList;
    }

    /**
     * Returns the certificate with the specified ID from the database.
     *
     * @param id Certificate ID
     * @return Optional of the certificate with the specified ID
     */
    @Override
    public Optional<Certificate> findById(Long id) {
        Certificate certificate = jdbcTemplate.queryForObject(GIFT_CERTIFICATE_FIND_BY_ID, certificateBuilder, id);
        if (certificate == null)
            return Optional.empty();
        setCertificateTags(certificate);
        return Optional.of(certificate);
    }

    /**
     * Returns a list of certificates that have the specified tag from the database.
     *
     * @param tag Tag to search for
     * @return List of certificates that have the specified tag
     */
    @Override
    public List<Certificate> findByTag(Tag tag) {
        List<Certificate> certificateList = jdbcTemplate.query(GIFT_CERTIFICATE_FIND_BY_TAG_NAME, certificateBuilder, tag.getName());
        for (Certificate certificate : certificateList) {
            setCertificateTags(certificate);
        }
        return certificateList;
    }

    /**
     * Returns a list of certificates that match the specified search criteria from the database.
     *
     * @param searchFilter FilterSearch object containing search criteria
     * @return List of certificates that match the specified search criteria
     */
    @Override
    public List<Certificate> findBySearchFilter(FilterSearch searchFilter) {
        String searchPlace = searchFilter.getSearchPlace().getStringValue(searchFilter.getSearchValue());
        String searchType = searchFilter.getSearchType().getStringValue();
        List<Certificate> certificateList = jdbcTemplate.query(String.format(GIFT_CERTIFICATE_FIND_BY_TYPE, searchType, searchPlace), certificateBuilder);
        certificateList.forEach(this::setCertificateTags);
        return certificateList;
    }

    /**
     * Returns a list of certificates sorted by the specified criteria from the database.
     *
     * @param sortFilter FilterSort object containing sorting criteria
     * @return List of certificates sorted by the specified criteria
     */
    @Override
    public List<Certificate> findBySortFilter(FilterSort sortFilter) {
        String sortType = sortFilter.getSortType().getStringValue();
        String sortOrder = sortFilter.getSortOrder().getStringValue();
        List<Certificate> certificateList = jdbcTemplate.query(String.format(FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE, sortType, sortOrder), certificateBuilder);
        certificateList.forEach(this::setCertificateTags);
        return certificateList;
    }

    /**
     * Updates an existing certificate in the database
     *
     * @param certificate The certificate to be updated
     */
    @Override
    public void update(Certificate certificate) {

        jdbcTemplate.update(GIFT_CERTIFICATE_UPDATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(), certificate.getId());
        jdbcTemplate.update(
                CERTIFICATE_TAG_DELETE,
                certificate.getId());
    }

    /**
     * Inserts a new certificate in the database
     *
     * @param certificate The certificate to be inserted
     * @return The ID of the inserted certificate
     */
    @Override
    public Long insert(Certificate certificate) {
        jdbcTemplate.update(GIFT_CERTIFICATE_INSERT,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration());

        List<Certificate> all = findAll();
        return all.get(all.size() - 1).getId();
    }

    /**
     * Inserts tags for a certificate in the database
     *
     * @param certificate The certificate to insert tags for
     */
    @Override
    public void insertTags(Certificate certificate) {
        certificate.getTags().forEach(tag -> jdbcTemplate.update(CERTIFICATE_TAG_INSERT, certificate.getId(), tag.getId()));
        setCertificateTags(certificate);
    }

    /**
     * Deletes a certificate from the database by its ID
     *
     * @param id The ID of the certificate to be deleted
     */
    @Override
    public void delete(Long id) {

        jdbcTemplate.update(GIFT_CERTIFICATE_DELETE, id);
    }

    /**
     * Sets tags for a given certificate
     *
     * @param certificate The certificate to set tags for
     */
    private void setCertificateTags(Certificate certificate) {
        certificate.setTags(new HashSet<>(jdbcTemplate.query(CERTIFICATE_TAGS_FIND_ALL_BY_CERTIFICATE_ID, tagBuilder, certificate.getId())));
    }
}
