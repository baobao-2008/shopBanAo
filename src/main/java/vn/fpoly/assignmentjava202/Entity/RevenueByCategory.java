package vn.fpoly.assignmentjava202.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevenueByCategory {
    private String categoryName;
    private Double totalRevenue;
    private Long totalQuantity;
    private Double maxPrice;
    private Double minPrice;
    private Double avgPrice;


}
