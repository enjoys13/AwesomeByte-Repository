package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.Access;
import co.id.jss.jssrestapi.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, QuerydslPredicateExecutor<Menu> {

    @Query("from Menu m where m.type = 'PARENT' or m in (select ma.menu from MenuAccess ma where ma.access in (:accessList)) order by m.parent, m.sorting")
    List<Menu> findByAccess(@Param("accessList") List<Access> accessList);
}
