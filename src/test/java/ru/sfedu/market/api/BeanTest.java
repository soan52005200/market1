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


    public Customer createCustomer() {
        Customer customer = new Customer();


        return customer;
        //public Product createProduct(Long id,Date receiptDate,String name,String name,String manufacturer,Integer ageLimit, )


    }

    public Eatable createEatable() {
        Eatable eatable = new Eatable();

        return eatable;


    }

    public Uneatable createUneatable() {
       Uneatable uneatable = new Uneatable();

        return uneatable;
    }
    public Order createOrder() {
        Order order= new Order();


        return order;
    }
}



