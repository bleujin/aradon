package net.ion.radon.filter;

public class Employee {

	private int deptno;
	private String ename;
	private int empNo;
	private String[] names ;

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empno) {
		this.empNo = empno;
	}

	public void setNames(String[] names){
		this.names = names ;
	}
	public String[] getNames(){
		return names ;
	}
}
