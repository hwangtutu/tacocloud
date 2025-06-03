package tacos.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import tacos.User;

public interface UserRepository extends MongoRepository<User, String> {    // ← 변경: Long → String

    User findByUsername(String username);

}
