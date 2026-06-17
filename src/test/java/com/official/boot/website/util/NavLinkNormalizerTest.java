package com.official.boot.website.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NavLinkNormalizerTest {

    @Test
    void internalRoute_prependsLeadingSlash() {
        assertThat(NavLinkNormalizer.normalize(2, "solutions/about")).isEqualTo("/solutions/about");
    }

    @Test
    void internalRoute_keepsAbsolutePath() {
        assertThat(NavLinkNormalizer.normalize(2, "/solutions/about")).isEqualTo("/solutions/about");
    }

    @Test
    void internalRoute_collapsesDoubleSlashes() {
        assertThat(NavLinkNormalizer.normalize(2, "//a//b")).isEqualTo("/a/b");
    }

    @Test
    void internalRoute_stripsTrailingSlash() {
        assertThat(NavLinkNormalizer.normalize(2, "/x/")).isEqualTo("/x");
    }

    @Test
    void internalRoute_preservesQuery() {
        assertThat(NavLinkNormalizer.normalize(2, "path?q=1")).isEqualTo("/path?q=1");
    }

    @Test
    void internalRoute_preservesFragment() {
        assertThat(NavLinkNormalizer.normalize(2, "path#s1")).isEqualTo("/path#s1");
    }

    @Test
    void pureFragment_isUnchanged() {
        assertThat(NavLinkNormalizer.normalize(2, "#s1")).isEqualTo("#s1");
    }

    @Test
    void externalLink_isUnchanged() {
        assertThat(NavLinkNormalizer.normalize(1, "https://x.com/a")).isEqualTo("https://x.com/a");
    }

    @Test
    void groupDirectory_returnsNull() {
        assertThat(NavLinkNormalizer.normalize(3, "anything")).isNull();
    }

    @Test
    void nullInput_returnsNull() {
        assertThat(NavLinkNormalizer.normalize(2, null)).isNull();
    }
}
