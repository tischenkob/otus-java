package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private NavigableMap<Customer, String> mapOfCustomers = new TreeMap<>(
            Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return mapOfCustomers.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return mapOfCustomers.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        mapOfCustomers.put(customer.clone(), data);
    }
}
