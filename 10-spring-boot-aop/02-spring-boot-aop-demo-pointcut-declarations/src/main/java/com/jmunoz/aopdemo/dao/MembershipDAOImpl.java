package com.jmunoz.aopdemo.dao;

import org.springframework.stereotype.Repository;

// Habilitando la clase para el escaneo de componentes
@Repository
public class MembershipDAOImpl implements MembershipDAO {

  @Override
  public void addAccount() {
    System.out.println(getClass() + ": DOING MY DB WORK: ADDING A MEMBERSHIP ACCOUNT");
  }
  
  @Override
  public void goToSleep() {
    System.out.println(getClass() + ": I'm going to sleep now...");
  }

}
