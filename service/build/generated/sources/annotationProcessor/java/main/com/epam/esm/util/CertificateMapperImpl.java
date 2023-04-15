package com.epam.esm.util;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-14T17:37:17+0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class CertificateMapperImpl implements CertificateMapper {

    @Override
    public CertificateDto toCertificateDTO(Certificate certificate) {
        if ( certificate == null ) {
            return null;
        }

        CertificateDto.CertificateDtoBuilder certificateDto = CertificateDto.builder();

        certificateDto.id( certificate.getId() );
        certificateDto.name( certificate.getName() );
        certificateDto.description( certificate.getDescription() );
        if ( certificate.getPrice() != null ) {
            certificateDto.price( certificate.getPrice() );
        }
        if ( certificate.getDuration() != null ) {
            certificateDto.duration( certificate.getDuration() );
        }
        certificateDto.lastUpdatedDate( dateToString( certificate.getLastUpdatedDate() ) );
        certificateDto.tags( tagSetToTagDtoArray( certificate.getTags() ) );

        return certificateDto.build();
    }

    @Override
    public Certificate toCertificate(CertificateDto certificateDTO) {
        if ( certificateDTO == null ) {
            return null;
        }

        Certificate.CertificateBuilder certificate = Certificate.builder();

        certificate.id( certificateDTO.getId() );
        certificate.name( certificateDTO.getName() );
        certificate.description( certificateDTO.getDescription() );
        certificate.price( certificateDTO.getPrice() );
        certificate.duration( certificateDTO.getDuration() );
        certificate.lastUpdatedDate( stringToDate( certificateDTO.getLastUpdatedDate() ) );
        certificate.tags( tagDtoArrayToTagSet( certificateDTO.getTags() ) );

        return certificate.build();
    }

    protected TagDto tagToTagDto(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    protected TagDto[] tagSetToTagDtoArray(Set<Tag> set) {
        if ( set == null ) {
            return null;
        }

        TagDto[] tagDtoTmp = new TagDto[set.size()];
        int i = 0;
        for ( Tag tag : set ) {
            tagDtoTmp[i] = tagToTagDto( tag );
            i++;
        }

        return tagDtoTmp;
    }

    protected Tag tagDtoToTag(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        Tag.TagBuilder tag = Tag.builder();

        tag.id( tagDto.getId() );
        tag.name( tagDto.getName() );

        return tag.build();
    }

    protected Set<Tag> tagDtoArrayToTagSet(TagDto[] tagDtoArray) {
        if ( tagDtoArray == null ) {
            return null;
        }

        Set<Tag> set = new LinkedHashSet<Tag>( Math.max( (int) ( tagDtoArray.length / .75f ) + 1, 16 ) );
        for ( TagDto tagDto : tagDtoArray ) {
            set.add( tagDtoToTag( tagDto ) );
        }

        return set;
    }
}
