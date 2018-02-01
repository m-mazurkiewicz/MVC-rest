package mmazurkiewicz.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    //@ApiModelProperty(value = "Vendor ID")
    //private Long id;

    @ApiModelProperty(value = "Vendor name", required = true)
    private String name;

    @ApiModelProperty(value = "Vendor URL")
    private String vendorUrl;
}
