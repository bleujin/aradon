function ICS(session){
	this.path = 'http://localhost:8182/ics5/' ;
	this.session = session ;

	this.getCategory = function(catid){
		return new Category(this, catid);
	} ;
	
	this.getArticle = function(catid, artid){
		return new Article(this, catid, artid) ;
	} ;
	
	this.toString = function(){
		return this.path ;
	} ;
}


function Session(userId){
	this.userId = userId ;
	
	this.jsonString = function(){
		return "session:{userId:'" + this.userId + "'}";
	}
}


function Category(ics, catid){

	this.ics = ics ;
	this.catid = catid ;
	this.dataPath = '' ;

	this.listNode = function(){
		this.dataPath = 'category/' + this.catid + '/article/list.json' ;
		return eval('(' + this.execute(this, 'GET') + ')') ;
	} ; 

	this.getPath = function(){
		return this.getICSPath() + this.dataPath ;
	}
	
	
	this.execute = function(queryObj, ctype){ // private function
		var aj = new ajax(queryObj.getPath(), ctype) ;
		aj.addParam('parameter', queryObj.paramString()) ;
		aj.execMethod = function(parentThis, xmlRequest){
			parentThis.result = xmlRequest.responseText ;
			return parentThis ;
		}
		
		aj.connect(false) ;
		return aj.result ;
	}
	
	this.toString = function(){
		return this.getPath() ;
	} ;
}

Category.prototype = new Node() ;

function Article(ics, catid, artid){
	this.ics = ics ;
	this.catid = catid ;
	this.artid = artid ;
	this.dataPath = '' ;

	this.viewNode = function(){
		this.dataPath = 'category/' + this.catid + '/article/' + this.artid + '.json' ;
		return eval('(' + this.execute(this, 'GET') + ')') ;
	} ; 
}

Article.prototype = new Category() ;


function Node() {
	this.page = new Page(0, 0) ;
	this.params = new Params() ;

	this.getICSPath = function(){
		return this.ics.path ;
	}
	
	this.setParam = function(paramName, paramValue){
		this.params.addParam(paramName, paramValue) ;
	} ;

	this.setPage = function(listNum, pageNo){
		this.page = new Page(listNum, pageNo) ;
	} ;
		
	this.paramString = function(ctype){
		return this.page.jsonString() + ', ' + this.params.jsonString();
	} ;
	
	
}

function Page(listnum, pageno){
	this.listnum = listnum ;
	this.pageno = pageno ;
	
	this.jsonString = function(){
		if (this.listnum <= 0 || this.pageno <= 0) return "page:{}" ;
		
		return "page:{listnum:" + this.listnum + ", pageno:" + pageno + "}" ;
	}
}

function Params(){
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
	}
}

function Map(){
	
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
	}
}
	