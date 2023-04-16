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

/**
 * The TagService class provides methods to perform CRD operations on the Tag entity.
 * It uses TagRepository to access the underlying database.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    /**
     * Retrieves all the tags in the database.
     *
     * @return List of TagDto objects.
     * @throws NotFoundException if no tags found.
     */
    public List<TagDto> findAll() throws NotFoundException {
        try {
            return tagRepository.findAll().stream().map(tagMapper::toTagDTO).toList();
        } catch (DataAccessException ex) {
            throw new NotFoundException(TAGS_NOT_FOUND, ex);
        }
    }

    /**
     * Retrieves the tag with the specified ID.
     *
     * @param id the ID of the tag to retrieve.
     * @return the TagDto object.
     * @throws NotFoundException if the tag with the specified ID is not found.
     */
    public TagDto findById(Long id) throws NotFoundException {
        try {
            return tagMapper.toTagDTO(tagRepository.findById(id).get());
        } catch (EmptyResultDataAccessException ex) {
            throw new NotFoundException(TAG_ID_NOT_FOUND, id);
        }
    }

    /**
     * Creates a new tag in the database.
     *
     * @param tagdto the TagDto object to create.
     * @throws ModificationException if the creation fails.
     */
    public void create(TagDto tagdto) throws ModificationException {
        try {
            tagRepository.insert(tagMapper.toTag(tagdto));
        } catch (DataAccessException ex) {
            throw new ModificationException(TAG_CREATE_FAILED, ex);
        }
    }

    /**
     * Deletes the tag with the specified ID.
     *
     * @param id the ID of the tag to delete.
     * @throws NotFoundException     if the tag with the specified ID is not found.
     * @throws ModificationException if the deletion fails.
     */
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
