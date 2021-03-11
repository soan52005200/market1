package ru.sfedu.market.bean;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Class Eatable
 */
@Root(name = "Eatable")
public class Eatable extends Product {

  //
  // Fields
  //

  @Element
  @CsvBindByPosition(position = 6)
  @CsvDate("dd.MM.yyyy")
  private Date bestBefore;
  
  //
  // Constructors
  //
  public Eatable () { };

  public Eatable(Long id, Date receiptDate, String name, String manufacturer, Integer ageLimit, Date bestBefore) {
    super(id, receiptDate, name, manufacturer, ageLimit, ProductType.EATABLE);
    this.bestBefore = bestBefore;
  }

  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of bestBefore
   * @param newVar the new value of bestBefore
   */
  public void setBestBefore (Date newVar) {
    bestBefore = newVar;
  }

  /**
   * Get the value of bestBefore
   * @return the value of bestBefore
   */
  public Date getBestBefore () {
    return bestBefore;
  }

  //
  // Other methods
  //


  @Override
  public String toString() {
    return "Eatable{" +
            "bestBefore=" + bestBefore +
            ", id=" + id +
            ", receiptDate=" + receiptDate +
            ", name='" + name + '\'' +
            ", manufacturer='" + manufacturer + '\'' +
            ", ageLimit=" + ageLimit +
            ", type=" + type +
            '}';
  }
}
