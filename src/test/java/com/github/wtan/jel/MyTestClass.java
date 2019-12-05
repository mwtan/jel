package com.github.wtan.jel;

public class MyTestClass {
	public int rc = 0;
	public String mystring = null;

	public MyTestClass() {
	}
	public MyTestClass(boolean parm) {
	}
	public MyTestClass(byte parm) {
	}
	public MyTestClass(char parm) {
	}
	public MyTestClass(short parm) {
	}
	public MyTestClass(int parm) {
	}
	public MyTestClass(long parm) {
	}
	public MyTestClass(float parm) {
	}
	public MyTestClass(double parm) {
	}
	public MyTestClass(boolean parm1, int parm2, String parm3) throws InstantiationException {
		throw new InstantiationException();
	}
	public MyTestClass(int i, String str) {
		rc = i;
		mystring = str;
	}

	public int doubleit(int i) {
		return (i * 2);
	}

	public String welcome() {
		return mystring;
	}
}
