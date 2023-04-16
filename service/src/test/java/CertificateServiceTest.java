import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ModificationException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.filter.search.FilterSearch;
import com.epam.esm.filter.sort.FilterSort;
import com.epam.esm.repository.implementation.CertificateRepositoryImpl;
import com.epam.esm.repository.implementation.TagRepositoryImpl;
import com.epam.esm.repository.repository.CertificateRepository;
import com.epam.esm.repository.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.CertificateMapper;
import com.epam.esm.util.FilterMapper;
import com.epam.esm.util.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static util.EntityHolder.*;

@Rollback(value = true)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CertificateMapper certificateMapper;

    @Mock
    private FilterMapper filterMapper;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private CertificateService certificateService;


    @BeforeEach
    public void setUp() {
        certificateMapper = mock(CertificateMapper.class);
        filterMapper = mock(FilterMapper.class);
        tagMapper = mock(TagMapper.class);
        tagRepository = mock(TagRepositoryImpl.class);
        certificateRepository = mock(CertificateRepositoryImpl.class);
        certificateService = new CertificateService(certificateRepository, tagRepository, certificateMapper, filterMapper, tagMapper);
    }

    @Test
    public void testFindAll() throws NotFoundException {
        List<Certificate> certificates = Arrays.asList(certificate);
        when(certificateRepository.findAll()).thenReturn(certificates);
        when(certificateMapper.toCertificateDTO(certificate)).thenReturn(certificateDTO);

        List<CertificateDto> result = certificateService.findAll();

        assertEquals(1, result.size());
        verify(certificateRepository).findAll();
        verify(certificateMapper, times(1)).toCertificateDTO(any(Certificate.class));
    }


    @Test
    public void testFindAllThrowsNotFoundException() {

        when(certificateRepository.findAll()).thenThrow(new DataAccessException("nothing found") {
        });

        assertThrows(NotFoundException.class, () -> certificateService.findAll());
    }

    @Test
    public void testFindById() throws NotFoundException {
        Certificate certificate = Certificate.builder().build();
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.of(certificate));
        when(certificateMapper.toCertificateDTO(any(Certificate.class))).thenReturn(CertificateDto.builder().build());

        CertificateDto result = certificateService.findById(1L);

        assertNotNull(result);
        verify(certificateRepository).findById(anyLong());
        verify(certificateMapper).toCertificateDTO(any(Certificate.class));
    }

    @Test
    public void testFindByIdThrowsNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).findById(0L);
        assertThrows(NotFoundException.class, () -> certificateService.findById(0L));
    }

    @Test
    public void testFindByTag() throws NotFoundException {
        TagDto tagDto = TagDto.builder().build();
        Tag tag = Tag.builder().build();
        Certificate certificate = Certificate.builder().build();
        when(tagMapper.toTag(tagDto)).thenReturn(tag);
        when(certificateRepository.findByTag(tag)).thenReturn(Arrays.asList(certificate));
        when(certificateMapper.toCertificateDTO(any(Certificate.class))).thenReturn(CertificateDto.builder().build());

        List<CertificateDto> result = certificateService.findByTag(tagDto);

        assertEquals(1, result.size());
        verify(tagMapper).toTag(any(TagDto.class));
        verify(certificateRepository).findByTag(any(Tag.class));
        verify(certificateMapper).toCertificateDTO(any(Certificate.class));
    }

    @Test
    public void testFindByTagThrowsNotFoundException() {
        when(tagMapper.toTag(tagDTO)).thenReturn(tag);
        doThrow(new DataAccessException("failed") {
        }).when(certificateRepository).findByTag(tag);

        assertThrows(NotFoundException.class, () -> certificateService.findByTag(tagDTO));
    }

    @Test
    public void testFindBySearchFilterWithResult() throws NotFoundException {
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);
        when(filterMapper.toSearchFilter(searchFilterDTO))
                .thenReturn(searchFilter);
        when(certificateRepository.findBySearchFilter(any(FilterSearch.class))).thenReturn(certificates);
        when(certificateMapper.toCertificateDTO(certificate)).thenReturn(certificateDTO);

        List<CertificateDto> result = certificateService.findBySearchFilter(searchFilterDTO);

        assertEquals(1, result.size());
    }

    @Test
    public void testFindBySearchFilterWithNoResult() {
        when(filterMapper.toSearchFilter(searchFilterDTO))
                .thenReturn(searchFilter);
        doThrow(new DataAccessException("failed") {
        }).when(certificateRepository).findBySearchFilter(searchFilter);

        assertThrows(NotFoundException.class, () -> {
            certificateService.findBySearchFilter(searchFilterDTO);
        });
    }

    @Test
    public void testFindBySortFilterWithResult() throws NotFoundException {
        when(filterMapper.toSortFilter(sortFilterDTO)).thenReturn(sortFilter);
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);
        when(certificateRepository.findBySortFilter(any(FilterSort.class))).thenReturn(certificates);
        when(certificateMapper.toCertificateDTO(any(Certificate.class))).thenReturn(CertificateDto.builder().build());

        List<CertificateDto> result = certificateService.findBySortFilter(sortFilterDTO);

        assertEquals(1, result.size());
    }

    @Test
    public void testFindBySortFilterWithNoResult() {
        when(filterMapper.toSortFilter(sortFilterDTO)).thenReturn(sortFilter);
        doThrow(new DataAccessException("failed") {
        }).when(certificateRepository).findBySortFilter(any(FilterSort.class));

        assertThrows(NotFoundException.class, () -> {
            certificateService.findBySortFilter(sortFilterDTO);
        });
    }

    @Test
    public void testCreate() {
        when(certificateMapper.toCertificate(certificateDTO)).thenReturn(certificate);
        when(certificateRepository.insert(certificate)).thenReturn(1L);
        when(tagMapper.toTag(tagDTO)).thenReturn(tag);
        assertDoesNotThrow(() -> certificateService.create(certificateDTO));

        verify(certificateRepository).insert(certificate);
        verify(tagRepository).insert(tag);
        verify(certificateRepository).insertTags(certificate);
    }

    @Test
    public void testUpdate() {
        assertDoesNotThrow(() ->
                certificateService.update(certificateDTO));
    }

    @Test
    public void testUpdateThrowNotFoundException() {
        CertificateDto newCertificateDto = CertificateDto.builder()
                .id(500L)
                .name("updatedCertificate")
                .build();

        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).findById(any(Long.class));

        assertThrows(NotFoundException.class, () -> certificateService.update(newCertificateDto));
    }

    @Test
    public void testUpdateThrowModificationException() {

        doThrow(new DataAccessException("failed") {
        }).when(certificateRepository).findById(any(Long.class));

        assertThrows(ModificationException.class, () -> certificateService.update(certificateDTO));
    }

    @Test
    public void testDelete() {
        doNothing()
                .when(certificateRepository)
                .delete(0L);
        assertDoesNotThrow(() ->
                certificateService.delete(0L));
    }

    @Test
    public void testDeleteThrowNotFoundException() {
        doThrow(EmptyResultDataAccessException.class).when(certificateRepository).delete(any(Long.class));
        assertThrows(NotFoundException.class, () ->
                certificateService.delete(0L));
    }

    @Test
    public void testDeleteThrowModificationException() {
        doThrow(new DataAccessException("failed") {
        }).when(certificateRepository).delete(any(Long.class));
        assertThrows(ModificationException.class, () ->
                certificateService.delete(0L));
    }
}


