package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Class Product
 */
@Root(name = "Product")
public class Product {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 0)
  protected Long id;
  @Element
  @CsvBindByPosition(position = 1)
  @CsvDate("dd.MM.yyyy")
  protected Date receiptDate;
  @Element
  @CsvBindByPosition(position = 2)
  protected String name;
  @Element
  @CsvBindByPosition(position = 3)
  protected String manufacturer;
  @Element
  @CsvBindByPosition(position = 4)
  protected Integer ageLimit;
  @Element
  @CsvBindByPosition(position = 5)
  protected ProductType type;
  
  //
  // Constructors
  //
  public Product () { };

  public Product(Long id, Date receiptDate, String name, String manufacturer, Integer ageLimit, ProductType type) {
    this.id = id;
    this.receiptDate = receiptDate;
    this.name = name;
    this.manufacturer = manufacturer;
    this.ageLimit = ageLimit;
    this.type = type;
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
   * Set the value of receiptDate
   * @param newVar the new value of receiptDate
   */
  public void setReceiptDate (Date newVar) {
    receiptDate = newVar;
  }

  /**
   * Get the value of receiptDate
   * @return the value of receiptDate
   */
  public Date getReceiptDate () {
    return receiptDate;
  }

  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName (String newVar) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName () {
    return name;
  }

  /**
   * Set the value of manufacturer
   * @param newVar the new value of manufacturer
   */
  public void setManufacturer (String newVar) {
    manufacturer = newVar;
  }

  /**
   * Get the value of manufacturer
   * @return the value of manufacturer
   */
  public String getManufacturer () {
    return manufacturer;
  }

  /**
   * Set the value of ageLimit
   * @param newVar the new value of ageLimit
   */
  public void setAgeLimit (Integer newVar) {
    ageLimit = newVar;
  }

  /**
   * Get the value of ageLimit
   * @return the value of ageLimit
   */
  public Integer getAgeLimit () {
    return ageLimit;
  }

  public ProductType getType() {
    return type;
  }

  public void setType(ProductType type) {
    this.type = type;
  }

//
  // Other methods
  //

}
