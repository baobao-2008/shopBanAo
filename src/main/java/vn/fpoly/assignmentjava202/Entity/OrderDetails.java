package vn.fpoly.assignmentjava202.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="OrderDetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Products product;
}
