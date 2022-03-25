package co.id.jss.jssrestapi.repository;

import co.id.jss.jssrestapi.domain.MenuAccess;
import co.id.jss.jssrestapi.domain.MenuAccessPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuAccessRepository extends JpaRepository<MenuAccess, MenuAccessPK>, QuerydslPredicateExecutor<MenuAccess> {

}
