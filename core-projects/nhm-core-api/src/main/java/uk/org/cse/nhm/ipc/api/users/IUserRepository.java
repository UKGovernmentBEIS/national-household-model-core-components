package uk.org.cse.nhm.ipc.api.users;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

public interface IUserRepository {
	public static final String ALWAYS_ADMIN = "always admin";
	
	List<IUser> listUsers() throws UserRepoInaccessibleException;
	Optional<IUser> getUser(String loginName) throws UserRepoInaccessibleException;
	IUser createUser(String loginName, String email, String hashAndSalt) throws UserRepoInaccessibleException;
	IUser promoteUser(IUser normalUser) throws UserRepoInaccessibleException;
	IUser demoteUser(IUser admin) throws UserRepoInaccessibleException;
	IUser disableUser(IUser normalUser) throws UserRepoInaccessibleException;
	IUser enableUser(IUser disabledUser) throws UserRepoInaccessibleException;
	IUser changeEmail(IUser user, String email) throws UserRepoInaccessibleException;
	IUser changePassword(IUser user, String hashAndSalt) throws UserRepoInaccessibleException;
	IUser useRecoveryPassword(IUser user, String hashAndSalt) throws UserRepoInaccessibleException;
	IUser changeTags(IUser user, Set<String> add, Set<String> remove) throws UserRepoInaccessibleException;
	IUser loggedIn(IUser user) throws UserRepoInaccessibleException;
}
