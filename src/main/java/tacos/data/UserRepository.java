package tacos.data;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import tacos.User;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByUsername(String username);

}