package YOUmI.domain.User.repository;

import YOUmI.domain.User.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    //@Query("SELECT c.username,c.password FROM Customer as c WHERE c.username = :username")
    public Customer findByUsername(String username);

}