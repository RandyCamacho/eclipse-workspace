package login;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

public class UserAccountManager implements Serializable, Iterable<UserAccount> {
	public static volatile UserAccountManager accountManager;
	private volatile HashSet<UserAccount> userAccounts;

	public UserAccountManager() {
		userAccounts = new HashSet<>();
	}

	@SuppressWarnings("unchecked")
	public UserAccountManager(String filepath) throws FileNotFoundException {
		this.userAccounts = loadUsers(filepath);
	}

	private static HashSet<UserAccount> loadUsers(String filepath) throws FileNotFoundException {
		HashSet<UserAccount> userAccounts;
		try {
			FileInputStream fileIN = new FileInputStream(filepath);
			ObjectInputStream objectIN = new ObjectInputStream(fileIN);
			userAccounts = (HashSet<UserAccount>)objectIN.readObject();
		} catch (IOException e) {
			userAccounts = new UserAccountManager().userAccounts;
		} catch (ClassNotFoundException e) {
			userAccounts = new UserAccountManager().userAccounts;
		}
		return userAccounts;
	}

	public static void init() {
		accountManager = new UserAccountManager();
	}

	public static void init(String filepath) throws FileNotFoundException {
		accountManager = new UserAccountManager();
		accountManager.load(filepath);
	}

	private static void saveUsers(String filepath, HashSet<UserAccount> userAccounts) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filepath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(userAccounts);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String filepath) throws FileNotFoundException {
		this.userAccounts = UserAccountManager.loadUsers(filepath);
	}

	public void save(String filepath) {
		UserAccountManager.saveUsers(filepath, this.userAccounts);
	}

	public void add(UserAccount userAccount) {
		userAccounts.add(userAccount);
	}

	public void addAll(Collection<? extends UserAccount> userAccounts) {
		this.userAccounts.addAll(userAccounts);
	}

	public void getAccountByName(String username) {
		final UserAccount[] foundUser = {null};
		forEach(user -> {
			if(foundUser[0] != null && user.getUsername().equals(username)) {
				foundUser[0] = user;
			}
		});
		if(foundUser[0] == null) throw new NoSuchElementException();
	}

	public void getAccountByEmail(String email) {
		final UserAccount[] foundUser = {null};
		forEach(user -> {
			if(foundUser[0] != null && user.getEmail().equals(email)) {
				foundUser[0] = user;
			}
		});
		if(foundUser[0] == null) throw new NoSuchElementException();
	}

	public void remove(UserAccount userAccount) {
		userAccounts.remove(userAccount);
	}

	public void clear() {
		userAccounts.clear();
	}

	public boolean contains(UserAccount userAccount) {
		return userAccounts.contains(userAccount);
	}

	public int size() {
		return userAccounts.size();
	}

	public boolean isEmpty() {
		return userAccounts.isEmpty();
	}

	@Override
	public Iterator<UserAccount> iterator() {
		return userAccounts.iterator();
	}

	@Override
	public void forEach(Consumer<? super UserAccount> action) {
		userAccounts.forEach(action);
	}

	@Override
	public Spliterator<UserAccount> spliterator() {
		return userAccounts.spliterator();
	}
}
