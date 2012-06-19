package net.ion.bulletin

// export JAVA_OPTS="-Dfile.encoding=UTF-8"


current = 1
next = 1

import groovy.beans.Bindable
@Bindable limit ;

limit = limit ?: 5


limit.times{
    print current + ' '
    newCurrent = next 
    next = next + current
    current = newCurrent 
}
print ' '
print new HelloWorld(name:"bleujin").hi()

class 한글 {
	def 지도 = [1:"흠", 2:"냥", 3:"컁"]
}

def 프린트 = {print it} 

new  한글().지도.each { 프린트(it.value) }

def html =  new groovy.xml.MarkupBuilder()

html.html(){
	body{} {
		for (p in new 한글().지도){
			h1(p.key){
			
			}
		}
	
	}
}

print("하이.")