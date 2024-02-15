package membership;

public class MemberDTO { //member테이블에 정보를 객체화 하여 getter.setter 설정
	//멤버 변수 선언
	private String id;
	private String pass;
 	private String name;
	private String regidate;
	
	//생성자는 기본생성자로
	public MemberDTO() {};
	
	//멤버 변수별 게터와 세터
	public String getId() {
		return id;
	}
	public String getPass() {
		return pass;
	}
	public String getName() {
		return name;
	}
	public String getRegidate() {
		return regidate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRegidate(String regidate) {
		this.regidate = regidate;
	}
}
