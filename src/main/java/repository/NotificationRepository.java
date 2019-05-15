package repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import model.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

	@Nullable
	Notification findById(long id);

}
