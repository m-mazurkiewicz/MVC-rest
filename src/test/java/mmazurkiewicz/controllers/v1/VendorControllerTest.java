package mmazurkiewicz.controllers.v1;

import mmazurkiewicz.api.v1.model.VendorDTO;
import mmazurkiewicz.controllers.RestResponseEntityExceptionHandler;
import mmazurkiewicz.services.ResourceNotFoundException;
import mmazurkiewicz.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static mmazurkiewicz.controllers.v1.AbstractRestControllerTest.asJsonString;
import static mmazurkiewicz.controllers.v1.VendorController.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {

    //public static final Long ID = 1L;
    public static final String NAME = "John";
    //public static final String LAST_NAME = "Lock";

    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(BASE_URL + "/1");

        VendorDTO vendorDTO1 = new VendorDTO();
        vendorDTO1.setName("Jack");
        vendorDTO1.setVendorUrl(BASE_URL + "/2");

        List<VendorDTO> vendorDTOS = Arrays.asList(vendorDTO, vendorDTO1);


        when(vendorService.getAllVendors()).thenReturn(vendorDTOS);

        mockMvc.perform(get(BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetVendorById() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);
        vendorDTO.setVendorUrl(BASE_URL + "/1");

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);

        //when
        mockMvc.perform(get(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }

    @Test
    public void createNewVendor() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendorDTO.getName());
        returnDTO.setVendorUrl(BASE_URL + "/1");

        when(vendorService.createNewVendor(vendorDTO)).thenReturn(returnDTO);

        mockMvc.perform(post(BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

    @Test
    public void testUpdateVendor() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(NAME);
        returnDTO.setVendorUrl(BASE_URL + "/1");

        when(vendorService.saveVendorByDTO(anyLong(), ArgumentMatchers.any(VendorDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(BASE_URL + "/1")));
    }

//    @Test
//    public void testPatchVendor() throws Exception{
//
//        //given
//        VendorDTO vendorDTO = new VendorDTO();
//        vendorDTO.setName(NAME);
//
//        VendorDTO returnDTO = new VendorDTO();
//        returnDTO.setName();
//
//        CustomerDTO returnDTO = new CustomerDTO();
//        returnDTO.setFirstName(customerDTO.getFirstName());
//        returnDTO.setLastName(LAST_NAME);
//        returnDTO.setCustomerUrl(BASE_URL + "/1");
//
//        when(customerService.patchCustomer(anyLong(), ArgumentMatchers.any(CustomerDTO.class))).thenReturn(returnDTO);
//
//        mockMvc.perform(patch(BASE_URL + "/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(customerDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
//                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
//                .andExpect(jsonPath("$.customerUrl", equalTo(BASE_URL + "/1")));
//
//    }

    @Test
    public void testDeleteById() throws Exception{
        mockMvc.perform(delete(BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception{
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL + "/222")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
