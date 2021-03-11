package ru.sfedu.market.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.*;

public class DataProviderCSV implements IDataProvider {
    private static final Logger log = LogManager.getLogger(DataProviderCSV.class);
    /**
     * Регистрация клиента в сервисе
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> register(Customer customer) {
        if (getCustomerById(customer.getId()).isEmpty()) {
            return append(customer, CSV_CUSTOMER_KEY);
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
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByCustomerCascade(id);
        customers.removeIf(o -> o.getId().equals(id));
        return refresh(customers, CSV_CUSTOMER_KEY);
    }

    /**
     * Изменение информации о клиенте
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> editCustomer(Customer customer) {
        List<Customer> customers = getAll(Customer.class, CSV_CUSTOMER_KEY);
        if (customers.stream().noneMatch(o -> o.getId().equals(customer.getId()))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, customer.getId()));
        }
        customers.removeIf(o -> o.getId().equals(customer.getId()));
        customers.add(customer);
        Result<Void> refresh = refresh(customers, CSV_CUSTOMER_KEY);
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
        return getAll(Customer.class, CSV_CUSTOMER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
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
            return append(eatable, CSV_EATABLE_KEY);
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
        List<Eatable> eatables = getAll(Eatable.class, CSV_EATABLE_KEY);
        if (eatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByProductCascade(ProductType.EATABLE, id);
        eatables.removeIf(o -> o.getId().equals(id));
        return refresh(eatables, CSV_EATABLE_KEY);
    }

    /**
     * Получить информацию о съедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Eatable> getEatableProductById(Long id) {
        return getAll(Eatable.class, CSV_EATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Вывести список всех съедобных продуктов, кроме тех у которых истёк срок годности
     *
     * @return
     */
    @Override
    public List<Eatable> getAllEatable() {
        return getAll(Eatable.class, CSV_EATABLE_KEY).stream().filter(o -> o.getBestBefore().after(new Date())).collect(Collectors.toList());
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
            return append(uneatable, CSV_UNEATABLE_KEY);
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
        List<Uneatable> uneatables = getAll(Uneatable.class, CSV_UNEATABLE_KEY);
        if (uneatables.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        removeOrderByProductCascade(ProductType.UNEATABLE, id);
        uneatables.removeIf(o -> o.getId().equals(id));
        return refresh(uneatables, CSV_UNEATABLE_KEY);
    }

    /**
     * Получить информацию о несъедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Uneatable> getUneatableProductById(Long id) {
        return getAll(Uneatable.class, CSV_UNEATABLE_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
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
            if (order.getCustomer().getAge() > order.getProduct().getAgeLimit()) {
                return append(order, CSV_ORDER_KEY);
            } else {
                return new Result<>(UNSUCCESSFUL, null, EXCEPTION_AGE_LIMIT);
            }
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
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        if (orders.stream().noneMatch(o -> o.getId().equals(id))) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }

        Order order = getOrderById(id).get();
        removeProductByOrderCascade(order.getType(), order.getProduct().getId());
        orders.removeIf(o -> o.getId().equals(id));

        Result<Void> result = refresh(orders, CSV_ORDER_KEY);
        if (result.getStatus() == SUCCESS) {
            result = new Result<>(SUCCESS, null, ORDER_CLOSE);
        }
        return result;
    }

    /**
     * Получить информацию о заказе
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Order> getOrderById(Long id) {
        return getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * Вывести все заказы покупателя
     *
     * @param customerId
     * @return
     */
    @Override
    public List<Order> getOrderByCustomerId(Long customerId) {
        return getAll(Order.class, CSV_ORDER_KEY).stream().filter(o -> o.getCustomer().getId().equals(customerId)).collect(Collectors.toList());
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
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        orders.removeIf(o -> o.getCustomer().getId().equals(customerId));
        refresh(orders, CSV_ORDER_KEY);
    }

    public void removeOrderByProductCascade(ProductType type, Long productId) {
        List<Order> orders = getAll(Order.class, CSV_ORDER_KEY);
        orders.removeIf(o -> o.getType().equals(type) && o.getProduct().getId().equals(productId));
        refresh(orders, CSV_ORDER_KEY);
    }

    private <T> List<T> getAll(Class<T> clazz, String key) {
        List<T> result;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(getConfigurationEntry(key)));

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(clazz).build();
            List<T> collection = csvToBean.parse();
            csvReader.close();

            result = collection;
        } catch (Exception e) {
            log.error(e);
            result = new ArrayList<T>();
        }
        return result;
    }

    private <T> Result<T> append(T bean, String key) {
        Result<T> result;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), true));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(bean);
            csvWriter.close();

            result = new Result<T>(SUCCESS, bean, String.format(PERSISTENCE_SUCCESS, bean.toString()));
        } catch (Exception e) {
            log.error(e);
            result = new Result<T>(ERROR, null, e.getMessage());
        }
        return result;
    }

    private <T> Result<Void> refresh(List<T> beans, String key) {
        Result<Void> result;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(getConfigurationEntry(key), false));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(beans);
            csvWriter.close();

            result = new Result<Void>(SUCCESS, null, REMOVE_SUCCESS);
        } catch (Exception e) {
            log.error(e);
            result = new Result<Void>(ERROR, null, e.getMessage());
        }
        return result;
    }

}
