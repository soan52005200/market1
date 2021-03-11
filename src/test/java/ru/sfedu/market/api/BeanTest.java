package ru.sfedu.market.api;

import com.opencsv.bean.CsvBindByPosition;
import org.junit.Test;
import org.simpleframework.xml.Element;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.sfedu.market.utils.Status.SUCCESS;
import static ru.sfedu.market.utils.Status.UNSUCCESSFUL;

public class BeanTest {

    protected final IDataProvider csv;

    public BeanTest(IDataProvider csv) {
        this.csv = csv;
    }


    public Customer createCustomer(Long id, String name, int age) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFio(name);
        customer.setAge(age);

        return customer;
        //public Product createProduct(Long id,Date receiptDate,String name,String name,String manufacturer,Integer ageLimit, )


    }

    public Eatable createEatable(Long id, Date receiptDate, String name, String manufacturer, Integer ageLimit,Date bestBefore) {
        Eatable eatable = new Eatable();
        eatable.setId(id);
        eatable.setReceiptDate(receiptDate);
        eatable.setName(name);
        eatable.setManufacturer(manufacturer);
        eatable.setAgeLimit(ageLimit);
        eatable.setBestBefore(bestBefore);

        return eatable;


    }

    public Uneatable createUneatable(Long id, Date receiptDate, String name, String manufacturer, Integer ageLimit) {
        Uneatable uneatable= new Uneatable();
        uneatable.setId(id);
        uneatable.setReceiptDate(receiptDate);
        uneatable.setName(name);
        uneatable.setManufacturer(manufacturer);
        uneatable.setAgeLimit(ageLimit);

        return uneatable;
    }
    public Order createOrder(Long id, Product product, Customer customer) {
        Order order= new Order();
        order.setId(id);
        order.setProduct(product);
        order.setCustomer(customer);

        return order;
    }
}



