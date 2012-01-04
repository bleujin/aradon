package net.ion.radon.util;

import net.ion.radon.core.let.TestMultiValueMap;
import net.ion.radon.param.TestParamterKey;
import net.ion.radon.param.request.TestAradonParameter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestMultiValueMap.class, TestAradonParameter.class, TestParamterKey.class, TestOptions.class})
public class TestSuiteCommon {

}
