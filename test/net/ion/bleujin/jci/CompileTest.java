package net.ion.bleujin.jci;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.ion.bleujin.JavaCompilerFactory;
import net.ion.framework.util.Debug;

import org.apache.commons.jci.compilers.CompilationResult;
import org.apache.commons.jci.compilers.JavaCompiler;
import org.apache.commons.jci.readers.FileResourceReader;
import org.apache.commons.jci.stores.FileResourceStore;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.ProxyClassLoader;

public class CompileTest extends TestCase {

	public void testFirst() throws Exception {
		File sourceDir = new File("./imsi/");
		File targetDir = new File("./imsi/");
		String[] sources = new String[] { "HelloWorld.java" };

		JavaCompiler compiler = new JavaCompilerFactory().createCompiler("eclipse");
		assertNotNull("compiler is null", compiler) ;
		CompilationResult result = compiler.compile(sources, new FileResourceReader(sourceDir), new FileResourceStore(targetDir));

		Debug.debug(result.getErrors());
		assertEquals(0, result.getErrors().length);
		assertEquals(0, result.getWarnings().length);
	}
	
	public void testJCRLoad() throws Exception {
		
		JarClassLoader jcl = new JarClassLoader() ;
		jcl.add(new FileInputStream("./lib/fat/core_fat.jar")) ;
		jcl.add(new FileInputStream("./lib/imsi/core_lucene_fat.jar")) ;
		jcl.add("./lib/imsi/imsi.jar") ;
		ProxyClassLoader loader = jcl.getCurrentLoader() ;
		
		Class helloClass = loader.loadClass("net.ion.rest.ics.category.CategoryCRUDRest", true) ;
		Debug.debug("class:", helloClass) ;
		
		JclObjectFactory factory = JclObjectFactory.getInstance() ;
		Object obj = factory.create(jcl, "HelloWorld");

		Method m = obj.getClass().getMethod("hello") ;
		
		m.invoke(obj) ;	
	}
	
	public void testClassForName() throws Exception {
		printClassLoader(ClassLoader.getSystemClassLoader(), "gnu.getopt.GetoptDemo", false) ;
	}
	
	public void testSimpleJarLoad() throws Exception {
		JarClassLoader jcl = new JarClassLoader() ;
		jcl.add(new FileInputStream("./imsi/js.jar")) ;
		ProxyClassLoader loader = jcl.getCurrentLoader() ;

		
		printClassLoader(jcl, "org.mozilla.javascript.UintMap", false) ;
		// printClassLoader(ClassLoader.getSystemClassLoader(), "org.mozilla.javascript.UintMap", false) ;
		
		JarClassLoader newLoader = new JarClassLoader() ;
		Class newClz = newLoader.loadClass("org.mozilla.javascript.UintMap", true) ;
		Object newObj = newClz.newInstance() ;
		Debug.debug("class:", newClz, "object:", newObj) ;

		ClassLoader sysLoader = ClassLoader.getSystemClassLoader() ;
		Class sysClz = sysLoader.loadClass("org.mozilla.javascript.UintMap") ;
		Object sysObj = sysClz.newInstance() ;
		Debug.debug("class:", sysClz, "object:", sysObj) ;
	}
	
	public void testURLClassLoader() throws Exception {
		
		//testSimpleJarLoad() ;
		Debug.line() ;
		
		List<URL> targets = new ArrayList<URL>() ;
		targets.add(new File("./imsi/").toURL()) ;
		String[] jarNames = new String[]{"./imsi/js.jar"} ;
		for (String jarName : jarNames) {
			URL url = new URL("jar:file:" + new File(jarName).getAbsolutePath() + "!/");
			targets.add(url) ;
		}
		
		
		URLClassLoader uloader = URLClassLoader.newInstance(targets.toArray(new URL[0]), ClassLoader.getSystemClassLoader());
		printClassLoader(uloader, "net.ion.bleujin.HelloWorld", true) ;
		
		printClassLoader(uloader, "org.mozilla.javascript.UintMap", false) ;
	}

	
	

	public void testJarLoading() throws Exception {
		List<URL> jarURLs = makeURL() ;
		URL helloURL =  new File("./imsi/").toURL() ;
		jarURLs.add(helloURL) ;
		
		URLClassLoader parent = URLClassLoader.newInstance(jarURLs.toArray(new URL[0]), URL.class.getClassLoader());

		Debug.debug(parent.loadClass("org.apache.lucene.util.ArrayUtil").newInstance()) ;
		Debug.debug(parent.loadClass("org.apache.lucene.analysis.Analyzer")) ;

		Class clz = parent.loadClass("HelloWorld");
		Object obj = clz.newInstance() ;

		Method m = clz.getMethod("hello") ;
		
		m.invoke(obj) ;		
	}
	
	private List<URL> makeURL() throws MalformedURLException{
		List<URL> result = new ArrayList<URL>() ;
		String[] jarNames = new String[]{"./lib/fat/core_lucene_fat.jar", "./lib/fat/core_fat.jar", "./lib/fat/common_apache_fat.jar", "./lib/fat/apache_extend_fat.jar"} ;
		for (String jarName : jarNames) {
			URL url = new URL("jar:file:" + new File(jarName).getAbsolutePath() + "!/");
			result.add(url) ;
		}
		return result ;
	}
	
	
	private void printClassLoader(ClassLoader uloader, String clsName, boolean invokeMethod) throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
		Class clz = uloader.loadClass(clsName) ;
		final Object obj = clz.newInstance();
		Debug.debug("clz:", clz, "object:", obj) ;
		if (invokeMethod){
			clz.getMethod("hello").invoke(obj) ;			
		}
	}
	
}
