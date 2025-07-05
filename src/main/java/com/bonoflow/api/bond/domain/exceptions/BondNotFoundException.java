package com.bonoflow.api.bond.domain.exceptions;

public class BondNotFoundException extends RuntimeException {
  public BondNotFoundException(Long bondId) {
    super("Bond with id " + bondId + " not found");
  }
}