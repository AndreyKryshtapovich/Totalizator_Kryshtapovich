 package by.epamtr.totalizator.service.impl;

import by.epamtr.totalizator.bean.dto.EventDTO;
import by.epamtr.totalizator.bean.dto.GameCupounDTO;
import by.epamtr.totalizator.bean.dto.EventsListDTO;
import by.epamtr.totalizator.bean.entity.Event;
import by.epamtr.totalizator.bean.entity.GameCupoun;
import by.epamtr.totalizator.dao.AdminDAO;
import by.epamtr.totalizator.dao.DAOFactory;
import by.epamtr.totalizator.dao.exception.DAOException;
import by.epamtr.totalizator.dao.impl.DBAdminDAO;
import by.epamtr.totalizator.service.AdminOperationService;
import by.epamtr.totalizator.service.exception.ServiceException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AdminOperation implements AdminOperationService {

	@Override
	public boolean createNewGameCupoun(GameCupounDTO gameCupounDTO) throws ServiceException {
		
		boolean result = true;
		
		if (!Validator.newGameCupounInfoValidation(gameCupounDTO)) {
			result = false;
			return result;
		}
		
		Integer intMinBetAmount = Integer.parseInt(gameCupounDTO.getMinBetAmount());
	
		String correctStartDate = gameCupounDTO.getStartDate() + " " + gameCupounDTO.getStartTimeHours() + ":" + gameCupounDTO.getStartTimeMinutes() + ":" + "00";
		String correctEndDate = gameCupounDTO.getEndDate() + " " + gameCupounDTO.getEndTimeHours() + ":" + gameCupounDTO.getEndTimeMinutes() + ":" + "00";
		Timestamp gameCuponStartDate = Timestamp.valueOf(correctStartDate);
		Timestamp gameCuponEndDate =  Timestamp.valueOf(correctEndDate);
		
		GameCupoun gameCupon = new GameCupoun();
		
		gameCupon.setStartDate(gameCuponStartDate);
		gameCupon.setEndDate(gameCuponEndDate);
		gameCupon.setMinBetAmount(intMinBetAmount);
		
		DAOFactory factory = DAOFactory.getInstance();
		DBAdminDAO adminDAO = factory.getDBAdminDAO();
		
		try {
			if(!adminDAO.createNewGameCupoun(gameCupon)){
				result = false;
			}	
			
		} catch (DAOException e) {
			throw new ServiceException("Failed creating new game cupoun.", e);
		}
		return result;
	}

	@Override
	public boolean createNewEvent(EventDTO eventDTO) throws ServiceException {
		boolean result = true;
		
		if (!Validator.newEventInfoValidation(eventDTO)) {
			//throw new ServiceException("invalid parameters.");
			result = false;
			return result;
		}
		
		String correctStartDate = eventDTO.getStartDate() + " " + eventDTO.getStartTimeHours() + ":" + eventDTO.getStartTimeMinutes() + ":" + "00";
		String correctEndDate = eventDTO.getEndDate() + " " + eventDTO.getEndTimeHours() + ":" + eventDTO.getEndTimeMinutes() + ":" + "00";
		Timestamp eventStartDate = Timestamp.valueOf(correctStartDate);
		Timestamp eventEndDate =  Timestamp.valueOf(correctEndDate);
		
		Event event = new Event();
		
		event.setEventName(eventDTO.getEventName());
		event.setStartDate(eventStartDate);
		event.setEndDate(eventEndDate);
		event.setTeamOne(eventDTO.getTeamOne());
		event.setTeamTwo(eventDTO.getTeamTwo());
		
		DAOFactory factory = DAOFactory.getInstance();
		DBAdminDAO adminDAO = factory.getDBAdminDAO();
		
		try {
			if(!adminDAO.createNewEvent(event)){
				result = false;
			}	
			
		} catch (DAOException e) {
			throw new ServiceException("Failed creating new event.", e);
		}
		return result;
	}

	@Override
	public List<GameCupoun> getGamesInDevelopment() throws ServiceException {
		List<GameCupoun> gamesList = null;
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		
		try {
			gamesList = adminDAO.getGamesInDevelopment();
		} catch (DAOException e) {
			throw new ServiceException("Failed showing games.", e);
		}
		return gamesList;
	}

	@Override
	public EventsListDTO getUnmatchedEvents(String parameters) throws ServiceException {
		List<Event> eventsList = null;
		EventsListDTO unmatchedEventsDTO = new EventsListDTO();
		
		if (!Validator.dropDownValidation(parameters)) {
			throw new ServiceException("invalid parameters.");
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		
		int gameCupounId =Integer.valueOf(parameters.substring(0, 1));
		String startDate = parameters.substring(3,25);
		String endDate = parameters.substring(27);
		Timestamp gameStartDate = Timestamp.valueOf(startDate);
		Timestamp gameEndDate = Timestamp.valueOf(endDate);
		
		try {
			eventsList = adminDAO.getUnmatchedEvents(gameStartDate, gameEndDate);
			unmatchedEventsDTO.setEventList(eventsList);
			unmatchedEventsDTO.setGameCupounId(gameCupounId);
			unmatchedEventsDTO.setGameStartDate(gameStartDate);
			unmatchedEventsDTO.setGameEndDate(gameEndDate);
		} catch (DAOException e) {
			throw new ServiceException("Failed showing events.", e);
		}
		return unmatchedEventsDTO;
	}

	@Override
	public boolean matchEventAndGame(int selectedGameCupounId, int selectedEventId) throws ServiceException {
		boolean result = true;
		//TODO 
		// validation is not required all parameters are preselected by another commands
		
		DAOFactory factory = DAOFactory.getInstance();
		DBAdminDAO adminDAO = factory.getDBAdminDAO();
		try {
			if(!adminDAO.matchEventAndGame(selectedGameCupounId, selectedEventId)){
				result = false;
			}	
		} catch (DAOException e) {
			throw new ServiceException("Failed matching event and game.", e);
		}
		return result;
	}

	@Override
	public List<Event> getEventsByGameCupounId(int gameCupounId) throws ServiceException {
		List<Event> eventsList = null;
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		try {
			eventsList = adminDAO.getEventsByGameCupounId(gameCupounId);
		} catch (DAOException e) {
			throw new ServiceException("Failed showing events.", e);
		}
		return eventsList;
	}

	@Override
	public List<GameCupoun> getAllGames() throws ServiceException {
		List<GameCupoun> gamesList = null;
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		try {
			gamesList = adminDAO.getAllGames();
		} catch (DAOException e) {
			throw new ServiceException("Failed showing events.", e);
		}
		return gamesList;
	}

	@Override
	public boolean updateEvent(Event event) throws ServiceException {
		boolean result = true;
		//TODO VALIDATION
		if (!Validator.updateEventValidation(event)) {
			throw new ServiceException("invalid dates.");
		}
		
		DAOFactory factory = DAOFactory.getInstance();
		DBAdminDAO adminDAO = factory.getDBAdminDAO();
		
		try {
			if(!adminDAO.updateEvent(event)){
				result = false;
			}	
			
		} catch (DAOException e) {
			throw new ServiceException("Failed updating an event.", e);
		}
		return result;
	}

	@Override
	public Map<Integer, String> getResultDictionaryData() throws ServiceException {
		Map<Integer,String> resultMap = null;
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		try {
			resultMap = adminDAO.getResultDictionaryData();
		} catch (DAOException e) {
			throw new ServiceException("Failed showing results data.", e);
		}
		return resultMap;
	}

	@Override
	public Map<Integer, String> getStatusDictionaryData() throws ServiceException {
		Map<Integer,String> statusMap = null;
		DAOFactory factory = DAOFactory.getInstance();
		AdminDAO adminDAO = factory.getDBAdminDAO();
		try {
			statusMap = adminDAO.getStatusDictionaryData();
		} catch (DAOException e) {
			throw new ServiceException("Failed showing status data.", e);
		}
		return statusMap;
	}

}
