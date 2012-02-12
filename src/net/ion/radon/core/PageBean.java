package net.ion.radon.core;

import java.io.Serializable;
import java.util.List;

import net.ion.framework.db.Page;

public class PageBean implements Serializable{

	private static final long serialVersionUID = 1185739585588000730L;
	public final static PageBean TEN = new PageBean(Page.TEN) ;
	public static final PageBean ALL = new PageBean(Page.ALL) ;
	private Page page;

	public PageBean(){ // for reflection 
		this(Page.TEN) ;
	}
	
	private PageBean(Page page) {
		this.page = page ;
	}
	
	public final static PageBean create(int listNum, int pageNo, int screenCount) {
		return new PageBean(Page.create(listNum, pageNo, screenCount)) ; 
	}
	public final static PageBean create(int listNum, int pageNo) {
		return new PageBean(Page.create(listNum, pageNo)) ; 
	}

	public void setListNum(int listNum) {
		page = Page.create(listNum, page.getPageNo(), page.getScreenCount());
	}

	public void setPageNo(int pageNo) {
		page = Page.create(page.getListNum(), pageNo, page.getScreenCount());
	}

	public void setScreenCount(int screenCount) {
		page = Page.create(page.getListNum(), page.getPageNo(), screenCount);
	}

	public int getListNum() {
		return page.getListNum();
	}

	public int getPageNo() {
		return page.getPageNo();
	}

	public int getScreenCount() {
		return page.getScreenCount();
	}

	public int getStartLoc() {
		return page.getStartLoc();
	}

	public int getEndLoc() {
		return page.getEndLoc();
	}

	public int getMaxScreenCount() {
		return page.getMaxScreenCount();
	}
	
	public int getSkipScreenCount(){
		return page.getPreScreenEndPageNo() * getListNum() ;
	}
	
	public int getPageIndexOnScreen(){
		return getPageNo() - page.getMinPageNoOnScreen() ;
	}

	public int getMaxCount() {
		return page.getMaxCount();
	}

	public <T> List<T> subList(List<T> result) {
		return page.subList(result) ;
	}

	public String toString() {
		return "{pageNo:" + getPageNo() + ", listNum:" + getListNum() + ", screenCount:" + getScreenCount() + "}";
	}

	public PageBean getNextPage() {
		return new PageBean(page.getNextPage());
	}

	public PageBean getPrePage() {
		return new PageBean(page.getPrePage());
	}

	public Page toPage(){
		return page ;
	}
	
//	public int getSkipPageCount() {
//		return ((getPageNo()-1) % getListNum()) * getListNum();
//	}
}
