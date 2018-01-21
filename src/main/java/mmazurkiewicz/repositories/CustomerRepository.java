package mmazurkiewicz.repositories;

import mmazurkiewicz.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //Customer findByFirstNameAndLastName(String firstName, String lastName);
}
