package org.springframework.security.core.context;

/**
 * A strategy for storing security context information against a thread.
 *
 * <p>
 * The preferred strategy is loaded by {@link SecurityContextHolder}.
 *
 * @author Ben Alex
 */
public interface SecurityContextHolderStrategy {
	// ~ Methods
	// ========================================================================================================

	/**
	 * Clears the current context.
	 */
	void clearContext();

	/**
	 * Obtains the current context.
	 *
	 * @return a context (never <code>null</code> - create a default implementation if
	 * necessary)
	 */
	SecurityContext getContext();

	/**
	 * Sets the current context.
	 *
	 * @param context to the new argument (should never be <code>null</code>, although
	 * implementations must check if <code>null</code> has been passed and throw an
	 * <code>IllegalArgumentException</code> in such cases)
	 */
	void setContext(SecurityContext context);

	/**
	 * Creates a new, empty context implementation, for use by
	 * <tt>SecurityContextRepository</tt> implementations, when creating a new context for
	 * the first time.
	 *
	 * @return the empty context.
	 */
	SecurityContext createEmptyContext();
}
