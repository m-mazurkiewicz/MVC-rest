package mmazurkiewicz.services;

import mmazurkiewicz.api.v1.mapper.CustomerMapper;
import mmazurkiewicz.api.v1.model.CustomerDTO;
import mmazurkiewicz.domain.Customer;
import mmazurkiewicz.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static mmazurkiewicz.controllers.v1.CustomerController.BASE_URL;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    public static final Long ID = 1L;
    public static final String FIRST_NAME = "John";
    public static final String LAST_NAME = "Lock";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception {

        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(2, customerDTOS.size());
    }

/*    @Test
    public void getCustomerByFirstAndLastName() throws Exception {

        //given
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setId(ID);

        when(customerRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(customer);

        //when
        CustomerDTO customerDTO = customerService.getCustomerByFirstAndLastName(FIRST_NAME, LAST_NAME);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(ID, customerDTO.getId());
    }*/

    @Test
    public void testGetCustomerById() throws Exception{

        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        assertEquals(FIRST_NAME, customerDTO.getFirstName());
    }

    @Test
    public void testCreateNewCustomer() throws Exception{

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals(BASE_URL + "/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void testSaveCustomerByDTO() throws Exception{

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(ID, customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDTO.getFirstName());
        assertEquals(BASE_URL + "/1", savedDTO.getCustomerUrl());
    }

    @Test
    public void testDeleteCustomerById() throws Exception{
        customerRepository.deleteById(ID);

        verify(customerRepository, times(1)).deleteById(anyLong());
    }
}