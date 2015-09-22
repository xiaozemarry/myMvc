package Base;

/**
 * 系统相关的一些路径
 * @author jacky-chueng
 *
 */
public final class Path {
  public static final String CATALINE_BASE =  System.getProperty("catalina.base");
  public static final String CATALINE_HOME =  System.getProperty("catalina.home");
  public static final String FILE_ENCODING =  System.getProperty("file.encoding");
  /**
   * file.separator=\
   */
  public static final String FS =  System.getProperty("file.separator");
  /**
   * line.separator=\r\n
   */
  public static final String LS =  System.getProperty("line.separator");
  /**
   * path.separator=;
   */
  public static final String PS =  System.getProperty("path.separator");
  public static final String USER_DIR =  System.getProperty("user.dir");
  public static final String USER_HOME =  System.getProperty("user.home");
  public static final String USER_NAME =  System.getProperty("user.name");
  public static final String WTP_DEPLOY =  System.getProperty("wtp.deploy");
} 
