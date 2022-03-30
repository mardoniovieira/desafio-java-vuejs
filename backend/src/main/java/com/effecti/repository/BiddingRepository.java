package com.effecti.repository;

import com.effecti.model.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BiddingRepository extends JpaRepository<Bidding, Long> {
    Bidding findOneByHash(int hash);
    void deleteBiddingByHash(int hash);
}
