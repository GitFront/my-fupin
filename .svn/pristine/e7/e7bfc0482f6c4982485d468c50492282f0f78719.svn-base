package com.aspire.birp.modules.sys.authentication;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.aspire.auth.modules.sys.entity.Menu;
import com.aspire.auth.modules.sys.entity.User;
import com.aspire.birp.modules.sys.utils.UserUtils;

public class SimpleCasRealm  extends CasRealm{
	@Autowired  
    private CacheManager cacheManager;  
	
	//rmi  client
    private final static Logger log = LoggerFactory.getLogger(SimpleCasRealm.class);  
  
    public SimpleCasRealm() {  
        super();  
        setCacheManager(cacheManager);  
    }
    
    
    public static HostnameVerifier getLocalHostnameVerifier() {
		 return new HostnameVerifier() {
 			public boolean verify(String urlHostName, SSLSession session) {
 			 System.out.println("Warning: URL Host: " + urlHostName +
			 " vs. "  + session.getPeerHost());
				// TODO Auto-generated method stub
				return true;
			}
		};
	}
    
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
      	System.setProperty("https.protocols", "TLSv1");
    	
		HttpsURLConnection.setDefaultHostnameVerifier(getLocalHostnameVerifier());  

        CasToken casToken = (CasToken) token;  
        if (token == null)  
            return null;  
        String ticket = (String) casToken.getCredentials();  
        if (!StringUtils.hasText(ticket))  
            return null;  
        TicketValidator ticketValidator = ensureTicketValidator();  
        try {
        	System.out.println("TicketValidator="+ticketValidator.getClass().getName());
        	System.out.println("CasService="+getCasService());
        	
             Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
             
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();  
            String userId = casPrincipal.getName();  
             
            log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[] { ticket,  
                    getCasServerUrlPrefix(), userId });  
            Map attributes = casPrincipal.getAttributes();  
            casToken.setUserId(userId);  
            String rememberMeAttributeName = getRememberMeAttributeName();  
            String rememberMeStringValue = (String) attributes.get(rememberMeAttributeName);  
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);  
            if (isRemembered)  
                casToken.setRememberMe(true);  
            List principals = CollectionUtils.asList(new Object[] { userId, attributes });  
             PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());  
             //这里可以拿到Cas的登录账号信息,加载到对应权限体系信息放到缓存中...
            return new SimpleAuthenticationInfo(principalCollection, ticket);  
        } catch (TicketValidationException e) {  
        	e.printStackTrace();
            throw new CasAuthenticationException((new StringBuilder()).append("Unable to validate ticket [")  
                    .append(ticket).append("]").toString(), e);  
        }  
    }  
    
    
    
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
 		User user = UserUtils.getUser();
         if(user!=null){
        	SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();  
          	List<Menu> list = new ArrayList<Menu>();
			try {
				list = UserUtils.getMenuList();
			} catch (RemoteException e) {
 				e.printStackTrace();
			}
 			eachMenuChirdList(list,simpleAuthorizationInfo);
     		return simpleAuthorizationInfo;  
        }else {
			return null;
		} 
    }  
    
    private static void eachMenuChirdList(List<Menu> menus,SimpleAuthorizationInfo simpleAuthorizationInfo){
 		if(menus!=null){
			for(int i = 0 ; i < menus.size() ; i++){
				 Menu menu = menus.get(i);
				if (menu.getPermission()!=null && !menu.getPermission().trim().equals("")){
    				// 添加基于Permission的权限信息
    				String[] permissions = menu.getPermission().split(",");
    				if(permissions!=null){
	    				for (String permission : permissions){
	        				System.out.println("permission="+permission);
	    					simpleAuthorizationInfo.addStringPermission(permission);
	    				}
    				}
    			}
  		     	 //System.out.println(menus.get(i).getName());
			     if(menus.get(i).getChildList()!=null && menus.get(i).getChildList().size()>0){
			    	 eachMenuChirdList( menus.get(i).getChildList(),simpleAuthorizationInfo);
			     }
			}
		}
 	}
  
    protected List split(String s) {  
        List list = new ArrayList();  
        String elements[] = s==null?null:s.split(",");  
        if (elements != null && elements.length > 0) {  
            String arr$[] = elements;  
            int len$ = arr$.length;  
            for (int i$ = 0; i$ < len$; i$++) {  
                String element = arr$[i$];  
                if (StringUtils.hasText(element))  
                    list.add(element.trim());  
            }  
        }  
        return list;  
    }  
  
    protected void addRoles(SimpleAuthorizationInfo simpleAuthorizationInfo, List roles) {  
        String role;  
        for (Iterator i$ = roles.iterator(); i$.hasNext(); simpleAuthorizationInfo.addRole(role))  
            role = (String) i$.next();  
     }  
    
    protected void addPermissions(SimpleAuthorizationInfo simpleAuthorizationInfo, List permissions) {  
        String permission;  
        for (Iterator i$ = permissions.iterator(); i$.hasNext(); simpleAuthorizationInfo  
                .addStringPermission(permission))  
            permission = (String) i$.next();  
     }  
    
    /** 重写退出时缓存处理方法 */  
    protected void doClearCache(PrincipalCollection principals) {  
        Object principal = principals.getPrimaryPrincipal();  
        try {  
            getCache().remove(principal);  
            log.debug(new StringBuffer().append(principal).append(" on logout to remove the cache [").append(principal)  
                    .append("]").toString());  
        } catch (CacheException e) {  
            log.error(e.getMessage());  
        }  
    }
  
    /** 获取缓存管理器的缓存堆实例 */  
    protected Cache<Object, Object> getCache() throws CacheException {  
        return cacheManager.getCache("MEMCACHED_DATA_CACHE");  
    }  
  
    public CacheManager getCacheManager() {  
        return cacheManager;  
    }  
  
    public void setCacheManager(CacheManager cacheManager) {  
        this.cacheManager = cacheManager;  
    }  
    
  
    
}
