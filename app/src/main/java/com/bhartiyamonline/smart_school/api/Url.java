package com.bhartiyamonline.smart_school.api;

public class Url {

    private static final String ANOTHER_BASE_URL = "https://bhartiyamonline.com/schoolcrm/api/";
    private static final String BASE_URL = "https://onduties.com/schoolcrm/api/";
    private static final String SMS_BASE_URL = "http://nimbusit.co.in/api/";

    public static final String ANOTHER_Image_url = "https://bhartiyamonline.com/schoolcrm/upload/";
    public static final String Image_url = "https://onduties.com/schoolcrm/upload/";

    public static final String MAIN_URL = BASE_URL;
    public static final String LOGIN_API =BASE_URL+"login?";
    public static final String SHOW_CLASS_API = BASE_URL+"show-class?";
    public static final String ADD_CLASS_API = BASE_URL+"submit-class";
    public static final String SHOW_STUDENT_API = BASE_URL+"show-student?";
    public static final String SHOW_TEACHER_API = BASE_URL+"show-teacher?";
    public static final String SHOW_SECTION_API = BASE_URL+"show-section?";
    public static final String ADD_SECTION_API = BASE_URL+"submit-section?";
    public static final String ADD_STUDENT_API = BASE_URL+"student-submit";
    public static final String ADD_TEACHER_API = BASE_URL+"teacher-submit";
    public static final String SHOW_STUDENT_DETAILS_API = BASE_URL+"student-details/";
    public static final String SHOW_TEACHER_DETAILS_API = BASE_URL+"teacher-details/";
    public static final String DELETE_CLASS_API = BASE_URL+"class-delete";
    public static final String DELETE_SECTION_API = BASE_URL+"section-delete";
    public static final String DASHBOARD_LIST_DATA_API = BASE_URL+"dashboard-tile-data";
    public static final String SHOW_TOTAL_STUDENT_API = BASE_URL+"student-list";
    public static final String SHOW_TOTAL_SMS_API = BASE_URL+"school-sms";




    public static final String REMAIN_SMS_COUNT_API = BASE_URL+"sms-sent";
    public static final String ADD_SMS_REQUEST_API = BASE_URL+"sms-request-submit";



    //-------------------------SMS URLS--------------------------------
    public static final String SEND_SMS_API = SMS_BASE_URL+"swsend.asp?";


}
