package vn.fpoly.assignmentjava202.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Categories")
public class Catergories {
    @Id
    @Column(name="Id")
    private String id;

    @Column(name="Name")
    private String name;

    @OneToMany(mappedBy = "category")
    List<Products> products;
}
