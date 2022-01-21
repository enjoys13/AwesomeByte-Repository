package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.Access;
import co.id.jss.jssrestapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access, Long>, QuerydslPredicateExecutor<Access> {

    @Query("select a from Access a join a.roleAccessById ra where ra.roleByRoleId in (:roles)")
    List<Access> findAccessByRoles(@Param("roles") List<Role> roles);

    @Query("select a.id from Access a join a.roleAccessById ra where ra.roleByRoleId in (:roles)")
    List<Long> findAccessIdByRoles(@Param("roles") List<Role> roles);

    Optional<Access> findOneByName (String name);
}
