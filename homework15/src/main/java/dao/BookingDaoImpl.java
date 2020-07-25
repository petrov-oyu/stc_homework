package dao;

import pojo.Booking;

import java.util.Optional;

public class BookingDaoImpl implements BookingDao {

	@Override
	public Long addBooking(Booking booking) {
		return null;
	}

	@Override
	public Optional<Booking> getBookingById(Long id) {
		return Optional.empty();
	}

	@Override
	public boolean updateBookingById(Booking product) {
		return false;
	}

	@Override
	public boolean deleteBookingById(Long id) {
		return false;
	}
}
