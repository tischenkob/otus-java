package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private Deque<Customer> dequeOfCustomers = new ArrayDeque<>();

    public void add(Customer customer) {
        dequeOfCustomers.addLast(customer);
    }

    public Customer take() {
        return dequeOfCustomers.removeLast();
    }
}
