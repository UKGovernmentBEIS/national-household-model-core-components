package uk.org.cse.nhm.ipc.api.users;

@SuppressWarnings("serial")
public class UserRepoInaccessibleException extends Exception {
	public UserRepoInaccessibleException(final Exception e) {
		super("Cannot access users data store: " + e.getMessage(), e);
	}
}
	