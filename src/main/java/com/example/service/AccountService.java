package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.exception.*;


@Service
public class AccountService {
    private final AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    public Account registerAccount(Account account) {

        if (account.getUsername().isBlank()) {
            throw new BadRequestException("Username cannot be blank");
        }
        
        if (account.getPassword().length() < 4) {
            throw new BadRequestException("Password must be at least 4 characters long");
        }
        
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new ResourceExistsException("Account", "username", account.getUsername());
        }
        
        return accountRepository.save(account);
    }
    
    public Account login(Account loginAttempt) {
        return accountRepository.findByUsername(loginAttempt.getUsername())
                .filter(account -> account.getPassword().equals(loginAttempt.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));
    }
    
    public boolean accountExists(Integer accountId) {
        return accountRepository.existsById(accountId);
    }
    
    public Account getAccountById(Integer accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", accountId));
    }
}