package mmazurkiewicz.bootstrap;


import mmazurkiewicz.domain.Category;
import mmazurkiewicz.domain.Customer;
import mmazurkiewicz.repositories.CategoryRepository;
import mmazurkiewicz.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner{

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
        loadCustomers();
    }

    private void loadCategories(){
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data Loaded = " + categoryRepository.count() );
    }

    private void loadCustomers(){

        Customer customer = new Customer();
        customer.setFirstName("Joe");
        customer.setLastName("Newman");

        Customer customer1 = new Customer();
        customer1.setFirstName("David");
        customer1.setLastName("Winter");

        Customer customer2 = new Customer();
        customer2.setFirstName("Anne");
        customer2.setLastName("Hine");

        Customer customer3 = new Customer();
        customer3.setFirstName("fred");
        customer3.setLastName("meyers");

        Customer customer4 = new Customer();
        customer4.setFirstName("Andrea");
        customer4.setLastName("Il grosso");

        Customer customer5 = new Customer();
        customer5.setFirstName("pippo");
        customer5.setLastName("pluto");

        Customer customer6 = new Customer();
        customer6.setFirstName("Andrea");
        customer6.setLastName("Terzi");

        Customer customer7 = new Customer();
        customer7.setFirstName("Fabiano");
        customer7.setLastName("Gaeta");

        customerRepository.save(customer);
        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
        customerRepository.save(customer5);
        customerRepository.save(customer6);
        customerRepository.save(customer7);

        System.out.println("Customer data loaded " + customerRepository.count());
    }
}
