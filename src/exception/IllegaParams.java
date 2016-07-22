package exception;

public class IllegaParams extends Throwable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msg;
	private Object[] object;
	public IllegaParams(){
	}
	
	public IllegaParams(String msg){
		this.msg = msg;
	}
	
	public IllegaParams(String msg,Object...objects){
		this.msg = msg;
		this.object = object;
	}
	
	@Override
	public String getMessage() {
		if(this.msg!=null){
			return this.getMessage();
		}else if(this.msg!=null && this.object!=null){
			return String.format(this.msg,this.object);
		}
		return super.getMessage();
	}
}
