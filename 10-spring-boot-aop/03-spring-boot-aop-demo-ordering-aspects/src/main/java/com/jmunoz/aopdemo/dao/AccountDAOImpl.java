package com.jmunoz.aopdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jmunoz.aopdemo.Account;

// Habilitando la clase para el escaneo de componentes
@Repository
public class AccountDAOImpl implements AccountDAO {

  private String name;
  private String serviceCode;

  @Override
  public List<Account> findAccounts() {
    // Don't throw an exception
    return findAccounts(false);
  }

  @Override
  public List<Account> findAccounts(boolean tripWire) {

    // for academic purposes ... simulate an exception
    if (tripWire) {
      throw new RuntimeException("No soup for you!!!");
    }

    List<Account> myAccounts = new ArrayList<>();

    // create sample accounts
    Account temp1 = new Account("Marina", "Silver");
    Account temp2 = new Account("Tania", "Platinum");
    Account temp3 = new Account("Adri", "Gold");

    // add them to our accounts list
    myAccounts.add(temp1);
    myAccounts.add(temp2);
    myAccounts.add(temp3);

    return myAccounts;    
  }  

  @Override
  public void addAccount() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING AN ACCOUNT");
  }
  
  @Override
  public void addSillyMember() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A SILLY MEMBER ACCOUNT");
  }
  
  @Override
  public void addAccount(Account theAccount) {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A CONCRETE ACCOUNT");
  }
  
  @Override
  public void addAccount(Account theAccount, boolean vipFlag) {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A CONCRETE VIP ACCOUNT");
  }
  
  @Override
  public boolean doWork() {
    System.out.println(getClass() + ": doWork()");
    return true;
  }

  // getter/setter methods

  public String getName() {
    System.out.println(getClass() + ": getName()");
    return name;
  }

  public void setName(String name) {
    System.out.println(getClass() + ": setName()");
    this.name = name;
  }

  public String getServiceCode() {
    System.out.println(getClass() + ": getServiceCode()");
    return serviceCode;
  }

  public void setServiceCode(String serviceCode) {
    System.out.println(getClass() + ": setServiceCode()");
    this.serviceCode = serviceCode;
  }

}
