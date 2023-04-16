import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.util.TagMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static util.EntityHolder.tag;
import static util.EntityHolder.tagDTO;

public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() throws NotFoundException {
        List<TagDto> tagDtos = Arrays.asList(tagDTO, tagDTO);
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag, tag));
        when(tagMapper.toTagDTO(any(Tag.class))).thenReturn(tagDTO);
        List<TagDto> result = tagService.findAll();
        assertEquals(tagDtos.size(), result.size());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllThrowsNotFoundException() {
        when(tagRepository.findAll()).thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(NotFoundException.class, () -> tagService.findAll());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() throws NotFoundException {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        when(tagMapper.toTagDTO(any(Tag.class))).thenReturn(tagDTO);
        TagDto result = tagService.findById(1L);
        assertEquals(tagDTO, result);
        verify(tagRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindByIdThrowsNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(tagRepository).findById(anyLong());
        assertThrows(NotFoundException.class, () -> tagService.findById(1L));
        verify(tagRepository, times(1)).findById(1L);
    }


    @Test
    public void testCreateSuccess() throws ModificationException {
        when(tagMapper.toTag(any(TagDto.class))).thenReturn(tag);
        assertDoesNotThrow(() -> tagService.create(tagDTO));
        verify(tagRepository, times(1)).insert(any(Tag.class));
    }

    @Test
    public void testCreateThrowsModificationException() {
        when(tagMapper.toTag(any(TagDto.class))).thenReturn(tag);
        when(tagRepository.insert(any(Tag.class))).thenThrow(new DataAccessException("failed") {
        });
        Assertions.assertThrows(ModificationException.class, () -> tagService.create(tagDTO));
        verify(tagRepository, times(1)).insert(any(Tag.class));
    }

    @Test
    public void testDeleteSuccess() throws NotFoundException, ModificationException {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));
        tagService.delete(1L);
        verify(tagRepository, times(1)).delete(1L);
    }

    @Test
    public void testDeleteThrowsNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(tagRepository).findById(anyLong());
        doNothing().when(tagRepository).delete(anyLong());
        assertThrows(NotFoundException.class, () -> tagService.delete(1L));
    }
    @Test
    public void testDeleteThrowsModificationException() {
        doThrow(new DataAccessException("failed to delete") {}).when(tagRepository).delete(anyLong());
//        tagRepository.findById(1L);
        assertThrows(ModificationException.class, () -> tagService.delete(1L));
    }
}