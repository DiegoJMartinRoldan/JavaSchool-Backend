package es.javaschool.springbootosisfinal_task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {
    private ProductDTO productDTO;
    private Integer quantity;

}
