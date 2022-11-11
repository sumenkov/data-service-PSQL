package ru.sumenkov.dspsql;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;

public class LaunchOptions {
    public Options launchOptions() {

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(new Option("s", "search", false, "Поиск по параметрам: --search input.json output.json"));
        optionGroup.addOption(new Option("st", "stat", false, "Статистика за период: --stat input.json output.json"));

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        return options;
    }
}
