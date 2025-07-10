package pl.kedrabartosz.HomeBudget.version2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version2.entities.QuantityEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.QuantityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class QuantityServiceTest {

    private static final int ANY_QUANTITY_ID = 1;

    private QuantityService quantityService;

    @Mock
    private QuantityRepository quantityRepository;

    @Mock
    private QuantityEntity quantityEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        quantityService = new QuantityService(quantityRepository);
    }

    @Test
    public void shouldGetQuantityById() {
        // given
        when(quantityRepository.getReferenceById(eq(ANY_QUANTITY_ID))).thenReturn(quantityEntity);

        // when
        var result = quantityService.getQuantity(ANY_QUANTITY_ID);

        // then
        verify(quantityRepository).getReferenceById(eq(ANY_QUANTITY_ID));
        assertEquals(quantityEntity, result);
    }

    @Test
    public void shouldGetAllQuantities() {
        // given
        List<QuantityEntity> expected = List.of(quantityEntity);
        when(quantityRepository.findAll()).thenReturn(expected);

        // when
        var result = quantityService.getAllQuantities();

        // then
        verify(quantityRepository).findAll();
        assertEquals(expected, result);
    }
}