package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, QuerydslPredicateExecutor<Role> {

    @Query("select r from Role r join r.userRoleById ur where ur.userByUserId.id = :userId")
    List<Role> findRolesByUser(@Param("userId") Long userId);

    Optional<Role> findOneByName (String name);
}
