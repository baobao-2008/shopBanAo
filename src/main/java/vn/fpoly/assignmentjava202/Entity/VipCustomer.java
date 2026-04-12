package vn.fpoly.assignmentjava202.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VipCustomer {
    private String fullname;
    private Double totalSpent;
    private LocalDate firstOrderDate;
    private LocalDate lastOrderDate;
}
