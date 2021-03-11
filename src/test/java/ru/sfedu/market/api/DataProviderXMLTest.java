package ru.sfedu.market.api;

import org.junit.Test;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.sfedu.market.utils.Status.SUCCESS;
import static ru.sfedu.market.utils.Status.UNSUCCESSFUL;

public class DataProviderXMLTest extends BeanTest{
    protected Customer getCustomerS() {
        return new Customer(2L, "Ivan", 17);
    }
    protected Customer getCustomerF() { return new Customer(1L, "Andrew", 18); }
    protected Eatable getEatableS() {
        return new Eatable(1L,new Date(),"Молоко","Агрокомплекс",20,new Date());
    }
    protected Eatable getEatableF() {
        return new Eatable(6L,new Date(),"Молоко","Агрокомплекс",20,new Date());
    }
    protected Uneatable getUneatableS() {
        return new Uneatable(1L,new Date(),"Молоко","Агрокомплекс",20);
    }
    protected Uneatable getUneatableF() {
        return new Uneatable(6L,new Date(),"Молоко","Агрокомплекс",20);
    }


    private final IDataProvider xml = new DataProviderXML();

    public DataProviderXMLTest(IDataProvider xml) {
        super(xml);
    }
    @Test
    public void registerCustomerSuccess() {
        Result<Customer> result;
//        save bean
        result = xml.register(createCustomer(1l, "Andrew", 18));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);

    }

    @Test
    public void registerCustomerFail() {
        Result<Customer> result;
//
//        save bean unsuccessful
        result = xml.register(createCustomer(1l, "Andrew", 18));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);


    }
    @Test
    public void getCustomerSuccess(){

        assertTrue(xml.getCustomerById(getCustomerS().getId()).isPresent());
        System.out.println(xml.getCustomerById(getCustomerS().getId()));

    }
    @Test
    public void getCustomerFail(){

        assertTrue(xml.getCustomerById(getCustomerF().getId()).isPresent());
        System.out.println(xml.getCustomerById(getCustomerF().getId()));

    }
    @Test
    public void updateCustomerSuccess(){
        Result<Customer> result;

        result = xml.editCustomer(createCustomer(1L, "Andrew", 18));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void updateCustomerFail(){
        Result<Customer> result;

        result = xml.editCustomer(createCustomer(8L, "Petr", 19));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void deleteCustomerSuccess() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer(1l, "Andrew", 18).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteCustomerFail() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer(1l, "Andrew", 18).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
    @Test
    public void appendEatableSuccess(){
        Result<Eatable> result;
        result= xml.appendEatableProduct(createEatable(1L,new Date(),"Молоко","Агрокомплекс",20,new Date()));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendEatableFail(){
        Result<Eatable> result;
        result= xml.appendEatableProduct(createEatable(1L,new Date(),"Молоко","Агрокомплекс",20,new Date()));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void getEatableSuccess(){

        assertTrue(xml.getEatableProductById(getEatableS().getId()).isPresent());
        System.out.println(xml.getEatableProductById(getEatableS().getId()));

    }
    @Test
    public void getEatableFail(){

        assertTrue(xml.getCustomerById(getCustomerF().getId()).isPresent());
        System.out.println(xml.getEatableProductById(getEatableF().getId()));

    }


    @Test
    public void deleteEatableSuccess() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer(1l, "Andrew", 18).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteEatableFail() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer(1l, "Andrew", 18).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
    @Test
    public void appendUneatableSuccess(){
        Result<Uneatable> result;
        result= xml.appendUneatableProduct(createUneatable(1L,new Date(),"Молоко","Агрокомплекс",20));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendUneatableFail(){
        Result<Uneatable> result;
        result= xml.appendUneatableProduct(createUneatable(1L,new Date(),"Молоко","Агрокомплекс",20));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void getUneatableSuccess(){

        assertTrue(xml.getUneatableProductById(getUneatableS().getId()).isPresent());
        System.out.println(xml.getUneatableProductById(getUneatableS().getId()));

    }
    @Test
    public void getUneatableFail(){

        assertTrue(xml.getUneatableProductById(getUneatableF().getId()).isPresent());
        System.out.println(xml.getUneatableProductById(getUneatableF().getId()));

    }


    @Test
    public void deleteUneatableSuccess() {
        Result<Void> voidResult = xml.removeUneatableProductById(createUneatable(1L,new Date(),"Молоко","Агрокомплекс",20).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteUneatableFail() {
        Result<Void> voidResult = xml.removeUneatableProductById(createUneatable(1L,new Date(),"Молоко","Агрокомплекс",20).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}


    @Test
    public void createOrderSuccess(){
        Result<Order> result;
        result= xml.createOrder(createOrder(1L,new Product(),new Customer()));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }
    @Test
    public void createOrderFail(){
        Result<Order> result;
        result= xml.createOrder(createOrder(1L,new Product(),new Customer()));
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void orderDeleteSuccess() {
        Result<Void> voidResult = xml.closeOrderById(createOrder(1L,new Product(),new Customer()).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }
    @Test
    public void orderDeleteFail() {
        Result<Void> voidResult = xml.closeOrderById(createOrder(1L,new Product(),new Customer()).getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }

}