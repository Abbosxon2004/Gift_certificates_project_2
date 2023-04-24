import com.epam.esm.controller.CertificateController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.SearchFilterDto;
import com.epam.esm.dto.SortFilterDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.response.ExceptionResponse.InvalidRequestException;
import com.epam.esm.response.Response;
import com.epam.esm.service.CertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.List;

import static com.epam.esm.util.ExceptionMessages.CERTIFICATES_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CertificateControllerTest {
    @Mock
    private BindingResult bindingResult;
    @Mock
    private CertificateService certificateService;
    @InjectMocks
    private CertificateController certificateController;

    @BeforeEach
    public void setUp() {
        bindingResult = mock(BindingResult.class);
        certificateService = mock(CertificateService.class);
        certificateController = new CertificateController(certificateService);
    }

    @Test
    public void findAllCertificates() throws NotFoundException {
        List<CertificateDto> list = (List) certificateController.findAll().getContent();
        assertEquals(list.size(), certificateService.findAll().size());
    }

    @Test
    public void findAllCertificatesThrowNotFoundException() throws NotFoundException {
        doThrow(new NotFoundException(CERTIFICATES_NOT_FOUND, new Throwable().getCause())).when(certificateService).findAll();

        assertEquals(
                new Response<>(HttpStatus.NOT_FOUND, new NotFoundException(CERTIFICATES_NOT_FOUND, new Throwable().getCause()).getMessage()),
                certificateController.findAll());
    }

    @Test
    public void findCertificateById() throws NotFoundException {
        when(certificateService.findById(anyLong())).thenReturn(CertificateDto.builder().build());
        Response responsebyId = certificateController.findById(1l);
        assertEquals(responsebyId, new Response(HttpStatus.OK, certificateService.findById(1l)));
    }

    @Test
    public void findCertificateByIdThrowNotFoundException() throws NotFoundException {
        doThrow(NotFoundException.class).when(certificateService).findById(anyLong());
        Response responsebyId = certificateController.findById(1l);
        assertEquals(responsebyId, new Response(HttpStatus.NOT_FOUND, new Throwable().getMessage()));
    }

    @Test
    public void findCertificatesByTag() throws NotFoundException {
        when(certificateService.findByTag(any(TagDto.class))).thenReturn(List.of(CertificateDto.builder().build()));

        Response responsebyTag = certificateController.findByTag(TagDto.builder().name("tag").build(), bindingResult);

        assertEquals(responsebyTag, new Response(HttpStatus.OK, certificateService.findByTag(TagDto.builder().name("tag").build())));
    }

    @Test
    public void findCertificatesByTagInvalidRequestException() throws NotFoundException {
        when(bindingResult.hasErrors()).thenReturn(true);

        when(certificateService.findByTag(any(TagDto.class))).thenReturn(List.of(CertificateDto.builder().build()));

        Response responsebyTag = certificateController.findByTag(TagDto.builder().name("tag").build(), bindingResult);

        assertEquals(responsebyTag,
                new Response<>(new InvalidRequestException(bindingResult).getViolations(),
                        HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void findCertificatesByTagNotFoundExeption() throws NotFoundException {
        doThrow(NotFoundException.class).when(certificateService).findByTag(any(TagDto.class));

        Response responsebyTag = certificateController.findByTag(TagDto.builder().name("tag").build(), bindingResult);

        assertEquals(responsebyTag, new Response(HttpStatus.NOT_FOUND, new Throwable().getMessage()));
    }

    @Test
    public void findBySearchFilterInvalidRequestException() {
        when(bindingResult.hasErrors()).thenReturn(true);
        Response responseBySearchFilter = certificateController.findBySearchFilter(SearchFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySearchFilter,
                new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void findBySearchFilter() throws NotFoundException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(certificateService.findBySearchFilter(any(SearchFilterDto.class))).thenReturn(List.of(CertificateDto.builder().build()));

        Response responseBySearchFilter = certificateController.findBySearchFilter(SearchFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySearchFilter,
                new Response(HttpStatus.OK, List.of(CertificateDto.builder().build())));
    }

    @Test
    public void findBySearchFilterNotFoundException() throws NotFoundException {
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(NotFoundException.class).when(certificateService).findBySearchFilter(any(SearchFilterDto.class));

        Response responseBySearchFilter = certificateController.findBySearchFilter(SearchFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySearchFilter,
                new Response(HttpStatus.NOT_FOUND, new Throwable().getMessage()));
    }

    @Test
    public void findBySortFilter() throws NotFoundException {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(certificateService.findBySortFilter(any(SortFilterDto.class))).thenReturn(List.of(CertificateDto.builder().build()));

        Response responseBySortFilter = certificateController.findBySortFilter(SortFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySortFilter,
                new Response(HttpStatus.OK, List.of(CertificateDto.builder().build())));
    }

    @Test
    public void findBySortFilterInvalidRequestException() {
        when(bindingResult.hasErrors()).thenReturn(true);
        Response responseBySortFilter = certificateController.findBySortFilter(SortFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySortFilter,
                new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void findBySortFilterNotFoundException() throws NotFoundException {
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(NotFoundException.class).when(certificateService).findBySortFilter(any(SortFilterDto.class));

        Response responseBySortFilter = certificateController.findBySortFilter(SortFilterDto.builder().build(), bindingResult);
        assertEquals(responseBySortFilter,
                new Response(HttpStatus.NOT_FOUND, new Throwable().getMessage()));
    }

    @Test
    public void createCertificateInvalidRequestException() {
        when(bindingResult.hasErrors()).thenReturn(true);
        Response response = certificateController.create(CertificateDto.builder().build(), bindingResult);
        assertEquals(response,
                new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void createCertificate() throws ModificationException {
        doNothing().when(certificateService).create(any(CertificateDto.class));
        Response response = certificateController.create(CertificateDto.builder().build(), bindingResult);
        assertEquals(new Response<>(HttpStatus.OK, "certificate was successfully created!"), response);
        assertDoesNotThrow(() -> certificateController.create(CertificateDto.builder().build(), bindingResult));
    }

    @Test
    public void createCertificateThrowModificationException() throws ModificationException {
        doThrow(ModificationException.class).when(certificateService).create(any(CertificateDto.class));
        assertEquals(new Response<>(HttpStatus.NOT_MODIFIED, new Throwable().getMessage()),
                certificateController.create(CertificateDto.builder().build(), bindingResult));
    }

    @Test
    public void editCertificate() throws NotFoundException, ModificationException {
        doNothing().when(certificateService).update(any(CertificateDto.class));
        Response response = certificateController.edit(CertificateDto.builder().build(), bindingResult);
        assertEquals(new Response<>(HttpStatus.OK, "certificate was successfully updated!"), response);
        assertDoesNotThrow(() -> certificateController.edit(CertificateDto.builder().build(), bindingResult));
    }

    @Test
    public void editCertificateInvalidRequestException() {
        when(bindingResult.hasErrors()).thenReturn(true);
        Response response = certificateController.edit(CertificateDto.builder().build(), bindingResult);
        assertEquals(response,
                new Response<>(new InvalidRequestException(bindingResult).getViolations(), HttpStatus.BAD_REQUEST, new InvalidRequestException(bindingResult).getMessage()));
    }

    @Test
    public void editCertificateThrowModificationException() throws ModificationException, NotFoundException {
        doThrow(ModificationException.class).when(certificateService).update(any(CertificateDto.class));
        assertEquals(new Response<>(HttpStatus.NOT_MODIFIED, new Throwable().getMessage()),
                certificateController.edit(CertificateDto.builder().build(), bindingResult));
    }

    @Test
    public void editCertificateThrowNotFoundException() throws ModificationException, NotFoundException {
        doThrow(NotFoundException.class).when(certificateService).update(any(CertificateDto.class));
        assertEquals(new Response<>(HttpStatus.NOT_FOUND, new Throwable().getMessage()),
                certificateController.edit(CertificateDto.builder().build(), bindingResult));
    }

    @Test
    public void deleteCertificate() throws NotFoundException, ModificationException {
        doNothing().when(certificateService).delete(anyLong());
        Response response = certificateController.deleteById(1l);
        assertEquals(new Response<>(HttpStatus.NO_CONTENT, "certificate was successfully deleted!"), response);
        assertDoesNotThrow(() -> certificateController.deleteById(1l));
    }

    @Test
    public void deleteCertificateThrowNotFoundException() throws ModificationException, NotFoundException {
        doThrow(NotFoundException.class).when(certificateService).delete(anyLong());
        assertEquals(new Response<>(HttpStatus.NOT_FOUND, new Throwable().getMessage()),
                certificateController.deleteById(1l));
    }
    @Test
    public void deleteCertificateThrowModificationException() throws ModificationException, NotFoundException {
        doThrow(ModificationException.class).when(certificateService).delete(anyLong());
        assertEquals(new Response<>(HttpStatus.NOT_MODIFIED, new Throwable().getMessage()),
                certificateController.deleteById(1l));
    }
}
