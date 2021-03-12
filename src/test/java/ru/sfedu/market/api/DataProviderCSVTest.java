package ru.sfedu.market.api;


import org.junit.Test;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.util.Date;

import static org.junit.Assert.*;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSVTest extends BeanTest{
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


    private final IDataProvider csv = new DataProviderCSV();

    public DataProviderCSVTest(IDataProvider csv) {
        super(csv);
    }

    @Test
    public void registerCustomerSuccess() {
        Result<Customer> result;
//        save bean
        result = csv.register(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);

    }

    @Test
    public void registerCustomerFail() {
        Result<Customer> result;
//
//        save bean unsuccessful
        result = csv.register(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);


    }
    @Test
    public void getCustomerSuccess(){

        assertTrue(csv.getCustomerById(getCustomerS().getId()).isPresent());
        System.out.println(csv.getCustomerById(getCustomerS().getId()));

    }
    @Test
    public void getCustomerFail(){

        assertTrue(csv.getCustomerById(getCustomerF().getId()).isPresent());
        System.out.println(csv.getCustomerById(getCustomerF().getId()));

    }
    @Test
    public void updateCustomerSuccess(){
        Result<Customer> result;

        result = csv.editCustomer(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void updateCustomerFail(){
        Result<Customer> result;

        result = csv.editCustomer(createCustomer());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void deleteCustomerSuccess() {
        Result<Void> voidResult = csv.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteCustomerFail() {
        Result<Void> voidResult = csv.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
        @Test
    public void appendEatableSuccess(){
        Result<Eatable> result;
        result= csv.appendEatableProduct(createEatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendEatableFail(){
        Result<Eatable> result;
        result= csv.appendEatableProduct(createEatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void getEatableSuccess(){

        assertTrue(csv.getEatableProductById(getEatableS().getId()).isPresent());
        System.out.println(csv.getEatableProductById(getEatableS().getId()));

    }
    @Test
    public void getEatableFail(){

        assertTrue(csv.getCustomerById(getCustomerF().getId()).isPresent());
        System.out.println(csv.getEatableProductById(getEatableF().getId()));

    }


    @Test
    public void deleteEatableSuccess() {
        Result<Void> voidResult = csv.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteEatableFail() {
        Result<Void> voidResult = csv.removeCustomerById(createCustomer().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}
    @Test
    public void appendUneatableSuccess(){
        Result<Uneatable> result;
        result= csv.appendUneatableProduct(createUneatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), SUCCESS);
    }
    @Test
    public void appendUneatableFail(){
        Result<Uneatable> result;
        result= csv.appendUneatableProduct(createUneatable());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void getUneatableSuccess(){

        assertTrue(csv.getUneatableProductById(getUneatableS().getId()).isPresent());
        System.out.println(csv.getUneatableProductById(getUneatableS().getId()));

    }
    @Test
    public void getUneatableFail(){

        assertTrue(csv.getUneatableProductById(getUneatableF().getId()).isPresent());
        System.out.println(csv.getUneatableProductById(getUneatableF().getId()));

    }


    @Test
    public void deleteUneatableSuccess() {
        Result<Void> voidResult = csv.removeUneatableProductById(createUneatable().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);}
    @Test
    public void deleteUneatableFail() {
        Result<Void> voidResult = csv.removeUneatableProductById(createUneatable().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), UNSUCCESSFUL);}


    @Test
    public void createOrderSuccess(){
        Result<Order> result;
        result= csv.createOrder(createOrder());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }
    @Test
    public void createOrderFail(){
        Result<Order> result;
        result= csv.createOrder(createOrder());
        System.out.println(result.toString());
        assertEquals(result.getStatus(), UNSUCCESSFUL);
    }

    @Test
    public void orderDeleteSuccess() {
        Result<Void> voidResult = csv.closeOrderById(createOrder().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }
    @Test
    public void orderDeleteFail() {
        Result<Void> voidResult = csv.closeOrderById(createOrder().getId());
        System.out.println(voidResult.toString());
        assertEquals(voidResult.getStatus(), SUCCESS);
    }

   // @Test
    public void update(){
        assertEquals(csv.register(new Customer(1L, "Ivan", 17)).getStatus(), SUCCESS);
        assertEquals(csv.register(new Customer(2L, "Ivan", 18)).getStatus(), SUCCESS);
        assertEquals(csv.register(new Customer(3L, "Ivan", 19)).getStatus(), SUCCESS);

        System.out.println(csv.getCustomerById(1L));
        System.out.println(csv.getCustomerById(2L));
        System.out.println(csv.getCustomerById(3L));

        assertEquals(csv.editCustomer(new Customer(1L, "Petr", 17)).getStatus(), SUCCESS);
        assertEquals(csv.editCustomer(new Customer(2L, "Kirill", 18)).getStatus(), SUCCESS);
        assertEquals(csv.editCustomer(new Customer(3L, "Mefodii", 19)).getStatus(), SUCCESS);

        System.out.println(csv.getCustomerById(1L));
        System.out.println(csv.getCustomerById(2L));
        System.out.println(csv.getCustomerById(3L));

    }

}