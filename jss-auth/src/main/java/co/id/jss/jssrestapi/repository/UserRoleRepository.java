package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>, QuerydslPredicateExecutor<UserRole> {

}
