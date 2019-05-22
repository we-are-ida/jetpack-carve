package be.ida.jetpack.carve.manager.constants;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class PersistenceConstantsTest {

    @Test
    public void testConstructor() {
        boolean exception = false;

        try {
            new PersistenceConstants();
        } catch (UnsupportedOperationException e) {
            exception = true;
        }

        assertThat(exception).isTrue();
    }
}
