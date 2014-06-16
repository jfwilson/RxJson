import com.googlecode.jcompilo.Environment;
import com.googlecode.jcompilo.convention.AutoBuild;

import java.lang.Override;
import java.util.Properties;

public class build extends AutoBuild {

    @Override
    public Properties lastCommitData() {
        return new Properties();
    }
}