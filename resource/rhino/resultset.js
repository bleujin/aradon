importPackage(net.ion.framework.db) ;


println("Employee") ;

while(rows.next()){
	println(rows.getString("ename")) ;
}