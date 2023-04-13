package com.epam.esm.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DatabaseQueries {

    /**
     * Tag table columns ✔
     * ↓
     */
    public static final String TAG_ID_COLUMN = "id";
    public static final String TAG_NAME_COLUMN = "name";


    /**
     * Gift_Certificate table columns ✔
     * ↓
     */

    public static final String GIFT_CERTIFICATE_ID_COLUMN = "id";
    public static final String GIFT_CERTIFICATE_NAME_COLUMN = "name";
    public static final String GIFT_CERTIFICATE_DESCRIPTION_COLUMN = "description";
    public static final String GIFT_CERTIFICATE_PRICE_COLUMN = "price";
    public static final String GIFT_CERTIFICATE_DURATION_COLUMN = "duration";
    public static final String GIFT_CERTIFICATE_CREATED_DATE_COLUMN = "created_date";
    public static final String GIFT_CERTIFICATE_LAST_UPDATED_DATE_COLUMN = "last_updated_date";

    /**
     * CRD queries for Tag table ✔
     * C - Create
     * R - READ
     * D - DELETE
     * ↓
     */
    public static final String TAG_FIND_BY_ID = "SELECT * FROM tag where id=?;";
    public static final String TAG_FIND_ALL = "SELECT * FROM tag;";
    public static final String TAG_INSERT = "INSERT INTO tag(name) values(?);";
    //on conflict do nothing returning id;
    // may be added to tag_insert
    public static final String TAG_DELETE = "DELETE FROM tag where id=?;";

    /**
     * CRUD queries for Gift_Certificate table
     * C - Create
     * U - Update
     * R - READ
     * D - DELETE
     * ↓
     */

    public static final String GIFT_CERTIFICATE_FIND_BY_TAG_NAME = "SELECT gift_certificate.*\n" +
            "FROM gift_certificate\n" +
            "         JOIN certificate_tag ON gift_certificate.id = certificate_tag.certificate_id\n" +
            "         JOIN Tag ON certificate_tag.tag_id = tag.id\n" +
            "WHERE tag.name = ?;";
    public static final String GIFT_CERTIFICATE_FIND_BY_TYPE = "select * from gift_certificate where %s like %s";
    public static final String GIFT_CERTIFICATE_FIND_BY_ID = "select * from gift_certificate where id=?";
    public static final String GIFT_CERTIFICATE_FIND_ALL = "select * from gift_certificate";
    public static final String FIND_ALL_CERTIFICATES_SORT_BY_TYPE_AND_VALUE = "select * from gift_certificate order by %s %s";
    public static final String GIFT_CERTIFICATE_INSERT = "insert into gift_certificate(name,description,price,duration,created_date,last_updated_date) values(?,?,?,?,now(),now())";
    public static final String GIFT_CERTIFICATE_UPDATE = "update gift_certificate\n" +
            "set name=coalesce(?, name),\n" +
            "    description=COALESCE(?, description),\n" +
            "    price=coalesce(?,price),\n" +
            "    duration=coalesce(?, duration),\n" +
            "    last_updated_date=now()\n" +
            "where id=?;";
    public static final String GIFT_CERTIFICATE_DELETE = "delete from gift_certificate where id=?;";

    /**
     * Queries for Certificate_Tag table ↓
     */
    public static final String CERTIFICATE_TAGS_FIND_ALL_BY_CERTIFICATE_ID = "select * from certificate_tag inner join tag t on t.id = certificate_tag.tag_id where certificate_id = ?";
    public static final String CERTIFICATE_TAGS_FIND_ALLL = "select * from certificate_tag";
    public static final String CERTIFICATE_TAG_INSERT = "insert into certificate_tag (certificate_id, tag_id) values(?, ?);";
    public static final String CERTIFICATE_TAG_DELETE = "delete from certificate_tag where certificate_id = ?;";


}
