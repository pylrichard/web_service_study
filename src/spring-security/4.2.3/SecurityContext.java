package org.springframework.security.core.context;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Interface defining the minimum security information associated with the current thread
 * of execution.
 *
 * <p>
 * The security context is stored in a {@link SecurityContextHolder}.
 * </p>
 *
 * @author Ben Alex
 */
public interface SecurityContext extends Serializable {
	// ~ Methods
	// ========================================================================================================

	/**
	 * Obtains the currently authenticated principal, or an authentication request token.
	 *
	 * @return the <code>Authentication</code> or <code>null</code> if no authentication
	 * information is available
	 */
	Authentication getAuthentication();

	/**
	 * Changes the currently authenticated principal, or removes the authentication
	 * information.
	 *
	 * @param authentication the new <code>Authentication</code> token, or
	 * <code>null</code> if no further authentication information should be stored
	 */
	void setAuthentication(Authentication authentication);
}
