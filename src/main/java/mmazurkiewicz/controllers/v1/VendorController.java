package mmazurkiewicz.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mmazurkiewicz.api.v1.model.VendorDTO;
import mmazurkiewicz.api.v1.model.VendorListDTO;
import mmazurkiewicz.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static mmazurkiewicz.controllers.v1.VendorController.BASE_URL;

@Api(description = "This is Vendors controller.")
@RestController
@RequestMapping(BASE_URL)
public class VendorController {

    public static final String BASE_URL = "/api/v1/vendors";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "This returns all vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @ApiOperation(value = "This returns vendor of specific ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable String id){
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @ApiOperation(value = "this creates new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
        return vendorService.createNewVendor(vendorDTO);
    }

    @ApiOperation(value = "This changes existing vendor")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(Long.valueOf(id), vendorDTO);
    }

    @ApiOperation(value = "This changes existing vendor")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(Long.valueOf(id), vendorDTO);
    }

    @ApiOperation(value = "This deletes existing vendor")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable String id){
        vendorService.deleteVendorById(Long.valueOf(id));
    }
}
