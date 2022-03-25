package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Optional<User> findOneByUserlogin(String userlogin);

    Optional<User> findOneByPhoneNumber(String phoneNumber);

    List<User> findByUserloginOrPhoneNumber(String userlogin, String phoneNumber);

    Optional<User> findOneByUserloginAndPhoneNumber(String userlogin, String phoneNumber);
}
