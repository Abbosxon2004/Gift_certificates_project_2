package com.epam.esm.repository.implementation;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.util.builder.TagBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.util.DatabaseQueries.*;

/**
 * This class implements the TagRepository interface and provides methods to perform CRUD operations on tags in the database.
 */
@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagBuilder tagBuilder;

    /**
     * Finds all tags in the database.
     *
     * @return List of all tags in the database
     */
    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, tagBuilder);
    }

    /**
     * Finds a tag in the database by its id.
     *
     * @param id Id of the tag to be found
     * @return Optional containing the found tag object, or empty if no such tag exists
     */
    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(TAG_FIND_BY_ID, tagBuilder, id));
    }

    /**
     * Updates an existing tag in the database.
     * But now it is not available
     *
     * @param tag Tag object containing the new values to be updated
     */
    @Override
    public void update(Tag tag) {
    }

    /**
     * Inserts a new tag into the database.
     *
     * @param tag Tag object to be inserted
     * @return The id of the newly inserted tag
     */
    @Override
    public Long insert(Tag tag) {
        jdbcTemplate.update(TAG_INSERT, tag.getName());

        List<Tag> all = findAll();
        return all.get(all.size() - 1).getId();
    }

    /**
     * Deletes a tag from the database.
     *
     * @param id Id of the tag to be deleted
     */
    @Override
    public void delete(Long id) {
        jdbcTemplate.update(TAG_DELETE, id);
    }
}
