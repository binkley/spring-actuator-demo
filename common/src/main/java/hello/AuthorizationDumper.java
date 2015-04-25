package hello;

import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.event.AbstractAuthorizationEvent;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * {@code AuditDumper} <strong>needs documentation</strong>.
 *
 * @author <a href="mailto:boxley@thoughtworks.com">Brian Oxley</a>
 * @todo Needs documentation
 */
@Configuration
public class AuthorizationDumper
        implements ApplicationListener<AbstractAuthorizationEvent> {
    private final Logger logger = getLogger(getClass());

    @Override
    public void onApplicationEvent(final AbstractAuthorizationEvent event) {
        logger.error("*** SEE MEE!! {}", event);
    }
}
