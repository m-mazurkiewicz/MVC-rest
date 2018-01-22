package mmazurkiewicz.services;

import mmazurkiewicz.api.v1.mapper.CustomerMapper;
import mmazurkiewicz.api.v1.model.CustomerDTO;
import mmazurkiewicz.domain.Customer;
import mmazurkiewicz.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static mmazurkiewicz.controllers.v1.CustomerController.BASE_URL;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerUrl(BASE_URL + "/" + customer.getId());
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    /*@Override
    public CustomerDTO getCustomerByFirstAndLastName(String firstName, String lastName) {
        return customerMapper.customerToCustomerDTO(customerRepository.findByFirstNameAndLastName(firstName, lastName));
    }*/

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository
                .findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .map(customerDTO -> {
                    customerDTO.setCustomerUrl(BASE_URL + "/" + id);
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);

        returnDTO.setCustomerUrl(BASE_URL + "/" + savedCustomer.getId());

        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if (customerDTO.getFirstName() != null){
                customer.setFirstName(customerDTO.getFirstName());
            }

            if (customerDTO.getLastName() != null){
                customer.setLastName(customerDTO.getLastName());
            }

            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));

            returnDTO.setCustomerUrl(BASE_URL + "/" + id);

            return returnDTO;
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
