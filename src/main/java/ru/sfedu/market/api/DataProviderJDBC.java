package ru.sfedu.market.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.bean.*;
import ru.sfedu.market.utils.Result;
import ru.sfedu.market.utils.Status;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.sfedu.market.Constants.*;
import static ru.sfedu.market.utils.ConfigurationUtil.getConfigurationEntry;
import static ru.sfedu.market.utils.Status.SUCCESS;
import static ru.sfedu.market.utils.Status.UNSUCCESSFUL;

public class DataProviderJDBC implements IDataProvider{
    private static final Logger log = LogManager.getLogger(DataProviderJDBC.class);
    /**
     * Регистрация клиента в сервисе
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> register(Customer customer) {
        return execute(String.format(CUSTOMER_INSERT, customer.getId(), customer.getFio(), customer.getAge()));
    }

    /**
     * Удаление клиента из сервиса
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeCustomerById(Long id) {
        return execute(String.format(CUSTOMER_DELETE, id));
    }

    /**
     * Изменение информации о клиенте
     *
     * @param customer
     * @return
     */
    @Override
    public Result<Customer> editCustomer(Customer customer) {
        return execute(String.format(CUSTOMER_UPDATE, customer.getFio(), customer.getAge(), customer.getId()));
    }

    /**
     * Получить информацию о клиенте по его id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Customer> getCustomerById(Long id) {
        Customer customer = null;
        ResultSet set = select(String.format(CUSTOMER_SELECT, id));
        try {
            if (set != null && set.next()) {
                customer = new Customer(
                        set.getLong(1),
                        set.getString(2),
                        set.getInt(3)
                );
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(customer);
    }

    /**
     * Добавить съедобный продукт
     *
     * @param eatable
     * @return
     */
    @Override
    public Result<Eatable> appendEatableProduct(Eatable eatable) {
        return execute(String.format(EATABLE_INSERT,
                eatable.getId(),
                new java.sql.Date(eatable.getReceiptDate().getTime()),
                eatable.getName(),
                eatable.getManufacturer(),
                eatable.getAgeLimit(),
                eatable.getType().name(),
                new java.sql.Date(eatable.getBestBefore().getTime())));
    }

    /**
     * Удаляет съедобный продукт
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeEatableProductById(Long id) {
        execute(String.format(EATABLE_DELETE_CASCADE, id));
        return execute(String.format(EATABLE_DELETE, id));
    }

    /**
     * Получить информацию о съедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Eatable> getEatableProductById(Long id) {
        Eatable obj = null;
        ResultSet set = select(String.format(EATABLE_SELECT, id));
        try {
            if (set != null && set.next()) {
                obj = new Eatable(
                        set.getLong(1),
                        new Date(set.getDate(2).getTime()),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5),
                        new Date(set.getDate(7).getTime())
                );
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(obj);
    }

    /**
     * Вывести список всех съедобных продуктов, кроме тех у которых истёк срок годности
     *
     * @return
     */
    @Override
    public List<Eatable> getAllEatable() {
        List<Eatable> list = new ArrayList<>();
        ResultSet set = select(EATABLE_SELECT_ALL);
        try {
            while (set != null && set.next()) {
                list.add(new Eatable(
                        set.getLong(1),
                        new Date(set.getDate(2).getTime()),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5),
                        new Date(set.getDate(7).getTime())
                ));
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return list.stream().filter(o -> o.getBestBefore().after(new Date())).collect(Collectors.toList());
    }

    /**
     * Добавить несъедобный продукт
     *
     * @param uneatable
     * @return
     */
    @Override
    public Result<Uneatable> appendUneatableProduct(Uneatable uneatable) {
        return execute(String.format(UNEATABLE_INSERT,
                uneatable.getId(),
                new java.sql.Date(uneatable.getReceiptDate().getTime()),
                uneatable.getName(),
                uneatable.getManufacturer(),
                uneatable.getAgeLimit(),
                uneatable.getType().name()));
    }

    /**
     * Удаляет несъедобный продукт
     *
     * @param id
     * @return
     */
    @Override
    public Result<Void> removeUneatableProductById(Long id) {
        execute(String.format(UNEATABLE_DELETE_CASCADE, id));
        return execute(String.format(UNEATABLE_DELETE, id));
    }

    /**
     * Получить информацию о несъедобном продукте по id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Uneatable> getUneatableProductById(Long id) {
        Uneatable obj = null;
        ResultSet set = select(String.format(UNEATABLE_SELECT, id));
        try {
            if (set != null && set.next()) {
                obj = new Uneatable(
                        set.getLong(1),
                        new Date(set.getDate(2).getTime()),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5)
                );
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(obj);
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
            return execute(String.format(ORDER_INSERT, order.getId(), order.getProduct().getId(), order.getCustomer().getId(), order.getType().name()));
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
        Optional<Order> optional = getOrderById(id);
        if (optional.isEmpty()) {
            return new Result<>(UNSUCCESSFUL, null, String.format(EMPTY_BEAN, id));
        }
        switch (optional.get().getType()) {
            case EATABLE:
                removeEatableProductById(optional.get().getProduct().getId());
                break;
            case UNEATABLE:
                removeUneatableProductById(optional.get().getProduct().getId());
                break;
        }
        return new Result<Void>(SUCCESS, null, REMOVE_SUCCESS);
    }

    /**
     * Получить информацию о заказе
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Order> getOrderById(Long id) {
        Order obj = null;
        ResultSet set = select(String.format(ORDER_SELECT, id));
        try {
            if (set != null && set.next()) {
                obj = new Order();
                obj.setId(set.getLong(1));

                ProductType type = ProductType.valueOf(set.getString(4).toUpperCase());
                Optional<? extends Product> product = getProductByTypeAndId(type, set.getLong(2));
                Optional<Customer> customer = getCustomerById(set.getLong(3));

                if (product.isEmpty()) {
                    throw new NullPointerException(NPE_PRODUCT);
                } else if (customer.isEmpty()) {
                    throw new NullPointerException(NPE_CUSTOMER);
                }

                obj.setProduct(product.get());
                obj.setCustomer(customer.get());
                obj.setType(type);
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return Optional.ofNullable(obj);
    }

    /**
     * Вывести все заказы покупателя
     *
     * @param customerId
     * @return
     */
    @Override
    public List<Order> getOrderByCustomerId(Long customerId) {
        List<Order> list = new ArrayList<>();
        ResultSet set = select(String.format(ORDER_SELECT_CUSTOMER, customerId));
        try {
            while (set != null && set.next()) {
                Order obj = new Order();
                obj.setId(set.getLong(1));

                ProductType type = ProductType.valueOf(set.getString(4).toUpperCase());
                Optional<? extends Product> product = getProductByTypeAndId(type, set.getLong(2));
                Optional<Customer> customer = getCustomerById(set.getLong(3));

                if (product.isEmpty()) {
                    throw new NullPointerException(NPE_PRODUCT);
                } else if (customer.isEmpty()) {
                    throw new NullPointerException(NPE_CUSTOMER);
                }

                obj.setProduct(product.get());
                obj.setCustomer(customer.get());
                obj.setType(type);
                list.add(obj);
            }
        } catch (Exception exception) {
            log.error(exception);
        }
        return list;
    }

    private <T> Result<T> execute(String sql) {
        Statement statement;
        try {
            statement = initConnection().createStatement();
            statement.executeUpdate(sql);
            initConnection().close();
            return new Result<>(Status.SUCCESS, null, null);
        } catch (Exception exception) {
            log.error(exception);
            return new Result<>(Status.ERROR, null, null);
        }
    }

    private ResultSet select(String sql) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = initConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            initConnection().close();
        } catch (Exception exception) {
            log.error(exception);
        }
        return resultSet;
    }

    private Connection initConnection() throws ClassNotFoundException, IOException, SQLException {
        Class.forName(getConfigurationEntry(JDBC_DRIVER));
        Connection connection = DriverManager.getConnection(
                getConfigurationEntry(JDBC_URL),
                getConfigurationEntry(JDBC_USER),
                getConfigurationEntry(JDBC_PASSWORD)
        );
        connection.setAutoCommit(true);
        return connection;
    }


}
