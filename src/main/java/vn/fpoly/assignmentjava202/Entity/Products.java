package vn.fpoly.assignmentjava202.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="Name")
    private String name;

    @Column(name="Image")
    private String image;


    @Column(name ="Price")
    private Float price;

    @Column(name="CreateDate")
private LocalDate createDate;

    @Column(name="Available")
    private Boolean available;

//    @Column(name="CategoryId")
//    private String categoryId;

    @Column(name="Quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private Catergories category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails> orderDetails;
}
