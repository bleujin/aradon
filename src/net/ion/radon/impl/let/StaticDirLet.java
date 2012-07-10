package net.ion.radon.impl.let;

import net.ion.radon.core.TreeContext;

import org.restlet.Restlet;
import org.restlet.resource.Directory;

public class StaticDirLet {

	private TreeContext context ;
	public StaticDirLet(TreeContext context){
		this.context = context ;
	}
	
	private static final String ROOT_URI = "file:///c:/temp/";
	public Restlet createRestlet(){
		
		
		return new Directory(context, ROOT_URI) ;
	}
	

}
