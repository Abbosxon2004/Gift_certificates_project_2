import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ExceptionResponse.InvalidRequestException;
import com.epam.esm.response.Response;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;

import static com.epam.esm.util.ExceptionMessages.TAGS_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private TagService tagService;
    @InjectMocks
    private TagController tagController;

    @BeforeEach
    public void setUp() {
        bindingResult = mock(BindingResult.class);
        tagService = mock(TagService.class);
        tagController = new TagController(tagService);
    }

    @Test
    public void findAllTags() throws NotFoundException {
        List<TagDto> list = (List) tagController.findAll().getContent();
        assertEquals(list.size(), tagService.findAll().size());
    }

    @Test
    public void findAllTagsThrowNotFoundException() throws NotFoundException {
        doThrow(new NotFoundException(TAGS_NOT_FOUND, new Throwable().getCause())).when(tagService).findAll();

        assertEquals(
                new Response<>(HttpStatus.NOT_FOUND, new NotFoundException(TAGS_NOT_FOUND, new Throwable().getCause()).getMessage()),
                tagController.findAll());
    }

    @Test
    public void findTagById() throws NotFoundException {
        when(tagService.findById(anyLong())).thenReturn(TagDto.builder().build());
        Response responsebyId = tagController.findById(1l);
        assertEquals(responsebyId, new Response(HttpStatus.OK, tagService.findById(1l)));
    }

    @Test
    public void findTagByIdThrowNotFoundException() throws NotFoundException {
        doThrow(NotFoundException.class).when(tagService).findById(anyLong());
        Response responsebyId = tagController.findById(1l);
        assertEquals(responsebyId, new Response(HttpStatus.NOT_FOUND, new Throwable().getMessage()));
    }


    @Test
    public void createTagInvalidRequestException() {
        when(bindingResult.hasErrors()).thenReturn(true);
        Response response = tagController.create(TagDto.builder().build(), bindingResult);
        assertEquals(response,
                new Response<>(HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void createTag() throws ModificationException {
        doNothing().when(tagService).create(any(TagDto.class));
        Response response = tagController.create(TagDto.builder().build(), bindingResult);
        assertEquals(new Response<>(HttpStatus.OK, "Tag was successfully created!"), response);
        assertDoesNotThrow(() -> tagController.create(TagDto.builder().build(), bindingResult));
    }

    @Test
    public void createTagThrowModificationException() throws ModificationException {
        doThrow(ModificationException.class).when(tagService).create(any(TagDto.class));
        assertEquals(new Response<>(HttpStatus.NOT_MODIFIED, new Throwable().getMessage()),
                tagController.create(TagDto.builder().build(), bindingResult));
    }


    @Test
    public void deleteTag() throws NotFoundException, ModificationException {
        doNothing().when(tagService).delete(anyLong());
        Response response = tagController.deleteById(1l);
        assertEquals(new Response<>(HttpStatus.NO_CONTENT, "tag was successfully deleted!"), response);
        assertDoesNotThrow(() -> tagController.deleteById(1l));
    }

    @Test
    public void deleteTagThrowNotFoundException() throws ModificationException, NotFoundException {
        doThrow(NotFoundException.class).when(tagService).delete(anyLong());
        assertEquals(new Response<>(HttpStatus.NOT_FOUND, new Throwable().getMessage()),
                tagController.deleteById(1l));
    }

    @Test
    public void deleteTagThrowModificationException() throws ModificationException, NotFoundException {
        doThrow(ModificationException.class).when(tagService).delete(anyLong());
        assertEquals(new Response<>(HttpStatus.NOT_MODIFIED, new Throwable().getMessage()),
                tagController.deleteById(1l));
    }
}
