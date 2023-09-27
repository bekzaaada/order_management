package com.example.manageuser.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String uid;//  ->id
    private String username;
    private String password;
    private int type;
    private Date createTime;
    private Date lastLogin;
    private String refreshtokenuser;
    private int expireSecond;


}
