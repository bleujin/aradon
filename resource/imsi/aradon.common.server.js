// ***************************** common

// package....
var $net = {
		ion : {
			aradon : {sample:{}}, 
			common : {valid:{}},
			util : {}, 
			config : {mode:{}}, 
			lang :{}
		}
	} ;

Object.extend = function(destination, source) {
  for (property in source) {
    destination[property] = source[property];
  }
  return destination;
}



function $I() {
	var elements = new Array();
	
	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		var elementId = arguments[i];
		if (typeof element == 'string') {
			element = document.getElementById(element);
		}
	
		if (arguments.length == 1) 
			return element;
	
		elements.push(element);
	}
	
	return elements;
}

function $N() {
	var elements = new Array();
	
	for (var i = 0; i < arguments.length; i++) {
		var element = arguments[i];
		if (typeof element == 'string')
			element = document.getElementsByName(element);
		
		if (arguments.length == 1) 
			return element;
		
		elements.push(element);
	}
	
	return elements;
}

$net.ion.common.Form = {
	
	findByName : function(frmId, name){
		if ($net.ion.common.Form.findByName.arguments.length == 1) {
			return $('form').find("[name='" + frmId + "']") ;
		} else {
			return $('#' + frmId).find("[name='" + name + "']") ;
		}
	}, 
	
	getValue : function(frmId, name) {
		if ($net.ion.common.Form.getValue.arguments.length == 1) {
			return $net.ion.common.Form.findByName(frmId).val() ;
		} else {
			return $net.ion.common.Form.findByName(frmId, name).val() ;
		}
		// return 'abcd' ;
	}
}

var $F = (function() {
	return $net.ion.common.Form.getValue ;
}
)() ;

var $FE = (function() {
	return $net.ion.common.Form.findByName ;
}
)() ; 



$net.ion.common.Browser = function (){
	var agent = navigator.userAgent.toLowerCase();
	this.major = parseInt(navigator.appVersion);
	this.minor = parseFloat(navigator.appVersion);

	this.ns  = ((agent.indexOf('mozilla')!=-1) && ((agent.indexOf('spoofer')==-1) && (agent.indexOf('compatible') == -1)));
	this.ns2 = (this.ns && (this.major == 2));
	this.ns3 = (this.ns && (this.major == 3));
	this.ns4b = (this.ns && (this.major == 4) && (this.minor <= 4.03));
	this.ns4 = (this.ns && (this.major >= 4));

	this.ie   = (agent.indexOf("msie") != -1);
	this.ie3  = (this.ie && (this.major < 4));
	this.ie4  = (this.ie && (this.major < 5));
	this.ie5  = (this.ie && (this.major < 5.5));
	this.ie55  = (this.ie && (this.major >= 5.5));

	this.op3 = (agent.indexOf("opera") != -1);
} ;



$net.ion.common.DivUtil = function(){
	
	this.getTextById = function(eId, index){
		isArray = this.getTextById.arguments.length == 1 ? false : true ;
		if (isArray) return this.getTextByElement($i(eId)[index]) ;
		else return this.getTextByElement($i(eId)) ;
	} ;
	
	this.getTextByName = function(eId, index){
		return this.getTextByElement($N(eId)[index]) ;
	} ;
	
	this.getTextByElement = function(element){
		if(Common.Browser.ie)
			return element.innerText ;
		else 
			return element.textContent ;
	} ;
	
	this.setTextById = function(eId, value){
		return this.setTextByElement($i(eId), value) ;
	} ;
	
	this.setTextByName = function(eId, index, value){
		return this.setTextByElement($N(eId)[index], value) ;
	} ;
	
	this.setTextByElement = function(element, value){
		if(Common.Browser.ie)
			return element.innerText = value ;
		else 
			return element.textContent = value ;
	
	} ;
	
	this.getHTMLByName = function(eId, index){
		return this.getHTMLByElement($N(eId)[index]) ;
	} ;
		
	this.getHTMLByElement = function(element){
		return element.innerHTML ;
	} ;
		
	this.setHTMLById = function(eId, value){
		return $i(eId).innerHTML = value ;
	} ;
	this.setHTMLByName = function(eId, index, value){
		return $N(eId)[index].innerHTML = value ;
	} ;
}



$net.ion.config.mode.Debug = false ;



//--------------------------------------------------------------------- START COMMON FORM LINK


$net.ion.common.Link = function() {

	// get common obj
  	this.getSelfForm = function() {
  		return document.forms[0] ;
  	} ;

	this.getParentWindow = function(){
		try {
			if (opener == '[object]' || opener == '[object Window]' || opener == '[object global]'  || opener == '[object DOMWindow]'){
				return opener ;
			} else {
				if(window.dialogArguments) {
					return window.dialogArguments ;
				} else if(parent){
					return parent ;
				}
			}
		} catch(ex){
			alert('Not Exist Parent Document') ;
		}
	} ;

	this.getParentDocument = function(){
		try {
			if (opener == '[object]' || opener == '[object Window]' || opener == '[object global]' || opener == '[object DOMWindow]'){
				return opener.document ;
			} else {
				if(window.dialogArguments) {
					return window.dialogArguments.document ;
				} else if(parent){
					return parent.document ;
				}
			}
		} catch(ex){
			alert('Not Exist Parent Document') ;
		}
	} ;

	this.getParentForm = function(){
		try {
			if (opener == '[object]' || opener == '[object Window]' || opener == '[object global]' || opener == '[object DOMWindow]'){
				return opener.link.getSelfForm() ;
			} else {
				if(window.dialogArguments) {
					return window.dialogArguments.link.getSelfForm() ;
				} else if(parent){
					return parent.link.getSelfForm() ;
				}
			}
		} catch(ex){
			alert('Not Exist Parent Form') ;
		}
	} ;

	this.closeWindow = function closeWindow(){
		try {
			parent.iframeWin.close();
		} catch(ex){
			self.close() ;
		}
	} ;

	this.resetForm = function resetForm(){
		this.getSelfForm().reset() ;
	} ;

	this.refreshDocument = function refreshDocument(){
		//self.location='http://www.naver.com';
		document.location.href=document.location.href;
	} ;
 
	this.paramSerialize = function paramSerialize(){
		var result = '' ;
		for (i=0, last=this.getSelfForm().length ; i < last ; i++){
			result += this.getSelfForm()[i].name + '=' + this.getSelfForm()[i].value + ',' ;
		}
		return result ;
	} ;

	// common util method
	this.copyClip = function(value){
		if (window.clipboardData) {
			window.clipboardData.setData("Text", value);
		}
//		beforeValue = $("tempval").innerText ;
//		$("tempval").innerText = value ;
//		var doc = document.body.createTextRange();
//		doc.moveToElementText(tempval);
//		doc.execCommand('copy');
//		$("tempval").innerText = beforeValue ;
	} ;

	this.selfSubmit = function(){
		this.getSelfForm().submit() ;
	} ;

	this.getElement = function(fieldName, idx) {
		if (this.getElementSize(fieldName) == 1) {
			return this.getFormFieldByName(fieldName)[0] ;
		} else {
			return this.getFormFieldByName(fieldName)[idx] ;
		}
	} ;

	this.getElementSize = function(fieldName) {
		try{
			if (typeof this.getFormFieldByName(fieldName).length == 'undefined' || this.getFormFieldByName(fieldName).type == 'select-one'){
				return 1 ;  // should be not 0
			} else {
				return this.getFormFieldByName(fieldName).length ;
			}
		} catch(ex){
			// alert('not found : ' + fieldName + ', ' +  ex)	;
		}
	} ;
	
	this.getFormFieldByName = function(fieldName) {
		//return this.getSelfForm()[fieldName];
		return $N(fieldName);
	} ;

	this.getSelectElementValue = function(fieldName, defaultValue){
		for(i=0; i < this.getFormFieldByName(fieldName).length; i++){
			for(j=0; j < this.getFormFieldByName(fieldName)[i].options.length; j++){
				if (this.getFormFieldByName(fieldName)[i].options[j].selected == true){
					return this.getFormFieldByName(fieldName)[i].options[j].value ;
				}
			}
		}
		return defaultValue ;
	} ;

	this.getRadioElementValue = function(fieldName, defaultValue){
		for(i=0; i < this.getFormFieldByName(fieldName).length; i++){
			if (this.getFormFieldByName(fieldName)[i].checked == true){
				return this.getFormFieldByName(fieldName)[i].value ;
			}
		}
		return defaultValue ;
	} ;

	// common go list page
	this.goListPage = function goListPage(pageno, forwardName){
		if (this.goListPage.arguments.length >= 2) { // not defined forwardName
			this.getSelfForm().forwardName.value = forwardName ;
		} 
		if (this.goListPage.arguments.length >= 1) { // not defined pageno
			this.getSelfForm().pageNo.value = pageno ;
		}
		this.getSelfForm().write.value = 'false' ;
		this.getSelfForm().submit() ;
	} ;

	this.goAbsoluteListPage = function goAbsoluteListPage(pageno){// use this for [from search to list]
		try {
			this.getSelfForm().searchKey.value = '';
		} catch(ex) {}
		
		this.goListPage(pageno) ;
	} ;

	this.goFirstListPage = function goFirstListPage(){
		this.goAbsoluteListPage(1);
	} ;

	this.goPreListPage = function goPreListPage(){
		this.getSelfForm().write.value = 'false' ;
		this.getSelfForm().forwardName.value = 'list' ;
		this.getSelfForm().submit() ;
	} ;

	this.goNextScreen = function goNextScreen(pageno, forwardName){
		if (this.goNextScreen.arguments.length >= 2) { // not defined forwardName
			this.goListPage(pageno, forwardName) ;
		} else {
			this.goListPage(pageno) ;
		}
	} ;

	this.goPreScreen = function goPreScreen(pageno, forwardName){
		if (this.goPreScreen.arguments.length >= 2) { // not defined forwardName
			this.goListPage(pageno, forwardName) ;
		} else {
			this.goListPage(pageno) ;
		}
	} ;

	this.changeListNum = function changeListNum(){
		this.goChangeListNum(this.getSelfForm().listNum, this.getSelfForm().listNum.value) ;
	} ;

	this.goChangeListNum = function goChangeListNum(textbox, listnum) {
		 if ( (! listnum.toString().isNumeric()) || (listnum - 0 < 1)) { // include common_lang
		 	this.getSelfForm().listNum.value = '10' ;
		} 
		this.goListPage(1);
	} ;


	// common go page
	this.goViewPage = function(formId, idValue){
		this.goPage('view', formId, idValue) ;
	} ;

	this.goInfoPage = function(formId, idValue){
		this.goPage('info', formId, idValue) ;
	} ;

	this.goSetPage = function(formId, idValue){
		this.goPage('set', formId, idValue) ;
	} ;

	this.goAddPage = function(formId, idValue){
		this.goPage('add', formId, idValue) ;
	} ;
	this.goEditPage = function(formId, idValue){
		this.goPage('edit', formId, idValue) ;
	} ;

	this.goDelPage = function(formId, idValue){
		this.goPage('del', formId, idValue) ;
	} ;

	this.goMovePage = function(formId, idValue){
		this.goPage('move', formId, idValue) ;
	} ;

	this.goCopyPage = function(formId, idValue){
		this.goPage('copy', formId, idValue) ;
	} ;

	this.goSearchPage = function(pageNo){
		if (this.validForm('search')){
			if (this.getSelfForm().forwardName.value == 'view' || this.getSelfForm().forwardName.value == 'edit' || this.getSelfForm().forwardName.value == 'add' || this.getSelfForm().forwardName.value == 'order')
				this.goListPage(pageNo, 'list');
			else this.goListPage(pageNo);
		}
	} ;

	// use this method as private method
	this.goPage = function(pageForward, formId, idValue){
		if (this.goPage.arguments.length == 3 && ! this.isBlank(formId)) { // if needed id
			try {
				this.getSelfForm()[formId].value = idValue ;
			} catch(ex) {
				alert('Not Found Form Element -' + formId + '[' + ex + ']') ;
			}
		}

		this.getSelfForm().forwardName.value = pageForward ;
		this.getSelfForm().write.value = 'false' ;
		this.getSelfForm().submit() ;
	} ;

	this.openPage = function(pageForward, formId, idValue){
		if (this.openPage.arguments.length == 3 && ! this.isBlank(formId)) { // if needed id
			try {
				this.getSelfForm()[formId].value = idValue ;
			} catch(ex) {
				alert('Not Found Form Element -' + formId + '[' + ex + ']') ;
			}
		}
		// toolbar=no,alwaysRaised=yes,location=no,status=no,menubar=no,scrollbars=no,width=268,height=428,resizable=no,hotkey=no,dependent=yes,screenX=100,screenY=100
		wmap = window.open('', '__' + pageForward, 'toolbar=no,alwaysRaised=yes,location=no,status=no,menubar=no,scrollbars=no,width=640,height=480,resizable=no,hotkey=no,dependent=yes,screenX=100,screenY=100');

		this.getSelfForm().forwardName.value = pageForward ;
		this.getSelfForm().write.value = 'false' ;
		this.getSelfForm().target = '__' + pageForward ;
		this.getSelfForm().submit() ;
		this.getSelfForm().taget = "_self" ;
	} ;


	this.popupViewPage = function(urlPath, width, heigh, formId, idValue){
		this.popupPage(urlPath, 'view', width, heigh, formId, idValue) ;
	} ;

	this.popupInfoPage = function(urlPath, width, heigh, formId, idValue){
		this.popupPage(urlPath, 'info', width, heigh, formId, idValue) ;
	} ;

	this.popupAddPage = function(urlPath, width, heigh, formId, idValue){
		this.popupPage(urlPath, 'add', width, heigh, formId, idValue) ;
	} ;
	this.popupEditPage = function(urlPath, width, heigh, formId, idValue){
		this.popupPage(urlPath, 'edit', width, heigh, formId, idValue) ;
	} ;

	this.popupDefaultPage = function(urlPath, width, heigh, formId, idValue){
		this.popupPage(urlPath, '', width, heigh, formId, idValue) ;
	} ;

	// common popup page.. must include dialog.js
	// use this method as private method
	this.popupPage = function(urlPath, popupForward, width, heigh, formId, idValue){
		_urlPath = this.isBlank(urlPath) ? this.getSelfForm().action : urlPath ;
		_popupForward = this.isBlank(popupForward) ? '' : 'forwardName=' + popupForward ;
		_width = this.isBlank(width) ? 600 : width ;
		_height = this.isBlank(heigh) ? 400 : heigh ;
		_idPath = this.isBlank(formId) ? '' : formId + '=' + idValue ;
		ModalDialog_Env(_urlPath + '?' + _popupForward + '&' + _idPath, window, _width, _height, 'No', 'Yes', 'No' );
	} ;




	// common exec
	this.viewObj = function(formId, idValue){
		this.actionObj('view', formId, idValue) ;
	} ;

	this.setObj = function(formId, idValue){
		this.actionObj('set', formId, idValue) ;
	} ;

	this.addObj = function(formId, idValue){
		this.actionObj('add', formId, idValue) ;
	} ;
	this.editObj = function(formId, idValue){
		this.actionObj('edit', formId, idValue) ;
	} ;
	this.delObj = function(formId, idValue){
		this.actionObj('del', formId, idValue) ;
	} ;
	this.moveObj = function(formId, idValue){
		this.actionObj('move', formId, idValue) ;
	} ;
	this.copyObj = function(formId, idValue){
		this.actionObj('copy', formId, idValue) ;
	} ;
	
	
	// use this method as private method
	this.actionObj = function actionObj(actionForward, formId, idValue){
		if (this.actionObj.arguments.length == 3 && ! this.isBlank(formId)) { // if needed id
			try {
				this.getSelfForm()[formId].value = idValue ;
			} catch(ex) {
				alert('Not Found Form Element -' + formId + '[' + ex + ']') ;
			}
		}
		
		if (this.extendValidChecker()){
			if (this.validForm(actionForward)){
				this.cutEvent();  // actionObj is only One Click
				this.getSelfForm().forwardName.value = actionForward ;
				this.getSelfForm().write.value = 'true' ;
				this.getSelfForm().submit() ;
			}
		}
	} ;

	this.cutEvent = function cutEvent(){
		document.onmousedown = nullClick ;
		document.onclick = nullClick ;
		document.onmouseover = nullClick ;
	} ;

	nullClick = function nullClick(evt){
		if (isIE()) evt = window.event;
		if (isIE())
			evt.returnValue=false;
		else {
			evt.preventDefault();
		}
	} ;

	this.validForm = function validForm(actionForward){

		// confirm exist fvalid...
		try{
			instanceOf(fvalid, FormValid) ; // no action..
		}catch(ex){
			return true; // if not exist fvalid, always true..
		}

		if(instanceOf(fvalid, FormValid) && fvalid.size() > 0 ){
			return fvalid.validate(actionForward) ;
		}

		return true ;

	} ;

	this.isBlank = function isBlank(obj){
		return obj == null || obj == 'undefined' || obj.length == '' ;
	} ;


	// common utility function.. 
	this.getFormValue = function getFormValue(elementId, repValue){
		try{
			return this.getSelfForm()[elementId].value ;
		} catch(ex) {
			return repValue ;	
		}
	} ;

	this.setFormValue = function setFormValue(elementId, repValue){
		try{
			this.getSelfForm()[elementId].value = repValue;
		} catch(ex) {
		}
	}	 ;
	
	this.move_step = function move_step( selectObj, dir, validateName ){
		if (fvalid.validate(validateName)) {
			el = selectObj.selectedIndex ;
			if (el != -1 && el != null) {
				o_text = selectObj.options[el].text;
				o_value = selectObj.options[el].value;
				if (el > 0 && dir == -1) {
					selectObj.options[el].text = selectObj.options[el-1].text;
					selectObj.options[el].value = selectObj.options[el-1].value;
					selectObj.options[el-1].text = o_text;
					selectObj.options[el-1].value = o_value;
					selectObj.selectedIndex--;
				} else if (el > 0 && dir == -2) {
					for ( kk = el-1 ; kk >= 0 ; kk-- ) {
						temp_text  = selectObj.options[kk].text ;
						temp_value = selectObj.options[kk].value ;
						selectObj.options[kk+1].text = temp_text;
						selectObj.options[kk+1].value = temp_value;
					}
					selectObj.options[0].text = o_text;
					selectObj.options[0].value = o_value;
					selectObj.selectedIndex=0 ;
	
				} else if (el < selectObj.length-1 && dir == 1) {
					selectObj.options[el].text = selectObj.options[el+1].text;
					selectObj.options[el].value = selectObj.options[el+1].value;
					selectObj.options[el+1].text = o_text;
					selectObj.options[el+1].value = o_value;
					selectObj.selectedIndex++;
				} else if (el < selectObj.length-1 && dir == 2) {
					for ( kk=el ; kk < selectObj.length-1 ; kk++ ) {
						temp_text  = selectObj.options[kk+1].text ;
						temp_value = selectObj.options[kk+1].value ;
						selectObj.options[kk].text = temp_text;
						selectObj.options[kk].value = temp_value;
					}
					selectObj.options[selectObj.length-1].text = o_text;
					selectObj.options[selectObj.length-1].value = o_value;
					selectObj.selectedIndex=selectObj.length-1 ;
				}
			} 
		}
	} ;	 
	
	this.markAllSelectObj = function markAllSelectObj(selectObj){
		for(i=0 ; i < selectObj.length ; i++){
			selectObj[i].selected = true ;
		}
	} ;
	
	this.markAllDeSelectObj = function markAllDeSelectObj(selectObj){
		for(i=0 ; i < selectObj.length ; i++){
			selectObj[i].selected = false ;
		}
	} ;
	this.getWindowWidth = function(defaultWidth){
		var width = defaultWidth;
		if (parseInt(navigator.appVersion) > 3) {
			if (navigator.appName=="Netscape") {
				width = window.innerWidth;
			}
			if (navigator.appName.indexOf("Microsoft")!=-1) {
				width = document.body.offsetWidth;
			}
		}
		return width;
	} ;

	this.getWindowHeight = function(defaultHeight){
		var height = defaultHeight;
		if (parseInt(navigator.appVersion) > 3) {
			if (navigator.appName=="Netscape") {
				height = window.innerHeight;
			}
			if (navigator.appName.indexOf("Microsoft")!=-1) {
				height = document.body.offsetHeight;
			}
		}
		return height;
	} ;
} ;


//--------------------------------------------------------------------- 

(function(){
  var alertFun = window.alert, c=0;
  window.alert=function(q){
    
    if (++c%10==0) { // Change the number below to the number of alert boxes to display before the warning is given.
      if (!confirm('\nThere have been '+c+' alert boxes, continue displaying them?')) window.alert=function(){};
    } else {
    	alertFun ((arguments.length && arguments.length >1) ? Array.prototype.join.call(arguments, ', ')  : arguments[0]);
    }
  } ;
})();


(function() {
	var initFun = window.onload ;
	
	window.onload = function() {
		initFun ;
	} ;
	
})() ;







// ***************************** commmon_lang

/*

Object.prototype.implementsProp=function(propName){
  return (this[propName]!=null);
} ;
Object.prototype.implementsFunc=function(funcName){
  return this[funcName] && this[funcName] instanceof Function;
} ;
Object.prototype.simpleXmlify=function(tagname){
  var xml="<"+tagname;
  for (i in this){
    if (!this[i] instanceof Function){
      xml+=" "+i+"=\""+this[i]+"\"";
    }
  }
  xml+="/>";
  return xml;
} ;
*/

// in explorer, Object can not override
/*
Object.prototype.isArray = function() {
	return (this && this[0]) ? true : false;
};

Object.prototype.toJsonString = function(level, depth) {
  	return ObjectUtil.toDescString(this, level, depth) ;
};
*/


// extend string
String.prototype.isRequired = function (){
	return (this.trim() != '' && escape(this).replace(/%u3000/g, "").replace(/%20/g, "").length != 0 );
};
String.prototype.isNotRequired = function (){
	return ! this.isRequired();
};
String.prototype.isEmpty = function (){
	return this == '' ;
};
String.prototype.isBlank = function (){
	return this.trim() == '' ;
};
String.prototype.isNotBlank = function (){
	return ! this.isBlank() ;
};
String.prototype.isNotEmpty = function (){
	return ! this.isEmpty() ;
};

String.prototype.trim = function() {
    return this.replace(/^\s*|\s*$/g, "");
};

String.prototype.ltrim = function() {
    return this.replace(/^\s*/g, "");
};

String.prototype.rtrim = function() {
    return this.replace(/\s*$/g, "");
};

String.prototype.toNumber = function(){
	return this - 0 ;
} ;

String.prototype.defaultString = function(defaultStr){
	return this.isBlank() ? defaultStr : this.str ;
} ;

String.prototype.equalsIgnoreCase = function(_that){
	return this.toLowerCase() == _that.toLowerCase() ;
} ;

String.prototype.substringBefore = function (separator){
	strArray = this.split(separator) ;
	return strArray[0] ;
} ;
String.prototype.substringAfter = function (separator){
	strArray = this.split(separator) ;
	return this.substr(strArray[0].length + separator.length) ;
} ;

String.prototype.substringBeforeLast = function(separator){
	strArray = this.split(separator) ;
	var result = '' ;
	for(i=0; i<strArray.length-1 ; i++){
		result += (strArray[i] + separator) ;
	}
	return result.substring(0, (result.length - separator.length))  ;
};
String.prototype.substringAfterLast = function (separator){
	strArray = this.split(separator) ;
	return strArray[strArray.length - 1] ;
};
String.prototype.substringBetween = function(beginStr, endStr) {
	beginLoc = this.indexOf(beginStr) ;
	return this.substring(beginLoc + beginStr.length, this.indexOf(endStr, beginLoc + beginStr.length)) ;
} ;

String.prototype.substringBetweenLast = function(beginStr, endStr) {
	beginLoc = this.lastIndexOf(beginStr) ;
	return this.substring(beginLoc + beginStr.length, this.indexOf(endStr, beginLoc + beginStr.length)) ;
} ;

String.prototype.repeat = function(num){
	result = '' ;
	for (i=0 ; i < num ; i++){
		result += this ;
	}
	return result ;
} ;

String.prototype.replaceString = function(_this, _that) { 
	return this.split(_this).join(_that); 
} ;

String.prototype.lpad = function (str, num){
	return '' + str.repeat(num -this.length) + this ;
} ;

String.prototype.stripTag = function () { 
      return this.replaceString('<', '&lt;').replace('>', '&gt;'); 
} ;

// use in utf-8 mode
String.prototype.getByteLength = function (){
	count = 0;

	for(z=0; this != '' && z < this.length; z++){
		var c = this.charCodeAt(z);
		if (c <= 0x0000007F){ // between 0 and 127=> 1byte
			count += 1 ;
		} else if (c <= 0x000007FF){ // between 127 and 2047 => 2byte
			count+= 2;
		} else if (c <= 0x0000FFFF) { // char count increase 3 because common CJK char is 3 bytes
			count+=3 ;
		} else if (c <= 0x001FFFFF) {
			count+=4 ;
		} else if (c <= 0x03FFFFFF) {
			count+=5 ;
		} else {
			count+=6 ;
		}
	}
	return count;
} ;

String.prototype.isRangeByteLength = function (_minLength, _maxLength){
	return this.getByteLength(this) >= _minLength &&  this.getByteLength(this) <= _maxLength ;
};

String.prototype.countMatches = function(sub) {
	if (this.isEmpty() || sub.isEmpty()) {
		return 0;
	}
	return this.split(sub).length -1 ;
};

// validation.....
String.prototype.isAlpha = function() {
    return (/^[a-z]+$/i.test(this));
};
String.prototype.isDigit = function() {
    return (/^\d$/.test(this));
};
String.prototype.isNumeric = function() { // include negative number..
    if (this.length > 0 && this.charAt(0) == "-") { 
    	return (/^\d+$/.test(this.substring(1)));
	}
    return (/^\d+$/.test(this));
};
String.prototype.isPositiveNumber = function() { // include negative number..
    return (/^\d+$/.test(this));
};
String.prototype.isAlphaNumUnderBar = function(){
	return (/^[a-zA-Z0-9_]+$/.test(this));
} ;
String.prototype.isAlphaNumUnderBarHyphen = function(){
	return (/^[a-zA-Z0-9_\-]+$/.test(this));
} ;
String.prototype.isNamoUploadFileNames = function(){  // namo upload file name...
	if(this.length > 0 ){
		for(var i=0; i<this.length; i++){
			var cdAt = this.charCodeAt(i);
			if (cdAt > 128 ){
				return false;
			}
		}
	}
	if(this.length > 0 ){
		return (/^[a-zA-Z0-9_()\-. \[\]\^\@\{\}\~\!\$\`\'\;\=]+$/.test(this));
	}
	
	return true;
	//return (/^[a-zA-Z0-9_ ()\-]+$/.test(this));
} ;

String.prototype.isUploadFileNames = function(){ // input file name 
	if(this.length > 0 ){
		return (/^[a-zA-Z0-9_()\-.]+$/.test(this));
	}
	return true;
} ;

String.prototype.startWith = function(str){
	return this.substr(0, str.length) == str ;
} ;

String.prototype.isDateFormat = function(){
	if(this == "") return true;
	
	if(!(/[0-9]{8}\-[0-9]{6}$/.test(this))){
		return false;
	}

	var currDay = new Date(this.substring(0,4), this.substring(4,6)-1, this.substring(6,8)) ;

	if (this.indexOf("-")> 0){
		currDay.setHours(this.substring(9,11));
		currDay.setMinutes(this.substring(11,13));
		currDay.setSeconds(this.substring(13,15));
	}

	var strDay = "" ;

	strDay += currDay.getFullYear();
	strDay += (currDay.getMonth()+1).lpad(2) ;
	strDay += currDay.getDate().lpad(2) ;

	if (this.indexOf("-") > 0){
		strDay += "-";
		strDay += currDay.getHours().lpad(2) ;
		strDay += currDay.getMinutes().lpad(2) ;
		strDay += currDay.getSeconds().lpad(2) ;
	}

	return strDay == this  ;
} ;

String.prototype.isFloatFormat = function() {
	return !(/[^0-9.]/g.test(this)) && this.split('.').length <= 2;
} ;

String.prototype.isExpectFileType = function(expectedFileType){
	if(this == '') return true ;
	if (expectedFileType.toLowerCase() == 'all') return true;
	return expectedFileType.equalsIgnoreCase(this.substringAfterLast('.')) ;
} ;
String.prototype.isImageFileType = function(){
	if(this == '') return true ;
	var imgExt = ',jpg,gif,rgb,pbm,pgm,ppm,tiff,tif,rast,xbm,jpeg,bmp,png,';
	if (imgExt.indexOf(',' + this.substringAfterLast('.').toLowerCase() + ',') >= 0) {
			return true ;
		} else return false ;
} ;
String.prototype.isNotIncludeFullWidthSpace = function(){
	for(var z=0;z< this.length;z++){
		var c = this.charAt(z);
		if ( escape( c ) == "%u3000" ) return false ;
	}
    return true;
} ;

/* yyyy-mm-dd */
String.prototype.isDateType = function() {
	var s = this ;
    if (!(/^\d{4,4}-\d{2,2}-\d{2,2}$/.test(s))) { return false; }
    var a = s.split("-");
    var d = new Date(a[0], Number(a[1])-1, a[2]);
    d = [d.getFullYear().toString(), (d.getMonth()+1).toString(), d.getDate().toString()];
    if (!d[0].length || !d[1].length || !d[2].length) { return false; }
    if (d[1].length == 1) { d[1] = "0"+d[1]; }
    if (d[2].length == 1) { d[2] = "0"+d[2]; }
    return a[0] == d[0] && a[1] == d[1] && a[2] == d[2];
} ;
/* hh:mm:ss */
String.prototype.isHourType = function() {
	var s = this ;
    if (!(/^\d{2,2}:\d{2,2}:\d{2,2}$/.test(s))) { return false; }
    var a = s.split(":");
    a[0] = Number(a[0]);
    a[1] = Number(a[1]);
    a[2] = Number(a[2]);
    return a[0] >= 0 && a[0] <= 23 &&
        a[1] >= 0 && a[1] <= 59 &&
        a[2] >= 0 && a[2] <= 59;
} ;
/* yyyy-mm-dd hh:mm:ss */
String.prototype.isDateIso = function() {
	var s = this ;
    if (!(/^\d{4,4}-\d{2,2}-\d{2,2} \d{2,2}:\d{2,2}:\d{2,2}$/.test(s))) { return false; }
    var a = s.split(" ");
    return isDate(a[0]) && isHour(a[1]);
} ;

/* -0.01, 10, 10.45 - ok
   01, 00.1, .1, 0.0.0 - bad */
String.prototype.isDecimal = function() {
	var s = this
    if (s.length && s.charAt(0) == "-") { return s.substr(1).isDecimal(); }
    if (!(/^[\d.]+$/.test(s))) { return false; }
    if (s.indexOf(".") != -1 && (s.indexOf(".") != s.lastIndexOf("."))) { return false; }
    if (s.charAt(0) == ".") { return false; }
    if (s.length >= 2 && s.charAt(0) == "0" && s.charAt(1) != ".") { return false; }
    return !isNaN(s);
} ;
String.prototype.isEmail = function() {
	var s = this ;
    return (/^\w+@\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
} ;
/* isHttpAddress("gosu.pl") - true
   isHttpAddress("www.gosu.pl") - true
   isHttpAddress("www.gosu.pl", 1) - false
   isHttpAddress("https://gosu.pl", 1) - true */
String.prototype.isHttpAddress = function (full) {
	var s = this ;
    if (full) {
        return (/^http(s)?:\/\/(www\.)?\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
    } else {
        return (/^(http(s)?:\/\/)?(www\.)?\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
    }
} ;








// extend Number
Number.prototype.lpad = function(num){
	return this.toString().lpad('0', num) ;
} ;

Number.prototype.round = function round(n) {
    return this.toFixed(n);
} ;




// extend Date
$net.ion.lang.dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = $net.ion.lang.dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
$net.ion.lang.dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
$net.ion.lang.dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

Date.prototype.format = function (mask, utc) {
	return $net.ion.lang.dateFormat(this, mask, utc);
};


Date.prototype.defaultFormat = function() {
	return '' + this.getFullYear() + (this.getMonth() + 1).lpad(2) + this.getDate().lpad(2) + '-' 
			+ this.getHours().lpad(2) + this.getMinutes().lpad(2) + this.getSeconds().lpad(2);
} ;

Date.prototype.addDay = function(_yyyy, _mm, _dd, _hh, _mi, _ss){
	this.setFullYear(this.getFullYear() + _yyyy) ;
	this.setMonth((this.getMonth()-0) + _mm) ;
	this.setDate(this.getDate() + _dd) ;
	this.setHours(this.getHours() + _hh) ;
	this.setMinutes(this.getMinutes() + _mi) ;
	this.setSeconds(this.getSeconds() + _ss) ;
	
	return this ;
} ;

Date.prototype.addYear = function(num){
	return this.addDay(num, 0, 0, 0, 0, 0);
} ;
Date.prototype.addMonth = function(num){
	return this.addDay(0, num, 0, 0, 0, 0);
} ;
Date.prototype.addDate = function(num){
	return this.addDay(0, 0, num, 0, 0, 0);
} ;
Date.prototype.addHour = function(num){
	return this.addDay(0, 0, 0, num, 0, 0);
} ;
Date.prototype.addMinute = function(num){
	return this.addDay(0, 0, 0, 0, num, 0);
} ;
Date.prototype.addSecond = function(num){
	return this.addDay(0, 0, 0, 0, 0, num);
} ;

Date.prototype.getStrYear = function(){
	return this.getFullYear().lpad(4) ;
} ;
Date.prototype.getStrMonth = function(){
	return (this.getMonth() + 1).lpad(2) ;
} ;
Date.prototype.getStrDate = function(){
	return this.getDate().lpad(2) ;
} ;
Date.prototype.getStrHour = function(){
	return this.getHours().lpad(2) ;
} ;
Date.prototype.getStrMinute = function(){
	return this.getMinutes().lpad(2) ;
} ;
Date.prototype.getStrSecond = function(){
	return this.getSeconds().lpad(2) ;
} ;

Date.prototype.equalDate = function(_that){
	return this.format() == _that.format() ;
} ;

Date.prototype.isToday = function(){
	return this.equalDate(new Date()) ;
} ;

Date.prototype.getLastDate = function(){
	return (new Date(this.getFullYear(), this.getMonth() + 1, 0)).getStrDate();
} ;



// extend array
Array.prototype.indexOf=function(obj){
  var result=-1;
  for (var i=0;i<this.length;i++){
    if (this[i]==obj){
      result=i;
      break;
    }
  }
  return result;
} ;

Array.prototype.contains = function(obj) {
    return (this.indexOf(obj)>=0);
};

Array.prototype.clear=function(){
  this.length=0;
} ;

Array.prototype.equals = function(a) {
    if (this.length != a.length) { return false; }
    for (var i = 0; i < this.length; ++i) {
        if (this[i] !== a[i]) { return false; }
    }
    return true;
};

Array.prototype.indexOf = function(item) {
    for (var i = 0; i < this.length; ++i) {
        if (this[i] === item) { return i; }
    }
    return -1;
};

Array.prototype.getLast = function() {
    return this[this.length-1];
};

Array.prototype.filter = function(func) {
    var i, indexes = [];
    for (i = 0; i < this.length; ++i) {
        if (!func(this[i])) { indexes.push(i); }
    }
    for (i = indexes.length - 1; i >= 0; --i) {
        this.splice(indexes[i], 1);
    }
};

Array.prototype.map = function(func) {
    for (var i = 0; i < this.length; ++i) {
        this[i] = func(this[i]);
    }
};

Array.prototype.insertAt = function(el, index) {
    this.splice(index, 0, el);
};

Array.prototype.removeAt = function(index) {
    this.splice(index, 1);
};

Array.prototype.remove=function(obj){
	var index=this.indexOf(obj);
	if (index>=0){
		this.removeAt(index);
	}
};
 
Array.prototype.removeDuplicates = function() {
    var i, unique = [], indexes = [];
    for (i = 0; i < this.length; ++i) {
        if (unique.indexOf(this[i]) == -1) { unique.push(this[i]); }
        else { indexes.push(i); }
    }
    for (i = indexes.length - 1; i >= 0; --i) {
        this.splice(indexes[i], 1);
    }
};
 
Array.prototype.copy = function() {
    var copy = [];
    for (var i = 0; i < this.length; ++i) {
        copy[i] = (typeof this[i].copy != "undefined") ? this[i].copy() : this[i];
    }
    return copy;
};
 
Array.prototype.swap = function(index1, index2) {
    var temp = this[index1];
    this[index1] = this[index2];
    this[index2] = temp;
};

Array.prototype.shuffle = function() {
    for (var i = 0; i < this.length; ++i) {
        var ind1 = Math.floor(Math.random() * this.length);
        var ind2 = Math.floor(Math.random() * this.length);
        this.swap(ind1, ind2);
    }
};

Array.prototype.toString = function() {
	var result = '' ;
    for (var i = 0; i < this.length; ++i) {
        result += this[i] + ',';
    }
    return result ;
};



// +----------------------------------------------------------------+
// | Array functions that are missing in IE 5.0                     |
// | Author: Cezary Tomczak [www.gosu.pl]                           |
// | Free for any use as long as all copyright messages are intact. |
// +----------------------------------------------------------------+

if (!Array.prototype.pop) {
    Array.prototype.pop = function() {
        var last;
        if (this.length) {
            last = this[this.length - 1];
            this.length -= 1;
        }
        return last;
    };
}

if (!Array.prototype.push) {
    Array.prototype.push = function() {
        for (var i = 0; i < arguments.length; ++i) {
            this[this.length] = arguments[i];
        }
        return this.length;
    };
}

if (!Array.prototype.shift) {
    Array.prototype.shift = function() {
        var first;
        if (this.length) {
            first = this[0];
            for (var i = 0; i < this.length - 1; ++i) {
                this[i] = this[i + 1];
            }
            this.length -= 1;
        }
        return first;
    };
}

if (!Array.prototype.unshift) {
    Array.prototype.unshift = function() {
        if (arguments.length) {
            var i, len = arguments.length;
            for (i = this.length + len - 1; i >= len; --i) {
                this[i] = this[i - len];
            }
            for (i = 0; i < len; ++i) {
                this[i] = arguments[i];
            }
        }
        return this.length;
    };
}

if (!Array.prototype.splice) {
    Array.prototype.splice = function(index, howMany) {
        var elements = [], removed = [], i;
        for (i = 2; i < arguments.length; ++i) {
            elements.push(arguments[i]);
        }
        for (i = index; (i < index + howMany) && (i < this.length); ++i) {
            removed.push(this[i]);
        }
        for (i = index + howMany; i < this.length; ++i) {
            this[i - howMany] = this[i];
        }
        this.length -= removed.length;
        for (i = this.length + elements.length - 1; i >= index + elements.length; --i) {
            this[i] = this[i - elements.length];
        }
        for (i = 0; i < elements.length; ++i) {
            this[index + i] = elements[i];
        }
        return removed;
    };
}



Function.prototype.bindAsEventListener = function(object) {
  var __method = this;
  return function(event) {
    __method.call(object, event || window.event);
  } ;
} ;







function ObjectUtil() { }

ObjectUtil._isArray = function(obj) {
  return (obj && obj.join) ? true : false;
};

ObjectUtil._indent = function(level, depth) {
  var reply = "";
  if (level != 0) {
    for (var j = 0; j < depth; j++) {
      reply += "\u00A0\u00A0";
    }
    reply += " ";
  }
  return reply;
};


ObjectUtil._isHTMLElement = function(ele, nodeName) {
  if (ele == null || typeof ele != "object" || ele.nodeName == null) {
    return false;
  }
  if (nodeName != null) {
    var test = ele.nodeName.toLowerCase();
    if (typeof nodeName == "string") {
      return test == nodeName.toLowerCase();
    }
    if (ObjectUtil._isArray(nodeName)) {
      var match = false;
      for (var i = 0; i < nodeName.length && !match; i++) {
        if (test == nodeName[i].toLowerCase()) {
          match =  true;
        }
      }
      return match;
    }
    // alert("ObjectUtil._isHTMLElement was passed test node name that is neither a string or array of strings");
    return false;
  }
  return true;
};


ObjectUtil._isDate = function(data) {
  return (data && data.toUTCString) ? true : false;
};


ObjectUtil._detailedTypeOf = function(x) {
  var reply = typeof x;
  if (reply == "object") {
    reply = Object.prototype.toString.apply(x); // Returns "[object class]"
    reply = reply.substring(8, reply.length-1);  // Just get the class bit
  }
  return reply;
};

ObjectUtil.descString = function(elm) {
    if (elm.nodeType == 3) { // TEXT_NODE
        return elm.nodeValue;
    }
    var result = '';
    for (var i = 0, child; child = elm.childNodes[i]; i++) {
    	result += ObjectUtil.descString(child);
    }
    return result;
} ;

ObjectUtil.toDescString = function(data, level, depth) {
  var reply = "";
  var i = 0;
  var value;
  var obj;
  if (level == null) level = 0;
  if (depth == null) depth = 0;
  if (data == null) return "null";
  if (ObjectUtil._isArray(data)) {
    if (data.length == 0) reply += "[]";
    else {
      if (level != 0) reply += "[\n";
      else reply = "[";
      for (i = 0; i < data.length; i++) {
        try {
          obj = data[i];
          if (obj == null || typeof obj == "function") {
            continue;
          }
          else if (typeof obj == "object") {
            if (level > 0) value = ObjectUtil.toDescString(obj, level - 1, depth + 1);
            else value = ObjectUtil._detailedTypeOf(obj);
          }
          else {
            value = "" + obj;
            value = value.replace(/\/n/g, "\\n");
            value = value.replace(/\/t/g, "\\t");
          }
        }
        catch (ex) {
          value = "" + ex;
        }
       if (level != 0)  {
          reply += ObjectUtil._indent(level, depth + 2) + value + ", \n";
       }
        else {
          if (value.length > 13) value = value.substring(0, 10) + "...";
          reply += value + ", ";
          if (i > 5) {
            reply += "...";
            break;
          }
        }
      }
      if (level != 0) reply += ObjectUtil._indent(level, depth) + "]";
      else reply += "]";
    }
    return reply;
  }
  if (typeof data == "string" || typeof data == "number" || ObjectUtil._isDate(data)) {
    return data.toString();
  }
  if (typeof data == "object") {
    var typename = ObjectUtil._detailedTypeOf(data);
    if (typename != "Object")  reply = typename + " ";
    if (level != 0) reply += "{\n";
    else reply = "{";
    var isHtml = ObjectUtil._isHTMLElement(data);
    for (var prop in data) {
      if (isHtml) {
        // HTML nodes have far too much stuff. Chop out the constants
        if (prop.toUpperCase() == prop || prop == "title" ||
          prop == "lang" || prop == "dir" || prop == "className" ||
          prop == "form" || prop == "name" || prop == "prefix" ||
          prop == "namespaceURI" || prop == "nodeType" ||
          prop == "firstChild" || prop == "lastChild" ||
          prop.match(/^offset/)) {
          continue;
        }
      }
      value = "";
      try {
        obj = data[prop];
        if (obj == null || typeof obj == "function") {
          continue;
        }
        else if (typeof obj == "object") {
          if (level > 0) {
            value = "\n";
            value += ObjectUtil._indent(level, depth + 2);
            value = ObjectUtil.toDescString(obj, level - 1, depth + 1);
          }
          else {
            value = ObjectUtil._detailedTypeOf(obj);
          }
        }
        else {
          value = "" + obj;
          value = value.replace(/\/n/g, "\\n");
          value = value.replace(/\/t/g, "\\t");
        }
      }
      catch (ex) {
        value = "" + ex;
      }
      if (level == 0 && value.length > 13) value = value.substring(0, 10) + "...";
      var propStr = prop;
      if (propStr.length > 30) propStr = propStr.substring(0, 27) + "...";
      if (level != 0) reply += ObjectUtil._indent(level, depth + 1);
      reply += prop + ":" + value + ", ";
      if (level != 0) reply += "\n";
      i++;
      if (level == 0 && i > 5) {
        reply += "...";
        break;
      }
    }
    reply += ObjectUtil._indent(level, depth);
    reply += "}";
    return reply;
  }
  return data.toString();
};



$net.ion.exception = {} ;
$net.ion.event = {} ;


$net.ion.util.StringUtils = function(){

	this.trim = function (str) {
		re = /^\s+|\s+$/g;
		return str.replace(re, '');
	};
	this.ltrim = function (str) {
		re = /^\s+/g;
		return str.replace(re, '');
	};
	this.rtrim = function (str) {
		re = /\s+$/g;
		return str.replace(re, '');
	};
	this.isEmpty = function (str){
		return str == '' ;	
	};
	this.isNotEmpty = function (str){
		return ! this.isEmpty() ;	
	};
	this.isBlank = function (str){
		return this.trim(str) == '' ;
	};
	this.isNotBlank = function (str){
		return ! this.isBlank() ;
	};
	this.substringBefore = function (str, separator){
		strArray = str.split(separator) ;
		return strArray[0] ;
	}
	// this.substringAfter = substringAfter ;
	// this.substringBeforeLast = substringBeforeLast ;
	this.substringAfterLast = function (str, separator){
		strArray = str.split(separator) ;
		return strArray[strArray.length - 1] ;
	};
	// this.substringBetween = substringBetween ;

	this.isAlpha = function(str) {
	    return (/^[a-z]$/i.test(str));
	};
	
	/* Check if string is a digit */
	this.isDigit = function(str) {
	    return (/^\d$/.test(str));
	};
	
	/* Check if string is numeric */
	this.isNumeric = function(str) {
	    return (/^\d+$/.test(str));
	};
} 



$net.ion.util.AssertUtil = function(){
	this.assertInclude = function (object, jsFile) {
		if (object==null){
			throw new AssertException( "file " + jsFile + " was not included ");
		}
	};
	
	this.assertParamNotNull = function (value, paramName){
		if (value == null){
			throw new $net.ion.exception.IllegalArgumentException(	"Parameter must not be null: " + paramName );
		}
	};
	this.assertParamIsNotEmpty = function (value, paramName){
		this.assertParamNotNull(value, paramName);
		if (StringUtil.isEmpty(value)){
			throw new $net.ion.exception.IllegalArgumentException(	"Parameter must not be empty: " + paramName );
		}
	};
	
	this.assertIntegerParam = function (value, paramName){
		this.assertParamNotNull (value, paramName);
		var i = parseInt(value, 10);
		if ((i == null) || (isNaN(i))){
			throw new $net.ion.exception.IllegalArgumentException( "Invalid Integer Parameter: " + paramName);
		}
	};
	this.assertGreaterThanZeroParam = function (value, paramName){
		this.assertIntegerParam (value, paramName);
		var i = parseInt(value, 10);
		if (i <= 0){
			throw new $net.ion.exception.IllegalArgumentException( " assertGreaterThanZeroParam : " + paramName);
		}
	};
	
	this.assertInstance = function (param, clazz, clazzName){
		this.assertParamNotNull(param, "param");
		this.assertParamFalse(typeof(clazz) == "string", "Invalid clazz parameter");
		if (!(param instanceof clazz)){
			throw new $net.ion.exception.IllegalArgumentException( " Invalid parameter must be instance of " + clazzName);
		}
	};
	
	this.assertParamInstance = function (param, clazz, clazzName){
		if (!(param instanceof clazz)){
			throw new $net.ion.exception.IllegalArgumentException( " Invalid parameter must be instance of " + clazzName);
		}
	};
	this.assertResultNotNull = function (valueOfResult, message) {
		if ( valueOfResult == null ){
			throw new AssertException( "Invalid result value: " + message);
		}
	};
	
	this.assertParamFalse = function(booleanValue , message){
		if (booleanValue){
			throw new $net.ion.exception.IllegalArgumentException( "AssertParamFalse Exception message: " + message);
		}
	};
	this.assertMethodExists = function(object, functionName){
		this.assertParamNotNull( object, "object");
		if (object[functionName]==null){
			throw new AssertException(	" Object " + object + " does not have function :" + functionName );
		}
	};
	this.assertMemberState = function(member, memberName, clazz, clazzName, mandatory, valueRange){
		if (mandatory){
			if (member == null){
				throw new AssertException( " Member " + memberName + " must not be null ");
			}
			if (!(member instanceof clazz)){
				throw new AssertException( " Invalid state " + memberName + " must be instance of " + clazzName);
			}
		}
		else if (member != null){
			if (!(member instanceof clazz)){
				throw new AssertException( "Invalid state " + memberName + " must be instance of " + clazzName);
			}
		}
	};
	this.assertMemberStateString = function(member, memberName, mandatory, valueRange){
		if (mandatory){
			if (typeof(member) != typeof(" ")){
				throw new AssertException( new AssertException( "Invalid state " + memberName + 
												" must be instance of string and not " + typeof(member)) );
			}
			if (StringUtil.isEmpty(member)){
				throw new AssertException( "AssertException "	+ memberName + " must not be empty");
			}
		}
		else if (member != null){
			if (typeof(member) != typeof(" ")){
				throw new AssertException( "Invalid state " + memberName + " must be instance string " );
			}
		}
	};
}


$net.ion.util.Map = function(){
	
	var keys = new Array();
	var AssertUtil = new $net.ion.util.AssertUtil() ;

	this.contains = function(key){
		AssertUtil.assertParamNotNull(key, "key");
		var entry = findEntry(key);
		return !(entry == null || entry instanceof $net.ion.util.NullKey);
	};

	this.get = function(key) {
		var entry = findEntry(key);
	 	if ( !(entry == null || entry instanceof $net.ion.util.NullKey) )
			return entry.value;
		else
			return null;
	};

	this.put = function(key, value) {
		AssertUtil.assertParamNotNull(key, "Map.put.key");
		AssertUtil.assertParamNotNull(value, "Map.put.value");
		var entry = findEntry(key);
		if (entry){
			entry.value = value;
		} else {
			addNewEntry(key, value);
		}
	};
	
	this.clearMap = function(){
		keys = [] ;
	} ;

	this.remove = function (key){
		AssertUtil.assertParamNotNull(key, "key");
		for (var i=0;i<keys.length;i++){
			var entry = keys[i];
			if (entry instanceof $net.ion.util.NullKey) continue;
			if (entry.key == key){
					keys[i] = $net.ion.util.NullKey;
			}
		}				
	};
	function findEntry(key){
		for (var i=0;i<keys.length;i++){
			var entry = keys[i];
			if (entry instanceof $net.ion.util.NullKey) continue;
			if (entry.key == key){
				return entry
			}
		}
		return null;
	};
	function addNewEntry(key, value){
		var entry = new Object();
		entry.key = key;
		entry.value = value;
		keys[keys.length] = entry; 
	};
	this.size = function(){
		return keys.length ;
	};
	
	this.getEntry = function(idx){
		return keys[idx] ;
	};
	
	this.flat = function(){
		var result = {} ;
		for (var i=0;i<keys.length;i++){
			var entry = keys[i];
			if (entry instanceof $net.ion.util.NullKey) continue;
			result[entry.key] = entry.value ;
		}
		return result;
	} ;
	
	this.toJString = function(){
		return JSON.stringify(this.flat(), 5, 5) ;
	}
}
	
//replace the entries of map in key array, removing the former value;	
$net.ion.util.NullKey = function(){
}
// new $net.ion.util.NullKey();


$net.ion.util.ArrayList = function(){
	this.array = new Array();
	this.add = function(obj){
		this.array[this.array.length] = obj;
	}
	this.iterator = function (){
		return new Iterator(this)
	}
	this.size = function (){
		return this.array.length;
	}
	this.get = function (index){
		return this.array[index];
	}
	this.addAll = function (obj){
		if (obj instanceof Array){
			for (var i=0;i<obj.length;i++){
				this.add(obj[i]);
			}
		} else if (obj instanceof ArrayList){
			for (var i=0;i<obj.length();i++){
				this.add(obj.get(i));
			}
		}
	}
}

$net.ion.util.Iterator = function(arrayList){
	this.arrayList;
	this.index = 0;
	this.hasNext = function (){
		return this.index < this.arrayList.length();
	}
	this.next = function() {
		return this.arrayList.get(index++);
	}
}


$net.ion.util.Collection= function(){
	this.array = new Array();
	var AssertUtil = new $net.ion.util.ArrserUtil() ;
	
	this.item = function _getItem(id){
		AssertUtil.assertIntegerParam(id, "id");
		if (this.array.length <= id || id < 0 ){
			throw new IndexOutOfBoundsException(id, source);
		}
		return this.array[id];
	};
	this.add = function(item){
		AssertUtil.assertParamNotNull(item, "item must not be null");
		this.array[this.array.length] = item;
	};
	this.iterator = function () {
		return new Iterator(this);
	}
	this.length = function (){
		return this.array.length;
	} ;
}
	
$net.ion.util.Iterator = function(collection){
	this.index = 0;
	this.collection = collection; 
	this.next = function () {
		return this.collection.item(this.index++);
	}
	this.hasNext = function () {
		return this.index < this.collection.length();
	}
}



$net.ion.util.DomUtil = function(){

	this.getChildByName = function ( htmlElement, name, tagName ){
		var logger = LoggerFactory.getLogger( "dom.util" );
		var elements = htmlElement.getElementsByTagName( tagName );
		if ( elements == null && elements.length == 0 ){
			 throw new $net.ion.exception.IllegalArgumentException( tagName );
		}
		
		logger.trace( "DomUtil name " + name + " tag name " + tagName );
		for ( var i = 0;i < elements.length;i++ ){
			 var element = elements[i];
			 if ( element.name == name || element.id == name ){
				 return element ;
			 } else {
				 logger.trace("DomUtil name " + element.name + " id " + element.id);
			 }
		}
		
		throw new $net.ion.exception.IllegalArgumentException( "there is no child element with name" + name );
	} 
	
	this.getParentNode = function ( node, tagName ){
		while ( node.parentNode ){
			if ( node.parentNode.tagName.toLowerCase() == tagName.toLowerCase() ) {
				return node.parentNode;
		 	}
		 	node = node.parentNode;
		}
	}	

}





$net.ion.event.EventDispatcher = function(){
	var AssertUtil = new $net.ion.util.ArrserUtil() ;
	
	this.fireEvent = function(collection, methodName, parameter){
		AssertUtil.assertParamNotNull(collection,"collection");
		AssertUtil.assertParamInstance(collection,Collection);
		for (var iterator = collection.iterator();iterator.hasNext();){
			var listener = iterator.next();
			AssertUtil.assertMethodExists(listener, methodName);
			eval("listener." + methodName + "(parameter);");
		}
	}
}




$net.ion.event.EventWrapper = function(navEvent){
	var isIE = (navigator.appName.indexOf("Microsoft")!= -1);
	this.event =	isIE ? window.event : navEvent;
	this.getKey = function(){ return (isIE) ? this.event.keyCode : this.event.which; }
	this.getX = function() { return (isIE) ? this.event.x : this.event.clientX; }
	this.getY = function() { return (isIE) ? this.event.y : this.event.clientY; }
	this.hasAlt = function(){ return	this.event.altKey; }
	this.hasCtrl = function(){return	this.event.ctrlKey;}
	this.hasShift = function (){ return	this.event.shiftKey ; }
	this.getSource = function () { return (isIE) ? this.event.srcElement : this.event.target; }
	this.cancel = function(){
		//todo find a way to cancel in Navigator 
		this.event.returnValue = false;
		return false;
	}
	this.setEvent = function (navEvent) 
	{
		this.event = isIE ? window.event : navEvent;
	}
}

$net.ion.event.EventWrapperFactory = function(){}
new $net.ion.event.EventWrapperFactory();
$net.ion.event.EventWrapperFactory.instance = new $net.ion.event.EventWrapper(window.event);

$net.ion.event.EventWrapperFactory.getInstance = function (navEvent){
	$net.ion.event.EventWrapperFactory.instance.setEvent(navEvent);
	return $net.ion.event.EventWrapperFactory.instance;
}






$net.ion.exception.CustomError = function(message) {
	var error = new Error(message);
	var stackTrace = error.stack.split("@:0"); 
	this.toString = function(){
		return error.toString() + "\n\n<<<StackTrace>>\n\n" + this.printStackTrace();
	}
	this.printStackTrace = function(){
		var result = "";
		for (var i =0;i<stackTrace.length;i++){ result += "\t-" + stackTrace[i] + "\n"; }
		return result;
	}
}

$net.ion.exception.AssertException = function(message){
	this.base = CustomError;
	this.base("( AssertException ) " + message);
}
$net.ion.exception.AssertException.prototype = $net.ion.exception.CustomError;

$net.ion.exception.IllegalArgumentException = function(message) {
	this.base = $net.ion.exception.CustomError;
	this.base("( IllegalArgumentException ) " + message);
}
$net.ion.exception.IllegalArgumentException.prototype = $net.ion.exception.CustomError;

$net.ion.exception.IndexOutOfBoundsException = function(message, source){
	this.base = $net.ion.exception.CustomError;
	this.base("( IllegalArgumentException ) " + message);
}
$net.ion.exception.IndexOutOfBoundsException.prototype = $net.ion.exception.CustomError;







$net.ion.util.LoggerLevels = function(){
	this.TRACE_LEVEL = 0;
	this.DEBUG_LEVEL = 1;
	this.WARN_LEVEL = 2;
	this.INFO_LEVEL = 3;
	this.ERROR_LEVEL = 4;
}
 

$net.ion.util.LoggerFactory = function(){
	this.loggers = new Map();
	this.loggerLevel = $net.ion.util.LoggerLevels.TRACE_LEVEL;
	this.getLogger = function (typeName){
		if (!LoggerFactory.loggers.contains(typeName)){
			LoggerFactory.loggers.put(typeName, new Logger(LoggerFactory.loggerLevel, typeName));
		}
		return new LoggerFactory.loggers.get(typeName);
	}
	
	this.createElementLog = function(){
		try {
			 var elementLog = document.getElementById("logDiv");
			 if (elementLog==null){
		
				 //alert("createElementLog");			 
				 var newDiv = document.createElement("div");
				 newDiv.style.left	 = 10;
				 newDiv.style.top		= 10;
				 newDiv.style.heigth		= 100;
				 //newDiv.style.width		 = 100;
				 newDiv.style.backgroundColor = "#FF6347";
				 newDiv.style.position = "absolute";
				 newDiv.style.visibility = "visible";
				 //alert("Criou Div");
				 
				 var frame = document.createElement("table");
				 var firstRow = document.createElement("tr");
				 var firstRowCell = document.createElement("td");
				 firstRowCell.appendChild = document.createTextNode("X");
				 firstRowCell.height = "80%";
				 firstRowCell.width = "100%";
				 
				 var secondRow = document.createElement("tr");
				 var secondRowCell = document.createElement("td");
								
				 
				 var textAreaLogger = document.createElement("textarea");
				 textAreaLogger.id = "logDiv";
				 textAreaLogger.cols = 80;
				 textAreaLogger.rows = 10;
				 newDiv.appendChild(textAreaLogger);
				 document.body.appendChild(newDiv);
				 //alert("document.body.appendChild");
				 newDiv.style.visibility = "visible";
				 textAreaLogger.style.visibility = "visible";
				 //alert("textAreaLogger.style.visibility");
				 elementLog =	textAreaLogger;
			 }
			 return elementLog;
		}catch (err){
			 throw err;
		}
	} 
	
}
	

	
$net.ion.util.Logger = function(loggerLevel, className){
	var prefix = className;
	var level = loggerLevel;
	var elementLog = LoggerFactory.createElementLog()
	this.trace = function(message){
		print(LoggerLevels.TRACE_LEVEL, message);
	}
	this.debug = function(message){
		print(LoggerLevels.DEBUG_LEVEL, message);
	}
	this.info = function(message){
		print(LoggerLevels.INFO_LEVEL, message);
	}
	this.error = function(message, errorObj){
		if (errorObj != null){
			message = ("" | message ) + "\n\t stackTrace : \n" + errorObj.toString();
		}
		print(LoggerLevels.ERROR_LEVEL, message);
	}
	
	
	function print(logLevel, message){
		
		if (level >	logLevel) return;
		var s = "\r\n";
		s += prefix;
		if (logLevel == LoggerLevels.TRACE_LEVEL){s += " - TRACE - ";}
		if (logLevel == LoggerLevels.DEBUG_LEVEL){s += " - DEBUG - ";}
		if (logLevel == LoggerLevels.WARN_LEVEL) {s += " - WARN	- ";}
		if (logLevel == LoggerLevels.INFO_LEVEL) {s += " - INFO	- ";}
		if (logLevel == LoggerLevels.ERROR_LEVEL){s += " - ERROR - ";}
		s += " : "
		s += message;
		elementLog.value += s;
		/*var textNode = document.createTextNode(s);
		elementLog.appendChild(textNode);
		elementLog.visibility = "visible";
		*/
	}
}
	//all static members
	
	



// *************************** common_valid.js





// Validator is base class of all validators
$net.ion.common.valid.Vaildator = function(frm) {
	this.frm = frm ;
	this.elementList = new Array() ;

	this.getForm = function() {
		return (this.frm && true) ?  this.frm : document.forms[0];
	} ;

	this.setForm = function(frmObj) {
		this.frm = frmObj ;
	} ;

	this.getElement = function(fieldName, idx) {
		if (this.getElementSize(fieldName) == 1) {
			return this.getForm()[fieldName] ;
		} else {
			return this.getForm()[fieldName][idx] ;
		}
	} ;

	this.getElementSize = function(fieldName) {
		try{
			if (typeof this.getForm()[fieldName].length == 'undefined'){
				return 1 ;  // should be not 0
			} else if(this.getForm()[fieldName].nodeName == 'SELECT') {
				return 1;
			} else {
				return this.getForm()[fieldName].length ;
			}
		} catch(ex){
			alert('not found : ' + fieldName + ', ' +  ex)	;
		}
	} ;

	this.getRadioElementValue = function(fieldName, defaultValue){
		for(i=0; i < this.getForm()[fieldName].length; i++){
			if (this.getForm()[fieldName][i].checked == true){
				return this.getForm()[fieldName][i].value ;
			}
		}
		return defaultValue ;
	} ;

	this.getFieldName = function() {
		return this.fieldName;
	} ;
	this.getErrorMessage = function() {
		return this.errorMessage;
	} ;
	this.isFocus = function() {
		return this.focus;
	} ;

	this.elseAction = function(){
		if (this.isFocus() == true) this.getElement(this.fieldName, 0).focus();
		if (this.getErrorMessage() > '') alert(this.getErrorMessage());
		return false;
	} ;
	
	


}

$net.ion.common.valid.RequiredValidator = function (fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	
	this.validate = function() {
		for(var i=0, last=this.getElementSize(this.fieldName) ; i < last ; i++){
			if (this.getElement(this.fieldName, i).value.trim() != '') continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.RequiredValidator.prototype = new $net.ion.common.valid.Vaildator ;


$net.ion.common.valid.DateTypeValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(var i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			str = this.getElement(this.fieldName, i).value;
			if( str.isDateFormat()) continue ;
			else  return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.DateTypeValidator.prototype = new $net.ion.common.valid.Vaildator;



// max length validator
$net.ion.common.valid.MaxLengthValidator = function(fieldName, maxLength, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.maxLength = maxLength;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			if (this.getElement(this.fieldName, i).value.length <= this.maxLength) continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.MaxLengthValidator.prototype = new $net.ion.common.valid.Vaildator;


// max length(byte) validator
$net.ion.common.valid.MaxLengthByteValidator = function(fieldName, maxLength, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.maxLength = maxLength;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			str = this.getElement(this.fieldName, i).value;
			if ( (str.length+ ((escape(str)+"%u").match(/%u/g).length-1)*2) <= this.maxLength ) continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.MaxLengthByteValidator.prototype = new $net.ion.common.valid.Vaildator;


// min length validator
$net.ion.common.valid.MinLengthValidator = function(fieldName, minLength, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.minLength = minLength;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			if (this.getElement(this.fieldName, i).value.length >= this.minLength) continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.MinLengthValidator.prototype = new $net.ion.common.valid.Vaildator;

// min length(byte) validator
$net.ion.common.valid.MinLengthByteValidator = function(fieldName, minLength, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.minLength = minLength;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			str = this.getElement(this.fieldName, i).value;
			if ( (str.length+((escape(str)+"%u").match(/%u/g).length-1)*2) >= this.minLength ) continue ;
			else  return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.MinLengthByteValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.BetweenLengthByteValidator = function(fieldName, minLength, maxLength, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.minValidator = new MinLengthByteValidator(fieldName, minLength, errorMessage, focus);
	this.maxValidator = new MaxLengthByteValidator(fieldName, maxLength, errorMessage, focus);
	this.validate = function() {
		return this.minValidator.validate() && this.maxValidator.validate() ;
	} ;
}
$net.ion.common.valid.BetweenLengthByteValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.ExpressionValidator = function(fieldName, expression, errorMessage, focus){
	this.fieldName = fieldName;
	this.expression = expression;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		return eval(this.expression) ;
	} ;
}
$net.ion.common.valid.ExpressionValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.ConditionExpressionValidator = function(fieldName, condition, expression, errorMessage, focus){
	this.fieldName = fieldName;
	this.condition = condition ;
	this.expression = expression;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		if (eval(this.condition) == true) {
			return eval(this.expression) ;
		} else return true ;
	} ;
}
$net.ion.common.valid.ConditionExpressionValidator.prototype = new $net.ion.common.valid.Vaildator;

// regex pattern validator
$net.ion.common.valid.RegexValidator = function(fieldName, regex, errorMessage, focus) {
	this.fieldName = fieldName;
	this.regex = regex;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			if (str.length == 0 || str.search(this.regex) != -1) continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.RegexValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.RegexSepcialCharValidator = function(fieldName, regex, errorMessage, focus) {
	this.fieldName = fieldName;
	this.regex = regex;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			if (str.length == 1 && str.search(this.regex) != -1)
	            return false;
		}
		return true ;
	} ;
}
$net.ion.common.valid.RegexSepcialCharValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.AFieldFileTypeValidator = function(fieldName, expectedFileType, errorMessage, focus) {
	this.fieldName = fieldName;
	this.expectedFileType = expectedFileType ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		if (this.expectedFileType.toLowerCase() == 'all') return true ;
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			_value = this.getElement(this.fieldName, i).value ;
			if (_value == '')
				continue ;
	
			if (this.expectedFileType.toLowerCase() != _value.substring(_value.indexOf(".")+1).toLowerCase() )
				return false ;
		}
		return true ;
	} ;	
}
$net.ion.common.valid.AFieldFileTypeValidator.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.UploadFileNameValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			_value = this.getElement(this.fieldName, i).value ;
			if (_value == '') continue ;
			if( !(_value.substringAfterLast('\\').substringBeforeLast('.').isUploadFileNames() &&  _value.substringAfterLast('\\').substringAfterLast('.').isAlphaNumUnderBar() ) ){
				return false ;
			}
		}
		return true ;
	} ;
}
$net.ion.common.valid.UploadFileNameValidator.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.ImportFileTypeValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		var imgExt = ',xml,' ;
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			_value = this.getElement(this.fieldName, i).value ;
			if (_value == '') continue ;
	
			ext =_value.split(".")[_value.split(".").length-1].toLowerCase() ;
			if (imgExt.indexOf(',' + ext + ',') >= 0) continue ;
			else return false ;
		}
		return true ;
	} ;	
}
$net.ion.common.valid.ImportFileTypeValidator.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.ImgFileTypeValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		var imgExt = ',jpg,gif,rgb,pbm,pgm,ppm,tiff,tif,rast,xbm,jpeg,bmp,png,' ;
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			_value = this.getElement(this.fieldName, i).value ;
			if (_value == '') continue ;
	
			ext =_value.split(".")[_value.split(".").length-1].toLowerCase() ;
			if (imgExt.indexOf(',' + ext + ',') >= 0) continue ;
			else return false ;
		}
		return true ;
	} ;	
}
$net.ion.common.valid.ImgFileTypeValidator.prototype = new $net.ion.common.valid.Vaildator;



$net.ion.common.valid.ImgFileNameValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			_value = this.getElement(this.fieldName, i).value ;
			if (_value == '') continue ;
	
			if(! _value.substringAfterLast('\\').substringBefore('.').isAlphaNumUnderBar()){
				return false ;
			}
		}
		return true ;
	} ;
}
$net.ion.common.valid.ImgFileNameValidator.prototype = new $net.ion.common.valid.Vaildator;



$net.ion.common.valid.EqualValueValidator = function(fieldName, errorMessage, value, focus) {
	this.fieldName = fieldName ;
	this.errorMessage = errorMessage;
	this.value = value ;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
	        //alert(this.getElement(this.fieldName, i).value);
	        if( this.getElement(this.fieldName, i).value == this.value)
	        	continue;
	        else
	       		return false;
		}
		return true ;
	} ;
}
$net.ion.common.valid.EqualValueValidator.prototype = new $net.ion.common.valid.Vaildator;



$net.ion.common.valid.EqualValidator = function(fieldName, fieldName2, errorMessage, focus) {
	this.fieldName = fieldName ;
	this.fieldName2 = fieldName2 ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			if (this.getElement(this.fieldName, i).value == this.getElement(this.fieldName2, i).value) continue ;
			else return false ;
		}
		return true ;
	} ;
}
$net.ion.common.valid.EqualValidator.prototype = new $net.ion.common.valid.Vaildator;



$net.ion.common.valid.LessEqualNumberValidator = function(fieldName, num, errorMessage, focus) {
	this.fieldName = fieldName ;
	this.num = num ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			if (this.getElement(this.fieldName, i).value -0 <= this.num) continue ;
			else return false ;
		}
		return true ;
	} ;
	
}
$net.ion.common.valid.LessEqualNumberValidator.prototype = new $net.ion.common.valid.Vaildator;

$net.ion.common.valid.MoreEqualNumberValidator = function(fieldName, num, errorMessage, focus) {
	this.fieldName = fieldName ;
	this.num = num ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			if (this.getElement(this.fieldName, i).value -0 >= this.num) continue ;
			else return false ;
		}
	} ;
}
$net.ion.common.valid.MoreEqualNumberValidator.prototype = new $net.ion.common.valid.Vaildator;


// check selected
$net.ion.common.valid.SelectionValidator = function(fieldName, firstIdx, errorMessage, focus) {
	this.fieldName = fieldName;
	this.firstIdx = firstIdx;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		var idx = this.getForm()[this.fieldName].selectedIndex;
		return idx >= this.firstIdx;
	} ;	
}
$net.ion.common.valid.SelectionValidator.prototype = new $net.ion.common.valid.Vaildator;


// check select not null
$net.ion.common.valid.SelectionNotNullValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		var value = this.getForm()[this.fieldName].value;
		return value == '' ? false : true;
	} ;	
}
$net.ion.common.valid.SelectionNotNullValidator.prototype = new $net.ion.common.valid.Vaildator;


// check selected
$net.ion.common.valid.NotExistValidator = function(cvalue, optionBox, errorMessage, focus) {
	this.cvalue= cvalue;
	this.optionBox = optionBox;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		_optionBox = eval(this.optionBox) ;
		_cvalue = eval(this.cvalue) ;
	
	    for( j = 0 ; j < _optionBox.options.length ; j++ ){
	        if( _optionBox.options[j].value == _cvalue ){
	            return false ;
	        }
	    }
		return true;
	} ;
}
$net.ion.common.valid.NotExistValidator.prototype = new $net.ion.common.valid.Vaildator;



// check checkbox checked
$net.ion.common.valid.AtLeastOneCheckValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
	
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			ele = this.getElement(this.fieldName, i) ;
			if (ele.checked) return true ;
			else continue ;
		}
		return false ;
	} ;
}
$net.ion.common.valid.AtLeastOneCheckValidator.prototype = new $net.ion.common.valid.Vaildator;


// select box
$net.ion.common.valid.AtLeastOneItemValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		try {
			ele = this.getForm()[this.fieldName];
			return ele.length > 0 ;
		} catch(ex) {
			return false ;
		}
	} ;	
}
$net.ion.common.valid.AtLeastOneItemValidator.prototype = new $net.ion.common.valid.Vaildator;



// include
$net.ion.common.valid.IncludeCharValidator = function(fieldName, validStr, errorMessage, focus) {
	this.fieldName = fieldName;
	this.validStr = validStr ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
	
		matchCount = 0 ;
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			for(var z=0;z< this.validStr.length;z++){
				var c = this.validStr.charAt(z);
				if (str.indexOf(c) > -1) {
				 	matchCount++ ; 
				 	break; 
				 }
			}
		}
		return matchCount == this.getElementSize(this.fieldName) ;
	} ;
}
$net.ion.common.valid.IncludeCharValidator.prototype = new $net.ion.common.valid.Vaildator;


// not allowed char
$net.ion.common.valid.NotAllowedCharValidator = function(fieldName, notAllowedStr, errorMessage, focus) {
	this.fieldName = fieldName;
	this.notAllowedStr = notAllowedStr ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		valid = new IncludeCharValidator(this.fieldName, this.notAllowedStr, this.errorMessage, this.focus);
		return !valid.validate() ;
	} ;
}
$net.ion.common.valid.NotAllowedCharValidator.prototype = new $net.ion.common.valid.Vaildator;


// include
$net.ion.common.valid.IncludeCharValidator1 = function(fieldName, validStr, errorMessage, focus) {
	this.fieldName = fieldName;
	this.validStr = validStr ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
	
		matchCount = 0 ;
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			for(var z=0;z< this.validStr.length;z++){
				var c = this.validStr.charAt(z);
				if (str.indexOf(c) > -1) { matchCount++ ; break; }
			}
	
		}
	    return matchCount > 0;
	} ;
}
$net.ion.common.valid.IncludeCharValidator1.prototype = new $net.ion.common.valid.Vaildator;

// not allowed char _ heeya
$net.ion.common.valid.NotAllowedCharValidator1 = function(fieldName, notAllowedStr, errorMessage, focus) {
	this.fieldName = fieldName;
	this.notAllowedStr = notAllowedStr ;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		valid = new IncludeCharValidator1(this.fieldName, this.notAllowedStr, this.errorMessage, this.focus);
		return !valid.validate() ;
	} ;
}
$net.ion.common.valid.NotAllowedCharValidator1.prototype = new $net.ion.common.valid.Vaildator;


// not allowed 2 byte space
$net.ion.common.valid.NotAllowed2ByteSpaceValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			for(var z=0;z< str.length;z++){
				var c = str.charAt(z);
				if ( escape( c ) == "%u3000" ) return false ;
			}
		}
	    return true;
	} ;
}
$net.ion.common.valid.NotAllowed2ByteSpaceValidator.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.AnyoneTrueValidator = function(valid1, valid2, errorMessage) {
	this.valid1 = valid1 ;
	this.valid2 = valid2 ;
	this.errorMessage = errorMessage;
	this.focus = false;
	this.validate = function() {
	    return this.valid1.validate() || this.valid2.validate() ;
	} ;
}
$net.ion.common.valid.AnyoneTrueValidator.prototype = new $net.ion.common.valid.Vaildator;



$net.ion.common.valid.NoElseActionValidator = function(){
	this.elseAction = function(){
		return false ;
	} ;
}
$net.ion.common.valid.NoElseActionValidator.prototype = new $net.ion.common.valid.Vaildator ;


$net.ion.common.valid. ConfirmValidator = function(confirmMsg) {
	this.confirmMsg = confirmMsg;
	this.errorMessage = '' ; // set empty..
	this.validate = function() {
	    return confirm(this.confirmMsg);
	} ;
}
$net.ion.common.valid.ConfirmValidator.prototype = new $net.ion.common.valid.NoElseActionValidator;



// regex number pattern validator
$net.ion.common.valid.RegexValidatorPositiveNumber = function(fieldName, regex, errorMessage, focus) {
	this.fieldName = fieldName;
	this.regex = regex;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
	    for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
	        if(str.length == 0) return true;
	        if(Number(str).valueOf() <= 0 ){
	          	return false;
	        }
		}
		return true ;
	} ;
}
$net.ion.common.valid.RegexValidatorPositiveNumber.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.AlertValidator = function(alertMsg) {
	this.alertMsg = alertMsg;

	this.validate = function() {
	    return alert(this.alertMsg);
	} ;
}
$net.ion.common.valid.AlertValidator.prototype = new $net.ion.common.valid.NoElseActionValidator;


$net.ion.common.valid.SiteUrlValidator = function(fieldName, errorMessage, focus) {
	this.fieldName = fieldName;
	//this.regex = /^https?:\/\/([-\w\.]+)+(:\d+)?(\/([\w/_\.]*(\?\S+)?)?)?$/;
	this.regex = /^https?:\/\/([\S+]+)+$/;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		for(i=0, last=this.getElementSize(this.fieldName) ; i < last  ; i++){
			var str = this.getElement(this.fieldName, i).value ;
			if (str.length == 0 || str.search(this.regex) != -1 || '/' == str) continue ;
			else return false ;
		}
		return true ;
	} ;
}

$net.ion.common.valid.SiteUrlValidator.prototype = new $net.ion.common.valid.Vaildator;


$net.ion.common.valid.FunctionValidator = function(func, errorMessage, focus) {
	this.func = func;
	this.errorMessage = errorMessage;
	this.focus = focus;
	this.validate = function() {
		return this.func();
	} ;
}

$net.ion.common.valid.FunctionValidator.prototype = new $net.ion.common.valid.Vaildator;







$net.ion.common.valid.FormValid = function () {
	this.currentForward = '' ;
	this.forwardList = new Array();
	this.validatorList = new Array();
	

	this.pushValidator = function(validator){
		this.forwardList.push(this.currentForward) ;
		this.validatorList.push(validator) ;
	} ;
	
	this.setForward = function(forwardName){
		this.currentForward = forwardName ;
		return this ;
	} ;
	
	
	this.validate = function(forwardName) {
		var _forwardName = ((this.validate.arguments.length == 0) ? '' : forwardName) ;
		for (v=0, vsize=this.validatorList.length ; v < vsize ; v++ ) { // not use v, vsize in child object.. because valiable x is protect scope in javascript
			
			
			var validator = this.validatorList[v] ;
			if (_forwardName != this.forwardList[v]) {
				continue ; // if
			}
			if (validator.validate() == false) {
				return validator.elseAction() ;
			}
		}
		return true;
	} ;
	
	this.size = function(){
		return this.validatorList.length ;
	} ;
	
	this.callback = function(callback,errorMessage, focus) {
		this.pushValidator(new FunctionValidator(callback,errorMessage, focus));
	} ;
	
	
	
	
	
		
		
	this.required = function(fieldName, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.RequiredValidator(fieldName, errorMessage, focus));
	} ;
	
	this.equalvalue = function(fieldName, errorMessage, value, focus){
	    this.pushValidator(new $net.ion.common.valid.EqualValueValidator(fieldName, errorMessage, value, focus));
	} ;
	
	this.isDate = function(fieldName, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.DateTypeValidator(fieldName, errorMessage, focus));
	} ;
	
	this.maxLength = function(fieldName, maxLength, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.MaxLengthValidator(fieldName, maxLength, errorMessage, focus));
	} ;
	
	this.maxLengthByte = function(fieldName, maxLength, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.MaxLengthByteValidator(fieldName, maxLength, errorMessage, focus));
	} ;
	
	this.minLength = function(fieldName, minLength, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.MinLengthValidator(fieldName, minLength, errorMessage, focus));
	} ;
	
	this.minLengthByte = function(fieldName, minLength, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.MinLengthByteValidator(fieldName, minLength, errorMessage, focus));
	} ;
	
	this.betweenLengthByte = function(fieldName, minLength, maxLength, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.BetweenLengthByteValidator(fieldName, minLength, maxLength, errorMessage, focus));
	} ;
	
	this.regex = function(fieldName, regex, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, regex, errorMessage, focus));
	} ;
	
	this.expression = function(fieldName, expression, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.ExpressionValidator(fieldName, expression, errorMessage, focus));
	} ;
	
	this.conditionExpression = function(fieldName, condition, expression, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.ConditionExpressionValidator(fieldName, condition, expression, errorMessage, focus));
	} ;
	
	
	this.alphaHyphon = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z-]+$/, errorMessage, focus));
	} ;
	
	this.alphaNum = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9]+$/, errorMessage, focus));
	} ;
	
	this.alphaUnderBar = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z_]+$/, errorMessage, focus));
	} ;
	
	this.alphaNumUnderBar = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9_]+$/, errorMessage, focus));
	} ;
	
	this.alphaNumUnderBarAndPeriod = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9_\.\,-]+$/, errorMessage, focus));
	} ;
	
	this.smallAlphaNum = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-z0-9]+$/, errorMessage, focus));
	} ;
	
	this.smallAlphaNumUnderBar = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-z0-9_]+$/, errorMessage, focus));
	} ;
	
	this.smallAlphaNumUnderBarHyphen = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-z0-9_\-]+$/, errorMessage, focus));
	} ;
	
	this.specialCharacterCheck = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexSepcialCharValidator(fieldName, /^[\_\-\,\.]+$/, errorMessage, focus));
	} ;
	
	this.onlyNumber = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[0-9]+$/, errorMessage, focus));
	} ;
	
	this.onlyPositiveNumber = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidatorPositiveNumber(fieldName, /^[0-9]+$/, errorMessage, focus));
	} ;
	
	this.decimal = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^(\-)?[0-9]*(\.[0-9]*)?$/, errorMessage, focus));
	} ;
	
	this.email = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/, errorMessage, focus));
	} ;
	
	this.phone = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, '^[0-9\-#\(\)\+\.\]+$', errorMessage, focus)); // 82-2-574-1080.2(#205)
	} ;
	
	this.password= function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, "^[a-zA-Z0-9\~`!@#\$%\^\*\(\)\+\?\.,;:\"'\{\}_\\\/]+$", errorMessage, focus)); 
			 // ~'!@#$%^*()+?.,;:"'{}_\/       ==> &<>  not used (password input to xml type) 
	} ;
												
	this.ipaddress = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^((0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})\.){3}(0|1[0-9]{0,2}|2[0-9]{0,1}|2[0-4][0-9]|25[0-5]|[3-9][0-9]{0,1})$/, errorMessage, focus)); 
	} ;
	
	this.host = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9\:\#\$\%\&\=\-\~\+\/\?\_\.\,]+$/, errorMessage, focus));
		//this.pushValidator(new NotAllowedCharValidator(fieldName, "\!\"'\(\)\^\|\\\[\]\{\}`@\*;\<\>", errorMessage, focus)); // ! " ' ( ) ^ | \ [ ] { } ` @ * ; < >
	} ;
	
	this.url = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^https?:\/\/([\S+]+)+$/, errorMessage, focus));
			 
		this.pushValidator(new RegexValidator(fieldName, /^[a-zA-Z0-9\:\#\$\%\&\=\-\~\+\/\?\_\.\,]+$/, errorMessage, focus));
		//this.pushValidator(new NotAllowedCharValidator(fieldName, "\!\"'\(\)\^\|\\\[\]\{\}`@\*;\<\>", errorMessage, focus)); // ! " ' ( ) ^ | \ [ ] { } ` @ * ; < >
	} ;
	
	this.dir = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, '^[a-zA-Z0-9_\~`!@#\$%\^&\(\)\+\.,;\{\}\\-]+$', errorMessage, focus));
	} ;
	
	this.path = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, '^[a-zA-Z0-9 _\\\\\~`!@#\$%\^&\(\)\+\.,;\{\}\\-\/:]+$', errorMessage, focus));
	} ;
	
	this.query = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, '^[a-zA-Z0-9_\~`!@#\$%\^&\(\)\+\.,;\{\}\\-= \\n\\t\\r\']+$', errorMessage, focus));
	}  ;     //a-zA-Z0-9 _ \ ' ! @ # $ % ^ & (  ) + . , ; { } - = \n \t \r '
	
	this.alphaNumRest = function(fieldName, errorMessage, focus) {
		this.pushValidator(
			new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9, ]+$/, errorMessage, focus));
	} ;
	
	this.imgFileName = function(fieldName, nameErrorMessage, typeErrorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.UploadFileNameValidator(fieldName, nameErrorMessage, focus));
		this.pushValidator(new $net.ion.common.valid.ImgFileTypeValidator(fieldName, typeErrorMessage, focus));
	} ;
	
	this.uploadFileName = function(fieldName, nameErrorMessage, typeErrorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.UploadFileNameValidator(fieldName, nameErrorMessage, focus));
		this.pushValidator(new $net.ion.common.valid.ImportFileTypeValidator(fieldName, typeErrorMessage, focus));
	} ;
	
	this.aFieldFileName = function(fieldName, expectedFileType, nameErrorMessage, typeErrorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.ImgFileNameValidator(fieldName, nameErrorMessage, focus));
		this.pushValidator(new $net.ion.common.valid.AFieldFileTypeValidator(fieldName, expectedFileType, typeErrorMessage, focus));
	} ;
	
	this.equal = function(fieldName1, fieldName2, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.EqualValidator(fieldName1, fieldName2, errorMessage, focus));
	} ;
	
	this.lessEqualNumber = function(fieldName, num, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.LessEqualNumberValidator(fieldName, num, errorMessage, focus));
	} ;
	
	this.moreEqualNumber = function(fieldName, num, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.MoreEqualNumberValidator(fieldName, num, errorMessage, focus));
	} ;
	
	this.selected = function(fieldName, firstIdx, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.SelectionValidator(fieldName, firstIdx, errorMessage, focus));
	} ;
	
	this.selectNotNull = function(fieldName, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.SelectionNotNullValidator(fieldName, errorMessage, focus));
	} ;
	
	this.notExist = function(cvalue, optionBox, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.NotExistValidator(cvalue, optionBox, errorMessage, focus));
	} ;
	
	this.leastOneChecked = function(fieldName, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.AtLeastOneCheckValidator(fieldName, errorMessage, focus));
	} ;
	
	this.leastOneItem = function(fieldName, errorMessage, focus) {
		this.pushValidator(new $net.ion.common.valid.AtLeastOneItemValidator(fieldName, errorMessage, focus));
	} ;
	
	this.includeChar = function(fieldName, validStr, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.IncludeCharValidator(fieldName, validStr, errorMessage, focus));
	} ;
	
	this.notAllowedChar = function(fieldName, notAllowedStr, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator(fieldName, notAllowedStr, errorMessage, focus));
	} ;
	
	this.notAllowedQuota = function(fieldName, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator(fieldName, "\"\'", errorMessage, focus)); // ' "
	} ;
	
	this.notAllowedSpecial = function(fieldName, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator1(fieldName, "\',\"#\$%&\*\+\{\}\<\>\\\\", errorMessage, focus)); // ' " , # $ % & * + { } < > \
	} ;
	
	this.notAllowedSpecial1 = function(fieldName, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator1(fieldName, "\',\"#\$%\*\+\{\}\<\>\\\\", errorMessage, focus)); // ' " , # $ % * + { } < > \
	} ;
	
	this.notAllowedSpecial2 = function(fieldName, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator1(fieldName, "\'\"\$%\\<\>\\\\", errorMessage, focus)); // ' " $ % < > \
	} ;
	
	this.notAllowedSpecial3 = function(fieldName, errorMessage, focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowedCharValidator1(fieldName, "\'\"\\;\{\}\\*<\>\\", errorMessage, focus)); // ' " < > ; { } *
	} ;
	
	this.notAllowed2ByteSpace = function(fieldName, errorMessage,focus){
		this.pushValidator(new $net.ion.common.valid.NotAllowed2ByteSpaceValidator(fieldName, errorMessage, focus));
	} ;
	
	this.anyoneTrue = function(valid1, valid2, errorMessage){
		this.pushValidator(new $net.ion.common.valid.AnyoneTrueValidator(valid1, valid2, errorMessage));
	} ;
	
	this.confirm = function(confirmMsg){
		this.pushValidator(new $net.ion.common.valid.ConfirmValidator(confirmMsg)) ;
	} ;
	
	this.alert = function(alertMsg){
		this.pushValidator(new $net.ion.common.valid.AlertValidator(alertMsg)) ;
	} ;
	
	this.valid = function(validator){
		this.pushValidator(validator);
	} ;
	

		
	this.siteUrl = function(fieldName, errorMessage, focus) { 
		this.pushValidator(new $net.ion.common.valid.SiteUrlValidator(fieldName, errorMessage, focus)); 
		this.pushValidator(new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9\:\#\$\%\&\*\=\-\~\+\/\?\_\.\,]+$/, errorMessage, focus)); // : # $ % & * = - ~ + / ? _ . ,
		//this.pushValidator(new NotAllowedCharValidator(fieldName, "\!\"'\(\)\^\|\\\[\]\{\}`@\*;\<\>", errorMessage, focus)); 
	}  ;
	
	this.siteUrl1 = function(fieldName, errorMessage, focus) { 
		this.pushValidator(new $net.ion.common.valid.SiteUrlValidator(fieldName, errorMessage, focus)); 
		this.pushValidator(new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9\:\#\$\%\=\-\~\+\/\?\_\.\,]+$/, errorMessage, focus)); // : # $ % = - ~ + / ? _ . ,
		
	} ;
	
	this.wildcard = function(fieldName, errorMessage, focus) { 
		this.pushValidator(new $net.ion.common.valid.RegexValidator(fieldName, /^[a-zA-Z0-9\:\#\$\%\&\*\=\-\~\+\/\?\_\.\,]+$/, errorMessage, focus)); // : # $ % & * = - ~ + / ? _ . ,
	} ;

	
	
}




// ******************************** template


$net.ion.common.PageOutPut = function(tableId, rowCount, page){
	var tableId = tableId ? tableId : 'page_table' ;
	var rowCount = rowCount ;
	var page = page ;
	
    this.toHtml = function() {
        if(this.isValidPage()){
            var buffer = "";
            buffer += "<table border='0' cellspacing='0' cellpadding='0' id='" + tableId + "' class='pageout'><tr>";
            buffer += "<td width='25'><div class='page_prescreen'></div></td>";
            buffer += "<td align=center>";
            buffer += this.printPageNoList();
            buffer += "</td>";
            buffer += "<td width='25' align='right'><div class='page_nextscreen'></div></td>";
            buffer += "</tr></table>";
            return buffer;
        } else {
            return "";
        }
    }
	
	this.printPageNoList = function() {
		var rtnValue = "";

		if (page.getPageNo() > 1) rtnValue += "<a class=\"prepage\"></a>";
		if (page.getPageNo() < page.getMaxPageNo(rowCount)) rtnValue += "<a id=\"nextpage\"></a>";

		for (startPage = page.getMinPageNoOnScreen(), endPage = Math.min(page.getMaxPageNo(rowCount), page.getMaxPageNoOnScreen()); startPage <= endPage; startPage++) {
			rtnValue += " <a class='pageno" + (startPage == page.getPageNo() ? ' currentpage' : '') +"'>" + startPage + "</a>";
		}

		return rtnValue;
	} ;
	
	this.isValidPage = function() {
		if (page.getPageNo() > 1 && rowCount == 0) {
			return false;
		}
		return true;
	} ;

	this.existNextScreen = function() {
		return rowCount > page.getListNum() * page.getScreenCount() ;
	} ;
	
	this.bindLink = function(ptableId, pageFn, nextScreenFn, preScreenFn){
		var pagecontext = $(ptableId) ;
		pagecontext.find('div.page_prescreen').append("<img src='../../common/img/list_prev.gif' border='0'>");
		pagecontext.find('div.page_nextscreen').append("<img src='../../common/img/list_next.gif' border='0'>");
		pagecontext.find('a.pageno').attr('href', 'javascript://').bind('click', pageFn).prepend('[').append(']') ;
		pagecontext.find('a.currentpage').attr('href', 'javascript://').wrap().css('color', '06F').css('font-weight', 'bold').unbind('click', false).removeAttr('href') ;
		if (this.existNextScreen()) pagecontext.find('div.page_nextscreen').css('cursor', 'pointer').bind('click',nextScreenFn) ;
		if (page.getCurrentScreen() > 1) pagecontext.find('div.page_prescreen').css('cursor', 'pointer').bind('click', preScreenFn) ;
	} ;

} ;


$net.ion.common.Page = function(listNum, pageNo, screenCount){
	// readonly and private
	var listNum = (listNum < 1) ? 1 : listNum ;
	var _pageNo = (pageNo < 1) ? 0 : pageNo-1 ;
	var screenCount = (screenCount && screenCount > 0 ) ? screenCount : 10 ;
	
	this.getListNum = function(){
		return listNum ;
	} ;
	
	this.getPageNo = function(){
		return _pageNo+1 ;
	} ;
	
	this.getScreenCount = function(){
		return (screenCount > 1) ? screenCount : 1 ;
	} ;
	
	this.getStartLoc = function(){
		return listNum * _pageNo ;
	};
	
	this.getEndLoc = function(){
		return this.getListNum() * (_pageNo + 1) ;
	} ;

	this.getMaxScreenCount = function(){
		return this.getListNum() * this.getScreenCount()  ;
	} ;
	
	this.getMaxCount = function(){
		return this.getListNum() * screenCount * (Math.floor(_pageNo/10) + 1) + 1;
	} ;
	
	this.getNextPage = function() {
		return new Page(this.getListNum(), this.getPageNo() + 1, this.getScreenCount());
	} ;

	this.getPrePage = function() {
		return new Page(this.getListNum(), this.getPageNo() - 1, this.getScreenCount());
	} ;

	this.getMaxPageNo = function(rowCount){
		return Math.floor((rowCount + this.getListNum() - 1) / this.getListNum()) + (this.getCurrentScreen() -1)* this.getScreenCount() ;		
	} ;
	
	this.getCurrentScreen = function(){
		return Math.floor(_pageNo / this.getScreenCount()) + 1 ; 
	} ;
	
	this.getMaxScreen = function(rowCount){
		return Math.floor((rowCount-1) / (this.getListNum()) * this.getScreenCount())  + 1;
	} ;

	this.getMinPageNo = function(rowCount){
		return (this.getMaxScreen(rowCount) -1) * this.getScreenCount() + 1 ;
	} ;
	
	this.getMinPageNoOnScreen = function() {
		return Math.floor(_pageNo / this.getScreenCount()) * this.getScreenCount() + 1;
	} ;
	
	this.getMaxPageNoOnScreen = function() {
		return Math.floor(_pageNo / this.getScreenCount()) * this.getScreenCount() + this.getScreenCount();
	} ;
	
	this.getNextScreenStartPageNo = function() {
		return this.getCurrentScreen() * this.getListNum() + 1;
	} ;
	this.getPreScreenEndPageNo = function(){
		return (this.getCurrentScreen() - 1) * this.getListNum() ;
	} ;

	
	this.jsonString = function(){
		if (this.listNum <= 0 || this.pageNo <= 0) return "page:{listnum:10, pageno:1, screencount:10}" ;
		
		return "page:{listnum:" + this.getListNum() + ", pageno:" + this.getPageNo() + ", screencount:" + this.getScreenCount() + "}" ;
	} ;
}


$net.ion.common.Params = function(){
	this.params = new Map() ;
	
	this.addParam = function(key, value){
		this.params.put(key, value) ;
	} ;
	
	this.jsonString = function(){
		var result = 'param:{' ;
		
		for(var i=0, last=this.params.size() ; i < last ; i++){
			if (i > 0) result += ', ' ;
			
			result += this.params.getEntry(i).key + ":" ;
			var value = this.params.getEntry(i).value ;
			if (typeof value != 'number') value = "\"" + value + "\"" ;
			
			result += value ;
		}
		
		result += '}';
		return result ;
	} ;
}

$net.ion.common.Map = function(){
	
	var keys = new Array();

	this.contains = function(key){
		AssertUtil.assertParamNotNull(key, "key");
		var entry = findEntry(key);
		return !(entry == null || entry instanceof NullKey);
	};

	this.get = function(key) {
		var entry = findEntry(key);
	 	if ( !(entry == null || entry instanceof NullKey) )
			return entry.value;
		else
			return null;
	};

	this.put = function(key, value) {
		AssertUtil.assertParamNotNull(key, "Map.put.key");
		AssertUtil.assertParamNotNull(value, "Map.put.value");
		var entry = findEntry(key);
		if (entry){
			entry.value = value;
		} else {
			addNewEntry(key, value);
		}
	};

	this.remove = function (key){
		AssertUtil.assertParamNotNull(key, "key");
		for (var i=0;i<keys.length;i++){
			var entry = keys[i];
			if (entry instanceof NullKey) continue;
			if (entry.key == key){
					keys[i] = NullKey;
			}
		}				
	};
	function findEntry(key){
		for (var i=0;i<keys.length;i++){
			var entry = keys[i];
			if (entry instanceof NullKey) continue;
			if (entry.key == key){
				return entry ;
			}
		}
		return null;
	};
	function addNewEntry(key, value){
		var entry = new Object();
		entry.key = key;
		entry.value = value;
		keys[keys.length] = entry; 
	};
	this.size = function(){
		return keys.length ;
	};
	
	this.getEntry = function(idx){
		return keys[idx] ;
	} ;
}













////////////////////////////// aradon.js




$net.ion.aradon.AradonClient = function() {
	this.address = 'http://localhost:9002/' ;
	
	this.createSession = function(userId) {
		return new $net.ion.aradon.Session(userId) ;
	}  ;
	
	this.getSection = function(sectionName){
		return new $net.ion.aradon.Section(this, sectionName) ;
	} ;
	
	this.getSystemLet = function(name) {
		return 	new $net.ion.aradon.Section(this, 'system').getLet('/' + name) ;
	} ;
	
	this.getAddress  = function() {
		return this.address ;
	} ;
	
	this.createLets = function(){
		return new $net.ion.aradon.BatchLet(this, new $net.ion.aradon.Section(this, 'system').getLet('/lets')) ;
	}
} 



$net.ion.aradon.Section = function(client, name) {
	this.client = client ;
	this.name = name ;
	
	this.getName = function(){
		return this.name ;
	} ;
	
	this.getLet = function(path) {
		return new $net.ion.aradon.SectionLet(this, path) ;
	} ;
	
	this.getPath = function(){
		return client.getAddress() + this.getName() ;
	}
}

$net.ion.aradon.SectionLet = function(section, path){
	var section = section ;
	var path = path ;
	this.data = new $net.ion.util.Map() ;
	
	this.addParam = function(key, value){
		this.data.put(key, value) ;
		return this ;
	} ;
	
	this.getParams = function(){
		return this.data.flat() ;
	} ;
	
	this.setParams = function(params){
		self = this ;
		$.each(params, function(key, value) {
			self.addParam(key, value) ;
		}) ;
	} ;
	
	this.getSection = function() {
		return section ;
	}
	

	this.subLet = function(subpath) {
		var result = new $net.ion.aradon.SectionLet(section, path + '/' +  subpath) ;
		result.data = this.data ;
		return result ;
	} ;
	
	this.fullPath = function() {
		return section.getPath() + path ;
	} ;

	this.letPath = function() {
		return path ;	
	}
		
	this.clearParam = function(){
		this.data.clearMap() ;
		return this ;
	} ;

	this.del = function(sfn){
		this.addParam('aradon.result.method', 'DELETE') ;
		return this.callHttp("POST", sfn) ;
	} ;
	
	this.put = function(sfn){
		this.addParam('aradon.result.method', 'PUT') ;
		return this.callHttp("POST", sfn) ;
	} ;

	
	this.post = function(sfn){
		return this.callHttp("POST", sfn) ;
	} ;

	this.get = function(sfn){
		return this.callHttp("GET", sfn) ;
	} ;
	
	
	this.callHttp = function(_type, sfn){
		var response = $.ajax({
			type: _type, 
			url: this.fullPath(), 
			data : this.getParams(), 
			success : sfn
		}) ;
		this.clearParam() ;
		return response ;
	} ;
	

	function init(thisObj){
		var debug = debugMessage ;
		$.ajaxSetup({
			cache: false,
			asynch: false, 
			dataType: 'json', 
			timeout: 30000, 
			data: {}, 
			complete : function(res, status){
				debug.call(null, res) ;
			}, 
			error: function(res,error){
				alert('Error', JSON.stringify(res), error) ;
			}, 
			beforeSend: function(req){
				// this.type = 'POST' ;
				// req.setRequestHeader("MY-HTTP-Method-Override", this.overrideMethod) ;
				debug.call(null, 'Clear') ;
				
				
				return true ;
			}
		})
	} ;

	function debugMessage(res) {
		$('#debugmessage').remove() ;
		var divObject = jQuery('<div></div>').attr('id', 'debugmessage') ;
		divObject.text('Success<br/>' + JSON.stringify(res)).appendTo('body') ;
	} ;

	init(this) ;
}




$net.ion.aradon.BatchLet = function(client, realLet){
	var client = client ;
	var realLet ;
	this.lets = new $net.ion.util.Map() ;

	this.addLet = function(name, let, method) {
		this.lets.put(name, this.toElement(name, let, method)) ;
	} ;
	
	this.toElement = function(name, let, method) {
		var result = {} ;
		result.name = name ;
		result.section = let.getSection().getName() ;
		result.path = let.letPath() ;
		result.param = let.getParams() ;
		result.param['aradon.result.method'] = method ;
		
		return result;
	} ;
	
	this.toStringify = function() {
		var result = {} ;
		var saved = new Array() ;
		for(var i=0 ; i < this.lets.size() ; i++){
			saved[i] = this.lets.getEntry(i).value ;
		}
		result.aradon = saved ;
		
		return JSON.stringify(result) ;
	} ;
	
	this.callHttp = function(_type, sfn){
		var response = $.ajax({
			type: _type, 
			url: realLet.fullPath(), 
			data : {'aradon.parameter':this.toStringify()},
			success : sfn
		}) ;
		return response ;
	} ;

}

$net.ion.aradon.BatchLet.prototype = new $net.ion.aradon.SectionLet() ;




$net.ion.aradon.Session = function(userId){
	this.userId = userId ;
	
	this.getUserId = function() {
		return this.userId ;
	} ;
	
	this.jsonString = function(){
		return "session:{userId:'" + this.userId + "'}";
	} ;
}

















/**
 * TrimPath Template. Release 1.1.2.
 * Copyright (C) 2004 - 2007 TrimPath.
 */

if (typeof(TrimPath) == 'undefined')
    TrimPath = {};

// TODO: Debugging mode vs stop-on-error mode - runtime flag.
// TODO: Handle || (or) characters and backslashes.
// TODO: Add more modifiers.

(function() {               // Using a closure to keep global namespace clean.
    if (TrimPath.evalEx == null)
        TrimPath.evalEx = function(src) { return eval(src); };

    var UNDEFINED;
    if (Array.prototype.pop == null)  // IE 5.x fix from Igor Poteryaev.
        Array.prototype.pop = function() {
            if (this.length === 0) {return UNDEFINED;}
            return this[--this.length];
        };
    if (Array.prototype.push == null) // IE 5.x fix from Igor Poteryaev.
        Array.prototype.push = function() {
            for (var i = 0; i < arguments.length; ++i) {this[this.length] = arguments[i];}
            return this.length;
        };

    TrimPath.parseTemplate = function(tmplContent, optTmplName, optEtc) {
        if (optEtc == null)
            optEtc = TrimPath.parseTemplate_etc;
        var funcSrc = parse(tmplContent, optTmplName, optEtc);
        var func = TrimPath.evalEx(funcSrc, optTmplName, 1);
        if (func != null)
            return new optEtc.Template(optTmplName, tmplContent, funcSrc, func, optEtc);
        return null;
    } ;
    
    var exceptionDetails = function(e) {
        return (e.toString()) + ";\n " +
               (e.message) + ";\n " + 
               (e.name) + ";\n " + 
               (e.stack       || 'no stack trace') + ";\n " +
               (e.description || 'no further description') + ";\n " +
               (e.fileName    || 'no file name') + ";\n " +
               (e.lineNumber  || 'no line number');
    } ;

    try {
        String.prototype.process = function(context, optFlags) {
            var template = TrimPath.parseTemplate(this, null);
            if (template != null)
                return template.process(context, optFlags);
            return this;
        } ;
    } catch (e) { // Swallow exception, such as when String.prototype is sealed.
    }
    
    TrimPath.parseTemplate_etc = {};            // Exposed for extensibility.
    TrimPath.parseTemplate_etc.statementTag = "forelse|for|if|elseif|else|var|macro";
    TrimPath.parseTemplate_etc.statementDef = { // Lookup table for statement tags.
        "if"     : { delta:  1, prefix: "if (", suffix: ") {", paramMin: 1 },
        "else"   : { delta:  0, prefix: "} else {" },
        "elseif" : { delta:  0, prefix: "} else if (", suffix: ") {", paramDefault: "true" },
        "/if"    : { delta: -1, prefix: "}" },
        "for"    : { delta:  1, paramMin: 3, 
                     prefixFunc : function(stmtParts, state, tmplName, etc) {
                        if (stmtParts[2] != "in")
                            throw new etc.ParseError(tmplName, state.line, "bad for loop statement: " + stmtParts.join(' '));
                        var iterVar = stmtParts[1];
                        var listVar = "__LIST__" + iterVar;
                        return [ "var ", listVar, " = ", stmtParts[3], ";",
                             // Fix from Ross Shaull for hash looping, make sure that we have an array of loop lengths to treat like a stack.
                             "var __LENGTH_STACK__;",
                             "if (typeof(__LENGTH_STACK__) == 'undefined' || !__LENGTH_STACK__.length) __LENGTH_STACK__ = new Array();", 
                             "__LENGTH_STACK__[__LENGTH_STACK__.length] = 0;", // Push a new for-loop onto the stack of loop lengths.
                             "if ((", listVar, ") != null) { ",
                             "var ", iterVar, "_ct = 0;",       // iterVar_ct variable, added by B. Bittman     
                             "for (var ", iterVar, "_index in ", listVar, ") { ",
                             iterVar, "_ct++;",
                             "if (typeof(", listVar, "[", iterVar, "_index]) == 'function') {continue;}", // IE 5.x fix from Igor Poteryaev.
                             "__LENGTH_STACK__[__LENGTH_STACK__.length - 1]++;",
                             "var ", iterVar, " = ", listVar, "[", iterVar, "_index];" ].join("");
                     } },
        "forelse" : { delta:  0, prefix: "} } if (__LENGTH_STACK__[__LENGTH_STACK__.length - 1] == 0) { if (", suffix: ") {", paramDefault: "true" },
        "/for"    : { delta: -1, prefix: "} }; delete __LENGTH_STACK__[__LENGTH_STACK__.length - 1];" }, // Remove the just-finished for-loop from the stack of loop lengths.
        "var"     : { delta:  0, prefix: "var ", suffix: ";" },
        "macro"   : { delta:  1, 
                      prefixFunc : function(stmtParts, state, tmplName, etc) {
                          var macroName = stmtParts[1].split('(')[0];
                          return [ "var ", macroName, " = function", 
                                   stmtParts.slice(1).join(' ').substring(macroName.length),
                                   "{ var _OUT_arr = []; var _OUT = { write: function(m) { if (m) _OUT_arr.push(m); } }; " ].join('');
                     } }, 
        "/macro"  : { delta: -1, prefix: " return _OUT_arr.join(''); };" }
    } ;
    TrimPath.parseTemplate_etc.modifierDef = {
        "eat"        : function(v)    { return ""; },
        "escape"     : function(s)    { return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;"); },
        "capitalize" : function(s)    { return String(s).toUpperCase(); },
        "substring"  : function(s, start, limit)    { return String(s).substring(start,limit) ; }, 
        "default"    : function(s, d) { return s != null ? s : d; }
    } ;
    TrimPath.parseTemplate_etc.modifierDef.h = TrimPath.parseTemplate_etc.modifierDef.escape;

    TrimPath.parseTemplate_etc.Template = function(tmplName, tmplContent, funcSrc, func, etc) {
        this.process = function(context, flags) {
            if (context == null)
                context = {};
            if (context._MODIFIERS == null)
                context._MODIFIERS = {};
            if (context.defined == null)
                context.defined = function(str) { return (context[str] != undefined); };
            for (var k in etc.modifierDef) {
                if (context._MODIFIERS[k] == null)
                    context._MODIFIERS[k] = etc.modifierDef[k];
            }
            if (flags == null)
                flags = {};
            var resultArr = [];
            var resultOut = { write: function(m) { resultArr.push(m); } };
            try {
                func(resultOut, context, flags);
            } catch (e) {
                if (flags.throwExceptions == true)
                    throw e;
                var result = new String(resultArr.join("") + 
                    "[ERROR: template: <pre>" + exceptionDetails(e) + "</pre>]");
                result["exception"] = e;
                return result;
            }
            return resultArr.join("");
        } ;
        this.name       = tmplName;
        this.source     = tmplContent; 
        this.sourceFunc = funcSrc;
        this.toString   = function() { return "TrimPath.Template [" + tmplName + "]"; } ;
    } ;
    TrimPath.parseTemplate_etc.ParseError = function(name, line, message) {
        this.name    = name;
        this.line    = line;
        this.message = message;
    } ;
    TrimPath.parseTemplate_etc.ParseError.prototype.toString = function() { 
        return ("TrimPath template ParseError in " + this.name + ": line " + this.line + ", " + this.message);
    } ;
    
    var parse = function(body, tmplName, etc) {
        body = cleanWhiteSpace(body);
        var funcText = [ "var TrimPath_Template_TEMP = function(_OUT, _CONTEXT, _FLAGS) { with (_CONTEXT) {" ];
        var state    = { stack: [], line: 1 };                              // TODO: Fix line number counting.
        var endStmtPrev = -1;
        while (endStmtPrev + 1 < body.length) {
            var begStmt = endStmtPrev;
            // Scan until we find some statement markup.
            begStmt = body.indexOf("{", begStmt + 1);
            while (begStmt >= 0) {
                var endStmt = body.indexOf('}', begStmt + 1);
                var stmt = body.substring(begStmt, endStmt);
                var blockrx = stmt.match(/^\{(cdata|minify|eval)/); // From B. Bittman, minify/eval/cdata implementation.
                if (blockrx) {
                    var blockType = blockrx[1]; 
                    var blockMarkerBeg = begStmt + blockType.length + 1;
                    var blockMarkerEnd = body.indexOf('}', blockMarkerBeg);
                    if (blockMarkerEnd >= 0) {
                        var blockMarker;
                        if( blockMarkerEnd - blockMarkerBeg <= 0 ) {
                            blockMarker = "{/" + blockType + "}";
                        } else {
                            blockMarker = body.substring(blockMarkerBeg + 1, blockMarkerEnd);
                        }                        
                        
                        var blockEnd = body.indexOf(blockMarker, blockMarkerEnd + 1);
                        if (blockEnd >= 0) {                            
                            emitSectionText(body.substring(endStmtPrev + 1, begStmt), funcText);
                            
                            var blockText = body.substring(blockMarkerEnd + 1, blockEnd);
                            if (blockType == 'cdata') {
                                emitText(blockText, funcText);
                            } else if (blockType == 'minify') {
                                emitText(scrubWhiteSpace(blockText), funcText);
                            } else if (blockType == 'eval') {
                                if (blockText != null && blockText.length > 0) // From B. Bittman, eval should not execute until process().
                                    funcText.push('_OUT.write( (function() { ' + blockText + ' })() );');
                            }
                            begStmt = endStmtPrev = blockEnd + blockMarker.length - 1;
                        }
                    }                        
                } else if (body.charAt(begStmt - 1) != '@' &&               // Not an expression or backslashed,
                           body.charAt(begStmt - 1) != '\\') {              // so check if it is a statement tag.
                    var offset = (body.charAt(begStmt + 1) == '/' ? 2 : 1); // Close tags offset of 2 skips '/'.
                                                                            // 10 is larger than maximum statement tag length.
                    if (body.substring(begStmt + offset, begStmt + 10 + offset).search(TrimPath.parseTemplate_etc.statementTag) == 0) 
                        break;                                              // Found a match.
                }
                begStmt = body.indexOf("{", begStmt + 1);
            }
            if (begStmt < 0)                              // In "a{for}c", begStmt will be 1.
                break;
            var endStmt = body.indexOf("}", begStmt + 1); // In "a{for}c", endStmt will be 5.
            if (endStmt < 0)
                break;
            emitSectionText(body.substring(endStmtPrev + 1, begStmt), funcText);
            emitStatement(body.substring(begStmt, endStmt + 1), state, funcText, tmplName, etc);
            endStmtPrev = endStmt;
        }
        emitSectionText(body.substring(endStmtPrev + 1), funcText);
        if (state.stack.length != 0)
            throw new etc.ParseError(tmplName, state.line, "unclosed, unmatched statement(s): " + state.stack.join(","));
        funcText.push("}}; TrimPath_Template_TEMP");
        return funcText.join("");
    } ;
    
    var emitStatement = function(stmtStr, state, funcText, tmplName, etc) {
        var parts = stmtStr.slice(1, -1).split(' ');
        var stmt = etc.statementDef[parts[0]]; // Here, parts[0] == for/if/else/...
        if (stmt == null) {                    // Not a real statement.
            emitSectionText(stmtStr, funcText);
            return;
        }
        if (stmt.delta < 0) {
            if (state.stack.length <= 0)
                throw new etc.ParseError(tmplName, state.line, "close tag does not match any previous statement: " + stmtStr);
            state.stack.pop();
        } 
        if (stmt.delta > 0)
            state.stack.push(stmtStr);

        if (stmt.paramMin != null &&
            stmt.paramMin >= parts.length)
            throw new etc.ParseError(tmplName, state.line, "statement needs more parameters: " + stmtStr);
        if (stmt.prefixFunc != null)
            funcText.push(stmt.prefixFunc(parts, state, tmplName, etc));
        else 
            funcText.push(stmt.prefix);
        if (stmt.suffix != null) {
            if (parts.length <= 1) {
                if (stmt.paramDefault != null)
                    funcText.push(stmt.paramDefault);
            } else {
                for (var i = 1; i < parts.length; i++) {
                    if (i > 1)
                        funcText.push(' ');
                    funcText.push(parts[i]);
                }
            }
            funcText.push(stmt.suffix);
        }
    } ;

    var emitSectionText = function(text, funcText) {
        if (text.length <= 0)
            return;
        var nlPrefix = 0;               // Index to first non-newline in prefix.
        var nlSuffix = text.length - 1; // Index to first non-space/tab in suffix.
        while (nlPrefix < text.length && (text.charAt(nlPrefix) == '\n'))
            nlPrefix++;
        while (nlSuffix >= 0 && (text.charAt(nlSuffix) == ' ' || text.charAt(nlSuffix) == '\t'))
            nlSuffix--;
        if (nlSuffix < nlPrefix)
            nlSuffix = nlPrefix;
        if (nlPrefix > 0) {
            funcText.push('if (_FLAGS.keepWhitespace == true) _OUT.write("');
            var s = text.substring(0, nlPrefix).replace('\n', '\\n'); // A macro IE fix from BJessen.
            if (s.charAt(s.length - 1) == '\n')
            	s = s.substring(0, s.length - 1);
            funcText.push(s);
            funcText.push('");');
        }
        var lines = text.substring(nlPrefix, nlSuffix + 1).split('\n');
        for (var i = 0; i < lines.length; i++) {
            emitSectionTextLine(lines[i], funcText);
            if (i < lines.length - 1)
                funcText.push('_OUT.write("\\n");\n');
        }
        if (nlSuffix + 1 < text.length) {
            funcText.push('if (_FLAGS.keepWhitespace == true) _OUT.write("');
            var s = text.substring(nlSuffix + 1).replace('\n', '\\n');
            if (s.charAt(s.length - 1) == '\n')
            	s = s.substring(0, s.length - 1);
            funcText.push(s);
            funcText.push('");');
        }
    } ;
    
    var emitSectionTextLine = function(line, funcText) {
        var endMarkPrev = '}';
        var endExprPrev = -1;
        while (endExprPrev + endMarkPrev.length < line.length) {
            var begMark = "@{", endMark = "}";
            var begExpr = line.indexOf(begMark, endExprPrev + endMarkPrev.length); // In "a${b}c", begExpr == 1
            if (begExpr < 0)
                break;
            if (line.charAt(begExpr + 2) == '%') {
                begMark = "@{%";
                endMark = "%}";
            }
            var endExpr = line.indexOf(endMark, begExpr + begMark.length);         // In "a${b}c", endExpr == 4;
            if (endExpr < 0)
                break;
            emitText(line.substring(endExprPrev + endMarkPrev.length, begExpr), funcText);                
            // Example: exprs == 'firstName|default:"John Doe"|capitalize'.split('|')
            var exprArr = line.substring(begExpr + begMark.length, endExpr).replace(/\|\|/g, "#@@#").split('|');
            for (var k in exprArr) {
                if (exprArr[k].replace) // IE 5.x fix from Igor Poteryaev.
                    exprArr[k] = exprArr[k].replace(/#@@#/g, '||');
            }
            funcText.push('_OUT.write(');
            emitExpression(exprArr, exprArr.length - 1, funcText); 
            funcText.push(');');
            endExprPrev = endExpr;
            endMarkPrev = endMark;
        }
        emitText(line.substring(endExprPrev + endMarkPrev.length), funcText); 
    } ;
    
    var emitText = function(text, funcText) {
        if (text == null ||
            text.length <= 0)
            return;
        text = text.replace(/\\/g, '\\\\');
        text = text.replace(/\n/g, '\\n');
        text = text.replace(/"/g,  '\\"');
        funcText.push('_OUT.write("');
        funcText.push(text);
        funcText.push('");');
    } ;
    
    var emitExpression = function(exprArr, index, funcText) {
        // Ex: foo|a:x|b:y1,y2|c:z1,z2 is emitted as c(b(a(foo,x),y1,y2),z1,z2)
        var expr = exprArr[index]; // Ex: exprArr == [firstName,capitalize,default:"John Doe"]
        if (index <= 0) {          // Ex: expr    == 'default:"John Doe"'
            funcText.push(expr);
            return;
        }
        var parts = expr.split(':');
        funcText.push('_MODIFIERS["');
        funcText.push(parts[0]); // The parts[0] is a modifier function name, like capitalize.
        funcText.push('"](');
        emitExpression(exprArr, index - 1, funcText);
        if (parts.length > 1) {
            funcText.push(',');
            funcText.push(parts[1]);
        }
        funcText.push(')');
    } ;

    var cleanWhiteSpace = function(result) {
        result = result.replace(/\t/g,   "    ");
        result = result.replace(/\r\n/g, "\n");
        result = result.replace(/\r/g,   "\n");
        // result = result.replace(/^(\s*\S*(\s+\S+)*)\s*$/, '$1'); // Right trim by Igor Poteryaev.
        return result;
    } ;

    var scrubWhiteSpace = function(result) {
        result = result.replace(/^\s+/g,   "");
        result = result.replace(/\s+$/g,   "");
        result = result.replace(/\s+/g,   " ");
        // result = result.replace(/^(\s*\S*(\s+\S+)*)\s*$/, '$1'); // Right trim by Igor Poteryaev.
        return result;
    } ;

    // The DOM helper functions depend on DOM/DHTML, so they only work in a browser.
    // However, these are not considered core to the engine.
    //
    TrimPath.parseDOMTemplate = function(elementId, optDocument, optEtc) {
        if (optDocument == null)
            optDocument = document;
        var element = optDocument.getElementById(elementId);
        var content = element.value;     // Like textarea.value.
        if (content == null)
            content = element.innerHTML; // Like textarea.innerHTML.
        content = content.replace(/&lt;/g, "<").replace(/&gt;/g, ">");
        return TrimPath.parseTemplate(content, elementId, optEtc);
    } ;

    TrimPath.processDOMTemplate = function(elementId, context, optFlags, optDocument, optEtc) {
        return TrimPath.parseDOMTemplate(elementId, optDocument, optEtc).process(context, optFlags);
    } ;
}) ();





if (!this.JSON) {
    this.JSON = {};
}

(function () {

    function f(n) {
        // Format integers to have at least two digits.
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function (key) {

            return isFinite(this.valueOf()) ?
                   this.getUTCFullYear()   + '-' +
                 f(this.getUTCMonth() + 1) + '-' +
                 f(this.getUTCDate())      + 'T' +
                 f(this.getUTCHours())     + ':' +
                 f(this.getUTCMinutes())   + ':' +
                 f(this.getUTCSeconds())   + 'Z' : null;
        };

        String.prototype.toJSON =
        Number.prototype.toJSON =
        Boolean.prototype.toJSON = function (key) {
            return this.valueOf();
        };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

// If the string contains no control characters, no quote characters, and no
// backslash characters, then we can safely slap some quotes around it.
// Otherwise we must also replace the offending characters with safe escape
// sequences.

        escapable.lastIndex = 0;
        return escapable.test(string) ?
            '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' :
            '"' + string + '"';
    }


    function str(key, holder) {

// Produce a string from holder[key].

        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];

// If the value has a toJSON method, call it to obtain a replacement value.

        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

// If we were called with a replacer function, then call the replacer to
// obtain a replacement value.

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

// What happens next depends on the value's type.

        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':

// JSON numbers must be finite. Encode non-finite numbers as null.

            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':

// If the value is a boolean or null, convert it to a string. Note:
// typeof null does not produce 'null'. The case is included here in
// the remote chance that this gets fixed someday.

            return String(value);

// If the type is 'object', we might be dealing with an object or an array or
// null.

        case 'object':

// Due to a specification blunder in ECMAScript, typeof null is 'object',
// so watch out for that case.

            if (!value) {
                return 'null';
            }

// Make an array to hold the partial results of stringifying this object value.

            gap += indent;
            partial = [];

// Is the value an array?

            if (Object.prototype.toString.apply(value) === '[object Array]') {

// The value is an array. Stringify every element. Use null as a placeholder
// for non-JSON values.

                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }

// Join all of the elements together, separated with commas, and wrap them in
// brackets.

                v = partial.length === 0 ? '[]' :
                    gap ? '[\n' + gap +
                            partial.join(',\n' + gap) + '\n' +
                                mind + ']' :
                          '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }

// If the replacer is an array, use it to select the members to be stringified.

            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    k = rep[i];
                    if (typeof k === 'string') {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {

// Otherwise, iterate through all of the keys in the object.
				
				
				/* bleujin
                for (k in value) {
                	
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
                */
            }

// Join all of the member texts together, separated with commas,
// and wrap them in braces.

            v = partial.length === 0 ? '{}' :
                gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +
                        mind + '}' : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }

// If the JSON object does not yet have a stringify method, give it one.

    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {

// The stringify method takes a value and an optional replacer, and an optional
// space parameter, and returns a JSON text. The replacer can be a function
// that can replace values, or an array of strings that will select the keys.
// A default replacer method can be provided. Use of the space parameter can
// produce text that is more easily readable.

            var i;
            gap = '';
            indent = '';

// If the space parameter is a number, make an indent string containing that
// many spaces.

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

// If the space parameter is a string, it will be used as the indent string.

            } else if (typeof space === 'string') {
                indent = space;
            }

// If there is a replacer, it must be a function or an array.
// Otherwise, throw an error.

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                     typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

// Make a fake root object containing our value under the key of ''.
// Return the result of stringifying the value.

            return str('', {'': value});
        };
    }


// If the JSON object does not yet have a parse method, give it one.

    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {

// The parse method takes a text and an optional reviver function, and returns
// a JavaScript value if the text is a valid JSON text.

            var j;

            function walk(holder, key) {

// The walk method is used to recursively walk the resulting structure so
// that modifications can be made.

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }


// Parsing happens in four stages. In the first stage, we replace certain
// Unicode characters with escape sequences. JavaScript handles many characters
// incorrectly, either silently deleting them, or treating them as line endings.

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

// In the second stage, we run the text against regular expressions that look
// for non-JSON patterns. We are especially concerned with '()' and 'new'
// because they can cause invocation, and '=' because it can cause mutation.
// But just to be safe, we want to reject all unexpected forms.

// We split the second stage into 4 regexp operations in order to work around
// crippling inefficiencies in IE's and Safari's regexp engines. First we
// replace the JSON backslash pairs with '@' (a non-JSON character). Second, we
// replace all simple value tokens with ']' characters. Third, we delete all
// open brackets that follow a colon or comma or that begin the text. Finally,
// we look to see that the remaining characters are only whitespace or ']' or
// ',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

            if (/^[\],:{}\s]*$/
.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
.replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

// In the third stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

                j = eval('(' + text + ')');

// In the optional fourth stage, we recursively walk the new structure, passing
// each name/value pair to a reviver function for possible transformation.

                return typeof reviver === 'function' ?
                    walk({'': j}, '') : j;
            }

// If the text is not JSON parseable, then a SyntaxError is thrown.

            throw new SyntaxError('JSON.parse');
        };
    }
}());







