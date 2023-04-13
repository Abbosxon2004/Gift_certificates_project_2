package com.epam.esm.repository.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.BaseRepository;

import java.util.List;
import java.util.Optional;


public interface TagRepository extends BaseRepository<Tag> {
    /**
     * Get all tags
     *
     * @return List of Tags
     */
    @Override
    List<Tag> findAll();

    /**
     * Get Tag by id
     *
     * @param id
     * @return
     */
    @Override
    Optional<Tag> findById(Long id);

    /**
     * Update existing tag
     * It will not be used
     *
     * @param tag
     */
    @Override
    void update(Tag tag);

    /**
     * Insert new tag
     *
     * @param tag
     * @return
     */
    @Override
    Long insert(Tag tag);

    /**
     * Delete Tag by id
     *
     * @param id
     * @return
     */
    @Override
    void delete(Long id);
}
