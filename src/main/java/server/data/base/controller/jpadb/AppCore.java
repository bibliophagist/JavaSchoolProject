package server.data.base.controller.jpadb;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import server.data.base.controller.model.User;
import server.data.base.controller.model.Account;
import server.connection.service.Request;
import server.connection.service.Response;
import server.connection.service.RequestType;
import server.data.base.controller.NoSuchRequestException;;

public class AppCore {

	private static final EntityManagerFactory emFactoryObj;
	private static final String PERSISTENCE_UNIT_NAME = "OnlineBank";
	private static final long TIMEOUT = 5000;
	private static boolean whiteFlag = false;
	
	public static void setWhiteFlag(boolean whiteFlag) {
		AppCore.whiteFlag = whiteFlag;
	}
	
	public static boolean getWhiteFlag() {
		return whiteFlag;
	}
	
	public static EntityManagerFactory getEMfactory() {
		return emFactoryObj;
	}

	static {
		emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
	//TODO check if RequestList handler works correctly: rollback in particular.
	public static Response handleRequestList(List<Request> reqList) {
		Response response = null;
		if(reqList.size() == 0 || reqList == null) {
			response = new Response();
		}
		EntityManager em = emFactoryObj.createEntityManager();
		em.getTransaction().begin();
		try {
			boolean check = true;
			for(Request req: reqList) {
				response = handle(em, req);
				if(!response.isRequestSuccessful()) {
					check = false;
					break;
				}
			}
			if(check) {
				em.getTransaction().commit();
				em.clear();
				em.close();
			} else {
				em.getTransaction().rollback();
				em.close();
			}
		} catch (SQLException e) {
			// TODO log
			System.out.println("Database failure");
			em.getTransaction().rollback();
			e.printStackTrace();
		} catch (NoSuchRequestException e) {
			// TODO log
			System.out.println("Wrong request type");
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		return response;
	}
	
	public static Response handleRequest(Request req) {
		if (req == null) {
			throw new NullPointerException("Not a request");
		}

		Response response = null;
		
		EntityManager em = emFactoryObj.createEntityManager();
		em.getTransaction().begin();
		try {
			response = handle(em, req);
		} catch (SQLException e) {
			// TODO log
			System.out.println("Database failure");
			e.printStackTrace();
		} catch (NoSuchRequestException e) {
			// TODO log
			System.out.println("Wrong request type");
			e.printStackTrace();
		}

		em.getTransaction().commit();
		em.clear();
		emFactoryObj.getCache().evict(User.class);
		emFactoryObj.getCache().evict(Account.class);
		if(response.isRequestSuccessful() && response.getUserID() != null) {
			em.getTransaction().begin();
			List<Account> accList = getUserByID(em, response.getUserID()).getAccountList();
			String accListString = accListToString(accList);
			response.setAccListString(accListString);
			em.getTransaction().commit();
			em.clear();
		}
		em.close();
		emFactoryObj.getCache().evict(User.class);
		emFactoryObj.getCache().evict(Account.class);
		return response;
	}
	
	public static void handleRequestFromAnotherBank(Response response, Request req) { 
		if (req == null) {
			throw new NullPointerException("Not a request");
		}
				
		EntityManager em = emFactoryObj.createEntityManager();
		em.getTransaction().begin();
		try {
			response = handle(em, req);
		} catch (SQLException e) {
			// TODO log
			System.out.println("Database failure");
			e.printStackTrace();
		} catch (NoSuchRequestException e) {
			// TODO log
			System.out.println("Wrong request type");
			e.printStackTrace();
		}
	
		if(!response.isRequestSuccessful()) {
			em.getTransaction().rollback();
		}
		
		try {
			Thread.sleep(TIMEOUT);
		} catch (InterruptedException e) {
			//TODO log
			e.printStackTrace();
		}
		if(whiteFlag) {
			em.getTransaction().commit();
		} else {
			em.getTransaction().rollback();
		}
		em.clear();
		em.close();
		emFactoryObj.getCache().evict(User.class);
		emFactoryObj.getCache().evict(Account.class);
}

	public static Response handle(EntityManager em, Request req) throws SQLException, NoSuchRequestException {
		Response response = null;
		if(req.getAccTitle() != null) {
			if(!req.getAccTitle().substring(0, Account.getBankID().length()).equals(Account.getBankID()) || (req.getAccTitle().length() < Account.getBankID().length())) {
				req.setAccTitle(Account.getBankID() + req.getAccTitle());
			}
		}
		switch (req.getReqType()) {
		case REGISTER:
			response = register(em, req);
			break;
		case LOGIN:
			response = login(em, req);
			break;
		case REMOVE_USER:
			response = removeUser(em, req);
			break;
		case CREATE_ACC:
			response = createAcc(em, req);
			break;
		case REMOVE_ACC:
			response = removeAcc(em, req);
			break;
		case CHECK_BALANCE:
			response = checkBalance(em, req);
			break;
		case ADD_FUNDS:
			response = manageFunds(em, req);
			break;
		case REMOVE_FUNDS:
			response = manageFunds(em, req);
			break;
		default:
			throw new NoSuchRequestException("No Such Request" + req.getReqType().toString());
		}
		return response;
	}

	public static String accListToString(List<Account> accList) { 
		String accListString = "";
		if(accList != null && accList.size() > 0) {
			for(Account a: accList) {
				accListString = accListString + a.getTitle() + " ,";
			}
			accListString = accListString.substring(0, accListString.length()-2);
		}
		
		return accListString; 
	}
	
	private static boolean userExists(EntityManager em, String userID) {
		if(userID == null) {
			return false;
		} else {
			User u2 = null;
			try {
				u2 = em.find(User.class, userID);
			} catch (IllegalArgumentException e) {
				// TODO log
				System.out.println("No such entity or not a valid type for such entity");
				e.printStackTrace();
			}
			return u2 != null;
		}
	}
	
	private static User getUserByID(EntityManager em, String userID) {
		if(userID == null) {
			return null;
		} else {
			User user = null;
			try {
				user = em.find(User.class, userID);
			} catch (IllegalArgumentException e) {
				// TODO log
				System.out.println("No such entity or not a valid type for such entity");
				e.printStackTrace();
			}
			return user;
		}
	}
	
	private static Account getAccountByTitle(EntityManager em, String accTitle) {
		if(accTitle == null) {
			return null;
		} else {
			Account acc = null;
			try {
				acc = em.find(Account.class, accTitle);
			} catch (IllegalArgumentException e) {
				// TODO log
				System.out.println("No such entity or not a valid type for such entity");
				e.printStackTrace();
			}
			return acc;	
		}
		
	}
	
	private static Response register(EntityManager em, Request req) throws SQLException {
		Response response = new Response();
		if (userExists(em, req.getUserID())) {
			response.setResponseMessage("Such user already exists");
			response.setRequestSuccessful(false);
		} else {
			User user = new User(req.getUserID(), req.getUsername(), req.getPassword());
			em.persist(user);
			Account acc = null;
			if(req.getAccTitle() == null || req.getAccTitle().equals("")) {
				acc = new Account(req.getMoneyToMove(), user);
			} else {
				acc = new Account(req.getAccTitle(), req.getMoneyToMove(), user);
			}
			response.setRequestSuccessful(true);
			response.setResponseMessage("Successful registration");
			em.persist(acc);
			response.setUserID(user.getId());
		}
		return response;
	}
	
	private static Response login(EntityManager em, Request req) throws SQLException {
		Response response = new Response();
		if(!userExists(em, req.getUserID())) {
			response.setResponseMessage("Such user doesn't exist");
			response.setRequestSuccessful(false);
		} else {
			User user = getUserByID(em, req.getUserID());
			if(req.getUsername() == null) {
				if(user.getUserName() != null) {
					response.setResponseMessage("Wrong nickname");
					response.setRequestSuccessful(false);
				} else {
					response.setRequestSuccessful(true);
					response.setResponseMessage("Successful log in");
					response.setUserID(user.getId());
				}
			} else if(user.getUserName() == null) {
				response.setResponseMessage("Wrong nickname");
				response.setRequestSuccessful(false);
			} else if(!user.getUserName().equals(req.getUsername())) {
				response.setResponseMessage("Wrong nickname");
				response.setRequestSuccessful(false);
			} else if(!user.getPassword().equals(req.getPassword())) {
				response.setResponseMessage("Wrong password");
				response.setRequestSuccessful(false);
			} else {
				response.setRequestSuccessful(true);
				response.setResponseMessage("Successful log in");
				response.setUserID(user.getId());
			}
		}
		return response;
	}

	private static Response createAcc(EntityManager em, Request req) throws SQLException {
		Response response = new Response();
		if(!userExists(em, req.getUserID())) {
			response.setResponseMessage("Failed to create account: user wasn't found");
			response.setRequestSuccessful(false);
		} else {
			User user = getUserByID(em, req.getUserID());
			Account acc = getAccountByTitle(em, req.getAccTitle());
			if(acc != null) {
				response.setResponseMessage("Failed to create account: such account already exists");
				response.setRequestSuccessful(false);
			} else if(req.getAccTitle() == null || req.getAccTitle().equals("")) {
				acc = new Account(req.getMoneyToMove(), user);
				response.setRequestSuccessful(true);
				response.setResponseMessage("Account has been successfully created");
			} else {
				acc = new Account(req.getAccTitle(), req.getMoneyToMove(), user);
				response.setRequestSuccessful(true);
				response.setResponseMessage("Account has been successfully created");
			}
			em.persist(acc);
			response.setUserID(user.getId());
		}
		return response;
	}

	private static Response removeAcc(EntityManager em, Request req) throws SQLException {
		//TODO: can't delete account while it has funds on it
		Response response = new Response();
		if(!userExists(em, req.getUserID())) {
			response.setResponseMessage("Failed to delete account: user wasn't found");
			response.setRequestSuccessful(false);
		} else {
			Account acc = getAccountByTitle(em, req.getAccTitle());		
			if(acc == null) {
				response.setResponseMessage("Can't delete account: account with such title doesn't exist");
				response.setRequestSuccessful(false);
			} else {
				em.remove(acc);
				response.setRequestSuccessful(true);
				response.setResponseMessage("Account has been successfully deleted");
				response.setUserID(acc.getUser().getId());
			}
		}
		return response;
	}

	private static Response removeUser(EntityManager em, Request req) throws SQLException {
		Response response = new Response();
		if(!userExists(em, req.getUserID())) {
			response.setResponseMessage("Failed to delete user: user wasn't found");
			response.setRequestSuccessful(false);
		} else {
			User user = getUserByID(em, req.getUserID());
			List<Account> accList = user.getAccountList();
			for(Account acc: accList) {
				em.remove(acc);
			}
			response.setRequestSuccessful(true);
			response.setResponseMessage("User has been successfully deleted");
			em.remove(user);
		}
		return response;
	}

	private static Response checkBalance(EntityManager em, Request req) {
		Response response = new Response();
		if(!userExists(em, req.getUserID())) {
			response.setResponseMessage("Failed to check balance: user wasn't found");
			response.setRequestSuccessful(false);
		} else {
			Account acc = getAccountByTitle(em, req.getAccTitle());
			if(acc == null) {
				response.setResponseMessage("Failed to check balance: account with such title wasn't found");
				response.setRequestSuccessful(false);
			} else {
				response.setRequestSuccessful(true);
				response.setResponseMessage("Current balance on account \"" + acc.getTitle() + "\" is " + acc.getBalance());
			}
		}
		return response;
	}

	private static Response manageFunds(EntityManager em, Request req) {
		Response response = new Response();
		Account acc = getAccountByTitle(em, req.getAccTitle());
		if (acc == null) {
			response.setResponseMessage("Failed to transfer funds: account with such title wasn't found");
			response.setRequestSuccessful(false);
		} else {
			response = performMoneyOperation(em, req, acc);
		}
		return response;
	}
	
	private static Response performMoneyOperation(EntityManager em, Request req, Account acc) {
		Response response = new Response();
		long balance = acc.getBalance();
		if(req.getReqType().equals(RequestType.ADD_FUNDS)) {
			acc.setBalance(balance + req.getMoneyToMove());
			response.setRequestSuccessful(true);
			response.setResponseMessage("Funds were successfully transfered");
		} else if(req.getReqType().equals(RequestType.REMOVE_FUNDS)) {
			if((balance - req.getMoneyToMove()) < 0) {
				response.setResponseMessage("Can't perform money operation: insufficient funds");
				response.setRequestSuccessful(false);
			} else {
				acc.setBalance(balance - req.getMoneyToMove());
				response.setRequestSuccessful(true);
				response.setResponseMessage("Funds were successfully transfered");
			}
		}
		em.persist(acc);
		return response;
	}

	public static void main(String[] args) throws NoSuchRequestException, InterruptedException {
		
		//Request req11 = new Request(RequestType.REGISTER, "0123456789", "Peter", "SpiderVan");
		//Request req12 = new Request(RequestType.REGISTER, "0123456789", "Peter", "SpiderVan", "parker_account", 200);
		//Request req13 = new Request(RequestType.REGISTER, "0123456789", "Peter", "SpiderVan", "parker_account");
		//Request req14 = new Request(RequestType.REGISTER, "0123456789", "Peter", "SpiderVan", 100);
		//Request req15 = new Request(RequestType.REGISTER, "4567890123", null, "whatever");
		
		//Request req21 = new Request(RequestType.LOGIN, "0123456789", "Peter", "SpiderVan"); // correct log in
		//Request req22 = new Request(RequestType.LOGIN, "1023456789", "Peter", "SpiderVan"); // wrong ID
		//Request req23 = new Request(RequestType.LOGIN, "0123456789", "Peter1", "SpiderVan"); // wrong Name
		//Request req24 = new Request(RequestType.LOGIN, "0123456789", "Peter", "SpiderMan"); // wrong Pass
		//Request req25 = new Request(RequestType.LOGIN, "4567890123", null, "whatever"); // correct log in

		
		//Request req31 = new Request(RequestType.CREATE_ACC, "0123456789");
		//Request req32 = new Request(RequestType.CREATE_ACC, "0123456789", "parker_account2");
		//Request req33 = new Request(RequestType.CREATE_ACC, "0123456789", 100);
		//Request req34 = new Request(RequestType.CREATE_ACC, "0123456789", "parker_account3", 100);
		//Request req35 = new Request(RequestType.CREATE_ACC, "0123456789", "parker_account2"); //same title
		//Request req36 = new Request(RequestType.CREATE_ACC, "0123456789", "70parker_account2"); //same title
		
		//Request req41 = new Request(RequestType.REMOVE_ACC, "1023456789", "Peter_0"); //wrong userID
		//Request req42 = new Request(RequestType.REMOVE_ACC, "0123456789", "Peter_0"); //no such account
		//Request req43 = new Request(RequestType.REMOVE_ACC, "0123456789"); //no such account
		//Request req44 = new Request(RequestType.REMOVE_ACC, "0123456789", "parker_account");
		//Request req45 = new Request(RequestType.REMOVE_ACC, "0123456789", "70parker_account2");
		
		//Request req51 = new Request(RequestType.CHECK_BALANCE, "1023456789", "parker_account3"); //wrong userID
		//Request req52 = new Request(RequestType.CHECK_BALANCE, "0123456789", "parker_account2"); //no such account (delete it first)
		//Request req53 = new Request(RequestType.CHECK_BALANCE, "0123456789", "parker_account3");
		//Request req54 = new Request(RequestType.CHECK_BALANCE, "0123456789", "parker_account2"); //(create it first)
		
		//Request req61 = new Request(RequestType.ADD_FUNDS, "0123456789", "parker_account5"); //no such account
		//Request req62 = new Request(RequestType.ADD_FUNDS, null, "parker_account2", 50);
		//Request req63 = new Request(RequestType.REMOVE_FUNDS, "0123456789", "parker_account3", 50);
		//Request req64 = new Request(RequestType.REMOVE_FUNDS, null, "parker_account2", 50);//not enough funds
		
		//Request req7 = new Request(RequestType.REMOVE_USER, "0123456789"); 
		
		/*
		ArrayList<Request> reqList = new ArrayList<>();
		reqList.add(req12);
		reqList.add(req21);
		//reqList.add(req24); //wrong pass
		reqList.add(req31);
		reqList.add(req32);
		reqList.add(req33);
		//reqList.add(req35); //duplicate acc title
		reqList.add(req34);		
		Response resp = handleRequestList(reqList);
		System.out.println(resp.getResponseMessage());
		//*/
		
		/*
		ArrayList<Request> reqList = new ArrayList<>();
		reqList.add(req63);
		reqList.add(req62);
		Response resp = handleRequestList(reqList);
		System.out.println(resp.getResponseMessage());
		//*/
		
		//Request req111 = new Request(RequestType.LOGIN, "1111111111", null, "1");
		//Request req111 = new Request(RequestType.REGISTER, "1111111111", null, "1");
		/*
		Response resp2 = handleRequest(req111);
		System.out.println(resp2.getResponseMessage());
		//*/
		
		/*
		Response resp3 = handleRequest(req61);
		System.out.println(resp3.getResponseMessage());
		if(resp3.getAcclist() != null) {
			for(Account a: resp3.getAcclist()) {
				System.out.println(a.getTitle());
			}
			System.out.println(resp3.getAcclist().size());
		}
		//*/
	}
}
