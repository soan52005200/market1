package ru.sfedu.market.api;

import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface IDataProvider {

    /**
     * Регистрация клиента в сервисе
     * @param customer
     * @return
     */
    Result<Customer> register(Customer customer);

    /**
     * Удаление клиента из сервиса
     * @param id
     * @return
     */
    Result<Void> removeCustomerById(Long id);

    /**
     * Изменение информации о клиенте
     * @param customer
     * @return
     */
    Result<Customer> editCustomer(Customer customer);

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */
    Optional<Customer> getCustomerById(Long id);

    /**
     * Добавить съедобный продукт
     * @param eatable
     * @return
     */
    Result<Eatable> appendEatableProduct(Eatable eatable);

    /**
     * Удаляет съедобный продукт
     * @param id
     * @return
     */
    Result<Void> removeEatableProductById(Long id);

    /**
     * Получить информацию о съедобном продукте по id
     * @param id
     * @return
     */
    Optional<Eatable> getEatableProductById(Long id);

    /**
     * Вывести список всех съедобных продуктов, кроме тех у которых истёк срок годности
     * @return
     */
    List<Eatable> getAllEatable();

    /**
     * Добавить несъедобный продукт
     * @param uneatable
     * @return
     */
    Result<Uneatable> appendUneatableProduct(Uneatable uneatable);
    /**
     * Удаляет несъедобный продукт
     * @param id
     * @return
     */
    Result<Void> removeUneatableProductById(Long id);
    /**
     * Получить информацию о несъедобном продукте по id
     * @param id
     * @return
     */
    Optional<Uneatable> getUneatableProductById(Long id);

    Optional<? extends Product> getProductByTypeAndId(ProductType type, Long id);

    /**
     * Создать заказ
     * @param order
     * @return
     */
    Result<Order> createOrder(Order order);

    /**
     * Закрыть заказ
     * @param id
     * @return
     */
    Result<Void> closeOrderById(Long id);

    /**
     * Получить информацию о заказе
     * @param id
     * @return
     */
    Optional<Order> getOrderById(Long id);

    /**
     * Вывести все заказы покупателя
     * @param customerId
     * @return
     */
    List<Order> getOrderByCustomerId(Long customerId);

}
