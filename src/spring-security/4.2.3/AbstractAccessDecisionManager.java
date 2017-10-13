package org.springframework.security.access.vote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.Assert;

/**
 * Abstract implementation of {@link AccessDecisionManager}.
 *
 * <p>
 * Handles configuration of a bean context defined list of {@link AccessDecisionVoter}s
 * and the access control behaviour if all voters abstain from voting (defaults to deny
 * access).
 */
public abstract class AbstractAccessDecisionManager implements AccessDecisionManager,
		InitializingBean, MessageSourceAware {
	// ~ Instance fields
	// ================================================================================================
	protected final Log logger = LogFactory.getLog(getClass());

    //一组Voter，如必须是管理员，必须是某个IP地址发出的请求，AccessDecisionVoter执行具体的判断逻辑
	private List<AccessDecisionVoter<? extends Object>> decisionVoters;

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private boolean allowIfAllAbstainDecisions = false;

	protected AbstractAccessDecisionManager(
			List<AccessDecisionVoter<? extends Object>> decisionVoters) {
		Assert.notEmpty(decisionVoters, "A list of AccessDecisionVoters is required");
		this.decisionVoters = decisionVoters;
	}

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(this.decisionVoters, "A list of AccessDecisionVoters is required");
		Assert.notNull(this.messages, "A message source must be set");
	}

	protected final void checkAllowIfAllAbstainDecisions() {
		if (!this.isAllowIfAllAbstainDecisions()) {
			throw new AccessDeniedException(messages.getMessage(
					"AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
	}

	public List<AccessDecisionVoter<? extends Object>> getDecisionVoters() {
		return this.decisionVoters;
	}

	public boolean isAllowIfAllAbstainDecisions() {
		return allowIfAllAbstainDecisions;
	}

	public void setAllowIfAllAbstainDecisions(boolean allowIfAllAbstainDecisions) {
		this.allowIfAllAbstainDecisions = allowIfAllAbstainDecisions;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	public boolean supports(ConfigAttribute attribute) {
		for (AccessDecisionVoter voter : this.decisionVoters) {
			if (voter.supports(attribute)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Iterates through all <code>AccessDecisionVoter</code>s and ensures each can support
	 * the presented class.
	 * <p>
	 * If one or more voters cannot support the presented class, <code>false</code> is
	 * returned.
	 *
	 * @param clazz the type of secured object being presented
	 * @return true if this type is supported
	 */
	public boolean supports(Class<?> clazz) {
		for (AccessDecisionVoter voter : this.decisionVoters) {
			if (!voter.supports(clazz)) {
				return false;
			}
		}

		return true;
	}
}
