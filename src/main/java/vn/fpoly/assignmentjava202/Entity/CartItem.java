package vn.fpoly.assignmentjava202.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Integer id;
    private String productName;
    private String productImage;
    private Double Price;
    private Integer Quantity;

    public Double getTotalPrice() {
        return Price * Quantity;
    }
}
