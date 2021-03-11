package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.util.Optional;

import static ru.sfedu.market.Constants.NPE_PRODUCT;

public class ProductConverter extends AbstractBeanField<Product, Long> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        String[] str = s.split("-", 2);
        ProductType type = ProductType.valueOf(str[0].toUpperCase());
        Optional<? extends Product> product = csv.getProductByTypeAndId(type, Long.parseLong(str[1]));
        if (product.isEmpty()) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return product.get();
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Product product = (Product) value;
        if (csv.getProductByTypeAndId(product.getType(), product.getId()).isEmpty()) {
            throw new NullPointerException(NPE_PRODUCT);
        }
        return String.format("%s-%d", product.getType().name(), product.getId());
    }

}
