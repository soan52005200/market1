package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.Constants.XML_ORDER_KEY;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;
import static ru.sfedu.market.utils.Status.SUCCESS;

public class DataProviderXML implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderXML.class);
    /**
     * Регистрация клиента в сервисе
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> register(Customer customer) {
        if (getCustomerById(customer.getId()).isEmpty()) {
            List<Customer> list = getAll(Customer.class, XML_CUSTOMER_KEY);
            list.add(customer);
            return refresh(list, XML_CUSTOMER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, customer, String.format(PRESENT_BEAN, customer.getId()));
    }

    /**
     * Удаление клиента из сервиса
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeCustomerById(Long id) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByCustomerCascade(id);
        customers.removeIf(o -> o.getId().equals(id));
        Result<Customer> result = refresh(customers, XML_CUSTOMER_KEY);
        return new Result<>(result.getStatus(), null, result.getLog());
    }

    /**
     * Изменение информации о клиенте
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> editCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, XML_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Customer> refresh = refresh(customers, XML_CUSTOMER_KEY);
        if (refresh.getStatus() == SUCCESS) {
            return new Result<>(SUCCESS, customer, String.format(UPDATE_SUCCESS, customer.toString()));
        } else {
            return new Result<>(ERROR, customer, refresh.getLog());
        }
    }

    /**
     * Получить информацию о клиенте по его id
     * @param id
     * @return
     */
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return getAll(Customer.class, XML_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Добавить съедобный продукт
     *
     * @param eatable
     * @return
     */
    @Override
    public Result<Eatable> appendEatableProduct(Eatable eatable) {
        if (getEatableProductById(eatable.getId()).isEmpty()) {
            List<Eatable> list = getAll(Eatable.class, XML_EATABLE_KEY);
            list.add(eatable);
            return refresh(list, XML_EATABLE_KEY);
        }
        return new Result<>(UNSUCCESSFUL, eatable, String.format(PRESENT_BEAN, eatable.getId()));
    }

    /**
     * Удаляет съедобный продукт
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeEatableProductById(Long id) {
        List<Eatable> eatables = getAll(Eatable.class, XML_EATABLE_KEY);
        if (eatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByProductCascade(ProductType.EATABLE, id);
        eatables.removeIf(o -> o.getId().equals(id));
        Result<Eatable> result = refresh(eatables, XML_EATABLE_KEY);
        return new Result<Void>(result.getStatus(), null, result.getLog());
    }

    /**
     * Получить информацию о съедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Eatable> getEatableProductById(Long id) {
        return getAll(Eatable.class, XML_EATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Вывести список всех съедобных продуктов, кроме тех у которых истёк срок годности
     *
     * @return
     */
    @Override
    public List<Eatable> getAllEatable() {
        return getAll(Eatable.class, XML_EATABLE_KEY).stream().filter(o -> o.getBestBefore().after(new Date())).collect(Collectors.toList());
    }

    /**
     * Добавить несъедобный продукт
     *
     * @param uneatable
     * @return
     */
    @Override
    public Result<Uneatable> appendUneatableProduct(Uneatable uneatable) {
        if (getUneatableProductById(uneatable.getId()).isEmpty()) {
            List<Uneatable> list = getAll(Uneatable.class, XML_UNEATABLE_KEY);
            list.add(uneatable);
            return refresh(list, XML_UNEATABLE_KEY);
        }
        return new Result<>(UNSUCCESSFUL, uneatable, String.format(PRESENT_BEAN, uneatable.getId()));
    }

    /**
     * Удаляет несъедобный продукт
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeUneatableProductById(Long id) {
        List<Uneatable> uneatables = getAll(Uneatable.class, XML_UNEATABLE_KEY);
        if (uneatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByProductCascade(ProductType.UNEATABLE, id);
        uneatables.removeIf(o -> o.getId().equals(id));
        Result<Uneatable> result = refresh(uneatables, XML_UNEATABLE_KEY);
        return new Result<Void>(result.getStatus(), null, result.getLog());
    }

    /**
     * Получить информацию о несъедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Uneatable> getUneatableProductById(Long id) {
        return getAll(Uneatable.class, XML_UNEATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public Optional<? extends Product> getProductByTypeAndId(ProductType type, Long id) {
        switch (type) {
            case EATABLE:
                return getEatableProductById(id);
            case UNEATABLE:
                return getUneatableProductById(id);
            default:
                return Optional.empty();
        }
    }

    /**
     * Создать заказ
     *
     * @param order
     * @return
     */
    @Override
    public Result<Order> createOrder(Order order) {
        if (getOrderById(order.getId()).isEmpty()) {
            Optional<Customer> customer = getCustomerById(order.getCustomer().getId());
            Optional<? extends Product> product = getProductByTypeAndId(order.getType(), order.getProduct().getId());
            if (customer.isEmpty()) {
                return new Result<Order>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, order.getCustomer().getId()));
            }
            if (product.isEmpty()) {
                return new Result<Order>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, order.getProduct().getId()));
            }
            if (customer.get().getAge() < product.get().getAgeLimit()) {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }
            List<Order> list = getAll(Order.class, XML_ORDER_KEY);
            list.add(order);
            return refresh(list, XML_ORDER_KEY);
        }
        return new Result<>(UNSUCCESSFUL, order, String.format(PRESENT_BEAN, order.getId()));
    }

    /**
     * Закрыть заказ
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> closeOrderById(Long id) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }

        Order order = getOrderById(id).get();
        removeProductByOrderCascade(order.getType(), order.getProduct().getId());
        orders.removeIf(o -> o.getId().equals(id));

        Result<Order> result = refresh(orders, XML_ORDER_KEY);
        if (result.getStatus() == SUCCESS) {
            result = new Result<>(SUCCESS, null, ORDER_CLOSE);
        }
        return new Result<>(result.getStatus(), null, result.getLog());
    }

    /**
     * Получить информацию о заказе
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Order> getOrderById(Long id) {
        return getAll(Order.class, XML_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Вывести все заказы покупателя
     *
     * @param customerId
     * @return
     */
    @Override
    public List<Order> getOrderByCustomerId(Long customerId) {
        return getAll(Order.class, XML_ORDER_KEY).stream().filter(o -> o.getCustomer().getId().equals(customerId)).collect(Collectors.toList());
    }

    public void removeProductByOrderCascade(ProductType type, Long productId) {
        switch (type) {
            case UNEATABLE:
                removeUneatableProductById(productId);
                break;
            case EATABLE:
                removeEatableProductById(productId);
                break;
        };
    }

    public void removeOrderByCustomerCascade(Long customerId) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        orders.removeIf(o -> o.getCustomer().getId().equals(customerId));
        refresh(orders, XML_ORDER_KEY);
    }

    public void removeOrderByProductCascade(ProductType type, Long productId) {
        List<Order> orders = getAll(Order.class, XML_ORDER_KEY);
        orders.removeIf(o -> o.getType().equals(type) && o.getProduct().getId().equals(productId));
        refresh(orders, XML_ORDER_KEY);
    }

    private <T> Result<T> refresh(List<T> container, String key) {
        try {
            FileWriter fileWriter = new FileWriter(getConfigurationEntry(key));
            Serializer serializer = new Persister();
            serializer.write(new Container<T>(container), fileWriter);

            return new Result<T>(SUCCESS, null, PERSISTENCE_SUCCESS);
        } catch (Exception exception) {
            log.error(exception);
            return new Result<T>(ERROR, null, exception.getMessage());
        }
    }


    private <T> List<T> getAll(Class<T> clazz, String key) {
        try {
            FileReader fileReader = new FileReader(getConfigurationEntry(key));
            Serializer serializer = new Persister();

            Container<T> container = serializer.read(Container.class, fileReader);
            fileReader.close();

            return container.getList();
        } catch (Exception exception) {
            log.error(exception);
            return new ArrayList<>();
        }

    }

    @Root(name = "Container")
    private static class Container<T> {
        @ElementList(inline = true, required = false)
        private List<T> list;

        public Container() {
        }

        public Container(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }
    }

}
