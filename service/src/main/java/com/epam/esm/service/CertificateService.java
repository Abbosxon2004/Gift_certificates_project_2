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

@Service
@Transactional
@RequiredArgsConstructor
public class CertificateService {
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final CertificateMapper certificateMapper;
    private final FilterMapper filterMapper;
    private final TagMapper tagMapper;

    public List<CertificateDto> findAll()
            throws NotFoundException {
        try {
            return certificateRepository.findAll().stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    public CertificateDto findById(Long id) throws NotFoundException {
        try {
            return certificateMapper.toCertificateDTO(certificateRepository.findById(id).get());
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundException(CERTIFICATE_ID_NOT_FOUND, id);
        }
    }

    public List<CertificateDto> findByTag(TagDto tag) throws NotFoundException {
        try {
            return certificateRepository.findByTag(tagMapper.toTag(tag)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    public List<CertificateDto> findBySearchFilter(SearchFilterDto searchFilter) throws NotFoundException {
        try {
            return certificateRepository.findBySearchFilter(filterMapper.toSearchFilter(searchFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

    public List<CertificateDto> findBySortFilter(SortFilterDto sortFilter) throws NotFoundException {
        try {
            return certificateRepository.findBySortFilter(filterMapper.toSortFilter(sortFilter)).stream()
                    .map(certificateMapper::toCertificateDTO).toList();
        } catch (DataAccessException exception) {
            throw new NotFoundException(CERTIFICATES_NOT_FOUND, exception);
        }
    }

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
