package com.ezshipp.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ezshipp.api.model.PagingResponse;
import com.ezshipp.api.persistence.entity.OrderEventEntity;

public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Integer>{

	List<OrderEventEntity> findByDriverIdAndStatusId(Integer driverId, int i);

	List<OrderEventEntity> findOrderEventEntityByDriverIdAndOrderId(Integer driverId, Integer orderId);

	List<OrderEventEntity> findOrderEventEntityByDriverIdAndOrderIdAndStatusId(Integer userId, Integer orderId,
			Integer statusId);

	Page<OrderEventEntity> findByDriverIdAndStatusIdAndLastUpdatedTimeBetween(Integer driverId, Integer statusId,
			Pageable createPageRequest, Date startDate, Date endDate);

	Page<OrderEventEntity> findOrderEventEntityByDriverIdAndStatusId(Integer driverId, Integer statusId,
			Pageable createPageRequest);

	List<OrderEventEntity> findByDriverIdAndStatusIdNotIn(Integer driverId, List<Integer> statusList);

	Page<OrderEventEntity> findByDriverIdAndStatusIdAndLastUpdatedTimeBetweenOrderByLastUpdatedTimeDesc(
			Integer driverId, Integer statusId, Pageable createPageRequest, Date startDate, Date endDate);

	Page<OrderEventEntity> findOrderEventEntityByDriverIdAndStatusIdOrderByLastUpdatedTimeDesc(Integer driverId,
			Integer statusId, Pageable createPageRequest);

	Page<OrderEventEntity> findOrdersByDriverIdAndStatusIdIn(Integer driverId, List<Integer> statuses,
			Pageable createPageRequest);

	
	
//	@Query("SELECT oe.id, oe.last_updated_time, oe.order_id, oe.status_id, oe.driver_id,oe.created_by, oe.created_time, oe.modified_by, oe.modified_time, oe.latitude, oe.longitude FROM   order_event oe, order ord  WHERE  oe.order_id = ord.id AND oe.status_id = 2 AND ord.status_id = 2 AND oe.driver_id = ?1 ")
//	List<OrderEventEntity> getAllOrdersByDriverIdIsAssignedTrue(Integer driverId);
//	
//	@Query("SELECT oe.id, oe.last_updated_time, oe.order_id, oe.status_id, oe.driver_id, oe.created_by, oe.created_time, oe.modified_by, oe.modified_time, oe.latitude, oe.longitude FROM   order_event oe, order ord WHERE  oe.order_id = ord.id AND oe.status_id NOT IN (1,2,8,12,13,14) AND oe.status_id = ord.status_id AND oe.driver_id = ?1")
//	List<OrderEventEntity> getAllOrdersByDriverIdIsAssignedFalse(Integer driverId);

}
