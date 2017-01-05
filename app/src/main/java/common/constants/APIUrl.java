package common.constants;

/**
 * Created by longnguyen on 12/14/16.
 */

public class APIUrl {

    /** UAT */
//    private static final String API_HOST = "http://54.169.59.1:9090/service_design_toolkit-web/api/";

    /**
     * DEV
     */
    private static final String API_HOST = "http://54.169.243.190:8080/service_design_toolkit-web/api/";

    public static final String API_GET_JOURNEY_LIST_FOR_REGISTER = API_HOST + "get_journey_list_for_register";

    public static final String API_FIELD_RESEARCHER_REGISTER = API_HOST + "field_researcher_register";

    public static final String API_REGISTER_FIELD_RESEARCHER_WITH_JOURNEY = API_HOST + "register_field_researcher_with_journey";

    public static final String API_UPDATE_RESEARCH_WORK = API_HOST + "update_research_work";

    public static final String API_GET_TOUCH_POINT_LIST_OF_REGISTERED_JOURNEY = API_HOST + "get_touch_point_list_of_registered_journey";

    public static final String API_GET_RESEARCH_WORK_LIST_BY_JOURNEY_NAME_AND_USERNAME = API_HOST + "get_research_work_list_by_journey_name_and_username";

    public static final String API_UDPDATE_FIELD_RESEARCHER_CURRENT_LOCATION = API_HOST + "refresh_current_location";

    public static final String API_MARK_JOURNEY_COMPLETED = API_HOST + "journey_mark_complete";

    public static final String METHOD_POST = "POST";

    public static final String METHOD_GET = "GET";
}
