package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class Customer
 */
@Root(name = "Customer")
public class Customer {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 0)
  private Long id;
  @Element
  @CsvBindByPosition(position = 1)
  private String fio;
  @Element
  @CsvBindByPosition(position = 2)
  private Integer age;
  
  //
  // Constructors
  //
  public Customer () { };

  public Customer(Long id, String fio, Integer age) {
    this.id = id;
    this.fio = fio;
    this.age = age;
  }

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public void setId (Long newVar) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public Long getId () {
    return id;
  }

  /**
   * Set the value of fio
   * @param newVar the new value of fio
   */
  public void setFio (String newVar) {
    fio = newVar;
  }

  /**
   * Get the value of fio
   * @return the value of fio
   */
  public String getFio () {
    return fio;
  }

  /**
   * Set the value of age
   * @param newVar the new value of age
   */
  public void setAge (Integer newVar) {
    age = newVar;
  }

  /**
   * Get the value of age
   * @return the value of age
   */
  public Integer getAge () {
    return age;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "Customer{" +
            "id=" + id +
            ", fio='" + fio + '\'' +
            ", age=" + age +
            '}';
  }
}
