package pl.kedrabartosz.HomeBudget;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.kedrabartosz.HomeBudget.version2.entities.*;
import pl.kedrabartosz.HomeBudget.version2.repositories.*;
import pl.kedrabartosz.HomeBudget.version2.service.ItemService;

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

        PersonRepository personRepository = context.getBean(PersonRepository.class);
        System.out.println(personRepository.save(personToBeSaved));
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
                .price(222)
                .itemEntity(itemToBeSaved)
                .effectiveDate(Instant.now())
                .build();

        CostRepository costRepository = context.getBean(CostRepository.class);
        System.out.println(costRepository.save(costToBeSaved));
        System.out.println(costRepository.findAll());

        ReceiptEntity receiptToBeSaved = ReceiptEntity
                .builder()
                .personEntity(personToBeSaved)
                .totalCost(200)
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

    private static void versionOne(String[] args) {
        // SpringApplication.run(HomeBudgetApplication.class, args);
        // CostRepository costRepository = new ListBasedRepository();
        //costRepository.addCost();
        //System.out.println(costRepository.getAll());

        // 1. new
        pl.kedrabartosz.HomeBudget.version1.repository.ItemRepository itemRepository = new pl.kedrabartosz.HomeBudget.version1.repository.ListBasedItemRepository(new ArrayList<>());

        // 2. builder
        pl.kedrabartosz.HomeBudget.version1.repository.ItemRepository itemRepositoryBuilder = pl.kedrabartosz.HomeBudget.version1.repository.ListBasedItemRepository.builder().build();

        // 3. Factory

        pl.kedrabartosz.HomeBudget.version1.repository.ItemRepository itemRepositoryFactory = pl.kedrabartosz.HomeBudget.version1.repository.ItemRepositoryFactory.createCostRepository();


        // IoC - Inversion of Control, czyli nie my tworzymy obiekty tylko zlecamy to frameworkpwi
        // i to on je stworzy i wstrzyknie (w konstruktorze/setterze) wtedy gdy będa potrzebne a nie na zaś

        ConfigurableApplicationContext context = SpringApplication.run(HomeBudgetApplication.class, args);

        // context - wór ze wszystkimi beanami, którymi ma Spring zarządzać
        // aby móc korzystać z costServicu nie możemy go robić (new CostService())!!, tylko
        // pozwalamy Springowi to zrobić i my chcemy tylko się dostać do tego beana, co on stworzył,
        // i na nim operować (ale nie sami go tworzyć)
        ItemService itemService = context.getBean(ItemService.class);

        pl.kedrabartosz.HomeBudget.version1.Person me = new pl.kedrabartosz.HomeBudget.version1.Person("Bartosz");


        pl.kedrabartosz.HomeBudget.version1.Item newItem = itemService.saveItem("Jewelry", 250.00, pl.kedrabartosz.HomeBudget.version1.Category.builder().build());
        pl.kedrabartosz.HomeBudget.version1.Item newItem5 = itemService.saveItem("Car", 4000, pl.kedrabartosz.HomeBudget.version1.Category.builder().build());

        if (itemService.doesItemExits("Pencil")) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        pl.kedrabartosz.HomeBudget.version1.Item newItem6 = itemService.saveItem("Pencil", 5, pl.kedrabartosz.HomeBudget.version1.Category.builder().build());
        if (itemService.doesItemExits("Pencil")) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        pl.kedrabartosz.HomeBudget.version1.repository.CategoryRepository fileBasedCategoryRepository = new pl.kedrabartosz.HomeBudget.version1.repository.FileBasedCategoryRepository();
        pl.kedrabartosz.HomeBudget.version1.Category buildHome = fileBasedCategoryRepository.save("budowadomu");
        pl.kedrabartosz.HomeBudget.version1.Category jewelry = fileBasedCategoryRepository.save("jewelery");
        System.out.println(buildHome);
        System.out.println(fileBasedCategoryRepository.getAll());

        //shopping cart

        pl.kedrabartosz.HomeBudget.version1.service.ReceiptService cartService = context.getBean(pl.kedrabartosz.HomeBudget.version1.service.ReceiptService.class);


        List<pl.kedrabartosz.HomeBudget.version1.Receipt> all = cartService.getShoppingCarts(me);
        System.out.println("Number cart for " + me.getName() + ": " + all.size());
        all.forEach(System.out::println);

        // @Autowired, @Service, @Component, @Repository
        // HW:  @RestController, @Bean, @Configuration,
        // HW: Scope (czym jest), primary bean*, @Value / @Resource - wstrzykiwanie po NAZWIE, by name

        // DI - Dependency Injection, wstrzykiwanie zależności (this.XXX = YYY), przypisane wartości do pola
        // Guice,
        //@Primary - Służy do wskazania preferowanego beana w przypadku, gdy jest więcej niż jeden bean tego samego typu.
        //@Value - Umożliwia wstrzykiwanie wartości (np. z plików konfiguracyjnych) do pól klasy.
        // @Resource pochodzi od javy a @Autowired od springa
        //Autowired wstrzykuje na podstawie typu a Resource na postawie nazwy
    }
}
// dokonczyc to implemetnowac interfejs Food i tez klasy gdzie beda implementowane te rzeczy! (zrobioone)
// (nazwa jedzenia resturacja czy wyjscia na jedzenie) (zrobione)
// , wstawic do gh, zapoznac sie z nowym wzorcem projektowym Repozytorium!
//poczytac pdfa!!