package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class Order
 */
@Root(name = "Order")
public class Order {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 0)
  private Long id;
  @Element
  @CsvCustomBindByPosition(position = 1, converter = ProductConverter.class)
  private Product product;
  @Element
  @CsvCustomBindByPosition(position = 2, converter = CustomerConverter.class)
  private Customer customer;
  @Element
  @CsvBindByPosition(position = 5)
  private ProductType type;
  
  //
  // Constructors
  //
  public Order () { };

  public Order(Long id, Product product, Customer customer) {
    this.id = id;
    this.product = product;
    this.customer = customer;
    this.type = product.getType();
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
   * Set the value of product
   * @param newVar the new value of product
   */
  public void setProduct (Product newVar) {
    product = newVar;
  }

  /**
   * Get the value of product
   * @return the value of product
   */
  public Product getProduct () {
    return product;
  }

  /**
   * Set the value of customer
   * @param newVar the new value of customer
   */
  public void setCustomer (Customer newVar) {
    customer = newVar;
  }

  /**
   * Get the value of customer
   * @return the value of customer
   */
  public Customer getCustomer () {
    return customer;
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


  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", product=" + product +
            ", customer=" + customer +
            ", type=" + type +
            '}';
  }
}
