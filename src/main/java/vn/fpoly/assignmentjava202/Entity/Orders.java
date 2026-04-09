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
@Table(name="Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="CreateDate")
    private LocalDate createDate = LocalDate.now();

    @Column(name="Address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "Username")
    private Accounts account;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;
}
