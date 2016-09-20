package sample.jpa.infrastructure.customer;

import sample.jpa.domain.customer.Customer;
import sample.jpa.domain.customer.CustomerCode;
import sample.jpa.domain.customer.CustomerRepository;

import javax.persistence.EntityManager;

public class JpaCustomerRepository implements CustomerRepository {

    private EntityManager em;

    public JpaCustomerRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Customer find(CustomerCode customerCode) {
        return this.em.find(Customer.class, customerCode);
    }
}
