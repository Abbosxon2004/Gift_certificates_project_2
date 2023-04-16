package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.CertificateMapper;
import com.epam.esm.util.FilterMapper;
import com.epam.esm.util.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.epam.esm.util.ExceptionMessages.*;

/**
 * This class represents the service layer of the certificate entity in the application.
 * It contains methods for finding, creating, updating and deleting certificates.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;
    private final FilterMapper filterMapper;
    private final TagMapper tagMapper;

    /**
     * This method retrieves all certificates from the database.
     *
     * @return a list of CertificateDto objects representing all the certificates in the database.
     * @throws NotFoundException if no certificates are found in the database.
     */
    public List<CertificateDto> findAll()
            throws NotFoundException {
        try {
            return certificateRepository.findAll().stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    /**
     * This method retrieves a certificate from the database by its ID.
     *
     * @param id the ID of the certificate to be retrieved.
     * @return a CertificateDto object representing the certificate.
     * @throws NotFoundException if the certificate with the given ID is not found in the database.
     */
    public CertificateDto findById(Long id) throws NotFoundException {
        try {
            return certificateMapper.toCertificateDTO(certificateRepository.findById(id).get());
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        }
    }

    /**
     * This method retrieves all certificates from the database that have a given tag.
     *
     * @param tag a TagDto object representing the tag to be searched for.
     * @return a list of CertificateDto objects representing the certificates that have the tag.
     * @throws NotFoundException if no certificates with the tag are found in the database.
     */
    public List<CertificateDto> findByTag(TagDto tag) throws NotFoundException {
        try {
            return certificateRepository.findByTag(tagMapper.toTag(tag)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    /**
     * This method retrieves all certificates from the database that match a given search filter.
     *
     * @param searchFilter a SearchFilterDto object representing the search filter.
     * @return a list of CertificateDto objects representing the certificates that match the search filter.
     * @throws NotFoundException if no certificates that match the search filter are found in the database.
     */
    public List<CertificateDto> findBySearchFilter(SearchFilterDto searchFilter) throws NotFoundException {
        try {
            return certificateRepository.findBySearchFilter(filterMapper.toSearchFilter(searchFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    /**
     * This method retrieves all certificates from the database that match a given sort filter.
     *
     * @param sortFilter a SearchFilterDto object representing the sort filter.
     * @return a list of CertificateDto objects representing the certificates that match the search filter.
     * @throws NotFoundException if no certificates that match the search filter are found in the database.
     */
    public List<CertificateDto> findBySortFilter(SortFilterDto sortFilter) throws NotFoundException {
        try {
            return certificateRepository.findBySortFilter(filterMapper.toSortFilter(sortFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    /**
     * Inserts a new certificate into the database with the provided data.
     *
     * @param certificatedto The data for the new certificate as a CertificateDto object.
     * @throws ModificationException If the creation process fails due to a database error.
     */
    public void create(CertificateDto certificatedto) throws ModificationException {
        Certificate certificate = certificateMapper.toCertificate(certificatedto);
        try {
            certificate.setId(certificateRepository.insert(certificate));
            Arrays.stream(certificatedto.getTags()).forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
            certificateRepository.insertTags(certificate);
        } catch (DataAccessException exception) {
            throw new ModificationException(CERTIFICATE_CREATE_FAILED, exception);
        }
    }

    /**
     * Updates an existing certificate in the database with the provided data.
     *
     * @param certificatedto The data for the updated certificate as a CertificateDto object.
     * @throws NotFoundException     If the certificate to update does not exist in the database.
     * @throws ModificationException If the update process fails due to a database error.
     */
    public void update(CertificateDto certificatedto) throws NotFoundException, ModificationException {
        long id = certificatedto.getId();
        try {
            certificateRepository.findById(id);
            Certificate certificate = certificateMapper.toCertificate(certificatedto);
            certificateRepository.update(certificate);
            Arrays.stream(certificatedto.getTags()).forEach(tag -> tag.setId(tagRepository.insert(tagMapper.toTag(tag))));
            certificateRepository.insertTags(certificate);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException exception) {
            throw new ModificationException(CERTIFICATE_UPDATE_FAILED, exception);
        }
    }

    /**
     * Deletes a certificate with the specified ID from the database.
     *
     * @param id The ID of the certificate to delete.
     * @throws NotFoundException     If a certificate with the specified ID does not exist in the database.
     * @throws ModificationException If the deletion process fails due to a database error.
     */
    public void delete(Long id) throws NotFoundException, ModificationException {
        try {
            certificateRepository.findById(id);
            certificateRepository.delete(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        } catch (DataAccessException exception) {
            throw new ModificationException(CERTIFICATE_DELETE_FAILED, exception);
        }
    }
}
