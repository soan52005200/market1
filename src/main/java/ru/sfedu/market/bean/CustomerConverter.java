package ru.sfedu.market.bean;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import ru.sfedu.market.api.DataProviderCSV;
import ru.sfedu.market.api.IDataProvider;

import java.util.Optional;

import static ru.sfedu.market.Constants.NPE_CUSTOMER;

public class CustomerConverter extends AbstractBeanField<Customer, Long> {

    private final IDataProvider csv = new DataProviderCSV();

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        Optional<Customer> optional = csv.getCustomerById(Long.parseLong(s));
        if (optional.isEmpty()) {
            throw new NullPointerException(NPE_CUSTOMER);
        }
        return optional.get();
    }

    @Override
    protected String convertToWrite(Object value) throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Optional<Customer> optional = csv.getCustomerById(((Customer) value).getId());
        if (optional.isEmpty()) {
            throw new NullPointerException(NPE_CUSTOMER);
        }
        return String.valueOf(optional.get().getId());
    }
}
