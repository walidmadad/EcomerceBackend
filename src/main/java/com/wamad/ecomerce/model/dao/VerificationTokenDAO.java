package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.List;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(User user);
    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
