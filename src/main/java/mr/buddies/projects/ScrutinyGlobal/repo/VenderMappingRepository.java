package mr.buddies.projects.ScrutinyGlobal.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mr.buddies.projects.ScrutinyGlobal.model.VenderMappingDetails;

@Repository
public interface VenderMappingRepository extends JpaRepository<VenderMappingDetails,Integer> {

	@Query(value = "SELECT vmd.vender_id,vmd.quota_fullurl,vmd.security_terminateurl,vmd.terminateurl,vmd.successurl,vmd.rate,ru.country,ru.city,ru.email,ru.number "
			             +"FROM vender_mapping_details vmd"
				 		 + " join vender_details vd  on vmd.vender_id=vd.vender_id join register_user ru on vmd.vender_id=ru.user_id where vender_mapping_id=:venderMappingId ", nativeQuery = true)
	 	public List<Map<String,Object>> getProjectVenderList(@Param("venderMappingId") String venderMappingId);
}
