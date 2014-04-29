package no.runsafe.sieve;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerCommandPreprocessEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandFilter implements IPlayerCommandPreprocessEvent, IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		rules.clear();
		for(String pattern : configuration.getConfigValueAsList("blocked"))
			rules.add(Pattern.compile(pattern));
	}

	@Override
	public void OnBeforePlayerCommand(RunsafePlayerCommandPreprocessEvent event)
	{
		for(Pattern rule : rules)
			if(rule.matcher(event.getMessage()).find())
				event.cancel();
	}

	private final List<Pattern> rules = new ArrayList<Pattern>();
}
