package mmazurkiewicz.services;

import mmazurkiewicz.api.v1.mapper.VendorMapper;
import mmazurkiewicz.api.v1.model.VendorDTO;
import mmazurkiewicz.domain.Vendor;
import mmazurkiewicz.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static mmazurkiewicz.controllers.v1.VendorController.BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {

    public static final Long ID = 1L;
    public static final String NAME = "John";

    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getAllVendors() throws Exception {

        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());

        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(2, vendorDTOS.size());
    }

    /*@Test
    public void getVendorByName() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);
        vendor.setId(ID);

        when(vendorRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(customer);

        //when
        CustomerDTO customerDTO = customerService.getCustomerByFirstAndLastName(FIRST_NAME, LAST_NAME);

        //then
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
        assertEquals(ID, customerDTO.getId());
    }*/

    @Test
    public void testGetVendorById() throws Exception{

        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        assertEquals(NAME, vendorDTO.getName());

        //given
        //Vendor vendor = getVendor
    }

    @Test
    public void testCreateNewVendor() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDTO = vendorService.createNewVendor(vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(BASE_URL + "/1", savedDTO.getVendorUrl());
    }

    @Test
    public void testSaveCustomerByDTO() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(ID);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedDTO = vendorService.saveVendorByDTO(ID, vendorDTO);

        //then
        assertEquals(vendorDTO.getName(), savedDTO.getName());
        assertEquals(BASE_URL + "/1", savedDTO.getVendorUrl());
    }

    @Test
    public void testDeleteCustomerById() throws Exception{
        vendorRepository.deleteById(ID);

        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}
