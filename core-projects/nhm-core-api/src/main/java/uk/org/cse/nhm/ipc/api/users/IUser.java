package uk.org.cse.nhm.ipc.api.users;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

/**
 * A user of the NHM web client. 
 */
public interface IUser {
	String getLoginName();
	String getEmail();
	Optional<DateTime> getLastLoginDate();
	Set<Role> getRoles();
	IAuthDetails getAuthDetails();
	boolean isEnabled();
	Set<String> getTags();
	
	/**
	 * Represents an instance of a password with a user.
	 */
	public interface IAuthDetails {
		/**
		 * @return The salt generated for this password combined with the result of putting the user's password + salt through the cryptographic hash function. 
		 */
		String getSaltAndHash();
		
		/**
		 * @return The date on which the password was last set explicitly by the user.
		 */
		DateTime getPasswordDate();
		
		/**
		 * @return Whether or not the password is a one-time computer generated password which must be changed at next login. 
		 */
		boolean isOneTime();
	}
	
	enum Role {
		AnyLoggedIn,
		User,
		Admin
	}
}
