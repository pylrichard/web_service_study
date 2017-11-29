package org.springframework.security.access.vote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * Simple concrete implementation of
 * {@link org.springframework.security.access.AccessDecisionManager} that requires all
 * voters to abstain or grant access.
 */
public class UnanimousBased extends AbstractAccessDecisionManager {

    public UnanimousBased(List<AccessDecisionVoter<? extends Object>> decisionVoters) {
        super(decisionVoters);
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * This concrete implementation polls all configured {@link AccessDecisionVoter}s for
     * each {@link ConfigAttribute} and grants access if <b>only</b> grant (or abstain)
     * votes were received.
     * <p>
     * Other voting implementations usually pass the entire list of
     * <tt>ConfigAttribute</tt>s to the <code>AccessDecisionVoter</code>. This
     * implementation differs in that each <code>AccessDecisionVoter</code> knows only
     * about a single <code>ConfigAttribute</code> at a time.
     * <p>
     * If every <code>AccessDecisionVoter</code> abstained from voting, the decision will
     * be based on the {@link #isAllowIfAllAbstainDecisions()} property (defaults to
     * false).
     *
     * @param authentication the caller invoking the method
     * @param object         the secured object
     * @param attributes     the configuration attributes associated with the method being
     *                       invoked
     * @throws AccessDeniedException if access is denied
     */
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> attributes) throws AccessDeniedException {

        int grant = 0;
        int abstain = 0;

        List<ConfigAttribute> singleAttributeList = new ArrayList<ConfigAttribute>(1);
        singleAttributeList.add(null);

        for (ConfigAttribute attribute : attributes) {
            singleAttributeList.set(0, attribute);

            for (AccessDecisionVoter voter : getDecisionVoters()) {
                int result = voter.vote(authentication, object, singleAttributeList);

                if (logger.isDebugEnabled()) {
                    logger.debug("Voter: " + voter + ", returned: " + result);
                }

                switch (result) {
                    case AccessDecisionVoter.ACCESS_GRANTED:
                        grant++;

                        break;

                    //有一个Voter拒绝则不通过
                    case AccessDecisionVoter.ACCESS_DENIED:
                        throw new AccessDeniedException(messages.getMessage(
                                "AbstractAccessDecisionManager.accessDenied",
                                "Access is denied"));

                    default:
                        abstain++;

                        break;
                }
            }
        }

        // To get this far, there were no deny votes
        if (grant > 0) {
            return;
        }

        // To get this far, every AccessDecisionVoter abstained
        checkAllowIfAllAbstainDecisions();
    }
}
