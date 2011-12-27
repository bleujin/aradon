package net.ion.radon.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import net.ion.radon.core.let.TestMultiValueMap;
import net.ion.radon.param.TestParamterKey;
import net.ion.radon.param.request.TestAradonParameter;
import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestMultiValueMap.class, TestAradonParameter.class, TestParamterKey.class, TestOptions.class})
public class TestSuiteCommon {

}
