package mmazurkiewicz.services;

import mmazurkiewicz.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

   // CustomerDTO getCustomerByFirstAndLastName(String firstName, String lastName);

    CustomerDTO getCustomerById(Long id);
}
