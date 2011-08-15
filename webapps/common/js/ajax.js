
function ajax(targetURL, method){

	this.targetURL = targetURL;
	
	if (ajax.arguments.length == 1) {
		this.method = 'GET';
	} else if (ajax.arguments.length >= 2){
		this.method = method;
	} else if (ajax.arguments.length == 0){
		alert('URL is not setted')	;
	}

	this.userId = '' ;
	this.userPwd = '' ;
	this.queryString = '' ;
	

	this.execMethod = execMethod ;
	this.connect = connect ;
	
	this.addParam = addParam ;
	this.getParam = getParam ;
	this.clientBrowser = clientBrowser ;
	this.createHttpRequest = createHttpRequest ;
	this.sendRequest = sendRequest ;
	this.setLoginInfo = setLoginInfo ;
}


function execMethod(){

	// abstract.. 	
	alert('this method is abstract method...') ;
}

function connect(async){
	return this.sendRequest(this, this.execMethod, async, true) ;
}


function setLoginInfo(userId, userPwd){
	this.userId = userId ;
	this.userPwd = userPwd ;
}

function addParam(paramId, paramValue) {
	this.queryString += encodeURIComponent(paramId) + '=' + encodeURIComponent(paramValue) + '&' ;
}

function getParam(){
	return this.queryString;
}


function clientBrowser(){
	var a,ua = navigator.userAgent;
	this.bw= { 
	  safari    : ((a=ua.split('AppleWebKit/')[1])?a.split('(')[0]:0)>=124 ,
	  konqueror : ((a=ua.split('Konqueror/')[1])?a.split(';')[0]:0)>=3.3 ,
	  mozes     : ((a=ua.split('Gecko/')[1])?a.split(" ")[0]:0) >= 20011128 ,
	  opera     : (!!window.opera) && ((typeof XMLHttpRequest)=='function') ,
	  msie      : (!!window.ActiveXObject)?(!!createHttpRequest()):false 
	}
	return (this.bw.safari||this.bw.konqueror||this.bw.mozes||this.bw.opera||this.bw.msie)
}
	
function createHttpRequest(){
	if(window.ActiveXObject){
		 //Win e4,e5,e6?
		try {
			return new ActiveXObject("Msxml2.XMLHTTP") ;
		} catch (e) {
			try {
				return new ActiveXObject("Microsoft.XMLHTTP") ;
			} catch (e2) {
				return null ;
 			}
 		}
	} else if(window.XMLHttpRequest){
		return new XMLHttpRequest() ;
	} else {
		return null ;
	}
}


function sendRequest(parentThis, callback, async, sload){
	var oj = createHttpRequest();
	if( oj == null ) return null;
	
	var sload = (!!sendRequest.arguments[2]) ? sload:false;
	if(sload || this.method.toUpperCase() == 'GET') this.targetURL += "?";
	if(sload) this.targetURL = this.targetURL+"t="+(new Date()).getTime() + '&';
	
	var bwoj = new clientBrowser();
	var opera	  = bwoj.bw.opera;
	var safari	  = bwoj.bw.safari;
	var konqueror = bwoj.bw.konqueror;
	var mozes	  = bwoj.bw.mozes ;


	if(opera || safari || mozes){
		oj.onload = function () { callback(parentThis, oj); }
	} else {
	
		oj.onreadystatechange =function () 
		{
			if ( oj.readyState == 4 ){
				callback(parentThis, oj);
			}
		}
	}

	if(this.method.toUpperCase() == 'GET') {
		this.targetURL += this.getParam() ;
	}
	
	//open
	oj.open(this.method,this.targetURL,async,this.userId,this.userPwd);

	setEncHeader(oj)

	oj.send(this.method.toUpperCase() == 'GET'? null : this.getParam());

	function setEncHeader(oj){
		var contentTypeUrlenc = 'application/*+xml; charset=UTF-8';
		if(!window.opera){
			oj.setRequestHeader('Content-Type',contentTypeUrlenc);
		} else {
			if((typeof oj.setRequestHeader) == 'function')
				oj.setRequestHeader('Content-Type',contentTypeUrlenc);
		}	
		return oj
	}


	return oj
}

function uriEncode(data){
	if(data!=""){
		var encdata = '';
		var datas = data.split('&');
		for(i=1 ; i < datas.length ; i++)
		{
			var dataq = datas[i].split('=');
			encdata += '&'+encodeURIComponent(dataq[0])+'='+encodeURIComponent(dataq[1]);
		}
	} else {
		encdata = "";
	}
	return encdata;
}


