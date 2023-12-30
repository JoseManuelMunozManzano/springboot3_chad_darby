package com.jmunoz.aopdemo.dao;

import org.springframework.stereotype.Repository;

// Habilitando la clase para el escaneo de componentes
@Repository
public class AccountDAOImpl implements AccountDAO {

  @Override
  public void addAccount() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING AN ACCOUNT");
  }
  
  @Override
  public void addSillyMember() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A SILLY MEMBER ACCOUNT");
  }

}
