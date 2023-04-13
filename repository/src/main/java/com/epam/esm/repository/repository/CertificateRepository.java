package com.epam.esm.repository.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends BaseRepository<Certificate> {

    /**
     * Get all certificates(information)
     *
     * @return List of certificates
     */
    @Override
    List<Certificate> findAll();

    /**
     * Get certificate by id
     *
     * @param id certificate id
     * @return
     */
    @Override
    Optional<Certificate> findById(Long id);

    /**
     * Get certificates by tag
     *
     * @param tag Tag class(Object)
     * @return
     */
    List<Certificate> findByTag(Tag tag);

    /**
     * Get certificates by searchFilter
     *
     * @param searchFilter
     * @return
     */
    List<Certificate> findBySearchFilter(FilterSearch searchFilter);

    /**
     * Get certificates by sortFilter
     *
     * @param sortFilter
     * @return
     */
    List<Certificate> findBySortFilter(FilterSort sortFilter);

    /**
     * Update existing certificate
     *
     * @param certificate
     */
    @Override
    void update(Certificate certificate);

    /**
     * Insert new certificate
     *
     * @param certificate
     * @return
     */
    @Override
    Long insert(Certificate certificate);

    /**
     * Insert new tags by certificate
     *
     * @param certificate
     */
    void insertTags(Certificate certificate);

    /**
     * delete certificate
     *
     * @param id Long certificate id
     */
    @Override
    void delete(Long id);
}
