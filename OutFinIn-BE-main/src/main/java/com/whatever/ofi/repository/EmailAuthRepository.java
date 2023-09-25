package com.whatever.ofi.repository;

import com.whatever.ofi.requestDto.EmailAuthDto;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRepository extends CrudRepository<EmailAuthDto, String>{
}
