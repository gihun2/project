package vo;

public class ActionForward {
	private String path; //�������
	private boolean redirect; //true:�����̷�Ʈ,false:����ġ
	private String nextPath = null;
	
	public ActionForward() {
	}
	
	public ActionForward(String path, boolean redirect) {
		super();
		this.path = path;
		this.redirect = redirect;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return redirect;
	}
	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	public String getNextPath() {
		return nextPath;
	}
	public void setNextPath(String nextPath) {
		this.nextPath = nextPath;
	}
	
	
}//ActionForward
