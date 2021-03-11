package ru.sfedu.market.bean;

import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Class Uneatable
 */
@Root(name = "Uneatable")
public class Uneatable extends Product {

  //
  // Fields
  //

  
  //
  // Constructors
  //
  public Uneatable() { };

  public Uneatable(Long id, Date receiptDate, String name, String manufacturer, Integer ageLimit) {
    super(id, receiptDate, name, manufacturer, ageLimit, ProductType.UNEATABLE);
  }
  //
  // Methods
  //


  //
  // Accessor methods
  //

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "Uneatable{" +
            "id=" + id +
            ", receiptDate=" + receiptDate +
            ", name='" + name + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", ageLimit=" + ageLimit +
            ", type=" + type +
            '}';
  }
}
