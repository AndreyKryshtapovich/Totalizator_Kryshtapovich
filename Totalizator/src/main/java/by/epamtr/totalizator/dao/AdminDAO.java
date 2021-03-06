package by.epamtr.totalizator.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import by.epamtr.totalizator.bean.entity.Event;
import by.epamtr.totalizator.bean.entity.GameCoupon;
import by.epamtr.totalizator.dao.exception.DAOException;

/**
 * Interface contains methods which are required to provide administrator's work
 * with the data storage.
 * 
 * @author Andrey Kryshtapovich
 *
 */
public interface AdminDAO {
	/**
	 * Creates new record representing particular game coupon in a data storage.
	 * 
	 * @param gameCupoun
	 *            {@link GameCoupon} object representing new game coupon.
	 * @return {@code true} if new game coupon was created in a data storage.
	 * @throws DAOException
	 *             if creating of new game coupon fails.
	 */
	boolean createNewGameCupoun(GameCoupon gameCupoun) throws DAOException;

	/**
	 * Creates new record representing particular event in a data storage.
	 * 
	 * @param event
	 *            {@link Event} object representing new event.
	 * @return {@code true} if new event was created in a data storage.
	 * @throws DAOException
	 *             if creating of new event fails.
	 */
	boolean createNewEvent(Event event) throws DAOException;

	/**
	 * Matches event to a particular game coupon.
	 * 
	 * Event will be associated with particular game coupon.
	 * 
	 * @param selectedGameCupounId
	 *            Id of the particular game coupon in the data storage.
	 * @param selectedEventId
	 *            Id of the event in the data storage to be matched to the game
	 *            coupon.
	 * @return {@code true} if event was successfully matched to the game
	 *         coupon.
	 * @throws DAOException
	 *             if matching fails due to the data storage issues.
	 */
	boolean matchEventAndGame(int selectedGameCupounId, int selectedEventId) throws DAOException;

	/**
	 * Updates record representing particular event in the data storage.
	 * 
	 * @param event
	 *            {@link Event} object representing particular event with up to
	 *            date information.
	 * @return {@code true} if event's record was successfully updated.
	 * @throws DAOException
	 *             if update fails due to the data storage issues.
	 */
	boolean updateEvent(Event event) throws DAOException;

	/**
	 * Updates record representing particular game coupon in the data storage.
	 * 
	 * @param game
	 *            {@link GameCoupon} object representing particular game coupon
	 *            with up to date information.
	 * @return {@code true} if game coupon's record was successfully updated.
	 * @throws DAOException
	 *             if update fails due to the data storage issues.
	 */
	boolean updateGame(GameCoupon game) throws DAOException;

	/**
	 * Unmatches event and the particular game coupon.
	 * 
	 * @param selectedEventId
	 *            Id of the event in the data storage to be unmatched with the
	 *            game coupon.
	 * @return {@code true} if event was successfully unmatched.
	 * @throws DAOException
	 *             if unmatching fails due to the data storage issues.
	 */

	boolean unmatchEventAndGame(int selectedEventId) throws DAOException;

	/**
	 * Deletes a record representing particular event from the data storage.
	 * 
	 * @param selectedEventId
	 *            Id of the event to be deleted from the data storage.
	 * @return {@code true} if event was deleted successfully.
	 * @throws DAOException
	 *             if deleting fails due to the data storage issues.
	 */
	boolean deleteEvent(int selectedEventId) throws DAOException;

	/**
	 * Calculates the prize for bets that won in the particular drawing. Changes
	 * status of the game coupon representing particular drawing to closed or
	 * cancelled. Updates the data storage with new information.
	 * 
	 * @param gameCouponId
	 *            Id of the particular game coupon in the data storage.
	 * @return {@code int} value representing the result : 1 - game coupon
	 *         closed successfully. -3 - rollback occurred. -2 - game coupon was
	 *         cancelled. -1 - error there are less than 15 events in the game
	 *         coupon.
	 * @throws DAOException
	 *             if closing game coupon fails due to the data storage issues.
	 */
	int closeGameCoupon(int gameCouponId) throws DAOException;

	/**
	 * Gets all game coupons with In developing status from the data storage.
	 * 
	 * @return {@link List} of {@link GameCoupon} objects.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	List<GameCoupon> getGamesInDevelopment() throws DAOException;

	/**
	 * Gets all game coupons from the data storage except Closed and Cancelled
	 * ones.
	 * 
	 * @return {@link List} of {@link GameCoupon} objects.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	List<GameCoupon> getAllGames() throws DAOException;

	/**
	 * Gets all events from the data storage that could be matched to the
	 * particular game coupon.
	 * 
	 * @param gameStartDate
	 *            {@link Timestamp} object representing particular game coupon's
	 *            start date
	 * @param gameEndDate
	 *            {@link Timestamp} object representing particular game coupon's
	 *            end date
	 * @return {@link List} of {@link Event} objects.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	List<Event> getUnmatchedEvents(Timestamp gameStartDate, Timestamp gameEndDate) throws DAOException;

	/**
	 * Gets all events matching particular game coupon.
	 * 
	 * @param gameCupounId
	 *            Id of the particular game coupon in the data storage.
	 * @return {@link List} of {@link Event} objects.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	List<Event> getEventsByGameCupounId(int gameCupounId) throws DAOException;

	/**
	 * Gets a particular game coupon from a data storage.
	 * 
	 * @param gameCupounId
	 *            Id of the game coupon in the data storage.
	 * @return {@link List} containing {@link GameCoupon} object.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	List<GameCoupon> getGameByGameCupounId(int gameCupounId) throws DAOException;

	/**
	 * Gets all data about available results from the data storage.
	 * 
	 * @return {@link Map} {@code key} - number of result. {@code value} -
	 *         result's description.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	Map<Integer, String> getResultDictionaryData() throws DAOException;

	/**
	 * Gets all data about available statuses from the data storage.
	 * 
	 * @return {@link Map} {@code key} - number of status. {@code value} -
	 *         status's description.
	 * @throws DAOException
	 *             if obtaining information from the data storage fails due to
	 *             the data storage issues.
	 */
	Map<Integer, String> getStatusDictionaryData() throws DAOException;
}
