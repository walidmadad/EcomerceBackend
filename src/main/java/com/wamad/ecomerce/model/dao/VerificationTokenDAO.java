package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

public interface VerificationTokenDAO extends ListCrudRepository<VerificationToken, Long> {
}
