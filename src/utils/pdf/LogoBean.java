package utils.pdf;

public class LogoBean {
	private String companyLogoPath;
	private String companyStampPath;

	public LogoBean(String companyLogoPath, String companyStampPath) {
		super();
		this.companyLogoPath = companyLogoPath;
		this.companyStampPath = companyStampPath;
	}

	public String getCompanyLogoPath() {
		return companyLogoPath;
	}

	public void setCompanyLogoPath(String companyLogoPath) {
		this.companyLogoPath = companyLogoPath;
	}

	public String getCompanyStampPath() {
		return companyStampPath;
	}

	public void setCompanyStampPath(String companyStampPath) {
		this.companyStampPath = companyStampPath;
	}

}
