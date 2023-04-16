package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This interface maps {@link Certificate} and {@link CertificateDto} objects to each other.
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {CertificateService.class})
public interface CertificateMapper {

    CertificateDto toCertificateDTO(Certificate certificate);

    Certificate toCertificate(CertificateDto certificateDTO);

    default String dateToString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }

        return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
    }

    default LocalDateTime stringToDate(String localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return LocalDateTime.parse(localDateTime, DateTimeFormatter.ISO_DATE_TIME);
    }
}
