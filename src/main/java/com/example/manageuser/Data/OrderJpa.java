package com.example.manageuser.Data;

import com.example.manageuser.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpa extends JpaRepository<Order, Long> {
    Order findOrderByOid(String oid);
    Order findOrderByOidAndDeleted(String oid, int deleted);
    Order findAllByOidAndDeleted(String oid, int deleted);
    Order deleteByOidAndDeleted(String oid, int deleted);
}
