package kz.solva.repository;

import kz.solva.model.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

    List<Limit> getLimitsByCustomer_Id(Long id);
}
