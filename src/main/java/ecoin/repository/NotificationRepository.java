package ecoin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import ecoin.model.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

	@Nullable
	Notification findById(long id);

	List<Notification> findAllBySentTime(String sentTime);

}
