package au.net.abc.utils;

public interface TestCaseConstants {
	
	public static final String PASSED = "Passed";
	public static final String FAILED = "Failed";
	
	public interface Protocol {
		public static final String HTTP_PROTOCOL = "http";
		public static final String FILE_PROTOCOL = "file";
		public static final String FTP_PROTOCOL = "ftp";
	}
	
	public interface FTPDetails {
		public static final String FTP_HOST = "localhost";
		public static final int FTP_PORT = 9981;
		public static final String FTP_USER = "user1";
		public static final String FTP_PASSWORD = "password";
	}
	
	public interface XmlTags {
		
		public static final String TEST_SUITE_TAG = "testsuite";
		public static final String TEST_CASE_TAG = "testcase";
		public static final String PROTOCOL_TAG = "protocol";
		public static final String REQUEST_TAG = "request";
		public static final String URL_TAG = "url";
		public static final String DROP_TAG = "drop";
		public static final String FILE_TAG = "file";
		public static final String VALIDATE_TAG = "validate";
		public static final String VALIDATE_FILE_TAG = "validate-file";
		public static final String VALIDATE_FTP_FILE_TAG = "validate-ftp-file";
		public static final String VALIDATE_FTP_UPLOAD_TAG = "validate-ftp-upload";
		public static final String VALIDATE_SLEEP_TAG = "validate-sleep";
		public static final String VALIDATE_RESPONSE_TAG = "validate-response";
		public static final String VERIFY_FTP_FILE_NOT_EXIST_TAG = "verify-ftp-file-not-exist";
		
		
		
		public static final String ORDER_ATTRIBUTE = "order";
	}
}
