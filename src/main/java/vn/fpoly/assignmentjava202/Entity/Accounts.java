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
@Table(name="Accounts")
public class Accounts {
    @Id
    @Column(name="Username")
    private String username;

    @Column(name="Password")
    private String password;

    @Column(name="Fullname")
    private String fullname;

    @Column(name="Email")
    private String email;

    @Column(name="Photo")
    private String photo;

    @Column(name="Activated")
    private Boolean activated;

    @Column(name="Admin")
    private Boolean admin;

    @OneToMany(mappedBy = "account")
    List<Orders> orders;
}
