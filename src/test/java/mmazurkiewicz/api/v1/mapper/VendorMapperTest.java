package mmazurkiewicz.api.v1.mapper;

import mmazurkiewicz.api.v1.model.VendorDTO;
import mmazurkiewicz.domain.Vendor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendorMapperTest {

    public static final String NAME = "Joe";
    //public static final Long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public void setUp() throws Exception {
    }

    @Test
    public void vendorToVendorDTO() throws Exception {

        //given
        Vendor vendor = new Vendor();
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertEquals(NAME, vendorDTO.getName());
    }

    @Test
    public void vendorDTOTOVendor() throws Exception{

        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

        //then
        assertEquals(NAME, vendor.getName());
    }
}
