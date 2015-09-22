package Base;

import java.io.Serializable;

public class DBCustorm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String poolName;
	private String CompanyID;
	private String UserName;
	private String LoginName;
	public String getLoginName(){
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	private String SeatID;
	private String DBUrl;
	private String DBName;
	private String DBType;
	private String DBPort;
	private String dbuser;
	private String dbpwd;
	private String userIp;
	private String userid;
	private String ScenceId;//场景Id，未进入场景时为空，进入后程序会更需为当前的场景ID
	private String ServerUrl;//服务器地址，服务器相对客户端的连接地址（相当于basePath）
	private String TerminalID;//三维客户端编号，ip+该编号可唯一标识一个三维客户端

	public String getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(String terminalID) {
		TerminalID = terminalID;
	}
	public String getServerUrl() {
		return ServerUrl;
	}
	public void setServerUrl(String serverUrl) {
		ServerUrl = serverUrl;
	}
	public String getScenceId() {
		return ScenceId;
	}
	public void setScenceId(String scenceId) {
		ScenceId = scenceId;
	}
		public String getDbpwd() {
		return dbpwd;
	}
	public void setDbpwd(String dbpwd) {
		this.dbpwd = dbpwd;
	}
	public String getDbuser() {
		return dbuser;
	}
	public void setDbuser(String dbuser) {
		this.dbuser = dbuser;
	}
		public String getDBPort() {
		return DBPort;
	}
	public void setDBPort(String port) {
		DBPort = port;
	}
		public String getCompanyID() {
			return CompanyID;
		}
		public void setCompanyID(String companyID) {
			CompanyID = companyID;
		}
		public String getUserName() {
			return UserName;
		}
		public void setUserName(String userName) {
			UserName = userName;
		}
		public String getSeatID() {
			return SeatID;
		}
		public void setSeatID(String seatID) {
			SeatID = seatID;
		}
		public String getDBUrl() {
			return DBUrl;
		}
		public void setDBUrl(String url) {
			DBUrl = url;
		}
		public String getDBName() {
			return DBName;
		}
		public void setDBName(String name) {
			DBName = name;
		}
		public String getDBType() {
			return DBType;
		}
		public void setDBType(String type) {
			DBType = type;
		}
		

		@Override
		public String toString() {
			
			return  CompanyID+"<-CompanyID ||"+
					UserName +"<-UserName  ||"+
					SeatID   +"<-SeatID ||"+
					DBUrl    +"<-DBUrl  ||"+
					DBName   +"<-DBName ||"+
					DBPort   +"<-DBPort ||"+
					DBType   +"<-DBType ||"+
					dbuser   +"<-dbuser ||"+
					dbpwd    +"<-dbpwd  ||"+
					userIp   +"<-userIp ||"+
					poolName +"<-poolName";
		}
		public String getPoolName() {
			poolName=getDBName()+"_"+getDbpwd()+getDbuser()+"_"+getDBType()+DBUrl;
			return poolName;
		}
		
		public String getPoolalias() {
		
			return getDBName()+"_"+getDbpwd()+"_"+getDbuser()+"_"+getDBType()+"_"+DBUrl.replaceAll("\\.","");
		}
		
		
		public String getUserIp() {
			return userIp;
		}
		public void setUserIp(String userIp) {
			this.userIp = userIp;
		}
		public String getUserid() {
			return userid;
		}
		public void setUserid(String userid) {
			this.userid = userid;
		}


		
}
