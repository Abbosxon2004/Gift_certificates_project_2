package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.TagService;

/**
 * This interface maps {@link Tag} and {@link TagDto} objects to each other.
 */
@org.mapstruct.Mapper(componentModel = "spring", uses = {CertificateService.class, TagService.class})
public interface TagMapper {
    TagDto toTagDTO(Tag tag);

    Tag toTag(TagDto tagDTO);
}
