package com.epam.esm.repository.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends BaseRepository<Certificate> {


    @Override
    List<Certificate> findAll();

    @Override
    Optional<Certificate> findById(Long id);

    List<Certificate> findByTag(Tag tag);

    List<Certificate> findBySearchFilter(FilterSearch searchFilter);

    List<Certificate> findBySortFilter(FilterSort sortFilter);

    @Override
    void update(Certificate certificate);

    @Override
    Long insert(Certificate certificate);

    void insertTags(Certificate certificate);

    @Override
    void delete(Long id);
}
