package com.jmunoz.aopdemo.dao;

import com.jmunoz.aopdemo.Account;

public interface AccountDAO {
  
  void addAccount();

  void addSillyMember();

  void addAccount(Account theAccount);
}
