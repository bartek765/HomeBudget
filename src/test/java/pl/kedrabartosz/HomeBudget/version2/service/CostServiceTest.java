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
    }

    // todo correct this test + implement 2nd test for failure
    @Test
    public void shouldSaveNewCost() {
        // given
        when(itemService.getItem(eq(ANY_ITEM_ID))).thenReturn(itemEntity);
        CostEntity builtCost = CostEntity.builder()
                .price(ANY_PRICE)
                .effectiveDate(ANY_DATE)
                .itemEntity(itemEntity)
                .build();
        when(costRepository.save(eq(builtCost))).thenReturn(builtCost);

        // when
        var result = costService.saveNewCost(ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        Assertions.assertEquals(builtCost, result);
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
        // given
        CostEntity current = CostEntity.builder()
            .id(ANY_COST_ID)
            .price(4.99d)
            .effectiveDate(Instant.MIN)
            .itemEntity(itemEntity)
            .build();
        when(costRepository.getReferenceById(eq(ANY_COST_ID))).thenReturn(current);

        CostEntity expected = CostEntity.builder()
            .id(ANY_COST_ID)
            .price(ANY_PRICE)
            .effectiveDate(ANY_DATE)
            .itemEntity(itemEntity)
            .build();

        when(costRepository.save(eq(expected))).thenReturn(expected);

        // when
        var updated = costService.updateCost(ANY_COST_ID, ANY_PRICE, ANY_DATE, ANY_ITEM_ID);

        // then
        Assertions.assertEquals(expected, updated);
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