package net.ion.radon.core;

import java.util.Collections;
import java.util.List;

import net.ion.framework.util.LimitedList;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.let.PathService;

import org.apache.commons.lang.ArrayUtils;
import org.restlet.service.LogService;

public class RadonLogService extends LogService {

	private List<LogRecord> recentRecord = Collections.synchronizedList(new LimitedList<LogRecord>(1000));


	public List<String> recentLog(IService service) {
		return recentLog(service, 20);
	}

	public List<String> recentLog(IService service, int count) {
		LogRecord[] records = recentRecord.toArray(new LogRecord[0]);
		ArrayUtils.reverse(records);

		List<String> result = ListUtil.newList();
		for (LogRecord record : records) {
			if (record.isMath(service.getNamePath())) {
				result.add(record.getMessage());
			}
			if (--count < 0)
				break;
		}
		return result;
	}

//	public String getLoggerName() {
//		return super.getLoggerName();
//	}
//
//	public Reference getLogPropertiesRef() {
//		return super.getLogPropertiesRef();
//	}
//
//	public String getResponseLogFormat() {
//		return super.getResponseLogFormat();
//	}

}

class LogRecord {
	private String path;
	private String message;

	private LogRecord(String path, String message) {
		this.path = path;
		this.message = message;
	}

	final static LogRecord create(PathService ps, String message) {
		return new LogRecord(ps.getNamePath(), message);
	}

	boolean isMath(String thatPath) {
		return path.startsWith(thatPath);
	}

	String getMessage() {
		return message;
	}
}
