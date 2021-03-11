package ru.sfedu.market;

public final class Constants {

    public static final String ENV_PROPERTIES = "env";
    public static final String LOG4J2_PROPERTIES = "log4j2.configurationFile";
    public static final String ENV_DEFAULT = "./src/main/resources/enviroment.properties";

    public static final String CLI_CSV = "csv";
    public static final String CLI_XML = "xml";
    public static final String CLI_JDBC = "jdbc";
    public static final String CLI_EXCEPTION = "Ключа %s не существует";
    public static final String CLI_SAVE = "save";
    public static final String CLI_CREATE = "create";
    public static final String CLI_CLOSE = "close";
    public static final String CLI_DEL = "del";
    public static final String CLI_UPD = "upd";
    public static final String CLI_GET = "get";
    public static final String CLI_GET_ALL = "get_all";
    public static final String CLI_CUSTOMER = "customer";
    public static final String CLI_EATABLE = "eatable";
    public static final String CLI_UNEATABLE = "uneatable";
    public static final String CLI_ORDER = "order";

    public static final String NPE_PRODUCT = "Такого продукта не существует";
    public static final String NPE_CUSTOMER = "Такого клиента не существует";
    public static final String EXCEPTION_AGE_LIMIT = "Несоблюдено возрастное ограничение";
    public static final String PERSISTENCE_SUCCESS = "Объект %s успешно сохранен";
    public static final String UPDATE_SUCCESS = "Объект %s успешно обновлен";
    public static final String PRESENT_BEAN = "Клиент с ID=%d уже существует";
    public static final String EMPTY_BEAN = "Объект с ID=%d не существует";
    public static final String REMOVE_SUCCESS = "Объект успешно удалён";
    public static final String ORDER_CLOSE = "Заказ успешно закрыт";

    public static final String CSV_CUSTOMER_KEY = "csvCustomer";
    public static final String CSV_EATABLE_KEY = "csvEatable";
    public static final String CSV_UNEATABLE_KEY = "csvUneatable";
    public static final String CSV_ORDER_KEY = "csvOrder";

    public static final String XML_CUSTOMER_KEY = "xmlCustomer";
    public static final String XML_EATABLE_KEY = "xmlEatable";
    public static final String XML_UNEATABLE_KEY = "xmlUneatable";
    public static final String XML_ORDER_KEY = "xmlOrder";

    public static final String JDBC_DRIVER = "jdbcDriver";
    public static final String JDBC_URL = "jdbcUrl";
    public static final String JDBC_USER = "jdbcUser";
    public static final String JDBC_PASSWORD = "jdbcPassword";

    public static final String CUSTOMER_INSERT = "INSERT INTO customer VALUES(%d, '%s', %d);";
    public static final String CUSTOMER_SELECT = "SELECT id, fio, age FROM customer WHERE id = %d";
    public static final String CUSTOMER_UPDATE = "UPDATE customer SET fio='%s', age=%d WHERE id = %d";
    public static final String CUSTOMER_DELETE = "DELETE FROM customer WHERE id = %d";

    public static final String EATABLE_INSERT = "INSERT INTO eatable VALUES (%d, '%s', '%s', '%s', %d, '%s', '%s');";
    public static final String EATABLE_SELECT = "SELECT id, receiptDate, name, manufacturer, ageLimit, type, bestBefore FROM eatable WHERE id = %d;";
    public static final String EATABLE_SELECT_ALL = "SELECT id, receiptDate, name, manufacturer, ageLimit, type, bestBefore FROM eatable;";
    public static final String EATABLE_DELETE = "DELETE FROM eatable WHERE id = %d;";
    public static final String EATABLE_DELETE_CASCADE = "DELETE FROM \"order\" WHERE product_id = %d and type_='EATABLE';";

    public static final String UNEATABLE_INSERT = "INSERT INTO uneatable VALUES (%d, '%s', '%s', '%s', %d, '%s');";
    public static final String UNEATABLE_SELECT = "SELECT id, receiptDate, name, manufacturer, ageLimit, type FROM uneatable WHERE id = %d;";
    public static final String UNEATABLE_DELETE = "DELETE FROM uneatable WHERE id = %d;";
    public static final String UNEATABLE_DELETE_CASCADE = "DELETE FROM \"order\" WHERE product_id = %d and type_='UNEATABLE';";

    public static final String ORDER_INSERT = "INSERT INTO \"order\" VALUES (%d, %d, %d, '%s');";
    public static final String ORDER_SELECT = "SELECT id, product_id, customer_id, type_ FROM \"order\" WHERE id = %d;";
    public static final String ORDER_SELECT_CUSTOMER = "SELECT id, product_id, customer_id, type_ FROM \"order\" WHERE customer_id = %d;";

}
