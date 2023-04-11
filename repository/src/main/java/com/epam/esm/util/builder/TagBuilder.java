package com.epam.esm.util.builder;

import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.DatabaseQueries.TAG_ID_COLUMN;
import static com.epam.esm.util.DatabaseQueries.TAG_NAME_COLUMN;

@Component
public class TagBuilder implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Tag
                .builder()
                .id(rs.getLong(TAG_ID_COLUMN))
                .name(rs.getString(TAG_NAME_COLUMN))
                .build();
    }
}
