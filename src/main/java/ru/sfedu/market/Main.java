package ru.sfedu.market;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.DataProviderJDBC;
import ru.sfedu.market.api.DataProviderXML;
import ru.sfedu.market.api.IDataProvider;
import ru.sfedu.market.bean.*;

import java.util.Date;
import java.util.Optional;

import static ru.sfedu.market.Constants.*;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);

    /**
     * java -Dlog4j2.configurationFile=<log42_file> -Denv=<env_file> -jar <jar_file> ...
     * @param args
     */
    public static void main(String[] args) {
        try {
            log.debug(System.getProperty(ENV_PROPERTIES));
            log.debug(System.getProperty(LOG4J2_PROPERTIES));

            IDataProvider provider = initDataSource(args[0]);
            log.info(selectBean(provider, args));
        } catch (Exception exception) {
            log.error(exception);
        }
    }

    protected static IDataProvider initDataSource(String str) {
        switch (str.toLowerCase()) {
            case CLI_CSV:
                return new DataProviderCSV();
            case CLI_XML:
                return new DataProviderXML();
            case CLI_JDBC:
                return new DataProviderJDBC();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, str));
        }
    }


    protected static String selectBean(IDataProvider provider, String[] s) {
        switch (s[1].toLowerCase()) {
            case CLI_CUSTOMER:
                return customer(provider, s);
            case CLI_EATABLE:
                return eatable(provider, s);
            case CLI_UNEATABLE:
                return uneatable(provider, s);
            case CLI_ORDER:
                return order(provider, s);
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[1]));
        }
    }

    /**
     *  0     1      2   3   4  5
     * csv customer save 1 Ivan 18
     * csv customer upd 1 Petr 21
     * csv customer get 1
     * csv customer del 1
     * @param provider
     * @param s
     * @return
     */
    protected static String customer(IDataProvider provider, String[] s) {
        switch (s[2].toLowerCase()) {
            case CLI_SAVE:
                return provider.register(new Customer(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).getLog();
            case CLI_UPD:
                return provider.editCustomer(new Customer(Long.parseLong(s[3]), s[4], Integer.parseInt(s[5]))).getLog();
            case CLI_DEL:
                return provider.removeCustomerById(Long.parseLong(s[3])).getLog();
            case CLI_GET:
                return provider.getCustomerById(Long.parseLong(s[3])).toString();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /**
     *  0     1     2   3  4    5  6 7
     * csv eatable save 1 Milk MMM 0 6
     * csv eatable save 2 Milk MMM 0 10
     * csv eatable get 1
     * csv eatable get_all
     * csv eatable del 1
     * csv eatable del 2
     * @param provider
     * @param s
     * @return
     */
    protected static String eatable(IDataProvider provider, String[] s) {
        switch (s[2].toLowerCase()) {
            case CLI_SAVE:
                return provider.appendEatableProduct(new Eatable(
                    Long.parseLong(s[3]), new Date(), s[4], s[5], Integer.parseInt(s[6]),
                    new Date(new Date().getTime() + (1000L * 60 * 60 * 24 * Integer.parseInt(s[7])))
            )).getLog();
            case CLI_GET:
                return provider.getEatableProductById(Long.parseLong(s[3])).toString();
            case CLI_GET_ALL:
                return provider.getAllEatable().toString();
            case CLI_DEL:
                return provider.removeEatableProductById(Long.parseLong(s[3])).getLog();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /**
     *  0     1       2   3  4    5  6
     * csv uneatable save 1 House MMM 0
     * csv uneatable get 1
     * csv uneatable del 1
     * @param provider
     * @param s
     * @return
     */
    protected static String uneatable(IDataProvider provider, String[] s) {
        switch (s[2].toLowerCase()) {
            case CLI_SAVE:
                return provider.appendUneatableProduct(new Uneatable(
                    Long.parseLong(s[3]), new Date(), s[4], s[5], Integer.parseInt(s[6]))).getLog();
            case CLI_GET:
                return provider.getUneatableProductById(Long.parseLong(s[3])).toString();
            case CLI_DEL:
                return provider.removeUneatableProductById(Long.parseLong(s[3])).getLog();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

    /** 0    1     2    3 4 5    6
     * csv customer save 1 Ivan 18
     * csv uneatable save 1 House MMM 21
     * csv order create 1 1 1 uneatable
     * csv order get 1
     * csv order close 1
     * @param provider
     * @param s
     * @return
     */
    protected static String order(IDataProvider provider, String[] s) {
        switch (s[2].toLowerCase()) {
            case CLI_CREATE:
                ProductType productType = ProductType.valueOf(s[6].toUpperCase());
                Optional<Customer> customer = provider.getCustomerById(Long.parseLong(s[5]));
                Optional<? extends Product> product = provider.getProductByTypeAndId(productType, Long.parseLong(s[4]));

                if (customer.isEmpty() || product.isEmpty()) return EMPTY_BEAN;

                return provider.createOrder(new Order(Long.parseLong(s[3]), product.get(), customer.get())).getLog();
            case CLI_CLOSE:
                return provider.closeOrderById(Long.parseLong(s[3])).getLog();
            case CLI_GET:
                return provider.getOrderById(Long.parseLong(s[3])).toString();
            default:
                throw new IllegalArgumentException(String.format(CLI_EXCEPTION, s[2]));
        }
    }

}
