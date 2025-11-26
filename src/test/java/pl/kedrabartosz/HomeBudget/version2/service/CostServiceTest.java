package pl.kedrabartosz.HomeBudget.version2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version2.entities.CostEntity;
import pl.kedrabartosz.HomeBudget.version2.entities.ItemEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.CostRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CostServiceTest {

    private static final int ANY_COST_ID = 1;
    private static final int ANY_ITEM_ID = 2;
    private static final BigDecimal ANY_PRICE = BigDecimal.valueOf(123.45);
    private static final LocalDate ANY_DATE = LocalDate.now();

    private CostService costService;

    @Mock
    private CostRepository costRepository;

    @Mock
    private ItemService itemService;

    @Mock
    private CostEntity costEntity1;

    @Mock
    private CostEntity costEntity2;

    @Mock
    private ItemEntity itemEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        costService = new CostService(costRepository, itemService);
    }

    @Test
    public void shouldGetAllCost() {
        // given
        List<CostEntity> expected = List.of(costEntity1, costEntity2);
        when(costRepository.findAll()).thenReturn(expected);

        // when
        var actual = costService.getAllCost();

        // then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowExceptionWhenItemNotFound() {
        // given
        when(itemService.doesItemExits(eq(ANY_ITEM_ID))).thenReturn(false);

        // when + then
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                costService.saveNewCost(ANY_PRICE, ANY_DATE, ANY_ITEM_ID)
        );

        verifyNoInteractions(costRepository);
    }

    @Test
    public void shouldSaveNewCost() {
        // given
        when(itemService.doesItemExits(eq(ANY_ITEM_ID))).thenReturn(true);
        when(itemService.getItem(eq(ANY_ITEM_ID))).thenReturn(itemEntity);

        // zwracamy to, co przyszło do save – wygodny wzorzec w testach
        when(costRepository.save(any(CostEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        var result = costService.saveNewCost(ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(ANY_PRICE, result.getPrice());
        Assertions.assertEquals(ANY_DATE, result.getEffectiveDate());
        Assertions.assertEquals(itemEntity, result.getItemEntity());

        verify(costRepository).save(any(CostEntity.class));
    }

    @Test
    public void shouldFindCostById() {
        // given
        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(costEntity1);

        // when
        var result = costService.findById(ANY_COST_ID);

        // then
        Assertions.assertEquals(costEntity1, result);
    }

    @Test
    public void shouldUpdateCost() {
        // given – obecny stan w bazie
        CostEntity current = CostEntity.builder()
                .id(ANY_COST_ID)
                .price(BigDecimal.valueOf(4.99))
                .effectiveDate(ANY_DATE.minusDays(1))
                .itemEntity(itemEntity)
                .build();

        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(current);


        when(costRepository.save(any(CostEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        var updated = costService.updateCost(ANY_COST_ID, ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        Assertions.assertNotNull(updated);
        Assertions.assertEquals(ANY_COST_ID, updated.getId());
        Assertions.assertEquals(ANY_PRICE, updated.getPrice());
        Assertions.assertEquals(ANY_DATE, updated.getEffectiveDate());
        Assertions.assertEquals(itemEntity, updated.getItemEntity());

        verify(costRepository).save(any(CostEntity.class));
    }

    @Test
    public void shouldDeleteCost() {
        // given
        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(costEntity2);

        // when
        var deleted = costService.deleteCost(ANY_COST_ID);

        // then
        verify(costRepository).delete(eq(costEntity2));
        Assertions.assertEquals(costEntity2, deleted);
    }
}