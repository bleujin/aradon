package net.ion.radon.core.filter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.script.TestGroovyFilter;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestFilter.class, TestGroovyFilter.class, TestAdvanseFilter.class})
public class TestSuiteFilter {

}
