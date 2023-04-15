package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.service.CertificateService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
