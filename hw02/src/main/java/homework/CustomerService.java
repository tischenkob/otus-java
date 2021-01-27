package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    // TODO: 3. надо реализовать методы этого класса
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы,
    // они тут полезны

    private NavigableMap<Customer, String> mapOfCustomers = new TreeMap<>(comparator);
    private static Comparator<Customer> comparator;
    static {
        comparator = new Comparator<Customer>() {

            @Override
            public int compare(Customer o1, Customer o2) {
                return Long.compare(o1.getScores(), o2.getScores());
            }

        };
    }

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry
        // сделан в jdk
        return mapOfCustomers.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return  mapOfCustomers.higherEntry(customer);
    }

    public void add(Customer customer, String data) {
        mapOfCustomers.put(customer, data);
    }
}
