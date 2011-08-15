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
};

String.prototype.defaultString = function(defaultStr){
	return this == '' ? defaultStr : this ;
};

String.prototype.equalsIgnoreCase = function(_that){
	return this.toLowerCase() == _that.toLowerCase() ;
};

String.prototype.substringBefore = function (separator){
	strArray = this.split(separator) ;
	return strArray[0] ;
};

String.prototype.substringAfter = function (separator){
	strArray = this.split(separator) ;
	return this.substr(strArray[0].length+1) ;
};

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
};

String.prototype.substringBetweenLast = function(beginStr, endStr) {
	beginLoc = this.lastIndexOf(beginStr) ;
	return this.substring(beginLoc + beginStr.length, this.indexOf(endStr, beginLoc + beginStr.length)) ;
};

String.prototype.repeat = function(num){
	result = '' ;
	for (i=0 ; i < num ; i++){
		result += this ;
	}
	return result ;
};

//ibr_11644
String.prototype.replaceNative = String.prototype.replace;
String.prototype.replace = function(_this, _that) {
	if(typeof( _this) == 'string' && typeof( _that) == 'string' ) {
		return this.split(_this).join(_that);
	} 
	return this.replaceNative(_this, _that);
} ;


String.prototype.lpad = function (str, num){
	return '' + str.repeat(num -this.length) + this ;
};

String.prototype.stripTag = function () { 
      return this.replace('<', '&lt;').replace('>', '&gt;'); 
};

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
}

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
}
String.prototype.isAlphaNumUnderBarHyphen = function(){
	return (/^[a-zA-Z0-9_\-]+$/.test(this));
}
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
}
String.prototype.isUploadFileNames = function(){ // input file name 
	if(this.length > 0 ){
		return (/^[a-zA-Z0-9_()\-.]+$/.test(this));
	}
	return true;
}
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
}

String.prototype.isFloatFormat = function() {
	return !(/[^0-9.]/g.test(this)) && this.split('.').length <= 2;
}

String.prototype.isExpectFileType = function(expectedFileType){
	if(this == '') return true ;
	if (expectedFileType.toLowerCase() == 'all') return true;
	return expectedFileType.equalsIgnoreCase(this.substringAfterLast('.')) ;
}
String.prototype.isImageFileType = function(){
	if(this == '') return true ;
	var imgExt = ',jpg,gif,rgb,pbm,pgm,ppm,tiff,tif,rast,xbm,jpeg,bmp,png,';
	if (imgExt.indexOf(',' + this.substringAfterLast('.').toLowerCase() + ',') >= 0) {
			return true ;
		} else return false ;
}
String.prototype.isNotIncludeFullWidthSpace = function(){
	for(var z=0;z< this.length;z++){
		var c = this.charAt(z);
		if ( escape( c ) == "%u3000" ) return false ;
	}
    return true;
}



// extend Number
Number.prototype.lpad = function(num){
	return this.toString().lpad('0', num) ;
}

Number.prototype.round = function round(n) {
    return this.toFixed(n);
}



// extend Date
Date.prototype.dayFormat = function() {
	return '' + this.getFullYear() + (this.getMonth() + 1).lpad(2) + this.getDate().lpad(2) ;
}

Date.prototype.defaultFormat = function() {
	return '' + this.getFullYear() + (this.getMonth() + 1).lpad(2) + this.getDate().lpad(2) + '-' 
			+ this.getHours().lpad(2) + this.getMinutes().lpad(2) + this.getSeconds().lpad(2);
}

Date.prototype.addDay = function(_yyyy, _mm, _dd, _hh, _mi, _ss){
	this.setFullYear(this.getFullYear() + _yyyy) ;
	this.setMonth((this.getMonth()-0) + _mm) ;
	this.setDate(this.getDate() + _dd) ;
	this.setHours(this.getHours() + _hh) ;
	this.setMinutes(this.getMinutes() + _mi) ;
	this.setSeconds(this.getSeconds() + _ss) ;
	
	return this ;
}

Date.prototype.addYear = function(num){
	return this.addDay(num, 0, 0, 0, 0, 0);
}
Date.prototype.addMonth = function(num){
	return this.addDay(0, num, 0, 0, 0, 0);
}
Date.prototype.addDate = function(num){
	return this.addDay(0, 0, num, 0, 0, 0);
}
Date.prototype.addHour = function(num){
	return this.addDay(0, 0, 0, num, 0, 0);
}
Date.prototype.addMinute = function(num){
	return this.addDay(0, 0, 0, 0, num, 0);
}
Date.prototype.addSecond = function(num){
	return this.addDay(0, 0, 0, 0, 0, num);
}

Date.prototype.getStrYear = function(){
	return this.getFullYear().lpad(4) ;
}
Date.prototype.getStrMonth = function(){
	return (this.getMonth() + 1).lpad(2) ;
}
Date.prototype.getStrDate = function(){
	return this.getDate().lpad(2) ;
}
Date.prototype.getStrHour = function(){
	return this.getHours().lpad(2) ;
}
Date.prototype.getStrMinute = function(){
	return this.getMinutes().lpad(2) ;
}
Date.prototype.getStrSecond = function(){
	return this.getSeconds().lpad(2) ;
}

Date.prototype.equalDate = function(_that){
	return this.dayFormat() == _that.dayFormat() ;
}

Date.prototype.isToday = function(){
	return this.equalDate(new Date()) ;
}

Date.prototype.getLastDate = function(){
	return (new Date(this.getFullYear(), this.getMonth() + 1, 0)).getStrDate();
}



function $() {
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

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



Function.prototype.bindAsEventListener = function(object) {
  var __method = this;
  return function(event) {
    __method.call(object, event || window.event);
  }
}






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





/* yyyy-mm-dd */
ObjectUtil.isDate = function(s) {
    if (!(/^\d{4,4}-\d{2,2}-\d{2,2}$/.test(s))) { return false; }
    var a = s.split("-");
    var d = new Date(a[0], Number(a[1])-1, a[2]);
    d = [d.getFullYear().toString(), (d.getMonth()+1).toString(), d.getDate().toString()];
    if (!d[0].length || !d[1].length || !d[2].length) { return false; }
    if (d[1].length == 1) { d[1] = "0"+d[1]; }
    if (d[2].length == 1) { d[2] = "0"+d[2]; }
    return a[0] == d[0] && a[1] == d[1] && a[2] == d[2];
}
/* hh:mm:ss */
ObjectUtil.isHour = function(s) {
    if (!(/^\d{2,2}:\d{2,2}:\d{2,2}$/.test(s))) { return false; }
    var a = s.split(":");
    a[0] = Number(a[0]);
    a[1] = Number(a[1]);
    a[2] = Number(a[2]);
    return a[0] >= 0 && a[0] <= 23 &&
        a[1] >= 0 && a[1] <= 59 &&
        a[2] >= 0 && a[2] <= 59;
}
/* yyyy-mm-dd hh:mm:ss */
ObjectUtil.isDateIso = function(s) {
    if (!(/^\d{4,4}-\d{2,2}-\d{2,2} \d{2,2}:\d{2,2}:\d{2,2}$/.test(s))) { return false; }
    var a = s.split(" ");
    return isDate(a[0]) && isHour(a[1]);
}

/* -0.01, 10, 10.45 - ok
   01, 00.1, .1, 0.0.0 - bad */
ObjectUtil.isNumber = function(s) {
    if (s.length && s.charAt(0) == "-") { return isNumber(s.substr(1)); }
    if (!(/^[\d.]+$/.test(s))) { return false; }
    if (s.indexOf(".") != -1 && (s.indexOf(".") != s.lastIndexOf("."))) { return false; }
    if (s.charAt(0) == ".") { return false; }
    if (s.length >= 2 && s.charAt(0) == "0" && s.charAt(1) != ".") { return false; }
    return !isNaN(s);
}
ObjectUtil.isEmail = function(s) {
    return (/^\w+@\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
}
/* isHttpAddress("gosu.pl") - true
   isHttpAddress("www.gosu.pl") - true
   isHttpAddress("www.gosu.pl", 1) - false
   isHttpAddress("https://gosu.pl", 1) - true */
ObjectUtil.isHttpAddress = function (s, full) {
    if (full) {
        return (/^http(s)?:\/\/(www\.)?\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
    } else {
        return (/^(http(s)?:\/\/)?(www\.)?\w+\.[\w.]+$/.test(s) && s.charAt(s.length-1) != ".");
    }
}
/* checkSize("12", 4, 16) - true
   checkSize("12", null, 16) - true
   checkSize("12", 4, null) - true
   checkSize("12", 13) - false */
ObjectUtil.checkSize = function (s, min, max) {
    var n = Number(s);
    if (typeof min == "number") {
        if (n < min) { return false; }
    }
    if (typeof max == "number") {
        if (n > max) { return false; }
    }
    return true;
}
/* checkLength("abcdef", 4, 9) - true
   checkLength("abcdef", null, 9) - true
   checkLength("abcdef", 4, null) - true
   checkLength("abcdef", null, 5) - false */
ObjectUtil.checkLength = function (s, min, max) {
    if (typeof min == "number") {
        if (s.length < min) { return false; }
    }
    if (typeof max == "number") {
        if (s.length > max) { return false; }
    }
    return true;
}
ObjectUtil.isPesel = function(pesel) {
    if (pesel.length != 11 || !(/^\d+$/.test(pesel))) { return false; }
    var steps = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3];
    var sum_nb = 0, sum_m, sum_c;
    for (var x = 0; x < 10; ++x) {
        sum_nb += steps[x] * pesel[x];
    }
    sum_m = 10 - sum_nb % 10;
    if (sum_m == 10) { sum_c = 0; }
    else { sum_c = sum_m; }
    return (sum_c == pesel[10]);
}
ObjectUtil.isRegon = function(regon) {
    var steps = [8, 9, 2, 3, 4, 5, 6, 7];
    regon = regon.replace(/-/g, "");
    regon = regon.replace(/ /g, "");
    if (regon.length != 9) { return false; }
    var sum_nb = 0, sum_m;
    for (var x = 0; x < 8; ++x) {
        sum_nb += steps[x] * regon[x];
    }
    sum_m = sum_nb % 11;
    if (sum_m == 10) { sum_m = 0; }
    return (sum_m == regon[8]);
}
ObjectUtil.isNip = function(nip) {
    var steps = [6, 5, 7, 2, 3, 4, 5, 6, 7];
    nip = nip.replace(/-/g, "");
    nip = nip.replace(/ /g, "");
    if (nip.length != 10) { return false; }
    var sum_nb = 0, sum_m;
    for (var x = 0; x < 9; ++x) {
        sum_nb += steps[x] * nip[x];
    }
    sum_m = sum_nb % 11;
    if (sum_m == 10) { sum_m = 0; }
    return (sum_m == nip[9]);
}


