package dao;

import pojo.Booking;

import java.util.Optional;

/**
 * @author Petrov_OlegYu
 */
public interface BookingDao {
	Long addBooking(Booking booking);

	Optional<Booking> getBookingById(Long id);

	boolean updateBookingById(Booking product);

	boolean deleteBookingById(Long id);
}

