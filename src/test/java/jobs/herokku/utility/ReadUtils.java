package jobs.herokku.utility;

import org.apache.commons.lang3.RandomStringUtils;

public class ReadUtils {
	
	public static String jobId() {
		String generatedString = RandomStringUtils.randomNumeric(4);
		return ("John"+generatedString);
	}

	public static String jobTitle() {
		String generatedString = RandomStringUtils.randomAlphabetic(4);
		return (generatedString);
	}
	
	public static String jobNameAge() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return (generatedString);
	}

}
