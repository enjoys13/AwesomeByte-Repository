package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>, QuerydslPredicateExecutor<Client> {

    Optional<Client> findOneByIdAndIsActive(String id, boolean isActive);
}
