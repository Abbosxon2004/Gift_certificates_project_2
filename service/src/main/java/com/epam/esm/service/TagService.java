package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.TagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.util.ExceptionMessages.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDto> findAll() throws NotFoundException {
        try {
            return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException ex) {
            throw new NotFoundException(TAGS_NOT_FOUND, ex);
        }
    }

    public TagDto findById(Long id) throws NotFoundException {
        try {
            return tagMapper.toTagDTO(tagRepository.findById(id).get());
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        }
    }

    public void create(TagDto tagdto) throws ModificationException {
        try {
            tagRepository.insert(tagMapper.toTag(tagdto));
        } catch (DataAccessException ex) {
            throw new ModificationException(TAG_CREATE_FAILED, ex);
        }
    }

    public void delete(Long id)
            throws NotFoundException, ModificationException {
        try {
            tagRepository.findById(id);
            tagRepository.delete(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        } catch (DataAccessException ex) {
            throw new ModificationException(TAG_DELETE_FAILED, ex);
        }
    }
}
