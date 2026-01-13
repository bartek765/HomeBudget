package pl.kedrabartosz.HomeBudget;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kedrabartosz.HomeBudget.version2.entities.*;
import pl.kedrabartosz.HomeBudget.version2.repositories.*;

@SpringBootApplication
public class HomeBudgetApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HomeBudgetApplication.class, args);

        PersonEntity personToBeSaved = PersonEntity
                .builder()
                .firstName("tomek")
                .lastName("Jankowski")
                .joinedAt(Instant.now())
                .build();
        PersonEntity kolejnaosoba = PersonEntity.builder().firstName("antek").lastName("ojooj").joinedAt(Instant.now()).build();
        PersonRepository personRepository = context.getBean(PersonRepository.class);
        System.out.println(personRepository.save(personToBeSaved));
        System.out.println(personRepository.save(kolejnaosoba));
        System.out.println(personRepository.findAll());

        CategoryEntity categoryToBeSaved = CategoryEntity
                .builder()
                .name("Car")
                .createdAt(Instant.now())
                .lastUpdatedAt(Instant.now())
                .build();

        CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
        System.out.println(categoryRepository.save(categoryToBeSaved));
        System.out.println(categoryRepository.findAll());

        QuantityEntity quantityToBeSaved = QuantityEntity
                .builder()
                .value("five")
                .build();

        QuantityRepository quantityRepository = context.getBean(QuantityRepository.class);
        System.out.println(quantityRepository.save(quantityToBeSaved));
        System.out.println(quantityRepository.findAll());

        ItemEntity itemToBeSaved = ItemEntity
                .builder()
                .name("bread")
                .categoryEntity(categoryToBeSaved)
                .quantityEntity(quantityToBeSaved)
                .build();

        ItemRepository itemRepository = context.getBean(pl.kedrabartosz.HomeBudget.version2.repositories.ItemRepository.class);
        System.out.println(itemRepository.save(itemToBeSaved));
        System.out.println(itemRepository.findAll());

        CostEntity costToBeSaved = CostEntity
                .builder()
                .price(BigDecimal.valueOf(222))
                .itemEntity(itemToBeSaved)
                .effectiveDate(LocalDate.now())
                .build();

        CostRepository costRepository = context.getBean(CostRepository.class);
        System.out.println(costRepository.save(costToBeSaved));
        System.out.println(costRepository.findAll());

        ReceiptEntity receiptToBeSaved = ReceiptEntity
                .builder()
                .personEntity(personToBeSaved)
                .totalCost(BigDecimal.valueOf(200))
                .purchasedAt(Instant.now())
                .build();

        ReceiptRepository receiptRepository = context.getBean(ReceiptRepository.class);
        System.out.println(receiptRepository.save(receiptToBeSaved));
        System.out.println(receiptRepository.findAll());

        ItemsInReceiptEntity itemsInReceiptToBeSaved = ItemsInReceiptEntity
                .builder()
                .itemEntity(itemToBeSaved)
                .quantity(5)
                .receiptEntity(receiptToBeSaved)
                .build();

        ItemsInReceiptRepository itemsInReceiptRepository = context.getBean(ItemsInReceiptRepository.class);
        System.out.println(itemsInReceiptRepository.save(itemsInReceiptToBeSaved));
        System.out.println(itemsInReceiptRepository.findAll());

    }
}
