package pl.kedrabartosz.HomeBudget.version2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kedrabartosz.HomeBudget.version2.entities.CostEntity;
import pl.kedrabartosz.HomeBudget.version2.entities.ItemEntity;
import pl.kedrabartosz.HomeBudget.version2.repositories.CostRepository;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.*;

class CostServiceTest {

    private static final int ANY_COST_ID = 1;
    private static final int ANY_ITEM_ID = 2;
    private static final double ANY_PRICE = 123.45;
    private static final Instant ANY_DATE = Instant.now();

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
        verify(costRepository).findAll();
    }

    @Test
    public void shouldSaveNewCost() {
        // given
        when(itemService.getItem(eq(ANY_ITEM_ID))).thenReturn(itemEntity);
        CostEntity builtCost = CostEntity.builder()
                .price(ANY_PRICE)
                .effectiveDate(ANY_DATE)
                .itemEntity(itemEntity)
                .build();
        when(costRepository.save(any(CostEntity.class))).thenReturn(builtCost);

        // when
        var result = costService.saveNewCost(ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        verify(itemService).getItem(eq(ANY_ITEM_ID));
        verify(costRepository).save(any(CostEntity.class));
        Assertions.assertEquals(builtCost, result);
    }

    @Test
    public void shouldFindCostById() {
        // given
        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(costEntity1);

        // when
        var result = costService.findById(ANY_COST_ID);

        // then
        verify(costRepository).getReferenceById(eq(ANY_COST_ID));
        Assertions.assertEquals(costEntity1, result);
    }


    @Test
    public void shouldUpdateCost() {
        // given
        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(costEntity1);
        when(itemService.getItem(eq(ANY_ITEM_ID))).thenReturn(itemEntity);
        when(costRepository.save(eq(costEntity1))).thenReturn(costEntity1);

        // when
        var updated = costService.updateCost(ANY_COST_ID, ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        verify(costEntity1).setPrice(ANY_PRICE);
        verify(costEntity1).setEffectiveDate(ANY_DATE);
        verify(costEntity1).setItemEntity(itemEntity);
        verify(costRepository).save(costEntity1);
        Assertions.assertEquals(costEntity1, updated);
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