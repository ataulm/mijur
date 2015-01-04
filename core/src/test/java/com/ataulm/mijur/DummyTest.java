package com.ataulm.mijur;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class DummyTest {

    @Test
    public void noExplody() {
        boolean expected = true;
        boolean actual = true;
        assertThat(actual).isEqualTo(expected);
    }

}
