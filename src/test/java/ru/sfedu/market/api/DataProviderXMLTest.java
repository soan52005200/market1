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
        result = xml.register(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);

    }

    @Test
    public void registerCustomerFail() {
        Result<Customer> result;
//
//        save bean unsuccessful
        result = xml.register(createCustomer());
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

        result = xml.editCustomer(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void updateCustomerFail(){
        Result<Customer> result;

        result = xml.editCustomer(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void deleteCustomerSuccess() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteCustomerFail() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
    @Test
    public void appendEatableSuccess(){
        Result<Eatable> result;
        result= xml.appendEatableProduct(createEatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendEatableFail(){
        Result<Eatable> result;
        result= xml.appendEatableProduct(createEatable());
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
        Result<Void> voidResult = xml.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteEatableFail() {
        Result<Void> voidResult = xml.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
    @Test
    public void appendUneatableSuccess(){
        Result<Uneatable> result;
        result= xml.appendUneatableProduct(createUneatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendUneatableFail(){
        Result<Uneatable> result;
        result= xml.appendUneatableProduct(createUneatable());
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
        Result<Void> voidResult = xml.removeUneatableProductById(createUneatable().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteUneatableFail() {
        Result<Void> voidResult = xml.removeUneatableProductById(createUneatable().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}


    @Test
    public void createOrderSuccess(){
        Result<Order> result;
        result= xml.createOrder(createOrder());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }
    @Test
    public void createOrderFail(){
        Result<Order> result;
        result= xml.createOrder(createOrder());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void orderDeleteSuccess() {
        Result<Void> voidResult = xml.closeOrderById(createOrder().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }
    @Test
    public void orderDeleteFail() {
        Result<Void> voidResult = xml.closeOrderById(createOrder().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }

}