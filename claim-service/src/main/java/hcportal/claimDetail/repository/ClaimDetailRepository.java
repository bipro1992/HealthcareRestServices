package hcportal.claimDetail.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hcportal.claimDetail.entity.ClaimDetail;

@Repository
public interface ClaimDetailRepository extends CrudRepository<ClaimDetail, Integer> {

	public List<ClaimDetail> findByEmployeeId(int employeeId);

}
