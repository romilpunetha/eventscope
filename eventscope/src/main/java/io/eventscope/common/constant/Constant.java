package io.eventscope.common.constant;

import javax.inject.Singleton;
import java.util.*;

@Singleton
public class Constant {

    public static final String INDIA_COUNTRY_CALLING_CODE = "+91";
    public static final String COUNTRY_CALLING_CODE_CONNECTOR = "-";
    public static final Set<String> whitelistedDomains = new HashSet<>(Arrays.asList(
            "rediff.com", "aol.com", "live.in", "googlemail.com", "rocketmail.com",
            "privaterelay.appleid.com", "live.com", "icloud.com", "outlook.com", "ymail.com", "rediffmail.com", "yahoo.in",
            "hotmail.com", "yahoo.co.in", "yahoo.com", "gmail.com"
    ));
    public static final List<String> INTERNAL_USERS_EMAIL_DOMAINS = new ArrayList<>(Arrays.asList("@eventscope"));
    public static final String DELIMITER = "__";
    public static final Long DEFAULT_CACHE_EXPIRY_TIME_IN_MILLISECONDS = 60 * 60 * 1000L;
    public static final String HEADER_PREFIX = "x-eventscope-";
    public static final String HEADER_SESSION = HEADER_PREFIX + "session";


    public static final String HEADER_CLIENT_ID = HEADER_PREFIX + "clientId";
    public static final String HEADER_CLIENT_SECRET = HEADER_PREFIX + "clientSecret";

    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong";

    public static final String DEFAULT_GROUP_NAME = "-1";
    public static final String REQUEST_METHOD = "requestMethod";
    public static final String REQUEST_URL = "requestUrl";
    public static final String REQUEST_BODY = "requestBody";
    public static final String REQUEST_HEADERS = "requestHeaders";
    public static final String RESPONSE_URL = "responseUrl";
    public static final String RESPONSE_BODY = "responseBody";
    public static final String RESPONSE_HEADERS = "responseHeaders";
    public static final String RESPONSE_STATUS = "responseStatus";
    public static final String RESPONSE_STATUS_TEXT = "responseStatusText";
}
