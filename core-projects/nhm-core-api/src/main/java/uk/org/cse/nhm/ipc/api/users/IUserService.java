package uk.org.cse.nhm.ipc.api.users;

import java.util.List;

import com.google.common.base.Optional;

public interface IUserService {
	void save (IUser user) throws UserRepoInaccessibleException;
	List<IUser> listUsers() throws UserRepoInaccessibleException;
	Optional<IUser> getUser(String loginName) throws UserRepoInaccessibleException;
}
