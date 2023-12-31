package com.jmunoz.aopdemo.dao;

import java.util.List;

import com.jmunoz.aopdemo.Account;

public interface AccountDAO {
  
  // add a new method: findAccounts()
  List<Account> findAccounts();

  void addAccount();

  void addSillyMember();

  void addAccount(Account theAccount);

  void addAccount(Account theAccount, boolean vipFlag);

  boolean doWork();

  public String getName();

  public void setName(String name);

  public String getServiceCode();

  public void setServiceCode(String serviceCode);
  
}
