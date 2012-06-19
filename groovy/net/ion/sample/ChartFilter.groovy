package net.ion.sample

import net.ion.framework.parse.gson.JsonObject;

import java.util.*;
import java.util.Map.Entry;

import javax.swing.plaf.ListUI;

import org.restlet.data.MediaType;
import net.ion.radon.client.*;

import net.ion.radon.core.*;
import net.ion.radon.core.let.*;
import net.ion.radon.core.filter.*;
import org.restlet.*;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.rest.* ;
import net.ion.framework.util.* ;

import org.restlet.representation.*;

	
	if (true) return IFilterResult.CONTINUE_RESULT;

	Map jdata = JsonObject.fromString(response.getEntityAsText()).toMap() ; 
	Character[] mark = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray() ;
	int sum = 0 ;
	for (Entry<String, Integer> entry : jdata.entrySet()) {
		sum += entry.getValue() ;
	}

	List<Character> result = ListUtil.newList() ;
	List<String> label = ListUtil.newList() ;
	for (Entry<String, Integer> entry : jdata.entrySet()) {
		int data = entry.getValue() ;
		result.add( mark[ Math.min(Math.round(data / (sum * 1.0f /mark.length)) , 25).asType(Integer.class)  ]) ;
		label.add (entry.getKey()) ;
	}
	
	AradonClient client = AradonClientFactory.create("http://chart.apis.google.com") 
	IAradonRequest ir = client.createRequest("/chart") ;
	ir.addParameter("cht", "p3") ;
	ir.addParameter("chs", "500x250") ;
	ir.addParameter("chd", "s:" + StringUtil.join(result,"")) ;
	ir.addParameter("chl", StringUtil.join(label, "|")) ;
	
	Representation repr =  ir.get();
	
	
	response.setEntity(repr);
	// response.getEntity().setMediaType(MediaType.IMAGE_ALL) ;
	
	return IFilterResult.CONTINUE_RESULT;
