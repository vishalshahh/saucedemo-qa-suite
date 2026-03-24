package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        // Path to your feature files
        features = "src/test/resources/features",

        // Package where your step definitions live
        glue = "stepDefs",

        // Report formats
        plugin = {
                "pretty",                                    // readable console output
                "html:target/cucumber-reports/report.html", // HTML report
                "json:target/cucumber-reports/report.json"  // JSON for CI tools
        },

        // false = run all scenarios (not just @wip tagged ones)
        dryRun = false,

        // true = cleaner console output (no ANSI color codes)
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // This override enables PARALLEL execution of scenarios
    // Remove it if you want sequential execution (simpler for now)
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
