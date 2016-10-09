package vn.edu.fpt.a201.a1.utils.logging;

import java.util.Arrays;

/**
 * Application logger. Apache log4j enhanced wrapper class.<br />
 * Please do not change the credit of this class even if you use
 * this in another project.
 *
 * @author minhnn
 * @version 1.0
 */
public class Logger {

    private static Logger logger = new Logger();

    private org.apache.log4j.Logger __logger__;

    private Logger() {

    }

    /**
     * Get logger for a specific class.
     *
     * @param clz Class name of class will be used as the name of logger to retrieve.
     * @return logger
     */
    public static Logger getLogger(Class<?> clz) {
        logger.__logger__ = org.apache.log4j.Logger.getLogger(clz);
        return logger;
    }

    /**
     * Log a debug-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void debug(Object... msg) {
        __logger__.debug(format(msg));
    }

    /**
     * Log a info-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void info(Object... msg) {
        __logger__.info(format(msg));
    }

    /**
     * Log a warn-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void warn(Object... msg) {
        __logger__.warn(format(msg));
    }

    /**
     * Log a fatal-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void fatal(Object... msg) {
        __logger__.fatal(format(msg));
    }

    /**
     * Log a trace-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void trace(Object... msg) {
        __logger__.trace(format(msg));
    }

    /**
     * Log a error-level formatted message to log stream.
     *
     * @param msg array of objects to format message<br />
     *            msg[0] message form<br />
     *            msg[1..n] parameters to format<br />
     */
    public void error(Object... msg) {
        __logger__.error(format(msg));
    }

    /**
     * Format log parameters object.
     *
     * @param msg object of parameters to log
     * @return String to log
     */
    private String format(Object... msg) {
        if (msg == null || msg.length < 1) return null;
        String str = String.valueOf(msg[0]);
        return ((msg.length == 1)
                ? str : String.format(str, Arrays.copyOfRange(msg, 1, msg.length)));
    }
}
