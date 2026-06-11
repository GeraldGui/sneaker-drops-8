package com.pluralsight.sneakerdrops;

import com.pluralsight.sneakerdrops.data.BrandRepository;
import com.pluralsight.sneakerdrops.data.SneakerRepository;
import com.pluralsight.sneakerdrops.models.Brand;
import com.pluralsight.sneakerdrops.models.Sneaker;
import com.pluralsight.sneakerdrops.service.DropService;
import com.pluralsight.sneakerdrops.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class StartupRunner implements CommandLineRunner {

    private final BrandRepository brandRepository;
    private final SneakerRepository sneakerRepository;

    private final DropService dropService;
    private final InventoryService inventoryService;

    @Autowired
    public StartupRunner(BrandRepository brandRepository, SneakerRepository sneakerRepository, DropService dropService, InventoryService inventoryService) {
        this.brandRepository = brandRepository;
        this.sneakerRepository = sneakerRepository;
        this.dropService = dropService;
        this.inventoryService = inventoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        System.out.println(dropService.getStatus());
        System.out.println(inventoryService.getModule());

        for (Brand brand : brandRepository.findAll()) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Sneaker Library ---");
            System.out.println("1) List all sneakers");
            System.out.println("2) Find by model");
            System.out.println("3) Find by price");
            System.out.println("4) Find by year");
            System.out.println("5) Search");
            System.out.println("6) Find by Id");
            System.out.println("7) Add sneaker");
            System.out.println("8) Update sneaker");
            System.out.println("9) Delete sneaker");
            System.out.println("0) Quit");
            System.out.print("Your Choice: ");

            switch (scanner.nextInt()) {
                case 1 -> listSneakers();
                case 2 -> findByModel(scanner);
                case 3 -> findByPrice(scanner);
                case 4 -> findByYear(scanner);
                case 5 -> findBySearch(scanner);
                case 6 -> viewById(scanner);
                case 7 -> addSneaker(scanner);
                case 8 -> updateSneakerPrice(scanner);
                case 9 -> deleteSneaker(scanner);
                case 0 -> running = false;
                default -> System.out.println("Wrong Input!");
            }
        }
    }


    private void listSneakers() {
        System.out.println("You have " + sneakerRepository.count() + " sneakers:");
        for (Sneaker sneaker : sneakerRepository.findAll()) {
            System.out.printf("%d - %s ($%.2f %d)%n", sneaker.getId(), sneaker.getModel(), sneaker.getPrice(), sneaker.getReleaseYear());
        }
    }

    private void findByModel(Scanner scanner){
        scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();

        for (Sneaker sneaker : sneakerRepository.findByModelContaining(model)) {
            System.out.println(sneaker.getModel());
        }
    }

    private void findByPrice(Scanner scanner){
        System.out.print("Price: ");
        double price = scanner.nextDouble();

        for (Sneaker sneaker : sneakerRepository.findByPriceLessThan(price)) {
            System.out.printf("%s ($%.2f)%n", sneaker.getModel(), sneaker.getPrice());
        }
    }

    private void findByYear(Scanner scanner){
        System.out.print("Year: ");
        int year = scanner.nextInt();

        for (Sneaker sneaker : sneakerRepository.findByReleaseYear(year)) {
            System.out.println(sneaker.getModel() + "(" + sneaker.getReleaseYear() + ")");
        }
    }

    private void findBySearch(Scanner scanner){
        System.out.print("Enter your max price range: ");
        double price = scanner.nextDouble();

        System.out.print("Enter your min year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        for (Sneaker sneaker : sneakerRepository.search(price, year)) {
            System.out.printf("%s ($%.2f %d)%n", sneaker.getModel(), sneaker.getPrice(), sneaker.getReleaseYear());
        }

    }

    private void viewById(Scanner scanner){
        System.out.print("Sneaker id: ");
        long id = scanner.nextLong();

        Sneaker sneaker = sneakerRepository.findById(id).orElse(null);

        if (sneaker == null) {
            System.out.println("No sneaker with that id.");
        } else {
            System.out.printf("%d - %s ($%.2f)", sneaker.getId(), sneaker.getModel(), sneaker.getPrice());
        }
    }

    private void addSneaker(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Price: ");
        double price = scanner.nextDouble();
        System.out.print("Year: ");
        int year = scanner.nextInt();

        sneakerRepository.save(new Sneaker(model, price, year));
        System.out.println("Added Sneaker!");
    }

    private void updateSneakerPrice(Scanner scanner) {
        System.out.print("Sneaker id: ");
        long id = scanner.nextLong();

        Sneaker sneaker = sneakerRepository.findById(id).orElseThrow(() -> new RuntimeException("No game with id " + id));
        System.out.print("New price: ");
        sneaker.setPrice(scanner.nextDouble());
        sneakerRepository.save(sneaker);
        System.out.println("Updated Price!");
    }

    private void deleteSneaker(Scanner scanner) {
        System.out.print("Sneaker id: ");
        long id = scanner.nextLong();
        if (sneakerRepository.existsById(id)) {
            sneakerRepository.deleteById(id);
            System.out.println("Deleted Sneaker!");
        } else {
            System.out.println("No sneaker with that id.");
        }
    }

    private void seedData() {
        if(brandRepository.count() == 0) {
            brandRepository.save(new Brand("Nike"));
            brandRepository.save(new Brand("Jordan"));
            brandRepository.save(new Brand("Adidas"));
            brandRepository.save(new Brand("New Balance"));
            brandRepository.save(new Brand("Puma"));
        }

        if (sneakerRepository.count() == 0) {
            sneakerRepository.save(new Sneaker("Air Force 1'07", 115.00, 1982));
            sneakerRepository.save(new Sneaker("Air Jordan 13", 150.00, 1997));
            sneakerRepository.save(new Sneaker("Samba OG", 100.00, 1950));
            sneakerRepository.save(new Sneaker("574 Core", 100.00, 1988));
            sneakerRepository.save(new Sneaker("Suede Classic", 75.00, 1968));
        }
    }
}
