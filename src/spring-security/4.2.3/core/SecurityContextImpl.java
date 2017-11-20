package org.springframework.security.core.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * 重写equals()和hashCode()，保证Authentication唯一性
 *
 * Base implementation of {@link SecurityContext}.
 * <p>
 * Used by default by {@link SecurityContextHolder} strategies.
 *
 * @author Ben Alex
 */
public class SecurityContextImpl implements SecurityContext {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	// ~ Instance fields
	// ================================================================================================

	private Authentication authentication;

	// ~ Methods
	// ========================================================================================================

	public boolean equals(Object obj) {
		if (obj instanceof SecurityContextImpl) {
			SecurityContextImpl test = (SecurityContextImpl) obj;

			if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) {
				return true;
			}

			if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
					&& this.getAuthentication().equals(test.getAuthentication())) {
				return true;
			}
		}

		return false;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public int hashCode() {
		if (this.authentication == null) {
			return -1;
		}
		else {
			return this.authentication.hashCode();
		}
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());

		if (this.authentication == null) {
			sb.append(": Null authentication");
		}
		else {
			sb.append(": Authentication: ").append(this.authentication);
		}

		return sb.toString();
	}
}
