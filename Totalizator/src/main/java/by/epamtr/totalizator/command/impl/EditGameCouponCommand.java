package by.epamtr.totalizator.command.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epamtr.totalizator.bean.dto.GameCouponDTO;
import by.epamtr.totalizator.bean.entity.User;
import by.epamtr.totalizator.command.Command;
import by.epamtr.totalizator.command.exception.CommandException;
import by.epamtr.totalizator.service.AdminOperationService;
import by.epamtr.totalizator.service.ServiceFactory;
import by.epamtr.totalizator.service.exception.ServiceException;

/**
 * Class is designed to edit game coupon's info. Available for administrator
 * only.
 * 
 * @author Andrey Kryshtapovich
 *
 */
public class EditGameCouponCommand implements Command {
	private final static Logger Logger = LogManager.getLogger(EditGameCouponCommand.class.getName());
	private final static String LOCALHOST = "index.jsp";
	private final static String START_DATE = "start-date";
	private final static String START_TIME_HOURS = "start-time-hours";
	private final static String START_TIME_MINUTES = "start-time-minutes";
	private final static String END_DATE = "end-date";
	private final static String END_TIME_HOURS = "end-time-hours";
	private final static String END_TIME_MINUTES = "end-time-minutes";
	private final static String STATUS = "status";
	private final static String GO_TO_ERROR_PAGE = "Controller?command=go-to-error-page";
	private final static String CURRENT_URL = "currentUrl";
	private final static String GAME_EVENTS_URL = "gameEventsUrl";
	private final static String PARAM_RESULT = "param_result";
	private final static String GAME_COUPON_ID = "gameCouponId";
	private final static String MIN_BET_AMOUNT = "minBetAmount";
	private final static String JACKPOT = "jackpot";
	private final static String USER = "user";
	private final static String ADMIN = "admin";

	/**
	 * Accepts all required parameters, creates DTO and calls required service
	 * method.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String url = null;

		if (request.getSession(false) == null) {
			url = LOCALHOST;
			return url;
		}
		User user = (User) request.getSession(false).getAttribute(USER);

		if (user != null && user.getRole().equals(ADMIN)) {

			String gameCouponId = request.getParameter(GAME_COUPON_ID).toString();
			String startDate = request.getParameter(START_DATE);
			String startTimeHours = request.getParameter(START_TIME_HOURS);
			String startTimeMinutes = request.getParameter(START_TIME_MINUTES);

			String endDate = request.getParameter(END_DATE);
			String endTimeHours = request.getParameter(END_TIME_HOURS);
			String endTimeMinutes = request.getParameter(END_TIME_MINUTES);
			String status = request.getParameter(STATUS).toString();
			String minBetAmount = request.getParameter(MIN_BET_AMOUNT).toString();
			String jackpot = request.getParameter(JACKPOT).toString();

			GameCouponDTO gameDTO = new GameCouponDTO();
			gameDTO.setGameCupounId(gameCouponId);

			gameDTO.setStartDate(startDate);
			gameDTO.setStartTimeHours(startTimeHours);
			gameDTO.setStartTimeMinutes(startTimeMinutes);

			gameDTO.setEndDate(endDate);
			gameDTO.setEndTimeHours(endTimeHours);
			gameDTO.setEndTimeMinutes(endTimeMinutes);

			gameDTO.setStatus(status);
			gameDTO.setMinBetAmount(minBetAmount);
			gameDTO.setJackpot(jackpot);

			ServiceFactory factory = ServiceFactory.getInstance();
			AdminOperationService adminService = factory.getAdminOperationService();

			String gameEventsUrl = null;
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(GAME_EVENTS_URL)) {
						gameEventsUrl = cookie.getValue();
					}
				}
			}

			try {
				boolean result = adminService.updateGame(gameDTO);
				if (result) {
					request.getSession(false).setAttribute(PARAM_RESULT, true);
					url = gameEventsUrl;
				} else {
					request.getSession(false).setAttribute(PARAM_RESULT, false);
					url = request.getSession(false).getAttribute(CURRENT_URL).toString();
				}
			} catch (ServiceException e) {
				Logger.error(e);
				url = GO_TO_ERROR_PAGE;
			}
		} else {
			url = LOCALHOST;
		}
		return url;
	}

}
