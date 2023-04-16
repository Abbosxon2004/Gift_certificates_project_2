package com.epam.esm.util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-16T12:45:19+0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto toTagDTO(Tag tag) {
        if ( tag == null ) {
            return null;
        }

        TagDto.TagDtoBuilder tagDto = TagDto.builder();

        tagDto.id( tag.getId() );
        tagDto.name( tag.getName() );

        return tagDto.build();
    }

    @Override
    public Tag toTag(TagDto tagDTO) {
        if ( tagDTO == null ) {
            return null;
        }

        Tag.TagBuilder tag = Tag.builder();

        tag.id( tagDTO.getId() );
        tag.name( tagDTO.getName() );

        return tag.build();
    }
}
