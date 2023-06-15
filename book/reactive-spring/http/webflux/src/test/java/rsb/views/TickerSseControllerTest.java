package rsb.views;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rsb.utils.IntervalMessageProducer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

@AutoConfigureWebFlux
@AutoConfigureWebTestClient
public class TickerSseControllerTest {

	@Test
	public void thymeleafSseView() throws Exception {

		var templateResolver = new ClassLoaderTemplateResolver() {
			{
				setPrefix("/templates/");
				setSuffix(".html");
			}
		};

		var springWebFluxTemplateEngine = new SpringWebFluxTemplateEngine() {
			{
				setTemplateResolver(templateResolver);
			}
		};

		var bufferFactory = new DefaultDataBufferFactory();

		var htmlMediaType = MediaType.TEXT_HTML;
		var charset = StandardCharsets.UTF_8;

		var template = "ticker";
		var selectors = new HashSet<String>();

		var take = 2;
		var context = new Context();
		context.setVariable("updates",
				new ReactiveDataDriverContextVariable(IntervalMessageProducer.produce().take(take), 1));

		var publisher = Flux.from(springWebFluxTemplateEngine.processStream(template, selectors, context, bufferFactory,
				htmlMediaType, charset)).map(this::bufferAsString);

		StepVerifier.Step<String> stringStep = StepVerifier.create(publisher)
				.expectNextMatches(html -> html.contains("<title>Tick Tock</title>"));

		for (var i = 0; i < take; i++) {
			var capture = 1 + i;
			stringStep = stringStep.expectNextMatches(html -> html.contains("# " + capture));
		}

		stringStep.expectNextMatches(html -> html.contains("</html>")).verifyComplete();

	}

	private String bufferAsString(DataBuffer dataBuffer) {
		try (var is = dataBuffer.asInputStream(); var ir = new InputStreamReader(is)) {
			return FileCopyUtils.copyToString(ir);
		} //
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}