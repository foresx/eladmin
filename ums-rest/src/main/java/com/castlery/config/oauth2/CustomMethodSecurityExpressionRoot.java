package com.castlery.config.oauth2;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot
    implements MethodSecurityExpressionOperations {

  private Object filterObject;
  private Object returnObject;

  public CustomMethodSecurityExpressionRoot(Authentication authentication) {
    super(authentication);
  }

  @Override
  public Object getFilterObject() {
    return this.filterObject;
  }

  @Override
  public Object getReturnObject() {
    return this.returnObject;
  }

  @Override
  public Object getThis() {
    return this;
  }

  @Override
  public void setFilterObject(Object obj) {
    this.filterObject = obj;
  }

  @Override
  public void setReturnObject(Object obj) {
    this.returnObject = obj;
  }

  public boolean deleteable() {
    //      User user = ((ExtendUser) this.getPrincipal()).getUser();
    //      return user.getOrganization().getId().longValue() == OrganizationId.longValue();
    //    OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)
    // this.getAuthentication()
    //        .getDetails();
    //    String str = (String) details.getDecodedDetails();
    //    CustomerOAuth2AuthenticatedPrincipal principal =
    //        (CustomerOAuth2AuthenticatedPrincipal) this.getAuthentication().getPrincipal();
    return this.getAuthentication().getAuthorities().stream()
        .anyMatch(grant -> grant.getAuthority().equals("delete"));
    //    return "foo@bar.com".equals(principal.getExtra());
  }
}
