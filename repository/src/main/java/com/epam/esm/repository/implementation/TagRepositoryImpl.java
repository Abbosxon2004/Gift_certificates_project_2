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

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TagBuilder tagBuilder;

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(TAG_FIND_ALL, tagBuilder);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(TAG_FIND_BY_ID, tagBuilder, id));
    }

    @Override
    public void update(Tag tag) {
    }

    @Override
    public Long insert(Tag tag) {
        jdbcTemplate.update(TAG_INSERT, tag.getName());

        List<Tag> all = findAll();
        return all.get(all.size() - 1).getId();
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(TAG_DELETE, id);
    }
}
