package com.epam.esm.util.builder;

import com.epam.esm.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.util.DatabaseQueries.*;

@Component
public class CertificateBuilder implements RowMapper<Certificate> {
    @Override
    public Certificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Certificate
                .builder()
                .id(rs.getLong(GIFT_CERTIFICATE_ID_COLUMN))
                .name(rs.getString(GIFT_CERTIFICATE_NAME_COLUMN))
                .description(rs.getString(GIFT_CERTIFICATE_DESCRIPTION_COLUMN))
                .price(rs.getDouble(GIFT_CERTIFICATE_PRICE_COLUMN))
                .duration(rs.getInt(GIFT_CERTIFICATE_DURATION_COLUMN))
                .createdDate(rs.getTimestamp(GIFT_CERTIFICATE_CREATED_DATE_COLUMN).toLocalDateTime())
                .lastUpdatedDate(rs.getTimestamp(GIFT_CERTIFICATE_LAST_UPDATED_DATE_COLUMN).toLocalDateTime())
                .build();
    }
}
